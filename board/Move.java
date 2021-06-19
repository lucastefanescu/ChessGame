package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public abstract class Move
{
	//in chess you have to keep track of the board, what piece is moved and where it moves 
	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;
	
	public static final Move NULL_MOVE = new NullMove();
	
	private Move(final Board board, final Piece movedPiece, final int destinationCoordinate){
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.movedPiece.getPiecePosition();
		result = prime * result + this.destinationCoordinate;
		result = prime * result + this.movedPiece.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		if(!(other instanceof Move)) {
			return false;
		}
		final Move otherMove = (Move) other;
		
		return getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
				getMovedPiece().equals(otherMove.getMovedPiece());
	}
	
	public int getCurrentCoordinate() {
		return this.getMovedPiece().getPiecePosition();
	}
	
	public int getDestinationCoordinate() { 
		return this.destinationCoordinate;
	}
	
	public Piece getMovedPiece() {
		return this.movedPiece;
	}
	
	public boolean isAttack() {
		return false;
	}
	
	public boolean isCastlingMove() {
		return false;
	}
	
	public Piece getAttackedPiece() {
		return null;
	}
	
	public Board execute() {
		
		final Builder builder = new Builder(); // new builder makes a new board to execute
		
		for(final Piece piece : this.board.currentPlayer().getActivePieces()) { //traverses through the incoming boards current player
			if(!this.movedPiece.equals(piece)) { //for all of the pieces that arent moved
				builder.setPiece(piece); 
			}
		}
		
		for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {/* same thing is done for enemy pieces if statement isnt required 
			because the opponent doesn't move a piece*/
			builder.setPiece(piece);
		}
		//moves the moved piece
		builder.setPiece(this.movedPiece.movePiece(this));// represents the piece after its been moved	
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance()); //if it is whites turn it will set the movemaker to black
		
		return builder.build();	
	}
	
	public static final class MajorMove extends Move{ //subclass that defines a non-attacking move

		public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate)
		{
			super(board, movedPiece, destinationCoordinate);
		}
	}
		public static class AttackMove extends Move{ //subclass that defines an attacking move
			
			final Piece attackedPiece; //creates the piece being attacked
			
			public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece)
			{
				super(board, movedPiece, destinationCoordinate);
				this.attackedPiece = attackedPiece;
			}	
			
			@Override
			public int hashCode() {
				return this.attackedPiece.hashCode() + super.hashCode();
			}
			
			@Override
			public boolean equals(final Object other) {
				if(this == other) {
					return true;
				}
				
				if(!(other instanceof AttackMove)) {
					return false;
				}
				final AttackMove otherAttackMove = (AttackMove) other;
				return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
 			}
			
			public Board execute() {
				return null;
			}
			
			@Override
			public boolean isAttack() {
				return true;
			}
			
			@Override
			public Piece getAttackedPiece() {
				return this.attackedPiece;
			}
	}		
		
		public static final class PawnMove extends Move{ //subclass that defines a non-attacking move

			public PawnMove(final Board board, final Piece movedPiece, final int destinationCoordinate)
			{
				super(board, movedPiece, destinationCoordinate);
			}
		}
		
		public static class PawnAttackMove extends AttackMove{ //subclass that defines a non-attacking move

			public PawnAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece)
			{
				super(board, movedPiece, destinationCoordinate, attackedPiece);
			}
		}
		
		public static final class PawnJump extends Move{ //subclass that defines a non-attacking move

			public PawnJump(final Board board, final Piece movedPiece, final int destinationCoordinate)
			{
				super(board, movedPiece, destinationCoordinate);
			}
			
			@Override
			public Board execute() {
				final Builder builder = new Builder();
				for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
					if(!this.movedPiece.equals(piece)) {
						builder.setPiece(piece);
					}
				}
				for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
					builder.setPiece(piece);
				}
				final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
				builder.setPiece(movedPawn);
				builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
				return builder.build();
			}
			
		}
		
		static abstract class CastleMove extends Move{ //subclass that defines a non-attacking move

			protected final Rook CastleRook;
			protected final int castleRookStartPosition;
			protected final int castleRookDestination;
			
			public CastleMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Rook castleRook, 
					final int castleRookStartPosition, final int castleRookDestination)
			{
				super(board, movedPiece, destinationCoordinate);
				this.CastleRook = castleRook;
				this.castleRookStartPosition = castleRookStartPosition;
				this.castleRookDestination = castleRookDestination;
			}
			public Rook getCastleRook() {
				return this.CastleRook;
			}
			@Override
			public boolean isCastlingMove() {
				return true;
			}
			@Override
			public Board execute() {
				
				final Builder builder = new Builder();
				for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
					if(!this.movedPiece.equals(piece) && !this.CastleRook.equals(piece)) {
						builder.setPiece(piece);
					}
				}
				for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
					builder.setPiece(piece);
				}
				builder.setPiece(this.movedPiece.movePiece(this));
				builder.setPiece(new Rook(this.CastleRook.getPieceAlliance(), this.castleRookDestination));
				builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
				return builder.build();			
			}
		}
		
		public static final class KingSideCastleMove extends CastleMove{ //subclass that defines a non-attacking move

			public KingSideCastleMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Rook castleRook, 
					final int castleRookStartPosition, final int castleRookDestination)
			{
				super(board, movedPiece, destinationCoordinate, castleRook, castleRookStartPosition, castleRookDestination);
			}
			public String toString() {
				return "O-O";
			}
		}
		
		public static final class QueenSideCastleMove extends CastleMove{ //subclass that defines a non-attacking move

			public QueenSideCastleMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Rook castleRook, 
					final int castleRookStartPosition, final int castleRookDestination)
			{
				super(board, movedPiece, destinationCoordinate, castleRook, castleRookStartPosition, castleRookDestination);
			}
			public String toString() {
				return "O-O-O";
			}
		}
		
		public static final class NullMove extends Move{ //subclass that defines a non-attacking move

			public NullMove()
			{
				super(null, null, -1);
			}
			
			@Override
			public Board execute() {
				throw new RuntimeException("cannot execute the move because it is invalid");
			}
		}
		
		public static class MoveFactory { // factory class that will have a convenience method
			
			private MoveFactory() {
				throw new RuntimeException("Not instantiable");
			}

			public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {
				
				for(final Move move : board.getAllLegalMoves()) {
					if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate) {
						return move;
					}
				}
				return NULL_MOVE;
			}
	}
}