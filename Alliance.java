package com.chess.engine;

import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

/* Before Enums were introduced to java, the standard way to represent an enumerated type was like this:
 * public static final int SEASON_WINTER = 0;
 * public static final int SEASON_SPRING = 1;
 * but the probelem with this is that it is not type safe; because season is an integer therefore you can pass in any other integer where
 * season is required. You can also define behavior easier on an enum(I chose it because I only want two common instances of this alliance, white and 
 * black).
 */

public enum Alliance 
{
	WHITE {
		@Override 
		public int getDirection() {
			return -1;
		}

		@Override
		public boolean isBlack()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isWhite()
		{
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer)
		{
			// TODO Auto-generated method stub
			return whitePlayer;
		}
	},
	BLACK{
		@Override 
		public int getDirection( ) {
			return 1;
		}

		@Override
		public boolean isBlack()
		{
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isWhite()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer)
		{
			// TODO Auto-generated method stub
			return blackPlayer;
		}
	};
	
	public abstract int getDirection(); //This method is used to set a direction for the pawns

	public abstract boolean isBlack();

	public abstract boolean isWhite();

	public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);

}	 	