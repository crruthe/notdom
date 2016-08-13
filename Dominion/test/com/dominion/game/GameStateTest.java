package com.dominion.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.AttackCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.cards.basic.ColonyCard;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.CurseCard;
import com.dominion.game.cards.basic.DuchyCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.cards.kingdom.AdventurerCard;
import com.dominion.game.cards.kingdom.BridgeCard;
import com.dominion.game.cards.kingdom.CellarCard;
import com.dominion.game.cards.kingdom.CourtyardCard;
import com.dominion.game.cards.kingdom.GardensCard;
import com.dominion.game.cards.kingdom.MilitiaCard;
import com.dominion.game.cards.kingdom.MineCard;
import com.dominion.game.cards.kingdom.MoatCard;
import com.dominion.game.cards.kingdom.SmithyCard;
import com.dominion.game.cards.kingdom.SpyCard;
import com.dominion.game.cards.kingdom.WitchCard;
import com.dominion.game.cards.kingdom.WoodcutterCard;

import junit.framework.TestCase;

public class GameStateTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCurrentPlayer() {
		GameState state = new GameState();
		Player player1 = new Player(new MockPlayerInterface());
		Player player2 = new Player(new MockPlayerInterface());
		Player player3 = new Player(new MockPlayerInterface());
		state.addPlayer(player1);
		state.addPlayer(player2);
		state.addPlayer(player3);
		
