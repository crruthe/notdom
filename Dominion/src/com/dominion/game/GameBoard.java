package com.dominion.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.*;
import com.dominion.game.cards.basic.*;
import com.dominion.game.cards.kingdom.*;

public class GameBoard {

	private final HashMap<Class<? extends Card>, Integer> supplyStacks = new HashMap<Class<? extends Card>, Integer>();
	
	private CardCollection trashPile = new CardCollection();

	public void addToTrashPile(Card card) {
		trashPile.addCardToTop(card);
	}
	
	/**
	 * Count the number of stacks that are empty.
	 * Used for determining if the game is finished.
	 * 
	 * @return number of empty stacks
	 */
	public int countNumberOfEmptyStacks() {
		int numOfEmptyStacks = 0;
		for (Integer numOfCards: supplyStacks.values()) {
			if (numOfCards == 0) {
				numOfEmptyStacks++;
			}
		}
		return numOfEmptyStacks;
	}
	
	public HashMap<Class<? extends Card>, Integer> getSupplyStacks() {
		return supplyStacks;
	}

	public CardCollection getTrashPile() {
		return trashPile;
	}
	
	/**
	 * Check if a stack is empty (usually for end of game)
	 */
	public boolean isStackEmpty(Class<? extends Card> cardName) {
		return supplyStacks.get(cardName) == 0;
	}

	/**
	 * Build up a list of cards that the player can buy
	 * 
	 * @param amount of coins to buy with
	 * @return collection of cards
	 */
	public List<Card> listCardsFilterByCost(int amount) {
		List<Card> cards = new LinkedList<Card>();
		
		for (Class<? extends Card> cardClass : supplyStacks.keySet()) {	
			// Check if any cards are left in stack
			if (supplyStacks.get(cardClass) > 0) {
				Card card = getCard(cardClass);
				
				// Check if player can afford this card
				if (card.getCost() <= amount) {
					cards.add(card);
				}
			}
		}
		
		return cards;
	}
	
	private Card getCard(Class<? extends Card> cardClass) {
		try {
			return cardClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Build up a list of treasure cards that the player can buy
	 * 
	 * @param amount of coins to buy with
	 * @return collection of treasure cards
	 */
	public List<TreasureCard> listTreasureCardsFilterByCost(int amount) {
		List<TreasureCard> cards = new LinkedList<TreasureCard>();

		for (Class<? extends Card> cardClass : supplyStacks.keySet()) {	
			// Check if any cards are left in stack
			if (supplyStacks.get(cardClass) > 0) {
				Card card = getCard(cardClass);
				
				// Check if player can afford this card
				if (card.getCost() <= amount && card instanceof TreasureCard) {
					cards.add((TreasureCard)card);
				}
			}
		}
		
		return cards;
	}

	/**
	 * Remove the top card from the stack and return it
	 * 
	 * @param stack
	 * @return Card from top of stack
	 */
	public Card removeCard(Class<? extends Card> cardClass) {
		if (supplyStacks.get(cardClass) == 0) {			
			return null;
		}

		// Decrement the stack size by 1
		supplyStacks.put(cardClass, supplyStacks.get(cardClass)-1);
		
		return getCard(cardClass);
	}

	public void setup(int numberOfPlayers) {
		setupKingdomCards(numberOfPlayers);
		setupVictoryCards(numberOfPlayers);
		setupCurseCards(numberOfPlayers);
		setupTreasureCards();
	}
	
	/**
	 * Place 10 Curse cards in the Supply for a 2
	 * player game, 20 Curse cards for 3 players,
	 * and 30 Curse cards for 4 players.
	 */
	private void setupCurseCards(int numberOfPlayers) {		
		int numOfCards = 10;
		
		if (numberOfPlayers == 3) {
			numOfCards = 20;
		} else if (numberOfPlayers == 4) {
			numOfCards = 30;
		}
		
		supplyStacks.put(CurseCard.class, numOfCards);
	}
	
	/** 
	 * The players select 10 Kingdom
	 * cards and place 10 of each in face-up piles
	 * on the table.
	 * Exception: Kingdom Victory card piles
	 * (e.g. Gardens) have the same number as
	 * the Victory card piles (12 for a 3 or 4
	 * player game and 8 for a 2 player game)
	 */	
	private void setupKingdomCards(int numberOfPlayers) {
		int numOfCards;		
		
		LinkedList<Class<? extends Card>> cardList = new LinkedList<Class<? extends Card>>();
		
		cardList.add(WoodcutterCard.class);
		cardList.add(VillageCard.class);
		cardList.add(WorkshopCard.class);
		cardList.add(SmithyCard.class);
		cardList.add(CouncilRoomCard.class);		
		cardList.add(LaboratoryCard.class);		
		cardList.add(FestivalCard.class);		
		cardList.add(MarketCard.class);		
		cardList.add(WitchCard.class);		
		cardList.add(MoatCard.class);		
		cardList.add(GardensCard.class);		
		cardList.add(CellarCard.class);		
		cardList.add(ChapelCard.class);		
		cardList.add(FeastCard.class);		
		cardList.add(LibraryCard.class);		
		cardList.add(ChancellorCard.class);		
		cardList.add(MilitiaCard.class);	
		cardList.add(MineCard.class);		
		cardList.add(AdventurerCard.class);
		cardList.add(RemodelCard.class);		
		cardList.add(BureaucratCard.class);		
		cardList.add(ThroneRoomCard.class);						
		cardList.add(ChapelCard.class);
		cardList.add(MoatCard.class);
		cardList.add(ChancellorCard.class);
		cardList.add(MoneylenderCard.class);
		cardList.add(ThiefCard.class);
		cardList.add(SpyCard.class);
		cardList.add(CourtyardCard.class);
		
		Collections.shuffle(cardList);
		
		for (Class<? extends Card> cardClass: cardList) {
			
			// If kingdom card is a victory cards, we only have 10
			if (!VictoryCard.class.isAssignableFrom(cardClass)) {
				numOfCards = 10;
			} else if (numberOfPlayers == 2) {
				numOfCards = 8;
			} else {
				numOfCards = 12;
			}
			
			supplyStacks.put(cardClass, numOfCards);
		}		
	}
	
	/**
	 * Place 12 each of the Estate,
	 * Duchy, and Province cards in face-up piles
	 * in the Supply in a 3 or 4 player game.
	 * In a 2 player game, place only 8 of each
	 * of these Victory cards in the Supply.
	 */
	private void setupVictoryCards(int numberOfPlayers) {
		int numOfCards = 12;
		
		if (numberOfPlayers == 2) {
			numOfCards = 8;
		}
		
		supplyStacks.put(EstateCard.class, numOfCards);
		supplyStacks.put(DuchyCard.class, numOfCards);
		supplyStacks.put(ProvinceCard.class, numOfCards);
	}
	
	/**
	 * Game supply has "unlimited" cards. 50 should be enough.
	 * @param numberOfPlayers
	 */
	private void setupTreasureCards() {
		supplyStacks.put(CopperCard.class, 50);
		supplyStacks.put(SilverCard.class, 50);
		supplyStacks.put(GoldCard.class, 50);
	}	
}
