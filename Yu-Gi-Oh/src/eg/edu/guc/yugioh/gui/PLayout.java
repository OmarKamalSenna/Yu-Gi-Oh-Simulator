package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import eg.edu.guc.yugioh.board.player.Player;

@SuppressWarnings({ "serial", "unused" })
public class PLayout extends JPanel {
	public ArrayList<HandButton> Hand = new ArrayList<HandButton>();
	public ArrayList<MonsterButton> MField = new ArrayList<MonsterButton>();
	public ArrayList<SpellButton> SField = new ArrayList<SpellButton>();
	public JLabel Deck = new JLabel("Deck", SwingConstants.CENTER);
	public JLabel Graveyard = new JLabel("Graveyard", SwingConstants.CENTER);
	public JLabel Name = new JLabel("P2", SwingConstants.CENTER);
	public JLabel LP = new JLabel("8000", SwingConstants.CENTER);
	public JButton Peak = new JButton("peak");
	public JPanel JP1 = new JPanel();
	public JPanel JP2 = new JPanel();
	public JPanel JP3 = new JPanel();
	int Pnum;

	public PLayout(int j) {
		if (j == 1) {
			Pnum = 1;
			setPreferredSize(new Dimension(200, 300));
			setVisible(true);
			setLayout(new GridLayout(3, 1));
			JP1.setLayout(new GridLayout(1, 6));
			for (int i = 0; i < 5; i++) {
				MField.add(new MonsterButton("Monster Field"));
				// MField.get(i).setIcon(new ImageIcon(new
				 //ImageIcon("MO.png").getImage().getScaledInstance(70, 70,
				 //java.awt.Image.SCALE_SMOOTH)));
				JP1.add(MField.get(i));
			}
			JP1.add(Graveyard);
			add(JP1);
			JP2.setLayout(new GridLayout(1, 6));
			for (int i = 0; i < 5; i++) {
				SField.add(new SpellButton("Spell Field"));
				 //SField.get(i).setIcon(new ImageIcon(new
				 //ImageIcon("SP.png").getImage().getScaledInstance(70, 70,
				 //java.awt.Image.SCALE_SMOOTH)));
				JP2.add(SField.get(i));

			}
			JP2.add(Deck);
			add(JP2);
			JP3.setLayout(new FlowLayout());
			add(JP3);
		} else {
			Pnum = 2;
			setPreferredSize(new Dimension(200, 300));
			setVisible(true);
			setLayout(new GridLayout(3, 1));
			JP3.setLayout(new FlowLayout());
			add(JP3);
			JP2.setLayout(new GridLayout(1, 6));
			JP2.add(Deck);
			for (int i = 0; i < 5; i++) {
				SField.add(new SpellButton("Spell Field"));
				 //SField.get(i).setIcon(new ImageIcon(new
				 //ImageIcon("SP.png").getImage().getScaledInstance(70, 70,
				 //java.awt.Image.SCALE_SMOOTH)));
				JP2.add(SField.get(i));
			}
			add(JP2);
			JP1.setLayout(new GridLayout(1, 6));
			JP1.add(Graveyard);
			for (int i = 0; i < 5; i++) {
				MField.add(new MonsterButton("Monster Field"));
				 //MField.get(i).setIcon(new ImageIcon(new
				 //ImageIcon("MO.png").getImage().getScaledInstance(70, 70,
				 //java.awt.Image.SCALE_SMOOTH)));
				JP1.add(MField.get(i));
			}
			add(JP1);
		}
	}

	public void refreshHand(Player p) {
		Deck.setText("" + p.getField().getDeck().getDeck().size()
				+ " Cards Remaining");
		Hand = new ArrayList<HandButton>();
		JP3.removeAll();
		for (int i = 0; i < p.getField().getHand().size(); i++) {
			Hand.add(new HandButton(p.getField().getHand().get(i)));
			JP3.add(Hand.get(i));
		}
	}

	public void removeSpells() {
		for (int i = 0; i < SField.size(); i++) {
			SField = new ArrayList<SpellButton>();
			JP2.removeAll();
		}
		if (Pnum == 2)
			JP2.add(Deck);
		for (int i = 0; i < 5; i++) {
			SField.add(new SpellButton("Spell Field"));
			JP2.add(SField.get(i));
		}
		if (Pnum == 1)
			JP2.add(Deck);
	}

	public void removeMobs() {
		for (int i = 0; i < MField.size(); i++) {
			MField = new ArrayList<MonsterButton>();
			JP1.removeAll();
		}
		if (Pnum == 2)
			JP1.add(Graveyard);
		for (int i = 0; i < 5; i++) {
			MField.add(new MonsterButton("Monster Field"));
			JP1.add(MField.get(i));
		}
		if (Pnum == 1)
			JP1.add(Graveyard);
	}

	public void HideHand() {
		for (int i = 0; i < this.Hand.size(); i++) {
			if (this.Hand.get(i).getCard() != null)
				this.Hand.get(i).setText("Hidden");
		}
	}

	public void RevealHand() {
		for (int i = 0; i < this.Hand.size(); i++) {
			if (this.Hand.get(i).getCard() != null)
				this.Hand.get(i).setCard(this.Hand.get(i).getCard());
		}
	}

	public boolean checkMe() {
		for (int i = 0; i < this.Hand.size(); i++) {
			if (this.Hand.get(i).getCard() == null)
				return true;
		}
		return false;
	}
}
