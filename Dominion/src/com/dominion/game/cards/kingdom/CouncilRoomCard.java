package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.CouncilRoomAction;
import com.dominion.game.actions.PlusBuyAction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class CouncilRoomCard implements ActionCard {
	public static final int COST = 5;
	public static final String NAME = "CouncilRoom";
	private final int PLUS_BUYS = 1;
	private final int PLUS_CARDS = 4;

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusCardAction(PLUS_CARDS));
		cardActions.add(new PlusBuyAction(PLUS_BUYS));
		cardActions.add(new CouncilRoomAction());
		
		return cardActions;
	}

	@Override
	public boolean equals(Object obj) {
		// Cards are the same if names match (even if different instances)
		return NAME == ((Card)obj).getName();
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
