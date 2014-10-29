package com.dominion.game.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.CurseCard;
import com.dominion.game.cards.basic.DuchyCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.GoldCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;

public class SimpleConsolePlayer implements PlayerInterface {
	private String playerName = "";
	private ImmutableCardHand cardHand;
	private ImmutableTurnState turnState;
	private ImmutableGameBoard gameBoard;
	
	@Override
	public void updateCardHand(final ImmutableCardHand cardHand) {
		this.cardHand = cardHand;
	}
	
	public static void main(String[] args) {
		GameMaster gm = new GameMaster();
		
		
		gm.addPlayer(new Player(new SimpleConsolePlayer()));
		gm.addPlayer(new Player(new SimpleConsolePlayer()));
		gm.addPlayer(new Player(new SimpleConsolePlayer()));
		
		gm.startGame();
	}
	
	private void displayCardHand() {
		System.out.println(getPlayerName());
		System.out.println("=== Current Hand ===" );
		int i = 0;
		for (Card c : cardHand.getCards()) {
			System.out.println(i + " => " + c.getClass().getName());
			i++;
		}		
	}

	@Override
	public String getPlayerName() {
		if (!"".equals(playerName)) {
			return playerName;
		}
		
		System.out.print("Enter your name > ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			playerName = br.readLine();			
		} catch (IOException e) {
			throw new RuntimeException("unable to get player's name", e);
		}
		
		return playerName;
	}

	@Override
	public ActionCard getActionCardToPlay() {
		displayCardHand();
		
		System.out.println("Number of actions left: " + turnState.getNumberOfActions());
		System.out.print("Select action card to play (-1 to end phase)> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int handIndex = 0;
		
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
		
		if (handIndex >= cardHand.getCards().size()) {
			throw new RuntimeException("action card selection out of range");
		}
		
		Card card = cardHand.getCards().get(handIndex);
		
		if (!(card instanceof ActionCard)) {
			throw new RuntimeException("card selected is not an action card");
		}
		
		return (ActionCard)card;
	}

	@Override
	public void updateTurnState(final ImmutableTurnState turnState) {
		this.turnState = turnState;
	}

	@Override
	public Card getCardToBuy() {
		displayCardHand();
		displayGameBoard();
		System.out.println("Total coins left: " + turnState.getTotalCoins());
		System.out.println("Number of buys left: " + turnState.getNumberOfBuys());
		System.out.print("Select card to buy (-1 to end phase)> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String cardStr = "";
		
		try {
			cardStr = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		if (cardStr.equals("-1")) {
			return null;
		} else {
			return getCardByString(cardStr);
		}
	}

	@Override
	public Card getCardToGain(int cost) {
		displayGameBoard();
		
		System.out.print("Select card to gain (cost <= " + cost + ")> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String cardStr = "";
		
		try {
			cardStr = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		return getCardByString(cardStr);
	}

	@Override
	public void updateGameBoard(final ImmutableGameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	private void displayGameBoard() {
		System.out.println(EstateCard.class.getName() + " : " + EstateCard.COST + " : " + gameBoard.getEstateStackSize());
		System.out.println(DuchyCard.class.getName() + " : " + DuchyCard.COST + " : " + gameBoard.getDuchyStackSize());
		System.out.println(ProvinceCard.class.getName() + " : " + ProvinceCard.COST + " : " + gameBoard.getProvinceStackSize());
		System.out.println(CopperCard.class.getName() + " : " + CopperCard.COST);
		System.out.println(SilverCard.class.getName() + " : " + SilverCard.COST);
		System.out.println(GoldCard.class.getName() + " : " + GoldCard.COST);
		System.out.println(CurseCard.class.getName() + " : " + CurseCard.COST + " : " + gameBoard.getCurseStackSize());
		
		for (String key : gameBoard.getKingdomCards().keySet()) {
			try {
				Integer cost = (Integer) Class.forName(key).getDeclaredField("COST").get(null);
				
				System.out.println(key + " : " + cost + " : " + gameBoard.getKingdomCards().get(key));
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

	@Override
	public ReactionCard getReactionCardToPlay() {
		displayCardHand();
		
		boolean hasReactionCard = false;
		for (Card card : cardHand.getCards()) {
			if (card instanceof ReactionCard) {
				hasReactionCard = true;
			}
		}
		
		if (!hasReactionCard) {
			return null;
		}
		
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
		
		if (handIndex >= cardHand.getCards().size()) {
			throw new RuntimeException("reaction card selection out of range");
		}
		
		Card card = cardHand.getCards().get(handIndex);
		
		if (!(card instanceof ReactionCard)) {
			throw new RuntimeException("card selected is not a reaction card");
		}
		
		return (ReactionCard)card;	
	}

	@Override
	public Card getCardToDiscard() {
		displayCardHand();
		
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
		
		if (handIndex >= cardHand.getCards().size()) {
			throw new RuntimeException("card selection out of range");
		}
		
		Card card = cardHand.getCards().get(handIndex);
		
		return card;		
	}

	@Override
	public Card getCardToTrash() {
		displayCardHand();
		
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
		
		if (handIndex >= cardHand.getCards().size()) {
			throw new RuntimeException("card selection out of range");
		}
		
		Card card = cardHand.getCards().get(handIndex);
		
		return card;
	}

	@Override
	public boolean wantsToSetAsideCard(Card card) {
		displayCardHand();
		
		System.out.println(card);
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
	public boolean wantsToPutDeckInDiscard() {
		displayCardHand();
		
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
	public Card getTreasureCardToTrash() {
		displayCardHand();
		
		boolean hasTreasureCard = false;
		for (Card card : cardHand.getCards()) {
			if (card instanceof TreasureCard) {
				hasTreasureCard = true;
			}
		}
		
		if (!hasTreasureCard) {
			return null;
		}
		
		System.out.print("Select treasure card to trash (-1 to end phase)> ");
		
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
		
		if (handIndex >= cardHand.getCards().size()) {
			throw new RuntimeException("card selection out of range");
		}
		
		Card card = cardHand.getCards().get(handIndex);

		if (!(card instanceof TreasureCard)) {
			throw new RuntimeException("not a treasure card");
		}		
		
		return card;
	}

	@Override
	public Card getVictoryCardToReveal() {
		displayCardHand();
		
		boolean hasVictoryCard = false;
		for (Card card : cardHand.getCards()) {
			if (card instanceof VictoryCard) {
				hasVictoryCard = true;
			}
		}
		
		if (!hasVictoryCard) {
			return null;
		}
		
		System.out.print("Select victory card to reveal (-1 to end phase)> ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int handIndex = 0;
		
		try {
			handIndex = Integer.valueOf(br.readLine());
		} catch (IOException e) {
			throw new RuntimeException("unable to determine victory card", e);
		} catch (NumberFormatException e) {
			throw new RuntimeException("invalid victory card selection", e);
		}		
		
		if (handIndex == -1) {
			return null;
		}
		
		if (handIndex >= cardHand.getCards().size()) {
			throw new RuntimeException("victory card selection out of range");
		}
		
		Card card = cardHand.getCards().get(handIndex);
		
		if (!(card instanceof VictoryCard)) {
			throw new RuntimeException("card selected is not a victory card");
		}
		
		return card;	
	}
	
	/**
	 * Returns an instantiation of the card based on the string
	 * e.g. com.dominion.game.cards.basic.CopperCard
	 * 
	 * @param cardName
	 * @return
	 */
	private Card getCardByString(String cardClass) {
		try {
			return (Card) Class.forName(cardClass).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}	
}
