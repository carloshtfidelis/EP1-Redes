package Paint;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class BarraDeFerramentas extends JPanel{
	
	private static final long serialVersionUID = -2146477473731988703L;

	public BarraDeFerramentas(DrawPanel d){
		//d=null;
		setBounds(0, 550, 100, 200);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(new ButtonLapis(d));
		add(new ButtonEraser(d));
		add(new ButtonClear(d));
	}
}
