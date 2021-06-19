package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.*;


public class Board
{
	
	private final List<Tile> gameBoard;
	private final Collection<Piece> whitePieces; //creates a collection of where the white pieces are
	private final Collection<Piece> blackPieces; //creates a collection of where the white pieces are
	
	private final WhitePlayer whitePlayer;
	private final BlackPlayer blackPlayer;
	private final Player currentPlayer;
	
	private Board(Builder builder) {
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE); // for the gameboard and white pieces
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK); //for the gameboard and black pieces
		
		final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces); 
		final Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);
		
		this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
		this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
		
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
	}
	
	@Override
	public String toString() { // this will print out a board, in ASCII text way
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < BoardUtils.NUM_OF_TILES; i++) {
			final String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			if((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}
	
	public Player whitePlayer() {
		return this.whitePlayer;
	}
	
	public Player blackPlayer() {
		return this.blackPlayer;
	}
	
	public Player currentPlayer() {
		return this.currentPlayer;
	}
	
	public Collection<Piece> getBlackPieces(){ //extracts the black pieces in order to keep track of them ^^ final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces); 
		return this.blackPieces;
	}
	
	public Collection<Piece> getWhitePieces(){ //extracts the white pieces in order to keep track of them ^^ final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces); 
		return this.whitePieces;
	}
	
	private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) 
	{/*calculates the legal moves for each colour(it will implement all of the legal moves from each class)
	*/
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final Piece piece : pieces) {
			//returns a collection of legal moves and adds them into the legal moves list
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		return ImmutableList.copyOf(legalMoves);
	}

	private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance)
	{
		final List<Piece> activePieces = new ArrayList<>();
	// stores the active pieces in an array list	
		for(final Tile tile : gameBoard) {
			if(tile.isTileOccupied()) {
				final Piece piece = tile.getPiece(); 
				if(piece.getPieceAlliance() == alliance) {
					activePieces.add(piece);
				}
			}
		}
		return ImmutableList.copyOf(activePieces); //returns the active pieces on a chess board
	}

	public Tile getTile(final int tileCoordinate) {
		return gameBoard.get(tileCoordinate); // retrieves the tile coordinate for the gameboard
	}	
	
	private static List<Tile> createGameBoard(final Builder builder) { //populates a list of tiles from 0-63
		final Tile[] tiles = new Tile[BoardUtils.NUM_OF_TILES];
		for(int i = 0; i < BoardUtils.NUM_OF_TILES; i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i)); //from the config where it will be setup I will map a piece onto a tile ID
		}// create tile will call a function from the builder (ex: set piece; which will set a piece onto the given tile ID) 
		return ImmutableList.copyOf(tiles);
	}
	
	public static Board createStandardBoard() { //creates the inital position for a chess board
		
		Builder builder = new Builder();
		
		//Black layout
		builder.setPiece(new Rook(Alliance.BLACK, 0)); //tiles[i] = Tile.createTile(i, builder.boardConfig.get(i)) << this line of code gets the piece on position (i) if it isnt null then it will create an occupied tile
		builder.setPiece(new Knight(Alliance.BLACK, 1)); // when presenting show create tile method above
		builder.setPiece(new Bishop(Alliance.BLACK, 2));
		builder.setPiece(new Queen(Alliance.BLACK, 3));
		builder.setPiece(new King(Alliance.BLACK, 4));
		builder.setPiece(new Bishop(Alliance.BLACK, 5));
		builder.setPiece(new Knight(Alliance.BLACK, 6));
		builder.setPiece(new Rook(Alliance.BLACK, 7));
		builder.setPiece(new Pawn(Alliance.BLACK, 8));
		builder.setPiece(new Pawn(Alliance.BLACK, 9));
		builder.setPiece(new Pawn(Alliance.BLACK, 10));
		builder.setPiece(new Pawn(Alliance.BLACK, 11));
		builder.setPiece(new Pawn(Alliance.BLACK, 12));
		builder.setPiece(new Pawn(Alliance.BLACK, 13));
		builder.setPiece(new Pawn(Alliance.BLACK, 14));
		builder.setPiece(new Pawn(Alliance.BLACK, 15));
		//White Layout
		builder.setPiece(new Pawn(Alliance.WHITE, 48));
		builder.setPiece(new Pawn(Alliance.WHITE, 49));
		builder.setPiece(new Pawn(Alliance.WHITE, 50));
		builder.setPiece(new Pawn(Alliance.WHITE, 51));
		builder.setPiece(new Pawn(Alliance.WHITE, 52));
		builder.setPiece(new Pawn(Alliance.WHITE, 53));
		builder.setPiece(new Pawn(Alliance.WHITE, 54));
		builder.setPiece(new Pawn(Alliance.WHITE, 55));
		builder.setPiece(new Rook(Alliance.WHITE, 56));
		builder.setPiece(new Knight(Alliance.WHITE, 57));
		builder.setPiece(new Bishop(Alliance.WHITE, 58));
		builder.setPiece(new Queen(Alliance.WHITE, 59));
		builder.setPiece(new King(Alliance.WHITE, 60));
		builder.setPiece(new Bishop(Alliance.WHITE, 61));
		builder.setPiece(new Knight(Alliance.WHITE, 62));
		builder.setPiece(new Rook(Alliance.WHITE, 63));
		
		builder.setMoveMaker(Alliance.WHITE);
		
		return builder.build();
	}
	
	public Iterable<Move> getAllLegalMoves() 
	{
		return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
		// method defined in guava
	}
	
	public static class Builder { // builder class helps me create an instance of a board
		
		Map<Integer, Piece> boardConfig;
		//Map <Tile coordinate, and what piece is on it>.
		
		Alliance nextMoveMaker; //allows me to say what color is next to move.
		
		public Builder() {
			this.boardConfig = new HashMap<>();
		}
		
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		
		
		public Builder setMoveMaker(final Alliance nextMoveMaker) {
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		public Board build() {
			return new Board(this);
			//set a bunch of mutable fields on the builder and then once it is invoked it will make an immutable copy of the board
		}
		
		
	}
	
}