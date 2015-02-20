package com.dominion.game;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.interfaces.messages.CardGainedMessage;
import com.dominion.game.interfaces.messages.EndGameCardsMessage;
import com.dominion.game.interfaces.messages.EndGameScoreMessage;
import com.dominion.game.interfaces.messages.NewTurnMessage;


public class GameMaster {
	
	// TODO: Make this threadable
	private GameState state = new GameState();

	public void addPlayerToState(Player player) {
		state.addPlayer(player);
	}
	
	public void playerGainsCardFromSupply(Player player, Class<? extends Card> cardClass) {
		Card card = state.getGameBoard().removeCardFromSupply(cardClass);
		player.addCardToDiscardPile(card);	
		state.broadcastToAllPlayers(new CardGainedMessage(player, card));
	}
	
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
		
		int turnCount = 0;		
		
		// Main game loop
		while (true) {
			// On a new round, broadcast the turn count
			if ((turnCount % state.getPlayers().size()) == 0) {
				int round = (int)(turnCount / state.getPlayers().size()) + 1;
				state.broadcastToAllPlayers(new NewTurnMessage(round));
			}
			
			playTurn();
			state.rotatePlayers();
			
			if (state.hasGameEnded()) {
				
				printSupplyStack();
				break;
			}
			turnCount++;			
		}
		
		// Tally the victory points
		for (Player player : state.getPlayers()) {
			
			int score = player.getCurrentScore();
			state.broadcastToAllPlayers(new EndGameScoreMessage(player, score));
			state.broadcastToAllPlayers(new EndGameCardsMessage(player, player.getAllCards()));
		}
	}
	
	private void printSupplyStack() {
		List<Card> cards = new LinkedList<Card>();
		
		for(Class<? extends Card> cardClass : state.getGameBoard().getSupplyStacks().keySet()) {
			Card card = null;
			try {
				card = cardClass.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cards.add(card);
		}

		Collections.sort(cards, new Comparator<Card>() {
			@Override
			public int compare(Card o1, Card o2) {
				// Sort by cost
				int compare = new Integer(o1.getCost()).compareTo(o2.getCost());
				
				// Then sort by name
				if (compare == 0) {
					return o1.getName().compareTo(o2.getName());
				}
				return compare;
			}
		});
		
		LinkedHashMap<String, Integer> supplyObject = new LinkedHashMap<String, Integer>();		
		for (Card card : cards) {
			supplyObject.put(card.getName(), state.getGameBoard().getSupplyStacks().get(card.getClass()));
		}	
		System.out.println(supplyObject);
	}

	private void actionPhase() {
		// Continue while the player has actions left
		while(state.getTurnState().getNumOfActions() > 0) {
			state.getCurrentPlayer().notifyOfTurnState(state.getTurnState());
			
			ActionCard actionCard = state.getCurrentPlayer().getActionCardToPlay();

			// If null, player didn't select a card and wants to end the action phase
			if (actionCard == null) {
				break;
			}
			
			// Play the selected action card
			state.playActionCard(actionCard);
			
			// Consume on action for this turn
			state.getTurnState().decrementActions();
		}
	}
	
	private void buyPhase() {
		
		while(true) {
			
			TreasureCard treasureCard = state.getCurrentPlayer().getTreasureCardToPlay();
			
			// If null, player didn't select any cards and wants to end phase
			if (treasureCard == null) {
				break;
			}

			state.playTreasureCard(treasureCard);
		}
		
		while(state.getTurnState().getNumOfBuys() > 0) {
			
			state.getCurrentPlayer().notifyOfTurnState(state.getTurnState());
			
			List<Card> cards = state.listCardsFilterByCost(state.getTurnState().getTotalCoins());
			
			Card card = state.getCurrentPlayer().getCardToBuy(cards);
			
			// If null, player didn't select any cards and wants to end phase
			if (card == null) {
				break;
			} else {
				// Gain a card
				playerGainsCardFromSupply(state.getCurrentPlayer(), card.getClass());
				
				Card mCard = state.modifyCard(card);
				state.getTurnState().decrementCoins(mCard.getCost());
				state.getTurnState().decrementBuys();				
			}
		}		
	}
	
	private void playTurn() {	
		actionPhase();
		buyPhase();
		state.getCurrentPlayer().cleanUpPhase();
		state.getCurrentPlayer().drawNewHand();
		state.getTurnState().reset();
	}
}
