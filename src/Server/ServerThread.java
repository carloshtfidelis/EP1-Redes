package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{
	private Server server;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private int indice;
	
	ServerThread(Server server, Socket c, int i){
		this.server = server;
		this.connection = c;
		this.indice = i;
	}
	
	public void run(){
		
		try{
			setupStreams();
			chatting();
		}catch(IOException eof){
			showMenssagem("\nServer desconectou!\n");
		}finally{
			close();
		}
		
		// Finaliza a thread
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void updateIndice(int i){
		this.indice = i;
	}
	
	//Configura streams de entrada e saida
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		
		input = new ObjectInputStream(connection.getInputStream());
	}
	
	//Metodo roda enquanto comunicando
	private void chatting() throws IOException{
		String menssagem = "Conetado!\n";
		String[] array;
		enviaMenssagem(menssagem);
		
		do{
			try{
				menssagem = (String) input.readObject();
				showMenssagem(menssagem);
				
				server.sendToAll(menssagem, indice);
				
			}catch(ClassNotFoundException cnf){
				showMenssagem("Não consigo mostrar última menssagem!");
			}
			
		}while(!menssagem.equals("CLIENTE - END\n"));
	}
	
	public void close(){
		showMenssagem("Conexão estabelecida\n");
		
		try{
			output.close();
			input.close();
			connection.close();
			server.subUsers(indice);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void enviaMenssagem(String menssagem){
		try{
			output.writeObject(menssagem);
			output.flush();
			
		}catch(IOException ioe){
			showMenssagem("Não conssigo mandar essa menssagem\n");
		}
	}
	
	public void showMenssagem(final String m){
		server.showMenssagem(m);
	}
}
