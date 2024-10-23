package com.example.chess.player;

import java.util.List;

import com.example.chess.core.model.Board;
import com.example.chess.core.model.ChessMove;
import com.example.chess.core.model.Side;

public class AlphaBetaPlayer extends ChessPlayer {
	private static final int INITIAL_DEPTH = 5;
	private static final int TIME_OUT = 2000; // miliseconds 
	
	private int currentDepth;
	private ChessMove bestMove;
	private ChessMove globalBestMove;
	private long start; 
	private boolean timeOut;

	public AlphaBetaPlayer(Board board, Side side) {
		super(board, side);
	}
	
	@Override 
	public ChessMove decideMove() {
		timeOut = false;
		start = System.currentTimeMillis();
		for(int d = 0; ; ++d) {
			if(d > 0) {
				globalBestMove = bestMove; 
				System.out.println("Completed search with depth " + currentDepth + ".");
			} 
			
			currentDepth = INITIAL_DEPTH + d;
			maximizer(currentDepth, Integer.MIN_VALUE, Integer.MAX_VALUE); 
			
			if(timeOut) {
				return globalBestMove; 
			}
		}
		
	}
	
	// Alpha-Beta Pruning Algorithm
	private int maximizer(int depth, int alpha, int beta) {
		if(System.currentTimeMillis() - start > TIME_OUT) {
			timeOut = true;
			return alpha; 
		}
		
		if(depth == 0) {
			return board.computeRating(Side.BLACK);
		}

		List<ChessMove> legalMoves = computeAllLegalMoves(); 
		for(ChessMove move : legalMoves) {
			makeMove(move);
			side = side.opposite();
			int rating = minimizer(depth - 1, alpha, beta); 
			side = side.opposite();
			undoMove(move);
			
			if(rating > alpha) {
				alpha = rating;			
				if(depth == currentDepth) {
					bestMove = move;
				}
			}
			
			if(alpha >= beta) {
				return alpha;
			}
		}
		 
		return alpha; 
	}
	
	private int minimizer(int depth, int alpha, int beta) {
		if(depth == 0) {
			return board.computeRating(Side.BLACK);
		}

		List<ChessMove> legalMoves = computeAllLegalMoves(); 
		for(ChessMove move : legalMoves) {
			makeMove(move);
			side = side.opposite();
			int rating = maximizer(depth - 1, alpha, beta); 
			side = side.opposite();
			undoMove(move);
			
			if(rating <= beta) {
				beta = rating;
			}
			
			if(alpha >= beta) {
				return beta;
			}
		}
		
		return beta;
	}

}
