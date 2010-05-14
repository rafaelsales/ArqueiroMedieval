package image;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um conjunto de frames de uma animacao
 */
public class ImagemAnimada extends Imagem {

	private List<FrameAnimacao> listaFrames = new ArrayList<FrameAnimacao>();
    private long ultimaMudanca;
    private int frameAtual = 0;
    private boolean looping;

    /**
    * @param looping - Se a animacao deve ou nao se repetir infinitamente.
    */
    public ImagemAnimada(boolean looping) {
    	this.looping = looping;
    }
    
    public void add(FrameAnimacao frame){
    	listaFrames.add(frame);
    }

    /**Conforme o tempo passado desde a ultima chamada desse
	 * metodo, altera o frameAtual.
	 */
    private void alterarFrameAtual() {  
    	//Caso seja a primeira visualizacao nao altera o frame:
    	if (ultimaMudanca == 0){
    		ultimaMudanca = System.currentTimeMillis();
    		return;
    	}
    	
    	ultimaMudanca = System.currentTimeMillis();
        if (frameAtual < listaFrames.size() - 1){       	
        	frameAtual++;
        } else if (looping){
           	frameAtual = 0;
        }
        // Nao altera o frame;      
    }
    
    private long getTempoPassado(){
    	return System.currentTimeMillis() - ultimaMudanca;
    }

    /**
     * @return Referencia da imagem atual da animacao
	 */
    private Image getFrameAtual() {
        if (getTempoPassado() >= listaFrames.get(frameAtual).getDuracao()){
            this.alterarFrameAtual();
        }
        return this.listaFrames.get(frameAtual).getImagem();
    }
    
    @Override
    public Image getImage() {
		return getFrameAtual();
	}
    
    /**Verifica se a animacao chegou ao fim.
     * @return - true se a animacao nao esta em looping e se ja chegou ao ultimo frame
     */
    public boolean isFim(){
    	return (!looping 
    			&& frameAtual == listaFrames.size() - 1
    			&& getTempoPassado() >= listaFrames.get(frameAtual).getDuracao());
    }
    
    /**Posiciona a animacao para o primeiro frame
     */
    public void reset(){
    	frameAtual = 0;
    }
}