package Paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DrawPanel extends JPanel{

	private static final long serialVersionUID = 3987690509231474850L;

	public DrawPanel(int width, int height){
				
		//Mouse Listener
		MouseTracker mouseT = new MouseTracker(this);
		addMouseListener(mouseT);
		addMouseMotionListener(mouseT);		
		
		
		//Set de atributos da janela
		this.setBounds(0, 0, width, height);
		this.setSize(width, height);
		setBackground(Color.WHITE);
		
	}
	
	private ArrayList<Desenho> desenhos = new ArrayList<>();//ArrayList de todos os desenhos feitos
	private int i = 1, thickness = 4;
	private boolean clear = true;
	private Color cor = Color.BLACK;
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)(g);
				
		//Vai limpar a tela
		if(clear){
			super.paintComponent(g2);
			reset();
			clear = false;
		}	
		
		g2.setStroke(new BasicStroke(thickness));
		g2.setColor(cor);		
		
		//Pega a coordenada atual com a anterior e cria uma linha entre as duas
		if(1<desenhos.size()){				
			g2.drawLine(desenhos.get(i-1).x, desenhos.get(i-1).y,
							desenhos.get(i).x, desenhos.get(i).y);
			i++;
		} 
		else if(1==desenhos.size()){				
			g2.drawLine(desenhos.get(0).x, desenhos.get(0).y,
						desenhos.get(0).x, desenhos.get(0).y);
		}
		
	}
	
	//Limpa arrayList
	public void reset(){
		desenhos.clear();
		i=1;
	}
	
	public void addDesenho(int x, int y){
		desenhos.add(new Desenho(x,y));
	}
	
	public void setThickness(int t){
		this.thickness = t;
	}
	
	public void setColor(Color c){
		this.cor = c;
	}
	
	public void clear(){
		clear = true;
	}

}
