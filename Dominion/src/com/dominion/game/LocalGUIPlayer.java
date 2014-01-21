package com.dominion.game;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;

public class LocalGUIPlayer implements PlayerInterface {

	@Override
	public void updateCardHand() {
		// TODO Auto-generated method stub

	}

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
	public void updateTurnState() {
		// TODO Auto-generated method stub
		
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
	public void updateGameBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCardHand(CardHand cardHand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGameBoard(GameBoard gameBoard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTurnState(TurnState turnState) {
		// TODO Auto-generated method stub
		
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

}
