package com.dominion.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.cards.basic.*;
import com.dominion.game.cards.kingdom.*;

public class GameBoard {

	/**
	 * Check if a stack is empty (usually for end of game)
	 */
	public boolean isStackEmpty(String stack) {
		return supplyStacks.get(stack).isEmpty();
	}
	
	public int countNumberOfEmptyStacks() {
		int numOfEmptyStacks = 0;
		for (List<Card> cards : supplyStacks.values()) {
			if (cards.isEmpty()) {
				numOfEmptyStacks++;
			}
		}
		return numOfEmptyStacks;
	}

	private final LinkedList<Card> trashPile = new LinkedList<Card>();
	
	private final HashMap<String, List<Card>> supplyStacks = new HashMap<String, List<Card>>();
	
	public HashMap<String, List<Card>> getSupplyStacks() {
		return supplyStacks;
	}

	public void setup(int numberOfPlayers) {
		setupKingdomCards(numberOfPlayers);
		setupVictoryCards(numberOfPlayers);
		setupCurseCards(numberOfPlayers);
		setupTreasureCards();
	}
	
	private HashMap<String, Class<? extends Card>> selectRandomKingdomCards(HashMap<String, Class<? extends Card>> cardList) {
		HashMap<String, Class<? extends Card>> newCards = new HashMap<String, Class<? extends Card>>();

		// Build a list of keys
		LinkedList<String> cards = new LinkedList<String>(cardList.keySet());
		
		// Shuffle the keys
		Collections.shuffle(cards);
		
		// Select 10 of the new keys and place them in a new hashmap
		for(String card : cards.subList(0, 10)) {
			newCards.put(card, cardList.get(card));
		}
		return newCards;
	}

