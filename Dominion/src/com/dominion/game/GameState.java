package com.dominion.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.basic.ColonyCard;
import com.dominion.game.cards.basic.CopperCard;
import com.dominion.game.cards.basic.EstateCard;
import com.dominion.game.cards.basic.ProvinceCard;
import com.dominion.game.interfaces.messages.CardPlayedMessage;
import com.dominion.game.interfaces.messages.PlayerInterfaceMessage;
import com.dominion.game.interfaces.messages.UpdateSupplyMessage;
import com.dominion.game.modifiers.CardModifier;

/**
 * GameState is a container for everything that makes up a current state.
 * The game state will be invoked by Actions.
 */
public class GameState {

	private final static int NUM_COPPER_SETUP = 7;
	private final static int NUM_ESTATE_SETUP = 3;		

	protected GameBoard gameBoard;
	protected LinkedList<Player> players = new LinkedList<Player>();	
	protected TurnState turnState = new TurnState();	
	
	/**
	 * Clone constructor
	 * @param gameState
	 */
	public GameState(GameState gameState) {
		this.gameBoard = new GameBoard(gameState.gameBoard);		
		this.players = new LinkedList<Player>();
		for (Player player : gameState.players) {
			this.players.add(new Player(player));
		}
		this.turnState = new TurnState(gameState.turnState);
	}
	
