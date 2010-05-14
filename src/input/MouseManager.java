package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */ 
public class MouseManager implements MouseMotionListener, MouseListener {    
    
    private static MouseManager instancia;
    
    public static MouseManager getInstancia(){
        if (instancia == null)
        	instancia = new MouseManager();
        return instancia;
    }
    
    private List<MControlavel> listeners = new ArrayList<MControlavel>();
    private MouseEvent mouseEvent;
    private MouseEvent mouseMotionEvent;
            
    private MouseManager(){
        
    }
    
    public void addListener(MControlavel mControlavel){
    	listeners.add(mControlavel);
    }
    
    public void removeListener(MControlavel mControlavel){
        
    }

    public void loop() {
    	if (mouseEvent == null && mouseMotionEvent == null)
    		return;
    	
        for (int i = 0; i < listeners.size(); i++){
        	if (mouseEvent != null)
        		listeners.get(i).mouseClicado(mouseEvent);
        	if (mouseMotionEvent != null)
        		listeners.get(i).mouseMovido(mouseMotionEvent);
        }
        
        mouseEvent = null;
        mouseMotionEvent = null;
    }

    public void mouseDragged(MouseEvent e) {
        mouseMotionEvent = e;
    }

    public void mouseMoved(MouseEvent e) {
        mouseMotionEvent = e;
    }

    public void mouseClicked(MouseEvent e) {
        //mouseEvent = e;
    }

    public void mousePressed(MouseEvent e) {
        //mouseEvent = e;
    }

    public void mouseReleased(MouseEvent e) {
        mouseEvent = e;
    }

    public void mouseEntered(MouseEvent e) {
        mouseEvent = e;
    }

    public void mouseExited(MouseEvent e) {
        mouseEvent = e;
    }
}