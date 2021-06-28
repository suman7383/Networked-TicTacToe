import java.net.*;
import java.io.*;
import java.util.*;


public class Client {

	ClientConnection cc;
	public boolean shouldRun=true;
	public int PORT;
	
	public Client(String IP_ADDRESS, int PORT) {
		
			try {
				
				Socket s = new Socket(IP_ADDRESS, PORT);
					System.out.println("Connected Successfully");
					cc=new ClientConnection(s,this);
					cc.start();
					DataInputStream input=new DataInputStream(System.in);

					while(shouldRun) {

						String data = input.readLine();

						if(data.equals("quit")) {
							shouldRun=false;
							cc.writeData(data);
							break;
						}

						cc.writeData(data);

					}
					

					input.close();

				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (ConnectException e){
				System.out.println("Connections are full!!");
			}	
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
	}
	

}
