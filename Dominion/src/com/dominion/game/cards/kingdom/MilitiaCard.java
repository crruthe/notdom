package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.MilitiaAction;
import com.dominion.game.actions.PlusCoinAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.AttackCard;
import com.dominion.game.visitors.CardVisitor;

public class MilitiaCard implements ActionCard, AttackCard {

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getDescription() {
		return "Militia";
	}

	public static final int COST = 4;
	
	@Override
	public int getCost() {
		return COST;
	}

	private final int PLUS_COINS = 1;
	
	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusCoinAction(PLUS_COINS));
		cardActions.add(new MilitiaAction());
		
		return cardActions;
	}
}
