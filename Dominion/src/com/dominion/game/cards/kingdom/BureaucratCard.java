package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.BureaucratAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.AttackCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class BureaucratCard extends Card implements ActionCard, AttackCard {

	public BureaucratCard() {
		super("Bureaucrat", 4);
	}

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new BureaucratAction());
		
		return cardActions;
	}
}
