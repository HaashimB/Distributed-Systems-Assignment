import java.io.*;
import java.net.*;
import java.util.*;

public class Client
{
	private static InetAddress host;
	private static final int PORT = 1234;

	public static void main(String[] args)
	{
		try
		{
			host = InetAddress.getLocalHost();
		}catch(UnknownHostException uhEx)
		{
			System.out.println("\nHost ID not found!\n");
			System.exit(1);
		}

		try{
			Socket socket = new Socket(host,PORT);
			ReceiveMessage message = new ReceiveMessage(socket);
			message.start();
			sendMessages(socket);
		}catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
	}

	private static void sendMessages(Socket socket)
	{

		try
		{
			PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);
			//Set up stream for keyboard entry...
			Scanner userEntry = new Scanner(System.in);
			String message;
			System.out.println("Enter message ('QUIT' to exit)\n");
			do
			{
				message =  userEntry.nextLine();
				networkOutput.println(message);
			}while (!message.equals("QUIT"));
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
		finally
		{
			try
			{
				System.out.println("\nClosing connection...");
				socket.close();
			}
			catch(IOException ioEx)
			{
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}
}

class ReceiveMessage extends Thread
{
	private Scanner input;
	ReceiveMessage(Socket client)
	{
		//Set up reference to associated socket...
		try
		{
			input = new Scanner(client.getInputStream());
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
	}

	public void run()
	{
		String received;
		do
		{
			received = input.nextLine();
			System.out.println(received);

		}while (!received.equals("QUIT"));
	}
}


