package sprite;

import image.ImageLoader;
import image.Imagem;
import image.ImagemAnimada;
import input.MControlavel;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.Map;

import core.Janela;


public class Arqueiro extends SpriteAnimado implements MControlavel {

	public enum Estado {
		Parado, Recarregando, EmPosicao, Atirando;
	}

	private int numFlechas = 0;

	private int vidas = 3;

	private int nivel = 1;

	private double freqNasc;

	private int frags = 0;

	private Estado estado;

	private Flecha flecha;
	
	private long moedas = 50;
	

	public Arqueiro() {
		this.estado = Estado.Parado;
		this.x = 10;
		// this.y = Janela.SIZE.getHeight() / 2 - imagem.getHeight(null) / 2;
	}


	public void alterarVida(int i) {
		vidas = vidas + i;
	}

	public void carregarImagens(Map<Integer, Imagem> estadosImagens) {
		Imagem imagem;
		ImagemAnimada iAnimada;

		imagem = ImageLoader.getInstancia().getImagem("Arqueiro_Parado");
		estadosImagens.put(Estado.Parado.ordinal(), imagem);

		iAnimada = ImageLoader.getInstancia().getImagemAnimada(
				"Arqueiro_Recarregando");
		estadosImagens.put(Estado.Recarregando.ordinal(), iAnimada);

		imagem = ImageLoader.getInstancia().getImagem("Arqueiro_EmPosicao");
		estadosImagens.put(Estado.EmPosicao.ordinal(), imagem);

		iAnimada = ImageLoader.getInstancia().getImagemAnimada(
				"Arqueiro_Atirando");
		estadosImagens.put(Estado.Atirando.ordinal(), iAnimada);
	}

	public Estado getEstado() {
		return estado;
	}

	public Flecha getFlecha() {
		return flecha;
	}

	public int getFrags() {
		return frags;
	}

	public double getFreqNasc() {
		this.freqNasc = 1 / nivel * 1000;
		return freqNasc;
	}

	public long getMoedas() {
		return moedas;
	}

	public int getNumFlechas() {
		return numFlechas;
	}

	public int getVidas() {
		return vidas;
	}

	public void incrementarFrags(){
		frags++;
	}

	public void incrementarMoedas(int i) {
		this.moedas += i;
	}

	public void mouseClicado(MouseEvent mEvent) {
		if (mEvent.getButton() == MouseEvent.BUTTON3) {
			if (flecha == null) {
				this.setEstado(Estado.Recarregando);
				flecha = new Flecha(this);
				moverFlecha();
			}
		}

		if (mEvent.getButton() == MouseEvent.BUTTON1) {
			if (this.flecha != null && estado == Estado.EmPosicao){
				estado = Estado.Atirando;
				flecha.setEstado(Flecha.Estado.Movendo);
				flecha = null;
				numFlechas++;
				moedas -=3;
			}
		}
	}

	public void mouseMovido(MouseEvent mMotionEvent) {
		this.y = mMotionEvent.getY()
				- Janela.getInstancia().getInsets().top
				- 85
				- ImageLoader.getInstancia().getImagem("Flecha_NaoAtirada")
						.getImage().getHeight(null) / 2d;

		if (this.flecha != null) {
			this.moverFlecha();
		}
	}

	private void moverFlecha() {
		this.flecha.setX(this.x + 29);
		this.flecha.setY(this.y + 85);
	}

	public void render(Graphics2D g) {
		Imagem imagem = this.estadosImagens.get(this.estado.ordinal());
		// Verifica se a animacao de recarregando chegou ao fim:
		if (imagem instanceof ImagemAnimada) {
			ImagemAnimada iAnimada = (ImagemAnimada) imagem;
			if (iAnimada.isFim()) {
				if (estado == Estado.Recarregando)
					estado = Estado.EmPosicao;
				if (estado == Estado.Atirando)
					estado = Estado.Parado;
				// Reseta a animacao para outro uso:
				iAnimada.reset();
				// Se tiver alterado o estado, pega a imagem do novo estado:
				imagem = this.estadosImagens.get(this.estado.ordinal());
			}
		}

		Image img = imagem.getImage();
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		g.drawImage(img, (int) x, (int) y, null);
	}
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void setFlecha(Flecha val) {
		this.flecha = val;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public void tirarVida() {
		if (this.vidas > 0) this.vidas--;
	}
}
