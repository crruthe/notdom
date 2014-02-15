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
	 * @return the estateStackSize
	 */
	public int getEstateStackSize() {
		return estateStackSize;
	}

	/**
	 * @return the duchyStackSize
	 */
	public int getDuchyStackSize() {
		return duchyStackSize;
	}

	/**
	 * @return the provinceStackSize
	 */
	public int getProvinceStackSize() {
		return provinceStackSize;
	}

	/**
	 * @return the curseStackSize
	 */
	public int getCurseStackSize() {
		return curseStackSize;
	}

	/**
	 * @return the kingdomCards
	 */
	public HashMap<String, Integer> getKingdomCards() {
		return kingdomCards;
	}

	private int estateStackSize;
	private int duchyStackSize;
	private int provinceStackSize;
	
	private int curseStackSize;
	
	private final LinkedList<Card> trashPile = new LinkedList<Card>();
	
	private final HashMap<String, Integer> kingdomCards = new HashMap<String, Integer>();
	
	public void setup(int numberOfPlayers) {
		setupKingdomCards(numberOfPlayers);
		setupUpVictoryCards(numberOfPlayers);
		setupUpCurseCards(numberOfPlayers);
	}
	
	private List<Class<? extends Card>> selectRandomKingdomCards(List<Class<? extends Card>> cardClassList) {
		Collections.shuffle(cardClassList);
		return cardClassList.subList(0, 10);
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
		
		if (numberOfPlayers == 2) {
			numOfCards = 8;
		} else {
			numOfCards = 12;
		}
		
		List<Class<? extends Card>> cardClassList = new LinkedList<Class<? extends Card>>();
		
		cardClassList.add(WoodcutterCard.class);
		cardClassList.add(VillageCard.class);
		cardClassList.add(WorkshopCard.class);
		cardClassList.add(SmithyCard.class);
		cardClassList.add(CouncilRoomCard.class);		
		cardClassList.add(LaboratoryCard.class);		
		cardClassList.add(FestivalCard.class);		
		cardClassList.add(MarketCard.class);		
		cardClassList.add(WitchCard.class);		
		cardClassList.add(MoatCard.class);		
		cardClassList.add(GardensCard.class);		
		cardClassList.add(CellarCard.class);		
		cardClassList.add(ChapelCard.class);		
		cardClassList.add(FeastCard.class);		
		cardClassList.add(LibraryCard.class);		
		cardClassList.add(ChancellorCard.class);		
		cardClassList.add(MilitiaCard.class);		
		cardClassList.add(MineCard.class);		
		cardClassList.add(AdventurerCard.class);		
		cardClassList.add(RemodelCard.class);		
		cardClassList.add(BureaucratCard.class);		
		cardClassList.add(ThroneRoomCard.class);		
				
/*		cardClassList.add(ChapelCard.class);
		cardClassList.add(MoatCard.class);
		cardClassList.add(ChancellorCard.class);
*/		
		cardClassList = selectRandomKingdomCards(cardClassList);
		
		for (Class<? extends Card> cardClass : cardClassList) {			
			if (!VictoryCard.class.isAssignableFrom(cardClass)) {
				numOfCards = 10;
			} else if (numberOfPlayers == 2) {
				numOfCards = 8;
			} else {
				numOfCards = 12;
			}
			
			kingdomCards.put(cardClass.getName(), numOfCards);
		}		
	}
	
	/* Place 12 each of the Estate,
	 * Duchy, and Province cards in face-up piles
	 * in the Supply in a 3 or 4 player game.
	 * In a 2 player game, place only 8 of each
	 * of these Victory cards in the Supply.
	 */
	private void setupUpVictoryCards(int numberOfPlayers) {
		int numOfCards = 12;
		
		if (numberOfPlayers == 2) {
			numOfCards = 8;
		}
		
		estateStackSize = numOfCards;
		duchyStackSize = numOfCards;
		provinceStackSize = numOfCards;
	}

	/* Place 10 Curse cards in the Supply for a 2
	 * player game, 20 Curse cards for 3 players,
	 * and 30 Curse cards for 4 players.
	 */
	private void setupUpCurseCards(int numberOfPlayers) {		
		int numOfCards = 10;
		
		if (numberOfPlayers == 3) {
			numOfCards = 20;
		} else if (numberOfPlayers == 4) {
			numOfCards = 30;
		}
		
		curseStackSize = numOfCards;
	}
	
	/**
	 * @return copper card from infinite copper stack
	 */
	public Card getCopperCard() {
		return new CopperCard();
	}

	/**
	 * @return silver card from infinite silver stack
	 */
	public Card getSilverCard() {
		return new SilverCard();
	}

	/**
	 * @return gold card from infinite gold stack
	 */
	public Card getGoldCard() {
		return new GoldCard();
	}
	
	/**
	 * @return estate card from estate stack
	 */
	public Card getEstateCard() {
		if (estateStackSize > 0) {
			estateStackSize--;
			return new EstateCard();
		}
		return null;
	}

	/**
	 * @return duchy card from duchy stack
	 */	
	public Card getDuchyCard() {
		if (duchyStackSize > 0) {
			duchyStackSize--;
			return new DuchyCard();
		}
		return null;
	}
	
	/**
	 * @return province card from province stack
	 */	
	public Card getProvinceCard() {
		if (provinceStackSize > 0) {
			provinceStackSize--;
			return new ProvinceCard();
		}
		return null;
	}

	/**
	 * @return curse card from curse stack
	 */	
	public Card getCurseCard() {
		if (curseStackSize > 0) {
			curseStackSize--;
			return new CurseCard();
		}
		return null;
	}
	
	public void addToTrashPile(Card card) {
		trashPile.add(card);
	}

	public List<Card> getTrashPile() {
		return trashPile;
	}

	public Card getKingdomCard(String cardType) {
		Integer numCardLeft = kingdomCards.get(cardType);
		if (numCardLeft == null) {
			throw new RuntimeException("kingdom card does not exist");
		}
		
		if (numCardLeft > 0) {
			try {
				kingdomCards.put(cardType, Integer.valueOf(numCardLeft.intValue() - 1));
				return (Card)Class.forName(cardType).newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (ClassNotFoundException e) {				
				throw new RuntimeException(e.getMessage(), e);
			}
		}		
		
		return null;
	}
}
