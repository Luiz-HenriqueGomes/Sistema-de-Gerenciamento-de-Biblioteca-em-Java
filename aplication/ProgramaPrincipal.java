package aplication;

import java.io.*;
import java.util.*;
import entities.Biblioteca;
import entities.Livro;
import entities.Usuario;
//FEITA ALTERAÇÃO PARA TESTE NO GITHUB
public class ProgramaPrincipal {
	//GUARDA OS NOMES DOS ARQUIVOS QUE SÃO SALVOS NO SISTEMA
    private static final String ARQUIVO_LIVROS = "livros.txt";
    private static final String ARQUIVO_USUARIOS = "usuarios.txt";
    
    
    private static Biblioteca biblioteca = new Biblioteca("Biblioteca Central");//INSTANCIAÇÃO DA CLASSE BIBLIOTECA 
    private static List<Usuario> usuarios = new LinkedList<>(); //ARMAZENA OS USUÁRIOS CADASTRADOS

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        //CARREGAMENTOS DOS DADOS SALVOS 
        carregarLivrosDoArquivo();
        carregarUsuariosDoArquivo();

        int opcao;

        do {
            System.out.println("\n======= MENU =======");
            System.out.println("[1] Cadastrar Livro");
            System.out.println("[2] Listar Livros");
            System.out.println("[3] Editar Livro por Código");
            System.out.println("[4] Remover Livro por Código");
            System.out.println("[5] Buscar Livro por Título ou Autor");
            System.out.println("[6] Realizar Empréstimo");
            System.out.println("[7] Devolver Livro");
            System.out.println("[8] Mostrar total de livros em estoque");
            System.out.println("[9] Cadastrar usuário");
            System.out.println("[10] Listar usuários");
            System.out.println("[11] Editar usuário");
            System.out.println("[12] Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    cadastrarLivros(sc);
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    editarLivro(sc);
                    break;
                case 4:
                    removerLivro(sc);
                    break;
                case 5:
                    buscarLivro(sc);
                    break;
                case 6:
                    realizarEmprestimo(sc);
                    break;
                case 7:
                    realizarDevolucao(sc);
                    break;
                case 8:
                    mostrarTotalEstoque();
                    break;
                case 9:
                    cadastrarUsuario(sc);
                    break;
                case 10:
                    listarUsuarios();
                    break;
                case 11:
                    editarUsuario(sc);
                    break;
                case 12:
                    salvarLivrosNoArquivo();
                    salvarUsuariosNoArquivo();
                    System.out.println("Saindo... Arquivos atualizados!");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 12);

        sc.close();
    }

    private static void cadastrarLivros(Scanner sc) {
        System.out.print("Código: ");
        int codigo = Integer.parseInt(sc.nextLine());//CONVERTE A STRING DIGITADA EM INTEIRO
        System.out.print("Título: ");
        String titulo = sc.nextLine();
        System.out.print("Autor: ");
        String autor = sc.nextLine();
        System.out.print("Quantidade de cópias disponíveis: ");
        int quantidade = Integer.parseInt(sc.nextLine());

        Livro livro = new Livro(codigo, titulo, autor, quantidade); //CRIA O LIVRO COM OS DADOS FORNECIDOS
        biblioteca.adicionarLivro(livro);
        System.out.println("Livro adicionado com sucesso. " + quantidade + " cópias disponíveis.");
    }

    private static void listarLivros() {
        System.out.println("\n Lista de Livros: ");
        biblioteca.mostrarLivros();
    }

    private static void editarLivro(Scanner sc) {
        System.out.println("Informe o código do livro a editar: ");
        int codigo = Integer.parseInt(sc.nextLine());

        Livro livroParaEditar = buscarLivroPorCodigo(codigo);
        if (livroParaEditar == null) {
            System.out.println("Livro não encontrado.");
            return;
        }

        System.out.println("Novo título: (Enter para manter): ");
        String novoTitulo = sc.nextLine();
        if (!novoTitulo.isEmpty()) livroParaEditar.setTitulo(novoTitulo); // "!" NEGAÇÃO LÓGICA

        System.out.println("Novo autor (Enter para manter): ");
        String novoAutor = sc.nextLine();
        if (!novoAutor.isEmpty()) livroParaEditar.setAutor(novoAutor);

        System.out.println("Nova quantidade (Enter para manter): ");
        String novaQuantidade = sc.nextLine();
        if (!novaQuantidade.isEmpty()) {
            int qtd = Integer.parseInt(novaQuantidade);
            livroParaEditar.setQuantidadeDisponivel(qtd);
        }

        System.out.println("Livro atualizado com sucesso!");
    }

