package eg.edu.guc.yugioh.gui;

import eg.edu.guc.yugioh.cards.Card;

@SuppressWarnings("serial")
public class HandButton extends GameButton{
	public HandButton(){
		super();
	}
	public HandButton(Card card){
		setCard(card);
	}
}
