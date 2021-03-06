package client.view.gui.configurator;
import client.controller.ClientGUIController;
import client.view.gui.LoaderResources;
import controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 * It Controls the market filling the grids of the FXML associated file
 * and listening the events generated on the window
 *
 *
 */
public class MarketController extends ClientGUIController{

    @FXML
    private GridPane yourAssistant;

    @FXML
    private GridPane yourPolitic;

    @FXML
    private GridPane yourPermit;

    @FXML
    private GridPane marketAssistant;

    @FXML
    private GridPane marketPolitic;

    @FXML
    private GridPane marketPermit;

    @FXML
    private TextField sellingPrice;




    private Stage stage;
    private String css;
    private ServerSideConnectorInt connector;
    private Player player;
    private List<ItemOnSale> market;
    private String itemOnSaleTemp="";
    private String colorItem="";
    private int idTemp;
    private int itemId;


    public void setStage(Stage stage) {
        sellingPrice.setOnMouseClicked(event -> {
            clean();
        });
        itemId=-1;
        css = LoaderResources.loadPath("/configurator/style.css");
        this.stage = stage;
    }

    private void clean() {
        sellingPrice.setText("");
    }

    /**
     * The connector is used for the communication with the server
     * @param connector
     *
     */
    public void setConnector(ServerSideConnectorInt connector) {
        this.connector = connector;
    }

    /**
     * It fills the panes with the items of the player
     * @param player
     *
     */
    public void setPlayer(Player player) {
        this.player = player;
        setAssistant(player.getNumberOfAssistants());
        setPoliticCard(player.getPoliticCards());
        setPermitTile(player.getUnusedPermitTile());
    }

    /**
     * It fills the panes with the players items that can be bought by the specific player
     * @param market
     *
     */
    public void setMarket(List<ItemOnSale> market) {
        this.market = market;
        List<AssistantOnSale> assistantOnSales=new ArrayList<>();
        List<PoliticCardOnSale> politicCardOnSales=new ArrayList<>();
        List<PermitTileOnSale> permitTileOnSales=new ArrayList<>();
        if(market!=null){
            for(ItemOnSale itemOnSale:market){
                if(itemOnSale instanceof AssistantOnSale){
                    assistantOnSales.add((AssistantOnSale)itemOnSale);
                }
                if(itemOnSale instanceof PoliticCardOnSale){
                    politicCardOnSales.add((PoliticCardOnSale)itemOnSale);
                }
                if(itemOnSale instanceof PermitTileOnSale){
                    permitTileOnSales.add((PermitTileOnSale)itemOnSale);
                }
            }

            setMarketAssistant(assistantOnSales);
            setMarketPoliticCard(politicCardOnSales);
            setMarketPermitTile(permitTileOnSales);
        }
    }




    /**
     * It fills the pane with the assistants of the player
     * @param assistant
     *
     */
    public void setAssistant(int assistant) {
        int count=assistant;

        for (int i = 0; i < 10 && count>0; i++) {
            for (int j = 0; j < 4 && count > 0; j++) {

                Pane pane = new Pane();
                pane.getStylesheets().add(css);
                pane.getStyleClass().add("assistant");
                yourAssistant.add(pane, j, i);
                pane.setOnMouseClicked(event -> {
                    yourAssistantClicked();
                });
                count--;
            }
        }
    }

    /**
     * It fills the pane with the assistants of the players
     * @param assistant
     *
     */
    public void setMarketAssistant(List<AssistantOnSale> assistant) {
        int count=0;


        for (int i = 0; i < 40 && count<assistant.size(); i++) {
            for (int j = 0; j < 4 && count<assistant.size(); j++) {
                int itemId;
                itemId=assistant.get(count).getId();
                VBox vBox=new VBox();
                Label label=new Label(assistant.get(count).getSeller().getNickName());
                Label price=new Label(String.valueOf(assistant.get(count).getPrice()+"$"));
                price.getStyleClass().add("marketLabel");
                label.getStyleClass().add("marketLabel");
                vBox.getStylesheets().add(css);
                vBox.getStyleClass().add("assistant");
                vBox.getChildren().add(label);
                vBox.getChildren().add(price);
                marketAssistant.add(vBox, j, i);
                vBox.setOnMouseClicked(event -> {
                    marketItemClicked(itemId);
                });
                count++;
            }
        }

    }

