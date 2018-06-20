package eg.edu.guc.yugioh.board.player;

import java.util.ArrayList;

import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.Mode;
import eg.edu.guc.yugioh.cards.MonsterCard;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.exceptions.DefenseMonsterAttackException;
import eg.edu.guc.yugioh.exceptions.MonsterMultipleAttackException;
import eg.edu.guc.yugioh.exceptions.MultipleMonsterAdditionException;
import eg.edu.guc.yugioh.exceptions.NoMonsterSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpaceException;
import eg.edu.guc.yugioh.exceptions.NoSpellSpaceException;
import eg.edu.guc.yugioh.exceptions.WrongPhaseException;

public class Player implements Duelist {
	private String name;
	public MonsterCard Reborn;
	private int lifePoints;
	private Field field;
	private boolean summoned;

	public int getLifePoints() {
		return lifePoints;
	}

	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public Player(String name) throws Exception {
		this.name = name;
		lifePoints = 8000;
		field = new Field();
		summoned = false;
	}

	public boolean isActive() {
		return Card.getBoard().getActivePlayer() == this;
	}

	public boolean inHand(MonsterCard m) {
		return this.getField().getHand().contains(m)
				&& m.getLocation() == Location.HAND;
	}

	public boolean inHand(SpellCard s) {
		return this.getField().getHand().contains(s)
				&& s.getLocation() == Location.HAND;
	}

	public boolean inGrave(MonsterCard m) {
		return this.getField().getGraveyard().contains(m)
				&& m.getLocation() == Location.GRAVEYARD;
	}

	public boolean inGrave(SpellCard s) {
		return this.getField().getGraveyard().contains(s)
				&& s.getLocation() == Location.GRAVEYARD;
	}

	public boolean inArea(MonsterCard m) {
		return this.getField().getMonstersArea().contains(m)
				&& m.getLocation() == Location.FIELD;
	}

	public boolean inArea(SpellCard s) {
		return this.getField().getSpellArea().contains(s)
				&& s.getLocation() == Location.FIELD;
	}

	public boolean isBattlePhase() {
		return this.getField().getPhase() == Phase.BATTLE;
	}

	public boolean isMainPhase() {
		return this.getField().getPhase() == Phase.MAIN1
				|| this.getField().getPhase() == Phase.MAIN2;
	}

	public boolean summonMonster(MonsterCard monster)
			throws MultipleMonsterAdditionException, NoMonsterSpaceException,WrongPhaseException {
		if ((!summoned) && isActive() && inHand(monster)){
			if(isMainPhase()){
				if (this.getField().getMonstersArea().size() < 5) {
					this.getField().addMonsterToField(monster, Mode.ATTACK, false);
					summoned = true;
					return true;
				} else {
					if (this.getField().getMonstersArea().size() == 5) {
						throw new NoMonsterSpaceException(
								"Your Monster Zone Is Full");
					}
				}
			}else{
				throw new WrongPhaseException("You can only Summon In The Main Phase!");
			}
			

		}
		if (summoned) {
			throw new MultipleMonsterAdditionException(
					"You Can Only Summon One Monster Per Turn");
		}
		return false;

	}

	public boolean summonMonster(MonsterCard monster,
			ArrayList<MonsterCard> sacrifices) throws WrongPhaseException,
			MultipleMonsterAdditionException,NoMonsterSpaceException {
		//System.out.println("asdasd1");
		//System.out.println(summoned);
		//System.out.println(isActive());
		//System.out.println(inHand(monster));
		//System.out.println(monster.getName());
		//System.out.println(monster.getLocation());
		if ((!summoned) && isActive() && inHand(monster)) {
			//System.out.println("asdasd2");
			if (isMainPhase()){
				//System.out.println("asdasd3");
				this.getField().addMonsterToField(monster, Mode.ATTACK,
						sacrifices);
				//System.out.println("asdasd4");
				if (sacrifices.isEmpty()) {
					//System.out.println("asdasd5");
					summoned = true;
					return true;
				}
			}else{
				throw new WrongPhaseException("you cannot summon a monster in battle phase");
			}
			}
		//if((!summoned) && isActive() && inHand(monster)&&isBattlePhase()&&this.getField().getMonstersArea().size()<5){
			//throw new WrongPhaseException("you cannot summon a monster in battle phase");
		//}
		if (summoned) {
			throw new MultipleMonsterAdditionException(
					"You Can Only Summon One Monster Per Turn");
		}
		if((!summoned) && isActive() && inHand(monster)&&isMainPhase()&&this.getField().getMonstersArea().size() == 5){
			throw new NoMonsterSpaceException("your monster zone is full");
		}
		return false;
	}

