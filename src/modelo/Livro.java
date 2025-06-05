package modelo;

public class Livro extends ItemBiblioteca {
    private String isbn;
    private int numeroPaginas;

    public Livro(String titulo, int anoPublicacao, String isbn, int numeroPaginas) {
        super(titulo, anoPublicacao);
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public String getTipo() {
        return "Livro";
    }

    // Getters e Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" - ISBN: %s, %d páginas", isbn, numeroPaginas);
    }
}