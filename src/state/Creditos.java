package state;

import image.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.CicloJogo;
import core.Janela;


public class Creditos extends Estado implements KeyListener{

	private int VARy = -600;

	public Creditos(CicloJogo cicloJogo) {
		super(cicloJogo);
		Janela.getInstancia().addKeyListener(this);
	}


	public void finalizar() {
		Janela.getInstancia().removeKeyListener(this);
		cicloJogo.setProxEstado(new Menu(cicloJogo));
	}


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER && !e.isAltDown()) {
			finalizar();
		}
	}


	public void keyReleased(KeyEvent e) {
	
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	
	public void loop() {

	}


	public void render(Graphics2D g) {
		g.setColor(new Color(120, 137, 67));
		g.fillRect(0, 0, Janela.SIZE.width, Janela.SIZE.height);
		VARy = VARy + 4;
		if (VARy < 0) {
			Image CreditosImage = ImageLoader.getInstancia().getImagem("Creditos").getImage();
			g.drawImage(CreditosImage, 0, VARy, null);
		} else {
			Image CreditosImage = ImageLoader.getInstancia().getImagem("Creditos").getImage();
			g.drawImage(CreditosImage, 0, 0, null);
		}
	}

}
