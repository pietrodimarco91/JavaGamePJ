package game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

import client.actions.BuyPermitTileAction;
import client.actions.SimpleBuildEmporiumAction;
import controller.MatchActionsHandler;
import controller.MatchHandler;
import controller.Player;
import exceptions.TileNotFoundException;
import model.Board;
import model.City;
import model.Council;
import model.Councillor;
import model.PermitTile;
import model.PoliticCard;
import model.Tile;

public class TestBuildEmporiumWithPermitTile {

	@Test
	public void test() {
		Board board = new Board(4, 2, 2, 2, 2);
		MatchHandler match = new MatchHandler(0, new Date());
		match.setBoard(board);
		match.initializeMatchComponents();
		match.getPlayers().get(0).setPlayerOffline();
		for (int i = 1; i < 4; i++) {
			match.addPlayer(i);
			match.getPlayers().get(i).setPlayerOffline();
			assertEquals(6, match.getPlayers().get(i).getPoliticCards().size());
		}
		Player player = match.getPlayers().get(0);
		MatchActionsHandler actionsHandler = new MatchActionsHandler(match, board, match.getPlayers());
		Council council = board.getRegions()[0].getCouncil();
		ArrayList<String> councilColors = new ArrayList<String>();
		Queue<Councillor> councillors = council.getCouncillors();
		for (Councillor councillor : councillors) {
			councilColors.add(councillor.getColor());
		}
		player.getPoliticCards().clear();
		for (String color : councilColors) {
			player.addCardOnHand(new PoliticCard(color));
		}
		
		// Now checking the 'buyPermitTile' action
		assertEquals(4, player.getPoliticCards().size());
		actionsHandler.buyPermitTile(new BuyPermitTileAction("main", "COAST", councilColors, 1), 0);
		String s = "";
		Tile tile = null;
		try {
			tile = (PermitTile) player.getRandomUnusedPermitTile();
		} catch (TileNotFoundException e) {
			s += "error";
		}
		assertEquals("", s);
		assertNotEquals(null,tile);
		int tileId = ((PermitTile) tile).getId();

		// Now checking the 'buildEmporiumWithPermitTile'
		List<City> cities = ((PermitTile) tile).getCities();
		City city = cities.get(0);
		String cityName = String.valueOf(city.getName().charAt(0));
		player.resetTurn();
		actionsHandler.buildEmporiumWithPermitTile(new SimpleBuildEmporiumAction("main", tileId, cityName), 0);
		assertEquals(9, player.getNumberOfEmporium());
	}

}
