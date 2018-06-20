package eg.edu.guc.yugioh.board.player;

import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.*;
import eg.edu.guc.yugioh.cards.spells.SpellCard;

public class Field {
	private Phase phase;
	private ArrayList<MonsterCard>monstersArea = new ArrayList<MonsterCard>();
	private ArrayList<SpellCard>spellArea=new ArrayList<SpellCard>();
	private Deck deck;
	private ArrayList<Card> graveyard=new ArrayList<Card>();
	private ArrayList<Card> hand = new ArrayList<Card>();
	public Field() throws Exception{
		phase=Phase.MAIN1;
		deck=new Deck();
	}
	public boolean GameOver(){
		return Card.getBoard().isGameOver();
	}
	public void addMonsterToField(MonsterCard monster , Mode m ,boolean isHidden){
		if (!GameOver()){
		if (monstersArea.size()<5){
			monstersArea.add(monster);
			hand.remove(monster);
			monster.setMode(m);
			monster.setLocation(Location.FIELD);
			monster.setHidden(isHidden);
		}
	}
	}
	public void addMonsterToField(MonsterCard monster, Mode mode, ArrayList<MonsterCard>
	sacrifices){
		if (!GameOver()){
		if (monstersArea.size()<5 && 
				((sacrifices.size()==1 && (monster.getLevel()==5 || monster.getLevel()==6)) 
				|| (sacrifices.size()==2 && monster.getLevel()==7)
			|| (sacrifices.size()==2 && monster.getLevel()==8))){
			while(!sacrifices.isEmpty())
			{
				graveyard.add(sacrifices.get(sacrifices.size()-1));
				monstersArea.remove(sacrifices.get(sacrifices.size()-1));
				sacrifices.remove(sacrifices.size()-1);
				graveyard.get(graveyard.size()-1).setLocation(Location.GRAVEYARD);
		}
		monstersArea.add(monster);
		hand.remove(monster);
		monster.setMode(mode);
		if (monster.getMode()==Mode.DEFENSE)
			monster.setHidden(true);
		else
			monster.setHidden(false);
		monster.setLocation(Location.FIELD);
		}
	}
	}
	public void removeMonsterToGraveyard(MonsterCard monster){
		if (!GameOver() && monster.getLocation()==Location.FIELD){
		int j = monstersArea.indexOf(monster);
		graveyard.add(monstersArea.get(j));
		monstersArea.remove(j).setAttacked(false);
		graveyard.get(graveyard.size()-1).setLocation(Location.GRAVEYARD);
	}
	}
	public void removeMonsterToGraveyard(ArrayList<MonsterCard> monsters){
		if (!GameOver()){
		while(!monsters.isEmpty())
		{
			graveyard.add(monsters.get(monsters.size()-1));
			monstersArea.remove(monsters.size()-1).setAttacked(false);
			monsters.remove(monsters.size()-1);
			graveyard.get(graveyard.size()-1).setLocation(Location.GRAVEYARD);
	}
		}
	}
	public void addCardToHand(){
		if (!GameOver()){
			Card c=deck.drawOneCard();
	    	c.setLocation(Location.HAND);
			hand.add(c);
		}
	}
	public void addNCardsToHand(int n){
		if (!GameOver()){
			while(n>0){
		addCardToHand();
		n--;
		}	
		}
	}
public void addSpellToField(SpellCard action,MonsterCard monster,boolean Hidden){
	if (!GameOver()){
	if (!Hidden && spellArea.size()<5){
		addSpellToField(action,monster,true);
		activateSetSpell(action,monster);
	}else{if (spellArea.size()<5){
		spellArea.add(action);
		hand.remove(action);
		action.setLocation(Location.FIELD);
	}}
	}
	}
public void activateSetSpell(SpellCard action, MonsterCard monster){
	if (!GameOver()){
	if (spellArea.contains(action)){
	action.setHidden(false);
	action.action(monster);
	removeSpellToGraveyard(action);
	action.setLocation(Location.GRAVEYARD);
	}
	}
}
public void removeSpellToGraveyard(SpellCard spell){
	if (!GameOver() && spell.getLocation()==Location.FIELD){
		graveyard.add(spell);
		spellArea.remove(spell);
		spell.setHidden(false);
		spell.setLocation(Location.GRAVEYARD);
}
	}
public void removeSpellToGraveyard(ArrayList <SpellCard> spells){
	if (!GameOver()){
		while(!spells.isEmpty()){
		graveyard.add(spells.get(0));
		spellArea.remove(spells.get((0)));
		spells.remove(0).setLocation(Location.GRAVEYARD);
	}	
	}
}

public Phase getPhase() {
	return phase;
}


public void setPhase(Phase phase) {
	this.phase = phase;
}


public ArrayList<MonsterCard> getMonstersArea() {
	return monstersArea;
}


public ArrayList<SpellCard> getSpellArea() {
	return spellArea;
}


public Deck getDeck() {
	return deck;
}


public ArrayList<Card> getGraveyard() {
	return graveyard;
}


public ArrayList<Card> getHand() {
	return hand;
}

}