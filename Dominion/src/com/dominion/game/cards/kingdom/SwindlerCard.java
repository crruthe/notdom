package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusCoinAction;
import com.dominion.game.actions.SwindlerAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.AttackCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class SwindlerCard extends Card implements ActionCard, AttackCard {
	
	private static final int PLUS_COINS = 2;

	public SwindlerCard() {
		super("Swindler", 3);
	}
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new PlusCoinAction(PLUS_COINS));
		cardActions.add(new SwindlerAction());
		
		return cardActions;
	}
}
