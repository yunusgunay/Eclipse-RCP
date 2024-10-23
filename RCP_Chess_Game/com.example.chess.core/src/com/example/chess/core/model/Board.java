package com.example.chess.core.model;

import com.example.chess.core.model.piece.Piece;

public class Board {
	public static final int LENGTH = 8;
	
	private Square[][] squares;
	private Army whiteArmy;
	private Army blackArmy;

	public Board(Army whites, Army blacks) {
		whiteArmy = whites;
		blackArmy = blacks;
		squares = new Square[LENGTH][LENGTH];
		
		for(int row = 0; row < LENGTH; ++row) {
			for(int col = 0; col < LENGTH; ++col) {
				squares[row][col] = new Square(this, row, col);
			}
		}
	}
	
	public Army getArmy(Side side) {
		return side == Side.WHITE ? whiteArmy : blackArmy;
	}

	public Square getSquare(int row, int col) {
		return !(row < 0 || row >= LENGTH || col < 0 || col >= LENGTH) ? squares[row][col]: null;
	}
	
	public int computeRating(Side side) {
		int whiteScore = 0;
		int blackScore = 0;
		
		for(Piece p : whiteArmy.getAlivePieces()) {
			whiteScore += p.getScore();
		}
		
		for(Piece p : blackArmy.getAlivePieces()) {
			blackScore += p.getScore();
		}
		
		return side == Side.WHITE ? (whiteScore - blackScore) : (blackScore - whiteScore);
	} 

}
