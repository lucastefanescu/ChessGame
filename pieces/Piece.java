package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece
{
	protected final PieceType pieceType;
	protected final int piecePosition; // every piece will have a piece position (tile coordinate that is occupied).
	protected final Alliance pieceAlliance; // the piece will either be black or white
	protected final boolean isFirstMove;
	private final int cachedHashCode;
	
	Piece(final int piecePosition, final Alliance pieceAlliance, final PieceType pieceType){
		this.pieceType = pieceType;
		this.pieceAlliance = pieceAlliance;
		this.piecePosition = piecePosition;
		this.isFirstMove = false;
		this.cachedHashCode = computeHashCode();
	} // the constructor establishes the member fields, the position and alliance of the piece
	
	private int computeHashCode()
	{
		int result = pieceType.hashCode();
		result = 31 * result + pieceAlliance.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1 : 0);
		return result;
	}

	@Override
	public boolean equals(final Object other) { // implemented in move class
		if(this == other) {
			return true;
		}
		if(!(other instanceof Piece)) {
			return false;
		}
		final Piece otherPiece = (Piece) other;
		return  piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() && 
				pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
	} // these are classic methods that are commonly used in Java(equals and hashcode).
	
	@Override
	public int hashCode() {
		return this.cachedHashCode;
	}
	
	public abstract List<Move>calculateLegalMoves(final Board board);
	//I need to calculate a list of legal moves so I did this by using this method which returns a collection of moves (a list in my choice)
	//set cannot have duplicate entries and is unordered so I didn't want to use it.
	
	public abstract Piece movePiece(Move move);
	
	public boolean isFirstMove() {
		return this.isFirstMove;
	}
	
	public int getPiecePosition() {
		return this.piecePosition;
	}
	
	public PieceType getPieceType() {
		return this.pieceType;
	}
	
	public Alliance getPieceAlliance() {
		return this.pieceAlliance; //getter method for the color of the piece
	}
	
	public enum PieceType {
		
		PAWN("P") {
			@Override
			public boolean isKing()
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook()
			{
				// TODO Auto-generated method stub
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing()
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook()
			{
				// TODO Auto-generated method stub
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing()
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook()
			{
				// TODO Auto-generated method stub
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing()
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook()
			{
				// TODO Auto-generated method stub
				return true;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing()
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook()
			{
				// TODO Auto-generated method stub
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing()
			{
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean isRook()
			{
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		private String pieceName;
		
		PieceType(final String pieceName){
			this.pieceName = pieceName;
		}
		
		@Override 
		public String toString() {
			return this.pieceName;
		}
		
		public abstract boolean isKing();

		public abstract boolean isRook();
	}
}