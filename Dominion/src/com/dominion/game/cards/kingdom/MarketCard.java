package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusActionAction;
import com.dominion.game.actions.PlusBuyAction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.actions.PlusCoinAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class MarketCard implements Card, ActionCard {

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getDescription() {
		return "Market";
	}

	public static final int COST = 5;
	
	@Override
	public int getCost() {
		return COST;
	}

	private final int PLUS_CARDS = 1;
	private final int PLUS_ACTIONS = 1;
	private final int PLUS_BUYS = 1;
	private final int PLUS_COINS = 1;
	
	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusCardAction(PLUS_CARDS));
		cardActions.add(new PlusActionAction(PLUS_ACTIONS));
		cardActions.add(new PlusBuyAction(PLUS_BUYS));
		cardActions.add(new PlusCoinAction(PLUS_COINS));
		
		return cardActions;
	}
}
