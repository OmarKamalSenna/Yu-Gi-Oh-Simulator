package eg.edu.guc.yugioh.gui;

import eg.edu.guc.yugioh.cards.MonsterCard;

@SuppressWarnings("serial")
public class MonsterButton extends GameButton{
	public MonsterButton(){
		super();
	}
	public MonsterButton(String name){
		super(name);
	}
	public MonsterButton(MonsterCard monster){
		super(monster);
	}
}
