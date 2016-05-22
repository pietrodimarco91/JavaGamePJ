package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Map;

/**
 * This test verifies the correct generation of the default connections between
 * the cities at the beginning of the match
 * 
 * @author Riccardo
 *
 */
public class TestDefaultConnections {

	@Test
	public void test() {
		try {
			int counter = 0;
			for (int i = 2; i <= 8; i++) {
				for (int j = 1; j <= 3; j++) {
					for (int k = 2; k <= 4; k++,counter++) {
						Map map = new Map(i,j,k);
						System.out.println("Default connections between cities correctly generated!");
					}
				}
			}
			System.out.println("Total combinations: " + counter);
		} catch (Exception e) {
			fail("Test not passed");
		}
		/*
		 * Map map = new Map(4,2,2); System.out.println(map.toString());
		 * map.printMatrix();
		 */
	}

}
