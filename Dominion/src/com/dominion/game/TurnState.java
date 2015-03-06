package com.dominion.game;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.modifiers.CardModifier;

public class TurnState {

	// Modifiers for the turn, e.g. Bridge
	protected List<CardModifier> modifiers = new LinkedList<CardModifier>();

	protected int numOfActions;

	// Tracked for Conspirator
	protected int numOfActionsPlayed;	
	
	protected int numOfBuys;
	
	protected int totalCoins;
	
	public TurnState() {
		super();
		reset();
	}
	
	/**
	 * Copy constructor
	 * @param turnState
	 */
	public TurnState(TurnState turnState) {
		this.modifiers = new LinkedList<CardModifier>(turnState.modifiers);
		this.numOfActions = turnState.numOfActions;
		this.numOfActionsPlayed = turnState.numOfActionsPlayed;
		this.numOfBuys = turnState.numOfBuys;
		this.totalCoins = turnState.totalCoins;
	}

	public void addModifier(CardModifier modifier) {
		modifiers.add(modifier);
	}
	
	public void decrementActions() {
		numOfActions--;
	}
	
	public void decrementBuys() {
		numOfBuys--;
	}
	
	public void decrementCoins(int coins) {
		totalCoins -= coins;
	}
	
	public List<CardModifier> getModifiers() {
		return modifiers;
	}
	
	/**
	 * @return the numberOfActions
	 */
	public int getNumOfActions() {
		return numOfActions;
	}

	public int getNumOfActionsPlayed() {
		return numOfActionsPlayed;
	}
	
	/**
	 * @return the numberOfBuys
	 */
	public int getNumOfBuys() {
		return numOfBuys;
	}

	/**
	 * @return the totalCoins
	 */
	public int getTotalCoins() {
		return totalCoins;
	}
	
	public void incrementActions(int amount) {
		numOfActions += amount;
	}
	
	public void incrementActionsPlayed() {
		numOfActionsPlayed ++;
	}
	
	public void incrementBuys(int amount) {
		numOfBuys += amount;
	}

	public void incrementCoins(int amount) {
		totalCoins += amount;
	}
		
	public void reset() {
		numOfBuys = 1;
		numOfActions = 1;
		totalCoins = 0;	
		numOfActionsPlayed = 0;
		modifiers.clear();
	}
	
	public void zeroActions() {
		numOfActions = 0;
	}

	public void zeroBuys() {
		numOfBuys = 0;
	}
}
