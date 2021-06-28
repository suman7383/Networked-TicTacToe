import java.net.*;
import java.io.*;

public class ClientConnection extends Thread{

	Socket s;
	Client c;
	int PORT;
	int Rev_PORT;
	DataInputStream din;
	DataOutputStream dout;
	String a1=" ",a2=" ",a3=" ",
			b1=" ",b2=" ",b3=" ",
			c1=" ",c2=" ",c3=" "
			,player="",ThreadData="";

	
	
	public ClientConnection(Socket s, Client c) {
		this.s=s;
		this.c=c;
		this.PORT=c.PORT;
		try {
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PORT=s.getLocalPort();
	}
	
	public void writeData(String data) {
		
		try {
			if(Rev_PORT!=PORT) {
			
				dout.writeUTF(data+PORT);
				dout.flush();

			}else if(ThreadData.contains("invalid")) {
				if(Integer.parseInt(ThreadData.substring(7, ThreadData.length()))==PORT) {
					System.out.println("Invalid Move. Try Again!!");
				}else {
					System.out.println("Not your turn!!!");
				}
				
			}else {
				System.out.println("Not your turn!!!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void drawBoard(String a1, String a2, String a3,
			String b1, String b2, String b3,
			String c1, String c2, String c3) 
	{

		System.out.println("   1  2  3");
		System.out.println();
		System.out.println("A  "+a1+" |"+a2+" |"+a3);
		System.out.println("   --------");
		System.out.println("B  "+b1+" |"+b2+" |"+b3);
		System.out.println("   --------");
		System.out.println("C  "+c1+" |"+c2+" |"+c3);
	}
	
	public void getInput(String input) {
		
		if(input.contains("a1")) {
			a1=player;
		}
		if(input.contains("a2")) {
			a2=player;
		}
		if(input.contains("a3")) {
			a3=player;
		}
		if(input.contains("b1")) {
			b1=player;
		}
		if(input.contains("b2")) {
			b2=player;
		}
		if(input.contains("b3")) {
			b3=player;
		}
		if(input.contains("c1")) {
			c1=player;
		}
		if(input.contains("c2")) {
			c2=player;
		}
		if(input.contains("c3")) {
			c3=player;
		}
	}
	
	public void getPlayer(String data) {
		
		String temp=data.substring(2, data.length());
		int tempData=Integer.parseInt(temp);
		Rev_PORT=tempData;
//		System.out.println(temp);
		
		if(tempData==PORT) {
			player="X";
		}
		else {
			player="O";
		}
	}
	
	public void getWinner(String data) {
		int temp=Integer.parseInt(data.substring(6, data.length()));
		
		if(temp==PORT) {
			System.out.println("You Won!!!");
		}else {
			System.out.println("You Lost!!!");
		}
	}
	
	public void run() {
		
		try {
				
			while(c.shouldRun) {
				
				ThreadData=din.readUTF();
				if(ThreadData.toLowerCase().contains("quit")) {
					System.out.println(ThreadData);
					break;
				}
		
				else if(!ThreadData.contains("invalid")) {
					if(!ThreadData.contains("winner")) {
						getPlayer(ThreadData);
						getInput(ThreadData);
						drawBoard(a1,a2,a3,b1,b2,b3,c1,c2,c3);
						if(Rev_PORT!=PORT) {
							System.out.println("Your Turn!!");
						}else {
							System.out.println("Opponents Turn!!");
						}
					}else {
						getWinner(ThreadData);
					}
				}else {
					int temp=Integer.parseInt(ThreadData.substring(7, ThreadData.length()));
					if(temp==PORT) {
						System.out.println("Invalid Move. Try Again");
					}
				}
				
			}
			
			s.close();
			din.close();
			dout.close();
			
		} 
		catch (SocketException e) {
			System.out.println("Server is disconnected. Restart the game !");
			System.exit(0);
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
