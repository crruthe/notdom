package com.dominion.game;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.RemodelAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.cards.kingdom.AdventurerCard;
import com.dominion.game.cards.kingdom.RemodelCard;
import com.dominion.game.cards.kingdom.WitchCard;

import junit.framework.TestCase;

public class RemodelActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testMustTrashIfHaveCard() {		
		GameState state = new GameState();
		state.initialise();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			int attempts = 0;
			
			@Override
			public Card selectCardToTrashFromHand(List<Card> cards) {
				if (attempts < 3) {
					attempts += 1;
					return null;
				}
				return cards.get(cards.indexOf(new SilverCard()));
			}
			
			@Override
			public Card selectCardToBuy(List<Card> cards) {
				return cards.get(0);
			}
		};
		
		Player player = new Player(playerInterface);		
		state.addPlayer(player);
		
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		player.drawNewHand();
		
		CardAction action = new RemodelAction();		
		action.execute(state);		
		
		assertEquals(new SilverCard(), state.getGameBoard().getTrashPile().getTopCard());
		assertNotNull(playerInterface.getDiscardCard());
	}
	
	public void testCanSkipIfNoCardsInHand() {
		GameState state = new GameState();
		state.initialise();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			int attempts = 0;
			
			@Override
			public Card selectCardToTrashFromHand(List<Card> cards) {
				if (attempts < 3) {
					attempts += 1;
					return null;
				}
				fail("Attempt to trash card with no cards failed");
				return null;
			}
			
			@Override
			public Card selectCardToBuy(List<Card> cards) {
				fail("Allowed to buy even though no trashed card");
				return null;
			}
		};
		
		Player player = new Player(playerInterface);		
		state.addPlayer(player);
				
		CardAction action = new RemodelAction();		
		action.execute(state);		
		
		assertNull(state.getGameBoard().getTrashPile().getTopCard());
		assertNull(playerInterface.getDiscardCard());
	}
	
	public void testMustGainACard() {
		GameState state = new GameState();
		state.initialise();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			int attempts = 0;
			
			@Override
			public Card selectCardToTrashFromHand(List<Card> cards) {
				return cards.get(cards.indexOf(new SilverCard()));
			}
			
			@Override
			public Card selectCardToBuy(List<Card> cards) {
				if (attempts < 3) {
					attempts += 1;
					return null;
				}
				return cards.get(0);
			}
		};
		
		Player player = new Player(playerInterface);		
		state.addPlayer(player);

		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		player.drawNewHand();
				
		CardAction action = new RemodelAction();		
		action.execute(state);		
		
		assertEquals(new SilverCard(), state.getGameBoard().getTrashPile().getTopCard());
		assertNotNull(playerInterface.getDiscardCard());
	}

	public void testExecute() {
		GameState state = new GameState();
		GameBoard gameBoard = new GameBoard();
		List<Class<? extends Card>> kingdomCards = new LinkedList<Class<? extends Card>>();
		kingdomCards.add(RemodelCard.class);
		kingdomCards.add(AdventurerCard.class);
		kingdomCards.add(WitchCard.class);
		gameBoard.setup(kingdomCards, 2);
		state.setGameBoard(gameBoard);
		state.initialise();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			@Override
			public Card selectCardToTrashFromHand(List<Card> cards) {
				return cards.get(cards.indexOf(new SilverCard()));
			}
			
			@Override
			public Card selectCardToBuy(List<Card> cards) {
				assertTrue(cards.contains(new RemodelCard()));
				assertTrue(cards.contains(new WitchCard()));
				assertFalse(cards.contains(new AdventurerCard()));
				return cards.get(cards.indexOf(new WitchCard()));
			}
		};
		
		Player player = new Player(playerInterface);
		
		state.addPlayer(player);
		
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		player.drawNewHand();
		
		CardAction action = new RemodelAction();		
		action.execute(state);		
		
		assertEquals(new SilverCard(), state.getGameBoard().getTrashPile().getTopCard());
		assertEquals(new WitchCard(), playerInterface.getDiscardCard());
	}
}
