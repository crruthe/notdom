package com.dominion.game.interfaces;

import java.util.HashMap;
import java.util.List;

import com.dominion.game.Player;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;

public interface PlayerInterface {
	public ReactionCard selectReactionCard(final List<Card> cards);
	public Card selectCardFromHand(final List<Card> cards);
	public void updateSupply(final HashMap<String, List<Card>> supplyStack);
	public void updateHand(final List<Card> cards);
	public void updateOtherPlayer(final Player player);
	public void updatePlayArea(final List<Card> cards);
	public void updateDeck(final int numOfCards);
	public void updateDiscard(final Card card);
	public void updateTrashPile(final List<Card> cards);
	public void updateTurnState(int numOfActions, int numOfBuys, int numOfCoins);
	public ActionCard selectActionCardToPlay(final List<Card> cards);
	public TreasureCard selectTreasureCardToPlay(final List<Card> cards);
	public Card selectCardToBuy(final List<Card> cards);
	public Card selectCardToDiscard(final List<Card> cards);
	public boolean chooseIfPutDeckInDiscard();
	public Card selectCardToTrash(final List<Card> cards);
	public boolean chooseIfSetAsideCard(final Card card);
}
