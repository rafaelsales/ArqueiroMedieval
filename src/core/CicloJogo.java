package core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import state.Estado;
import state.Menu;



/**
 * Representa o ciclo do jogo contendo os estados e procedimentos comuns �
 * eles.
 */
public class CicloJogo extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private final int FPS_JOGANDO = 70;
	//private final int FPS_ESTATICO = 20;
	//private final int MAX_NO_DELAYS_PER_YELD = 16;

	private int periodoPorCiclo;

	private Thread tGame;
	private boolean looping;

	private Graphics doubleGc;
	private Image doubleImage = null;

	private Estado estadoAtual;
	private Estado proxEstado;

	public CicloJogo() {
		iniciar();
	}

	public int calculaPeriodo(int FPS) {
		return (int) (1000d / FPS);
	}

	/**
	 * Calcula o tempo de pausa da Thread de acordo com o FPS.
	 * 
	 * @param beforeTime -
	 *            nanoTime() obtido anteriormente ao evento.
	 * @return intervalo de tempo (em ms) entre <b>beforeTime</b> e o instante
	 *         da chamada do m�todo.
	 */
	public long calcularSleepTime(long beforeTime) {
		long time;
		time = System.nanoTime() - beforeTime;		
		time = time / 1000000L; // Transforma de nanosegundos para milisegundos
		time = this.periodoPorCiclo - time;
	
		return time;
	}

	private synchronized void gameRender() {
		doubleImage = this.createImage(this.getWidth(), this.getHeight());
		if (doubleImage == null)
			return;

		doubleGc = doubleImage.getGraphics();
		if (doubleGc == null)
			return;

		
		estadoAtual.render((Graphics2D) doubleGc);
		//---A SALVACAO
		super.paintComponents(doubleGc);
		//---A SALVACAO
	}

	public void iniciar() {
		if (tGame != null)
			return;
		this.looping = true;
		tGame = new Thread(this);
		tGame.start();
	}

	private void paintScreen() {
		if (doubleGc != null) {
			this.getGraphics().drawImage(doubleImage, 0, 0, null);
		}
	}

	public void parar() {
		looping = false;
	}

	public void run() {
		this.estadoAtual = new Menu(this);
		this.periodoPorCiclo = this.calculaPeriodo(FPS_JOGANDO);
		
		while (!this.isValid()) {
			try {
				Thread.yield();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//int frameSkips = 0;
		long beforeTime;
		long sleepTime;

		while (looping) {
			if (proxEstado != null) {
				estadoAtual = proxEstado;
				proxEstado = null;
			}
			beforeTime = System.nanoTime();

			this.estadoAtual.loop();
			this.gameRender();
			this.paintScreen();

			sleepTime = this.calcularSleepTime(beforeTime);
			try {
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				} else {
					//frameSkips++;
					//if (frameSkips >= MAX_NO_DELAYS_PER_YELD) {
						//Thread.yield();
						Thread.sleep(5);
						//frameSkips = 0;
					//}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		tGame = null;
	}
	
	public void sair() {
		Janela.getInstancia().fechar();
	}

	public void setProxEstado(Estado proxEstado) {
		this.proxEstado = proxEstado;
	}
}
