package eg.edu.guc.yugioh.cards.spells;
import java.util.ArrayList;

import eg.edu.guc.yugioh.board.*;
import eg.edu.guc.yugioh.cards.*;

public class MagePower extends SpellCard{
	public MagePower(String name, String description){
		super(name,description);
	}
	public void action(MonsterCard monster){
		Board b=Card.getBoard();
		actionHelper(monster,(5*b.getActivePlayer().getField().getSpellArea().size()));
	}
	public void actionHelper(MonsterCard monster, int i){
		Board b=Card.getBoard();
		ArrayList <MonsterCard> ma=new ArrayList<MonsterCard>();
		if (monster==null){
		ma=b.getActivePlayer().getField().getMonstersArea();
		}else{
		ma.add(monster);}
		for (int t=0;t<ma.size();t++){
		ma.get(t).setAttackPoints(ma.get(t).getAttackPoints()+(100*i));
		ma.get(t).setDefensePoints(ma.get(t).getDefensePoints()+(100*i));	
		}	
	}
}
