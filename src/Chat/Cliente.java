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
		texto.setEditable(false); // Impede que o cliente digite no chat enquanto offline
		texto.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//Todas as mensagens escritas ser�o transmitidas j� formatadas com o protocolo
				enviaMenssagem("200;"+nome+";: "+e.getActionCommand()+"\n");
				texto.setText("");
			}			
				
		});
		
		//Executa a��o quando fecha a janela
		this.addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	if(isConnected())
		    		enviaMenssagem("100;END;");
		    }
		});
		
		//Adcionando textField e TextArea
		add(texto,BorderLayout.SOUTH);
		janelaChat = new JTextArea();
		janelaChat.setLineWrap(true);
		janelaChat.setEditable(false);
		add(new JScrollPane(janelaChat));
	}
		
	//Retorna se o cliente est� conectado
	public boolean isConnected(){
		return conectado;
	}
	
	//Metodo tentar� conex�o
	private void connect() throws IOException{
		showMenssagem("Tentando conex�o...\n");
		int tries = 1;
		showMenssagem("Tentando conectar, tentativa "+tries+" de 10\n");
		
		while(true){
			try{
				//10 tentativas de conex�o at� desistir
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
		            showMenssagem("Servidor n�o responde...\n"
							+ "Tentando novamente, tentativa "+tries+" de 10\n");
		        }
		        catch(InterruptedException ie){
		            ie.printStackTrace();
		        }
				
			}catch (IOException e) {				
				try
		        {
		            Thread.sleep(2000);
		            showMenssagem("Servidor n�o responde...\n"
							+ "Tentando novamente, tentativa "+tries+" de 10\n");
		        }
		        catch(InterruptedException ie){
		            ie.printStackTrace();
		        }
		     }
		}
		
		if(isConnected())
			showMenssagem("Conex�o estabelecida: "+connection.getInetAddress().getHostName()+"\n");
		else
			showMenssagem("Incapaz de conectar com servidor =(\n");
	}
	
	//Configura streams de entrada e saida
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		
		input = new ObjectInputStream(connection.getInputStream());
	}
	
	//Metodo roda enquanto comunicando
	private void chatting() throws IOException{
		podeDigitar(true); // permiti que o cliente escreva
		
		do{
			try{
				menssagem = (String) input.readObject(); //Recebe mensagens
				showMenssagem(menssagem);
				
			}catch(ClassNotFoundException cnf){
				showMenssagem("N�o reconhe�o este objeto\n");
			}catch(SocketException se){
				showMenssagem("\nPerdeu conex�o com o servidor\n");
				conectado = false;
				texto.setEditable(false);
				break;
			}			
		}while(!menssagem.equals("SERVER - END"));
	}
	
	//Metodo que vai enviar as mensagens
	private void enviaMenssagem(String menssagem){
		try{
			output.writeObject(menssagem);//Escreve mensagem
			output.flush();
			showMenssagem(menssagem);
			
		}catch(IOException ioe){
			janelaChat.append("N�o conssigo mandar essa menssagem");
		}
	}
	
	// Metodo defini permiss�o se o cliente pode escrever ou n�o
	private void podeDigitar(final boolean b){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				texto.setEditable(b);
			}
		});
	}
	
	//Anexa mensagens no chat do cliente
	private void showMenssagem(final String m){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				janelaChat.append(m);
			}
		});
	}
	
	//Fecha conex�o
	private void close(){
		showMenssagem("Conex�o fechada...\nBem Vindo "+nome);
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
		
		nome = JOptionPane.showInputDialog("Qual � o seu nome?");//Pega nome do usuario
		
		try{
			connect();
			if(isConnected()){
				setupStreams();
				chatting();
			}
		}catch(EOFException eof){
			showMenssagem("Voc� foi desconectado\n");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}finally{
			if(isConnected())
				close();
		}
	}
}
