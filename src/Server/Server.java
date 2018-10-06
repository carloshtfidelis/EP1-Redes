package Server;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{
	
	private JTextArea janelaChat;
	private ServerSocket server;
	private Socket connection;
	private int numOfUsers = 0;
	private ArrayList<ServerThread> listaDeThreads;
	
	public Server(){
		
		super("Log do Servidor");
		listaDeThreads = new ArrayList<>();
		
		//Executa ação quando fecha a janela
		this.addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	sendToAll("END",-1);
		    }
		});
		
		janelaChat = new JTextArea();
		janelaChat.setLineWrap(true);
		janelaChat.setEditable(false);
		add(new JScrollPane(janelaChat));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350,300);
		setVisible(true);
	}
	
	public void start(){
		try{
			server = new ServerSocket(8765, 100);
			while(true){
				try{
					waitForConnection();
					ServerThread serverThread = new ServerThread(this, connection, numOfUsers-1);
					listaDeThreads.add(serverThread);
					serverThread.start();
				}catch(EOFException eof){
					showMenssagem("Server desconectou!\n");
				}			
			}
		}catch(IOException ioe){
			ioe.printStackTrace();;
		}
	}
		
	public void addUsers(){
		numOfUsers++;
	}
	
	public void subUsers(int i){
		if(numOfUsers>0){
			--numOfUsers;
			listaDeThreads.remove(i);
			
			for(int j = i; j<listaDeThreads.size(); j++)
				listaDeThreads.get(j).updateIndice(j);
		}
	}
		
	 private void waitForConnection() throws IOException{
		showMenssagem("Esperando conexão...\n");
		connection = server.accept();
		
		addUsers();
		showMenssagem("Conectado com "+connection.getInetAddress().getHostName()+"\n");
	}
	
	public void showMenssagem(final String m){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				janelaChat.append(m);
			}
		});
	}	
	
	public void sendToAll(String m, int j){		
		for(int i = 0; i<listaDeThreads.size(); i++)
			if(i!=j)
				listaDeThreads.get(i).enviaMenssagem(m);
	}
}
