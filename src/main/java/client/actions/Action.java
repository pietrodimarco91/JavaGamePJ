package client.actions;

import java.io.Serializable;

/**
 * This abstract class is the father of the concrete classes that represent the
 * actions that a Client will perform
 * 
 * @author Riccardo
 *
 */
public abstract class Action implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The type of action is a string which can be only "main" or "quick"
	 */
	private String typeOfAction;

	/**
	 * The action ID of the specified category (main or quick)
	 */
	private int actionID;

	public Action(String typeOfAction, int actionID) {
		this.typeOfAction = typeOfAction;
		this.actionID = actionID;
	}
	
	public String getTypeOfAction() {
		return this.typeOfAction;
	}
	
	public int getActionID() {
		return this.actionID;
	}
}
