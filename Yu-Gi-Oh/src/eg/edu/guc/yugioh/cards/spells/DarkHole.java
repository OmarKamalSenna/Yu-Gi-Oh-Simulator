package eg.edu.guc.yugioh.cards.spells;
import eg.edu.guc.yugioh.cards.*;


public class DarkHole extends Raigeki{
	public DarkHole(String name, String description){
		super(name,description);
	}
	public void action(MonsterCard monster){
		actionHelper(true);
	}
}
