package eg.edu.guc.yugioh.cards.spells;
import eg.edu.guc.yugioh.cards.*;

public class PotOfGreed extends SpellCard{
	public PotOfGreed(String name, String description){
		super(name,description);
	}
	public void action(MonsterCard monster){
		Card.getBoard().getActivePlayer().addNCardsToHand(2);
	}

}
