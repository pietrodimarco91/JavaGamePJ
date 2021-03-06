package board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.City;
import model.Board;

/**
 * Tests if the city where the king is located is correctly returned
 * @author Riccardo
 *
 */
public class TestFindKingCity {

	@Test
	public void test() {
		Board map = new Board(4,2,2,2,2);
		List<City> cities = map.getMap();
		City cityWithKing=null;
		int counter=0;
		for(City city:cities) {
			if(city.getKingIsHere()) {
				cityWithKing=city;
				counter++;
			}
		}
		assertEquals(cityWithKing,map.findKingCity());
		assertEquals(1,counter); //just one city with the king
	}

}
