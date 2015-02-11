package com.dominion.game;

import java.util.List;

import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.interfaces.messages.EndGameCardsMessage;
import com.dominion.game.interfaces.messages.EndGameScoreMessage;


public class GameMaster {
	
	// TODO: Make this threadable
	private GameState state = new GameState();
	
	/**
	 * Performs all the actions to start the game.
	 * Provides the main game loop.  
	 */
	public void startGame() {
		if (state.getPlayers().size() < 2) {
			throw new RuntimeException("not enough players to start");
		} else if (state.getPlayers().size() > 4) {
			throw new RuntimeException("too many players");
		}
		
		state.initialise();
		
		// Main game loop
		while (true) {			
			playTurn();			
			
			if (state.hasGameEnded()) {
				break;
			}
		}
		
		// Tally the victory points
		for (Player player : state.getPlayers()) {
			
			int score = player.getCurrentScore();
			state.broadcastToAllPlayers(new EndGameScoreMessage(player, score));
			state.broadcastToAllPlayers(new EndGameCardsMessage(player, player.getCardDeck()));
			System.out.println("Score: " + score);			
		}
	}
	
	private void playTurn() {	
		actionPhase();
		buyPhase();
		state.getCurrentPlayer().cleanUpPhase();
		state.getCurrentPlayer().drawNewHand();
		state.getTurnState().reset();
	}
	
	private void actionPhase() {
		// Continue while the player has actions left
		while(state.getTurnState().getNumberOfActions() > 0) {
			state.getCurrentPlayer().notifyOfTurnState(state.getTurnState());
			
			ActionCard actionCard = state.getCurrentPlayer().getActionCardToPlay();

			// If null, player didn't select a card and wants to end the action phase
			if (actionCard == null) {
				break;
			}
			
			// Play the selected action card
			playActionCard(actionCard);
			
			// Consume on action for this turn
			state.getTurnState().decrementActions();
		}
	}

	public void playActionCard(ActionCard actionCard) {

		// Play the card into their play area
		state.getCurrentPlayer().moveCardFromHandToPlayArea((Card)actionCard);
		
		// Iterate through actions for action card
		for (CardAction action : actionCard.buildActionList()) {
			action.execute(state);
		}
	}
	
	private void buyPhase() {
		
		while(true) {
			
			TreasureCard treasureCard = state.getCurrentPlayer().getTreasureCardToPlay();
			System.out.println(treasureCard);
			
			// If null, player didn't select any cards and wants to end phase
			if (treasureCard == null) {
				break;
			}

			System.out.println(((Card)treasureCard).getName());
			
			playTreasureCard(treasureCard);
		}
		
		while(state.getTurnState().getNumberOfBuys() > 0) {
			
			state.getCurrentPlayer().notifyOfTurnState(state.getTurnState());
			
			List<Card> cards = state.getGameBoard().listCardsFilterByCost(state.getTurnState().getTotalCoins());
			
			Card card = state.getCurrentPlayer().getCardToBuy(cards);
			
			// If null, player didn't select any cards and wants to end phase
			if (card == null) {
				break;
			} else {
				// Gain a card
				playerGainsCardFromSupply(state.getCurrentPlayer(), card.getClass());
				
				state.getTurnState().decrementCoins(card.getCost());
				state.getTurnState().decrementBuys();				
			}
		}		
	}
	
	public void playerGainsCardFromSupply(Player player, Class<? extends Card> cardClass) {
		Card card = state.getGameBoard().removeCardFromSupply(cardClass);
		player.addCardToDiscardPile(card);		
	}
	
	/**
	 * During buy phase you can play treasure cards
	 * @param card
	 */
	private void playTreasureCard(TreasureCard card) {
		state.getCurrentPlayer().moveCardFromHandToPlayArea((Card)card);
		
		state.getTurnState().incrementCoins(card.getCoinAmount());

		state.getCurrentPlayer().notifyOfTurnState(state.getTurnState());
	}
}
