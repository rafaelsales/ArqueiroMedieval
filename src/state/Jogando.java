package state;

import image.ImageLoader;
import input.MouseManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import sprite.Arqueiro;
import sprite.CollisionDetector;
import sprite.Flecha;
import sprite.Monstro;

import core.CicloJogo;
import core.Janela;


public class Jogando extends Estado implements KeyListener {

	private long ultimoTempo;
	private Arqueiro arqueiro = new Arqueiro();
	private boolean gameOver = false;
	private boolean pausado = false;
	
	public Jogando(CicloJogo cicloJogo) {
		super(cicloJogo);
		ImageLoader.getInstancia();
		Janela.getInstancia().addKeyListener(this);
		MouseManager.getInstancia().addListener(this.arqueiro);
	}

	public void finalizar() {
		MouseManager.getInstancia().removeListener(this.arqueiro);
		Janela.getInstancia().removeKeyListener(this);
		
		Monstro.getSprites().clear();
		Flecha.getSprites().clear();
		
		if (gameOver) {
			GameOver placar = new GameOver(cicloJogo);
			placar.setScores(arqueiro.getMoedas());
			cicloJogo.setProxEstado(placar);
		}
		else
			cicloJogo.setProxEstado(new Menu(cicloJogo));
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			finalizar();
		}
		if (e.getKeyCode() == KeyEvent.VK_P) {
			if (pausado)
				pausado = false;
			else
				pausado = true;
		}
		
	}
	
	

	public void keyReleased(KeyEvent e) {
	}
	
	public void keyTyped(KeyEvent e) {
	}

	public synchronized void loop() {
		
		if (pausado)
			return;

		MouseManager.getInstancia().loop();

		if (System.currentTimeMillis() - this.ultimoTempo > this.arqueiro
				.getFreqNasc()) {
			this.ultimoTempo = System.currentTimeMillis();
			new Monstro();
		}

		for (Flecha flecha : Flecha.getSprites()) {
			if (flecha.getEstado() != Flecha.Estado.NaoAtirada) {
				flecha.mover();
			}
		}

		this.testarColisoes();
		
		if(arqueiro.getVidas() == 0){
			gameOver = true;
			finalizar();
		}
	}

	public synchronized void render(Graphics2D g) {
		g.setColor(new Color(120, 137, 67));
		g.fillRect(0, 0, Janela.SIZE.width, Janela.SIZE.height);

		Monstro.renderAll(g);
		
		this.arqueiro.render(g);
		Flecha.renderAll(g);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, 14));
		g.setColor(new Color(0, 0, 0));
		// ---Flechas atiradas:
		g.drawString("" + arqueiro.getNumFlechas(), 605, 25);
		g.drawImage(ImageLoader.getInstancia().getImagem("Flecha_Menu")
				.getImage(), 580, 10, null);
		
		// ---Monstros mortos:
		g.drawString("" + arqueiro.getFrags(), 675, 25);
		g.drawImage(ImageLoader.getInstancia().getImagem("Monstro_Pequeno")
				.getImage(), 650, 3, null);
		
		// ---Score:
		g.drawString("" + arqueiro.getMoedas(), 525, 25);
		g.drawImage(ImageLoader.getInstancia().getImagem("Moedas")
				.getImage(), 505, 10, null);
		
		for (int i = 0; i < arqueiro.getVidas(); i++) {
			g.drawImage(ImageLoader.getInstancia().getImagem("Arqueiro_Coracao").getImage(), 40 + i*25, 10, null);
		}
	}

	/**
	 * Usado para testar colisoes entre Monstros-Flechas e Monstros-Arqueiro
	 */
	public void testarColisoes() {
		Iterator<Monstro> itM = Monstro.getSprites().iterator();

		while (itM.hasNext()) {
			Monstro monstro = itM.next();
			
			monstro.mover();

			if (monstro.getEstado() == Monstro.Estado.Morrendo)
				continue;

			
			Iterator<Flecha> itF = Flecha.getSprites().iterator();

			while (itF.hasNext()) {
				Flecha flecha = itF.next();
				if (flecha.getEstado() != Flecha.Estado.Movendo)
					continue;
				
				if (CollisionDetector.colisaoEntre(monstro, flecha)) {
					flecha.setEstado(Flecha.Estado.NoDestino);
					monstro.atingir(flecha);
					this.arqueiro.incrementarFrags();
					
					arqueiro.incrementarMoedas(5);
				}
			}

			// Colisao com a parede:
			// Deleta os monstros "pairantes"
			if (monstro.getX() < - monstro.getWidth()){
				itM.remove();
			}
			
			if (CollisionDetector.colisaoEntre(monstro, arqueiro))
				this.arqueiro.tirarVida();
		}
	}

}
