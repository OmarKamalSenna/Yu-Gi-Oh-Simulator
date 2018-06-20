package eg.edu.guc.yugioh.cards.spells;
import java.util.ArrayList;
import eg.edu.guc.yugioh.board.*;
import eg.edu.guc.yugioh.cards.Card;
import eg.edu.guc.yugioh.cards.Location;
import eg.edu.guc.yugioh.cards.MonsterCard;
public class CardDestruction extends SpellCard{
	public CardDestruction(String name, String description){
		super(name,description);
	}
	public void action(MonsterCard monster){
		Board b=Card.getBoard();
		int n=b.getActivePlayer().getField().getHand().size();
		int m=b.getOpponentPlayer().getField().getHand().size();
		ArrayList<Card> hn=b.getActivePlayer().getField().getHand();
		ArrayList<Card> gn=b.getActivePlayer().getField().getGraveyard();
		ArrayList<Card> hm=b.getOpponentPlayer().getField().getHand();
		ArrayList<Card> gm=b.getOpponentPlayer().getField().getGraveyard();
			while (n>0 && b.getActivePlayer().getField().getDeck().getDeck().size()!=0){
				gn.add(hn.remove(hn.size()-1));
				gn.get(gn.size()-1).setLocation(Location.GRAVEYARD);
				hn.add(0, b.getActivePlayer().getField().getDeck().getDeck()
					.remove(b.getActivePlayer().getField().getDeck().getDeck()
							.size()-1));
				hn.get(0).setLocation(Location.HAND);
				n--;
			}
		//b.getActivePlayer().addNCardsToHand(n);
	//	if (!Card.getBoard().isGameOver()){
			while (m>0 && b.getOpponentPlayer().getField().getDeck().getDeck().size()!=0){
				gm.add(hm.remove(hm.size()-1));
				gm.get(gm.size()-1).setLocation(Location.GRAVEYARD);
				hm.add(0, b.getOpponentPlayer().getField().getDeck().getDeck()
						.remove(b.getOpponentPlayer().getField().getDeck().getDeck()
								.size()-1));
				hm.get(0).setLocation(Location.HAND);
				m--;
			}
			if (n>0){
				Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
				Card.getBoard().setGameOver(true);
			}else{
			if (m>0){
				Card.getBoard().setWinner(Card.getBoard().getActivePlayer());
				Card.getBoard().setGameOver(true);
			}
			}
			
			
			/*b.switchPlayer();
		b.getOpponentPlayer().addNCardsToHand(m);
		b.switchPlayer();*/
		}
	}
//}
