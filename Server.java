import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

	public static ServerSocket ss;
	public static Socket s;
	public static DataInputStream din;
	public static DataOutputStream dout;
	public static String data="";
	ArrayList<ServerConnection> connections = new ArrayList<>();
	boolean isRunning=true;
	int assign=0;
	int a1=5,a2=5,a3=5,
			b1=5,b2=5,b3=5,
			c1=5,c2=5,c3=5;
	int arr[]=new int[65536];
	int sockets[]=new int[2];
	public int active=0;
	
	public Server(int PORT) {
		
		try {
			
			System.out.println("[Server] started successfully\nWaiting for Client");
			ss = new ServerSocket(PORT);
			while(active<2) {
				
				s = ss.accept();
				arr[s.getPort()]=assign;
				sockets[active]=s.getPort();
				System.out.println("[Client] "+s.getPort()+" is connected");
				ServerConnection sc=new ServerConnection(s,this);	
				sc.start();
				connections.add(sc);
				System.out.println("[Client] "+s.getPort()+" is assigned "+assign);
				System.out.println(arr[s.getPort()]);
				assign=assign^1;
				active++;
				

			}
			
			ss.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
