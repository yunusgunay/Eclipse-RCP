package com.example.chess.core.model.piece;

import java.util.ArrayList;
import java.util.List;

import com.example.chess.core.icon.IconHandler;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;

public class WhitePawn extends Piece {
	private static final int PAWN_SCORE = 1;
	
	public WhitePawn(Square square, Side side) {
		super(square, side, PAWN_SCORE, IconHandler.getIcon("Pawn", Side.WHITE));	
	}

	@Override
	public List<Square> computeLegalMoves() {
		List<Square> pawnMoves = new ArrayList<Square>();
		
		Square targetSquare = square.getAdjacentSquare(0, 1);		
		if(targetSquare != null && targetSquare.getPiece() == null) {
			pawnMoves.add(targetSquare);
			
			targetSquare = square.getAdjacentSquare(0, 2);			
			if(square.getRow() == 1 && targetSquare.getPiece() == null) {
				pawnMoves.add(targetSquare);
			}
		}
		
		targetSquare = square.getAdjacentSquare(1, 1);
		if(targetSquare != null && targetSquare.getPiece() != null && targetSquare.getPiece().side != side) {
			pawnMoves.add(targetSquare);
		}
		
		targetSquare = square.getAdjacentSquare(-1, 1);
		if(targetSquare != null && targetSquare.getPiece() != null && targetSquare.getPiece().side != side) {
			pawnMoves.add(targetSquare);
		}
		
		return pawnMoves;
	}

}
