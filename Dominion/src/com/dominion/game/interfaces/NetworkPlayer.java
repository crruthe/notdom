package com.dominion.game.interfaces;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import redis.clients.jedis.Jedis;

import com.dominion.game.GameMaster;
import com.dominion.game.Player;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;
import com.google.gson.Gson;

public class NetworkPlayer implements PlayerInterface {
	class Message {
		private Object data; 
		private String message;
		
		public Message(String message, Object data) {
			this.message = message;
			this.data = data;
		}
	}
	
	class Connection {
		private String clientid;
		private Jedis jedis;
		
		public Connection(String clientid) {
			jedis = new Jedis("localhost");
			this.clientid = clientid;
		}

		public void sendMessage(String json) {
			jedis.rpush("client:"+clientid, json);
		}		
		
		public String waitForMessage() {
			return waitForMessage(0);
		}
		
		public String waitForMessage(int timeout) {
			List<String> result = jedis.blpop(timeout, "server:"+clientid);
			if (result == null) {
				return null;
			}
			return result.get(1);			
		}		
	}
	
	public static void main(String[] args) {
		while (true) {
			GameMaster gm = new GameMaster();
			List<NetworkPlayer> players = new LinkedList<NetworkPlayer>();
			
			Jedis j = new Jedis("localhost");
			boolean startGame = false;
			while (players.isEmpty() || !startGame) {			
				System.out.println("Check for new players...");
				String clientid = j.lpop("newclient");
				
				if (clientid != null) {
					System.out.println("Client found: " + clientid);
					
					// Create a new player and add to player list
					NetworkPlayer newPlayer = new NetworkPlayer(clientid);
					players.add(newPlayer);				
				}
				
				startGame = true;
				
				// Check if players are still there, remove them if not
				Iterator<NetworkPlayer> iter = players.iterator();
				while (iter.hasNext()) {
					NetworkPlayer player = iter.next();
					if (!player.heartbeat()) {					
						iter.remove();
					} else {
						// If their name isn't set
						if (player.getPlayerName().equals("")) {
							// Setup their player name				
							player.selectPlayerName();
							// Wait for player to say their ready
							player.waitReadyToStart();
						}
						if (!player.isReady()) {
							startGame = false; 
						}
					}
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			j.close();
			
			//gm.addPlayer(new Player(new SimpleConsolePlayer()));
			
			for (NetworkPlayer player : players) {
				gm.addPlayerToState(new Player(player));
			}			
			gm.startGame();
		}
	}
	
	private Connection connection;
	private String playerName = "";
	private boolean isReady = false;

	public NetworkPlayer(String clientid) {
		this.connection = new Connection(clientid);
	}
	
	public boolean heartbeat() {
		Gson gson = new Gson();
		String json = gson.toJson(new Message("heartbeat", null));
		connection.sendMessage(json);
		
		// Wait 5 seconds for the player to respond
		String result = connection.waitForMessage(2);
		if (result != null && result.equals("ready")) {
			isReady = true;		
			result = connection.waitForMessage(2);
		}

		System.out.println(result);
		return result != null && result.equals("heartbeat");
	}
	
	public void selectPlayerName() {
		Gson gson = new Gson();
		String json = gson.toJson(new Message("selectPlayerName", null));
		connection.sendMessage(json);
		playerName = connection.waitForMessage();
	}
	
	public void waitReadyToStart() {
		Gson gson = new Gson();
		String json = gson.toJson(new Message("waitReadyToStart", null));
		connection.sendMessage(json);		
	}
	
	public boolean isReady() {
		return isReady;
	}

	@Override
	public boolean chooseIfDiscardCard(Card card) {
		System.out.println("chooseIfDiscardCard");
		String json = convertCardToJson("chooseIfDiscardCard", card);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		return result.equals("Y");
	}
	
	@Override
	public boolean chooseIfGainCard(Card card) {
		System.out.println("chooseIfGainCard");
		String json = convertCardToJson("chooseIfGainCard", card);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		return result.equals("Y");
	}
	
	@Override
	public boolean chooseIfGainCardThief(Card card) {
		System.out.println("chooseIfGainCardThief");
		String json = convertCardToJson("chooseIfGainCardThief", card);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		return result.equals("Y");
	}
		
	@Override
	public boolean chooseIfPutDeckInDiscard() {
		System.out.println("chooseIfPutDeckInDiscard");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("chooseIfPutDeckInDiscard", null));
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		return result.equals("Y");
	}
	
	@Override
	public boolean chooseIfSetAsideCard(Card card) {
		System.out.println("chooseIfSetAsideCard");
		String json = convertCardToJson("chooseIfSetAsideCard", card);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		return result.equals("Y");
	}	
	
	@Override
	public boolean chooseIfTrashCard(Card card) {
		System.out.println("chooseIfTrashCard");
		String json = convertCardToJson("chooseIfTrashCard", card);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		return result.equals("Y");
	}

	@Override
	public void notifyCardGained(Player player, Card card) {
		System.out.println("notifyLog");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyLog", player.getPlayerName() + " gained " + card.getName() + "."));
		connection.sendMessage(json);
	}

	@Override
	public void notifyCardPlayed(Player player, Card card) {
		System.out.println("notifyLog");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyLog", player.getPlayerName() + " played " + card.getName() + "."));
		connection.sendMessage(json);
	}

	@Override
	public void notifyCardRevealed(Player player, Card card) {
		System.out.println("notifyLog");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyLog", player.getPlayerName() + " revealed " + card.getName() + "."));
		connection.sendMessage(json);
	}

	@Override
	public void notifyEndGameCards(Player player, final List<Card> cards) {
		HashMap<String, Integer> cardMap = new HashMap<String, Integer>();	
		for (Card card : cards) {			
			if (!cardMap.containsKey(card.getName())) {
				cardMap.put(card.getName(), 1);
			} else {
				cardMap.put(card.getName(), cardMap.get(card.getName()) + 1);
			}
		}
		
		System.out.println("notifyLog");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyLog", player.getPlayerName() + " cards: " + cardMap + "."));
		connection.sendMessage(json);
	}

	@Override
	public void notifyEndGameScore(Player player, final int score) {
		System.out.println("notifyLog");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyLog", player.getPlayerName() + " scored " + score + "."));
		connection.sendMessage(json);
	}

	@Override
	public void notifyHandRevealed(Player player, List<Card> cards) {
		List<String> cardsString = new LinkedList<String>();
		for (Card card : cards) {
			cardsString.add(card.getName());
		}
		
		System.out.println("notifyLog");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyLog", player.getPlayerName() + " revealed hand: " + cardsString + "."));
		connection.sendMessage(json);
	}

	@Override
	public ActionCard selectActionCardToPlay(final List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectActionCardToPlay");
		String json = convertCardsToJson("selectActionCardToPlay", cards);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return (ActionCard)card;
			}
		}
		
