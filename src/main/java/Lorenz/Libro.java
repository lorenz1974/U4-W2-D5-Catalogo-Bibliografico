package Lorenz;

public class Libro extends ElementoCatalogo {
    private String autore;
    private String genere;

    // Costruttore
    public Libro(String isbn, String titolo, int annoPubblicazione, int numeroPagine, String autore, String genere) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
        this.autore = autore;
        this.genere = genere;
    }

    // Getters
    public String getAutore() {
        return autore;
    }

    public String getGenere() {
        return genere;
    }

    // Setters
    public void setAutore(String autore) {
        this.autore = autore;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public String toString() {
        if (Archivio.getFormatoTabella()) {
            return (super.toString()).substring(0, super.toString().length() - 1) + ", autore='" + autore + '\''
                    + ", genere='" + genere + "'}";
        } else {
            return super.toString() + String.format(" %-20s | %-10s | %-11s |", autore, genere, "") + "\n"
                    + "-".repeat(132);
        }
    }
}
