package com.dominion.game.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import com.dominion.game.GameMaster;
import com.dominion.game.Player;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;

public class SimpleConsolePlayer implements PlayerInterface {
	private int numOfActions;
	private int numOfCoins;
	private int numOfBuys;
	
	public static void main(String[] args) {
		GameMaster gm = new GameMaster();
		
		
		gm.addPlayer(new Player(new SimpleConsolePlayer()));
		gm.addPlayer(new Player(new SimpleConsolePlayer()));
		gm.addPlayer(new Player(new SimpleConsolePlayer()));
		
		gm.startGame();
	}

	@Override
	public ActionCard selectActionCardToPlay(final List<Card> cards) {
		
		System.out.println("Number of actions left: " + numOfActions);
		displayCards(cards);
		int handIndex = -1;
		while(handIndex == -1 || handIndex >= cards.size()) {
			System.out.print("Select action card to play (-1 to end phase)> ");
	
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
			
			try {
				handIndex = Integer.valueOf(br.readLine());			
			} catch (IOException e) {
				throw new RuntimeException("unable to determine action card", e);
			} catch (NumberFormatException e) {
				throw new RuntimeException("invalid action card selection", e);
			}		
			
			if (handIndex == -1) {
				return null;
			}
		}
		
		Card card = cards.get(handIndex);
		
		if (!(card instanceof ActionCard)) {
			throw new RuntimeException("card selected is not an action card");
		}
		
		return (ActionCard)card;
	}

	private void displayCards(List<Card> cards) {
		int i = 0;
		for (Card card : cards) {
			System.out.println(i + card.getName() + "(" + card.getCost() + ")");
			i++;
		}
	}

	@Override
	public Card selectCardToBuy(final List<Card> cards) {
		displayCards(cards);
		System.out.println("Total coins left: " + numOfCoins);
		System.out.println("Number of buys left: " + numOfBuys);
		System.out.print("Select card to buy (-1 to end phase)> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Integer cardNum = -1;
		try {
			cardNum = Integer.valueOf(br.readLine());
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (cardNum.equals(-1)) {
			return null;
		} else {
			return cards.get(cardNum);
		}
	}

	@Override
	public ReactionCard selectReactionCard(final List<Card> cards) {
		displayCards(cards);
		
		System.out.print("Select reaction card to play (-1 to end phase)> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int handIndex = 0;
		
		try {
			handIndex = Integer.valueOf(br.readLine());			
		} catch (IOException e) {
			throw new RuntimeException("unable to determine reaction card", e);
		} catch (NumberFormatException e) {
			throw new RuntimeException("invalid reaction card selection", e);
		}		
		
		if (handIndex == -1) {
			return null;
		}
		
		if (handIndex >= cards.size()) {
			throw new RuntimeException("reaction card selection out of range");
		}
		
		Card card = cards.get(handIndex);
		
		if (!(card instanceof ReactionCard)) {
			throw new RuntimeException("card selected is not a reaction card");
		}
		
		return (ReactionCard)card;	
	}

	@Override
	public Card selectCardToDiscard(final List<Card> cards) {
		displayCards(cards);
		
		System.out.print("Select card to discard (-1 to end phase)> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int handIndex = 0;
		
		try {
			handIndex = Integer.valueOf(br.readLine());			
		} catch (IOException e) {
			throw new RuntimeException("unable to determine card", e);
		} catch (NumberFormatException e) {
			throw new RuntimeException("invalid card selection", e);
		}		
		
		if (handIndex == -1) {
			return null;
		}
		
		if (handIndex >= cards.size()) {
			throw new RuntimeException("card selection out of range");
		}
		
		Card card = cards.get(handIndex);
		
		return card;		
	}

	@Override
	public Card selectCardToTrashFromHand(final List<Card> cards) {
		displayCards(cards);
		
		System.out.print("Select card to trash (-1 to end phase)> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int handIndex = 0;
		
		try {
			handIndex = Integer.valueOf(br.readLine());			
		} catch (IOException e) {
			throw new RuntimeException("unable to determine card", e);
		} catch (NumberFormatException e) {
			throw new RuntimeException("invalid card selection", e);
		}		
		
		if (handIndex == -1) {
			return null;
		}
		
		if (handIndex >= cards.size()) {
			throw new RuntimeException("card selection out of range");
		}
		
		Card card = cards.get(handIndex);
		
		return card;
	}

	@Override
	public boolean chooseIfSetAsideCard(final Card card) {
		System.out.println(card.getName());
		System.out.println("Set action card aside (1 for yes)> ");
				
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		boolean result = false;
		try {
			result = (Integer.valueOf(br.readLine())).equals(1);			
		} catch (IOException e) {
			throw new RuntimeException("unable to determine answer", e);
		} catch (NumberFormatException e) {
			throw new RuntimeException("invalid answer", e);
		}		
		
		return result;
	}

	@Override
	public boolean chooseIfPutDeckInDiscard() {
		System.out.println("Discard deck into discard (1 for yes)> ");
				
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		boolean result = false;
		try {
			result = (Integer.valueOf(br.readLine())).equals(1);			
		} catch (IOException e) {
			throw new RuntimeException("unable to determine answer", e);
		} catch (NumberFormatException e) {
			throw new RuntimeException("invalid answer", e);
		}		
		
		return result;
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(final List<Card> cards) {
		displayCards(cards);
		System.out.print("Select treasure card to play (-1 to end phase)> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int handIndex = 0;
		do {
			try {
				handIndex = Integer.valueOf(br.readLine());			
			} catch (IOException e) {
				throw new RuntimeException("unable to determine card", e);
			} catch (NumberFormatException e) {
				throw new RuntimeException("invalid card selection", e);
			}		
			
			if (handIndex == -1) {
				return null;
			}
			
			if (handIndex >= cards.size()) {
				System.out.println("card not found, try again.");
			}
		} while (handIndex >= cards.size());
	
		Card card = cards.get(handIndex);

		if (!(card instanceof TreasureCard)) {
			throw new RuntimeException("not a treasure card");
		}		
		
		return (TreasureCard)card;
	}

	@Override
	public Card selectVictoryCardToReveal(List<Card> cards) {
		displayCards(cards);
		
		System.out.print("Select card from hand (-1 to end phase)> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int handIndex = 0;
		
		do {
			try {
				handIndex = Integer.valueOf(br.readLine());			
			} catch (IOException e) {
				throw new RuntimeException("unable to determine card", e);
			} catch (NumberFormatException e) {
				throw new RuntimeException("invalid card selection", e);
			}		
			
			if (handIndex == -1) {
				return null;
			}
			
			if (handIndex >= cards.size()) {
				System.out.println("card not found, try again.");
			}
		} while (handIndex >= cards.size());
		
		Card card = cards.get(handIndex);
		
		return card;
	}

	@Override
	public void updateSupply(HashMap<String, List<Card>> supplyStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHand(List<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOtherPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlayArea(List<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDeck(int numOfCards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDiscard(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTrashPile(List<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTurnState(int numOfActions, int numOfBuys, int numOfCoins) {
		this.numOfActions = numOfActions;
		this.numOfBuys = numOfBuys;
		this.numOfCoins = numOfCoins;
	}
}