	/* The players select 10 Kingdom
	 * cards and place 10 of each in face-up piles
	 * on the table.
	 * Exception: Kingdom Victory card piles
	 * (e.g. Gardens) have the same number as
	 * the Victory card piles (12 for a 3 or 4
	 * player game and 8 for a 2 player game)
	 */	
	private void setupKingdomCards(int numberOfPlayers) {
		int numOfCards;		
		
		HashMap<String, Class<? extends Card>> cardList = new HashMap<String, Class<? extends Card>>();
		
		cardList.put(WoodcutterCard.NAME, WoodcutterCard.class);
		cardList.put(VillageCard.NAME, VillageCard.class);
		cardList.put(WorkshopCard.NAME, WorkshopCard.class);
		cardList.put(SmithyCard.NAME, SmithyCard.class);
		cardList.put(CouncilRoomCard.NAME, CouncilRoomCard.class);		
		cardList.put(LaboratoryCard.NAME, LaboratoryCard.class);		
		cardList.put(FestivalCard.NAME, FestivalCard.class);		
		cardList.put(MarketCard.NAME, MarketCard.class);		
		cardList.put(WitchCard.NAME, WitchCard.class);		
		cardList.put(MoatCard.NAME, MoatCard.class);		
		cardList.put(GardensCard.NAME, GardensCard.class);		
		cardList.put(CellarCard.NAME, CellarCard.class);		
		cardList.put(ChapelCard.NAME, ChapelCard.class);		
		cardList.put(FeastCard.NAME, FeastCard.class);		
		cardList.put(LibraryCard.NAME, LibraryCard.class);		
		cardList.put(ChancellorCard.NAME, ChancellorCard.class);		
		cardList.put(MilitiaCard.NAME, MilitiaCard.class);	
		cardList.put(MineCard.NAME, MineCard.class);		
		cardList.put(AdventurerCard.NAME, AdventurerCard.class);
		cardList.put(RemodelCard.NAME, RemodelCard.class);		
		cardList.put(BureaucratCard.NAME, BureaucratCard.class);		
		cardList.put(ThroneRoomCard.NAME, ThroneRoomCard.class);						
		cardList.put(ChapelCard.NAME, ChapelCard.class);
		cardList.put(MoatCard.NAME, MoatCard.class);
		cardList.put(ChancellorCard.NAME, ChancellorCard.class);
		cardList.put(MoneylenderCard.NAME, MoneylenderCard.class);
		cardList.put(ThiefCard.NAME, ThiefCard.class);
		cardList.put(SpyCard.NAME, SpyCard.class);
		cardList.put(CourtyardCard.NAME, CourtyardCard.class);
		
		cardList = selectRandomKingdomCards(cardList);
		
		for (String card : cardList.keySet()) {
			Class<? extends Card> cardClass = cardList.get(card);
			
			// If kingdom card is a victory cards, we only have 10
			if (!VictoryCard.class.isAssignableFrom(cardClass)) {
				numOfCards = 10;
			} else if (numberOfPlayers == 2) {
				numOfCards = 8;
			} else {
				numOfCards = 12;
			}
			
			// Build a stack of kingdom cards
			List<Card> cards = new LinkedList<Card>();
			for (int i=0; i<numOfCards; i++) {
				try {
					cards.add(cardClass.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			supplyStacks.put(card, cards);
		}		
	}
	
	/* Place 12 each of the Estate,
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
		
		List<Card> estateStack = new LinkedList<Card>();
		List<Card> duchyStack = new LinkedList<Card>();
		List<Card> provinceStack = new LinkedList<Card>();

		for (int i=0; i<numOfCards; i++) {
			estateStack.add(new EstateCard());
			duchyStack.add(new DuchyCard());
			provinceStack.add(new ProvinceCard());;			
		}		

		// When players are setup they start with 3 estates in their hand
		for (int i=0; i<(numberOfPlayers * 3); i++) {
			estateStack.add(new EstateCard());
		}
		
		supplyStacks.put(EstateCard.NAME, estateStack);
		supplyStacks.put(DuchyCard.NAME, duchyStack);
		supplyStacks.put(ProvinceCard.NAME, provinceStack);
	}

	/* Place 10 Curse cards in the Supply for a 2
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
		
		List<Card> curseStack = new LinkedList<Card>();		
		for (int i=0; i<numOfCards; i++) {
			curseStack.add(new CurseCard());
		}
		
		supplyStacks.put(CurseCard.NAME, curseStack);
	}

	/* Treasure cards are "unlimited", 50 should be enough
	 */
	private void setupTreasureCards() {		

		List<Card> copperStack = new LinkedList<Card>();
		List<Card> silverStack = new LinkedList<Card>();
		List<Card> goldStack = new LinkedList<Card>();

		for (int i=0; i<50; i++) {
			copperStack.add(new CopperCard());
			silverStack.add(new SilverCard());
			goldStack.add(new GoldCard());;			
		}
		
		supplyStacks.put(CopperCard.NAME, copperStack);
		supplyStacks.put(SilverCard.NAME, silverStack);
		supplyStacks.put(GoldCard.NAME, goldStack);
	}
	
	public void addToTrashPile(Card card) {
		trashPile.add(card);
	}

	public List<Card> getTrashPile() {
		return trashPile;
	}
	
	/**
	 * Build up a list of cards that the player can buy
	 * 
	 * @param amount of coins to buy with
	 * @return collection of cards
	 */
	public List<Card> listCardsToBuy(int amount) {
		List<Card> cards = new LinkedList<Card>();
		
		for (List<Card> cardStack : supplyStacks.values()) {	
			// Check if any cards are left in stack
			if (!cardStack.isEmpty()) {
				// Grab the first card on the stack
				Card card = cardStack.get(0);
				
				// Check if player can afford this card
				if (card.getCost() <= amount) {
					cards.add(card);
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
	public Card removeCardFromSupplyStack(String stack) {
		if (supplyStacks.get(stack).isEmpty()) {			
			return null;
		}
		return supplyStacks.get(stack).remove(0);
	}
}
