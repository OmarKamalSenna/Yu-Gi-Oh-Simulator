package eg.edu.guc.yugioh.listeners;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import eg.edu.guc.yugioh.board.Board;
import eg.edu.guc.yugioh.board.player.*;
import eg.edu.guc.yugioh.cards.*;
import eg.edu.guc.yugioh.cards.spells.ChangeOfHeart;
import eg.edu.guc.yugioh.cards.spells.MagePower;
import eg.edu.guc.yugioh.cards.spells.SpellCard;
import eg.edu.guc.yugioh.gui.GUI;
import eg.edu.guc.yugioh.gui.GameButton;
import eg.edu.guc.yugioh.gui.HandButton;
import eg.edu.guc.yugioh.gui.MonsterButton;
import eg.edu.guc.yugioh.gui.PLayout;
import eg.edu.guc.yugioh.gui.SpellButton;


@SuppressWarnings("unused")
public class GAME implements MouseListener {
	Board board;
	Activity activity = Activity.CHILLING;
	GUI GUI;
	PLayout ActivePlayer;
	SpellCard CurrentSpell;
	MonsterCard CurrentMonster;
	GameButton CurrentButton;
	boolean Waiting=false;
	boolean StillWaiting=false;
	int numSac = 0;
	GameButton FstClick;
	MonsterButton SndClick;
	MonsterButton TrdClick;
	MonsterCard NeedSac;
	MonsterCard FstSac;
	MonsterCard SndSac;
	Location CardLocation;
	GameButton MySpellButton;
	MonsterButton SpellTarget;
	
	public PLayout OpponentPlayer(){
		if (ActivePlayer==GUI.P1)
			return GUI.P2;
		else
			return GUI.P1;
	}
	
	
	public GAME() throws Exception{
		board = new Board();
		String p1 = JOptionPane.showInputDialog("Enter First Player Name");
		String p2 = JOptionPane.showInputDialog("Enter Second Player Name");
		board.startGame(new Player(p1), new Player(p2));
		GUI = new GUI();
		ActivePlayer = GUI.P1;
		GUI.P1.Name.setText(Card.getBoard().getActivePlayer().getName());
		GUI.P2.Name.setText(Card.getBoard().getOpponentPlayer().getName());
		GUI.P1.Deck.setText(""+Card.getBoard().getActivePlayer().getField().getDeck().getDeck().size()+" Cards Remaining");
		GUI.P2.Deck.setText(""+Card.getBoard().getOpponentPlayer().getField().getDeck().getDeck().size()+" Cards Remaining");
		for (int i=0;i<6;i++){
			GUI.P1.Hand.add(new HandButton(Card.getBoard().getActivePlayer().getField().getHand().get(i)));
			GUI.P1.Hand.get(i).addMouseListener(this);
			GUI.P1.JP3.add(GUI.P1.Hand.get(i));
		}
		for (int i=0;i<5;i++){
			GUI.P2.Hand.add(new HandButton(Card.getBoard().getOpponentPlayer().getField().getHand().get(i)));
			GUI.P2.Hand.get(i).addMouseListener(this);
			GUI.P2.JP3.add(GUI.P2.Hand.get(i));
		}
		GUI.P1.LP.setOpaque(true);
		GUI.P2.LP.setOpaque(true);
		GUI.P1.Name.setOpaque(true);
		GUI.P2.Name.setOpaque(true);
		GUI.P1.Name.setBackground(Color.GREEN);
		GUI.P1.LP.setBackground(Color.GREEN);
		GUI.P2.Name.setBackground(Color.RED);
		GUI.P2.LP.setBackground(Color.RED);
		GUI.nextPhase.setBackground(Color.ORANGE);
		GUI.endTurn.setBackground(Color.ORANGE);
		GUI.P1.JP1.setBackground(Color.GREEN);
		GUI.P1.JP2.setBackground(Color.GREEN);
		GUI.P1.JP3.setBackground(Color.GREEN);
		GUI.P2.JP1.setBackground(Color.RED);
		GUI.P2.JP2.setBackground(Color.RED);
		GUI.P2.JP3.setBackground(Color.RED);
		GUI.P2.HideHand();
		everybodyListen();
	}

	
	
