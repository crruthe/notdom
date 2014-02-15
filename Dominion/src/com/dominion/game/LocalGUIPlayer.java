package com.dominion.game;

import android.view.View;

import com.dominion.R;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.basic.CopperCard;

public class LocalGUIPlayer implements PlayerInterface {
	private CardHand cardHand;
	private TurnState turnState;
	private GameBoard gameBoard;
	
	private Card selectedCard;
	private boolean skip;
	
	Object syncToken = new Object();
	
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
	
	/**
	 * Unblock the game thread to return back to the GM
	 */
	private void updateGM() {
		synchronized (syncToken) {
			syncToken.notify();
		}
	}
	
	/**
	 * Block the game thread until user responds
	 */
	private void waitOnGUI() {
		synchronized(syncToken) {
			try {
				wait();
			} catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * GUI will call this method when a user selects a card to buy
	 * This will update selectedCard for the GM to use
	 * 
	 * @param v
	 */
	public void selectCardToBuy(View v) {
		switch (v.getId()) {
			case R.drawable.card_money_copper:
				selectedCard = getCardByString("com.dominion.game.cards.basic.CopperCard");
			//case R.drawable.skip:
				//skip = true;
		}
		updateGM();
	}
	
	/**
	 * Returns an instantiation of the card based on the string
	 * e.g. com.dominion.game.cards.basic.CopperCard
	 * 
	 * @param cardName
	 * @return Card object
	 */
	private Card getCardByString(String cardClass) {
		try {
			return (Card) Class.forName(cardClass).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Called by GameMaster when a player must select a card to buy
	 */
	@Override
	public Card getCardToBuy() {		
		// Wait for the user to select a card or skip this the buy action
		while (selectedCard == null && !skip) {
			waitOnGUI();
		}
		
		// Return a card, but first reset the selectedCard state
		Card card = selectedCard;
		selectedCard = null;
		skip = false;
		
		return card;
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
