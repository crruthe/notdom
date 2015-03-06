package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.BaronAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusBuyAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class BaronCard extends Card implements ActionCard {
	public static final int COST = 4;
	public static final String NAME = "Baron";
	private static final int PLUS_BUYS = 1;
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusBuyAction(PLUS_BUYS));
		cardActions.add(new BaronAction());
		
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
