package com.dominion.game.interfaces;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import redis.clients.jedis.Jedis;

import com.dominion.game.CardStack;
import com.dominion.game.GameBoard;
import com.dominion.game.GameMaster;
import com.dominion.game.Player;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.cards.TreasureCard;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class NetworkPlayer implements PlayerInterface {
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
	
	class Message {
		private Object data; 
		private String message;
		
		public Message(String message, Object data) {
			this.message = message;
			this.data = data;
		}

		public String getMessage() {
			return message;
		}

		public String getData() {
			return (String)data;
		}
	}
	
	static class PlayerThread implements Runnable {
		boolean isReady = false;
		boolean isDead = false;
		final NetworkPlayer player;

		public PlayerThread(NetworkPlayer player) {
			this.player = player;
		}
		
		@Override
		public void run() {
			System.out.println("Created player thread...");
			long lastHeartbeat = System.currentTimeMillis();
			player.selectPlayerName();
			
			while (true) {
				Message message = player.getMessageFromConnection();
				if (message == null) {
					System.out.println("Sleep...");
					player.heartbeat();
				} else if (message.getMessage().equals("heartbeat")) {
					lastHeartbeat = System.currentTimeMillis();
				} else if (message.getMessage().equals("setPlayerName")) {
					player.setPlayerName(message.getData());
					player.waitReadyToStart();
				} else if (message.getMessage().equals("ready")) {
					isReady = true;
					break;
				}
				
				if (System.currentTimeMillis() - lastHeartbeat > 5000) {
					System.out.println("DEAD DEAD DEAD... removing!");
					isDead = true;
					break;
				}
			}			
		}

		public boolean isReady() {
			return isReady;
		}

		public boolean isDead() {
			return isDead;
		}

		public NetworkPlayer getPlayer() {
			return player;
		}
	}
	
	public static void main(String[] args) {
		while (true) {
			GameMaster gm = new GameMaster();
			List<PlayerThread> players = new LinkedList<PlayerThread>();
			
			Jedis j = new Jedis("localhost");
			boolean startGame = false;
			while (players.isEmpty() || !startGame) {			
				System.out.println("Check for new players...");
				String clientid = j.lpop("newclient");
				System.out.println("Num of players: " + players.size());
				
				if (clientid != null) {
					System.out.println("Client found: " + clientid);
					
					// Create a new player and add to player list
					PlayerThread pt = new PlayerThread(new NetworkPlayer(clientid));
					(new Thread(pt)).start();
					players.add(pt);					
					
				} else {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				
				startGame = true;
				
				// Check if players are still there, remove them if not
				Iterator<PlayerThread> iter = players.iterator();
				while (iter.hasNext()) {
					PlayerThread player = iter.next();					
					if (player.isDead()) {
						System.out.println("DEAD DEAD DEAD... removing!");
						iter.remove();
					} else if (!player.isReady()) {
						startGame = false; 
					}
				}
			}
			j.close();
			
			//gm.addPlayer(new Player(new SimpleConsolePlayer()));			
						
			List<Class<? extends Card>> randomKingdoms = GameBoard.randomKingdoms(10 + players.size());
			
			for (PlayerThread player : players) {
				Class<? extends Card> cardClass = player.getPlayer().selectVetoCard(randomKingdoms);
				randomKingdoms.remove(cardClass);
				player.getPlayer().notifyStartGame();
				gm.addPlayerToState(new Player(player.getPlayer()));
			}		

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
			
			GameBoard gb = new GameBoard();
			gb.setup(randomKingdoms, players.size());
			gm.setGameBoard(gb);
			gm.startGame();
		}
	}
	
	private void notifyStartGame() {
		System.out.println("notifyStartGame");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyStartGame", null));
		connection.sendMessage(json);
	}

	private Class<? extends Card> selectVetoCard(List<Class<? extends Card>> randomKingdoms) {
		// TODO Auto-generated method stub
		System.out.println("selectVeto");
		
		HashMap<String, Class<? extends Card>> cardsMap = new HashMap<String, Class<? extends Card>>();
		
		for(Class<? extends Card> kingdom : randomKingdoms) {
			try {
				cardsMap.put(kingdom.newInstance().getName(), kingdom);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(new Message("selectVeto", cardsMap.keySet()));
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		return cardsMap.get(result);
	}

	private Connection connection;
	private boolean isReady = false;
	private String playerName = null;

	public NetworkPlayer(String clientid) {
		this.connection = new Connection(clientid);
	}
	
	public Message getMessageFromConnection() {
		String json = connection.waitForMessage(2);
		if (json == null)
			return null;
		
		Gson gson = new Gson();
		System.out.println("Got Message: " + json);
	    return gson.fromJson(json, Message.class);
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
	public String getPlayerName() {
		return playerName;
	}
		
	@Override
	public Card guessCard(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("guessCard");
		String json = convertCardsToJson("guessCard", cards);
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
	
	public void heartbeat() {
		Gson gson = new Gson();
		String json = gson.toJson(new Message("heartbeat", null));
		connection.sendMessage(json);
	}	
	
	public boolean isReady() {
		return isReady;
	}

	@Override
	public void notifyActionSelected(Player player, String action) {
		System.out.println("notifyLog");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyLog", player.getPlayerName() + " selected " + action + "."));
		connection.sendMessage(json);
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
	public void notifyCardTrashed(Player player, Card card) {
		System.out.println("notifyLog");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyLog", player.getPlayerName() + " trashed " + card.getName() + "."));
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
	public void notifyGuessCard(Player player, Card card) {
		System.out.println("notifyLog");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyLog", player.getPlayerName() + " guessed " + card.getName() + "."));
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
	public void notifyNewTurn(int round) {
		System.out.println("notifyLog");
		Gson gson = new Gson();
		String json = gson.toJson(new Message("notifyLog", "New Round - " +  round + "."));
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
	public String selectCardActionToPlay(HashMap<String, CardAction> actions) {
		System.out.println("selectCardActionToPlay");
		
		Gson gson = new Gson();		
		String json = gson.toJson(new Message("selectCardActionToPlay", actions.keySet()));
		connection.sendMessage(json);
		String result = connection.waitForMessage();
		System.out.println(result);
		
		return result;
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
	public Card selectCardToGain(List<Card> cards, int cost) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToGain");
		String json = convertCardsWithCostToJson("selectCardToGain", cards, cost);
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
	public Card selectCardToGainExact(List<Card> cards, int cost) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToGainExact");
		String json = convertCardsWithCostToJson("selectCardToGainExact", cards, cost);
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
	public Card selectCardToPassLeft(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToPassLeft");
		String json = convertCardsToJson("selectCardToPassLeft", cards);
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
	public Card selectCardToPutOnDeckScout(List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("selectCardToPutOnDeckScout");
		String json = convertCardsToJson("selectCardToPutOnDeckScout", cards);
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

	public void selectPlayerName() {
		Gson gson = new Gson();
		String json = gson.toJson(new Message("selectPlayerName", null));
		connection.sendMessage(json);
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
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
	public void updateSupply(HashMap<Class<? extends Card>, CardStack> supplyStack) {
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
			supplyObject.put(card.getName(), supplyStack.get(card.getClass()).getNumCards());
		}
		
		Gson gson = new Gson();		
		String json = gson.toJson(new Message("updateSupply", supplyObject));		
		connection.sendMessage(json);
	}

	@Override
	public void updateTrashPile(final List<Card> cards) {
		// TODO Auto-generated method stub
		System.out.println("updateTrashPile");
		String json = convertCardToJson("updateTrashPile", ((LinkedList<Card>)cards).peekLast());
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

	public void waitReadyToStart() {
		Gson gson = new Gson();
		String json = gson.toJson(new Message("waitReadyToStart", null));
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
		Collection<Object> object = new LinkedList<Object>();		
		Collection<String> cardsString = new LinkedList<String>();
		for(Card card : cards) {
			cardsString.add(card.getName());
		}
		String json = gson.toJson(new Message(message, cardsString));
		System.out.println(json);
		return json;
	}
	
	/**
	 * Converts all cards into a collection of strings to formats into json
	 * 
	 * @param cards
	 * @return String json
	 */
	private String convertCardsWithCostToJson(final String message, List<Card> cards, int cost) {
		Gson gson = new Gson();
		Collection<Object> object = new LinkedList<Object>();		
		Collection<String> cardsString = new LinkedList<String>();
		for(Card card : cards) {
			cardsString.add(card.getName());
		}
		object.add(cardsString);
		object.add(new Integer(cost));
		
		String json = gson.toJson(new Message(message, object));
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
}