	public boolean setMonster(MonsterCard monster) throws WrongPhaseException,
			MultipleMonsterAdditionException , NoMonsterSpaceException{
		if ((!summoned) && isActive() && inHand(monster)
				&& this.getField().getMonstersArea().size() < 5) {
			if (isMainPhase()){
				this.getField().addMonsterToField(monster, Mode.DEFENSE, true);
				summoned = true;
				return true;
			} else {
				throw new WrongPhaseException(
						"You can only set a monster in main phases");
			}

		}
		if (summoned) {
			throw new MultipleMonsterAdditionException(
					"You can only set one monster per turn");
		}
		if((!summoned) && isActive() && inHand(monster)
				&& this.getField().getMonstersArea().size() == 5 && isMainPhase()){
			throw new NoMonsterSpaceException("Your monster area is full!");
		}
		return false;
	}

	public boolean setMonster(MonsterCard monster,
			ArrayList<MonsterCard> sacrifices) throws WrongPhaseException , MultipleMonsterAdditionException {
		if ((!summoned) && isActive() && inHand(monster)
				&& this.getField().getMonstersArea().size() < 5) {
			if (isMainPhase()) {
				this.getField().addMonsterToField(monster, Mode.DEFENSE,
						sacrifices);
				summoned = true;
				return true;
			} else {
				throw new WrongPhaseException(
						"You can set a monster during main phases");
			}

		}
		if(summoned){
			throw new MultipleMonsterAdditionException("Nope");
		}
		if((!summoned) && isActive() && inHand(monster)
				&& this.getField().getMonstersArea().size()==5 && isMainPhase()){
			throw new NoMonsterSpaceException("you cannot set a new monster");
		}
		return false;
	}

	public boolean setSpell(SpellCard spell) throws NoSpaceException,
			NoSpellSpaceException, WrongPhaseException {
		if (isActive() && inHand(spell) && isMainPhase()
				&& this.getField().getSpellArea().size() == 5) {
			throw new NoSpellSpaceException(
					"Your Spell Zone is full , you cannot set a spell card");
		}
		if (isActive() && inHand(spell)
				&& this.getField().getSpellArea().size() < 5) {
			if (isMainPhase()) {
				this.getField().addSpellToField(spell, null, true);
				return true;
			} else {
				if (!isMainPhase()) {
					throw new WrongPhaseException(
							"You can only set a spell during main phases");
				}
			}

		}
		return false;

	}

	public boolean activateSpell(SpellCard spell, MonsterCard monster)
			throws WrongPhaseException, NoSpaceException {
		if (isActive()) {
		//	if (this.getField().getSpellArea().size() == 5) {
		//		throw new NoSpaceException("no slots to place your card");
		//	} else {
				if (inArea(spell)) {
					if (isMainPhase()) {
						this.getField().activateSetSpell(spell, monster);
						return true;
					} else {
						throw new WrongPhaseException(
								"You can only activate a spell during main phases");
					}

				} else {
					if (inHand(spell)) {
						if (isMainPhase()) {
							if (this.setSpell(spell)) {
								field.activateSetSpell(spell, monster);
								return true;
							}
						} else {
							throw new WrongPhaseException("You cannot activate a spell during Battle Phase!");
						}

					}

				}
	//		}

		}
		return false;
	}

