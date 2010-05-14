package state;

import image.ImageLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import sprite.Monstro;

import core.CicloJogo;
import core.Janela;


public class GameOver extends Estado {
	Monstro gOMonstro = new Monstro();

	private long scores;
	private String nome;
	private long tempo = System.currentTimeMillis();
	
	
	public void setScores(long moedas) {
		this.scores = moedas;
	}

	public GameOver(CicloJogo cicloJogo) {
		super(cicloJogo);
		gOMonstro.setY(Janela.SIZE.height/2-40);
	}
	
	@Override
	public void finalizar() {

	}

	@Override
	public void loop(){
		gOMonstro.mover(-8, 0);
		
		if(System.currentTimeMillis() - tempo > 4000 && nome == null) {
			
			boolean nomeOk;
			do {
				nomeOk = true;
				try {
					nome = receberNome();
				} catch (NomeDoidoException e) {
					JOptionPane.showMessageDialog(null, e.getMessage() + ": " + e.getNome(), Janela.TITULO, JOptionPane.ERROR_MESSAGE);
					nomeOk = false;
				}
			} while (!nomeOk);
			
			try {
				salvar(nome, scores);
			} catch (IOException e) { 
				JOptionPane.showMessageDialog(null,	"Nao foi possivel salvar o score!", "Janela.TITULO", JOptionPane.ERROR_MESSAGE);	
			}
			
			JOptionPane.showMessageDialog(null, "Scores: " + scores, Janela.TITULO, JOptionPane.PLAIN_MESSAGE);
			cicloJogo.setProxEstado(new Menu(cicloJogo));
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(120, 137, 67));
		g.fillRect(0, 0, Janela.SIZE.width, Janela.SIZE.height);
		
		Image gOImage = ImageLoader.getInstancia().getImagem("Game_Over").getImage();
		g.drawImage(gOImage, Janela.SIZE.width/2-gOImage.getWidth(null)/2, Janela.SIZE.height/2-gOImage.getHeight(null)/2, null);
				
		Monstro.renderAll(g);
	}
	
	public String receberNome() throws NomeDoidoException {
		String nome = JOptionPane.showInputDialog(null, "Digite seu nome:\nPara deixar em branco clique em cancelar.", Janela.TITULO, JOptionPane.PLAIN_MESSAGE);
		
		if (nome == null)
			return Janela.TITULO;
		
		if (nome.indexOf("|") != -1 || nome.length() == 0)
			throw new NomeDoidoException(nome);
		
		return nome;
	}
	
	private void salvar(String nome, long scores) throws IOException{
		FileWriter fileWriter = new FileWriter(Janela.ARQUIVO_SCORES, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);	
		
		printWriter.println(nome + "|" + scores);
		printWriter.close();
		fileWriter.close();
	}
}