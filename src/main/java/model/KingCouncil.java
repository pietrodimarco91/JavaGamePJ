package model;

import exceptions.CouncillorNotFoundException;

/**
 * Created by Gabriele Bressan on 13/05/16.
 */
public class KingCouncil extends Council{
	/**
	 * Default constructor:
	 */
	public KingCouncil(CouncillorsPool pool) {
		super(pool);
	}

	/**
	 * This method allows to perform the Main Move "Elect a councillor"
	 * 
	 * @param color
	 *            the color of the councillor to elect
	 * 
	 */
	public void electCouncillor(String color) throws CouncillorNotFoundException {
		if (pool.checkPresenceOfCouncillor(color)) {
			this.removeCouncillor();
			this.addCouncillor(color);
		} else
			throw new CouncillorNotFoundException();
	}
}