package com.chess.engine.player;

public enum MoveStatus
{
	DONE {
		@Override
		boolean isDone()
		{
			// TODO Auto-generated method stub
			return true;
		}
	},
	ILLEGAL_MOVE {
		@Override
		boolean isDone()
		{
			// TODO Auto-generated method stub
			return false; //returns false because the game isn't done
		}
	},
	LEAVES_PLAYER_IN_CHECK {
		@Override
		boolean isDone()
		{
			// TODO Auto-generated method stub
			return false; //game isn't done because there is no checkmate or stalemate, the player will remain in check
		}
	};
	
	abstract boolean isDone();
}
