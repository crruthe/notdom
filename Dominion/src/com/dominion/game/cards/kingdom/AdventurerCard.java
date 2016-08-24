package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.AdventurerAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class AdventurerCard extends Card implements ActionCard {
	
	public AdventurerCard() {
		super("Adventurer", 6);
	}

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new AdventurerAction());
		
		return cardActions;
	}
}