		assertEquals(player1, state.getCurrentPlayer());
	}

	public void testGetOtherPlayers() {
		GameState state = new GameState();
		Player player1 = new Player(new MockPlayerInterface());
		Player player2 = new Player(new MockPlayerInterface());
		Player player3 = new Player(new MockPlayerInterface());
		state.addPlayer(player1);
		state.addPlayer(player2);
		state.addPlayer(player3);
		
		LinkedList<Player> otherPlayers = new LinkedList<Player>();
		otherPlayers.add(player2);
		otherPlayers.add(player3);
		assertEquals(otherPlayers, state.getOtherPlayers());
	}

	public void testHasGameEndedThreeEmptyStacks() {
		GameState state = new GameState();
		state.gameBoard = new GameBoard();
		
		List<Class<? extends Card>> kingdomCards = new LinkedList<Class<? extends Card>>();
		kingdomCards.add(SmithyCard.class);
		kingdomCards.add(MineCard.class);
		kingdomCards.add(BridgeCard.class);
		kingdomCards.addAll(GameBoard.randomKingdoms(7));

		state.gameBoard.setup(kingdomCards, 2);
		
		assertFalse(state.hasGameEnded());
		
		for (int i = 0; i < 10; i++) {
			state.gameBoard.removeCardFromSupply(SmithyCard.class);
			state.gameBoard.removeCardFromSupply(MineCard.class);
			state.gameBoard.removeCardFromSupply(BridgeCard.class);
		}
		
		assertTrue(state.hasGameEnded());
	}
	
	public void testHasGameEndedNoProvinces() {
		GameState state = new GameState();
		state.initialise();
		
		assertFalse(state.hasGameEnded());
		
		for (int i = 0; i < 12; i++) {
			state.gameBoard.removeCardFromSupply(ProvinceCard.class);
		}
		
		assertTrue(state.hasGameEnded());
	}
	
	public void testHasGameEndedNoColonies() {
		GameState state = new GameState();
		state.gameBoard = new GameBoard();
		state.gameBoard.bigCardsUsed = true;
		
		List<Class<? extends Card>> kingdomCards = new LinkedList<Class<? extends Card>>();
		kingdomCards.addAll(GameBoard.randomKingdoms(10));

		state.gameBoard.setup(kingdomCards, 2);
		
		assertFalse(state.hasGameEnded());
		
		for (int i = 0; i < 12; i++) {
			state.gameBoard.removeCardFromSupply(ColonyCard.class);
		}
		
		assertTrue(state.hasGameEnded());
	}
	
	public void testHasGameEndedNotEnded() {
		GameState state = new GameState();
		state.gameBoard = new GameBoard();
		state.gameBoard.bigCardsUsed = true;
		
		List<Class<? extends Card>> kingdomCards = new LinkedList<Class<? extends Card>>();
		kingdomCards.add(SmithyCard.class);
		kingdomCards.add(MineCard.class);
		kingdomCards.add(BridgeCard.class);
		kingdomCards.addAll(GameBoard.randomKingdoms(7));

		state.gameBoard.setup(kingdomCards, 2);
		
		assertFalse(state.hasGameEnded());
		
		for (int i = 0; i < 7; i++) {
			state.gameBoard.removeCardFromSupply(SmithyCard.class);
			state.gameBoard.removeCardFromSupply(MineCard.class);
			state.gameBoard.removeCardFromSupply(BridgeCard.class);
			state.gameBoard.removeCardFromSupply(ProvinceCard.class);
		}
		
		assertFalse(state.hasGameEnded());
	}

	public void testInitialise() {
		GameState state = new GameState();
		Player player1 = new Player(new MockPlayerInterface());
		Player player2 = new Player(new MockPlayerInterface());
		Player player3 = new Player(new MockPlayerInterface());
		state.addPlayer(player1);
		state.addPlayer(player2);
		state.addPlayer(player3);

		state.initialise();
	}

	public void testRotatePlayers() {
		GameState state = new GameState();
		Player player1 = new Player(new MockPlayerInterface());
		Player player2 = new Player(new MockPlayerInterface());
		Player player3 = new Player(new MockPlayerInterface());
		state.addPlayer(player1);
		state.addPlayer(player2);
		state.addPlayer(player3);
		
		state.rotatePlayers();
		LinkedList<Player> otherPlayers;
		
		otherPlayers = new LinkedList<Player>();
		otherPlayers.add(player3);
		otherPlayers.add(player1);
		assertEquals(otherPlayers, state.getOtherPlayers());
		state.rotatePlayers();
		
		otherPlayers = new LinkedList<Player>();
		otherPlayers.add(player1);
		otherPlayers.add(player2);
		assertEquals(otherPlayers, state.getOtherPlayers());
	}
	
	public void testListCardsFilterByCost() {
		GameState state = new GameState();
		GameBoard gameBoard = new GameBoard();
		
		LinkedList<Class<? extends Card>> cardList = new LinkedList<Class<? extends Card>>();
		
		cardList.add(WoodcutterCard.class);
		cardList.add(WitchCard.class);		
		cardList.add(MoatCard.class);		
		cardList.add(GardensCard.class);		
		cardList.add(CellarCard.class);		
		cardList.add(MilitiaCard.class);	
		cardList.add(AdventurerCard.class);
		cardList.add(MoatCard.class);
		cardList.add(SpyCard.class);
		cardList.add(CourtyardCard.class);
		
		gameBoard.setup(cardList, 2);
		state.setGameBoard(gameBoard);		
		
		CardCollection cards = new CardCollection();
		
		cards.addCardToTop(new WoodcutterCard());
		cards.addCardToTop(new MoatCard());
		cards.addCardToTop(new MilitiaCard());
		cards.addCardToTop(new GardensCard());
		cards.addCardToTop(new CellarCard());
		cards.addCardToTop(new CourtyardCard());
		cards.addCardToTop(new CopperCard());
		cards.addCardToTop(new SilverCard());
		cards.addCardToTop(new EstateCard());
		cards.addCardToTop(new CurseCard());
		cards.addCardToTop(new SpyCard());
		
		List<Card> expectedCards = cards.getCards();
		List<Card> actualCards = state.listCardsFilterByCost(4);
		
		Collections.sort(expectedCards);
		Collections.sort(actualCards);
		
		assertEquals(expectedCards, actualCards);
	}

	public void testListCardsFilterByClassAndCost() {
		GameState state = new GameState();
		GameBoard gameBoard = new GameBoard();
		
		LinkedList<Class<? extends Card>> cardList = new LinkedList<Class<? extends Card>>();
		
		cardList.add(WoodcutterCard.class);
		cardList.add(WitchCard.class);		
		cardList.add(MoatCard.class);		
		cardList.add(GardensCard.class);		
		cardList.add(CellarCard.class);		
		cardList.add(MilitiaCard.class);	
		cardList.add(AdventurerCard.class);
		cardList.add(MoatCard.class);
		cardList.add(SpyCard.class);
		cardList.add(CourtyardCard.class);
		
		gameBoard.setup(cardList, 2);
		state.setGameBoard(gameBoard);	
		
		List<Card> actualCards;
		CardCollection cards;
		
		cards = new CardCollection();		
		cards.addCardToTop(new GardensCard());
		cards.addCardToTop(new EstateCard());
		cards.addCardToTop(new DuchyCard());
		
		List<Card> victoryCards = cards.getCards();
		actualCards = state.listCardsFilterByClassAndCost(VictoryCard.class,6);
		
		Collections.sort(victoryCards);
		Collections.sort(actualCards);
		
		assertEquals(victoryCards, actualCards);		

		cards = new CardCollection();		
		cards.addCardToTop(new CopperCard());
		cards.addCardToTop(new SilverCard());
		
		List<Card> treasureCards = cards.getCards();
		actualCards = state.listCardsFilterByClassAndCost(TreasureCard.class,3);
		
		Collections.sort(treasureCards);
		Collections.sort(actualCards);
		
		assertEquals(treasureCards, actualCards);		

		cards = new CardCollection();		
		cards.addCardToTop(new WoodcutterCard());
		cards.addCardToTop(new MoatCard());
		cards.addCardToTop(new MilitiaCard());
		cards.addCardToTop(new CellarCard());
		cards.addCardToTop(new CourtyardCard());
		cards.addCardToTop(new SpyCard());
		
		List<Card> actionCards = cards.getCards();
		actualCards = state.listCardsFilterByClassAndCost(ActionCard.class,4);
		
		Collections.sort(actionCards);
		Collections.sort(actualCards);
		
		assertEquals(actionCards, actualCards);		

		cards = new CardCollection();		
		cards.addCardToTop(new MilitiaCard());
		cards.addCardToTop(new WitchCard());
		cards.addCardToTop(new SpyCard());
		
		List<Card> attackCards = cards.getCards();
		actualCards = state.listCardsFilterByClassAndCost(AttackCard.class,5);
		
		Collections.sort(attackCards);
		Collections.sort(actualCards);
		
		assertEquals(attackCards, actualCards);		

		cards = new CardCollection();		
		cards.addCardToTop(new MoatCard());
		
		List<Card> reactionCards = cards.getCards();
		actualCards = state.listCardsFilterByClassAndCost(ReactionCard.class,3);
		
		Collections.sort(reactionCards);
		Collections.sort(actualCards);
		
		assertEquals(reactionCards, actualCards);		

		cards = new CardCollection();		
		
		List<Card> noCards = cards.getCards();
		actualCards = state.listCardsFilterByClassAndCost(VictoryCard.class,1);
		
		Collections.sort(noCards);
		Collections.sort(actualCards);
		
		assertEquals(noCards, actualCards);		
	}	
}
