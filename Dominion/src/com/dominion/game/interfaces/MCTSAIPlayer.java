package com.dominion.game.interfaces;

import java.util.HashMap;
import java.util.List;

import com.dominion.game.Player;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;

public class MCTSAIPlayer implements PlayerInterface {

	@Override
	public ReactionCard selectReactionCard(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card selectVictoryCardToReveal(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSupply(HashMap<String, List<Card>> supplyStack) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateHand(List<Card> cards) {
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
	public void updateDeck(int numOfCards) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDiscard(Card card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTrashPile(List<Card> cards) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTurnState(int numOfActions, int numOfBuys, int numOfCoins) {
		// TODO Auto-generated method stub

	}

	@Override
	public ActionCard selectActionCardToPlay(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(List<Card> cards) {
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
	public boolean chooseIfPutDeckInDiscard() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Card selectCardToTrashFromHand(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean chooseIfSetAsideCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateScore(int score) {
		// TODO Auto-generated method stub

	}

}
