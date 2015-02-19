package com.dominion.game;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;
import com.dominion.game.modifiers.CardModifier;

public class TurnState {
	private int numberOfActions;
	private int numberOfBuys;
	private int totalCoins;	
	private List<CardModifier> modifiers = new LinkedList<CardModifier>();
	
	public TurnState() {
		reset();
	}
	
	public void decrementActions() {
		numberOfActions--;
	}
	
	public void decrementBuys() {
		numberOfBuys--;
	}
	
	public void decrementCoins(int coins) {
		totalCoins -= coins;
	}
	
	/**
	 * @return the numberOfActions
	 */
	public int getNumberOfActions() {
		return numberOfActions;
	}
	
	/**
	 * @return the numberOfBuys
	 */
	public int getNumberOfBuys() {
		return numberOfBuys;
	}
	
	/**
	 * @return the totalCoins
	 */
	public int getTotalCoins() {
		return totalCoins;
	}

	public void incrementActions(int amount) {
		numberOfActions += amount;
	}
	
	public void incrementBuys(int amount) {
		numberOfBuys += amount;
	}

	public void incrementCoins(int amount) {
		totalCoins += amount;
	}
	
	public void reset() {
		numberOfBuys = 1;
		numberOfActions = 1;
		totalCoins = 0;	
		modifiers.clear();
	}
	
	public void zeroActions() {
		numberOfActions = 0;
	}

	public void zeroBuys() {
		numberOfBuys = 0;
	}
		
	public void addModifier(CardModifier modifier) {
		modifiers.add(modifier);
	}
	
	public List<CardModifier> getModifiers() {
		return modifiers;
	}
}
