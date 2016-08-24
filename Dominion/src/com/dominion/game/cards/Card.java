package com.dominion.game.cards;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.ReactionAction;
import com.dominion.game.interfaces.messages.CardRevealedMessage;
import com.dominion.game.visitors.CardVisitor;

public abstract class Card implements Comparable<Card> {
	/**
	 * Return an instantiation of the card for the given class type
	 * @param cardClass
	 * @return
	 */
	static public Card getCard(Class<? extends Card> cardClass) {
		try {
			return cardClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected final int cost;
	protected final String name;

	public Card(String name, int cost) {
		this.name = name;
		this.cost = cost;
	}
	
	public Card(Card card) {
		this.name = card.name;
		this.cost = card.cost;
	}
	
	public abstract void accept(CardVisitor visitor);

	@Override
	public int compareTo(Card o) {		
		if (this.cost == o.cost)
			return getName().compareTo(o.getName());
		else
			return new Integer(cost).compareTo(o.cost);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (!(obj instanceof Card))
			return false;
		
		Card other = (Card) obj;
		return getName().equals(other.getName());
	}
	
	public List<CardAction> getActionList() {
		return new LinkedList<CardAction>();
	}
	
	public int getCost() {
		return cost;
	}
		
	public String getName() {
		return name;
	}

	/**
	 * Performs the actions of a card based on the given game state
	 * 
	 * @param state
	 */
	public void onPlay(GameState state) {
		// Track actions player for Conspirator
		state.getTurnState().incrementActionsPlayed();
		
		if (this instanceof AttackCard) {
			getReactionToAttack(state);
		}
		
		List<CardAction> actions = getActionList();
		for (CardAction action : actions) {
			action.execute(state);
		}
	}	

	@Override
	public String toString() {
		return getName();
	}
	
	protected void getReactionToAttack(GameState state) {
		// Allow the other players to reveal reaction cards
		for (Player victim : state.getOtherPlayers()) {
			
			// Other players can keep revealing reaction cards
			ReactionCard card;
			do {
				// Allow the player to reveal any reaction cards
				card = victim.getReactionCardToPlay();
				if (card != null) {
					ReactionAction action = card.getReaction();
					state.broadcastToAllPlayers(new CardRevealedMessage(victim, (Card)card));
		
					action.executeOnPlayer(state, victim);
				}				
			} while (card != null);
		}		
	}
}
