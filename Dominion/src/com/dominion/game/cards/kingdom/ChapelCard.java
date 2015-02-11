package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.ChapelAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class ChapelCard extends Card implements ActionCard {
	public static final int COST = 2;
	public static final String NAME = "Chapel";
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new ChapelAction());
		
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
