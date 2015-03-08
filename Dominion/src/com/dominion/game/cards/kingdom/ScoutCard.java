package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusActionAction;
import com.dominion.game.actions.ScoutAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class ScoutCard extends Card implements ActionCard {
	public static final int COST = 4;
	public static final String NAME = "Scout";
	private static final int PLUS_ACTIONS = 1;

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new PlusActionAction(PLUS_ACTIONS));
		cardActions.add(new ScoutAction());
		
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
