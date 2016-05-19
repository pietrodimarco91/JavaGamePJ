package model;

import java.util.*;

import controller.Player;
import exceptions.CouncillorNotFoundException;

/**
 * Created by Gabriele Bressan on 13/05/16.
 */
public class Region {
	/**
	 * Name of the region
	 */
	private String name;

	/**
	 * Council of the region
	 */
	private Council council;

	/**
	 * ArrayList of cities that are in region
	 */
	private ArrayList<City> cities;

	/**
	 * Permit Tile Deck of the region
	 */
	private PermitTileDeck deck;

	/**
	 * 
	 */
	private Tile regionBonus;

	/**
	 * Instantiates a Region with its main attributes.
	 * 
	 * @param name
	 *            The region name
	 * @param council
	 *            The region council
	 * @param permitTileDeck
	 *            The permit tile deck of this region
	 */
	public Region(String name, Council council, PermitTileDeck deck) {
		this.name = name;
		this.council = council;
		this.deck = deck;
		TileFactory tileFactory = new ConcreteTileFactory();
		this.regionBonus = tileFactory.createRegionBonusTile(5 + new Random().nextInt(5));
	}

	/**
	 * This method allows to perform the Main Move "Elect a councillor"
	 * 
	 * @param color
	 *            the color of the councillor to elect
	 */
	public void electCouncillor(String color) throws CouncillorNotFoundException {
		if (CouncillorsPool.checkPresenceOfCouncillor(color)) {
			this.council.removeCouncillor();
			this.council.addCouncillor(color);
		}
		else throw new CouncillorNotFoundException();
	}

	public PermitTileDeck getDeck() {
		return this.deck;
	}

	/**
	 * @param politicCards
	 * @return true if council is satisfied or false if the council is not
	 *         satisfied
	 */
	public boolean checkCouncilSatisfaction(ArrayList<PoliticCard> politicCards) {
		Queue<Councillor> tempCouncillors; // Create a tempCouncillors Queue and
											// I'm used iterator system
		tempCouncillors = this.council.getCouncillors();// tempCouncillors is
														// equals to the real
														// councillors present
														// in specific region
		Iterator<Councillor> iterationCouncillors = tempCouncillors.iterator();
		Councillor councillor;
		PoliticCard tempPoliticCard;

		while (iterationCouncillors.hasNext()) {
			int i;
			councillor = iterationCouncillors.next();
			for (i = 0; i < politicCards.size(); i++) {
				tempPoliticCard = politicCards.get(i);
				if (councillor.getColor() == tempPoliticCard.getColorCard()
						|| tempPoliticCard.getColorCard() == "MULTICOLOR")
					return true;
			}
			councillor = iterationCouncillors.next();
		}
		return false;
	}

	/**
	 * @param owner
	 * @return boolean value that says if one player owns all cities in one
	 *         region
	 */
	public boolean isEligibleForRegionBonus(Player owner) {
		int i;
		City tempCity;
		for (i = 0; i < cities.size(); i++) {
			tempCity = cities.get(i);
			if ((!tempCity.checkPresenceOfEmporium(owner)))
				return false;
		}
		return true;
	}

	/**
	 * @param owner
	 * @return region bonus if player is eligible for region bonus, else return
	 *         null
	 */
	public Tile winRegionBonus(Player owner) {
		if (isEligibleForRegionBonus(owner))
			return this.regionBonus;
		return null;
	}

	/**
	 * This method adds the specified cities to this region
	 * 
	 * @param cities
	 *            the cities to add to this region
	 */
	public void addCities(ArrayList<City> cities) {
		this.cities = cities;
	}

	/**
	 * 
	 * @return ArrayList of cities in regione
	 */
	public ArrayList<City> getCities() {
		return cities;
	}

}