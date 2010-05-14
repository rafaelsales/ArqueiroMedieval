package state;

import image.ImageLoader;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.CicloJogo;
import core.Janela;


public class Menu extends Estado implements KeyListener{
	
	private enum Opcoes{
		Iniciar, Recordes, Creditos, Sair
	}
	
	private Image fundo;
	private Image seletor;
	
	private Opcoes opcao = Opcoes.Iniciar;
	
	public Menu(CicloJogo cicloJogo) {
		super(cicloJogo);
		fundo = ImageLoader.getInstancia().getImagem("Menu").getImage();
		seletor = ImageLoader.getInstancia().getImagem("Arqueiro_Seletor").getImage();
		Janela.getInstancia().addKeyListener(this);
	}


	public void finalizar() {
		Janela.getInstancia().removeKeyListener(this);
	}

	
	public void loop() {
	}
	
	public void render(Graphics2D g) {
		g.drawImage(fundo, 0, 0, null);
		g.drawImage(seletor, 280, 300 + opcao.ordinal() * 66, null);
	}


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			opcao = (opcao.ordinal() + 1 >= Opcoes.values().length) ? Opcoes.values()[0] : Opcoes.values()[opcao.ordinal() + 1];
		if (e.getKeyCode() == KeyEvent.VK_UP)
			opcao = (opcao.ordinal() - 1 < 0) ? Opcoes.values()[Opcoes.values().length - 1] : Opcoes.values()[opcao.ordinal() - 1];
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER && !e.isAltDown()){
			finalizar();
			if (opcao == Opcoes.Iniciar)
				cicloJogo.setProxEstado(new Jogando(cicloJogo));
			else if (opcao == Opcoes.Recordes)
				cicloJogo.setProxEstado(new Recordes(cicloJogo));
			else if (opcao == Opcoes.Creditos)
				cicloJogo.setProxEstado(new Creditos(cicloJogo));
			else if (opcao == Opcoes.Sair)
				cicloJogo.sair();
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
