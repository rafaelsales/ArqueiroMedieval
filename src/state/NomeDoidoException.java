package state;

public class NomeDoidoException extends Exception {
	private static final long serialVersionUID = 1L;
	private String nome;
	public NomeDoidoException(String nome){
		super("Erro - Nome inv�lido!");
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}