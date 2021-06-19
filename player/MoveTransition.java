package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MoveTransition
{
//the class at its base will represent once a move is made the transition between one board to another.
	
	private final Board transitionBoard; //tells me what the new board is transitioning to after the move is made
	private final Move move; //what move is made
	private final MoveStatus moveStatus; //tells me if the move was possible or not
	
	public MoveTransition(final Board transitionBoard, final Move move, final MoveStatus moveStatus) {
		this.transitionBoard = transitionBoard;
		this.move = move;
		this.moveStatus = moveStatus;
	}
	
	public MoveStatus getMoveStatus() {
		return this.moveStatus;
	}
}
