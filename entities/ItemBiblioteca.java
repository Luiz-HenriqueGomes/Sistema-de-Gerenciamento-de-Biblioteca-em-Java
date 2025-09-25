package entities;

//=================SUPERCLASSE==================

public class ItemBiblioteca {
    private int codigo;
    private String titulo;

    public ItemBiblioteca() {}

    public ItemBiblioteca(int codigo, String titulo) {
        this.codigo = codigo;
        this.titulo = titulo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Título: " + titulo;
    }
}
