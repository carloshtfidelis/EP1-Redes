package Chat;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Cliente extends JFrame implements Runnable{
	
	private JTextField texto;
	private JTextArea janelaChat;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String menssagem = "", serverIP, nome;
	private Socket connection;
	private boolean conectado = false;
	
	public Cliente(String host){
		super("Bate Papo UOL");
		serverIP = host;
		nome = "Cliente";
		
		//Configurando textField
		texto = new JTextField();
		texto.setEditable(false);
		texto.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				enviaMenssagem(e.getActionCommand());
				texto.setText("");
			}			
				
		});
		
		//Executa ação quando fecha a janela
		this.addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	if(isConnected())
		    		enviaMenssagem("END");
		    }
		});
		
		//Adcionando textField e TextArea
		add(texto,BorderLayout.SOUTH);
		janelaChat = new JTextArea();
		janelaChat.setLineWrap(true);
		janelaChat.setEditable(false);
		add(new JScrollPane(janelaChat));
		
		//Configurando janela
	}
		
	public boolean isConnected(){
		return conectado;
	}
	
	private void connect() throws IOException{
		showMenssagem("Tentando conexão...\n");
		int tries = 1;
		showMenssagem("Tentando conectar, tentativa "+tries+" de 10\n");
		
		while(true){
			try{
				if(tries == 10)
					break;				
				tries++;
				
				connection = new Socket(InetAddress.getByName(serverIP), 8765);
				conectado = true;
				break;
				
			}catch (UnknownHostException e){				
				try
		        {
		            Thread.sleep(2000);
		            showMenssagem("Servidor não responde...\n"
							+ "Tentando novamente, tentativa "+tries+" de 10\n");
		        }
		        catch(InterruptedException ie){
		            ie.printStackTrace();
		        }
				
			}catch (IOException e) {				
				try
		        {
		            Thread.sleep(2000);
		            showMenssagem("Servidor não responde...\n"
							+ "Tentando novamente, tentativa "+tries+" de 10\n");
		        }
		        catch(InterruptedException ie){
		            ie.printStackTrace();
		        }
		     }
		}
		
		if(isConnected())
			showMenssagem("Conexão estabelecida: "+connection.getInetAddress().getHostName()+"\n");
		else
			showMenssagem("Incapaz de conectar com servidor =(\n");
	}
	
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		
		input = new ObjectInputStream(connection.getInputStream());
	}
	
	private void chatting() throws IOException{
		podeDigitar(true);
		
		do{
			try{
				menssagem = (String) input.readObject();
				showMenssagem(menssagem);
				
			}catch(ClassNotFoundException cnf){
				showMenssagem("Não reconheço este objeto\n");
			}catch(SocketException se){
				showMenssagem("\nPerdeu conexão com o servidor\n");
				conectado = false;
				texto.setEditable(false);
				break;
			}			
		}while(!menssagem.equals("SERVER - END"));
	}
	
	private void enviaMenssagem(String menssagem){
		try{
			output.writeObject(nome+" :"+menssagem+"\n");
			output.flush();
			showMenssagem(nome+" :"+menssagem+"\n");
			
		}catch(IOException ioe){
			janelaChat.append("Não conssigo mandar essa menssagem");
		}
	}
	
	private void podeDigitar(final boolean b){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				texto.setEditable(b);
			}
		});
	}
	
	private void showMenssagem(final String m){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				janelaChat.append(m);
			}
		});
	}
	
	private void close(){
		showMenssagem("Conexão fechada...\nBem Vindo "+nome);
		podeDigitar(false);
		
		try{
			output.close();
			input.close();
			connection.close();
			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	@Override
	public void run() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350,300);
		setVisible(true);		
		
		nome = JOptionPane.showInputDialog("Qual é o seu nome?");
		
		try{
			connect();
			if(isConnected()){
				setupStreams();
				chatting();
			}
		}catch(EOFException eof){
			showMenssagem("Você foi desconectado\n");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}finally{
			if(isConnected())
				close();
		}
	}
}
