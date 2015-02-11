package com.dominion.game;

import java.util.List;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.GoldCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.cards.kingdom.CourtyardCard;
import junit.framework.TestCase;

public class CourtyardCardTest extends TestCase {

	private Player player;
	CourtyardMockPlayerInterface mockPlayerInterface;
	
	class CourtyardMockPlayerInterface extends MockPlayerInterface {
		private int count = 0;
		
		@Override
		public Card selectCardToPutOnDeck(List<Card> cards) {
			
			// After three tries, give up and return anything
			if (count >= 3) {
				return cards.get(0);
			}
			count++;
			
			for (Card c: cards) {
				if (c instanceof GoldCard) {
					return c;
				}
			}
			return null;
		}

		public int getCount() {
			return count;
		}		
	}

	protected void setUp() throws Exception {
		mockPlayerInterface =  new CourtyardMockPlayerInterface();
		player = new Player(mockPlayerInterface);
	}
	
	public void testDrawThreeCardsAndReturnToTopOfDeck() {		
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new GoldCard());
		player.playActionCard(new CourtyardCard());
		
		assertTrue(mockPlayerInterface.getCardHand().contains(new SilverCard()));
		assertTrue(mockPlayerInterface.getCardHand().contains(new CopperCard()));
		assertFalse(mockPlayerInterface.getCardHand().contains(new EstateCard()));

		Card card = player.drawCard();
		assertEquals("Courtyard failed to return to top of deck", GoldCard.class, card.getClass());
	}
	
	public void testCannotSelectNoCardToReturn() {		
		player.addCardToCardDeck(new EstateCard());
		player.addCardToCardDeck(new CopperCard());
		player.addCardToCardDeck(new SilverCard());
		player.addCardToCardDeck(new ProvinceCard());
		player.playActionCard(new CourtyardCard());
		Card card = player.drawCard();		
		assertEquals("Courtyard allows no cards to be put on the top of deck", 3, mockPlayerInterface.getCount());
	}
	
	public void testCostIsTwo() {
		assertEquals(2, CourtyardCard.COST);
	}
}
