package com.chess.engine.pieces;

import java.util.ArrayList;

import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;

public class Bishop extends Piece
{
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORIDNATES = { -9, -7, 7, 9};
	
	public Bishop(final Alliance pieceAlliance, final int piecePosition)
	{
		super(piecePosition, pieceAlliance, PieceType.BISHOP);
	}

	@Override
	public List<Move> calculateLegalMoves(final Board board)
	{
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORIDNATES) {
			
			int candidateDestinationCoordinate = this.piecePosition;
			
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) { //is the destination valid
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) || 
				isEigthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break;
				}
				candidateDestinationCoordinate += candidateCoordinateOffset;		// applies offset
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
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
						break; //if the tile is occupied dont keep checking if the points are valid
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public Bishop movePiece(Move move)
	{
		return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
		//creates a new bishop thats exactly like the old bishop but in the new position
	}
	
	@Override
	public String toString() {
		return PieceType.BISHOP.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7); 
	}
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == -7);
	}

	
}
