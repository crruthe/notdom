package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.CoppersmithAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class CoppersmithCard extends Card implements ActionCard {
	public static final int COST = 4;
	public static final String NAME = "Coppersmith";

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new CoppersmithAction());
		
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
