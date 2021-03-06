package Paint;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

//Ir� seguir e pegar as coordenadas do mouse
public class MouseTracker implements MouseListener,MouseMotionListener{

	private DrawPanel drawPanel;
	
	MouseTracker(DrawPanel d){
		this.drawPanel = d;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		drawPanel.addDesenho(e.getX(),e.getY());//Pega as coordenadas
		drawPanel.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//Do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Do nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		drawPanel.reset();//Quando o mouse � solto limpa a arrayList
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Do nothing
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		drawPanel.addDesenho(e.getX(), e.getY());//Pega coordenadas
		drawPanel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//Do nothing
	}
}
