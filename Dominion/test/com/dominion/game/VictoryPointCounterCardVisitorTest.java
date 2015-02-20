package com.dominion.game;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.kingdom.DukeCard;
import com.dominion.game.cards.kingdom.GardensCard;
import com.dominion.game.visitors.VictoryPointCounterCardVisitor;

import junit.framework.TestCase;

public class VictoryPointCounterCardVisitorTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCountVictoryWithGardensWorthZero() {
		VictoryPointCounterCardVisitor victoryPointCounterCardVisitor = new VictoryPointCounterCardVisitor();
		
		List<Card> cards = new LinkedList<Card>();
		
		cards.add(new GardensCard()); // 1 garden
		
		victoryPointCounterCardVisitor.setNumOfCards(1);
		
		for (Card card : cards) {
			card.accept(victoryPointCounterCardVisitor);
		}
	}
	
	public void testCountVictoryWithGardensWorthOne() {
		VictoryPointCounterCardVisitor victoryPointCounterCardVisitor = new VictoryPointCounterCardVisitor();
		List<Card> cards = new LinkedList<Card>();
		
		cards.add(new GardensCard()); // 1 garden
		
		victoryPointCounterCardVisitor.setNumOfCards(10); 
		for (Card card : cards) {
			card.accept(victoryPointCounterCardVisitor);
		}
		assertEquals(1, victoryPointCounterCardVisitor.getVictoryPoints());
	}	
	
	public void testCountVictoryWithGardensWorthTwo() {
		VictoryPointCounterCardVisitor victoryPointCounterCardVisitor = new VictoryPointCounterCardVisitor();
		
		List<Card> cards = new LinkedList<Card>();
		
		cards.add(new GardensCard()); // 1 garden
		
		victoryPointCounterCardVisitor.setNumOfCards(29);
		
		for (Card card : cards) {
			card.accept(victoryPointCounterCardVisitor);
		}
		
		assertEquals(2, victoryPointCounterCardVisitor.getVictoryPoints());		
	}	

	public void testCountVictoryWithMultipleGardensWorthTwo() {
		VictoryPointCounterCardVisitor victoryPointCounterCardVisitor = new VictoryPointCounterCardVisitor();
		
		List<Card> cards = new LinkedList<Card>();
		
		cards.add(new GardensCard()); // 1 garden
		cards.add(new GardensCard()); // 2 gardens
		
		victoryPointCounterCardVisitor.setNumOfCards(29);
		
		for (Card card : cards) {
			card.accept(victoryPointCounterCardVisitor);
		}
		
		assertEquals(4, victoryPointCounterCardVisitor.getVictoryPoints());		
	}
	
	public void testCountVictoryWithMultipleDukesOneDuchy() {
		VictoryPointCounterCardVisitor victoryPointCounterCardVisitor = new VictoryPointCounterCardVisitor();
		
		List<Card> cards = new LinkedList<Card>();
		
		cards.add(new DukeCard());
		cards.add(new DukeCard());
		
		victoryPointCounterCardVisitor.setNumOfDuchies(1);
		
		for (Card card : cards) {
			card.accept(victoryPointCounterCardVisitor);
		}
		
		assertEquals(2, victoryPointCounterCardVisitor.getVictoryPoints());		
	}
	
	public void testCountVictoryWithMultipleDukesMultipleDuchy() {
		VictoryPointCounterCardVisitor victoryPointCounterCardVisitor = new VictoryPointCounterCardVisitor();
		
		List<Card> cards = new LinkedList<Card>();
		
		cards.add(new DukeCard());
		cards.add(new DukeCard());
		
		victoryPointCounterCardVisitor.setNumOfDuchies(3);
		
		for (Card card : cards) {
			card.accept(victoryPointCounterCardVisitor);
		}
		
		assertEquals(6, victoryPointCounterCardVisitor.getVictoryPoints());		
	}

	public void testCountVictoryWithDukesNoDuchy() {
		VictoryPointCounterCardVisitor victoryPointCounterCardVisitor = new VictoryPointCounterCardVisitor();
		
		List<Card> cards = new LinkedList<Card>();
		
		cards.add(new DukeCard());
		
		victoryPointCounterCardVisitor.setNumOfDuchies(0);
		
		for (Card card : cards) {
			card.accept(victoryPointCounterCardVisitor);
		}
		
		assertEquals(0, victoryPointCounterCardVisitor.getVictoryPoints());		
	}
}