		return null;
	}

	@Override
	public CardAction selectCardActionToPlay(HashMap<String, CardAction> actions) {
		System.out.println("selectCardActionToPlay");
		
		Gson gson = new Gson();		
		String json = gson.toJson(new Message("selectCardActionToPlay", actions.keySet()));
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		return actions.get(result);
	}

	@Override
	public Card selectCardToBuy(final List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToBuy");
		String json = convertCardsToJson("selectCardToBuy", cards);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return card;
			}
		}
		return null;
	}

	@Override
	public Card selectCardToDiscard(final List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToDiscard");
		String json = convertCardsToJson("selectCardToDiscard", cards);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return card;
			}
		}
		return null;
	}

	@Override
	public Card selectCardToPutOnDeck(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToPutOnDeck");
		String json = convertCardsToJson("selectCardToPutOnDeck", cards);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return card;
			}
		}
		return null;
	}

	@Override
	public Card selectCardToTrashFromHand(final List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToTrashFromHand");
		String json = convertCardsToJson("selectCardToTrashFromHand", cards);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return card;
			}
		}
		return null;
	}

	@Override
	public Card selectCardToTrashThief(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToTrashThief");
		String json = convertCardsToJson("selectCardToTrashThief", cards);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return card;
			}
		}
		return null;
	}

	@Override
	public ReactionCard selectReactionCard(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectReactionCard");
		String json = convertCardsToJson("selectReactionCard", cards);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return (ReactionCard)card;
			}
		}
		return null;
	}

	@Override
	public TreasureCard selectTreasureCardToPlay(final List<Card> cards) {		
		// TODO Auto-generated method stub
		System.out.println("selectTreasureCardToPlay");
		String json = convertCardsToJson("selectTreasureCardToPlay", cards);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return (TreasureCard)card;
			}
		}
		
		return null;
	}

	@Override
	public Card selectVictoryCardToReveal(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectVictoryCardToReveal");
		String json = convertCardsToJson("selectVictoryCardToReveal", cards);
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		for(Card card : cards) {
			if (card.getName().equals(result)) {
				return card;
			}
		}
		return null;
	}

	@Override
	public void updateDeck(int numOfCards) {
		// TODO Auto-generated method stub
		System.out.println("updateDeck");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("updateDeck", numOfCards));		
		connection.sendMessage(json);
	}

	@Override
	public void updateDiscard(Card card) {
		// TODO Auto-generated method stub
		System.out.println("updateDiscard");
		String json = convertCardToJson("updateDiscard", card);
		connection.sendMessage(json);
	}

	@Override
	public void updateHand(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("updateHand");
		String json = convertCardsToJson("updateHand", cards);
		connection.sendMessage(json);
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
		connection.sendMessage(json);
	}

	@Override
	public void updateSupply(HashMap<Class<? extends Card>, Integer> supplyStack) {
		// TODO Auto-generated method stub
		List<Card> cards = new LinkedList<Card>();
		
		for(Class<? extends Card> cardClass : supplyStack.keySet()) {
			Card card = null;
			try {
				card = cardClass.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cards.add(card);
		}

		Collections.sort(cards, new Comparator<Card>() {
			@Override
			public int compare(Card o1, Card o2) {
				// Sort by cost
				int compare = new Integer(o1.getCost()).compareTo(o2.getCost());
				
				// Then sort by name
				if (compare == 0) {
					return o1.getName().compareTo(o2.getName());
				}
				return compare;
			}
		});
		
		LinkedHashMap<String, Integer> supplyObject = new LinkedHashMap<String, Integer>();		
		for (Card card : cards) {
			supplyObject.put(card.getName(), supplyStack.get(card.getClass()));
		}
		
		Gson gson = new Gson();		
		String json = gson.toJson(new Message("updateSupply", supplyObject));		
		connection.sendMessage(json);
	}

	@Override
	public void updateTrashPile(final List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("updateTrashPile");
		String json = convertCardToJson("updateTrashPile", cards.get(0));
		connection.sendMessage(json);
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
		connection.sendMessage(json);
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
	public String getPlayerName() {
		return playerName;
	}
}