	public void endTurn(){
		ActivePlayer.HideHand();
		ActivePlayer.JP1.setBackground(Color.RED);
		ActivePlayer.JP2.setBackground(Color.RED);
		ActivePlayer.JP3.setBackground(Color.RED);
		ActivePlayer.Name.setBackground(Color.RED);
		ActivePlayer.LP.setBackground(Color.RED);
		if (Card.getBoard().getActivePlayer().endTurn()){
			GUI.currentPhase.setText("Main Phase 1");
			if (ActivePlayer==GUI.P1)
				ActivePlayer=GUI.P2;
			else
				ActivePlayer=GUI.P1;

			ActivePlayer.Hand.add(new HandButton(Card.getBoard().getActivePlayer().getField().getHand().get(Card.getBoard().getActivePlayer().getField().getHand().size()-1)));
			ActivePlayer.Hand.get(ActivePlayer.Hand.size()-1).addMouseListener(this);
			ActivePlayer.JP3.add(ActivePlayer.Hand.get(ActivePlayer.Hand.size()-1));
			ActivePlayer.Deck.setText(""+Card.getBoard().getActivePlayer().getField().getDeck().getDeck().size()+" Cards Remaining");
			ActivePlayer.RevealHand();
		}
		ActivePlayer.JP1.setBackground(Color.GREEN);
		ActivePlayer.JP2.setBackground(Color.GREEN);
		ActivePlayer.JP3.setBackground(Color.GREEN);
		ActivePlayer.Name.setBackground(Color.GREEN);
		ActivePlayer.LP.setBackground(Color.GREEN);
		
	}
	
	
	public static void main(String [] args) throws Exception{
		GAME G = new GAME();
	}
	
	public void setMonster(MonsterCard monster){
		if(monster.getLevel()<5){
			//("should not go");
			if (Card.getBoard().getActivePlayer().setMonster(monster)){
				ActivePlayer.JP3.remove(ActivePlayer.Hand.get(ActivePlayer.Hand.indexOf(CurrentButton)));
				ActivePlayer.JP3.validate();
				ActivePlayer.Hand.remove(CurrentButton);
				for (int i=0;i<5;i++){
					if (ActivePlayer.MField.get(i).getCard()==null){
						ActivePlayer.MField.get(i).setCard(monster);
						ActivePlayer.MField.get(i).setText("Set Mob (DEF)");
						//(ActivePlayer.MField.get(i).getCard().getName());
						break;
					}
				}
			}
		}else{
			FstClick=CurrentButton;
			//(FstClick.getText());
			NeedSac=monster;
			activity=Activity.SETTING;
			//("What"+NeedSac.getLocation());
			GUI.nextPhase.setText("Cancel Sacrifices");
			if(monster.getLevel()==5||monster.getLevel()==6){
				Waiting=true;
				JOptionPane.showMessageDialog(null, "Please pick 1 sacrifice");
			}
			if(monster.getLevel()==7||monster.getLevel()==8){
				Waiting=true;
				StillWaiting=true;
				JOptionPane.showMessageDialog(null, "Please pick 2 sacrifices");
			}
		}
	}
	private void switchMode() {
		if (Card.getBoard().getActivePlayer().switchMonsterMode(CurrentMonster)){
			if (CurrentMonster.getMode()==Mode.ATTACK)
				CurrentButton.setText(CurrentMonster.getName()+ "  (ATK)");
			else
				CurrentButton.setText(CurrentMonster.getName()+ "  (DEF)");
			//(CurrentButton.getWidth());
			//(CurrentButton.getHeight());
		}
	}



