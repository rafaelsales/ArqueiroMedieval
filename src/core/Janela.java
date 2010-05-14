package core;

import input.MouseManager;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class Janela extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	public static final String TITULO = "Arqueiro Medieval";
	public static final Dimension SIZE = new Dimension(800, 600);
	public static final String ARQUIVO_SCORES = "recordes.txt";
	private static Janela instancia;

	public static Janela getInstancia() {
		if (instancia == null) {
			instancia = new Janela();
		}
		return instancia;
	}

	public static void main(String args[]) {
		Janela.getInstancia().abrirJanela();
	}

	private boolean fullscreen = false;
	private CicloJogo cicloJogo;

	public Janela() {
		cicloJogo = new CicloJogo();
		this.getContentPane().add(cicloJogo);
		this.addKeyListener(this);
		this.addMouseListener(MouseManager.getInstancia());
		this.addMouseMotionListener(MouseManager.getInstancia());
	}

	private void abrirJanela() {
		GraphicsEnvironment g = GraphicsEnvironment
				.getLocalGraphicsEnvironment();

		setTitle(TITULO);
		setResizable(false);
		setPreferredSize(SIZE);
		setMinimumSize(SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Point p = g.getCenterPoint();
		p.translate(-SIZE.width / 2, -SIZE.height / 2);
		setLocation(p);

		setFullScreen(false);
		
		setVisible(true);
	}

	public void fechar(){
		processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public void keyPressed(KeyEvent kEvent) {
	}

	public void keyReleased(KeyEvent kEvent) {
		if (kEvent.isAltDown() && kEvent.getKeyCode() == KeyEvent.VK_ENTER)
			setFullScreen(fullscreen ? false : true);
	}

	public void keyTyped(KeyEvent kEvent) {
	}

	public void setFullScreen(boolean fullscreen) {
		if (this.fullscreen == fullscreen)
			return;
		this.fullscreen = fullscreen;
		
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = g.getDefaultScreenDevice();
		
		cicloJogo.parar();
		if (!fullscreen) {
			if (isVisible())
				dispose();
			gd.setFullScreenWindow(null);
			this.setUndecorated(false);
			
			setVisible(true);
			pack();
		} else {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (isVisible())
				dispose();

			while (isDisplayable()) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			this.setUndecorated(true);
			setVisible(true);
			pack();
			gd.setFullScreenWindow(this);
			gd.setDisplayMode(new DisplayMode(SIZE.width, SIZE.height, 16, 60));
		}
		cicloJogo.iniciar();
	}
}
