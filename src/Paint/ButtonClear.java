package Paint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ButtonClear extends JButton implements ActionListener{
	private DrawPanel drawPanel;	
	
	public ButtonClear(DrawPanel d){
		this.setText("Limpar");
		this.addActionListener(this);
		
		this.drawPanel = d;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		drawPanel.clear();
		drawPanel.repaint();
	}
}
