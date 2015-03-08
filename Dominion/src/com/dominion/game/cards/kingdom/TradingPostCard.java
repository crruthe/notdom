package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.TradingPostAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class TradingPostCard extends Card implements ActionCard {
	public static final int COST = 5;
	public static final String NAME = "TradingPost";
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new TradingPostAction());
		
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
