package main;

import Chat.Cliente;
import Paint.JanelaDePintura;

public class MainClass {
	
	public static void main(String[] args){
		JanelaDePintura jdp = new JanelaDePintura();
		Cliente cliente = new Cliente("127.0.0.1");
		
		jdp.run();
		//Thread janelaDePintura = new Thread(jdp);
		//Thread janelaDoChat = new Thread(cliente);
		
		//janelaDePintura.start();
		//janelaDoChat.start();
	}
}
