package eg.edu.guc.yugioh.cards;

public class MonsterCard extends Card implements Cloneable{
	private int level;
	private int attackPoints;
	private int defensePoints;
	private Mode mode;
	private boolean attacked;
	private boolean switched;
	public MonsterCard(String name , String description , int level , int attack , int defense){
		super(name , description);
		this.level=level;
		this.attackPoints=attack;
		this.defensePoints=defense;
		this.mode=Mode.DEFENSE;
		attacked = false;
		switched = false;
		}
	public Object clone(){
		return new MonsterCard (this.getName(), this.getDescription(), this.level, this.attackPoints, this.defensePoints);
	}
	public void action(MonsterCard monster){
		if(monster.getMode()==Mode.DEFENSE){
			if (monster.isHidden()){
				monster.setHidden(false);
			if (this.getAttackPoints()<=monster.getDefensePoints()){
				Card.getBoard().getActivePlayer().setLifePoints(Card.getBoard().getActivePlayer().getLifePoints()-(monster.getDefensePoints()-this.getAttackPoints()));
			}else{
				Card.getBoard().getOpponentPlayer().getField().getMonstersArea().remove(monster);
				Card.getBoard().getOpponentPlayer().getField().getGraveyard().add(monster);
				monster.setLocation(Location.GRAVEYARD);
			}
			}else{
				if (this.getAttackPoints()<monster.getDefensePoints()){
					Card.getBoard().getActivePlayer().setLifePoints(Card.getBoard().getActivePlayer().getLifePoints()-(monster.getDefensePoints()-this.getAttackPoints()));
					//Card.getBoard().getActivePlayer().getField().getMonstersArea().remove(this);
					//Card.getBoard().getActivePlayer().getField().getGraveyard().add(this);
					//this.setLocation(Location.GRAVEYARD);
				}else{ if (this.getAttackPoints()>monster.getDefensePoints()){
					Card.getBoard().getOpponentPlayer().getField().getMonstersArea().remove(monster);
					Card.getBoard().getOpponentPlayer().getField().getGraveyard().add(monster);
					monster.setLocation(Location.GRAVEYARD);
				}else
				{	Card.getBoard().getOpponentPlayer().getField().getMonstersArea().remove(monster);
					Card.getBoard().getOpponentPlayer().getField().getGraveyard().add(monster);
					monster.setLocation(Location.GRAVEYARD);
					Card.getBoard().getActivePlayer().getField().getMonstersArea().remove(this);
					Card.getBoard().getActivePlayer().getField().getGraveyard().add(this);
					this.setLocation(Location.GRAVEYARD);
				}
		}}}else{
			if (this.getAttackPoints()>monster.getAttackPoints()){
				Card.getBoard().getOpponentPlayer().setLifePoints(Card.getBoard().getOpponentPlayer().getLifePoints()-(this.getAttackPoints()-monster.getAttackPoints()));
				Card.getBoard().getOpponentPlayer().getField().getMonstersArea().remove(monster);
				Card.getBoard().getOpponentPlayer().getField().getGraveyard().add(monster);
				monster.setLocation(Location.GRAVEYARD);
				
			}else{if (this.getAttackPoints()<monster.getAttackPoints()){
				Card.getBoard().getActivePlayer().setLifePoints(Card.getBoard().getActivePlayer().getLifePoints()-(monster.getAttackPoints()-this.getAttackPoints()));
				Card.getBoard().getActivePlayer().getField().getMonstersArea().remove(this);
				Card.getBoard().getActivePlayer().getField().getGraveyard().add(this);
				this.setLocation(Location.GRAVEYARD);
			}else{
				Card.getBoard().getOpponentPlayer().getField().getMonstersArea().remove(monster);
				Card.getBoard().getOpponentPlayer().getField().getGraveyard().add(monster);
				monster.setLocation(Location.GRAVEYARD);
				Card.getBoard().getActivePlayer().getField().getMonstersArea().remove(this);
				Card.getBoard().getActivePlayer().getField().getGraveyard().add(this);
				this.setLocation(Location.GRAVEYARD);
			}
		}
		}
		if (Card.getBoard().getActivePlayer().getLifePoints()<=0){
			Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
			Card.getBoard().setGameOver(true);
			System.out.println(Card.getBoard().getWinner().getName()+" Is The Winner!");
		}
		if (Card.getBoard().getOpponentPlayer().getLifePoints()<=0){
			Card.getBoard().setWinner(Card.getBoard().getActivePlayer());
			Card.getBoard().setGameOver(true);
			System.out.println(Card.getBoard().getWinner().getName()+" Is The Winner!");
		}
	}
	public void action(){
		Card.getBoard().getOpponentPlayer().setLifePoints(Card.getBoard().getOpponentPlayer().getLifePoints()-this.getAttackPoints());
		if (Card.getBoard().getOpponentPlayer().getLifePoints()<=0){
			Card.getBoard().setWinner(Card.getBoard().getActivePlayer());
			Card.getBoard().setGameOver(true);
			System.out.println(Card.getBoard().getWinner().getName()+" Is The Winner!");
		}
	}
	public boolean isAttacked() {
		return attacked;
	}
	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
	public boolean isSwitched() {
		return switched;
	}
	public void setSwitched(boolean switched) {
		this.switched = switched;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getAttackPoints() {
		return attackPoints;
	}
	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}
	public int getDefensePoints() {
		return defensePoints;
	}
	public void setDefensePoints(int defensePoints) {
		this.defensePoints = defensePoints;
	}
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	

}
