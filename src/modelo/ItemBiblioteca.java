package modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemBiblioteca {
    private String titulo;
    private int anoPublicacao;
    private List<Autor> autores;
    private boolean disponivel;

    public ItemBiblioteca(String titulo, int anoPublicacao) {
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.autores = new ArrayList<>();
        this.disponivel = true;
    }

    // Métodos abstratos (polimorfismo)
    public abstract String getTipo();

    // Getters e Setters (encapsulamento)
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public List<Autor> getAutores() {
        return new ArrayList<>(autores); // Retorna cópia para encapsulamento
    }

    public void adicionarAutor(Autor autor) {
        this.autores.add(autor);
    }

    public void removerAutor(Autor autor) {
        this.autores.remove(autor);
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return String.format("%s: %s (%d) - %s", 
            getTipo(), titulo, anoPublicacao, 
            disponivel ? "Disponível" : "Indisponível");
    }
}