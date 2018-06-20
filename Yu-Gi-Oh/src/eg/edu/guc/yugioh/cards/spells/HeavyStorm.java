package eg.edu.guc.yugioh.cards.spells;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.cards.*;

public class HeavyStorm extends HarpieFeatherDuster{
	public HeavyStorm(String name, String description){
		super(name,description);
	}
	public void action(MonsterCard monster){
		Board b=Card.getBoard();
		actionHelper(b,b.getActivePlayer(),b.getOpponentPlayer(), true);
	}
}
