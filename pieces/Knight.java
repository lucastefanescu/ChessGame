package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;
import static com.chess.engine.board.Move.*;

public class Knight extends Piece
{
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-17 , - 15 , - 10 , - 6 , 6 , 10 , 15 , 17}; 
	/*
	 * array that stores all of the legal moves based on if the chess board is #1-63 (fixed set of candidate coordinates)
	 */
	public Knight(final Alliance pieceAlliance, final int piecePosition)
	{
		super(piecePosition, pieceAlliance, PieceType.KNIGHT);
	}

	@Override
	public List<Move> calculateLegalMoves(final Board board)
	{	
		final List<Move> legalMoves = new ArrayList<>();
	
		for(int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) { // for each of the candidates in the array of candidates 
		
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset; // offsets position based on value of candidates (positive/negative) then updates value
			
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) { // if it is a valid tile coordinate (not out of bounds)

				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)
				|| isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) || isEigthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					continue; //if the exclusions are true, it will apply them and keep going through the loop
				}
				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate); //gets the coordinate of the destination tile and sets it to the current destination
				if(!candidateDestinationTile.isTileOccupied()) { // if the destination tile isn't occupied
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate)); //adds legal move to list
				} else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece(); //gets the piece on occupied tile
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance(); //gets the color of the piece on the tile
					//else (tile is occupied)
					if(this.pieceAlliance != pieceAlliance) { //if the piece color isn't the same as the one on the destination tile
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)); //adds legal move
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves); //returns the list of legal moves
	}
	
	@Override
	public String toString() {
		return PieceType.KNIGHT.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
		candidateOffset == 6 || candidateOffset == 15);		
		/*introduces an array of booleans in the board utils class. This methods purpose is to satisfy the exception to the knights movement
		* (when it is on the first column, the knight's movement is different/the candidate coordiantes don't apply) 
		*/
	}
	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
		//this method is the same as the first column exception and serves the same purpose as the first column method but has different values
	}
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == 10 || candidateOffset == -6);
	}
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 || candidateOffset == 10 ||
				candidateOffset == 17);
	}
	
	@Override
	public Knight movePiece(Move move)
	{
		return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
		//creates a new Knight thats exactly like the old Knight but in the new position
	}
}