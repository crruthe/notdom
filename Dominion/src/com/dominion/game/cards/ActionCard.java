package com.dominion.game.cards;

import java.util.Collection;
import com.dominion.game.actions.CardAction;

public interface ActionCard {
	public Collection<CardAction> buildActionList();
}
