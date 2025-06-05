import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import modelo.*;
import servico.BibliotecaService;

public class Main {
    private static BibliotecaService biblioteca = new BibliotecaService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Adiciona alguns dados iniciais para teste
        inicializarDadosTeste();
        
        // Menu principal
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerInteiro("Digite sua opção: ");
            
            switch (opcao) {
                case 1:
                    menuGerenciarItens();
                    break;
                case 2:
                    menuGerenciarAutores();
                    break;
                case 3:
                    menuConsultas();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        
        scanner.close();
    }

    private static void inicializarDadosTeste() {
    Autor autor1 = new Autor("J.K. Rowling", "Britânica");
    Autor autor2 = new Autor("George Orwell", "Britânica");
    Autor autor3 = new Autor("Stephen King", "Americana");

    // Primeiro cadastra os autores
    biblioteca.adicionarAutor(autor1);
    biblioteca.adicionarAutor(autor2);
    biblioteca.adicionarAutor(autor3);

    // Depois cria os itens com os autores
    Livro livro1 = new Livro("Harry Potter e a Pedra Filosofal", 1997, "9788532511010", 264);
    livro1.adicionarAutor(autor1);

    Livro livro2 = new Livro("1984", 1949, "9780451524935", 328);
    livro2.adicionarAutor(autor2);

    Revista revista1 = new Revista("National Geographic", 2023, "1234-5678", 245);
    revista1.adicionarAutor(autor3);

    biblioteca.adicionarItem(livro1);
    biblioteca.adicionarItem(livro2);
    biblioteca.adicionarItem(revista1);
}

