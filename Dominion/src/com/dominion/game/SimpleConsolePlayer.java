package com.dominion.game;

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
	private CardHand cardHand;
	private TurnState turnState;
	private GameBoard gameBoard;
	
	@Override
	public void updateCardHand() {
	}
	
	private void displayCardHand() {
		System.out.println(playerName);
		System.out.println("=== Current Hand ===" );
		int i = 0;
		for (Card c : cardHand.getCards()) {
			System.out.println(i + " => " + c.getDescription());
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
	public void updateTurnState() {			
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
		} else if (cardStr.equals("estate")) {
			return gameBoard.getEstateCard();
		} else if (cardStr.equals("duchy")) {
			return gameBoard.getDuchyCard();
		} else if (cardStr.equals("province")) {
			return gameBoard.getProvinceCard();
		} else if (cardStr.equals("copper")) {
			return gameBoard.getCopperCard();
		} else if (cardStr.equals("silver")) {
			return gameBoard.getSilverCard();
		} else if (cardStr.equals("gold")) {
			return gameBoard.getGoldCard();
		} else if (cardStr.equals("curse")) {
			return gameBoard.getCurseCard();
		}
		
		return gameBoard.getKingdomCard(cardStr);
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
		
		if (cardStr.equals("estate")) {
			return gameBoard.getEstateCard();
		} else if (cardStr.equals("duchy")) {
			return gameBoard.getDuchyCard();
		} else if (cardStr.equals("province")) {
			return gameBoard.getProvinceCard();
		} else if (cardStr.equals("copper")) {
			return gameBoard.getCopperCard();
		} else if (cardStr.equals("silver")) {
			return gameBoard.getSilverCard();
		} else if (cardStr.equals("gold")) {
			return gameBoard.getGoldCard();
		} else if (cardStr.equals("curse")) {
			return gameBoard.getCurseCard();
		}
		
		return gameBoard.getKingdomCard(cardStr);
	}

	@Override
	public void updateGameBoard() {		
	}
	
	private void displayGameBoard() {
		System.out.println("estate : " + EstateCard.COST + " : " + gameBoard.getEstateStackSize());
		System.out.println("duchy : " + DuchyCard.COST + " : " + gameBoard.getDuchyStackSize());
		System.out.println("province : " + ProvinceCard.COST + " : " + gameBoard.getProvinceStackSize());
		System.out.println("copper : " + CopperCard.COST);
		System.out.println("silver : " + SilverCard.COST);
		System.out.println("gold : " + GoldCard.COST);
		System.out.println("curse : " + CurseCard.COST + " : " + gameBoard.getCurseStackSize());
		
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
	public void setCardHand(CardHand cardHand) {
		this.cardHand = cardHand;		
	}

	@Override
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	@Override
	public void setTurnState(TurnState turnState) {
		this.turnState = turnState;
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
}
