package com.chess.engine.board;

import java.util.HashMap;

import java.util.Map;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

public abstract class Tile
{
	public int getTileCoordinate() {
		return this.tileCoordinate;
	}
	
	protected final int tileCoordinate; // member field that represents tile number 
	//protected and final so it cannot be accessed by subclasses and final so the value stays the same once set into a constructor
	
	private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles(); /*if someone tries to add more entries to the hash map
	or clear it or even mutate the container they will get a runtime exception.
	*/
	
	/*
	 ^^ creates all of the empty tiles that are valid in the front when game starts. When we want to retrieve an empty tile, 
	 we can look it up in the cache
	 */
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles()
	{
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		for(int i = 0; i < BoardUtils.NUM_OF_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		/*There is a function also within JDK which is Collections.unmodifiableMap(emptyTileMap) I noticed this after already using the guava
		 * immutable map so I stuck with it. 
		*/
		return ImmutableMap.copyOf(emptyTileMap); //uses googles guava library (makes the hash map immutable).
	}

	public static Tile createTile(final int tileCoordinate, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
	} //only method someone can use to create a tile, and if they want a empty tile they will get one of the cached empty tiles.
	
	private Tile(final int tileCoordinate){ 
		this.tileCoordinate = tileCoordinate;
	}
	
	public abstract boolean isTileOccupied(); //Tells us if the tile is occupied or not.
	
	public abstract Piece getPiece(); //Gets the piece off of a given tile. If tile is occupied returns something if not returns null
	
	public static final class EmptyTile extends Tile{
		
		private EmptyTile(final int coordinate){
			super(coordinate);
		}
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		@Override
		public Piece getPiece() {
			return null;
		}
		/*
		 //I declared the classes in this file because I didnt want them to be an inner class with inherited state from the tile class 
		 (I wanted them to be their own for a convention)
		 */
		@Override public String toString() {
			return "-";
		}
	}	
	public static final class OccupiedTile extends Tile{
		
		private final Piece pieceOnTile; //There has to be a piece defined on an occupied tile
		
		private OccupiedTile(int tileCoordinate, Piece pieceOnTile){
			super(tileCoordinate); //needs the coordinate and piece
			this.pieceOnTile = pieceOnTile;
		}

		@Override
		public boolean isTileOccupied()
		{
			return true;
		}

		@Override
		public Piece getPiece()
		{
			return this.pieceOnTile;
		}
		public String toString() {
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
		}
	}
		

}
