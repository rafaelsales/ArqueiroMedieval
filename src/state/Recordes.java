package state;

import image.ImageLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.CicloJogo;
import core.Janela;


public class Recordes extends Estado implements KeyListener {
	
	private List<Score> scores;
	
	public Recordes(CicloJogo cicloJogo) {
		super(cicloJogo);
		
		try {
			scores = ler(Janela.ARQUIVO_SCORES);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Janela.getInstancia().addKeyListener(this);
	}

	public void finalizar() {
		cicloJogo.setProxEstado(new Menu(cicloJogo));
		Janela.getInstancia().removeKeyListener(this);
	}


	public void loop() {
	}

	public void render(Graphics2D g) {
		g.setColor(new Color(120, 137, 67));
		g.fillRect(0, 0, Janela.SIZE.width, Janela.SIZE.height);

		Image CreditosImage = ImageLoader.getInstancia().getImagem("Recordes").getImage();
		g.drawImage(CreditosImage, 0, 0, null);

		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
		g.setColor(new Color(82, 78, 4));

		int i = 0;
		for (Score score : scores) {
			int espaco = 220 + 40 * i;
			g.drawString(score.getNome(), 175, espaco);
			g.drawString(score.getScore() + "", 510, espaco);
			i++;
			if( i==9 ) 
				break;
		}
	}
	

	private List<Score> ler(String arquivo) throws FileNotFoundException, IOException {
		List<Score> scores = new ArrayList<Score>();
		
		FileReader fileReader = new FileReader(Janela.ARQUIVO_SCORES);
		BufferedReader buffReader = new BufferedReader(fileReader);
		String linha;
		String nome;
		String score;
		
		while ((linha = buffReader.readLine()) != null){
			nome = linha.substring(0, linha.indexOf("|"));
			score = linha.substring(linha.indexOf("|") + 1);
			
			scores.add(new Score(nome, Integer.parseInt(score)));
		}
		Collections.sort(scores);
		
		return scores;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER && !e.isAltDown()) {
			finalizar();
		}
	}
	
	public void keyReleased(KeyEvent e) {	
	}
	public void keyTyped(KeyEvent e) {	
	}
}

class Score implements Comparable<Score> {
	private String nome;
	private int score;
	
	public Score(String nome, int score) {
		this.nome = nome;
		this.score = score;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getScore() {
		return score;
	}

	public int compareTo(Score s2) {
		return s2.getScore() - this.getScore();
	}
}