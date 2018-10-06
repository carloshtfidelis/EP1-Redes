package Chat;

public class ClientTest {
	public static void main(String[] args){
		Cliente cliente = new Cliente("127.0.0.1");
		Thread janelaDoChat = new Thread(cliente);
		
		janelaDoChat.start();
	}
}
