package eg.edu.guc.yugioh.cards.spells;
import java.util.ArrayList;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.MonsterCard;

public class ChangeOfHeart extends SpellCard{
	public ChangeOfHeart(String name, String description){
		super(name,description);
	}
	public void action(MonsterCard monster){
		Board b=Card.getBoard();
		ArrayList <MonsterCard> x =b.getActivePlayer().getField().getMonstersArea();
		ArrayList <MonsterCard> y =b.getOpponentPlayer().getField().getMonstersArea();
		y.remove(y.indexOf(monster));
		x.add(monster);
		monster.setAttacked(false);
	}
}
