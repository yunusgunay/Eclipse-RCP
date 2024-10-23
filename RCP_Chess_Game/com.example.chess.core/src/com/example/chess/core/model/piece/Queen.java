package com.example.chess.core.model.piece;

import java.util.ArrayList;
import java.util.List;

import com.example.chess.core.icon.IconHandler;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;

public class Queen extends Piece {
	private static final int QUEEN_SCORE = 9;
	
	public Queen(Square square, Side side) {
		super(square, side, QUEEN_SCORE, IconHandler.getIcon("Queen", side));	
	}

	@Override
	public List<Square> computeLegalMoves() {
		List<Square> queenMoves = new ArrayList<Square>();
		
		for(int h = -1; h <= 1; ++h) {
			for(int v = -1; v <= 1; ++v) {
				Square targetSquare = square.getAdjacentSquare(h, v);
				checkSquare(queenMoves, targetSquare);
			}
		}		
		return queenMoves; 
	}

}
