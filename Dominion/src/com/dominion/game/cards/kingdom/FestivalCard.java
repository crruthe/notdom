package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusActionAction;
import com.dominion.game.actions.PlusBuyAction;
import com.dominion.game.actions.PlusCoinAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class FestivalCard implements Card, ActionCard {
	public static final int COST = 5;
	public static final String NAME = "Festival";
	private final int PLUS_ACTIONS = 2;
	private final int PLUS_BUYS = 1;
	private final int PLUS_COINS = 2;
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusActionAction(PLUS_ACTIONS));
		cardActions.add(new PlusBuyAction(PLUS_BUYS));
		cardActions.add(new PlusCoinAction(PLUS_COINS));
		
		return cardActions;
	}

	@Override
	public boolean equals(Object obj) {
		// Cards are the same if names match (even if different instances)
		return NAME == ((Card)obj).getName();
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
