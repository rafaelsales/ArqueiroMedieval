package image;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLInput {
	
	private Document document;
	
	public XMLInput(String nomeArquivo) throws IOException {
		File arquivo = new File(nomeArquivo);
		if (!arquivo.exists()) {
			throw new IOException("Arquivo " + arquivo.getName() + " nao encontrado!");
		}
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			document = docBuilder.parse(arquivo);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public Document getDocument(){		
		return document;
	}
}