package com.example.chess.app.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.example.chess.app.parts.ChessBoardPart;
import com.example.chess.app.parts.PartRefresher;
import com.example.chess.core.model.Board;
import com.example.chess.core.model.ChessMove;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;
import com.example.chess.core.model.piece.Piece;
import com.example.chess.player.ChessPlayer;

public class ChessMoveListener implements MouseListener {
	private final Label label;
	private static Side side;
	private static Piece selectedPiece;
	private static boolean doubleClicked;
	private static ChessPlayer whitePlayer, blackPlayer;
	private static Board board;
	
	public ChessMoveListener(Label label) {
		this.label = label;
		side = Side.WHITE;
		whitePlayer = ChessBoardPart.getChessRoom().getPlayer(Side.WHITE);
		blackPlayer = ChessBoardPart.getChessRoom().getPlayer(Side.BLACK);
		board = ChessBoardPart.getChessRoom().getBoard();
	} 

	@Override
	public void mouseDoubleClick(MouseEvent e) { // double click
		resetLegalSquares();
		
		Piece piece = ((Square) (label.getData())).getPiece();
		if(piece != null && piece.getSide() == Side.WHITE) {
			for(Square square : piece.computeLegalMoves()) {
				square.setLegal(true);
			}
			
			selectedPiece = piece;
			doubleClicked = true;
		}
	}

	@Override
	public void mouseDown(MouseEvent e) { // single click	
		if(doubleClicked || side == Side.WHITE) {
			Square targetSquare = (Square) label.getData();
			if(targetSquare.isLegal()) {
				Square initialSquare = selectedPiece.getSquare();
				Piece targetPiece = targetSquare.getPiece();
				ChessMove move = new ChessMove(initialSquare, targetSquare, targetPiece);
				
				whitePlayer.makeMove(move);
				side = side.opposite(); 
			}
			
			doubleClicked = false;
			resetLegalSquares();
		}
		
		else if(side == Side.BLACK) {
			ChessMove move = blackPlayer.decideMove(); 
			blackPlayer.makeMove(move); 
			side = side.opposite();
			System.out.println("White rating: " + board.computeRating(Side.WHITE)); 
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {	
		PartRefresher.refresh();
		
		if (ChessBoardPart.getChessRoom().isGameOver()) {
            endGame();
        } 
	}
	
	private void resetLegalSquares() {
		for(int r = 0; r < Board.LENGTH; ++r) {
			for(int c = 0; c < Board.LENGTH; ++c) {
				board.getSquare(r, c).setLegal(false);
			}
		}
	}
	
	private void endGame() {
		Display.getDefault().asyncExec(() -> {
			Shell shell = Display.getDefault().getActiveShell();
			if(shell == null) {
				shell = new Shell(Display.getDefault());
			}
			
			MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_INFORMATION | SWT.OK);
			messageBox.setText("The End");
			messageBox.setMessage("GAME OVER! " + ((board.computeRating(Side.WHITE) > board.computeRating(Side.BLACK)) ? "YOU WON." : "YOU LOST."));
            messageBox.open(); 
		});
	}

}
