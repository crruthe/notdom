package com.dominion.game;

public class CardStack {
	protected boolean traceRouteToken = false;
	protected int numCards = 0;

	public CardStack(int numCards) {
		super();
		this.numCards = numCards;
	}

	public int getNumCards() {
		return numCards;
	}

	public boolean hasTraceRouteToken() {
		return traceRouteToken;
	}

	public void setTraceRouteToken(boolean hasTraceRouteToken) {
		this.traceRouteToken = hasTraceRouteToken;
	}

	public void decrementNumCards(int numCards) {
		this.numCards -= numCards;
	}
}
