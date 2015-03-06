package com.dominion.game;

import java.util.HashMap;
import java.util.Observable;

import com.dominion.game.cards.Card;

public class SupplyStack extends Observable {

	protected HashMap<Class<? extends Card>, Integer> stacks = new HashMap<Class<? extends Card>, Integer>();
	
	public SupplyStack() {
		super();
	}

	/**
	 * Cloning constructor
	 * @param supply
	 */
	public SupplyStack(SupplyStack supply) {
		this.stacks = new HashMap<Class<? extends Card>, Integer>(supply.stacks);		
	}

	public void addToStack(Class<? extends Card>cardClass, Integer numOfCards) {
		stacks.put(cardClass, numOfCards);
		setChanged();
		notifyObservers();
	}	
	
	public HashMap<Class<? extends Card>, Integer> getStacks() {
		return stacks;
	}

	/**
	 * Check if a stack is empty (usually for end of game)
	 */
	public boolean isStackEmpty(Class<? extends Card> cardName) {
		return stacks.get(cardName) == 0;		
	}
	
	/**
	 * Remove the top card from the stack and return it
	 * 
	 * @param stack
	 * @return Card from top of stack
	 */
	public boolean removeCard(Class<? extends Card> cardClass) {
		if (stacks.get(cardClass) == 0) {			
			return false;
		}

		// Decrement the stack size by 1
		stacks.put(cardClass, stacks.get(cardClass)-1);
		setChanged();
		notifyObservers();
		return true;
	}
}
