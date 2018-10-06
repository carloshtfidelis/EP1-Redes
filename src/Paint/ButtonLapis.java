package Paint;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

//Botão que configura a grossura e cor do lápis
public class ButtonLapis extends JButton implements ActionListener{
	private DrawPanel drawPanel;
	private int thickness = 4;
	private Color cor = Color.BLACK;
		
	public ButtonLapis(DrawPanel d){
		this.setText("Lápis");
		this.addActionListener(this);
		
		this.drawPanel = d;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		drawPanel.setThickness(thickness);
		drawPanel.setColor(cor);
	}
}
