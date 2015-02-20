package com.dominion.game.interfaces;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.lang.Math;

import com.dominion.game.GameMaster;
import com.dominion.game.Player;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;

public class RandomAIPlayer implements PlayerInterface {

	public static void main(String[] args) {
		for (int i=0; i<1000; i++) {
			GameMaster gm = new GameMaster();
			Player p1 = new Player(new BigMoneyAIPlayer());
			Player p2 = new Player(new RandomAIPlayer());
			Player p3 = new Player(new BasicRulesAIPlayer());
			gm.addPlayerToState(p1);		
			gm.addPlayerToState(p2);
			gm.addPlayerToState(p3);
			gm.startGame();
		}
	}
	
	@Override
	public boolean chooseIfDiscardCard(Card card) {
		if (Math.random() < 0.5) {
			return true;
		}		
		return false;
	}

	@Override
	public boolean chooseIfGainCard(Card card) {
		if (Math.random() < 0.5) {
			return true;
		}		
		return false;
	}

	@Override
	public boolean chooseIfGainCardThief(Card card) {
		if (Math.random() < 0.5) {
			return true;
		}		
		return false;
	}

	@Override
	public boolean chooseIfPutDeckInDiscard() {
		if (Math.random() < 0.5) {
			return true;
		}
		return false;
	}

	@Override
	public boolean chooseIfSetAsideCard(Card card) {
		if (Math.random() < 0.5) {
			return true;
		}
		return false;
	}

	@Override
	public boolean chooseIfTrashCard(Card card) {
		if (Math.random() < 0.5) {
			return true;
		}		
		return false;
	}

	@Override
	public String getPlayerName() {
		return "RandomAIPlayer";
	}

	@Override
	public Card guessCard(List<Card> cards) {
		Collections.shuffle(cards);
		return cards.get(0);
	}

	@Override
	public void notifyActionSelected(Player player, String action) {
		if (!player.getPlayerName().equals("RandomAIPlayer"))
			return;
		System.out.println("ActionSelected - " + player.getPlayerName() + ": " + action);
	}

	@Override
	public void notifyCardGained(Player player, Card card) {
		if (!player.getPlayerName().equals("RandomAIPlayer"))
			return;
		System.out.println("CardGained - " + player.getPlayerName() + ": " + card);
	}

	@Override
	public void notifyCardPlayed(Player player, Card card) {
		if (!player.getPlayerName().equals("RandomAIPlayer"))
			return;
		System.out.println("CardPlayed - " + player.getPlayerName() + ": " + card);
	}

	@Override
	public void notifyCardRevealed(Player player, Card card) {
		if (!player.getPlayerName().equals("RandomAIPlayer"))
			return;
		System.out.println("CardRevealed - " + player.getPlayerName() + ": " + card);
	}

	@Override
	public void notifyCardTrashed(Player player, Card card) {
		if (!player.getPlayerName().equals("RandomAIPlayer"))
			return;
		System.out.println("CardTrashed - " + player.getPlayerName() + ": " + card);
	}

	@Override
	public void notifyEndGameCards(Player player, List<Card> cards) {
		if (!player.getPlayerName().equals("RandomAIPlayer"))
			return;
		HashMap<String, Integer> sCards = new HashMap<String, Integer>();
		for (Card card : cards) {
			if (!sCards.containsKey(card.getName())) {
				sCards.put(card.getName(), 0);				
			}
			sCards.put(card.getName(), sCards.get(card.getName())+1);
		}
		System.out.println("EndGameCards - " + player.getPlayerName() + ": " + sCards);
	}

	@Override
	public void notifyEndGameScore(Player player, int score) {
		if (!player.getPlayerName().equals("RandomAIPlayer"))
			return;
		System.out.println("EndGameScore - " + player.getPlayerName() + ": " + score);
	}

	@Override
	public void notifyGuessCard(Player player, Card card) {
		if (!player.getPlayerName().equals("RandomAIPlayer"))
			return;
		System.out.println("GuessCard - " + player.getPlayerName() + ": " + card);
	}

	@Override
	public void notifyHandRevealed(Player player, List<Card> cards) {
		if (!player.getPlayerName().equals("RandomAIPlayer"))
			return;
		System.out.println("HandRevealed - " + player.getPlayerName() + ": " + cards);
	}

	@Override
	public void notifyNewTurn(int turnCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionCard selectActionCardToPlay(List<Card> cards) {
		Collections.shuffle(cards);
		return (ActionCard) cards.get(0);
	}

	@Override
	public CardAction selectCardActionToPlay(HashMap<String, CardAction> actions) {
		List<CardAction> actionsList = new LinkedList<CardAction>(actions.values());
		Collections.shuffle(actionsList);
		return actionsList.get(0);
	}

	@Override
	public Card selectCardToBuy(List<Card> cards) {
		// Chance to skip
		if (Math.random() < (1 / (cards.size()+1))) {
			return null;
		}
		Collections.shuffle(cards);
		return cards.get(0);
	}

	@Override
	public Card selectCardToDiscard(List<Card> cards) {
		// Chance to skip
		if (Math.random() < (1 / (cards.size()+1))) {
			return null;
		}
		Collections.shuffle(cards);
		return cards.get(0);
	}

	@Override
	public Card selectCardToPassLeft(List<Card> cards) {
		Collections.shuffle(cards);
		return cards.get(0);
	}

	@Override
	public Card selectCardToPutOnDeck(List<Card> cards) {
		Collections.shuffle(cards);
		return cards.get(0);
	}

	@Override
	public Card selectCardToTrashFromHand(List<Card> cards) {
		// Chance to skip
		if (Math.random() < (1 / (cards.size()+1))) {
			return null;
		}
		Collections.shuffle(cards);
		return cards.get(0);
	}

	@Override
	public Card selectCardToTrashThief(List<Card> cards) {
		return cards.get(0);
	}

	@Override
	public ReactionCard selectReactionCard(List<Card> cards) {
		if (Math.random() < 0.25) {
			return null;
		}
		Collections.shuffle(cards);
		return (ReactionCard) cards.get(0);
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(List<Card> cards) {
		Collections.shuffle(cards);
		return (TreasureCard) cards.get(0);
	}

	@Override
	public Card selectVictoryCardToReveal(List<Card> cards) {
		Collections.shuffle(cards);
		return cards.get(0);
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
	public void updateSupply(HashMap<Class<? extends Card>, Integer> supplyStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTrashPile(List<Card> cards) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTurnState(int numOfActions, int numOfBuys, int numOfCoins) {
		// TODO Auto-generated method stub

	}
}
