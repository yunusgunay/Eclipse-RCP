package com.example.chess.core.model;

public enum Side { // special class that represents a group of "constants"
	WHITE,
	BLACK;

	public Side opposite() {
		return this == WHITE ? BLACK : WHITE;
	}
}
