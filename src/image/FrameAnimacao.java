/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package image;

import java.awt.Image;
public class FrameAnimacao {
	
    /** Duracao em ms
     */
    private int duracao;
    private Image imagem;

    public FrameAnimacao(int duracao, Image imagem) {
		this.duracao = duracao;
		this.imagem = imagem;
	}
    
	public int getDuracao() {
        return duracao;
    }
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
    public Image getImagem() {
        return imagem;
    }
    public void setImagem(Image imagem) {
        this.imagem = imagem;
    }
}