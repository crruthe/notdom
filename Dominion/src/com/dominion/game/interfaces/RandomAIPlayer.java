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
		GameMaster gm = new GameMaster();
		Player p1 = new Player(new BigMoneyAIPlayer());
		Player p2 = new Player(new RandomAIPlayer());
		Player p3 = new Player(new BasicRulesAIPlayer());
		gm.addPlayerToState(p1);		
		gm.addPlayerToState(p2);
		gm.addPlayerToState(p3);
		gm.startGame();
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
	public Card selectVictoryCardToReveal(List<Card> cards) {
		Collections.shuffle(cards);
		return cards.get(0);
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
		// TODO Auto-generated method stub

	}

	@Override
	public ActionCard selectActionCardToPlay(List<Card> cards) {
		Collections.shuffle(cards);
		return (ActionCard) cards.get(0);
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(List<Card> cards) {
		Collections.shuffle(cards);
		return (TreasureCard) cards.get(0);
	}

	@Override
	public Card selectCardToBuy(List<Card> cards) {
		// Chance to skip
		if (Math.random() < (1 / cards.size())) {
			return null;
		}
		Collections.shuffle(cards);
		return cards.get(0);
	}

	@Override
	public Card selectCardToDiscard(List<Card> cards) {
		// Chance to skip
		if (Math.random() < (1 / cards.size())) {
			return null;
		}
		Collections.shuffle(cards);
		return cards.get(0);
	}

	@Override
	public boolean chooseIfPutDeckInDiscard() {
		if (Math.random() < 0.5) {
			return true;
		}
		return false;
	}

	@Override
	public Card selectCardToTrashFromHand(List<Card> cards) {
		// Chance to skip
		if (Math.random() < (1 / cards.size())) {
			return null;
		}
		Collections.shuffle(cards);
		return cards.get(0);
	}

	@Override
	public boolean chooseIfSetAsideCard(Card card) {
		if (Math.random() < 0.5) {
			return true;
		}
		return false;
	}

	@Override
	public void notifyEndGameScore(Player player, int score) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyEndGameCards(Player player, List<Card> cards) {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyCardPlayed(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyCardGained(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean chooseIfTrashCard(Card card) {
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
	public boolean chooseIfDiscardCard(Card card) {
		if (Math.random() < 0.5) {
			return true;
		}		
		return false;
	}

	@Override
	public void notifyCardRevealed(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean chooseIfGainCardThief(Card card) {
		if (Math.random() < 0.5) {
			return true;
		}		
		return false;
	}

	@Override
	public void notifyHandRevealed(Player player, List<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CardAction selectCardActionToPlay(HashMap<String, CardAction> actions) {
		List<CardAction> actionsList = new LinkedList<CardAction>(actions.values());
		Collections.shuffle(actionsList);
		return actionsList.get(0);
	}

	@Override
	public Card selectCardToPutOnDeck(List<Card> cards) {
		return cards.get(0);
	}

	@Override
	public Card selectCardToTrashThief(List<Card> cards) {
		return cards.get(0);
	}

	@Override
	public void updateSupply(HashMap<Class<? extends Card>, Integer> supplyStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPlayerName() {
		return "RandomAIPlayer";
	}
}
