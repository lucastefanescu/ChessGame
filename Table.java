package com.chess.engine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

public class Table{ //GUI
	
	private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#769656");
	
	private final BoardPanel boardPanel;
	private final JFrame gameFrame;
	
	private final Board chessBoard;

	private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(800, 800);
	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(800, 800);
	private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
	
	private static String defaultPieceImagesPath = "simple/simple";
	
	public Table() {
		this.gameFrame = new JFrame("JChess");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setVisible(true);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		this.chessBoard = Board.createStandardBoard();
	}

	private JMenuBar createTableMenuBar(){
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		return tableMenuBar;
	}

	private JMenu createFileMenu()
	{
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openPGN = new JMenuItem("Load PGN file");
		openPGN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("");
				
			}
			
	});
		fileMenu.add(openPGN);
		
		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		fileMenu.add(exitMenuItem);
		
		return fileMenu;
}
	
	private class BoardPanel extends JPanel {
		
		final java.util.List<TilePanel> boardTiles;
		
		BoardPanel(){
			super(new GridLayout(8,8)); //creates a grid layout of 8 * 8 grid
			this.boardTiles = new ArrayList<>();
			for(int i = 0; i < BoardUtils.NUM_OF_TILES; i++) {
				final TilePanel tilePanel = new TilePanel(this, i);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(BOARD_PANEL_DIMENSION);
			validate();
		}
		
	}
	
	private class TilePanel extends JPanel {
	
		private final int tileID;
		
		TilePanel(final BoardPanel boardPanel, final int tileID){
			super(new GridBagLayout());
			this.tileID = tileID;
			setPreferredSize(TILE_PANEL_DIMENSION);
			assignTileCoulor();
			validate();
			//assignTilePieceIcon(chessBoard);
		}
		// adds the image of pieces onto the chess board
	
		/*
		 private void assignTilePieceIcon(final Board board) {
	            this.removeAll();
	            if(board.getTile(this.tileID).isTileOccupied()) {
	                try{
	                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath +
	                            board.getTile(this.tileID).getPiece().getPieceAlliance().toString().substring(0, 1) + "" +
	                            board.getTile(this.tileID).toString() +
	                            ".gif"));
	                    add(new JLabel(new ImageIcon(image)));
	                } catch(final IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
 */
		private void assignTileCoulor()
		{
			if(BoardUtils.FIRST_ROW[this.tileID] ||
			BoardUtils.THIRD_ROW[this.tileID] || 
			BoardUtils.FIFTH_ROW[this.tileID] ||
			BoardUtils.SEVENTH_ROW[this.tileID]){
				setBackground(this.tileID % 2 == 0 ? lightTileColor : darkTileColor);
			}else if (BoardUtils.SECOND_ROW[this.tileID] || 
					BoardUtils.FOURTH_ROW[this.tileID] ||
					BoardUtils.SIXTH_ROW[this.tileID] ||
					BoardUtils.EIGTH_ROW[this.tileID]) {
				setBackground(this.tileID % 2 != 0 ? lightTileColor : darkTileColor);
			}
			
		}
		
	}
	
}
