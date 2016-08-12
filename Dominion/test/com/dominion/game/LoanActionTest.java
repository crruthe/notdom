package com.dominion.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.LoanAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.DuchyCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.cards.kingdom.WoodcutterCard;

import junit.framework.TestCase;

public class LoanActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExecuteShuffleDeckNoTreasure() {
		GameState state = new GameState();
		state.initialise();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			@Override
			public String selectCardActionToPlay(final HashMap<String, CardAction> actions) {
				return "Trash Card";
			}
		};
		
		Player player = new Player(playerInterface);
		
		state.addPlayer(player);
		
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new EstateCard());
		
		player.addCardToDiscardPile(new DuchyCard());
		player.addCardToDiscardPile(new DuchyCard());
		player.addCardToDiscardPile(new DuchyCard());
				
		CardAction action = new LoanAction();		
		action.execute(state);	
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new EstateCard());
		cards.add(new EstateCard());
		cards.add(new EstateCard());
		cards.add(new DuchyCard());
		cards.add(new DuchyCard());
		cards.add(new DuchyCard());
		
		assertEquals(cards, player.discardPile.cards);
		assertEquals(0, player.cardDeck.count());
		assertEquals(0, player.playArea.count());
	}
	
	public void testExecuteShuffleDeckTrashTreasure() {
		GameState state = new GameState();
		state.initialise();
		
		MockPlayerInterface playerInterface = new MockPlayerInterface() {
			@Override
			public String selectCardActionToPlay(final HashMap<String, CardAction> actions) {
				return "Trash Card";
			}
		};
		
		Player player = new Player(playerInterface);
		
		state.addPlayer(player);
		
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new EstateCard());
		
		player.addCardToDiscardPile(new CopperCard());
		player.addCardToDiscardPile(new CopperCard());
		player.addCardToDiscardPile(new CopperCard());
				
		CardAction action = new LoanAction();		
		action.execute(state);	
		
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.add(new EstateCard());
		cards.add(new EstateCard());
		cards.add(new EstateCard());
		
		LinkedList<Card> deckCards = new LinkedList<Card>();
		deckCards.add(new CopperCard());
		deckCards.add(new CopperCard());
		
		assertEquals(cards, player.discardPile.cards);
		assertEquals(deckCards, player.cardDeck.cards);
		assertEquals(0, player.playArea.count());
		assertEquals(1, state.gameBoard.trashPile.count());
	}
}
