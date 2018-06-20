package eg.edu.guc.yugioh.cards.spells;
import java.util.ArrayList;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.cards.*;

public class Raigeki extends SpellCard{
	public Raigeki(String name, String description){
		super(name,description);
	}
	public void action(MonsterCard monster){
		actionHelper(false);
	}
	public void actionHelper(boolean f){
		Board b=Card.getBoard();
		ArrayList <MonsterCard> ma=b.getOpponentPlayer().getField().getMonstersArea();
		ArrayList <Card> gr=b.getOpponentPlayer().getField().getGraveyard();
		ArrayList <MonsterCard> ama=b.getActivePlayer().getField().getMonstersArea();
		ArrayList <Card> agr=b.getActivePlayer().getField().getGraveyard();
		while(!ma.isEmpty()){
			gr.add(ma.remove(ma.size()-1));
			gr.get(gr.size()-1).setLocation(Location.GRAVEYARD);
		}
		while(f && !ama.isEmpty()){
			agr.add(ama.remove(ama.size()-1));
			agr.get(agr.size()-1).setLocation(Location.GRAVEYARD);
		}
	}
	
}