	public GameState() {
		super();
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public void broadcastToAllPlayers(PlayerInterfaceMessage message) {
		for (Player player: players) {
			player.invokeMessage(message);
		}
	}

	/**
	 * Return current player i.e. player who's turn it is
	 * @return
	 */
	public Player getCurrentPlayer() {
		return players.get(0);
	}	

	public GameBoard getGameBoard() {
		return gameBoard;
	}
	
	/**
	 * Returns a list of players that are not the current player (usually for attacks)
	 * @return
	 */
	public List<Player> getOtherPlayers() {
		LinkedList<Player> otherPlayers = new LinkedList<Player>(players);
		otherPlayers.removeFirst();
		return otherPlayers;
	}
		
	/**
	 * Get all players
	 * @return
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	public TurnState getTurnState() {
		return turnState;
	}
	
	/**
	 * The game ends at the end of any player's turn when either:
	 * 1) the Supply pile of Province cards is empty or
	 * 2) any 3 Supply piles are empty.
	 * 
	 * @return whether the game should end
	 */
	public boolean hasGameEnded() {
		if (gameBoard.isStackEmpty(ProvinceCard.class)) {			
			System.out.println("No provinces!");
			return true;
		}

		if (gameBoard.countNumberOfEmptyStacks() >= 3) {
			System.out.println("Stacks are empty.");
			return true;
		}
		
		if (gameBoard.areBigCardsUsed() && gameBoard.isStackEmpty(ColonyCard.class)) {
			System.out.println("No colonies!");
			return true;			
		}
		
		return false;
	}
	
	public void initialise() {
		if (gameBoard == null) {
			gameBoard = new GameBoard();
			gameBoard.setupRandom(players.size());			
		}
		setupAllPlayers();
		randomisePlayers();
		gameBoard.registerObservers(this);
		
		// Notify players of new supply
		broadcastToAllPlayers(new UpdateSupplyMessage(gameBoard.getSupplyStacks()));
	}
	
	/**
	 * Returns a list of the all the cards in the game
	 * @return
	 */
	public List<Card> listAllCards() {
		List<Card> cards = new LinkedList<Card>();
		
		for (Class<? extends Card> cardClass : gameBoard.getSupplyStacks().keySet()) {
			Card card = Card.getCard(cardClass);				
			cards.add(card);
		}
		return cards;		
	}
	
	/**
	 * Build up a list of cards that the player can buy
	 * 
	 * @param amount of coins to buy with
	 * @return collection of cards
	 */
	public List<Card> listCardsFilterByClassAndCost(Class<?> cardClass, int amount) {
		List<Card> cards = new LinkedList<Card>();
		
		for (Class<? extends Card> checkCardClass : gameBoard.getSupplyStacks().keySet()) {	
			// Check if any cards are left in stack
			if (gameBoard.getSupplyStacks().get(checkCardClass).getNumCards() > 0) {
				Card card = Card.getCard(checkCardClass);

				// Apply modifiers for cost, e.g. Bridge
				Card mCard = modifyCard(card);
				
				// Check if player can afford this card
				if (cardClass.isInstance(card) && mCard.getCost() <= amount) {
					cards.add(card);
				}
			}
		}
		return cards;
	}
	
	/** 
	 * Build up a list of cards that the player can buy
	 * 
	 * @param amount of coins to buy with
	 * @return collection of cards
	 */
	public List<Card> listCardsFilterByCost(int amount) {
		return listCardsFilterByCost(0, amount);
	}
	
	/** 
	 * Build up a list of cards that the player can buy
	 * 
	 * @param amount of coins to buy with
	 * @return collection of cards
	 */
	public List<Card> listCardsFilterByCostExact(int amount) {
		return listCardsFilterByCost(amount, amount);
	}
	
	/**
	 * Applies modifiers to the card in a pipeline and returns the final modified card
	 * 
	 * @param card
	 * @return
	 */
	public Card modifyCard(Card mCard) {
		for (CardModifier modifier : turnState.getModifiers()) {
			mCard = modifier.modify(mCard);
		}
		return mCard;
	}
	
	/**
	 * Applies modifiers to the card in a pipeline and returns the final modified card
	 * 
	 * @param card
	 * @return
	 */
	public TreasureCard modifyCard(TreasureCard mCard) {
		for (CardModifier modifier : turnState.getModifiers()) {
			mCard = modifier.modify(mCard);
		}
		return mCard;
	}
	
	public void playActionCard(ActionCard actionCard) {		
		broadcastToAllPlayers(new CardPlayedMessage(getCurrentPlayer(), (Card)actionCard));		

		// Play the card into their play area
		getCurrentPlayer().moveCardFromHandToPlayArea((Card)actionCard);
		
		// Perform on play actions
		((Card)actionCard).onPlay(this);
	}
	
	/**
	 * During buy phase you can play treasure cards
	 * @param card
	 */
	public void playTreasureCard(TreasureCard card) {
		getCurrentPlayer().moveCardFromHandToPlayArea((Card)card);		

		// Perform on play actions
		((Card)card).onPlay(this);

		// Update number of coins left
		TreasureCard mCard = modifyCard(card);
		turnState.incrementCoins(mCard.getCoinAmount());

		getCurrentPlayer().notifyOfTurnState(turnState);
		broadcastToAllPlayers(new CardPlayedMessage(getCurrentPlayer(), (Card)card));
	}
	
	public void rotatePlayers() {
		Player currentPlayer = players.removeFirst();
		players.addLast(currentPlayer);
	}


	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	/**
	 * Each player starts the game with the same cards:
	 * 7 coppers
	 * 3 estates
	 * 
	 * Each player shuffles these cards and places them (their Deck)
	 * face-down in their play area (the area near them on the table).
	 */
	private void buildDeckForPlayer(Player player) {
		LinkedList<Card> cards = new LinkedList<Card>();
		
		for (int i = 0; i < NUM_ESTATE_SETUP; i++) {
			cards.add(new EstateCard());
		}
		
		for (int i = 0; i < NUM_COPPER_SETUP; i++) {
			cards.add(new CopperCard());
		}
		
		Collections.shuffle(cards);
		player.addCardsToDeck(cards);
	}

	/** 
	 * Build up a list of cards that the player can buy
	 * 
	 * @param amount of coins to buy with
	 * @return collection of cards
	 */
	private List<Card> listCardsFilterByCost(int min, int max) {
		List<Card> cards = new LinkedList<Card>();
		
		for (Class<? extends Card> cardClass : gameBoard.getSupplyStacks().keySet()) {	
			// Check if any cards are left in stack
			if (gameBoard.getSupplyStacks().get(cardClass).getNumCards() > 0) {
				Card card = Card.getCard(cardClass);
				
				// Apply modifiers for cost, e.g. Bridge
				Card mCard = modifyCard(card);
				
				// Check if player can afford this card
				if (mCard.getCost() >= min && mCard.getCost() <= max) {
					cards.add(card);
				}
			}
		}
		return cards;		
	}
	
	/**
	 * Randomises the turn order, which also provides the starting player 
	 */
	private void randomisePlayers() {
		Collections.shuffle(players);
	}	
	
	/**
	 * Build the starting deck and draw a new hand for each player
	 */
	private void setupAllPlayers() {
		for (Player player : players) {
			buildDeckForPlayer(player);
			player.drawNewHand();
		}
	}	
}
