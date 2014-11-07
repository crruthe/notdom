package com.dominion.game.interfaces;

import java.util.HashMap;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import com.dominion.game.GameMaster;
import com.dominion.game.Player;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;
import com.google.gson.Gson;

public class NetworkPlayer implements PlayerInterface {
	private Jedis jedis;
	private String clientid;

	public static void main(String[] args) {
		GameMaster gm = new GameMaster();
		
		NetworkPlayer n = new NetworkPlayer();
		
		gm.addPlayer(new Player(new SimpleConsolePlayer()));
		gm.addPlayer(new Player(n));
		//gm.addPlayer(new Player(new SimpleConsolePlayer()));
		
		gm.startGame();
	}
	
	public NetworkPlayer() {
		jedis = new Jedis("localhost");
		
		setup();
	}
	
	private void sendMessage(String json) {
		jedis.rpush("client:"+clientid, json);
	}
	
	private String waitForMessage() {
		List<String> result = jedis.blpop(0, "server:"+clientid);
		return result.get(1);
	}
	
	private void setup() {		
		List<String> result = jedis.blpop(0, "newclient");
		clientid = result.get(1);
		System.out.println("Client found: " + clientid);						
	}
	
	@Override
	public ReactionCard selectReactionCard(List<Card> cards) {
		Gson gson = new Gson();
		String json = gson.toJson(cards);
		System.out.println(json);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card selectCardFromHand(List<Card> cards) {
		Gson gson = new Gson();
		String json = gson.toJson(cards);
		System.out.println(json);
		return null;
	}

	@Override
	public void updateSupply(HashMap<String, List<Card>> supplyStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHand(List<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOtherPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlayArea(List<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDeck(int numOfCards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDiscard(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTrashPile(List<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTurnState(int numOfActions, int numOfBuys, int numOfCoins) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionCard selectActionCardToPlay(List<Card> cards) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		String json = gson.toJson(cards);
		System.out.println(json);
		return null;
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(List<Card> cards) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		String json = gson.toJson(cards);
		System.out.println(json);
		return null;
	}

	@Override
	public Card selectCardToBuy(List<Card> cards) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		String json = gson.toJson(cards);
		System.out.println(json);
		return null;
	}

	@Override
	public Card selectCardToDiscard(List<Card> cards) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		String json = gson.toJson(cards);
		System.out.println(json);
		return null;
	}

	@Override
	public boolean chooseIfPutDeckInDiscard() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Card selectCardToTrash(List<Card> cards) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		String json = gson.toJson(cards);
		System.out.println(json);
		return null;
	}

	@Override
	public boolean chooseIfSetAsideCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

}