	public void nextPhase(){
		if (Card.getBoard().getActivePlayer().getField().getPhase()==Phase.MAIN2){
			GUI.currentPhase.setText("Main Phase 1");
			endTurn();
		}else{
			Card.getBoard().getActivePlayer().endPhase();
			if (Card.getBoard().getActivePlayer().getField().getPhase()==Phase.BATTLE)
				GUI.currentPhase.setText("Battle Phase");
			if (Card.getBoard().getActivePlayer().getField().getPhase()==Phase.MAIN2)
				GUI.currentPhase.setText("Main Phase 2");
		}
	}
	
	
	public void tribute(MonsterCard monster, ArrayList <MonsterCard> Sac){
		if ((activity==Activity.SUMMONING && 
				Card.getBoard().getActivePlayer().summonMonster(monster, Sac))
						||
				(activity==Activity.SETTING &&
				Card.getBoard().getActivePlayer().setMonster(monster, Sac))){
			if(SndClick!=null){
				ActivePlayer.MField.remove(SndClick);
				ActivePlayer.JP1.remove(SndClick);
				MonsterButton mb = new MonsterButton("Monster Field");
				ActivePlayer.MField.add(mb);
				ActivePlayer.JP1.validate();
				if (ActivePlayer==GUI.P1)
					ActivePlayer.JP1.add(mb,FlowLayout.LEFT);
				else
					ActivePlayer.JP1.add(mb,FlowLayout.RIGHT);
				
			}
			if(TrdClick!=null){
				ActivePlayer.MField.remove(TrdClick);
				ActivePlayer.JP1.remove(TrdClick);
				MonsterButton mb = new MonsterButton("Monster Field");
				ActivePlayer.MField.add(mb);
				if (ActivePlayer==GUI.P1)
					ActivePlayer.JP1.add(mb,FlowLayout.LEFT);
				else
					ActivePlayer.JP1.add(mb,FlowLayout.RIGHT);
			}
				ActivePlayer.Graveyard.setText("Graveyard:  "+Card.getBoard().getActivePlayer().getField().getGraveyard().get(Card.getBoard().getActivePlayer().getField().getGraveyard().size()-1).getName());
				ActivePlayer.JP3.remove(CurrentButton);
				ActivePlayer.JP3.validate();
				for (int i=0;i<5;i++){
					//(ActivePlayer.MField.get(i).getCard());
					if (ActivePlayer.MField.get(i).getCard()==null){
						ActivePlayer.MField.get(i).setCard(monster);
						if (activity==Activity.SETTING)
							ActivePlayer.MField.get(i).setText("Set Mob (DEF)");
						else
							ActivePlayer.MField.get(i).setText(monster.getName() + " (ATK)");
						break;
					}
				}
		}
		resetEverything();
	}
	
	public void Attack(){
		if(Card.getBoard().getActivePlayer().declareAttack(NeedSac,FstSac)){
			if (NeedSac.getLocation()==Location.GRAVEYARD){
				ActivePlayer.MField.remove(FstClick);
				ActivePlayer.JP1.remove(FstClick);
				MonsterButton mb = new MonsterButton("Monster Field");
				ActivePlayer.MField.add(mb);
				if (ActivePlayer==GUI.P1)
					ActivePlayer.JP1.add(mb,FlowLayout.LEFT);
				else
					ActivePlayer.JP1.add(mb,FlowLayout.RIGHT);
			}
			if (FstSac.getLocation()==Location.GRAVEYARD){
				OpponentPlayer().MField.remove(SndClick);
				OpponentPlayer().JP1.remove(SndClick);
				MonsterButton mb = new MonsterButton("Monster Field");
				OpponentPlayer().MField.add(mb);
				if (OpponentPlayer()==GUI.P1)
					OpponentPlayer().JP1.add(mb,FlowLayout.LEFT);
				else
					OpponentPlayer().JP1.add(mb,FlowLayout.RIGHT);
			}
			ActivePlayer.LP.setText(""+Card.getBoard().getActivePlayer().getLifePoints());
			OpponentPlayer().LP.setText(""+Card.getBoard().getOpponentPlayer().getLifePoints());
		}
		if (!Card.getBoard().getActivePlayer().getField().getGraveyard().isEmpty())
			ActivePlayer.Graveyard.setText("Graveyard:  "+Card.getBoard().getActivePlayer().getField().getGraveyard().get(Card.getBoard().getActivePlayer().getField().getGraveyard().size()-1).getName());
		if (!Card.getBoard().getOpponentPlayer().getField().getGraveyard().isEmpty())
			OpponentPlayer().Graveyard.setText("Graveyard:  "+Card.getBoard().getOpponentPlayer().getField().getGraveyard().get(Card.getBoard().getOpponentPlayer().getField().getGraveyard().size()-1).getName());
		resetEverything();
	}
	
