package com.dominion.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.CurseCard;
import com.dominion.game.cards.basic.DuchyCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.interfaces.PlayerInterface;
import com.dominion.game.interfaces.messages.PlayerInterfaceMessage;
import com.dominion.game.observers.*;
import com.dominion.game.visitors.VictoryPointCounterCardVisitor;

public class Player {	
	protected final static int NUM_CARDS_IN_HAND = 5;
	protected CardCollection cardDeck = new CardCollection();
	protected CardCollection cardHand = new CardCollection();	
	protected CardCollection discardPile = new CardCollection();
	protected boolean immune = false;	

	protected CardCollection playArea = new CardCollection();	
	
	protected PlayerInterface playerInterface;
	
	
	/**
	 * Clone constructor
	 * @param player
	 */
	public Player(Player player) {
		this.cardDeck = new CardCollection(player.cardDeck);
		this.cardHand = new CardCollection(player.cardHand);
		this.discardPile = new CardCollection(player.discardPile);
		this.immune = player.immune;
		this.playArea = new CardCollection(player.playArea);
		this.playerInterface = player.playerInterface;
	}
	
	/**
	 * Constructor
	 * @param playerInterface
	 */
	public Player(PlayerInterface playerInterface) {
		this.playerInterface = playerInterface;
		
		cardDeck.addObserver(new CardDeckObserver(playerInterface));
		cardHand.addObserver(new CardHandObserver(playerInterface));
		discardPile.addObserver(new DiscardPileObserver(playerInterface));
		playArea.addObserver(new PlayAreaObserver(playerInterface));
	}

	public void addCardsToDeck(LinkedList<Card> cards) {
		cardDeck.addCards(cards);
	}

	/**
	 * Add multiple cards to the discard pile
	 * @param cards
	 */
	public void addCardsToDiscardPile(LinkedList<Card> cards) {
		discardPile.addCards(cards);
	}

	public void addCardToCardDeck(Card card) {
		cardDeck.addCardToTop(card);			
	}


	public void addCardToDiscardPile(Card card) {
		discardPile.addCardToTop(card);
	}

	/**
	 * Move a card into the hand
	 * @param card
	 */
	public void addCardToHand(Card card) {
		cardHand.addCardToTop(card);
	}

	public void cleanUpPhase() {
		discardPlayArea();
		discardHand();
		setImmune(false);
	}
	
	public int countCurseCards(List<Card> cards) {
		int count = 0;
		for (Card c: cards)
		{
			if (c instanceof CurseCard) {
				count++;
			}
		}
		return count;
	}
		
	/** 
	 * Counts the total number of victory points in the card deck for the
	 * player.
	 * 
	 * @param player
	 * @return
	 */
	public int countVictoryPoints(List<Card> cards) {
		VictoryPointCounterCardVisitor victoryPointCounterCardVisitor = new VictoryPointCounterCardVisitor();
		
		// Count number of Duchies for Duke 
		int numOfDuchies = 0;
		for (Card card : cards) {
			if (card instanceof DuchyCard) {
				numOfDuchies++;
			}
		}
		victoryPointCounterCardVisitor.setNumOfDuchies(numOfDuchies);
		
		// Necessary for gardens card
		int cardDeckSize = cards.size();		
		victoryPointCounterCardVisitor.setNumOfCards(cardDeckSize);
		
		// Visit all cards and tally their points
		for (Card card : cards) {
			card.accept(victoryPointCounterCardVisitor);
		}
		
		return victoryPointCounterCardVisitor.getVictoryPoints();
	}
	
	/**
	 * Move a card from hand to the discard pile
	 * @param card
	 */
	public void discardCardFromHand(Card card) {
		cardHand.removeCard(card);
		discardPile.addCardToTop(card);
	}

	/**
	 * Move the card deck to the discard pile
	 */
	public void discardEntireCardDeck() {
		discardPile.addCards(cardDeck.clearCards());
	}
	
	/**
	 * Discard current hand into the discard pile
	 */
	public void discardHand() {
		discardPile.addCards(cardHand.clearCards());
	}

	/**
	 * Move all cards from the play area into the discard deck
	 */
	public void discardPlayArea() {
		discardPile.addCards(playArea.clearCards());
	}	
	
