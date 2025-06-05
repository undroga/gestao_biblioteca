package servico;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.*;

public class BibliotecaService {
    private List<ItemBiblioteca> itens;
    private List<Autor> autores;  // Novo repositório de autores

    public BibliotecaService() {
        this.itens = new ArrayList<>();
        this.autores = new ArrayList<>();  // Inicializa a lista de autores
    }

    // Métodos para gerenciar autores
    public void adicionarAutor(Autor autor) {
        autores.add(autor);
    }

    public List<Autor> listarAutores() {
        return new ArrayList<>(autores);  // Retorna cópia para encapsulamento
    }

    public Optional<Autor> buscarAutorPorNome(String nome) {
        return autores.stream()
                   .filter(a -> a.getNome().equalsIgnoreCase(nome))
                   .findFirst();
    }
    // CRUD para itens da biblioteca
    public void adicionarItem(ItemBiblioteca item) {
        itens.add(item);
    }

    public boolean removerItem(String titulo) {
        return itens.removeIf(item -> item.getTitulo().equalsIgnoreCase(titulo));
    }

    public Optional<ItemBiblioteca> buscarItemPorTitulo(String titulo) {
        return itens.stream()
                   .filter(item -> item.getTitulo().equalsIgnoreCase(titulo))
                   .findFirst();
    }

    public List<ItemBiblioteca> listarTodosItens() {
        return new ArrayList<>(itens); // Retorna cópia para encapsulamento
    }

    public List<ItemBiblioteca> listarItensDisponiveis() {
        return itens.stream()
                   .filter(ItemBiblioteca::isDisponivel)
                   .toList();
    }

    public boolean editarItem(String tituloAntigo, ItemBiblioteca novoItem) {
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getTitulo().equalsIgnoreCase(tituloAntigo)) {
                itens.set(i, novoItem);
                return true;
            }
        }
        return false;
    }

    // Métodos específicos para livros
    public List<Livro> listarLivros() {
        return itens.stream()
                   .filter(item -> item instanceof Livro)
                   .map(item -> (Livro) item)
                   .toList();
    }

    // Métodos específicos para revistas
    public List<Revista> listarRevistas() {
        return itens.stream()
                   .filter(item -> item instanceof Revista)
                   .map(item -> (Revista) item)
                   .toList();
    }
}