	public void summonMonster(MonsterCard monster){
		if(monster.getLevel()<5){
			if (Card.getBoard().getActivePlayer().summonMonster(monster)){
				ActivePlayer.JP3.remove(CurrentButton);
				ActivePlayer.JP3.validate();
				ActivePlayer.Hand.remove(CurrentButton);
				for (int i=0;i<5;i++){
					if (ActivePlayer.MField.get(i).getCard()==null){
						ActivePlayer.MField.get(i).setCard(monster);
						ActivePlayer.MField.get(i).setText(monster.getName()+ " (ATK)");
						break;
					}
				}
			}
		}else{
			FstClick=CurrentButton;
			//(FstClick.getText());
			NeedSac=monster;
			activity=Activity.SUMMONING;
			//("What"+NeedSac.getLocation());
			GUI.nextPhase.setText("Cancel Sacrifices");
			if(monster.getLevel()==5||monster.getLevel()==6){
				Waiting=true;
				JOptionPane.showMessageDialog(null, "This Monster Needs 1 Sacrifice , Please Select it");
			}
			if(monster.getLevel()==7||monster.getLevel()==8){
				Waiting=true;
				StillWaiting=true;
				JOptionPane.showMessageDialog(null, "This Monster Needs 2 Sacrifices , Please Select them");
			}
		}
	}
	
	public void setSpell(SpellCard spell){
		if (Card.getBoard().getActivePlayer().setSpell(spell)){
			ActivePlayer.JP3.remove(CurrentButton);
			ActivePlayer.JP3.validate();
			ActivePlayer.Hand.remove(CurrentButton);
			for (int i=0;i<5;i++){
				if (ActivePlayer.SField.get(i).getCard()==null){
					ActivePlayer.SField.get(i).setCard(spell);
					ActivePlayer.SField.get(i).setText("Set Spell");
					break;
				}
			}
		}
	}
	
	
	
	
	public void everybodyListen(){
		GUI.Attack.addMouseListener(this);
		GUI.ChangeMode.addMouseListener(this);
		GUI.endTurn.addMouseListener(this);
		GUI.nextPhase.addMouseListener(this);
		GUI.P1.LP.addMouseListener(this);
		GUI.P2.LP.addMouseListener(this);
        GUI.P1.Peak.addMouseListener(this);
        GUI.P2.Peak.addMouseListener(this);
		for (int i=0;i<5;i++){
			GUI.P1.MField.get(i).addMouseListener(this);
	//		GUI.P1.MField.get(i).setIcon(new ImageIcon(new ImageIcon("Baby Dragon.png").getImage().getScaledInstance(GUI.P1.MField.get(i).getWidth(), 227, java.awt.Image.SCALE_SMOOTH)));
	//		GUI.P1.SField.get(i).setIcon(new ImageIcon(new ImageIcon("Card Back.png").getImage().getScaledInstance(227, 100, java.awt.Image.SCALE_SMOOTH)));
			GUI.P1.SField.get(i).addMouseListener(this);
		}
		for (int i=0;i<5;i++){
			GUI.P2.MField.get(i).addMouseListener(this);
	//		GUI.P2.MField.get(i).setIcon(new ImageIcon(new ImageIcon("Card Back.png").getImage().getScaledInstance(227, 100, java.awt.Image.SCALE_SMOOTH)));
	//		GUI.P2.SField.get(i).setIcon(new ImageIcon(new ImageIcon("Card Back.png").getImage().getScaledInstance(227, 100, java.awt.Image.SCALE_SMOOTH)));
			GUI.P2.SField.get(i).addMouseListener(this);
		}
		GUI.Activate.addMouseListener(this);
		GUI.SSet.addMouseListener(this);
		GUI.MSet.addMouseListener(this);
		GUI.Summon.addMouseListener(this);
	}


