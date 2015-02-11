package com.dominion.game;

import java.util.LinkedList;

import junit.framework.TestCase;

public class GameStateTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetCurrentPlayer() {
		GameState state = new GameState();
		Player player1 = new Player(new MockPlayerInterface());
		Player player2 = new Player(new MockPlayerInterface());
		Player player3 = new Player(new MockPlayerInterface());
		state.addPlayer(player1);
		state.addPlayer(player2);
		state.addPlayer(player3);
		
		assertEquals(player1, state.getCurrentPlayer());
	}

	public void testGetOtherPlayers() {
		GameState state = new GameState();
		Player player1 = new Player(new MockPlayerInterface());
		Player player2 = new Player(new MockPlayerInterface());
		Player player3 = new Player(new MockPlayerInterface());
		state.addPlayer(player1);
		state.addPlayer(player2);
		state.addPlayer(player3);
		
		LinkedList<Player> otherPlayers = new LinkedList<Player>();
		otherPlayers.add(player2);
		otherPlayers.add(player3);
		assertEquals(otherPlayers, state.getOtherPlayers());
	}

	public void testHasGameEnded() {
		fail("Not yet implemented"); // TODO
	}

	public void testInitialise() {
		GameState state = new GameState();
		Player player1 = new Player(new MockPlayerInterface());
		Player player2 = new Player(new MockPlayerInterface());
		Player player3 = new Player(new MockPlayerInterface());
		state.addPlayer(player1);
		state.addPlayer(player2);
		state.addPlayer(player3);

		state.initialise();
	}

	public void testRotatePlayers() {
		GameState state = new GameState();
		Player player1 = new Player(new MockPlayerInterface());
		Player player2 = new Player(new MockPlayerInterface());
		Player player3 = new Player(new MockPlayerInterface());
		state.addPlayer(player1);
		state.addPlayer(player2);
		state.addPlayer(player3);
		
		state.rotatePlayers();
		LinkedList<Player> otherPlayers;
		
		otherPlayers = new LinkedList<Player>();
		otherPlayers.add(player3);
		otherPlayers.add(player1);
		assertEquals(otherPlayers, state.getOtherPlayers());
		state.rotatePlayers();
		
		otherPlayers = new LinkedList<Player>();
		otherPlayers.add(player1);
		otherPlayers.add(player2);
		assertEquals(otherPlayers, state.getOtherPlayers());
	}
}
