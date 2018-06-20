package eg.edu.guc.yugioh.gui;

import eg.edu.guc.yugioh.cards.spells.SpellCard;

@SuppressWarnings("serial")
public class SpellButton extends GameButton{
	public SpellButton(){
		super();
	}
	
	public SpellButton(String name){
		super(name);
	}
	public SpellButton(SpellCard spell){
		super(spell);
	}
}
