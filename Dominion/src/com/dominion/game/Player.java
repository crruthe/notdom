package com.dominion.game;

import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.visitors.CountTotalCoinsVisitor;
import com.dominion.game.visitors.VictoryPointCounterCardVisitor;

public class Player {	
	private final CardDeck cardDeck = new CardDeck();
	private final DiscardPile discardPile = new DiscardPile();
	private final CardHand cardHand = new CardHand();
	private final PlayArea playArea = new PlayArea();
	
	private final PlayerInterface playerInterface;

	private final static int NUM_CARDS_IN_HAND = 5;
	private final static int NUM_ESTATE_SETUP = 3;
	private final static int NUM_COPPER_SETUP = 7;
	
	private final TurnState turnState = new TurnState();
	
	private boolean isImmune;
	
	/**
	 * @return the turnState
	 */
	public TurnState getTurnState() {
		return turnState;
	}

	/**
	 * @return the playerInterface
	 */
	public PlayerInterface getPlayerInterface() {
		return playerInterface;
	}

	/**
	 * factory constructor
	 */
	private Player(PlayerInterface playerInterface) {
		this.playerInterface = playerInterface;
		this.playerInterface.setCardHand(cardHand);
		this.playerInterface.setTurnState(turnState);
	}
	
	/**
	 * @return factory to create a default player
	 */
	public static Player createDefaultPlayer() {
		PlayerInterface playerInterface = new LocalGUIPlayer();
		return new Player(playerInterface);
	}
	
	/**
	 * @return factory to create a network player
	 */
	public static Player createNetworkPlayer() {
		PlayerInterface playerInterface = new NetworkPlayer();
		return new Player(playerInterface);
	}

	/**
	 * @return factory to create a console player
	 */
	public static Player createSimpleConsolePlayer() {
		PlayerInterface playerInterface = new SimpleConsolePlayer();
		return new Player(playerInterface);
	}
	
	/**
	 * Take a card from the deck and place it in the hand. If
	 * there are not enough cards in his Deck, he draws as many as he
	 * can, shuffles his Discard pile to form a new face-down Deck, and
	 * then draws the rest of his new hand.
	 */
	public Card drawCard() {
		// Check if deck is empty and the discard pile needs to be reshuffled
		if (cardDeck.isEmpty()) {
			// Move discard pile into card deck 
			cardDeck.addCards(discardPile.clearDiscardPile());
			
			cardDeck.shuffle();
		}
		
		Card card = cardDeck.drawCard();
		return card;
	}
	
	public void discardCard(Card card) {
		cardHand.removeCard(card);
		discardPile.addCard(card);
	}
		
	/**
	 * Each player starts the game with the same cards:
	 * 7 coppers
	 * 3 estates
	 * 
	 * Each player shuffles these cards and places them (his Deck)
	 * face-down in his play area (the area near him on the table).
	 */
	private void setUpDeck() {
		for (int i = 0; i < NUM_ESTATE_SETUP; i++) {
			cardDeck.addCard(new EstateCard());
		}
		
		for (int i = 0; i < NUM_COPPER_SETUP; i++) {
			cardDeck.addCard(new CopperCard());
		}
		
		cardDeck.shuffle();		
	}
	
	
	/**
	 * The player draws a new hand of 5 cards from his Deck.
	 */
	private void drawNewHand() {
		for (int i = 0; i < NUM_CARDS_IN_HAND; i++) {
			cardHand.addCard(drawCard());
		}
	}
	
	public void setup() {
		setUpDeck();
		drawNewHand();		
	}

	public void actionPhase() {
		// Reset actions, buys and coins
		turnState.reset();		
		playerInterface.updateTurnState();
		
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
			
			playerInterface.updateTurnState();
		}
	}
	
	public CardHand getCardHand() {
		return cardHand;
	}
	
	public PlayArea getPlayArea() {
		return playArea;		
	}
	
	private void playActionCard(ActionCard actionCard) {
		// Move action card from hand to play area
		cardHand.removeCard(actionCard);
		playArea.addCard(actionCard);
		
		playerInterface.updateCardHand();
		
		// Iterate through actions for action card
		for (CardAction action : actionCard.buildActionList()) {
			action.setPlayer(this);
			action.execute();
		}
	}
	
	public void buyPhase() {
		// Determine amount of coins to buy with
		countTotalCoinsInHand();
		
		while(turnState.getNumberOfBuys() > 0) {
			Card card = playerInterface.getCardToBuy();
			
			if (card == null) {
				turnState.zeroBuys();
			} else {
				if (card.getCost() > turnState.getTotalCoins()) {
					throw new RuntimeException("not enough coin to purchase card");
				}

				// Gain a card
				discardPile.addCard(card);
				
				System.out.println(card.getDescription());
				
				turnState.decrementCoins(card.getCost());
				turnState.decrementBuys();
			}
			playerInterface.updateTurnState();
		}		
	}
	
	/**
	 * Counts the total number of coins available from treasure cards
	 */
	private void countTotalCoinsInHand() {
		CountTotalCoinsVisitor totalCoinsVisitor = new CountTotalCoinsVisitor();
		for (Card card : cardHand.getCards()) {
			card.accept(totalCoinsVisitor);
		}
		turnState.incrementCoins(totalCoinsVisitor.getCoins());
		playerInterface.updateTurnState();		
	}

	public void endGamePhase() {
		// Move card hand into card deck
		cardDeck.addCards(cardHand.clearHand());
		
		// Move discard pile into the card deck
		cardDeck.addCards(discardPile.clearDiscardPile());
	}
	
	public int countVictoryPoints() {
		VictoryPointCounterCardVisitor victoryPointCounterCardVisitor = new VictoryPointCounterCardVisitor();
		
		// Necessary for gardens card
		int cardDeckSize = cardDeck.getCards().size();		
		victoryPointCounterCardVisitor.setNumberOfCards(cardDeckSize);
		
		for (Card card : cardDeck.getCards()) {
			card.accept(victoryPointCounterCardVisitor);
		}
		
		return victoryPointCounterCardVisitor.getVictoryPoints();
	}
	
	public void cleanUpPhase() {
		// Move play area into discard pile
		discardPile.addCards(playArea.clearPlayArea());
		
		// Move card hand into discard pile
		discardPile.addCards(cardHand.clearHand());
		
		drawNewHand();	
	}

	public DiscardPile getDiscardPile() {
		return discardPile;
	}
	
	public CardDeck getCardDeck() {
		return cardDeck;
	}
	
	private void playReactionCard(ReactionCard reactionCard) {
		// Move action card from hand to play area
		cardHand.removeCard(reactionCard);
		playArea.addCard(reactionCard);
		
		playerInterface.updateCardHand();

		CardAction action = reactionCard.getReaction();

		action.setPlayer(this);
		action.execute();
	}

	public void revealReactionPhase() {
		boolean endPhase = false;
		
		while (!endPhase) {			
			ReactionCard card = playerInterface.getReactionCardToPlay();
			
			if (card == null) {
				endPhase = true;
			} else {
				playReactionCard(card);
			}
		}

		// Move reveal cards into card hand
		cardHand.addCards(playArea.clearPlayArea());
	}
	
	public boolean isImmune() {
		return isImmune;
	}

	public void setImmune(boolean isImmune) {
		this.isImmune = isImmune;
	}

	public void trashCard(Card card) {
		cardHand.removeCard(card);
		GameMaster.getInstance().getGameBoard().addToTrashPile(card);
	}	
}
