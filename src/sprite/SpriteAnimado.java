package sprite;

import image.Imagem;

import java.util.HashMap;
import java.util.Map;


/**
 * Representa um Sprite animado.
 */
public abstract class SpriteAnimado extends Sprite {

	/**
	 * Integer - O estado da imagem animada.
	 * ImagemAnimada - A seq��ncia de imagens de um estado.
	 */
	protected Map<Integer, Imagem> estadosImagens;

	public SpriteAnimado() {
		this.estadosImagens = new HashMap<Integer, Imagem>();
		this.carregarImagens(this.estadosImagens);
	}
	
	/**Preenche a Map estadosImagens com os estados da anima��o
	 * e suas respectivas ImagensAnimadas.
	 */
	protected abstract void carregarImagens(Map<Integer, Imagem> estadosImagens);

}