    /**
     * It fills the pane with the politic cards of the player
     * @param politicCards
     *
     */
    public void setPoliticCard(ArrayList<PoliticCard> politicCards) {
        int countCards = 0;
        boolean stop = false;

        for (int i = 0; i < 10 && !stop; i++) {
            for (int j = 0; j < 4 && !stop; j++) {
                if (countCards < politicCards.size()) {
                    Pane pane = new Pane();
                    pane.getStylesheets().add(css);
                    String color=politicCards.get(countCards).getColorCard();
                    setStyle(color,pane);
                    yourPolitic.add(pane, j, i);
                    pane.setOnMouseClicked(event -> {
                        yourPoliticClicked(color);
                    });
                    countCards++;
                } else
                    stop = true;

            }
        }
    }

    /**
     * It fills the pane with the politic cards of the players
     * @param politicCardOnSales
     *
     */
    private void setMarketPoliticCard(List<PoliticCardOnSale> politicCardOnSales) {
        int countCards = 0;
        boolean stop = false;

        for (int i = 0; i < 10 && !stop; i++) {
            for (int j = 0; j < 4 && !stop; j++) {
                if (countCards < politicCardOnSales.size()) {
                    int itemId;
                    itemId=politicCardOnSales.get(countCards).getId();
                    VBox vBox=new VBox();
                    Label label=new Label(politicCardOnSales.get(countCards).getSeller().getNickName());
                    Label price=new Label(String.valueOf(String.valueOf(politicCardOnSales.get(countCards).getPrice()+"$")));
                    price.getStyleClass().add("marketLabel");
                    label.getStyleClass().add("marketLabel");
                    vBox.getStylesheets().add(css);
                    setStyle(politicCardOnSales.get(countCards).getColor(),vBox);
                    vBox.getChildren().add(label);
                    vBox.getChildren().add(price);
                    String color=politicCardOnSales.get(countCards).getColor();
                    setStyle(color,vBox);
                    marketPolitic.add(vBox, j, i);
                    vBox.setOnMouseClicked(event -> {
                        marketItemClicked(itemId);
                    });
                    countCards++;
                } else
                    stop = true;

            }
        }
    }

    private void setStyle(String color, Pane pane) {
        switch (color) {
            case "PINK":
                pane.getStyleClass().add("pinkCard");
                break;
            case "PURPLE":
                pane.getStyleClass().add("purpleCard");
                break;
            case "BLACK":
                pane.getStyleClass().add("blackCard");
                break;
            case "BLUE":
                pane.getStyleClass().add("blueCard");
                break;
            case "WHITE":
                pane.getStyleClass().add("whiteCard");
                break;
            case "ORANGE":
                pane.getStyleClass().add("orangeCard");
                break;
            case "MULTICOLOR":
                pane.getStyleClass().add("multicolorCard");
                break;
        }
    }


    /**
     * It fills the pane with the permit tiles of the player
     * @param permitTile
     *
     */
    public void setPermitTile(ArrayList<Tile> permitTile) {
        int countCards = 0;
        int idCard;
        List<City>cardCity;
        ArrayList<String>cardBonus;
        boolean stop = false;

        for (int i = 0; i < 10 && !stop; i++) {
            for (int j = 0; j < 4 && !stop; j++) {
                if(countCards<permitTile.size()){

                    PermitTile tempTile=(PermitTile)permitTile.get(countCards);
                    idCard=tempTile.getId();
                    cardCity=tempTile.getCities();
                    cardBonus=tempTile.getBonus();
                    VBox pane = new VBox();
                    pane.getStylesheets().add(css);
                    pane.getStyleClass().add("permitTile");
                    Label id = new Label("ID: "+idCard);
                    id.getStylesheets().add(css);
                    id.getStyleClass().add("id");

                    pane.getChildren().add(id);
                    String city="\n\n\nCity:";
                    for(int k=0;k<cardCity.size();k++){
                        city+=cardCity.get(k).getName().charAt(0)+",";
                    }
                    Label cityName = new Label(city);
                    cityName.getStylesheets().add(css);
                    cityName.getStyleClass().add("cityPermitTile");
                    pane.getChildren().add(cityName);

                    String bonus="\n\n\n\nBonus:\n";
                    for(int k=0;k<cardBonus.size();k++){
                        bonus+=cardBonus.get(k)+"\n";
                    }
                    Label cityBonus =new Label(bonus);
                    cityBonus.getStylesheets().add(css);
                    cityBonus.getStyleClass().add("bonus");
                    pane.getChildren().add(cityBonus);
                    String finalBonus = bonus;
                    yourPermit.add(pane, j, i);
                    pane.setOnMouseClicked(event -> {
                        yourPermitTileClicked(tempTile.getId());
                    });
                    countCards++;

                }else
                    stop=false;
            }
        }
    }

