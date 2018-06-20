package eg.edu.guc.yugioh.gui;

import javax.swing.JButton;

import eg.edu.guc.yugioh.cards.Card;

@SuppressWarnings("serial")
public class GameButton extends JButton{
	private Card card;
	public GameButton(){
		super();
	}
	public GameButton(String name){
		super(name);
	}
	public GameButton(Card card){
		setCard(card);
	}
	public Card getCard() {
		return card;
	}
	public void clearCard(){
		this.card=null;
		this.setText("Empty");
	}
	public void setCard(Card card) {
		this.card = card;
		this.setText(card.getName());
	}
}
