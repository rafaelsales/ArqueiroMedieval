package state;

import java.awt.Graphics2D;

import core.CicloJogo;


public abstract class Estado {
	protected CicloJogo cicloJogo;
	
	public Estado(CicloJogo cicloJogo){
		this.cicloJogo = cicloJogo;
	}
	public abstract void finalizar();
	public abstract void loop();
	
	public abstract void render(Graphics2D g);
}
