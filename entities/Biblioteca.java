package entities;

import java.util.LinkedList;
import java.util.List;

public class Biblioteca {
    private String nome;
    private List<Livro> livros;

    public Biblioteca(String nome) {
        this.setNome(nome);
        this.livros = new LinkedList<>();
    }
    
    //MÉTODOS QUE ADICIONAM E MOSTRAM OS LIVROS
    						
    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public List<Livro> getLivros() {
        return livros;
    }
    
   
    public void mostrarLivros() {
        for (Livro livro : livros) { //FOR QUE APENAS ACESSA OS ELEMENTOS DA ARRAY DE LIVROS SEM ALTERA-LOS 
            System.out.println(livro);
        }
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
