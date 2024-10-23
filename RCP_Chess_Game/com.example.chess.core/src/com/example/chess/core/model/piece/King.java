package com.example.chess.core.model.piece;

import java.util.ArrayList;
import java.util.List;

import com.example.chess.core.icon.IconHandler;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;

public class King extends Piece {
	private static final int KING_SCORE = 99;
	
	public King(Square square, Side side) {
		super(square, side, KING_SCORE, IconHandler.getIcon("King", side));	
	}

	@Override
	public List<Square> computeLegalMoves() {
		List<Square> kingMoves = new ArrayList<Square>();
		
		kingMoves.addAll(computeLinearMoves(1, 0));
		kingMoves.addAll(computeLinearMoves(-1, 0));
		kingMoves.addAll(computeLinearMoves(0, 1));
		kingMoves.addAll(computeLinearMoves(0, -1));
		
		kingMoves.addAll(computeLinearMoves(1, 1));
		kingMoves.addAll(computeLinearMoves(-1, 1));
		kingMoves.addAll(computeLinearMoves(1, -1));
		kingMoves.addAll(computeLinearMoves(-1, -1));
		
		return kingMoves; 
	}

}
