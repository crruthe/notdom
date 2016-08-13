package com.dominion.game;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import com.dominion.game.actions.TradeRouteAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.DuchyCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.cards.kingdom.BridgeCard;
import com.dominion.game.cards.kingdom.GardensCard;
import com.dominion.game.cards.kingdom.HaremCard;

public class TradeRouteActionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testExecuteNoTradeRouteTokens() {
		GameState state = new GameState();
		state.initialise();

		assertEquals(0, state.getGameBoard().getTradeRouteMatTokens());
		new TradeRouteAction().execute(state);
		assertEquals(0, state.getTurnState().getTotalCoins());
		
		state.getGameBoard().removeCardFromSupply(CopperCard.class);
	}
	
	public void testExecuteTradeRouteTokens() {
		GameState state = new GameState();
		state.initialise();

		assertEquals(0, state.getGameBoard().getTradeRouteMatTokens());
		new TradeRouteAction().execute(state);
		assertEquals(0, state.getTurnState().getTotalCoins());
		
		state.getGameBoard().removeCardFromSupply(ProvinceCard.class);
		assertEquals(1, state.getGameBoard().getTradeRouteMatTokens());
		new TradeRouteAction().execute(state);
		assertEquals(1, state.getTurnState().getTotalCoins());

		state.getGameBoard().removeCardFromSupply(ProvinceCard.class);
		assertEquals(1, state.getGameBoard().getTradeRouteMatTokens());

		state.getGameBoard().removeCardFromSupply(DuchyCard.class);
		assertEquals(2, state.getGameBoard().getTradeRouteMatTokens());
		new TradeRouteAction().execute(state);
		assertEquals(3, state.getTurnState().getTotalCoins());

		state.getGameBoard().removeCardFromSupply(ProvinceCard.class);
		assertEquals(2, state.getGameBoard().getTradeRouteMatTokens());
	}

	public void testExecuteTradeRouteTokensFromKingdomCards() {
		GameState state = new GameState();
		state.gameBoard = new GameBoard();
		
		List<Class<? extends Card>> kingdomCards = new LinkedList<Class<? extends Card>>();
		kingdomCards.add(HaremCard.class);
		kingdomCards.add(GardensCard.class);
		kingdomCards.add(BridgeCard.class);
		kingdomCards.addAll(GameBoard.randomKingdoms(7));

		state.gameBoard.setup(kingdomCards, 2);

		assertEquals(0, state.getGameBoard().getTradeRouteMatTokens());
		new TradeRouteAction().execute(state);
		assertEquals(0, state.getTurnState().getTotalCoins());
		
		state.getGameBoard().removeCardFromSupply(ProvinceCard.class);
		assertEquals(1, state.getGameBoard().getTradeRouteMatTokens());
		new TradeRouteAction().execute(state);
		assertEquals(1, state.getTurnState().getTotalCoins());

		state.getGameBoard().removeCardFromSupply(DuchyCard.class);
		assertEquals(2, state.getGameBoard().getTradeRouteMatTokens());
		new TradeRouteAction().execute(state);
		assertEquals(3, state.getTurnState().getTotalCoins());
		
		state.getGameBoard().removeCardFromSupply(GardensCard.class);
		assertEquals(3, state.getGameBoard().getTradeRouteMatTokens());
		
		state.getGameBoard().removeCardFromSupply(GardensCard.class);
		assertEquals(3, state.getGameBoard().getTradeRouteMatTokens());

		state.getGameBoard().removeCardFromSupply(DuchyCard.class);
		assertEquals(3, state.getGameBoard().getTradeRouteMatTokens());

		state.getGameBoard().removeCardFromSupply(HaremCard.class);
		assertEquals(4, state.getGameBoard().getTradeRouteMatTokens());
	}
}
