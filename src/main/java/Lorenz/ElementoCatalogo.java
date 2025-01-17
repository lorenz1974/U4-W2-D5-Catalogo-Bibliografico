package Lorenz;

public class ElementoCatalogo {
    private String isbn;
    private String titolo;
    private int annoPubblicazione;
    private int numeroPagine;

    // Costruttore
    public ElementoCatalogo(String isbn, String titolo, int annoPubblicazione, int numeroPagine) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    // Setters
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public void setNumeroPagine(int numeroPagine) {
        this.numeroPagine = numeroPagine;
    }

    @Override
    public String toString() {

        if (Archivio.getFormatoTabella()) {
            return "ElementoCatalogo{" +
                    "isbn='" + isbn + '\'' +
                    ", titolo='" + titolo + '\'' +
                    ", annoPubblicazione=" + annoPubblicazione +
                    ", numeroPagine=" + numeroPagine +
                    '}';
        } else {
            return String.format("| %-11s | %-50s | %-4d | %-4d |", isbn, titolo, annoPubblicazione, numeroPagine);
        }
    }
}