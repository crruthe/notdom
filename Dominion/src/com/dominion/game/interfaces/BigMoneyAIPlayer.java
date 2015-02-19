package com.dominion.game.interfaces;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.Player;
import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusActionAction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.actions.PlusCoinAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.AttackCard;
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
import com.dominion.game.cards.kingdom.ThroneRoomCard;

public class BigMoneyAIPlayer implements PlayerInterface {

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
		return "BigMoneyAIPlayer";
	}

	@Override
	public void notifyCardGained(Player player, Card card) {
		if (!player.getPlayerName().equals("BigMoneyAIPlayer"))
			return;
		System.out.println("CardGained - " + player.getPlayerName() + ": " + card);
	}

	@Override
	public void notifyCardPlayed(Player player, Card card) {
		if (!player.getPlayerName().equals("BigMoneyAIPlayer"))
			return;
		System.out.println("CardPlayed - " + player.getPlayerName() + ": " + card);
	}

	@Override
	public void notifyCardRevealed(Player player, Card card) {
		if (!player.getPlayerName().equals("BigMoneyAIPlayer"))
			return;
		System.out.println("CardRevealed - " + player.getPlayerName() + ": " + card);
	}

	@Override
	public void notifyCardTrashed(Player player, Card card) {
		if (!player.getPlayerName().equals("BigMoneyAIPlayer"))
			return;
		System.out.println("CardTrashed - " + player.getPlayerName() + ": " + card);
	}

	@Override
	public void notifyEndGameCards(Player player, List<Card> cards) {
		if (!player.getPlayerName().equals("BigMoneyAIPlayer"))
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
		if (!player.getPlayerName().equals("BigMoneyAIPlayer"))
			return;
		System.out.println("EndGameScore - " + player.getPlayerName() + ": " + score);
	}

	@Override
	public void notifyHandRevealed(Player player, List<Card> cards) {
		if (!player.getPlayerName().equals("BigMoneyAIPlayer"))
			return;
		System.out.println("HandRevealed - " + player.getPlayerName() + ": " + cards);
	}

	@Override
	public ActionCard selectActionCardToPlay(List<Card> cards) {
		return null;	
	}

	@Override
	public CardAction selectCardActionToPlay(HashMap<String, CardAction> actions) {
		// TODO Auto-generated method stub
		return actions.values().iterator().next();
	}

	@Override
	public Card selectCardToBuy(List<Card> cards) {
		
		if (cards.contains(new ProvinceCard())) {
			return new ProvinceCard();
		}
		
		if (cards.contains(new GoldCard())) {
			return new GoldCard();
		}
		
		if (cards.contains(new DuchyCard())) {
			return new DuchyCard();
		}
		
		if (cards.contains(new SilverCard())) {
			return new SilverCard();
		}
		
		return null;
	}

	@Override
	public Card selectCardToDiscard(List<Card> cards) {		
		for (Card c: cards) {
			if (c instanceof VictoryCard) {
				return c;
			}
		}
		if (Math.random() < 0.5) {
			return null;
		}		
		Collections.shuffle(cards);
		return cards.get(0);		
	}

	@Override
	public Card selectCardToPassLeft(final List<Card> cards) {
		// Send over the cheapest card
		List<Card> sCards = new LinkedList<Card>(cards);
		Collections.sort(sCards);
		return cards.get(0);
	}

	@Override
	public Card selectCardToPutOnDeck(List<Card> cards) {
		return cards.get(0);
	}

	@Override
	public Card selectCardToTrashFromHand(List<Card> cards) {
		for (Card c: cards) {
			if (c instanceof CurseCard) {
				return c;
			}
		}
		if (Math.random() < 0.5) {
			return null;
		}		
		for (Card c: cards) {
			if (c instanceof EstateCard || c instanceof CopperCard) {
				return c;
			}
		}		
		// Send over the cheapest card
		List<Card> sCards = new LinkedList<Card>(cards);
		Collections.sort(sCards);
		return cards.get(0);
	}

	@Override
	public Card selectCardToTrashThief(List<Card> cards) {
		// Trash the most expensive card
		List<Card> sCards = new LinkedList<Card>(cards);
		Collections.sort(sCards);
		Collections.reverse(sCards);
		return cards.get(0);
	}	
	@Override
	public ReactionCard selectReactionCard(List<Card> cards) {
		if (Math.random() < 0.25) {
			return null;
		}		
		return (ReactionCard) cards.get(0);
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(List<Card> cards) {
		return (TreasureCard) cards.get(0);
	}

	@Override
	public Card selectVictoryCardToReveal(List<Card> cards) {
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
