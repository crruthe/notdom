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

	private List<Card> cardHand = new LinkedList<Card>();
	private Card discardCard;
	private int numOfActions;
	private int numOfBuys;
	private int numOfCoins;
	private List<Card> playArea = new LinkedList<Card>();
	private int deckSize = 0;
	
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
	public Card selectCardToTrashFromHand(List<Card> cards) {
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
		deckSize = numOfCards;		
	}

	@Override
	public void updateDiscard(Card card) {
		discardCard = card;		
	}

	@Override
	public void updateOtherPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlayArea(List<Card> cards) {
		playArea = cards;
	}

	@Override
	public void notifyEndGameScore(Player player, int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyEndGameCards(Player player, List<Card> cards) {
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

	@Override
	public void updateSupply(HashMap<Class<? extends Card>, Integer> supplyStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean chooseIfGainCardThief(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Card selectCardToTrashThief(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyHandRevealed(Player player, List<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	public Card getDiscardCard() {
		return discardCard;
	}

	public List<Card> getPlayArea() {
		return playArea;
	}	
	
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

	public int getDeckSize() {
		return deckSize;
	}	
}
