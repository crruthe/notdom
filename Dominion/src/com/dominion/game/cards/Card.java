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
		return getName().equals(other.getName());
	}
	
	@Override
	public int compareTo(Card o) {		
		if (getCost() == o.getCost())
			return getName().compareTo(o.getName());
		else
			return new Integer(getCost()).compareTo(o.getCost());
	}

	public abstract void accept(CardVisitor visitor);
	public abstract String getName();
	public abstract int getCost();	
}
