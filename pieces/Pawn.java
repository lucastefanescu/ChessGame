package com.chess.engine.pieces;

import java.util.ArrayList;

import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece
{
	private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 16, 7, 9};
	
	public Pawn(final Alliance pieceAlliance, final int piecePosition)
	{
		super(piecePosition, pieceAlliance, PieceType.PAWN);
	}

	@Override
	public List<Move> calculateLegalMoves(final Board board)
	{
		final List<Move> legalmoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
			
			final int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);
			
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				legalmoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
			} else if(currentCandidateOffset == 16 && this.isFirstMove() && (BoardUtils.SECOND_ROW[this.piecePosition] 
				&& this.getPieceAlliance().isBlack()) || (BoardUtils.SEVENTH_ROW[this.piecePosition])
					&& this.getPieceAlliance().isWhite()) {
			final int behindCanidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
			if(board.getTile(behindCanidateDestinationCoordinate).isTileOccupied() 
			&& board.getTile(candidateDestinationCoordinate).isTileOccupied()){
				legalmoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
			}
			//exceptions to the movement/attacking of a pawn
			}else if(currentCandidateOffset == 7 && !((BoardUtils.EIGTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() 
			|| (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))){
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate  = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalmoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
					}
					}
			}else if(currentCandidateOffset == 9 && !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() 
			|| (BoardUtils.EIGTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))))
			{
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate  = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalmoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			}
		}
		return ImmutableList.copyOf(legalmoves);
	}
	
	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}

	@Override
	public Pawn movePiece(Move move)
	{
		return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
		//creates a new bishop thats exactly like the old bishop but in the new position
	}
	
}