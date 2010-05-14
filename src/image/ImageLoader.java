package image;

import java.awt.MediaTracker;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ImageLoader {
	
	private static final String ARQUIVO_DE_IMAGENS = "imagens.xml";
	private static ImageLoader instancia;

	/**
	 * @return a instancia instancia de ImageLoader.
	 */
	public static ImageLoader getInstancia() {
		if (instancia == null) {
			instancia = new ImageLoader();
		}
		return instancia;
	}

	/**String - Nome da Imagem.
	 * ImageIcon - A imagem.
	 */
	private Map<String, Imagem> listaImagem = new HashMap<String, Imagem>();
	private Map<String, ImagemAnimada> listaImagemAnimada = new HashMap<String, ImagemAnimada>();

	private ImageLoader() {
		carregarImagens();
	}

	private void carregarImagens() {
		XMLInput xmlInput = null;
		try {
			xmlInput = new XMLInput(ARQUIVO_DE_IMAGENS);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		if (xmlInput == null){
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel carregar as imagens.",
					this.getClass().getName(),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Document document = xmlInput.getDocument();
		
		// Pega o noh <arqueiro>:
		Element elemRaiz = document.getDocumentElement();
		// Pega o noh <imagens>:
		Element elemImagens = (Element) elemRaiz.getElementsByTagName("imagens").item(0);
		// Pega o noh <animacoes>:
		Element elemAnimacoes = (Element) elemRaiz.getElementsByTagName("animacoes").item(0);

		try {
			listaImagem = carregarImagens(elemImagens);
			listaImagemAnimada = carregarAnimacoes(elemAnimacoes);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					e.getMessage(),
					this.getClass().getName(),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private Map<String, Imagem> carregarImagens(Element elemImagens) throws IOException {
		// Pega todos os elementos de imagens:
		NodeList imagens = elemImagens.getElementsByTagName("imagem");
		Map<String, Imagem> listImagens = new HashMap<String, Imagem>();
		
		// Passa por todos os itens do tipo <imagem>:
		for (int i = 0; i < imagens.getLength(); i++) {
			Element imagem = (Element) imagens.item(i);
			
			ImageIcon imgIcon = new ImageIcon(imagem.getAttribute("arquivo"));
			checkImage(imgIcon);
			
			// Adiciona a imagem na lista:
			listImagens.put(
					imagem.getAttribute("nome"),
					new Imagem(imgIcon.getImage())
					);
			
		}
		return listImagens;
	}

	private Map<String, ImagemAnimada> carregarAnimacoes(Element elemAnimacoes) throws IOException {
		// Pega todos os elementos de <animacoes>:
		NodeList animacoes = elemAnimacoes.getElementsByTagName("animacao");
		Map<String, ImagemAnimada> listAnimacoes = new HashMap<String, ImagemAnimada>();

		// Passa por todos os itens do tipo <animacao>:
		for (int i = 0; i < animacoes.getLength(); i++) {
			Element elemAnimacao = (Element) animacoes.item(i);
			NodeList frames = elemAnimacao.getElementsByTagName("frame");

			ImagemAnimada animacao =
				new ImagemAnimada(Boolean.valueOf(elemAnimacao.getAttribute("looping")));

			// Passa por todos os itens do tipo <frame>:
			for (int j = 0; j < frames.getLength(); j++) {
				Element frame = (Element) frames.item(j);
				
				ImageIcon imgIcon = new ImageIcon(frame.getAttribute("arquivo"));
				checkImage(imgIcon);

				// Adiciona o frame na animacao:
				animacao.add(new FrameAnimacao(Integer.parseInt(frame
						.getAttribute("duracao")), new ImageIcon(frame
						.getAttribute("arquivo")).getImage()));
				
			}

			// Adiciona a animacao na lista:
			listAnimacoes.put(elemAnimacao.getAttribute("nome"), animacao);
		}

		return listAnimacoes;
	}
	
	/**Verifica se a imagem foi carregada/encontrada
	 * @param imgIcon - A imagem a ser verificada
	 * @throws IOException - se a imagem nao foi carregada/encontrada
	 */
	private void checkImage(ImageIcon imgIcon) throws IOException {
		if (imgIcon.getImageLoadStatus() == MediaTracker.ERRORED)
			throw new IOException("Erro ao carregar a imagem '" + imgIcon.getDescription() + "'");
	}
	
	/**
	 * @param nomeImagem - Nome da imagem.
	 * @return Referencia da imagem ja carregada.
	 */
	public Imagem getImagem(String nomeImagem) {
		return listaImagem.get(nomeImagem);
	}
	
	public ImagemAnimada getImagemAnimada(String nomeAnimacao){
		return listaImagemAnimada.get(nomeAnimacao);
	}
}