	@Override
	public void mouseClicked(MouseEvent e) {

	}



	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() instanceof GameButton){
			GameButton x = (GameButton) e.getSource();
			if(x.getCard()!=null && x.getCard() instanceof MonsterCard){
				MonsterCard m = (MonsterCard) x.getCard();
				if (!(x.getText()=="Hidden") && !(m.getLocation()==Location.FIELD && m.isHidden()))
					GUI.currentPhase.setText("ATK:"+m.getAttackPoints()+ "    DEF:"+m.getDefensePoints() + "    LVL:"+m.getLevel());
			}else{
			if(x.getCard()!=null && x.getCard() instanceof SpellCard){
				SpellCard s = (SpellCard) x.getCard();
				if (x.getText()!="Hidden" && !(s.getLocation()==Location.FIELD && s.isHidden()))
					GUI.currentPhase.setText("This is a Spell Card!");
			}
			}
		}
	}



	@Override
	public void mouseExited(MouseEvent e) {
		if (Card.getBoard().getActivePlayer().getField().getPhase()==Phase.MAIN1){
            GUI.currentPhase.setText("Main Phase 1");
          }else{
            if(Card.getBoard().getActivePlayer().getField().getPhase()==Phase.MAIN2){
                        GUI.currentPhase.setText("Main Phase 2");
           } else{
                        GUI.currentPhase.setText("Battle Phase");
          }
}
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	public void resetEverything(){
		Waiting=false;
		StillWaiting=false;
		NeedSac=null;
		FstSac=null;
		SndSac=null;
		FstClick=null;
		SndClick=null;
		TrdClick=null;
		activity=Activity.CHILLING;
		CardLocation=null;
		SpellTarget=null;
		GUI.nextPhase.setText("Next Phase");
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		try{	
			if (Waiting){
				if (e.getSource() == GUI.nextPhase){
					resetEverything();
				}
				if (StillWaiting){
					if (e.getSource() instanceof MonsterButton && (activity==Activity.SUMMONING || activity==Activity.SETTING)){
						if (SndClick==null){
							SndClick = (MonsterButton) e.getSource();
							FstSac=(MonsterCard) SndClick.getCard();
						}else{
							ArrayList<MonsterCard> MyA=new ArrayList<MonsterCard>();
							TrdClick = (MonsterButton) e.getSource();
							SndSac=(MonsterCard) TrdClick.getCard();
							MyA.add(FstSac);
							MyA.add(SndSac);
							tribute(NeedSac,MyA);
						}
					}
				}else{
					if (e.getSource() instanceof MonsterButton && (activity==Activity.SUMMONING || activity==Activity.SETTING)){
							ArrayList<MonsterCard> MyA=new ArrayList<MonsterCard>();
							SndClick = (MonsterButton) e.getSource();
							if (SndClick.getCard()!=null){
								FstSac=(MonsterCard) SndClick.getCard();
								MyA.add(FstSac);
								tribute(NeedSac,MyA);
							}else
								SndClick = null;
						}
				}
				if (activity==Activity.ACTIVATING && e.getSource() instanceof MonsterButton){
					SndClick = (MonsterButton) e.getSource();
					if  (SndClick.getCard()!=null){
						SpellTarget = SndClick;
						SpellAction(CurrentSpell);
						resetEverything();
					}else
						SndClick=null;
				}
				
				if (e.getSource() instanceof MonsterButton && (activity==Activity.ATTACKING)){
					SndClick = (MonsterButton) e.getSource();
					FstSac = (MonsterCard) SndClick.getCard();
					Attack();
			}
			}else{
				if (e.getSource()==ActivePlayer.Peak){
					if (Card.getBoard().getActivePlayer().getField().getSpellArea().contains((SpellCard) CurrentButton.getCard())){
						JOptionPane.showMessageDialog(null,CurrentButton.getCard().getName()+": "+CurrentButton.getCard().getDescription());
					}
					
					}
				if (e.getSource() ==GUI.Activate){
					if (!(MySpellButton.getCard() instanceof MagePower
							||
							MySpellButton.getCard() instanceof ChangeOfHeart))
						SpellAction(CurrentSpell);
					else{
						GUI.nextPhase.setText("Cancel Spell");
						activity=Activity.ACTIVATING;
						Waiting=true;
						JOptionPane.showMessageDialog(null, "Please select a target!");
					}
				}
				if (e.getSource() == GUI.MSet)
					setMonster(CurrentMonster);
				if (e.getSource() == GUI.endTurn) 
					endTurn();
				if (e.getSource() == GUI.Attack){
					
				}
				if (e.getSource() == GUI.ChangeMode){
					switchMode();
				}
				if (e.getSource() instanceof MonsterButton){
					CurrentButton = (MonsterButton) e.getSource();
					if (CurrentButton.getCard()!=null && Card.getBoard().getActivePlayer().getField().getMonstersArea().contains((MonsterCard) CurrentButton.getCard())){
						CurrentMonster = (MonsterCard) CurrentButton.getCard();
						JPopupMenu J = new JPopupMenu();
						J.add(GUI.Attack);
						J.add(GUI.ChangeMode);
						J.show((Component) e.getSource(), ((Component) e.getSource()).getWidth()/2, ((Component) e.getSource()).getHeight()/2);
					}
				}
				if (e.getSource() instanceof SpellButton){
					CurrentButton = (GameButton) e.getSource();
					CurrentSpell = (SpellCard) CurrentButton.getCard();
					if (CurrentSpell!=null && Card.getBoard().getActivePlayer().getField().getSpellArea().contains((SpellCard) CurrentButton.getCard())){
						MySpellButton = CurrentButton;
						JPopupMenu J = new JPopupMenu();
						J.add(GUI.Activate);
						J.add(ActivePlayer.Peak);
						J.show((Component) e.getSource(), ((Component) e.getSource()).getWidth()/2, ((Component) e.getSource()).getHeight()/2);
					}
				}
				if (e.getSource() instanceof HandButton){
					CurrentButton= (GameButton) e.getSource();
						if (CurrentButton.getCard() instanceof SpellCard && Card.getBoard().getActivePlayer().getField().getHand().contains(CurrentButton.getCard())){
						CurrentSpell = (SpellCard) CurrentButton.getCard();
						MySpellButton = CurrentButton;
						JPopupMenu J = new JPopupMenu();
						J.add(GUI.Activate);
						J.add(GUI.SSet);
						J.show((Component) e.getSource(), ((Component) e.getSource()).getWidth()/2, ((Component) e.getSource()).getHeight()/2);
						}
						if (CurrentButton.getCard() instanceof MonsterCard && Card.getBoard().getActivePlayer().getField().getHand().contains(CurrentButton.getCard())){
						CurrentMonster = (MonsterCard) CurrentButton.getCard();
						JPopupMenu J = new JPopupMenu();
						J.add(GUI.Summon);
						J.add(GUI.MSet);
						J.show((Component) e.getSource(), ((Component) e.getSource()).getWidth()/2, ((Component) e.getSource()).getHeight()/2);
						}
				}
				
				if (e.getSource() == GUI.Summon)
						summonMonster(CurrentMonster);
				if (e.getSource() == GUI.nextPhase)
						nextPhase();
				if (e.getSource() == GUI.SSet)
					setSpell(CurrentSpell);
				if (e.getSource() == GUI.Attack){
					if (Card.getBoard().getOpponentPlayer().getField().getMonstersArea().isEmpty()){
						Card.getBoard().getActivePlayer().declareAttack((MonsterCard) CurrentButton.getCard());
						OpponentPlayer().LP.setText(""+Card.getBoard().getOpponentPlayer().getLifePoints());
						if (Card.getBoard().isGameOver())
							GameIsOver();
					}
					else{	
					Waiting=true;
					GUI.nextPhase.setText("Cancel Attack");
					FstClick = CurrentButton;
					NeedSac = (MonsterCard) CurrentButton.getCard();
					activity=Activity.ATTACKING;
					JOptionPane.showMessageDialog(null, "Please pick a target");
					}
				}
					
				}
		}
		catch(Exception error){
			JOptionPane.showMessageDialog(null, error.getMessage());
			resetEverything();
		}
		GameIsOver();
	}
	
	public void SpellAction(SpellCard spell){
		switch (spell.getName()){
		case ("Card Destruction"):
			CardDestruction(spell);
			break;
		case ("Change Of Heart"):
			CoH(spell);
			break;
		case ("Dark Hole"):
			DarkHole(spell);
			break;
		case ("Graceful Dice"):
			GracefulDice(spell);
			break;
		case ("Harpie's Feather Duster"):
			HFD(spell);
			break;
		case ("Heavy Storm"):
			HeavyStorm(spell);
			break;
		case ("Mage Power"):
			MagePower(spell);
			break;
		case ("Monster Reborn"):
			MonsterReborn(spell);
			break;
		case ("Pot of Greed"):
			PoG(spell);
			break;
		case ("Raigeki"):
			Raigeki(spell);
			break;
		default:
			return;
		}
	//	ActivePlayer.Graveyard.setText("Graveyard: "+Card.getBoard().getActivePlayer().getField().getGraveyard().get(Card.getBoard().getActivePlayer().getField().getGraveyard().size()-1).getName());
	}
	
		public void CoH(SpellCard spell){
			if(DoAction(spell,(MonsterCard) SpellTarget.getCard())){
				OpponentPlayer().MField.remove(SndClick);
				OpponentPlayer().JP1.remove(SndClick);
				MonsterButton mb = new MonsterButton("Monster Field");
				OpponentPlayer().MField.add(mb);
				if (OpponentPlayer()==GUI.P1)
					OpponentPlayer().JP1.add(mb,FlowLayout.LEFT);
				else
					OpponentPlayer().JP1.add(mb,FlowLayout.RIGHT);
				for (int i=0;i<5;i++){
					if (ActivePlayer.MField.get(i).getCard()==null){
						ActivePlayer.MField.get(i).setCard(SpellTarget.getCard());
						break;
					}
				}
			}
		}
		
	public void MagePower(SpellCard spell){
		DoAction(spell,(MonsterCard) SpellTarget.getCard());
	}
	
	public void GracefulDice(SpellCard spell){
		DoAction(spell,null);
	}
	
	public void MonsterReborn(SpellCard spell){
		if (DoAction(spell,null)){
			for (int i=0; i<5; i++){
				if (ActivePlayer.MField.get(i).getCard()==null){
					ActivePlayer.MField.get(i).setCard(Card.getBoard().getActivePlayer().Reborn);
					break;
				}
			}
		}
	}
	
	public void DarkHole(SpellCard spell){
		if (DoAction(spell,null)){
			OpponentPlayer().removeMobs();
			ActivePlayer.removeMobs();
			for (int i=0; i<5; i++){
				OpponentPlayer().MField.get(i).addMouseListener(this);
				ActivePlayer.MField.get(i).addMouseListener(this);
			}
		}
			
	}
	
	public void CardDestruction(SpellCard spell){
		if (DoAction(spell,null)){
			ActivePlayer.refreshHand(Card.getBoard().getActivePlayer());
			OpponentPlayer().refreshHand(Card.getBoard().getOpponentPlayer());
		}
		for (int i=0; i<ActivePlayer.Hand.size(); i++){
			ActivePlayer.Hand.get(i).addMouseListener(this);
		}
		for (int i=0; i<OpponentPlayer().Hand.size(); i++){
			OpponentPlayer().Hand.get(i).setText("Hidden");
			OpponentPlayer().Hand.get(i).addMouseListener(this);
		}
	}
	
	public void Raigeki(SpellCard spell){
		if (DoAction(spell,null))
			OpponentPlayer().removeMobs();
			for (int i=0; i<5; i++){
				OpponentPlayer().MField.get(i).addMouseListener(this);
			}
	}
	
	
	
	public void HeavyStorm(SpellCard spell){
		if (DoAction(spell,null)){
			OpponentPlayer().removeSpells();
			ActivePlayer.removeSpells();
			for (int i=0; i<5; i++){
				ActivePlayer.SField.get(i).addMouseListener(this);
				OpponentPlayer().SField.get(i).addMouseListener(this);
			}
		}
	}
	
	public boolean DoAction(SpellCard spell, MonsterCard monster){
		if (spell.getLocation()==Location.HAND)
			CardLocation=Location.HAND;
		else
			CardLocation=Location.FIELD;
		if (Card.getBoard().getActivePlayer().activateSpell(spell, monster)){
			SpellButton s = new SpellButton("Spell Field");
			if (CardLocation==Location.HAND){
				ActivePlayer.JP3.remove(MySpellButton);
				ActivePlayer.Hand.remove(MySpellButton);
			}else{
				ActivePlayer.JP2.remove(MySpellButton);
				ActivePlayer.SField.remove(MySpellButton);
				ActivePlayer.SField.add(s);
				if (ActivePlayer==GUI.P1)
					ActivePlayer.JP2.add(s,FlowLayout.LEFT);
				else
					ActivePlayer.JP2.add(s,FlowLayout.RIGHT);
			}
			if (!Card.getBoard().getActivePlayer().getField().getGraveyard().isEmpty())
			ActivePlayer.Graveyard.setText("Graveyard: "+Card.getBoard().getActivePlayer().getField().getGraveyard().get(Card.getBoard().getActivePlayer().getField().getGraveyard().size()-1).getName());
			if (!Card.getBoard().getOpponentPlayer().getField().getGraveyard().isEmpty())
				OpponentPlayer().Graveyard.setText("Graveyard: "+Card.getBoard().getOpponentPlayer().getField().getGraveyard().get(Card.getBoard().getOpponentPlayer().getField().getGraveyard().size()-1).getName());
			GameIsOver();
			return true;
		}
		return false;
	}
	
	public void HFD(SpellCard spell){
		if (DoAction(spell,null))
			OpponentPlayer().removeSpells();
			for (int i=0; i<5; i++){
				OpponentPlayer().SField.get(i).addMouseListener(this);
		}
	}
	
	
	
	public void PoG(SpellCard spell){
		if (DoAction(spell,null)){
			ActivePlayer.refreshHand(Card.getBoard().getActivePlayer());
		}
		for (int i=0; i<ActivePlayer.Hand.size(); i++){
			ActivePlayer.Hand.get(i).addMouseListener(this);
		}
	}
	public void GameIsOver(){
		if (Card.getBoard().isGameOver()){
			String [] choices = {"Play Again! :)", "Exit! :("};
			int choice = JOptionPane.showOptionDialog(null, Card.getBoard().getWinner().getName()+" has won! \nDo you wish to play again?!", "Game Over!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, "Play Again! :)");
			if (choice==0){
				try{
					GAME GAME = new GAME();
					GUI.dispose();
				}catch(Exception error){
					JOptionPane.showMessageDialog(null, "We were unable to restart your game! :(");
					System.exit(0);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Thank you for playing! :)");
				System.exit(0);
			}
		}
		
	}
}
