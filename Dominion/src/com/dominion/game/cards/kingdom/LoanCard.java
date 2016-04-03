package com.dominion.game.cards.kingdom;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.LoanAction;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ProsperityCard;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.visitors.CardVisitor;


public class LoanCard extends Card implements TreasureCard, ProsperityCard {	
	public static final int COST = 3;
	public static final String NAME = "Loan";
	private static final int COINS = 1;
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public List<CardAction> getActionList() {
		List<CardAction> cardActions = super.getActionList();
		
		cardActions.add(new LoanAction());
		
		return cardActions;
	}
	
	@Override
	public int getCoinAmount() {
		return COINS;
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
