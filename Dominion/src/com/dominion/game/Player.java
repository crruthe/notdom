package com.dominion.game;

import java.util.Collection;

import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CountTotalCoinsVisitor;
import com.dominion.game.visitors.VictoryPointCounterCardVisitor;

public class Player {	
	private final CardDeck cardDeck = new CardDeck();
	private final DiscardPile discardPile = new DiscardPile();
	private final CardHand cardHand = new CardHand();
	private final PlayArea playArea = new PlayArea();
	
	private GameBoard gameBoard;
	private TurnState turnState;	
	private Collection<Player> otherPlayers;
	
	private final PlayerInterface playerInterface;

	private final static int NUM_CARDS_IN_HAND = 5;
	
	private boolean isImmune;	
	
	public Player(PlayerInterface playerInterface) {
		this.playerInterface = playerInterface;
	}
	
	public void playTurn() {
		turnState = new TurnState();
		
		actionPhase();
		buyPhase();
		cleanUpPhase();		
		drawNewHand();	
	}
	
	private void actionPhase() {
		updateTurnState();
		
		// Continue while the player has actions left
		while(turnState.getNumberOfActions() > 0) {
			ActionCard actionCard = playerInterface.getActionCardToPlay();
			
			// If null, player didn't select a card and wants to end the action phase
			if (actionCard == null) {
				// Set the number of actions to zero
				turnState.zeroActions();
			} else {
				// Play the selected action card
				playActionCard(actionCard);
				
				// Consume on action for this turn
				turnState.decrementActions();
			}
			
			updateTurnState();
		}
	}
	
	private void updateTurnState() {
		playerInterface.updateTurnState(new ImmutableTurnState(turnState));
	}
	
	private void updateCardHand() {
		playerInterface.updateCardHand(new ImmutableCardHand(cardHand));
	}
	
	private void buyPhase() {
		// Determine amount of coins to buy with
		turnState.incrementCoins(countTotalCoinsInHand());
		updateTurnState();
		
		while(turnState.getNumberOfBuys() > 0) {
			Card card = playerInterface.getCardToBuy();
			
			if (card == null) {
				turnState.zeroBuys();
			} else {
				if (card.getCost() > turnState.getTotalCoins()) {
					throw new RuntimeException("not enough coin to purchase card");
				}

				// Gain a card
				gainCard(card);
				
				System.out.println(card.getDescription());
				
				turnState.decrementCoins(card.getCost());
				turnState.decrementBuys();
			}
			updateTurnState();
		}		
	}

	private void cleanUpPhase() {
		discardPlayArea();
		discardHand();
	}	
	
	private void playActionCard(ActionCard actionCard) {

		playCard(actionCard);
		
		updateCardHand();
		
		// Iterate through actions for action card
		for (CardAction action : actionCard.buildActionList()) {
			action.setPlayer(this);
			action.execute();
		}
	}
		
	/**
	 * @return the playerInterface
	 */
	public PlayerInterface getPlayerInterface() {
		return playerInterface;
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
			
			cardDeck.shuffle();
		}
		
