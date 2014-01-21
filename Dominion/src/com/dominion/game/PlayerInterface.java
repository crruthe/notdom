package com.dominion.game;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;

public interface PlayerInterface {
	public void updateCardHand();
	public void updateGameBoard();
	public void updateTurnState();
	
	public void setCardHand(CardHand cardHand);
	public void setGameBoard(GameBoard gameBoard);
	public void setTurnState(TurnState turnState);
	
	
	public String getPlayerName();
	public ActionCard getActionCardToPlay();
	public Card getCardToBuy();
	public Card getCardToGain(int cost);
	public Card getCardToDiscard();
	public ReactionCard getReactionCardToPlay();
	public Card getCardToTrash();
	public Card getTreasureCardToTrash();
	public boolean wantsToSetAsideCard(Card card);
	public boolean wantsToPutDeckInDiscard();
	public Card getVictoryCardToReveal();
}
