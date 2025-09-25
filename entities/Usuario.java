package entities;

public class Usuario {
    private String nome;
    private String cpf;

    public Usuario(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    //SALVA NO ARQUIVO O NOME E O CPF DO USUÁRIO CADASTRADO
    public String toArquivo() {
        return nome + ";" + cpf;
    }

   //METÓDO QUE LÊ DO ARQUIVO USUÁRIO.TXT E CRIA O OBJETO USUARIO
    public static Usuario fromArquivo(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 2) return null; //SE FALTAR 1 DOS 2, NOME OU CPF, RETORNA ERRO
        String nome = partes[0];
        String cpf = partes[1];
        return new Usuario(nome, cpf);
    }
	
	@Override
	public String toString() {
		return "Usuario: "+nome+", CPF: "+cpf;
	
	}
}