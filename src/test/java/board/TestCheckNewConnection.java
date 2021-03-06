package board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.City;
import model.Board;

/**
 * Tests the method that checks whether a connection between the specified
 * cities is possible or not.
 * 
 * @author Riccardo
 *
 */
public class TestCheckNewConnection {

	@Test
	public void test() {
		for (int i = 2; i <= 4; i++) {
			Board map = new Board(4, 2, 2, 2, i);
			List<City> cities = map.getMap();
			for (City city1 : cities) {
				for (City city2 : cities) {
					if (city1.getConnectedCities().contains(city2))
						assertEquals(false, map.checkPossibilityOfNewConnection(city1, city2));
					else if (city1.getConnectedCities().size() < i && city2.getConnectedCities().size() < i)
						assertEquals(true, map.checkPossibilityOfNewConnection(city1, city2));
					else
						assertEquals(false, map.checkPossibilityOfNewConnection(city1, city2));
				}
			}
		}
	}

}
