package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.PlusBuyAction;
import com.dominion.game.actions.TradeRouteAction;
import com.dominion.game.actions.TrashCardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ProsperityCard;
import com.dominion.game.visitors.CardVisitor;


public class TradeRouteCard extends Card implements ActionCard, ProsperityCard {	
	public static final int COST = 3;
	public static final String NAME = "TradeRoute";
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new PlusBuyAction(1));
		cardActions.add(new TradeRouteAction());
		cardActions.add(new TrashCardAction(1, true));
		
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
}
