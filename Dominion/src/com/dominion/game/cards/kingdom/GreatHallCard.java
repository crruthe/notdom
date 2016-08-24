package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusActionAction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class GreatHallCard extends Card implements VictoryCard, ActionCard {	

	public static final int POINTS = 1;
	private static final int PLUS_ACTIONS = 1;
	private static final int PLUS_CARDS = 1;
	
	public GreatHallCard() {
		super("GreatHall", 3);
	}
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit((VictoryCard)this);
		visitor.visit((ActionCard)this);
	}

	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new PlusCardAction(PLUS_CARDS));
		cardActions.add(new PlusActionAction(PLUS_ACTIONS));
		
		return cardActions;
	}

	@Override
	public int getVictoryPoints() {
		return POINTS;
	}	
}
