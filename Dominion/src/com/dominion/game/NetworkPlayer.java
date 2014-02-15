package com.dominion.game;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;

public class NetworkPlayer implements PlayerInterface {

	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionCard getActionCardToPlay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card getCardToBuy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card getCardToGain(int cost) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReactionCard getReactionCardToPlay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card getCardToDiscard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card getCardToTrash() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean wantsToSetAsideCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wantsToPutDeckInDiscard() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Card getTreasureCardToTrash() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card getVictoryCardToReveal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCardHand(ImmutableCardHand cardHand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGameBoard(ImmutableGameBoard gameBoard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTurnState(ImmutableTurnState turnState) {
		// TODO Auto-generated method stub
		
	}
	
}
