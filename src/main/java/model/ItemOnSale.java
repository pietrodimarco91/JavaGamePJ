package model;

import controller.Player;

public abstract class ItemOnSale {
	/**
	 * This attribute represents the seller of this item
	 */
	private Player seller;

	private int id;

	/**
	 * This attribute represents the price (coins) for this item
	 */
	private int price;
	
	public ItemOnSale(Player seller, int price) {
		this.seller=seller;
		this.price=price;
	}

	public Player getSeller() {
		return seller;
	}

	public int getPrice() {
		return price;
	}
	
	public int getId() {
		return id;
	}
	
	/**
	 * This method returns this item on sale when someone buys it
	 * @return the item contained in this ItemOnSale object
	 */
	public abstract void buyItem(Player player);
	
	/**
	 * This methods allows to state whether a player could buy a specified item or not.
	 * @return true if coins are enough, false otherwise
	 */
	public boolean hasEnoughCoins(int coins) {
		return coins>=price;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String string="";
		string+="ITEM ON SALE\n";
		string+="ID: "+this.id+", ";
		string+="Seller: "+this.seller.getNickName()+"\n";
		string+="Price: "+this.getPrice()+"\n";
		return string;
	}
}
