package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusActionAction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.visitors.CardVisitor;


public class GreatHallCard extends Card implements VictoryCard, ActionCard {	
	public static final int COST = 3;
	public static final String NAME = "GreatHall";
	public static final int POINTS = 1;
	private final int PLUS_ACTIONS = 1;
	private final int PLUS_CARDS = 1;
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit((VictoryCard)this);
		visitor.visit((ActionCard)this);
	}

	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusCardAction(PLUS_CARDS));
		cardActions.add(new PlusActionAction(PLUS_ACTIONS));
		
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

	@Override
	public int getVictoryPoints() {
		return POINTS;
	}	
}