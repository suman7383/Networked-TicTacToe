import java.net.*;
import java.io.*;
import java.util.*;

public class ServerConnection extends Thread{
	
	Socket socket;
	Server s;
	DataInputStream din;
	DataOutputStream dout;
	String data="";
	public boolean shouldRun=true;

	
	public ServerConnection(Socket socket, Server s) {
		
		super("ServerConnectionThread");
		this.socket=socket;
		this.s = s;
		try {
			din =new DataInputStream(socket.getInputStream());
			dout =new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String readData(Socket socket) {
		
		try {
			
			String data =din.readUTF();
			System.out.println("Client sent --> "+ data);
//			System.out.println("Sending msg back to client");
				
			return data;
			
		}
		
		catch (SocketException e) {
			System.out.println("One or more Players are disconnected. Restart the game!!!");
			return "One Player is Disconnected. You Won";
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void writeData(String data) {
		
		try {
//			System.out.println("sending data "+data);
			dout.writeUTF(data);
			dout.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void writeDataToAll(String data) {
		
			
		for(int idx=0; idx<s.connections.size(); idx++) {
			
			ServerConnection sc = s.connections.get(idx);
			
				sc.writeData(data);
		
		}
	}
	
	public boolean isWinner(int a1, int a2, int a3,
			int b1, int b2, int b3,
			int c1, int c2, int c3,
			int player) 
	{

		if((a1==s.arr[player] && a2==s.arr[player]  && a3==s.arr[player] )
				|| (a1==s.arr[player]  && b1==s.arr[player]  && c1==s.arr[player] )
				|| (c1==s.arr[player]  && c2==s.arr[player] && c3==s.arr[player] )
				|| (a3==s.arr[player]  && b3==s.arr[player] && c3==s.arr[player] )
				|| (a1==s.arr[player]  && b2==s.arr[player] && c3==s.arr[player] )
				|| (a3==s.arr[player]  && b2==s.arr[player]  && c1==s.arr[player] )
				|| (a2==s.arr[player]  && b2==s.arr[player] && c2==s.arr[player] )
				|| (b1==s.arr[player]  && b2==s.arr[player] && b3==s.arr[player] )) 
		{

			return true;

		}else {

			return false;
		}

	}
	
	public void getMove(String input, int player) {
		
		if(input.contains("a1")) {
			s.a1=s.arr[player];
		}
		if(input.contains("a2")) {
			s.a2=s.arr[player];
		}
		if(input.contains("a3")) {
			s.a3=s.arr[player];
		}
		if(input.contains("b1")) {
			s.b1=s.arr[player];
		}
		if(input.contains("b2")) {
			s.b2=s.arr[player];
		}
		if(input.contains("b3")) {
			s.b3=s.arr[player];
		}
		if(input.contains("c1")) {
			s.c1=s.arr[player];
		}
		if(input.contains("c2")) {
			s.c2=s.arr[player];
		}
		if(input.contains("c3")) {
			s.c3=s.arr[player];
		}
	}
	
	public boolean isValid(String input) {
		
		if((input.contains("a") || input.contains("b") || input.contains("c"))
			&& (Integer.parseInt(input.substring(1,2))>=1 && Integer.parseInt(input.substring(1,2))<=3)) {
			
			if(input.contains("a1") && s.a1==5) {
				return true;
			}
			else if(input.contains("a2") && s.a2==5) {
			
				return true;
			}
			else if(input.contains("a3") && s.a3==5) {
			
				return true;
			}
			else if(input.contains("b1") && s.b1==5) {
				
				return true;
			}
			else if(input.contains("b2") && s.b2==5) {
				
				return true;
			}
			else if(input.contains("b3") && s.b3==5) {
			
				return true;
			}
			else if(input.contains("c1") && s.c1==5) {
			
				return true;
			}
			else if(input.contains("c2") && s.c2==5) {
			
				return true;
			}
			else if(input.contains("c3") && s.c3==5) {
			
				return true;
			}
			
			return false;
			
		}else {
			return false;
		}
		
		
	}
	
	public void run() {
		// TODO Auto-generated method stub
		int close=0;

		
		while(true) {
			
			int size=s.sockets.length;
			data=readData(socket);

			if(data!=null) {
				if(data.toLowerCase().contains("quit")) {
					writeDataToAll(data);
					shouldRun=false;
					break;
				}

				if(isValid(data)) {

					if(!data.toLowerCase().contains("quit")) {
						String move=data.substring(0, 2);
						int C_Port=Integer.valueOf(data.substring(2, data.length()));
						//					System.out.println("Getting move");
						getMove(move, C_Port);
						//					System.out.println(move + " "+ C_Port);
						System.out.println(s.a1+" "+ s.a2 +" "+s.a3+" "+ s.b1+" "+s.b2+" "+s.b3+" "+s.c1+" "+s.c2+" "+s.c3+" ");
					}
					writeDataToAll(data);	
					for(int i=0; i<size; i++) {
						int port=s.sockets[i];
						if(isWinner(s.a1,s.a2,s.a3,s.b1,s.b2,s.b3,s.c1,s.c2,s.c3,port)) {
							writeDataToAll("winner"+port);
							break;

						}
					}

				}else {
					writeDataToAll("invalid"+socket.getPort());
				}


			}else {
				s.isRunning=false;
				break;	
			}

		}
	try {

			System.out.println("Thread Closing");
			din.close();
			dout.close();
			socket.close();
			
		} 

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
