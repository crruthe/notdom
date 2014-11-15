package com.dominion.game.interfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import redis.clients.jedis.Jedis;

import com.dominion.game.GameMaster;
import com.dominion.game.Player;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.interfaces.messages.Message;
import com.google.gson.Gson;

public class NetworkPlayer implements PlayerInterface {
	private Jedis jedis;
	private String clientid;

	public static void main(String[] args) {
		GameMaster gm = new GameMaster();
		
		//gm.addPlayer(new Player(new SimpleConsolePlayer()));
		gm.addPlayer(new Player(new NetworkPlayer()));
		gm.addPlayer(new Player(new NetworkPlayer()));
		
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
		System.out.println("Waiting for player to connect...");
		List<String> result = jedis.blpop(0, "newclient");
		clientid = result.get(1);
		System.out.println("Client found: " + clientid);						
	}
	
	/**
	 * Converts all cards into a collection of strings to formats into json
	 * 
	 * @param cards
	 * @return String json
	 */
	private String convertCardsToJson(final String message, List<Card> cards) {
		Gson gson = new Gson();		
		Collection<String> cardsString = new LinkedList<String>();
		for(Card card : cards) {
			cardsString.add(card.getName());
		}
		String json = gson.toJson(new Message(message, cardsString));
		System.out.println(json);
		return json;
	}
	
	/**
	 * Converts a card into a collection of strings to formats into json
	 * 
	 * @param cards
	 * @return String json
	 */
	private String convertCardToJson(final String message, Card card) {
		Gson gson = new Gson();		
		String cardString = null;
		if (card != null) {
			cardString = card.getName();
		}
		String json = gson.toJson(new Message(message, cardString));
		System.out.println(json);
		return json;
	}	
	
	@Override
	public ReactionCard selectReactionCard(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectReactionCard");
		String json = convertCardsToJson("selectReactionCard", cards);
		sendMessage(json);
		String result = waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return (ReactionCard)card;
			}
		}
		return null;
	}

	@Override
	public Card selectVictoryCardToReveal(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectVictoryCardToReveal");
		String json = convertCardsToJson("selectVictoryCardToReveal", cards);
		sendMessage(json);
		String result = waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return card;
			}
		}
		return null;
	}

	@Override
	public void updateSupply(HashMap<String, List<Card>> supplyStack) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		HashMap<String,Integer> supplyObject = new HashMap<String, Integer>();
		for(String card : supplyStack.keySet()) {
			supplyObject.put(card, supplyStack.get(card).size());
		}
		String json = gson.toJson(new Message("updateSupply", supplyObject));		
		sendMessage(json);
	}

	@Override
	public void updateHand(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("updateHand");
		String json = convertCardsToJson("updateHand", cards);
		sendMessage(json);
	}

	@Override
	public void updateOtherPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlayArea(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("updatePlayArea");
		String json = convertCardsToJson("updatePlayArea", cards);
		sendMessage(json);
	}

	@Override
	public void updateDeck(int numOfCards) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		String json = gson.toJson(new Message("updateDeck", numOfCards));		
		sendMessage(json);
	}

	@Override
	public void updateDiscard(Card card) {
		// TODO Auto-generated method stub
		System.out.println("updateDiscard");
		String json = convertCardToJson("updateDiscard", card);
		sendMessage(json);
	}

	@Override
	public void updateTrashPile(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("updateTrashPile");
		String json = convertCardsToJson("updateTrashPile", cards);
		sendMessage(json);
	}

	@Override
	public void updateTurnState(int numOfActions, int numOfBuys, int numOfCoins) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		HashMap<String,Integer> turnState = new HashMap<String, Integer>();
		turnState.put("numOfActions", numOfActions);
		turnState.put("numOfBuys", numOfBuys);
		turnState.put("numOfCoins", numOfCoins);
		String json = gson.toJson(new Message("updateTurnState", turnState));		
		sendMessage(json);
	}

	@Override
	public ActionCard selectActionCardToPlay(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectActionCardToPlay");
		String json = convertCardsToJson("selectActionCardToPlay", cards);
		sendMessage(json);
		String result = waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return (ActionCard)card;
			}
		}
		
		return null;
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(List<Card> cards) {
		
		// TODO Auto-generated method stub
		System.out.println("selectTreasureCardToPlay");
		String json = convertCardsToJson("selectTreasureCardToPlay", cards);
		sendMessage(json);
		String result = waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return (TreasureCard)card;
			}
		}
		
		return null;
	}

	@Override
	public Card selectCardToBuy(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToBuy");
		String json = convertCardsToJson("selectCardToBuy", cards);
		sendMessage(json);
		String result = waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return card;
			}
		}
		return null;
	}

	@Override
	public Card selectCardToDiscard(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToDiscard");
		String json = convertCardsToJson("selectCardToDiscard", cards);
		sendMessage(json);
		String result = waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return card;
			}
		}
		return null;
	}

	@Override
	public boolean chooseIfPutDeckInDiscard() {
		// TODO Auto-generated method stub
		System.out.println("chooseIfPutDeckInDiscard");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("chooseIfPutDeckInDiscard", null));
		sendMessage(json);
		String result = waitForMessage();
		return result.equals("Y");
	}

	@Override
	public Card selectCardToTrash(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToTrash");
		String json = convertCardsToJson("selectCardToTrash", cards);
		sendMessage(json);
		String result = waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return card;
			}
		}
		return null;
	}

	@Override
	public boolean chooseIfSetAsideCard(Card card) {
		// TODO Auto-generated method stub
		System.out.println("chooseIfSetAsideCard");
		String json = convertCardToJson("chooseIfSetAsideCard", card);
		sendMessage(json);
		String result = waitForMessage();
		return result.equals("Y");
	}
}