    private static void exibirMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE GESTÃO DE BIBLIOTECA ===");
        System.out.println("1. Gerenciar Itens da Biblioteca");
        System.out.println("2. Gerenciar Autores");
       // System.out.println("3. Consultas e Relatórios");
        System.out.println("0. Sair");
    }

    private static void menuGerenciarItens() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR ITENS ===");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Adicionar Revista");
            System.out.println("3. Editar Item");
            System.out.println("4. Remover Item");
            System.out.println("5. Listar Todos os Itens");
            System.out.println("0. Voltar ao menu principal");
            
            opcao = lerInteiro("Digite sua opção: ");
            
            switch (opcao) {
                case 1:
                    adicionarLivro();
                    break;
                case 2:
                    adicionarRevista();
                    break;
                case 3:
                    editarItem();
                    break;
                case 4:
                    removerItem();
                    break;
                case 5:
                    listarTodosItens();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void adicionarLivro() {
        System.out.println("\n--- Adicionar Livro ---");
        String titulo = lerString("Título: ");
        int ano = lerInteiro("Ano de publicação: ");
        String isbn = lerString("ISBN: ");
        int paginas = lerInteiro("Número de páginas: ");
        
        Livro livro = new Livro(titulo, ano, isbn, paginas);
        
        // Adicionar autores
        while (true) {
            String nomeAutor = lerString("Nome do autor (ou deixe em branco para parar): ");
            if (nomeAutor.isBlank()) break;
            
            Optional<Autor> autorExistente = buscarAutorPorNome(nomeAutor);
            if (autorExistente.isPresent()) {
                livro.adicionarAutor(autorExistente.get());
                System.out.println("Autor adicionado: " + autorExistente.get());
            } else {
                System.out.println("Autor não encontrado. Cadastre o autor primeiro no menu de Autores.");
            }
        }
        
        biblioteca.adicionarItem(livro);
        System.out.println("Livro adicionado com sucesso!");
    }

    private static void adicionarRevista() {
        System.out.println("\n--- Adicionar Revista ---");
        String titulo = lerString("Título: ");
        int ano = lerInteiro("Ano de publicação: ");
        String issn = lerString("ISSN: ");
        int edicao = lerInteiro("Número da edição: ");
        
        Revista revista = new Revista(titulo, ano, issn, edicao);
        
        // Adicionar autores
        while (true) {
            String nomeAutor = lerString("Nome do autor (ou deixe em branco para parar): ");
            if (nomeAutor.isBlank()) break;
            
            Optional<Autor> autorExistente = buscarAutorPorNome(nomeAutor);
            if (autorExistente.isPresent()) {
                revista.adicionarAutor(autorExistente.get());
                System.out.println("Autor adicionado: " + autorExistente.get());
            } else {
                System.out.println("Autor não encontrado. Cadastre o autor primeiro no menu de Autores.");
            }
        }
        
        biblioteca.adicionarItem(revista);
        System.out.println("Revista adicionada com sucesso!");
    }

    private static void editarItem() {
        System.out.println("\n--- Editar Item ---");
        String titulo = lerString("Digite o título do item que deseja editar: ");
        
        Optional<ItemBiblioteca> itemExistente = biblioteca.buscarItemPorTitulo(titulo);
        if (itemExistente.isEmpty()) {
            System.out.println("Item não encontrado!");
            return;
        }
        
        ItemBiblioteca item = itemExistente.get();
        System.out.println("Editando item: " + item);
        
        if (item instanceof Livro) {
            editarLivro((Livro) item);
        } else if (item instanceof Revista) {
            editarRevista((Revista) item);
        }
        
        System.out.println("Item editado com sucesso!");
    }

    private static void editarLivro(Livro livro) {
        String novoTitulo = lerString("Novo título [" + livro.getTitulo() + "]: ");
        if (!novoTitulo.isBlank()) livro.setTitulo(novoTitulo);
        
        String novoAnoStr = lerString("Novo ano [" + livro.getAnoPublicacao() + "]: ");
        if (!novoAnoStr.isBlank()) livro.setAnoPublicacao(Integer.parseInt(novoAnoStr));
        
        String novoIsbn = lerString("Novo ISBN [" + livro.getIsbn() + "]: ");
        if (!novoIsbn.isBlank()) livro.setIsbn(novoIsbn);
        
        String novoPaginasStr = lerString("Novo número de páginas [" + livro.getNumeroPaginas() + "]: ");
        if (!novoPaginasStr.isBlank()) livro.setNumeroPaginas(Integer.parseInt(novoPaginasStr));
    }

    private static void editarRevista(Revista revista) {
        String novoTitulo = lerString("Novo título [" + revista.getTitulo() + "]: ");
        if (!novoTitulo.isBlank()) revista.setTitulo(novoTitulo);
        
        String novoAnoStr = lerString("Novo ano [" + revista.getAnoPublicacao() + "]: ");
        if (!novoAnoStr.isBlank()) revista.setAnoPublicacao(Integer.parseInt(novoAnoStr));
        
        String novoIssn = lerString("Novo ISSN [" + revista.getIssn() + "]: ");
        if (!novoIssn.isBlank()) revista.setIssn(novoIssn);
        
        String novoEdicaoStr = lerString("Novo número da edição [" + revista.getNumeroEdicao() + "]: ");
        if (!novoEdicaoStr.isBlank()) revista.setNumeroEdicao(Integer.parseInt(novoEdicaoStr));
    }

    private static void removerItem() {
        System.out.println("\n--- Remover Item ---");
        String titulo = lerString("Digite o título do item que deseja remover: ");
        
        if (biblioteca.removerItem(titulo)) {
            System.out.println("Item removido com sucesso!");
        } else {
            System.out.println("Item não encontrado!");
        }
    }

    private static void listarTodosItens() {
        System.out.println("\n--- Todos os Itens da Biblioteca ---");
        List<ItemBiblioteca> itens = biblioteca.listarTodosItens();
        
        if (itens.isEmpty()) {
            System.out.println("Nenhum item cadastrado.");
        } else {
            itens.forEach(System.out::println);
        }
    }

    private static void menuGerenciarAutores() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR AUTORES ===");
            System.out.println("1. Cadastrar Autor");
            System.out.println("2. Listar Autores");
            System.out.println("0. Voltar ao menu principal");
            
            opcao = lerInteiro("Digite sua opção: ");
            
            switch (opcao) {
                case 1:
                    cadastrarAutor();
                    break;
                case 2:
                    listarAutores();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

   private static void cadastrarAutor() {
    System.out.println("\n--- Cadastrar Autor ---");
    String nome = lerString("Nome: ");
    String nacionalidade = lerString("Nacionalidade: ");
    
    Autor autor = new Autor(nome, nacionalidade);
    biblioteca.adicionarAutor(autor);  // Agora adiciona ao repositório
    System.out.println("Autor cadastrado com sucesso: " + autor);
}

  private static void listarAutores() {
    System.out.println("\n--- Lista de Autores ---");
    List<Autor> autores = biblioteca.listarAutores();
    
    if (autores.isEmpty()) {
        System.out.println("Nenhum autor cadastrado.");
    } else {
        autores.forEach(System.out::println);
    }
}

   private static Optional<Autor> buscarAutorPorNome(String nome) {
    return biblioteca.buscarAutorPorNome(nome);  // Agora usa o serviço
   }

    private static void menuConsultas() {
        int opcao;
        do {
            System.out.println("\n=== CONSULTAS E RELATÓRIOS ===");
            System.out.println("1. Buscar Item por Título");
            System.out.println("2. Listar Itens Disponíveis");
            System.out.println("3. Listar Livros");
            System.out.println("4. Listar Revistas");
            System.out.println("0. Voltar ao menu principal");
            
            opcao = lerInteiro("Digite sua opção: ");
            
            switch (opcao) {
                case 1:
                    buscarItemPorTitulo();
                    break;
                case 2:
                    listarItensDisponiveis();
                    break;
                case 3:
                    listarLivros();
                    break;
                case 4:
                    listarRevistas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void buscarItemPorTitulo() {
        System.out.println("\n--- Buscar Item por Título ---");
        String titulo = lerString("Digite o título: ");
        
        Optional<ItemBiblioteca> item = biblioteca.buscarItemPorTitulo(titulo);
        if (item.isPresent()) {
            System.out.println("Item encontrado: " + item.get());
        } else {
            System.out.println("Item não encontrado!");
        }
    }

    private static void listarItensDisponiveis() {
        System.out.println("\n--- Itens Disponíveis ---");
        List<ItemBiblioteca> itens = biblioteca.listarItensDisponiveis();
        
        if (itens.isEmpty()) {
            System.out.println("Nenhum item disponível no momento.");
        } else {
            itens.forEach(System.out::println);
        }
    }

    private static void listarLivros() {
        System.out.println("\n--- Lista de Livros ---");
        List<Livro> livros = biblioteca.listarLivros();
        
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private static void listarRevistas() {
        System.out.println("\n--- Lista de Revistas ---");
        List<Revista> revistas = biblioteca.listarRevistas();
        
        if (revistas.isEmpty()) {
            System.out.println("Nenhuma revista cadastrada.");
        } else {
            revistas.forEach(System.out::println);
        }
    }

    // Métodos auxiliares para entrada de dados
    private static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido!");
            }
        }
    }
}