package com.dominion.game;

import java.util.HashMap;
import java.util.Observable;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;

public class SupplyStack extends Observable {

	protected HashMap<Class<? extends Card>, CardStack> stacks = new HashMap<Class<? extends Card>, CardStack>();
	
	public SupplyStack() {
		super();
	}

	/**
	 * Cloning constructor
	 * @param supply
	 */
	public SupplyStack(SupplyStack supply) {
		this.stacks = new HashMap<Class<? extends Card>, CardStack>(supply.stacks);		
	}

	public void addToStack(Class<? extends Card>cardClass, Integer numOfCards) {
		
		CardStack cardStack = new CardStack(numOfCards);

		// if victorycard, add a trade route token
		if (VictoryCard.class.isAssignableFrom(cardClass)) {
			cardStack.setTraceRouteToken(true);
		}
		
		stacks.put(cardClass, cardStack);
		setChanged();
		notifyObservers();
	}	
	
	public HashMap<Class<? extends Card>, CardStack> getStacks() {
		return stacks;
	}

	/**
	 * Check if a stack is empty (usually for end of game)
	 */
	public boolean isStackEmpty(Class<? extends Card> cardName) {
		return stacks.get(cardName).getNumCards() == 0;		
	}
	
	/**
	 * Remove the top card from the stack and return it
	 * 
	 * @param stack
	 * @return Card from top of stack
	 */
	public boolean removeCard(Class<? extends Card> cardClass) {
		CardStack cardStack = stacks.get(cardClass);
		
		if (cardStack.getNumCards() == 0) {			
			return false;
		}

		// Decrement the stack size by 1
		cardStack.decrementNumCards(1);
		
		setChanged();
		notifyObservers();
		return true;
	}
	
	/**
	 * Remove the trade token from the stack
	 * 
	 * @param cardClass
	 * @return
	 */
	public boolean retrieveTradeRouteToken(Class<? extends Card> cardClass) {
		CardStack cardStack = stacks.get(cardClass);
		if (cardStack.hasTraceRouteToken()) {
			cardStack.setTraceRouteToken(false);
			return true;
		}
		return false;
	}
}
