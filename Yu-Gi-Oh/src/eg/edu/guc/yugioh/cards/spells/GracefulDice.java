package eg.edu.guc.yugioh.cards.spells;
import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.cards.*;

import java.util.ArrayList;

public class GracefulDice extends SpellCard{
	public GracefulDice(String name, String description){
		super(name,description);
	}
	public void action(MonsterCard monster){
		int i=(int) (10*Math.random())+1;
		actionHelper(null,i);
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
