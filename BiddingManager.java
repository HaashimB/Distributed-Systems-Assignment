import java.io.*;
import java.util.*;

//This class handles the following:
// - Outputs to the clients
// - Bid times
// - Updating bid items
class BiddingManager{
	
	private static ArrayList<Item> items = new ArrayList<>();
	private static ArrayList<PrintWriter> outputs = new ArrayList<>();
	private static Item it; //call Item class to handle bid item data
	private static int currentItem = 0;
	private static String message = "";
	private static boolean isBidRunning = true;
	
	BiddingManager(){
		items.clear();
		//Create array list of objects type Item with the bid item and start price
		items.add(it = new Item("Harambe",10));
		items.add(it = new Item("DatBoi",50));
		items.add(it = new Item("TheTingGoes",1000));
		items.add(it = new Item("MynamaJeff",2000));
		items.add(it = new Item("LambSauce",300));

		it = items.get(0);

		displayDefaultMessage();
	}
	

	void addPrintWriter(PrintWriter out){
		outputs.add(out);
	}

	void checkBidPrice(int bid){
		if(bid <= it.getBidPrice()) {
			setMessage("Error: new bid must be greater than the current bid amount");
		}else{
			it.setBidPrice(bid);
		}
	}

	private void printToClients(){
		for(int i = 0; i<outputs.size(); i++){
			if(outputs.get(i) != null){
				outputs.get(i).println(getMessage());
			}
		}
	}
	void displayDefaultMessage(){
		setMessage("Current Item for sale: " + it.getItemName() + "\nBidding Price: " + it.getBidPrice() + "\nEnter bid:");
	}

	private String getMessage(){
		return message;
	}

	private void setMessage(String message){
		this.message = message;
		printToClients();
	}

	boolean getIsBidRunning(){
		return isBidRunning;
	}
	private void updateBidItem(){

		if(currentItem<items.size()){
			it = items.get(currentItem);
			displayDefaultMessage();
		}else{
			isBidRunning = false;
			setMessage("The Auction is over type 'QUIT' to exit");
		}

	}

	void checkTime(int sec){
		if(sec == 0){
			if(it.getBidPrice() == it.getStartPrice()){
				System.out.println("adding " + it.getItemName() + " to arraylist");
				items.add(items.get(currentItem));
				setMessage("No one bid on the item");
				currentItem++;
			}else{
				System.out.println("removing " + it.getItemName() + " from arraylist");
				items.remove(items.get(currentItem));
				setMessage("The person bidding " + it.getBidPrice() + " won the bid!");
			}
			updateBidItem();
		}else if(sec == 5){
			setMessage("Five Seconds remaining in bid!");
		}
	}
}
class CheckTime extends Thread {
	private BiddingManager bid = new BiddingManager();
	private int sec = 15;
	public void run() {
		while(bid.getIsBidRunning()){
			try{
				Thread.sleep(1000);
			}catch(Exception e){
				System.out.println("Interrupt Exception");
			}
			if(sec == -1){
				sec = 15;
			}
			sec--;
			bid.checkTime(sec);
		}
	}
}