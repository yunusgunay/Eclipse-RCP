package com.example.chess.core.model;

import java.util.ArrayList;
import java.util.List;

import com.example.chess.core.model.piece.King;
import com.example.chess.core.model.piece.Piece;
import com.example.chess.core.model.piece.Queen;

public class Army {
	private final List<Piece> alivePieces;
	private final List<Piece> deadPieces;
	
	public Army() {
		alivePieces = new ArrayList<Piece>();
		deadPieces = new ArrayList<Piece>();
	}
	
	public List<Piece> getAlivePieces() {
		return alivePieces;
	}

	public List<Piece> getDeadPieces() {
		return deadPieces;
	}
	
	public void addPiece(Piece alivePiece) {
		alivePieces.add(alivePiece);
	}

	public void buryPiece(Piece deadPiece) {
		alivePieces.remove(deadPiece);
		deadPieces.add(deadPiece);
	}

	public void revivePiece(Piece targetPiece) {
		deadPieces.remove(targetPiece); 
		alivePieces.add(targetPiece);		
	}

	public boolean hasDeadKing() {
		return deadPieces.stream().anyMatch(piece -> piece instanceof Queen);    
	}
	
}
