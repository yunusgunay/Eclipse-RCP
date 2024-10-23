package com.example.chess.app.parts;

public class PartRefresher {
	private static ChessBoardPart chessBoardPart;
	private static GraveyardPart whiteGraveyard, blackGraveyard;
	
	public static void refresh() {
		if(chessBoardPart == null || whiteGraveyard == null || blackGraveyard == null) {
			return;
		}
		chessBoardPart.setFocus();
		whiteGraveyard.setFocus();
		blackGraveyard.setFocus();
	} 
	
	public static void setChessBoardPart(ChessBoardPart chessBoardPart) {
		PartRefresher.chessBoardPart = chessBoardPart;
	}
	
	public static void setWhiteGraveyardPart(GraveyardPart whiteGraveyard) {
		PartRefresher.whiteGraveyard = whiteGraveyard;
	}
	
	public static void setBlackGraveyardPart(GraveyardPart blackGraveyard) {
		PartRefresher.blackGraveyard = blackGraveyard; 
	} 
	
}
