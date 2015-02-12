package com.dominion.game;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusActionAction;
import com.dominion.game.actions.PlusBuyAction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.actions.PlusCoinAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.GoldCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.cards.kingdom.CourtyardCard;
import com.dominion.game.cards.kingdom.PawnCard;
import com.dominion.game.interfaces.PlayerInterface;

import junit.framework.TestCase;

public class PawnCardTest extends TestCase {

	protected void setUp() throws Exception {
	}
	
	public void testOneActionOneCard() {		
		MockPlayerInterface mockPlayerInterface = new MockPlayerInterface() {
			@Override
			public CardAction selectCardActionToPlay(List<CardAction> actions) {
				for (CardAction action: actions) {
					if (action instanceof PlusActionAction || action instanceof PlusCardAction) {
						return action;
					}
				}
				return null;
			}			
		};		
		Player player = new Player(mockPlayerInterface);
		
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		assertTrue(mockPlayerInterface.getCardHand().contains(new ProvinceCard()));
		assertEquals(1, mockPlayerInterface.getNumOfActions());		
		assertEquals(1, mockPlayerInterface.getNumOfBuys());		
		assertEquals(0, mockPlayerInterface.getNumOfCoins());		
	}
	
	public void testOneCoinOneBuy() {		
		MockPlayerInterface mockPlayerInterface = new MockPlayerInterface() {
			@Override
			public CardAction selectCardActionToPlay(List<CardAction> actions) {
				for (CardAction action: actions) {
					if (action instanceof PlusCoinAction || action instanceof PlusBuyAction) {
						return action;
					}
				}
				return null;
			}			
		};		
		Player player = new Player(mockPlayerInterface);
		
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new ProvinceCard());
		
		assertTrue(mockPlayerInterface.getCardHand().isEmpty());
		assertEquals(0, mockPlayerInterface.getNumOfActions());		
		assertEquals(2, mockPlayerInterface.getNumOfBuys());		
		assertEquals(1, mockPlayerInterface.getNumOfCoins());		
	}
	
	public void testCostIsTwo() {
		assertEquals(2, PawnCard.COST);
	}
}
