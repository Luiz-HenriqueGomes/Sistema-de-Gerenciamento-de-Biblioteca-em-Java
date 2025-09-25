package entities;

public class Livro extends ItemBiblioteca {
    private String autor;
    private int quantidadeDisponivel;

    public Livro(int codigo, String titulo, String autor) {
        super(codigo, titulo); //ACESSA OS ATRIBTOS DA CLASSE MÃE 
        this.autor = autor;
        this.quantidadeDisponivel = 1;
    }
// SOBRECARGA DE CONSTRUTORES PARA TER MAIS FLEXIBILIDADE
    public Livro(int codigo, String titulo, String autor, int quantidadeDisponivel) {
        super(codigo, titulo); //TAMBÉM ACESSA OS ATRIBUTOS DA CLASSE MÃE
        this.autor = autor;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public boolean emprestar() {
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
            return true;
        }
        return false;
    }

    public void devolver() {
        quantidadeDisponivel++;
    }

    public String toArquivo() {
        return getCodigo() + ";" + getTitulo() + ";" + autor + ";" + quantidadeDisponivel;
    }
    //LÊ O ARQUIVO DE LIVROS.TXT E CRIA UM OBJETO
    public static Livro fromArquivo(String linha) {
        String[] partes = linha.split(";");
        int codigo = Integer.parseInt(partes[0].trim());
        String titulo = partes[1].trim();
        String autor = partes[2].trim();
        int quantidade = partes.length >= 4 ? Integer.parseInt(partes[3].trim()) : 1;
        return new Livro(codigo, titulo, autor, quantidade);
    }

    @Override
    public String toString() {
        return "Código: " + getCodigo() + ", Título: " + getTitulo() + ", Autor: " + autor + ", Quantidade: " + quantidadeDisponivel;
    }
}
