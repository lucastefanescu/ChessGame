package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

public class BlackPlayer extends Player{

	public BlackPlayer(Board board, Collection<Move> whiteStandardMoves, Collection<Move> blackStandardMoves){
		super(board, blackStandardMoves, whiteStandardMoves);
	}
	
	@Override
	public Collection<Piece> getActivePieces()
	{
		// TODO Auto-generated method stub
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance()
	{
		// TODO Auto-generated method stub
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent()
	{
		// TODO Auto-generated method stub
		return this.board.whitePlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals)
	{
		final List<Move> kingCastles = new ArrayList<>();
		// black kingside castle
		if(this.playerKing.isFirstMove() && !this.isInCheck()) { //if King isn't in check then
			if(!this.board.getTile(7).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {// if the rook tiles are occupied 
				final Tile rookTile = this.board.getTile(7); //H1 rook should be on this tile
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) { // if it is the rooks first move
					if(Player.calculateAttacksOnTile(5, opponentsLegals).isEmpty()
							&& Player.calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
							rookTile.getPiece().getPieceType().isRook()) {
		
					kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 6, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
					}
				}
			}
			//black queenside castle
			if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(0);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && Player.calculateAttacksOnTile(2, opponentsLegals).isEmpty()
						&& Player.calculateAttacksOnTile(3, opponentsLegals).isEmpty() 
						&& rookTile.getPiece().getPieceType().isRook()) {
					kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
				}
			}
		}
		return ImmutableList.copyOf(kingCastles);
}
	
}
