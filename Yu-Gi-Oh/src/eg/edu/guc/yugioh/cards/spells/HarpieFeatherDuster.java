package eg.edu.guc.yugioh.cards.spells;
import eg.edu.guc.yugioh.cards.*;
import eg.edu.guc.yugioh.board.*;
import eg.edu.guc.yugioh.board.player.*;

import java.util.ArrayList;

public class HarpieFeatherDuster extends SpellCard{
	public HarpieFeatherDuster(String name, String description){
		super(name,description);
	}
	public void action(MonsterCard monster){
		Board b=Card.getBoard();
		actionHelper(b, null, b.getOpponentPlayer(), false);
	}
	public void actionHelper(Board b, Player p1, Player p2, boolean f){
		ArrayList <SpellCard> sp2=p2.getField().getSpellArea();
		ArrayList <Card> gr2=p2.getField().getGraveyard();
		while(!sp2.isEmpty()){
			sp2.get(sp2.size()-1).setLocation(Location.GRAVEYARD);
			gr2.add(sp2.remove(sp2.size()-1));
		}if (f){
			ArrayList <SpellCard> sp1=p1.getField().getSpellArea();
			ArrayList <Card> gr1=p1.getField().getGraveyard();
			while(!sp1.isEmpty()){
			sp1.get(sp1.size()-1).setLocation(Location.GRAVEYARD);
			gr1.add(sp1.remove(sp1.size()-1));
			}
		}
	}
}
