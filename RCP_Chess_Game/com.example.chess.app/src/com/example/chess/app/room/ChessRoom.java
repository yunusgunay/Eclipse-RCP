package com.example.chess.app.room;

import java.util.HashMap;
import java.util.Map;

import com.example.chess.core.model.Army;
import com.example.chess.core.model.Board;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.piece.Bishop;
import com.example.chess.core.model.piece.BlackPawn;
import com.example.chess.core.model.piece.King;
import com.example.chess.core.model.piece.Knight;
import com.example.chess.core.model.piece.Queen;
import com.example.chess.core.model.piece.Rook;
import com.example.chess.core.model.piece.WhitePawn;
import com.example.chess.player.AlphaBetaPlayer;
import com.example.chess.player.ChessPlayer;
import com.example.chess.player.RandomPlayer;

public class ChessRoom {
	private final Board board;
	private final Map<Side, ChessPlayer> players;
	
	public ChessRoom() {
		Army whiteArmy = new Army();
		Army blackArmy = new Army();
		board = new Board(whiteArmy, blackArmy);
		
		// White Side
		for(int c = 0; c < Board.LENGTH; ++c) {
			whiteArmy.addPiece(new WhitePawn(board.getSquare(1, c), Side.WHITE));
		}		
		whiteArmy.addPiece(new Rook(board.getSquare(0, 0), Side.WHITE));
		whiteArmy.addPiece(new Rook(board.getSquare(0, 7), Side.WHITE));		
		whiteArmy.addPiece(new Knight(board.getSquare(0, 1), Side.WHITE));
		whiteArmy.addPiece(new Knight(board.getSquare(0, 6), Side.WHITE));		
		whiteArmy.addPiece(new Bishop(board.getSquare(0, 2), Side.WHITE));
		whiteArmy.addPiece(new Bishop(board.getSquare(0, 5), Side.WHITE));		
		whiteArmy.addPiece(new King(board.getSquare(0, 4), Side.WHITE));
		whiteArmy.addPiece(new Queen(board.getSquare(0, 3), Side.WHITE));
		
		
		// Black Side
		for(int c = 0; c < Board.LENGTH; ++c) {
			blackArmy.addPiece(new BlackPawn(board.getSquare(6, c), Side.BLACK));
		}		
		blackArmy.addPiece(new Rook(board.getSquare(7, 0), Side.BLACK));
		blackArmy.addPiece(new Rook(board.getSquare(7, 7), Side.BLACK));		
		blackArmy.addPiece(new Knight(board.getSquare(7, 1), Side.BLACK));
		blackArmy.addPiece(new Knight(board.getSquare(7, 6), Side.BLACK));		
		blackArmy.addPiece(new Bishop(board.getSquare(7, 2), Side.BLACK));
		blackArmy.addPiece(new Bishop(board.getSquare(7, 5), Side.BLACK));	
		blackArmy.addPiece(new King(board.getSquare(7, 4), Side.BLACK));
		blackArmy.addPiece(new Queen(board.getSquare(7, 3), Side.BLACK));	
		
		// Players 
		players = new HashMap<Side, ChessPlayer>();
		players.put(Side.WHITE, new ChessPlayer(this.board, Side.WHITE));
		// players.put(Side.BLACK, new RandomPlayer(this.board, Side.BLACK));
		players.put(Side.BLACK, new AlphaBetaPlayer(this.board, Side.BLACK));  
	}
	
	public Board getBoard() {
		return board;
	}
	
	public ChessPlayer getPlayer(Side side) {
		return players.get(side);
	}
	
	public boolean isGameOver() {
		boolean whiteDeadKing = board.getArmy(Side.WHITE).hasDeadKing();
		boolean blackDeadKing = board.getArmy(Side.BLACK).hasDeadKing(); 
		return (whiteDeadKing || blackDeadKing);  
	}
	
}
