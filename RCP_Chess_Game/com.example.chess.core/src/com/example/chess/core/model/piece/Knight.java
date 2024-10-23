package com.example.chess.core.model.piece;

import java.util.ArrayList;
import java.util.List;

import com.example.chess.core.icon.IconHandler;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;

public class Knight extends Piece {
	private static final int KNIGHT_SCORE = 3;
	
	public Knight(Square square, Side side) {
		super(square, side, KNIGHT_SCORE, IconHandler.getIcon("Knight", side));	
	}

	@Override
	public List<Square> computeLegalMoves() {
		List<Square> knightMoves = new ArrayList<Square>();
		
		Square targetSquare = square.getAdjacentSquare(1, 2);
		checkSquare(knightMoves, targetSquare);
		targetSquare = square.getAdjacentSquare(-1, 2);
		checkSquare(knightMoves, targetSquare);
		targetSquare = square.getAdjacentSquare(1, -2);
		checkSquare(knightMoves, targetSquare);
		targetSquare = square.getAdjacentSquare(-1, -2);
		checkSquare(knightMoves, targetSquare);
		targetSquare = square.getAdjacentSquare(2, 1);
		checkSquare(knightMoves, targetSquare);
		targetSquare = square.getAdjacentSquare(-2, 1);
		checkSquare(knightMoves, targetSquare);
		targetSquare = square.getAdjacentSquare(2, -1);
		checkSquare(knightMoves, targetSquare);
		targetSquare = square.getAdjacentSquare(-2, -1);
		checkSquare(knightMoves, targetSquare);

		return knightMoves;
	}

} 
