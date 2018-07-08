import java.io.*;
import java.net.*;
import java.util.*;

public class Server
{
	private static ServerSocket serverSocket;
	private static final int PORT = 1234;
	public static void main(String[] args) throws IOException
	{
		CheckTime ct = new CheckTime();
		ct.start();
		try
		{
			serverSocket = new ServerSocket(PORT);
		}
		catch (IOException ioEx)
		{
			System.out.println("\nUnable to set up port!");
			System.exit(1);
		}

		while(true){
			
			//Wait for client...
			Socket client = serverSocket.accept();
			System.out.println("\nNew client accepted.\n");
			
			//Create a thread to handle communication with
			//this client and pass the constructor for this
			//thread a reference to the relevant socket...
			ClientHandler handler = new ClientHandler(client);

			handler.start();//As usual, this method calls run.
		}
	}
}

class ClientHandler extends Thread
{
	private Socket client;
	private Scanner input;
	private BiddingManager bid = new BiddingManager();
	ClientHandler(Socket socket)
	{
		client = socket;
		try
		{
			input = new Scanner(client.getInputStream());
			PrintWriter output = new PrintWriter(client.getOutputStream(),true);
			bid.addPrintWriter(output);
			
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
	}

	public void run()
	{
		String received;
		
		do{
			bid.displayDefaultMessage();
			received = input.nextLine();
			int num = Integer.parseInt(received);
			bid.checkBidPrice(num);
		}while(!received.equals("QUIT"));
		try
		{
			if (client!=null)
			{
				System.out.println("Closing down connection...");
				client.close();
			}
		}
		catch(IOException ioEx)
		{
			System.out.println("Unable to disconnect!");
		}
	}
}



