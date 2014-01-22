package com.dominion.game;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;

public class LocalGUIPlayer implements PlayerInterface {
	private CardHand cardHand;
	private TurnState turnState;
	private GameBoard gameBoard;
	
	
	public static void main(String[] args) {
		GameMaster gm = new GameMaster();
		gm.addPlayer(new Player(new LocalGUIPlayer()));
		gm.addPlayer(new Player(new SimpleConsolePlayer()));
		
		gm.startGame();
	}

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
		this.cardHand = cardHand;		
	}

	@Override
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;		
	}

	@Override
	public void setTurnState(TurnState turnState) {
		this.turnState = turnState;		
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
