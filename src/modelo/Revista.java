package modelo;

public class Revista extends ItemBiblioteca {
    private String issn;
    private int numeroEdicao;

    public Revista(String titulo, int anoPublicacao, String issn, int numeroEdicao) {
        super(titulo, anoPublicacao);
        this.issn = issn;
        this.numeroEdicao = numeroEdicao;
    }

    @Override
    public String getTipo() {
        return "Revista";
    }

    // Getters e Setters
    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public int getNumeroEdicao() {
        return numeroEdicao;
    }

    public void setNumeroEdicao(int numeroEdicao) {
        this.numeroEdicao = numeroEdicao;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" - ISSN: %s, Edição %d", issn, numeroEdicao);
    }
}