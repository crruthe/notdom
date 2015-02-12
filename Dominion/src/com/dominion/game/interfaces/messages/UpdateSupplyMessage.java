package com.dominion.game.interfaces.messages;

import java.util.HashMap;
import java.util.List;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class UpdateSupplyMessage implements PlayerInterfaceMessage {

	private HashMap<Class<? extends Card>, Integer> supplyStack;
	
	public UpdateSupplyMessage(HashMap<Class<? extends Card>, Integer> supplyStack) {		
		this.supplyStack = supplyStack;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {		
		playerInterface.updateSupply(supplyStack);
	}
}
