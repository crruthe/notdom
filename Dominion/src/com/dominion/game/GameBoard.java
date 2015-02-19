package com.dominion.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import com.dominion.game.cards.*;
import com.dominion.game.cards.basic.*;
import com.dominion.game.cards.kingdom.*;
import com.dominion.game.observers.SupplyStackObserver;
import com.dominion.game.observers.TrashPileObserver;

public class GameBoard {

	private SupplyStack supply = new SupplyStack();
	private CardCollection trashPile = new CardCollection();	
	
	public void registerObservers(GameState state) {
		supply.addObserver(new SupplyStackObserver(state));
		trashPile.addObserver(new TrashPileObserver(state));
	}
	
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
		for (Integer numOfCards: supply.getStacks().values()) {
			if (numOfCards == 0) {
				numOfEmptyStacks++;
			}
		}
		return numOfEmptyStacks;
	}
	
	public HashMap<Class<? extends Card>, Integer> getSupplyStacks() {
		return supply.getStacks();
	}
	
	public CardCollection getTrashPile() {
		return trashPile;
	}
	
	public boolean isStackEmpty(Class<? extends Card> cardName) {
		return supply.isStackEmpty(cardName);
	}


	public void setup(List<Class<? extends Card>> kingdomCards, int numberOfPlayers) {
		setupKingdomCards(kingdomCards, numberOfPlayers);
		setupVictoryCards(numberOfPlayers);
		setupCurseCards(numberOfPlayers);
		setupTreasureCards();
	}

	public void setupRandom(int numberOfPlayers) {
		List<Class<? extends Card>> kingdomCards = randomKingdoms();
		setup(kingdomCards, numberOfPlayers);
	}
		
	public Card removeCardFromSupply(Class<? extends Card> cardClass) {
		if (supply.removeCard(cardClass)) {
			return Card.getCard(cardClass);
		} else {
			return null;
		}		
	}

	
	/**
	 * Generate a random set of kingdom cards
	 * @param numberOfPlayers
	 */
	private List<Class<? extends Card>> randomKingdoms() {
		LinkedList<Class<? extends Card>> cardList = new LinkedList<Class<? extends Card>>();
		
		/* 
		 * Base  
		 */
		
		// 2: Cellar, Chapel, Moat 
		cardList.add(CellarCard.class);		
		cardList.add(ChapelCard.class);		
		cardList.add(MoatCard.class);

		// 3: Chancellor, Village, Woodcutter, Workshop
		cardList.add(ChancellorCard.class);		
		cardList.add(VillageCard.class);
		cardList.add(WoodcutterCard.class);
		cardList.add(WorkshopCard.class);

		// 4: Bureaucrat, Feast, Gardens, Militia, Moneylender, Remodel, Smithy, Spy, Thief, Throne Room
		cardList.add(BureaucratCard.class);	
		cardList.add(FeastCard.class);		
		cardList.add(GardensCard.class);
		cardList.add(MilitiaCard.class);	
		cardList.add(MoneylenderCard.class);
		cardList.add(RemodelCard.class);	
		cardList.add(SmithyCard.class);
		cardList.add(SpyCard.class);		
		cardList.add(ThiefCard.class);
		cardList.add(ThroneRoomCard.class);						

		// 5: Council Room, Festival, Laboratory, Library, Market, Mine, Witch
		cardList.add(CouncilRoomCard.class);				
		cardList.add(FestivalCard.class);
		cardList.add(LaboratoryCard.class);		
		cardList.add(LibraryCard.class);
		cardList.add(MarketCard.class);		
		cardList.add(MineCard.class);	
		cardList.add(WitchCard.class);		

		// 6: Adventurer
		cardList.add(AdventurerCard.class);

		/*
		 * Intrigue
		 */
		
		// 2: Courtyard, Pawn, Secret Chamber 
		cardList.add(CourtyardCard.class);
		cardList.add(PawnCard.class);
		
		// 3: Great Hall, Masquerade, Shanty Town, Steward, Swindler, Wishing Well 
		cardList.add(GreatHallCard.class);
		cardList.add(MasqueradeCard.class);

		// 4: Baron, Bridge, Conspirator, Coppersmith, Ironworks, Mining Village, Scout 
		cardList.add(BridgeCard.class);
		
		// 5: Duke, Minion, Saboteur, Torturer, Trading Post, Tribute, Upgrade 
		cardList.add(TributeCard.class);
		cardList.add(UpgradeCard.class);

		// 6: Harem, Nobles

		
		
		Collections.shuffle(cardList);		
		
		return cardList.subList(0, 10);
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
		
		supply.addToStack(CurseCard.class, numOfCards);
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
	private void setupKingdomCards(List<Class<? extends Card>> kingdomCards, int numberOfPlayers) {
		int numOfCards;		
		
		for (Class<? extends Card> cardClass: kingdomCards) {
			
			// If kingdom card is a victory cards, we only have 10
			if (!VictoryCard.class.isAssignableFrom(cardClass)) {
				numOfCards = 10;
			} else if (numberOfPlayers == 2) {
				numOfCards = 8;
			} else {
				numOfCards = 12;
			}
			
			supply.addToStack(cardClass, numOfCards);
		}		
	}
	
	/**
	 * Game supply has "unlimited" cards. 50 should be enough.
	 * @param numberOfPlayers
	 */
	private void setupTreasureCards() {
		supply.addToStack(CopperCard.class, 50);
		supply.addToStack(SilverCard.class, 50);
		supply.addToStack(GoldCard.class, 50);
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
		
		supply.addToStack(EstateCard.class, numOfCards);
		supply.addToStack(DuchyCard.class, numOfCards);
		supply.addToStack(ProvinceCard.class, numOfCards);
	}	
}
