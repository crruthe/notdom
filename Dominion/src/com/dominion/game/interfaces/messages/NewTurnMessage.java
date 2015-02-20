package com.dominion.game.interfaces.messages;

import com.dominion.game.interfaces.PlayerInterface;

public class NewTurnMessage implements PlayerInterfaceMessage {

	private int round;
	
	public NewTurnMessage(int round) {
		this.round = round;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {		
		playerInterface.notifyNewTurn(round);
	}
}