	/**
	 * Take a card from the deck. If there are not enough cards in their Deck, 
	 * they draws as many as they can, shuffles their Discard pile to form a 
	 * new face-down Deck, and then draws another card.
	 */
	public Card drawCard() {
		// Check if deck is empty and the discard pile needs to be reshuffled
		if (cardDeck.isEmpty()) {
			moveDiscardPileToCardDeck(); 
			
			// Still no cards to draw
			if (cardDeck.isEmpty()) {
				return null;
			}
			
			cardDeck.shuffle();
		}
		
		Card card = cardDeck.removeTopCard();
		
		return card;
	}

	public void drawCardToHand() {
		Card card = drawCard();
		if (card != null) {
			cardHand.addCardToTop(card);
		}		
	}

	/**
	 * The player draws a new hand of 5 cards from his Deck.
	 */
	public void drawNewHand() {
		for (int i = 0; i < NUM_CARDS_IN_HAND; i++) {
			drawCardToHand();
		}
	}

	public ActionCard getActionCardToPlay() {
		List<Card> cards = cardHand.getCardsFilterByClass(ActionCard.class);
		
		// If the player has no action cards, we can end this phase
		if (cards.isEmpty()) {
			return null;
		}
		
		return playerInterface.selectActionCardToPlay(cards);
	}	
		
	public List<Card> getAllCards() {
		LinkedList<Card> cards = new LinkedList<Card>();
		cards.addAll(cardHand.getCards());
		cards.addAll(discardPile.getCards());
		cards.addAll(playArea.getCards());
		cards.addAll(cardDeck.getCards());
		
		return cards;
	}

	public String getCardActionToPlay(HashMap<String, CardAction> actions) {
		return playerInterface.selectCardActionToPlay(actions);
		
	}
	
	public String getCardDeck() {
		HashMap<String, Integer> cardTally = new HashMap<String, Integer>();
		
		for (Card c: cardDeck.getCards()) {
			if (!cardTally.containsKey(c.getName())) {
				cardTally.put(c.getName(), 0);
			}
			cardTally.put(c.getName(), cardTally.get(c.getName())+1);
		}
		return "" + cardTally;
	}
	
	/**
	 * Used during buy phase (using coins)
	 * @param cards
	 * @return
	 */
	public Card getCardToBuy(List<Card> cards) {
		Card card = playerInterface.selectCardToBuy(cards);

		return card;
	}

	public Card getCardToDiscard() {
		if (cardHand.getCards().isEmpty()) {
			return null;
		}
		return playerInterface.selectCardToDiscard(cardHand.getCards());
	}

	/**
	 * Used when an action allows a gain
	 * @param cards
	 * @return
	 */
	public Card getCardToGain(List<Card> cards, int maxcost) {
		Card card = playerInterface.selectCardToGain(cards, maxcost);

		return card;
	}
	
	/**
	 * Used when an action allows a gain
	 * @param cards
	 * @return
	 */
	public Card getCardToGainExact(List<Card> cards, int cost) {
		Card card = playerInterface.selectCardToGainExact(cards, cost);

		return card;
	}

	public Card getCardToPassLeft() {
		return playerInterface.selectCardToPassLeft(cardHand.getCards());
	}

	public Card getCardToPutOnDeck() {		
		return playerInterface.selectCardToPutOnDeck(cardHand.getCards());
	}

	public Card getCardToPutOnDeckScout(List<Card> cards) {
		return playerInterface.selectCardToPutOnDeckScout(cards);
	}
	
	public Card getCardToTrashFromHand() {		
		return playerInterface.selectCardToTrashFromHand(cardHand.getCards());
	}
		
	public Card getCardToTrashThief(List<Card> cards) {
		return playerInterface.selectCardToTrashThief(cards);
	}	

	public Card getCardWishingWell(List<Card> cards) {
		return playerInterface.guessCard(cards);
	}
	
	public Card getCopperCardToTrash() {
		List<Card> cards = new LinkedList<Card>();
		
		for (Card card : cardHand.getCards()) {
			if (card instanceof CopperCard) {
				cards.add(card);
			}
		}
		
		if (!cards.isEmpty()) {
			return playerInterface.selectCardToTrashFromHand(cards);
		}
		
		return null;
	}


