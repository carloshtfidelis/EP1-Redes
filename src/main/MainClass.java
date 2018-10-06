package main;

import Chat.Cliente;
import Paint.JanelaDePintura;

public class MainClass {
	
	//Abre a janela de Chat e de desenho
	public static void main(String[] args){
		JanelaDePintura jdp = new JanelaDePintura();
		Cliente cliente = new Cliente("127.0.0.1");
		
		//Dessa meneira é possivel abrir as duas janelas
		Thread janelaDePintura = new Thread(jdp);
		Thread janelaDoChat = new Thread(cliente);
		
		janelaDePintura.start();
		janelaDoChat.start();
	}
}
