package client.controller;

import java.rmi.RemoteException;

import client.view.gui.GUIMainApp;
import client.view.gui.LoaderResources;
import controller.Packet;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ClientGUIController extends ClientController {

	@Override
	public void boardConfiguration() {}

	@Override
	public void mapConfiguration() {}

	@Override
	public void performNewAction() {}

	@Override
	public void requestBoardStatus() {}

	@Override
	public void sellItemOnMarket() {}

	@Override
	public void buyItemOnMarket() {}

	@Override
	public void requestPlayerStatus() {}

	@Override
	public void chat() {}

	@Override
	public void initialConfiguration() {}

	@Override
	public void play() {}

	@Override
	public void connect() throws RemoteException {}

	@Override
	public void countDistance() {}

	@Override
	public void editConnection(String choice) {}

	@Override
	public void newConfiguration() {}

	@Override
	public void welcome(String[] args) {
		Application.launch(GUIMainApp.class, args);
	}
	public void playSound(String soundPath){
		soundPath=LoaderResources.loadPath(soundPath);
		Media media = new Media(soundPath);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
	public void sendPacketToGUIController(Packet packet) {}
}
