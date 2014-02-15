package com.dominion.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.dominion.game.cards.Card;

public class ImmutableGameBoard {
	private final int estateStackSize;
	private final int duchyStackSize;
	private final int provinceStackSize;
	
	private final int curseStackSize;
	
	private final LinkedList<Card> trashPile;
	
	private final HashMap<String, Integer> kingdomCards;	

	public ImmutableGameBoard(GameBoard gameBoard) {
		this.estateStackSize = gameBoard.getEstateStackSize();
		this.duchyStackSize = gameBoard.getDuchyStackSize();
		this.provinceStackSize = gameBoard.getProvinceStackSize();
		this.curseStackSize = gameBoard.getCurseStackSize();
		this.trashPile = new LinkedList<Card>(gameBoard.getTrashPile());
		this.kingdomCards = new HashMap<String, Integer>(gameBoard.getKingdomCards());
	}
	
	public int getEstateStackSize() {
		return estateStackSize;
	}

	public int getDuchyStackSize() {
		return duchyStackSize;
	}

	public int getProvinceStackSize() {
		return provinceStackSize;
	}

	public int getCurseStackSize() {
		return curseStackSize;
	}

	public HashMap<String, Integer> getKingdomCards() {
		return kingdomCards;
	}

	public List<Card> getTrashPile() {
		return trashPile;
	}
}
