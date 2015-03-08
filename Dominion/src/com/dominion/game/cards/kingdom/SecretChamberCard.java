package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.ReactionAction;
import com.dominion.game.actions.SecretChamberAction;
import com.dominion.game.actions.SecretChamberReaction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.visitors.CardVisitor;

public class SecretChamberCard extends Card implements ReactionCard, ActionCard {
	public static final int COST = 2;
	public static final String NAME = "SecretChamber";	

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new SecretChamberAction());
		
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
	public ReactionAction getReaction() {
		return new SecretChamberReaction();
	}
}
