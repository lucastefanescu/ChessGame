package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class Queen extends Piece
{

	private final static int[] CANDIDATE_MOVE_VECTOR_COORIDNATES = { -9, -8, -7, -1, 1, 7, 8, 9,};
	
	public Queen(final Alliance pieceAlliance, final int piecePosition)
	{
		super(piecePosition, pieceAlliance, PieceType.QUEEN);
	}

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
	public String toString() {
		return PieceType.QUEEN.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7 || candidateOffset == -1); 
	}
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == -7 || candidateOffset == 1);
	}

	@Override
	public Queen movePiece(Move move)
	{
		return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
		//creates a new Queen thats exactly like the old Queen but in the new position
	}

}
