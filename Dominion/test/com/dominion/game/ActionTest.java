package com.dominion.game;

import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;
import com.dominion.game.cards.kingdom.*;

import junit.framework.TestCase;

public abstract class ActionTest extends TestCase {
	
	protected GameBoard gameBoard;
	protected GameState state;
	
	@Override
	protected void setUp() throws Exception {
		
		List<Class<? extends Card>> kingdomCards = new LinkedList<Class<? extends Card>>();
		
		// Reaction
		kingdomCards.add(MoatCard.class);

		// Attack
		kingdomCards.add(MilitiaCard.class);

		// Victory Action
		kingdomCards.add(GreatHallCard.class);

		// Victory Kingdom
		kingdomCards.add(GardensCard.class);
		
		// 2 coins
		kingdomCards.add(CellarCard.class);

		// 3 coins
		kingdomCards.add(VillageCard.class);

		// 4 coins
		kingdomCards.add(WorkshopCard.class);
		
		// 5 coins
		kingdomCards.add(MineCard.class);
		
		// 6 coins
		kingdomCards.add(AdventurerCard.class);
		
		gameBoard = new GameBoard();
		gameBoard.setup(kingdomCards, 3);
		state = new GameState();
		state.setGameBoard(gameBoard);
		
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	
}
