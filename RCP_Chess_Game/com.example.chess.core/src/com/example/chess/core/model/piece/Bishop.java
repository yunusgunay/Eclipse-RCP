package com.example.chess.core.model.piece;

import java.util.ArrayList;
import java.util.List;

import com.example.chess.core.icon.IconHandler;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;

public class Bishop extends Piece {
	private static final int BISHOP_SCORE = 3;
	
	public Bishop(Square square, Side side) {
		super(square, side, BISHOP_SCORE, IconHandler.getIcon("Bishop", side));	
	}

	@Override
	public List<Square> computeLegalMoves() {
		List<Square> bishopMoves = new ArrayList<Square>();
		
		bishopMoves.addAll(computeLinearMoves(1, 1));
		bishopMoves.addAll(computeLinearMoves(-1, 1));
		bishopMoves.addAll(computeLinearMoves(1, -1));
		bishopMoves.addAll(computeLinearMoves(-1, -1));

		return bishopMoves;
	}

}
