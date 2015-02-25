package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.actions.TorturerAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.AttackCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class TorturerCard extends Card implements ActionCard, AttackCard {
	public static final int COST = 5;	
	public static final String NAME = "Torturer";
	private final int PLUS_CARDS = 3;

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();		
		
		cardActions.add(new PlusCardAction(PLUS_CARDS));
		cardActions.add(new TorturerAction());
		
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