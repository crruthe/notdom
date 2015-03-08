package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.ChancellorAction;
import com.dominion.game.actions.PlusCoinAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class ChancellorCard extends Card implements ActionCard {
	public static final int COST = 3;
	public static final String NAME = "Chancellor";	
	private static final int PLUS_COINS = 2;

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new PlusCoinAction(PLUS_COINS));
		cardActions.add(new ChancellorAction());
		
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
