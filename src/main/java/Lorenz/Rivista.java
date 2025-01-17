package Lorenz;

public class Rivista extends ElementoCatalogo {
    private Periodicita periodicita;

    // Costruttore
    public Rivista(String isbn, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        if (Archivio.getFormatoTabella()) {
            return (super.toString()).substring(0, super.toString().length() - 1) + ", periodicita='" + periodicita
                    + "'}";
        } else {
            return super.toString()
                    + String.format(" %-20s | %-10s | %-11s |", "", "", periodicita) + "\n" + "-".repeat(132);
        }
    }
}