	public boolean declareAttack(MonsterCard activeMonster,
			MonsterCard opponentMonster) throws DefenseMonsterAttackException,
			WrongPhaseException, MonsterMultipleAttackException {
		if ((inArea(activeMonster) && (!Card.getBoard().isGameOver())
				&& isActive() && activeMonster.getMode() == Mode.ATTACK && Card
				.getBoard().getOpponentPlayer().inArea(opponentMonster))) {
			if (!activeMonster.isAttacked()) {
				if (isBattlePhase()) {
					activeMonster.action(opponentMonster);
					activeMonster.setAttacked(true);
					return true;
				} else {
					throw new WrongPhaseException("You must be in battle phase to attack!");
				}
			} 

		}
		if ((!activeMonster.isAttacked()) && isBattlePhase()
				&& inArea(activeMonster) && (!Card.getBoard().isGameOver())
				&& isActive() && activeMonster.getMode() == Mode.DEFENSE
				&& Card.getBoard().getOpponentPlayer().inArea(opponentMonster)) {
			throw new DefenseMonsterAttackException(
					"You Cannot Attack With a Monster in Defence Mode");
		}
		if (activeMonster.isAttacked()){
			throw new MonsterMultipleAttackException(
					"you can attack only once per turn");
			
		}
		return false;

	}

	public boolean declareAttack(MonsterCard activeMonster)
			throws DefenseMonsterAttackException, WrongPhaseException,
			MonsterMultipleAttackException {
		if(!activeMonster.isAttacked()){
			if (Card.getBoard().getOpponentPlayer().getField().getMonstersArea()
					.size() == 0
					&& inArea(activeMonster)
					&& !field.GameOver()
					&& isActive()
					&& activeMonster.getMode() == Mode.ATTACK){
					//System.out.println("Batee5");
					if (isBattlePhase()){
						//System.out.println("Batee51");
						activeMonster.action();
						activeMonster.setAttacked(true);
						return true;
					}else{
						//System.out.println("Batee52");
						throw new WrongPhaseException(
								"You cannot Attack outside Battle Phase");
					}
			}
		}else if (activeMonster.isAttacked()){
			throw new MonsterMultipleAttackException("you can only attack once per turn");
		}
		
		if ((!activeMonster.isAttacked())
				&& isBattlePhase()
				&& Card.getBoard().getOpponentPlayer().getField()
						.getMonstersArea().size() == 0 && inArea(activeMonster)
				&& !field.GameOver() && isActive()
				&& activeMonster.getMode() == Mode.DEFENSE) {
			throw new DefenseMonsterAttackException(
					"You Cannot Attack Directly With a Monster in Def mode");
		}
		return false;
	}

	public void addCardToHand() {
		if (isActive()) {
			if (field.getDeck().getDeck().size() > 0)
				field.addCardToHand();
			else {
				Card.getBoard().setGameOver(true);
				Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
			}
		}
	}

	public void addNCardsToHand(int n) {
		if (isActive()) {
			if (field.getDeck().getDeck().size() >= n)
				field.addNCardsToHand(n);
			else {
				field.addNCardsToHand(field.getDeck().getDeck().size());
				Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
				Card.getBoard().setGameOver(true);
			}
		}
	}

	@Override
	public void endPhase() {
		if (!field.GameOver() && isActive()) {
			switch (field.getPhase()) {
			case MAIN1:
				field.setPhase(Phase.BATTLE);
				break;
			case BATTLE:
				field.setPhase(Phase.MAIN2);
				break;
			case MAIN2:
				endTurn();
			}
		}
	}

	public boolean endTurn() {
		if ((!field.GameOver()) && isActive()) {
				summoned = false;
				int j = field.getMonstersArea().size();
				for (int i = 0; i < j; i++) {
					field.getMonstersArea().get(i).setSwitched(false);
					field.getMonstersArea().get(i).setAttacked(false);

				}
				Card.getBoard().nextPlayer();
				return true;
			}
			
		return false;
	}

	public boolean switchMonsterMode(MonsterCard monster)
			throws WrongPhaseException {
		if (!monster.isSwitched() && isActive() && inArea(monster)) {
			if (isMainPhase()){
				switch (monster.getMode()) {
				case ATTACK:
					monster.setMode(Mode.DEFENSE);
					monster.setSwitched(true);
					return true;
				case DEFENSE:
					monster.setMode(Mode.ATTACK);
					if (monster.isHidden())
						monster.setHidden(false);
					monster.setSwitched(true);
					return true;
				}
			}else
				throw new WrongPhaseException("Battle positions switch outside battle phase");

		}
		return false;
	}

}
