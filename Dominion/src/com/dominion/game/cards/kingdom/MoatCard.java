package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.MoatReaction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.actions.ReactionAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.visitors.CardVisitor;

public class MoatCard extends Card implements ReactionCard, ActionCard {
	
	private static final int PLUS_CARDS = 2;

	public MoatCard() {
		super("Moat", 2);
	}

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new PlusCardAction(PLUS_CARDS));
		
		return cardActions;
	}

	@Override
	public ReactionAction getReaction() {
		return new MoatReaction();
	}
}
