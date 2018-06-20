	package eg.edu.guc.yugioh.board;

import eg.edu.guc.yugioh.board.player.*;
import eg.edu.guc.yugioh.cards.Card;
public class Board {
		private Player activePlayer;
		private Player opponentPlayer;
		private Player winner;
		public boolean GameOver;
	public boolean isGameOver() {
			return GameOver;
		}
		public void setGameOver(boolean gameOver) {
			GameOver = gameOver;
		}
	public Board(){
		Card.setBoard(this);
	}
	public Player getActivePlayer() {
		return activePlayer;
	}
	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}
	public Player getOpponentPlayer() {
		return opponentPlayer;
	}
	public void setOpponentPlayer(Player opponentPlayer) {
		this.opponentPlayer = opponentPlayer;
	}
	public Player getWinner() {
		return winner;
	}
	public void setWinner(Player winner) {
		this.winner = winner;
	}
	
	public void whoStarts(Player p1 , Player p2){
		double x=Math.random();
		if(x<0.5){
			activePlayer=p1;
			opponentPlayer=p2;
		}
		else{
			activePlayer=p2;
			opponentPlayer=p1;
		}
	}
	public void switchPlayer(){
		Player x=activePlayer;
		activePlayer=opponentPlayer;
		opponentPlayer=x;
	}
	public void nextPlayer(){
		activePlayer.getField().setPhase(Phase.MAIN1);
		Player x=activePlayer;
		activePlayer=opponentPlayer;
		opponentPlayer=x;
		activePlayer.addCardToHand();
	}
	public void startGame(Player p1 , Player p2){
		p1.getField().addNCardsToHand(5);
		p2.getField().addNCardsToHand(5);
		whoStarts(p1, p2);
		activePlayer.getField().addCardToHand();		
	}
}