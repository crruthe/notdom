package com.dominion.game.actions;

import com.dominion.game.GameState;

/**
 * @author user
 *
 * +1 coin per token on the Trade Route mat.
 */
public class TradeRouteAction implements CardAction {
	@Override
	public void execute(GameState state) {
		int tradeRouteTokens = state.getGameBoard().getTradeRouteMatTokens();
		
		new PlusCoinAction(tradeRouteTokens).execute(state);
	}
}
