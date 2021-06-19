package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public abstract class Player
{
	//player has to keep track of these 
	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	private final boolean isInCheck;
	
	Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves){	
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
		this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty(); 
		//do the opponents pieces attack the kings position if so then get those different attacks if it isn't empty if true then the player is in check
	}
	
	public King getPlayerKing() {
		return this.playerKing;
	}

	public Collection<Move> getLegalMoves(){
		return this.legalMoves;
	}
	
	protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) { /*passes in current
		tile location of king and opponent moves then it goes through each one of the destination coordinates and checks if the destination coordinates
		overlaps with the kings position*/
		final List<Move> attackMoves = new ArrayList<>();
		for(final Move move : moves) {
			if(piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return ImmutableList.copyOf(attackMoves);
	}
	
	private King establishKing() { // ensures there is a king on the board for the player
		for(final Piece piece : getActivePieces()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Should not reach here not a valid board");
	}
	
	public boolean isMoveLegal(final Move move) { //tests if the move being passed in is legal
		return this.legalMoves.contains(move);
	}
	
	public boolean isInCheck() {
		return this.isInCheck;
	}
	
	public boolean isInCheckmate() {
		return this.isInCheck && !hasEscapeMoves();
	}
	
	protected boolean hasEscapeMoves()
	{
		for(final Move move : this.legalMoves) { /*in order to see if the players king is checkmated, I am going through each of the legal moves
			and then it makes the moves on an imaginary board through class makeMove after we make the move it is done if not then the game is over
			*/
			final MoveTransition transition = makeMove(move);
			if(transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}

	public boolean isInStaleMate() {
		return !this.isInCheck && !hasEscapeMoves();
	}
	
	public boolean isCastled() {
		return false;
	}
	
	public MoveTransition makeMove(final Move move) {
		
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE); // if move is legal then it returns this board not another one
		}
		
		final Board transitionBoard = move.execute(); // uses the board to polymorphically execute the move and returns us the same board that we transition to
		
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
		transitionBoard.currentPlayer().getLegalMoves());
		//this determines if there are attacks the king, if there are then it doesn't allow the king to move.
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK); /* cant make this move so it leaves us
			 on the copy of this board
			*/
		}
		
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE); //once a move is made it will return a movetransition which will represent if the move was possible
	}
	
	public abstract Collection<Piece> getActivePieces(); // collection of all of the active pieces
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	protected abstract Collection <Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals);
	
}