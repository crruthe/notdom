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

public class NetworkPlayer implements PlayerInterface {

	public static void main(String[] args) {
		GameMaster gm = new GameMaster();
		
		NetworkPlayer n = new NetworkPlayer();
		n.setup();
		
		//gm.addPlayer(new Player(new SimpleConsolePlayer()));
		//gm.addPlayer(new Player(new SimpleConsolePlayer()));
		//gm.addPlayer(new Player(new SimpleConsolePlayer()));
		
		//gm.startGame();
	}
	
	public void setup() {
		final Jedis jedis = new Jedis("localhost");
		List<String> result = jedis.blpop(0, "newclient");
		final String clientid = result.get(1);
		System.out.println(clientid);
		jedis.rpush("client:"+clientid, "testing, mofo!");
		while(true) {
			System.out.println("waiting for client..." + clientid);
			List<String> result1 = jedis.blpop(0, "server:"+clientid);
			String response = result1.get(1);
			System.out.println(response);
		}				
	}
	
	@Override
	public ReactionCard selectReactionCard(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card selectCardFromHand(List<Card> cards) {
		// TODO Auto-generated method stub
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
		return null;
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card selectCardToBuy(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card selectCardToDiscard(List<Card> cards) {
		// TODO Auto-generated method stub
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
		return null;
	}

	@Override
	public boolean chooseIfSetAsideCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

}
