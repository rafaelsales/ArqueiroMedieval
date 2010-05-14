package sprite;

import java.awt.Graphics2D;

public abstract class Sprite {
    
    /**Renderiza todos os sprites.
     * @param g - Referencia de uma instância de Graphics2D
     */
     
    protected double x;
    protected double y;
    protected int height;
    protected int width;
    
    public Sprite(){
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }


    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    /**Move o Sprite para o ponto p(difX, difY) relativo ao ponto p(x,y) atual.
     * @param difX - A quantidade x a ser adicionada à posição x do Sprite
     * @param difY - A quantidade y a ser adicionada à posição y do Sprite
     */
    public void mover(double difX, double difY){
    	this.x += difX;
    	this.y += difY;
    }
    
    public abstract void render (Graphics2D g);
    
}