    private static void removerLivro(Scanner sc) {
        System.out.println("Informe o código do livro a remover: ");
        int codigo = Integer.parseInt(sc.nextLine());
        boolean removido = biblioteca.getLivros().removeIf(livro -> livro.getCodigo() == codigo);
        if (removido) System.out.println("Livro removido com sucesso!");
        else System.out.println("Livro não encontrado.");
    }

    private static void buscarLivro(Scanner sc) {
        System.out.println("Digite a parte do título ou autor: ");
        String termo = sc.nextLine().toLowerCase();
        boolean encontrado = false;
        for (Livro livro : biblioteca.getLivros()) { //ACESSA E PEGA OS ELEMENTOS
            if (livro.getTitulo().toLowerCase().contains(termo) || livro.getAutor().toLowerCase().contains(termo)) {
                System.out.println(livro);
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("Nenhum livro encontrado com esse termo.");
    }

    private static void realizarEmprestimo(Scanner sc) {
        System.out.print("Digite o CPF do usuário que está alugando: ");
        String cpf = sc.nextLine();
        Usuario usuario = buscarUsuarioPorCpf(cpf);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        System.out.println("Usuário: " + usuario.getNome());

        System.out.print("Digite o código do livro que deseja alugar: ");
        int codigo = Integer.parseInt(sc.nextLine());
        Livro livro = buscarLivroPorCodigo(codigo);
        if (livro != null) {
            if (livro.emprestar()) {
                System.out.println("Empréstimo realizado com sucesso!");
            } else {
                System.out.println("Este livro está indisponível no momento.");
            }
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private static void realizarDevolucao(Scanner sc) {
        System.out.print("Digite o código do livro que deseja devolver: ");
        int codigo = Integer.parseInt(sc.nextLine());
        Livro livro = buscarLivroPorCodigo(codigo);
        if (livro != null) {
            livro.devolver();
            System.out.println("Devolução realizada! Agora há " + livro.getQuantidadeDisponivel() + " disponível.");
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private static Livro buscarLivroPorCodigo(int codigo) {
        for (Livro livro : biblioteca.getLivros()) {
            if (livro.getCodigo() == codigo) return livro;
        }
        return null;
    }

    private static void mostrarTotalEstoque() {
        int total = 0;
        for (Livro livro : biblioteca.getLivros()) {
            total += livro.getQuantidadeDisponivel();
        }
        System.out.println("Total de livros em estoque: " + total);
    }

    private static void cadastrarUsuario(Scanner sc) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        Usuario usuario = new Usuario(nome, cpf);
        usuarios.add(usuario);
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_USUARIOS, true))) {
            pw.println(usuario.toArquivo());
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
        System.out.println("Usuário cadastrado com sucesso.");
    }

    private static void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        System.out.println("\nLista de usuários:\n");
        for (Usuario u : usuarios) System.out.println(u);
    }

    private static Usuario buscarUsuarioPorCpf(String cpf) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(cpf)) return u;
        }
        return null;
    }

    private static void carregarLivrosDoArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_LIVROS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Livro livro = Livro.fromArquivo(linha);
                biblioteca.adicionarLivro(livro);
            }
        } catch (IOException e) {
            System.out.println("Nenhum arquivo de livros encontrado.");
        }
    }

    private static void salvarLivrosNoArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_LIVROS))) {
            for (Livro livro : biblioteca.getLivros()) {
                pw.println(livro.toArquivo());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar livros: " + e.getMessage());
        }
    }

    private static void carregarUsuariosDoArquivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Usuario usuario = Usuario.fromArquivo(linha);
                if (usuario != null) usuarios.add(usuario);
            }
        } catch (IOException e) {
            System.out.println("Nenhum arquivo de usuários encontrado.");
        }
    }

    private static void salvarUsuariosNoArquivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Usuario u : usuarios) {
                pw.println(u.toArquivo());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }
    
    private static void editarUsuario(Scanner sc) {
        System.out.print("Informe o CPF do usuário a ser editado: ");
        String cpf = sc.nextLine();

        Usuario usuario = buscarUsuarioPorCpf(cpf);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.println("Usuário encontrado: " + usuario);

        System.out.print("Novo nome (Enter para manter '" + usuario.getNome() + "'): ");
        String novoNome = sc.nextLine();
        if (!novoNome.isEmpty()) {
            usuario.setNome(novoNome);
        }

        System.out.print("Novo CPF (Enter para manter '" + usuario.getCpf() + "'): ");
        String novoCpf = sc.nextLine();
        if (!novoCpf.isEmpty()) {
            usuario.setCpf(novoCpf);
        }

     
        salvarUsuariosNoArquivo();

        System.out.println("Usuário atualizado com sucesso!");
    }
}
