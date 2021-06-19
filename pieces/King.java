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
import com.chess.engine.pieces.Piece.PieceType;

public class King extends Piece
{
	private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
	
	public King(final Alliance pieceAlliance, final int piecePosition)
	{
		super(piecePosition, pieceAlliance, PieceType.KING);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Move> calculateLegalMoves(Board board)
	{
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
			
		final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
		
		if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || 
		isEigthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
			continue;
		}
		
		if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
			final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);	
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
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.KING.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||
		candidateOffset == 7);		
		/*introduces an array of booleans in the board utils class. This methods purpose is to satisfy the exception to the knights movement
		* (when it is on the first column, the knight's movement is different/the candidate coordiantes don't apply) 
		*/
	}
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
		//this method is the same as the first column exception and serves the same purpose as the first column method but has different values
	}

	@Override
	public King movePiece(Move move)
	{
		return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
		//creates a new King thats exactly like the old King but in the new position
	}

}