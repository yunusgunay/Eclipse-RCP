package com.example.chess.core.model.piece;

import java.util.ArrayList;
import java.util.List;

import com.example.chess.core.icon.IconHandler;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;

public class Rook extends Piece {
	private static final int ROOK_SCORE = 5;
	
	public Rook(Square square, Side side) {
		super(square, side, ROOK_SCORE, IconHandler.getIcon("Rook", side));	
	}

	@Override
	public List<Square> computeLegalMoves() {
		List<Square> rookMoves = new ArrayList<Square>();
		 
		rookMoves.addAll(computeLinearMoves(1, 0));
		rookMoves.addAll(computeLinearMoves(-1, 0));
		rookMoves.addAll(computeLinearMoves(0, 1));
		rookMoves.addAll(computeLinearMoves(0, -1));
		
		return rookMoves;
	}

}
