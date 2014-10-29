package com.dominion.game.cards;

import com.dominion.game.actions.CardAction;

public interface ReactionCard extends ActionCard {
	public CardAction getReaction();
	public boolean grantsImmunity();
}
