package controller;

import client.actions.Action;
import filehandler.ConfigObject;

import java.io.Serializable;

/**
 * The packet is the main object used for the communication Client-Server, it contains all the actions
 * and requests that the clients can perform.
 * Are implemented different types of constructor that are used to set up different packets
 *
 */
public class Packet implements Serializable {

	private static final long serialVersionUID = 1L;

	private UpdateState update;

    /**
     *The header is used by the clients and by the server to decode the content of the packet
     */
	private String header;

    private Action action;

    private String messageString;

    private Integer configId;

    private ConfigObject configObject;

    private MarketEvent marketEvent;

    public Packet(ConfigObject configObject){
        header="CONFIGOBJECT";
        this.configObject=configObject;
    }
    
    public Packet() {
    	header="BOARDSTATUS";
    }

    public Packet(Action action){
        header="ACTION";
        this.action=action;
    }

    public Packet(String city1, String city2,String typeOfAction){
        if(typeOfAction.equals("ADD"))
        header="ADDLINK";
        else if(typeOfAction.equals("REMOVE"))
        	header="REMOVELINK";
        else if(typeOfAction.equals("COUNTDISTANCE"))
        	header="COUNTDISTANCE";
        messageString=city1+" "+city2;
    }

    public Packet(String messageString){
        header="MESSAGESTRING";
        this.messageString=messageString;
    }
    
    public Packet(UpdateState update) {
    	header="UPDATE";
    	this.update=update;
    }
    
    public Packet(String messageString, String code) {
    	if(code.equals("***")) {
    		header="CHAT";
    		this.messageString=messageString;
    	}
    }

    public Packet(Integer configId){
        header="CONFIGID";
        this.configId=configId;
    }

    public Packet(MarketEvent marketEvent){
        header="MARKET";
        this.marketEvent=marketEvent;
    }
    
    public UpdateState getUpdate() {
    	return this.update;
    }
    
    public ConfigObject getConfigObject() {
        return this.configObject;
    }

    public String getHeader() {
        return this.header;
    }

    public Action getAction() {
        return this.action;
    }

    public String getMessageString(){
        return this.messageString;
    }

    public int getConfigId() {
        return this.configId;
    }

    public MarketEvent getMarketEvent() {
        return this.marketEvent;
    }
}
