package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusActionAction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.actions.SpyAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.AttackCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class SpyCard extends Card implements ActionCard, AttackCard {
	public static final int COST = 4;	
	public static final String NAME = "Spy";
	private static final int PLUS_ACTIONS = 1;
	private static final int PLUS_CARDS = 1;

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new PlusCardAction(PLUS_CARDS));
		cardActions.add(new PlusActionAction(PLUS_ACTIONS));
		cardActions.add(new SpyAction());
		
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
