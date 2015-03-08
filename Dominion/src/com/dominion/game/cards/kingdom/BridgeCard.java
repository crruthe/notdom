package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.BridgeAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusBuyAction;
import com.dominion.game.actions.PlusCoinAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class BridgeCard extends Card implements ActionCard {
	public static final int COST = 4;
	public static final String NAME = "Bridge";
	private static final int PLUS_BUYS = 1;
	private static final int PLUS_COINS = 1;

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new PlusBuyAction(PLUS_BUYS));
		cardActions.add(new PlusCoinAction(PLUS_COINS));
		cardActions.add(new BridgeAction());
		
		return cardActions;
	}

	@Override
	public int getCost() {
		return COST;
	}
	
	@Override
	public String getName() {
		return NAME;
	}
}
