package Paint;

import javax.swing.JFrame;

//Defini janela da pintura com barra de ferramentas
public class JanelaDePintura extends JFrame implements Runnable{
	
	private static final long serialVersionUID = -7229136225639948575L;
	DrawPanel drawPanel ;
	
	public JanelaDePintura(){
		super("Paint");
		setSize(800, 700);
		this.setLayout(null);
		
		drawPanel = new DrawPanel(800, 550);
		BarraDeFerramentas bdf = new BarraDeFerramentas(drawPanel);
		this.add(drawPanel);
		this.add(bdf);
		
	}
	
	@Override
	public void run() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}
