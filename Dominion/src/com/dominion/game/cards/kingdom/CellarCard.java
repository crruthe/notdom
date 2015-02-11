package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.CellarAction;
import com.dominion.game.actions.PlusActionAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class CellarCard extends Card implements ActionCard {
	public static final int COST = 2;
	public static final String NAME = "Cellar";
	private final int PLUS_ACTIONS = 1;

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusActionAction(PLUS_ACTIONS));
		cardActions.add(new CellarAction());
		
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
