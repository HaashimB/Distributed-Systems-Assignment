class Item{
	
	private String itemName;
	private int startPrice;
	private int bidPrice;

	
	Item(String itemName, int startPrice){
	this.itemName = itemName;
	this.startPrice = startPrice;
	this.bidPrice = this.startPrice;
	}
	
	String getItemName(){
		return itemName;
	}

	int getBidPrice(){
		return bidPrice;
	}

	int getStartPrice(){
	    return startPrice;
    }
	
	void setBidPrice(int bidPrice){
		this.bidPrice = bidPrice;
	}

}