	public int getCurrentScore() {
		List<Card> cards = getAllCards();
		
		return countVictoryPoints(cards) - countCurseCards(cards);
	}
	
	public Card getEstateCardToDiscard() {
		List<Card> cards = new LinkedList<Card>();
		
		for (Card card : cardHand.getCards()) {
			if (card instanceof EstateCard) {
				cards.add(card);
			}
		}
		
		if (!cards.isEmpty()) {
			return playerInterface.selectCardToDiscard(cards);
		}
		
		return null;
	}

	public List<Card> getHand() {
		return cardHand.getCards();
	}	
	
	/**
	 * Returns the current size of the hand
	 * @return
	 */
	public int getHandSize() {
		return cardHand.count();
	}

	public String getPlayerName() {
		return playerInterface.getPlayerName();
	}

	public ReactionCard getReactionCardToPlay() {
		List<Card> cards = cardHand.getCardsFilterByClass(ReactionCard.class);
		
		if (!cards.isEmpty()) {
			return playerInterface.selectReactionCard(cards);
		}
		
		return null;
	}

	public Card getTreasureCardToGain(List<Card> cards, int cost) {
		Card card = playerInterface.selectCardToGain(cards, cost);
		return card;
	}

	public TreasureCard getTreasureCardToPlay() {
		List<Card> cards = cardHand.getCardsFilterByClass(TreasureCard.class);
		
		if (!cards.isEmpty()) {
			return playerInterface.selectTreasureCardToPlay(cards);
		}
		
		return null;
	}
	
	public TreasureCard getTreasureCardToTrash() {
		List<Card> cards = cardHand.getCardsFilterByClass(TreasureCard.class);
		if (cards.isEmpty()) {
			return null;
		}
		return (TreasureCard)playerInterface.selectCardToTrashFromHand(cards);
	}
	
	public Card getVictoryCardToReveal() {
		List<Card> cards = cardHand.getCardsFilterByClass(VictoryCard.class);
		
		if (!cards.isEmpty()) {
			return playerInterface.selectVictoryCardToReveal(cards);
		}
		
		return null;
	}

	public void invokeMessage(PlayerInterfaceMessage message) {
		message.invoke(playerInterface);		
	}

	public boolean isImmune() {
		return this.immune;
	}

	/**
	 * Move card from the hand to deck e.g. Bureaucrat
	 */
	public void moveCardFromHandToDeck(Card card) {
		cardHand.removeCard(card);
		cardDeck.addCardToTop(card);
	}

	/**
	 * Move card from hand into the play area
	 * @param card
	 */	
	public void moveCardFromHandToPlayArea(Card card) {
		cardHand.removeCard(card);
		playArea.addCardToTop(card);
	}

	/**
	 * Move the discard pile back into the card deck
	 */
	public void moveDiscardPileToCardDeck() { 
		cardDeck.addCards(discardPile.clearCards());
	}

	public void notifyOfTurnState(TurnState turnState) {
		playerInterface.updateTurnState(
				turnState.getNumOfActions(), 
				turnState.getNumOfBuys(), 
				turnState.getTotalCoins());
	}

	public void removeFromHand(Card card) {
		cardHand.removeCard(card);		
	}

	public void removeFromPlayArea(Card card) {
		playArea.removeCard(card);
	}

	public void setImmune(boolean immune) {
		this.immune = immune;
	}

	public boolean wantsToDiscardCard(Card card) {
		return playerInterface.chooseIfDiscardCard(card);		
	}
	
	public boolean wantsToGainCard(Card card) {
		return playerInterface.chooseIfGainCard(card);
	}

	public boolean wantsToGainCardThief(Card trashCard) {
		return playerInterface.chooseIfGainCardThief(trashCard);
	}

	public boolean wantsToPutDeckInDiscard() {
		return playerInterface.chooseIfPutDeckInDiscard();
	}

	public boolean wantsToSetAsideCard(Card card) {
		return playerInterface.chooseIfSetAsideCard(card);
	}

	public boolean wantsToTrashCard(Card card) {
		return playerInterface.chooseIfTrashCard(card);
	}
}
