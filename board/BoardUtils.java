package com.chess.engine.board;

public class BoardUtils
{
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	/*the exception of the knights movement in a chess game is limited to the first column hence this boolean
	 * is only true in the first column & the method 'isFirstColumnExclusion' only applies when this is true
	 */
	public static final boolean[] SECOND_COLUMN = initColumn(1);
	//same as first column
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);
	//same as first column
	public static final boolean[] EIGTH_COLUMN = initColumn(7);
	//same as first column
	
	//initcolumn passes values 0-7 instead of 1-8 because arrays start at 0
	
	public static final boolean[] SECOND_ROW = initRow(8); //creating a field for the second row in order to make the pawns movement
	public static final boolean[] SEVENTH_ROW = initRow(48); //creating a field for the seventh row in order to make the pawns movement
	
	public static final int NUM_OF_TILES = 64; //chess board has 63 tiles
	public static final int NUM_TILES_PER_ROW = 8; //chess board has 8 tiles per row
	public static final boolean[] FIRST_ROW = initRow(0);
	public static final boolean[] THIRD_ROW = initRow(16);
	public static final boolean[] FIFTH_ROW = initRow(32);
	public static final boolean[] FOURTH_ROW = initRow(24);
	public static final boolean[] SIXTH_ROW = initRow(40);
	public static final boolean[] EIGTH_ROW = initRow(56);
	
	private static boolean[] initColumn(int columnNumber) {// declares an array of booleans(64 booleans) with 1 parameter
		final boolean[] column = new boolean[NUM_OF_TILES]; 
		do {
			column[columnNumber] = true; // if there is a valid column number in the chess board
			columnNumber += NUM_TILES_PER_ROW; //it sets the whole column to true (initializes the entire column)
		}while(columnNumber < NUM_OF_TILES); // does it until the whole thing is initialized
			return column;
	} //allows me to initialize a particular column 
	
	private static boolean[] initRow(int rowNumber)
	{
		final boolean[] row = new boolean[NUM_OF_TILES];
		do {
			row[rowNumber] = true;
			rowNumber++;
		}while(rowNumber % NUM_TILES_PER_ROW != 0);
		
		return row; //checks all of the rows and sets them to true
	}

	private BoardUtils() {
		throw new RuntimeException("You Cannot Instantiate This constructor"); //if someone tries to instantiate this constructor, they will be given a runtime exception
	}
	
	public static boolean isValidTileCoordinate(int coordinate)
	{
		return coordinate >=0 && coordinate < 64; //if after applying the offset, the tile coordinate is valid (not out of bounds) it will be considered a valid tile cooridnate
	}
}