    /**
     * It fills the pane with the permit tiles of the players
     * @param marketPermitTile
     *
     */
    public void setMarketPermitTile(List<PermitTileOnSale> marketPermitTile) {
        int countCards = 0;
        int idCard;
        List<City>cardCity;
        ArrayList<String>cardBonus;
        boolean stop = false;


        for (int i = 0; i < 10 && !stop; i++) {
            for (int j = 0; j < 4 && !stop; j++) {
                if(countCards<marketPermitTile.size()){
                    int itemId;

                    PermitTile tempTile=(PermitTile)marketPermitTile.get(countCards).getTile();
                    itemId=marketPermitTile.get(countCards).getId();
                    idCard=tempTile.getId();
                    cardCity=tempTile.getCities();
                    cardBonus=tempTile.getBonus();
                    VBox vBox = new VBox();
                    vBox.getStylesheets().add(css);
                    vBox.getStyleClass().add("permitTile");
                    Label id = new Label("ID: "+idCard);
                    Label price = new Label("Price:"+marketPermitTile.get(countCards).getPrice());
                    Label seller = new Label("Price:"+marketPermitTile.get(countCards).getSeller().getNickName());
                    id.getStylesheets().add(css);
                    id.getStyleClass().add("id");

                    String city="\n\n\nCity:";
                    for(int k=0;k<cardCity.size();k++){
                        city+=cardCity.get(k).getName().charAt(0)+",";
                    }
                    Label cityName = new Label(city);
                    cityName.getStylesheets().add(css);
                    cityName.getStyleClass().add("cityPermitTile");


                    String bonus="\n\n\n\nBonus:\n";
                    for(int k=0;k<cardBonus.size();k++){
                        bonus+=cardBonus.get(k)+"\n";
                    }
                    Label cityBonus =new Label(bonus);
                    cityBonus.getStylesheets().add(css);
                    cityBonus.getStyleClass().add("bonus");

                    vBox.getChildren().add(id);
                    vBox.getChildren().add(cityBonus);
                    vBox.getChildren().add(cityName);
                    vBox.getChildren().add(seller);
                    vBox.getChildren().add(price);

                    marketPermit.add(vBox, j, i);
                    vBox.setOnMouseClicked(event -> {
                        marketItemClicked(itemId);
                    });
                    countCards++;

                }else
                    stop=false;
            }
        }
    }

    private void marketItemClicked(int itemId) {
        this.itemId=itemId;
    }



    private void yourAssistantClicked() {
        itemOnSaleTemp="ASSISTANT";
    }

    private void yourPoliticClicked( String color) {
        itemOnSaleTemp="POLITIC";
        this.colorItem=color;
    }

    private void yourPermitTileClicked(int id) {
        itemOnSaleTemp="TILE";
        this.idTemp=id;
    }


    /**
     * It handle the buy button click creating a packet that contains the itemId of the item wanted,
     * this packet is sent to the server that will check the transition
     * @throws RemoteException
     */
    @Override
	public void buyItemOnMarket() {
    	super.playSound("audio/buttonPressed.mp3");
        if(itemId==-1){
            String error = "";
            error += "Click an Item in the Market";
            showAlert(error);
            return;
        }

        try {
            connector.sendToServer(new Packet(new MarketEventBuy(itemId)));
            stage.close();

        } catch (RemoteException e) {
            showAlert(e.getMessage());
        }
    }

    /**
     * It handle the sell button click creating a packet that contains the information of the item sold,
     * this packet is sent to the server that will check the transition
     *
     * @throws RemoteException
     * @throws NumberFormatException if the TextField is not set up correctly
     */
    @Override
    public void sellItemOnMarket() {
    	super.playSound("audio/buttonPressed.mp3");
        if (sellingPrice.getText().equals("") || itemOnSaleTemp.equals("")) {
            String error = "";

            if (sellingPrice.getText().equals("")) {
                error += "Insert a selling price";
            }
            if (itemOnSaleTemp.equals("")) {
                error += "Choose item to sell";
            }
            showAlert(error);
            return;
        }
        try {
            switch (itemOnSaleTemp) {
                case "ASSISTANT":
                    connector.sendToServer(new Packet(new MarketEventSell(Integer.parseInt(sellingPrice.getText()))));
                    stage.close();
                    break;
                case "POLITIC":
                    connector.sendToServer(new Packet(new MarketEventSell(colorItem, Integer.parseInt(sellingPrice.getText()))));
                    stage.close();
                    break;
                case "TILE":
                    connector.sendToServer(new Packet(new MarketEventSell(idTemp, Integer.parseInt(sellingPrice.getText()))));
                    stage.close();
                    break;
            }

        } catch (RemoteException e) {
        	showAlert(e.getMessage());
        } catch (NumberFormatException e) {
            showAlert("Expected Integer!");
        }
    }

    private void showAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle("Ops...");
        alert.setHeaderText("Error!");
        alert.setContentText(error);
        alert.showAndWait();
    }


}
