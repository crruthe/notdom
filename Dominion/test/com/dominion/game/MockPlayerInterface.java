package com.dominion.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.actions.CardAction;
import com.dominion.game.interfaces.PlayerInterface;

public class MockPlayerInterface implements PlayerInterface {

	private List<Card> cardHand;
	private int numOfActions;
	private int numOfBuys;
	private int numOfCoins;

	public List<Card> getCardHand() {
		return cardHand;
	}
	
	public int getNumOfActions() {
		return numOfActions;
	}

	public int getNumOfBuys() {
		return numOfBuys;
	}

	public int getNumOfCoins() {
		return numOfCoins;
	}

	@Override
	public void updateHand(List<Card> cards) {
		cardHand = new LinkedList<Card>(cards);
	}
	
	@Override
	public boolean chooseIfPutDeckInDiscard() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean chooseIfSetAsideCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean chooseIfTrashCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean chooseIfGainCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean chooseIfDiscardCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void notifyCardPlayed(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyCardGained(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionCard selectActionCardToPlay(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card selectCardToBuy(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card selectCardToDiscard(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card selectCardToTrash(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReactionCard selectReactionCard(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card selectVictoryCardToReveal(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDeck(int numOfCards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDiscard(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOtherPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlayArea(List<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyEndGameScore(Player player, int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyEndGameCards(Player player, String cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSupply(HashMap<String, List<Card>> supplyStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTrashPile(List<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTurnState(int numOfActions, int numOfBuys, int numOfCoins) {
		this.numOfActions = numOfActions;
		this.numOfBuys = numOfBuys;
		this.numOfCoins = numOfCoins;		
	}

	@Override
	public void notifyCardRevealed(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Card selectCardToPutOnDeck(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardAction selectCardActionToPlay(List<CardAction> actions) {
		// TODO Auto-generated method stub
		return null;
	}

}
