package eg.edu.guc.yugioh.cards.spells;
import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.cards.*;

import java.util.ArrayList;
public class MonsterReborn extends SpellCard{
	public MonsterReborn(String name, String description){
		super(name,description);
	}
	public void action(MonsterCard monster){
		Board b=Card.getBoard();
		boolean f=false;boolean m=false;
		ArrayList <Card> agr=b.getActivePlayer().getField().getGraveyard();
		ArrayList <Card> ogr=b.getOpponentPlayer().getField().getGraveyard();
		MonsterCard max=new MonsterCard("Balabizo", "meh", 0, 0, 0);
		MonsterCard t;
		for(int i=0;i<agr.size();i++){
			if (agr.get(i) instanceof MonsterCard){
				t=(MonsterCard) agr.get(i);
				if (t.getAttackPoints() > max.getAttackPoints()){
					max = t;
					m=true;
				}
			}
		}
		for(int i=0;i<ogr.size();i++){
			if (ogr.get(i) instanceof MonsterCard){
				t=(MonsterCard) ogr.get(i);
					if (max.getAttackPoints()<t.getAttackPoints()){
						max=t;
						f=true;
						m=true;
					}
			}
		}
		if (m){
			Card.getBoard().getActivePlayer().getField().getMonstersArea().add(max);
			if (f)
				ogr.remove(max);
			else
				agr.remove(max);
			Card.getBoard().getActivePlayer().Reborn=max;
			max.setLocation(Location.FIELD);
			max.setAttacked(false);
		}
	}
}
