import java.util.*;

public class TicTacToe {

	private static String IP_ADDRESS;
	private static int PORT;
	public static int connections;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		System.out.println("Welcome to Tic Tac Toe Game");
		
		while(true){
			
			System.out.println("Select one below : ");
			System.out.println("Host or Join");
			String ans=sc.nextLine();

			if(ans.toLowerCase().equals("host")) {
				System.out.println("Enter the PORT to Host the Server");
				PORT = sc.nextInt();
				new Server(PORT);
				break;

			}else if(ans.toLowerCase().equals("join")) {
				System.out.println("Enter IP ADDRESS of the  SERVER ");
				IP_ADDRESS=sc.nextLine();
				System.out.println("Enter the PORT to connect ");
				PORT=sc.nextInt();
				new Client(IP_ADDRESS, PORT);
				break;

			}else if(ans.toLowerCase().equals("quit")) {
				break;
			}
			
			else {
				System.out.println("Not Valid Inputs. Try Again");
			}
		}
	}

}
