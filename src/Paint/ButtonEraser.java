package Paint;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

//Bot�o que configura a grossura e cor do l�pis criando uma borracha
public class ButtonEraser extends JButton implements ActionListener{
	private DrawPanel drawPanel;
	private final int thickness = 20;
	private final Color cor = Color.WHITE;
	
	public ButtonEraser(DrawPanel d){
		this.setText("Borracha");
		this.addActionListener(this);
		
		this.drawPanel = d;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		drawPanel.setThickness(thickness);
		drawPanel.setColor(cor);		
	}
}