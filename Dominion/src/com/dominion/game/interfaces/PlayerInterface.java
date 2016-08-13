package com.dominion.game.interfaces;

import java.util.HashMap;
import java.util.List;

import com.dominion.game.CardStack;
import com.dominion.game.Player;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;

public interface PlayerInterface {
	public boolean chooseIfDiscardCard(final Card card);
	public boolean chooseIfGainCard(final Card card);
	public boolean chooseIfGainCardThief(Card card);
	public boolean chooseIfPutDeckInDiscard();
	public boolean chooseIfSetAsideCard(final Card card);
	public boolean chooseIfTrashCard(final Card card);
	public String getPlayerName();
	public Card guessCard(final List<Card> cards);
	public void notifyActionSelected(Player player, String action);
	public void notifyCardGained(final Player player, final Card card);
	public void notifyCardPlayed(final Player player, final Card card);
	public void notifyCardRevealed(final Player player, final Card card);
	public void notifyCardTrashed(Player player, Card card);
	public void notifyEndGameCards(final Player player, List<Card> cards);
	public void notifyEndGameScore(final Player player, int score);
	public void notifyGuessCard(Player player, Card card);
	public void notifyHandRevealed(final Player player, final List<Card> cards);
	public void notifyNewTurn(int turnCount);
	public ActionCard selectActionCardToPlay(final List<Card> cards);
	public String selectCardActionToPlay(final HashMap<String, CardAction> actions);
	public Card selectCardToBuy(final List<Card> cards);
	public Card selectCardToDiscard(final List<Card> cards);
	public Card selectCardToGain(final List<Card> cards, int cost);
	public Card selectCardToGainExact(final List<Card> cards, int cost);
	public Card selectCardToPassLeft(final List<Card> cards);
	public Card selectCardToPutOnDeck(final List<Card> cards);
	public Card selectCardToPutOnDeckScout(final List<Card> cards);
	public Card selectCardToTrashFromHand(final List<Card> cards);
	public Card selectCardToTrashThief(List<Card> cards);
	public ReactionCard selectReactionCard(final List<Card> cards);
	public TreasureCard selectTreasureCardToPlay(final List<Card> cards);
	public Card selectVictoryCardToReveal(final List<Card> cards);
	public void updateDeck(final int numOfCards);
	public void updateDiscard(final Card card);
	public void updateHand(final List<Card> cards);
	public void updateOtherPlayer(final Player player);
	public void updatePlayArea(final List<Card> cards);
	public void updateSupply(final HashMap<Class<? extends Card>, CardStack> supplyStack);
	public void updateTrashPile(final List<Card> cards);
	public void updateTurnState(int numOfActions, int numOfBuys, int numOfCoins);
}
