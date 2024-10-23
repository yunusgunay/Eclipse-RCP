package com.example.chess.core.model.piece;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;

public abstract class Piece {
	private final int score;
	protected Square square;
	protected final Side side; // White or Black
	protected final Image icon;
	
	public abstract List<Square> computeLegalMoves();
	
	protected Piece(Square square, Side side, int score, Image icon) { // only child classes can call the constructor
		this.square = square;
		this.square.setPiece(this);
		this.side = side;
		this.score = score;
		this.icon = icon;
	}

	public void setSquare(Square targetSquare) {
		square = targetSquare;	
	}

	public Square getSquare() {
		return square;
	}
	
	protected void checkSquare(List<Square> legalMoves, Square targetSquare) {
		if(targetSquare != null && (targetSquare.getPiece() == null || targetSquare.getPiece().side != this.side)) {
			legalMoves.add(targetSquare);
		}
	}
	
	protected List<Square> computeLinearMoves(int horizontal, int vertical) {
		List<Square> legalMoves = new ArrayList<Square>();
		Square targetSquare = square.getAdjacentSquare(horizontal, vertical);
		
		for(; targetSquare != null; targetSquare = targetSquare.getAdjacentSquare(horizontal, vertical)) {
			if(targetSquare.getPiece() == null ) {
				legalMoves.add(targetSquare);
			}
			
			else if(targetSquare.getPiece().side == this.side) {
				break;
			}
			
			else {
				legalMoves.add(targetSquare);
				break;
			}
		}
		
		return legalMoves;
	}

	public Image getIcon() {
		return icon;
	}

	public Side getSide() {
		return side;
	}

	public int getScore() {
		return score;
	} 

}
