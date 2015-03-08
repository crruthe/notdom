package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.NoblesAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.visitors.CardVisitor;


public class NoblesCard extends Card implements VictoryCard, ActionCard {	
	public static final int COST = 6;
	public static final String NAME = "Nobles";
	public static final int POINTS = 2;
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit((VictoryCard)this);
		visitor.visit((ActionCard)this);
	}

	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new NoblesAction());
		
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
	public int getVictoryPoints() {
		return POINTS;
	}	
}
