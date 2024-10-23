package com.example.chess.player;

import java.util.List;
import java.util.Random;

import com.example.chess.core.model.Board;
import com.example.chess.core.model.ChessMove;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;
import com.example.chess.core.model.piece.Piece;

public class RandomPlayer extends ChessPlayer {
	private Random random;

	public RandomPlayer(Board board, Side side) {
		super(board, side);
		random = new Random();
	}
	
	@Override
	public ChessMove decideMove() {
		List<Piece> pieces = board.getArmy(side).getAlivePieces();
		
		Piece randomPiece;
		List<Square> legalMoves;		
		do {
			randomPiece = pieces.get(random.nextInt(pieces.size()));		
			legalMoves = randomPiece.computeLegalMoves();
		} while (legalMoves.size() == 0);
		
		int randomMoveIndex = random.nextInt(legalMoves.size());
		Square targetSquare = legalMoves.get(randomMoveIndex);
		
		return new ChessMove(randomPiece.getSquare(), targetSquare, targetSquare.getPiece());
	}
	
}
