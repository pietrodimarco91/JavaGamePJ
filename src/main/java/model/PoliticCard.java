package model;

import java.io.Serializable;

/**
 * Created by Gabriele Bressan on 13/05/16.
 */
public class PoliticCard implements Serializable {
	/**
	 * Variable used for serialization
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Color of Politic Card
	 */
	private String color;

	/**
	 * Default constructor that Set the colour of the Politic Card
	 */
	public PoliticCard() {
		this.color = CouncillorColors.getRandomColor();
	}
	/**
	 * 
	 * @overloading constructor
	 * @param one string of color
	 * In this case you can choose the color of politic card and this method is used in player cardsToCouncilSatisfaction method.
	 */
	public PoliticCard(String color) {
		this.color = color;
	}

	/**
	 * @return String of card color
	 */
	public String getColorCard() {
		return this.color;
	}
	
	@Override
	public String toString() {
		return this.color;
	}
	
	
}