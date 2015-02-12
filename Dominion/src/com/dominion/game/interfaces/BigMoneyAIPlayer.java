package com.dominion.game.interfaces;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
	public ReactionCard selectReactionCard(List<Card> cards) {
		if (Math.random() < 0.25) {
			return null;
		}		
		return (ReactionCard) cards.get(0);
	}

	@Override
	public Card selectVictoryCardToReveal(List<Card> cards) {
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
		
		Card myCard = null;
		
		// Find throne room
		if (cards.size() > 1) {
			for (Card c: cards) {
				if (c instanceof ThroneRoomCard) {
					return (ActionCard) c;
				}
			}
		}
		
		// Find increase actions
		int maxActions = 0;
		for (Card c: cards) {
			Collection<CardAction> actions = ((ActionCard)c).buildActionList();
			for (CardAction a: actions) {
				if (a instanceof PlusActionAction) {
					int numOfActions = ((PlusActionAction)a).getNumActions();
					if (numOfActions > maxActions) {
						myCard = c;
						maxActions = numOfActions;
					}
				}
			}
		}		
		if (maxActions > 0) {
			return (ActionCard)myCard;
		}
		
		
		// Find increase cards
		int maxCards = 0;
		for (Card c: cards) {
			Collection<CardAction> actions = ((ActionCard)c).buildActionList();
			for (CardAction a: actions) {
				if (a instanceof PlusCardAction) {
					int numOfCards = ((PlusCardAction)a).getNumCards();
					if (numOfCards > maxCards) {
						myCard = c;
						maxCards = numOfCards;
					}
				}
			}
		}		
		if (maxCards > 0) {
			return (ActionCard)myCard;
		}
		
		
		// Find attack cards
		for (Card c: cards) {
			if (c instanceof AttackCard) {
				return (ActionCard) c;
			}
		}
		
		
		// Find coins cards
		int maxCoins = 0;
		for (Card c: cards) {
			Collection<CardAction> actions = ((ActionCard)c).buildActionList();
			for (CardAction a: actions) {
				if (a instanceof PlusCoinAction) {
					int numOfCoins = ((PlusCoinAction)a).getCoins();
					if (numOfCoins > maxCoins) {
						myCard = c;
						maxCoins = numOfCoins;
					}
				}
			}
		}		
		if (maxCoins > 0) {
			return (ActionCard)myCard;
		}
		
		Collections.shuffle(cards);
		return (ActionCard) cards.get(0);		
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(List<Card> cards) {
		return (TreasureCard) cards.get(0);
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
	public boolean chooseIfPutDeckInDiscard() {
		if (Math.random() < 0.5) {
			return true;
		}		
		return false;
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
		return null;
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
		System.out.println(player.getPlayerName() + ": " + score);
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
	public void notifyEndGameCards(Player player, List<Card> cards) {
		HashMap<String, Integer> cardMap = new HashMap<String, Integer>();	
		for (Card card : cards) {			
			if (!cardMap.containsKey(card.getName())) {
				cardMap.put(card.getName(), 1);
			} else {
				cardMap.put(card.getName(), cardMap.get(card.getName()) + 1);
			}
		}
		System.out.println(player.getPlayerName() + ": " + cardMap);
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
	public CardAction selectCardActionToPlay(List<CardAction> actions) {
		// TODO Auto-generated method stub
		return null;
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
}
