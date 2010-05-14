package image;

import java.awt.Image;

public class Imagem {
	private Image imagem;

	protected Imagem(){
	}
	
	public Imagem(Image imagem){
		this.imagem = imagem;
	}
	
	public Image getImage(){
		return imagem;
	}
}
