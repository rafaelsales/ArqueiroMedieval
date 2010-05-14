package sprite;

import image.ImageLoader;
import image.Imagem;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.Janela;


public class Flecha extends SpriteAnimado {

	public enum Estado {
		NaoAtirada, Movendo, NoDestino;
	}

	private static List<Flecha> sprites = new LinkedList<Flecha>();
	
	public static List<Flecha> getSprites() {
		return sprites;
	}

	public static void renderAll(Graphics2D g) {
		Iterator<Flecha> it = sprites.iterator();
		while (it.hasNext()) {
			Flecha flecha = it.next();
			if (flecha.x > Janela.SIZE.width) {
				it.remove();
				continue;
			}
			flecha.render(g);
		}
	}

	private Arqueiro arqueiro;
	private int velocidade = 12;
	private Estado estado;

	public Flecha(Arqueiro arqueiro) {
		this.arqueiro = arqueiro;
		this.estado = Estado.NaoAtirada;
		Flecha.sprites.add(this);
	}

	@Override
	protected void carregarImagens(Map<Integer, Imagem> estadosImagens) {
		Imagem imagem;
		
		imagem = ImageLoader.getInstancia().getImagem("Flecha_NaoAtirada");
		estadosImagens.put(Estado.NaoAtirada.ordinal(), imagem);

		imagem = ImageLoader.getInstancia().getImagem("Flecha_Movendo");
		estadosImagens.put(Estado.Movendo.ordinal(), imagem);
		
		imagem = ImageLoader.getInstancia().getImagem("Flecha_Movendo");
		estadosImagens.put(Estado.NoDestino.ordinal(), imagem);
	}	

	public Estado getEstado() {
		return estado;
	}

	public void mover() {
		this.x += this.velocidade;
	}

	@Override
	public void render(Graphics2D g) {
		if (estado == Estado.NaoAtirada
				&& arqueiro.getEstado() == Arqueiro.Estado.Recarregando)
			return;
		Image img = this.estadosImagens.get(this.estado.ordinal()).getImage();
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		g.drawImage(img, (int)x, (int)y, null);
	}

	public void setEstado(Estado estado) {
		this.estado = estado;			
	}
	
	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	public double getVelocidade() {
		return velocidade;
	}

}