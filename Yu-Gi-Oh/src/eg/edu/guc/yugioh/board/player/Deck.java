package eg.edu.guc.yugioh.board.player;

import eg.edu.guc.yugioh.cards.*;
import eg.edu.guc.yugioh.cards.spells.*;
import java.util.Scanner;

import eg.edu.guc.yugioh.exceptions.*;

import java.util.ArrayList;

public class Deck {
	static private ArrayList<Card> monsters = new ArrayList<Card>();
	static private ArrayList<Card> spells = new ArrayList<Card>();
	private static String monstersPath = ("Database-Monsters.csv");
	private static String spellsPath = ("Database-Spells.csv");
	private ArrayList<Card> deck = new ArrayList<Card>();

	public static ArrayList<Card> getMonsters() {
		return monsters;
	}

	public static ArrayList<Card> getSpells() {
		return spells;
	}

	public Deck() throws Exception {
		Scanner sc = new Scanner(System.in);
		for (int t = 0; t <= 3; t++) {
			try {
				monsters = loadCardsFromFile(monstersPath);
				break;
			} catch (Exception e) {
				if (t < 3 && sc.hasNextLine()) {
					System.out.println("Please enter a correct file path: ");
					monstersPath = sc.nextLine();
				} else {
					sc.close();
					e.printStackTrace();
					throw e;
				}
			}
		}
		for (int t = 0; t <= 3; t++) {
			try {
				spells = loadCardsFromFile(spellsPath);
				break;
			} catch (Exception e) {
				if (t < 3 && sc.hasNextLine()) {
					System.out.println("Please enter a correct file path: ");
					spellsPath = sc.nextLine();
				} else {
					e.printStackTrace();
					sc.close();
					throw e;
				}
			}
		}
		sc.close();
		buildDeck(monsters, spells);
		shuffleDeck();
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public static int randomWithinRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	private void buildDeck(ArrayList<Card> Monsters, ArrayList<Card> Spells)
			throws CloneNotSupportedException {
		for (int i = 0; i < 15; i++)
			deck.add((Card) Monsters.get(
					randomWithinRange(0, Monsters.size()-1)).clone());
		for (int i = 0; i < 5; i++)
			deck.add((Card) Spells.get(randomWithinRange(0, Spells.size()-1))
					.clone());
	}

	private void shuffleDeck() {
		Card x;
		Card y;
		int i;
		int j;
		for (int k = 0; k < 20; k++) {
			i = randomWithinRange(0, 19);
			j = randomWithinRange(0, 19);
			if (i != j) {
				x = deck.get(i);
				y = deck.get(j);
				deck.set(i, y);
				deck.set(j, x);

			}
		}
	}

	public Card drawOneCard() {
		return drawNCards(1).get(0);
	}

	public ArrayList<Card> drawNCards(int n) {
		ArrayList<Card> c = new ArrayList<Card>();
		for (int i = 0; i < deck.size() && i < n; n--) {
			c.add(deck.remove(deck.size() - 1));
		}
		return c;
	}

	public ArrayList<Card> loadCardsFromFile(String path) throws Exception {
		ArrayList<String> s = ReadingCSVFile.readFile(path);
		ArrayList<Card> c = new ArrayList<Card>();
		int x = s.size();
		String f;

		String[] m = s.get(0).split(",");
		if ((!ReadingCSVFile.monster(m)) && (!ReadingCSVFile.spell(m))) {
			throw new UnknownCardTypeException(path, 1, m[0]);
		}
		if (ReadingCSVFile.monster(m)) {
			for (int i = 0; i < x; i++) {
				m = s.get(i).split(",");
				if (m.length < 6) {
					throw new MissingFieldException(path, i + 1);
				}
				if (!ReadingCSVFile.monster(m))
					throw new UnknownCardTypeException(path, i + 1, m[i]);
				for (int t = 1; t <= 5; t++) {
					f = m[t].replace(" ", "");
					if (f.isEmpty()) {
						throw new EmptyFieldException(path, i + 1, t);
					}
				}
				c.add(new MonsterCard(m[1], m[2], Integer.parseInt((m[5])),
						Integer.parseInt(m[3]), Integer.parseInt(m[4])));
			}
		} else {
			if (ReadingCSVFile.spell(m)) {
				for (int i = 0; i < x; i++) {
					m = s.get(i).split(",");
					if (!ReadingCSVFile.spell(m))
						throw new UnknownCardTypeException(path, i + 1, m[0]);
					if (m.length < 3) {
						throw new MissingFieldException(path, i + 1);
					}
					for (int t = 1; t < 3; t++) {
						f = m[t].replace(" ", "");
						if (f.isEmpty()) {
							throw new EmptyFieldException(path, i + 1, t + 1);
						}
					}
					switch (m[1]) {
					case ("Card Destruction"):
						c.add(new CardDestruction(m[1], m[2]));
						break;
					case ("Change Of Heart"):
						c.add(new ChangeOfHeart(m[1], m[2]));
						break;
					case ("Dark Hole"):
						c.add(new DarkHole(m[1], m[2]));
						break;
					case ("Graceful Dice"):
						c.add(new GracefulDice(m[1], m[2]));
						break;
					case ("Harpie's Feather Duster"):
						c.add(new HarpieFeatherDuster(m[1], m[2]));
						break;
					case ("Heavy Storm"):
						c.add(new HeavyStorm(m[1], m[2]));
						break;
					case ("Mage Power"):
						c.add(new MagePower(m[1], m[2]));
						break;
					case ("Monster Reborn"):
						c.add(new MonsterReborn(m[1], m[2]));
						break;
					case ("Pot of Greed"):
						c.add(new PotOfGreed(m[1], m[2]));
						break;
					case ("Raigeki"):
						c.add(new Raigeki(m[1], m[2]));
						break;
					default:
						throw new UnknownSpellCardException(path, i + 1, m[1]);
					}

				}
			}
		}
		return c;
	}

	public static String getMonstersPath() {
		return monstersPath;
	}

	public static void setMonstersPath(String monstersPath) {
		Deck.monstersPath = monstersPath;
	}

	public static String getSpellsPath() {
		return spellsPath;
	}

	public static void setSpellsPath(String spellsPath) {
		Deck.spellsPath = spellsPath;
	}

	public static void main(String[] args) throws Exception {

		@SuppressWarnings("unused")
		Deck d = new Deck();
	}

}