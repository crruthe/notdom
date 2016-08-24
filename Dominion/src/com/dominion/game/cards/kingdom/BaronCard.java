package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.BaronAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusBuyAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class BaronCard extends Card implements ActionCard {
	
	private static final int PLUS_BUYS = 1;

	public BaronCard() {
		super("Baron", 4);
	}
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new PlusBuyAction(PLUS_BUYS));
		cardActions.add(new BaronAction());
		
		return cardActions;
	}
}
