package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusBuyAction;
import com.dominion.game.actions.PlusCoinAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.visitors.CardVisitor;

public class WoodcutterCard implements ActionCard {
	@Override
	public boolean equals(Object obj) {
		// Only really card if card types match
		return this.getClass().isInstance(obj);
	}
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getDescription() {
		return "Wood Cutter";
	}

	public static final int COST = 3;
	
	@Override
	public int getCost() {
		return COST;
	}

	private final int PLUS_BUYS = 1;
	private final int PLUS_COINS = 2;
	
	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusBuyAction(PLUS_BUYS));
		cardActions.add(new PlusCoinAction(PLUS_COINS));
		
		return cardActions;
	}
}
