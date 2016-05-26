package model;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import exceptions.ConfigAlreadyExistingException;

/**
 * This class is responsible for managing the configuration file, from which the
 * configurations of the board will be loaded and where they will be saved.
 * 
 * @author Riccardo
 *
 */
public class ConfigFileManager {

	private File file;

	private final String filename = "config/board.config";

	private ObjectOutputStream outputStream;

	private ObjectInputStream inputStream;

	private int numberOfConfigurations;

	public ConfigFileManager() {
		this.numberOfConfigurations = 0;
		this.file = new File(filename);
		openFile();
	}

	public void createConfiguration(int numberOfPlayers, int rewardTokenBonusNumber, int permitTileBonusNumber,
			int nobilityTrackBonusNumber, int linksBetweenCities) throws ConfigAlreadyExistingException {
		if (configAlreadyExists(numberOfPlayers, rewardTokenBonusNumber, permitTileBonusNumber,
				nobilityTrackBonusNumber, linksBetweenCities)) {
			throw new ConfigAlreadyExistingException();
		}
		numberOfConfigurations++;
		try {
			outputStream.writeObject(new ConfigObject(numberOfConfigurations, numberOfPlayers, rewardTokenBonusNumber,
					permitTileBonusNumber, nobilityTrackBonusNumber, linksBetweenCities));
		} catch (IOException e) {
			System.out.println("Error: cannot save the configuration in file " + filename);
		}
	}

	public ArrayList<ConfigObject> getConfigurations() {
		ArrayList<ConfigObject> configurations = new ArrayList<ConfigObject>();
		try {
			inputStream = new ObjectInputStream(new FileInputStream(file));
			while (true) {
				configurations.add((ConfigObject) inputStream.readObject());
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Error in handling the class type");
			System.exit(0);
		} catch (EOFException e) {
			// just to break from the cycle and catch the end of file
		} catch (IOException e) {
			System.out.println("Error while reading the content of the file " + filename);
			System.exit(0);
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			System.out.println("Error while closing the input stream");
		}
		return configurations;
	}

	public boolean configAlreadyExists(int numberOfPlayers, int rewardTokenBonusNumber, int permitTileBonusNumber,
			int nobilityTrackBonusNumber, int linksBetweenCities) {
		ArrayList<ConfigObject> configs = getConfigurations();
		for (ConfigObject config : configs) {
			if (config.getLinksBetweenCities() == linksBetweenCities
					&& config.getNobilityTrackBonusNumber() == nobilityTrackBonusNumber
					&& config.getNumberOfPlayers() == numberOfPlayers
					&& config.getPermitTileBonusNumber() == permitTileBonusNumber
					&& config.getRewardTokenBonusNumber() == rewardTokenBonusNumber)
				return true;
		}
		return false;
	}

	public void openFile() {
		if (!file.exists()) {
			try {
				outputStream = new ObjectOutputStream(new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				System.out.println("Error while opening the file!");
				System.exit(0);
			} catch (IOException e) {
				System.out.println("Error while reading the file!");
				System.exit(0);
			}
		} else {
			try {
				outputStream = new AppendableObjectOutputStream(new FileOutputStream(file, true));
			} catch (FileNotFoundException e) {
				System.out.println("Error while opening the file!");
				System.exit(0);
			} catch (IOException e) {
				System.out.println("Error while reading the file!");
				System.exit(0);
			}
		}

	}

	public void closeFile() {
		try {
			outputStream.close();
		} catch (IOException e) {
			System.out.println("Error while closing the file!");
		}
	}

}