		Card card = cardDeck.drawCard();
		return card;
	}
	
	public void drawCardToHand() {
		cardHand.addCard(drawCard());
	}
	
	/**
	 * Move the discard pile back into the card deck
	 */
	public void moveDiscardPileToCardDeck() { 
		cardDeck.addCards(discardPile.clearDiscardPile());		
	}
	
	/**
	 * Move all cards from the play area into the discard deck
	 */
	public void discardPlayArea() {
		discardPile.addCards(playArea.clearPlayArea());
	}
	
	/**
	 * Move all cards from the play area into the discard deck
	 */
	public void moveCardFromHandToDeck(Card card) {
		cardHand.removeCard(card);
		cardDeck.addCard(card);
	}

	/**
	 * Move a card from hand to the discard pile
	 * @param card
	 */
	public void discardCardFromHand(Card card) {
		cardHand.removeCard(card);
		discardPile.addCard(card);
	}
	
	/**
	 * Move the card deck to the discard pile
	 */
	public void moveCardDeckToDiscardPile() {
		discardPile.addCards(cardDeck.getCards());
	}
	
	/**
	 * Returns the current size of the hand
	 * @return
	 */
	public int getHandSize() {
		return cardHand.getSize();
	}
	
	/**
	 * Add a card to the card deck
	 * @param card
	 */
	public void addCardToDeck(Card card) {
		cardDeck.addCard(card);
	}
	
	/**
	 * Add multiple cards to the card deck
	 * @param cards
	 */
	public void addCardsToDeck(Collection<Card> cards) {
		cardDeck.addCards(cards);
	}
	
	/**
	 * Add multiple cards to the discard pile
	 * @param cards
	 */
	public void addCardsToDiscardPile(Collection<Card> cards) {
		discardPile.addCards(cards);
	}
	
	/**
	 * Move card from hand into the play area
	 * @param card
	 */
	public void playCard(Card card) {
		cardHand.removeCard(card);
		playArea.addCard(card);		
	}
	
	/**
	 * Move a card into the hand
	 * @param card
	 */
	public void addCardToHand(Card card) {
		cardHand.addCard(card);
	}
	
	/**
	 * Add a new card into the discard pile
	 * @param card
	 */
	public void gainCard(Card card) {
		discardPile.addCard(card);	
	}	
	
	/**
	 * Discard current hand into the discard pile
	 */
	public void discardHand() {
		discardPile.addCards(cardHand.clearHand());		
	}
		
	/**
	 * The player draws a new hand of 5 cards from his Deck.
	 */
	public void drawNewHand() {
		for (int i = 0; i < NUM_CARDS_IN_HAND; i++) {
			cardHand.addCard(drawCard());
		}
		updateCardHand();
	}
	
	/** 
	 * Counts the total number of victory points in the card deck for the
	 * player.
	 * 
	 * @param player
	 * @return
	 */
	public int countVictoryPointsInCardDeck() {
		VictoryPointCounterCardVisitor victoryPointCounterCardVisitor = new VictoryPointCounterCardVisitor();
		
		// Necessary for gardens card
		int cardDeckSize = cardDeck.getCards().size();		
		victoryPointCounterCardVisitor.setNumberOfCards(cardDeckSize);
		
		for (Card card : cardDeck.getCards()) {
			card.accept(victoryPointCounterCardVisitor);
		}
		
		return victoryPointCounterCardVisitor.getVictoryPoints();
	}		
	
	/**
	 * Counts the total number of coins available from treasure cards
	 */
	public int countTotalCoinsInHand() {
		CountTotalCoinsVisitor totalCoinsVisitor = new CountTotalCoinsVisitor();
		for (Card card : cardHand.getCards()) {
			card.accept(totalCoinsVisitor);
		}
		return totalCoinsVisitor.getCoins();
	}
	
	/**
	 * Return the size of the card deck
	 * @return
	 */
	public int getCardDeckSize() {
		return cardDeck.count();
	}
	
	/**
	 * Return the size of the card deck
	 * @return
	 */
	public int getDiscardPileSize() {
		return discardPile.count();
	}
	
	public CardHand getCardHand() {
		return cardHand;
	}
	
	public PlayArea getPlayArea() {
		return playArea;		
	}
		
	public DiscardPile getDiscardPile() {
		return discardPile;
	}
	
	public CardDeck getCardDeck() {
		return cardDeck;
	}
	
	public boolean isImmune() {
		return isImmune;
	}

	public void setImmune(boolean isImmune) {
		this.isImmune = isImmune;
	}

	public void trashCard(Card card) {
		cardHand.removeCard(card);
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public Collection<Player> getOtherPlayers() {
		return otherPlayers;
	}

	public void setOtherPlayers(Collection<Player> otherPlayers) {
		this.otherPlayers = otherPlayers;
	}	
	
	public TurnState getTurnState() {
		return turnState;
	}

	public void setTurnState(TurnState turnState) {
		this.turnState = turnState;
	}
}
