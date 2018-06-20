package eg.edu.guc.yugioh.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class GUI extends JFrame{
	
	public PLayout P1 = new PLayout(1);
	public PLayout P2 = new PLayout(2);
	public JButton Activate = new JButton("Activate");
	public JButton Attack = new JButton("Attack");
	public JButton ChangeMode = new JButton("Switch Mode");
	public JButton MSet = new JButton("Set");
	public JButton SSet = new JButton("Set");
	public JButton Summon = new JButton("Summon");
	public JButton nextPhase = new JButton("Next Phase");
	public JLabel currentPhase = new JLabel("Main Phase 1", SwingConstants.CENTER);
	public JButton endTurn = new JButton("End Turn");
	public GUI(){
		super();
		setSize(700,700);
		setVisible(true);
//		this.setContentPane(new ImagePanel(new ImageIcon("Baby Dragon.png").getImage().getScaledInstance(700, 700, java.awt.Image.SCALE_SMOOTH)));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Yu-Gi-Oh! By Omar Yousry and Omar Kamal");
		setLayout(new BorderLayout());
		getContentPane().add(P1, BorderLayout.SOUTH);
		getContentPane().add(P2, BorderLayout.NORTH);
		getContentPane().add(nextPhase,BorderLayout.WEST);
		JPanel Yolo = new JPanel();
		getContentPane().add(Yolo, BorderLayout.CENTER);
		Yolo.setLayout(new GridLayout(2,3));
		Yolo.add(P2.Name);
		Yolo.add(currentPhase);
		Yolo.add(P1.Name);
		Yolo.add(P2.LP);
		Yolo.add(nextPhase);
		Yolo.add(P1.LP);
		getContentPane().add(endTurn,BorderLayout.EAST);
		validate();
	}
	public static void main(String [] args){
		JFrame h = new JFrame();
		GUI g = new GUI();
		h.add(g);
		h.setVisible(true);
		h.pack();
	}
}