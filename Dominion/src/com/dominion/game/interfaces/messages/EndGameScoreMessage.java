package com.dominion.game.interfaces.messages;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class EndGameScoreMessage implements NotifyMessage {

	private Player player;
	private int score;
	
	public EndGameScoreMessage(Player player, int score) {
		this.player = player;
		this.score = score;
	}
	
	@Override
	public void notify(PlayerInterface playerInterface) {		
		playerInterface.notifyEndGameScore(player, score);
	}
}
