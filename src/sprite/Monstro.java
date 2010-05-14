package sprite;

import image.ImageLoader;
import image.Imagem;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.Janela;


public  class Monstro extends SpriteAnimado{

	public enum Estado {
		Andando, Morrendo;
	}
	
	public enum ETipos {
		Terran, Protoss, Zerg;
	}

	private static List<Monstro> sprites = new LinkedList<Monstro>();
	public static List<Monstro> getSprites() {
		return sprites;
	}
	
	public static void renderAll(Graphics2D g) {
		Iterator<Monstro> it = sprites.iterator();
		Monstro monstro;
		while (it.hasNext()){
			monstro = it.next();
			monstro.render(g);

		}
	}
	
	
	private int velocidade = -3;
	private int vidas;
	private ETipos tipo;
	private Estado estado;

	public Monstro() {
		int numMonstro = (int) (Math.random() * 3);
		this.tipo = ETipos.values()[numMonstro];
		this.vidas = numMonstro + 1;
		
		this.estado = Estado.Andando;
		
		Insets janelaInsets = Janela.getInstancia().getInsets();
		
		this.x = Janela.SIZE.getWidth();
		this.y = Math.random() * (
				Janela.SIZE.getHeight() 
				- janelaInsets.top 
				- janelaInsets.bottom
				- ImageLoader.getInstancia().getImagem("Monstro_Andando").getImage().getHeight(null));
		Monstro.sprites.add(this);
	}
	

	public void atingir(Flecha flecha){
		estado = Estado.Morrendo;
		velocidade *= -1;
		flecha.setVelocidade(velocidade);
	}

	@Override
	protected void carregarImagens(Map<Integer, Imagem> estadosImagens) {
		Imagem imagem;
		
		imagem = ImageLoader.getInstancia().getImagem("Monstro_Andando");
		estadosImagens.put(Estado.Andando.ordinal(), imagem);
		
		imagem = ImageLoader.getInstancia().getImagem("Monstro_Morto");
		estadosImagens.put(Estado.Morrendo.ordinal(), imagem);
	}

	public void diminuirVida() {
		this.vidas--;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public ETipos getTipo() {
		return tipo;
	}

	public int getVelocidade() {
		return velocidade;
	}

	public int getVidas() {
		return vidas;
	}

	public void mover(){
		this.x += this.velocidade;
	}

	@Override
	public void render(Graphics2D g) {
		Image img = this.estadosImagens.get(this.estado.ordinal()).getImage();
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		g.drawImage(img, (int)x, (int)y, null);
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
