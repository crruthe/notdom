package com.dominion.game.cards;

import java.util.List;

import com.dominion.game.modifiers.CardModifier;
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
	
	/**
	 * Return an instantiation of the card for the given class type
	 * @param cardClass
	 * @return
	 */
	static public Card getCard(Class<? extends Card> cardClass) {
		try {
			return cardClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Applies modifiers to the card in a pipeline and returns the final modified card
	 * 
	 * @param card
	 * @return
	 */
	public Card modifyCard(List<CardModifier> modifiers) {
		Card mCard = this;
		for (CardModifier modifier : modifiers) {
			mCard = modifier.modify(mCard);
		}
		return mCard;
	}	
	
	@Override
	public String toString() {
		return getName();
	}
}
