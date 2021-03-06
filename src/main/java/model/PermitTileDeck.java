package model;

import exceptions.InvalidSlotException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * This class represents the deck of the Business Permit Tiles of a single
 * region.
 */
public class PermitTileDeck implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The deck is represented by a Queue.
	 */
	private Queue<Tile> deck;

	/**
	 * This attribute represents one of the uncovered Permit Tiles that a player
	 * can choose from when he purchases a Permit Tile.
	 */
	private Tile uncoveredPermitTile1;

	/**
	 * This attribute represents one of the uncovered Permit Tiles that a player
	 * can choose from when he purchases a Permit Tile.
	 */
	private Tile uncoveredPermitTile2;

	/**
	 * Each Region has its own PermitTileDeck
	 */
	private Region region;

	/**
	 * The initial ID of the Permit Tiles of the deck
	 */
	private int initId;

	/**
	 * The end ID of the Permit Tile of the deck
	 */
	private int endId;

	/**
	 * The number of the tiles of the deck, this is parametric according to the
	 * number of players
	 */
	private int numberOfTiles;

	/**
	 * Default constructor
	 * 
	 * @param endId
	 * @param initId
	 */
	public PermitTileDeck(int initId, int endId) {
		deck = new LinkedList<Tile>();
		this.initId = initId;
		this.endId = endId;
		this.numberOfTiles = endId + 1;
	}
/**
 * This method generate a new permit tiles
 * @param bonusNumber
 */
	public void generatePermitTiles(int bonusNumber) {
		TileFactory tileFactory = new ConcreteTileFactory();
		for (int i = initId; i <= endId; i++) {
			deck.add(tileFactory.createPermitTile(i, region.getCities(), bonusNumber));
		}

		uncoveredPermitTile1 = deck.remove();
		uncoveredPermitTile2 = deck.remove();
	}

	/**
	 * This method allows a player to perform the correspondent Quick Move.
	 */
	public void switchPermitTiles() {
		deck.add(uncoveredPermitTile1);
		deck.add(uncoveredPermitTile2);
		uncoveredPermitTile1 = deck.remove();
		uncoveredPermitTile2 = deck.remove();
	}

	/**
	 * This method allows a player to pick up one of the uncovered Permit Tiles.
	 * 
	 * @param slot
	 *            The number of the slot where the uncovered tiles are placed.
	 *            This number should be 1 or 2.
	 * @return The permit tile associated to the chosen slot
	 * @throws InvalidSlotException
	 *             if the specified slot is different from 1 or 2.
	 */
	public Tile drawPermitTile(int slot) throws InvalidSlotException, NoSuchElementException {
		switch (slot) {
		case 1: {
			if (deck.isEmpty())
				throw new NoSuchElementException();
			Tile tempPermitTile = uncoveredPermitTile1;
			uncoveredPermitTile1 = deck.remove();
			return tempPermitTile;
		}

		case 2: {
			if (deck.isEmpty())
				throw new NoSuchElementException();
			Tile tempPermitTile = uncoveredPermitTile2;
			uncoveredPermitTile2 = deck.remove();
			return tempPermitTile;
		}
		default:
			throw new InvalidSlotException();
		}
	}

	public Queue<Tile> getDeck() {
		return this.deck;
	}

	public Tile getUnconveredPermitTile1() {
		return this.uncoveredPermitTile1;
	}

	public Tile getUnconveredPermitTile2() {
		return this.uncoveredPermitTile2;
	}

	@Override
	public String toString() {
		Iterator<Tile> iterator = deck.iterator();
		String string = "";
		string += "Region: " + region.toString() + "\n";
		string += "Number of tiles in the deck: " + numberOfTiles;
		string += "Uncovered PermitTile 1: " + uncoveredPermitTile1.toString() + "\n";
		string += "Uncovered PermitTile 2: " + uncoveredPermitTile2.toString() + "\n\n";
		while (iterator.hasNext()) {
			string += "Covered PermitTile inside deck: " + iterator.next().toString();
		}
		return string;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
}