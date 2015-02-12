package com.dominion.game.observers;

import java.util.Observable;
import java.util.Observer;

import com.dominion.game.CardCollection;
import com.dominion.game.interfaces.PlayerInterface;

public class CardDeckObserver implements Observer {

	private PlayerInterface playerInterface;
	
	public CardDeckObserver(PlayerInterface playerInterface) {
		this.playerInterface = playerInterface;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		CardCollection cards = (CardCollection)o;
		playerInterface.updateDeck(cards.count());
	}
}
