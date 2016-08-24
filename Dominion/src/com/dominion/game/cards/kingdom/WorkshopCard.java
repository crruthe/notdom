package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.GainCardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class WorkshopCard extends Card implements ActionCard {
	
	private static final int GAIN_COST = 4;

	public WorkshopCard() {
		super("Workshop", 3);
	}

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new GainCardAction(GAIN_COST, true));
		
		return cardActions;
	}
}
