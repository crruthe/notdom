package com.dominion.game.cards;

import com.dominion.game.visitors.CardVisitor;

public abstract class Card implements Comparable<Card> {

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (!(obj instanceof Card))
			return false;
		
		Card other = (Card) obj;
		return name.equals(other.getName());
	}
	
	@Override
	public int compareTo(Card o) {
		return name.compareTo(o.getName());
	}

	private int cost;
	private String name;
	
	public abstract void accept(CardVisitor visitor);

	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
}
