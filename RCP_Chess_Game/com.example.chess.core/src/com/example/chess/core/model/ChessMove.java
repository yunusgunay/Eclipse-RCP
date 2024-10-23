package com.example.chess.core.model;

import com.example.chess.core.model.piece.Piece;

public class ChessMove {
	private Square initial;
	private Square target;
	private Piece targetPiece;
	
	public ChessMove(Square initial, Square target, Piece targetPiece) {
		this.initial = initial;
		this.target = target;
		this.targetPiece = targetPiece;
	}

	public Square getInitialSquare() {
		return initial;
	}

	public void setInitialSquare(Square initial) {
		this.initial = initial;
	}

	public Square getTargetSquare() {
		return target;
	}

	public void setTargetSquare(Square target) {
		this.target = target;
	}

	public Piece getTargetPiece() {
		return targetPiece;
	}

	public void setTargetPiece(Piece targetPiece) {
		this.targetPiece = targetPiece;
	}

}
