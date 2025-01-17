package Lorenz;

import java.util.*;
import java.util.stream.Collectors;

import static Lorenz.Utils.*;

class Archivio {
    private List<ElementoCatalogo> catalogo;

    // Variabile che determina il comportamento di toSting() degli elementi
    private static boolean formatoTabella;
    private String fileJson;

    public Archivio(String fileJson) {
        this.catalogo = new ArrayList<>();
        this.fileJson = fileJson;
        formatoTabella = false;
    }

    public void setFormatoTabella(boolean setting) {
        formatoTabella = setting;
    }

    public static boolean getFormatoTabella() {
        return formatoTabella;
    }

    public void anySearch(String query) {

        // Uso un Set per avere risultati unici
        Set<ElementoCatalogo> risultatiAnySearch = new HashSet<>();

        // Esegue la ricerca per testo
        String queryText = query.toLowerCase();

        // Cerca per autore e genere nei libri
        Set<ElementoCatalogo> risultatiLibri = catalogo.stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(libro -> libro.getTitolo().toLowerCase().contains(queryText) ||
                        libro.getIsbn().toLowerCase().contains(queryText)
                        || libro.getAutore().toLowerCase().contains(queryText) ||
                        libro.getGenere().toLowerCase().contains(queryText))
                .collect(Collectors.toSet());
        risultatiAnySearch.addAll(risultatiLibri);

        // Esegue un altra ricerca per le riviste perché hanno campi diversi
        Set<ElementoCatalogo> risultatiRiviste = catalogo.stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(rivista -> rivista.getTitolo().toLowerCase().contains(queryText) ||
                        rivista.getIsbn().toLowerCase().contains(queryText))
                .collect(Collectors.toSet());
        risultatiAnySearch.addAll(risultatiRiviste);

        // Se la query è convertibile in un numero intero, cerca anche sui campi
        // numerici
        int queryNum = query.matches("\\d+") ? Integer.parseInt(query) : -1;
        if (queryNum != -1) {
            Set<ElementoCatalogo> risultatiNumerici = catalogo.stream()
                    .filter(e -> e.getAnnoPubblicazione() == queryNum || e.getNumeroPagine() == queryNum)
                    .collect(Collectors.toSet());
            risultatiAnySearch.addAll(risultatiNumerici);
        }

        // Stampa i risultati
        risultatiAnySearch.forEach(e -> _W(e.toString()));
    }

    public void elencaTuttiGliElementi() {
        catalogo.forEach(e -> _W(e.toString()));
    }

    public boolean controlloIsbn(String isbn) {
        return catalogo.stream().anyMatch(e -> e.getIsbn().equals(isbn));
    }

    public void aggiungiElemento(ElementoCatalogo elemento) throws ErroreGenericoException {

        // Anche se il controllo viene fatto in fase di richiesta dati controlla
        // nuovamente
        // che l'ISBN non esista già per evitare errori
        if (catalogo.stream().anyMatch(e -> e.getIsbn().equals(elemento.getIsbn()))) {
            throw new ErroreGenericoException("Elemento con lo stesso ISBN già presente.");
        }
        catalogo.add(elemento);

        GeneratoreFileJSON.scriviSuFileJSON(catalogo, "catalogo.json");
    }

    public List<ElementoCatalogo> ricercaPerIsbn(String isbn) throws ElementoNonTrovatoException {
        List<ElementoCatalogo> risultati = catalogo.stream()
                .filter(e -> e.getIsbn().equals(isbn))
                .findFirst()
                .stream()
                .collect(Collectors.toList());
        if (risultati.isEmpty()) {
            throw new ElementoNonTrovatoException("Nessun con ISBN " + isbn + " non trovato.");
        }
        return risultati;
    }

    public void rimuoviElemento(String isbn) throws ElementoNonTrovatoException {
        boolean rimosso = catalogo.removeIf(e -> e.getIsbn().equals(isbn));
        if (!rimosso) {
            throw new ElementoNonTrovatoException("Elemento con ISBN " + isbn + " non trovato.");
        }

        // Scrive il file JSON
        GeneratoreFileJSON.scriviSuFileJSON(catalogo, "catalogo.json");

    }

    public List<ElementoCatalogo> ricercaPerAnno(int anno) throws ElementoNonTrovatoException {
        List<ElementoCatalogo> risultati = catalogo.stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());

        if (risultati.isEmpty()) {
            throw new ElementoNonTrovatoException("Nessun elemento trovato per l'anno " + anno + ".");
        }
        return risultati;
    }

    public List<Libro> ricercaPerAutore(String autore) throws ElementoNonTrovatoException {
        List<Libro> risultati = catalogo.stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(libro -> libro.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
        if (risultati.isEmpty()) {
            throw new ElementoNonTrovatoException("Nessun libro trovato per l'autore " + autore + ".");
        }
        return risultati;
    }

    public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento)
            throws ElementoNonTrovatoException, ErroreGenericoException {

        // Prima rimuove l'elemento
        rimuoviElemento(isbn);
        // Poi lo aggiunge di nuovo
        aggiungiElemento(nuovoElemento);

        // Scrive il file JSON
        GeneratoreFileJSON.scriviSuFileJSON(catalogo, "catalogo.json");

        // ... Gli errori sono gestiti dal metodo di rimozione e aggiunta. Credo...
    }

    public void stampaStatistiche() {
        // Calcola prima tutti gli elementi e poi li manda a video
        long totaleLibri = catalogo.stream().filter(e -> e instanceof Libro).count();
        long totaleRiviste = catalogo.stream().filter(e -> e instanceof Rivista).count();

        ElementoCatalogo elementoConPiuPagine = catalogo.stream()
                .max(Comparator.comparingInt(ElementoCatalogo::getNumeroPagine))
                .get();

        double mediaPagine = catalogo.stream().mapToInt(ElementoCatalogo::getNumeroPagine).average().orElse(0);

        Map<String, Long> elementiPerGenere = catalogo.stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .collect(Collectors.groupingBy(Libro::getGenere, TreeMap::new, Collectors.counting()));

        Map<String, Long> elementiPerAutore = catalogo.stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .collect(Collectors.groupingBy(Libro::getAutore, TreeMap::new, Collectors.counting()));

        Map<Integer, Long> elementiPerAnno = catalogo.stream()
                .collect(Collectors.groupingBy(
                        ElementoCatalogo::getAnnoPubblicazione,
                        TreeMap::new,
                        Collectors.counting()));

        // Stampa a video una tabella pulita
        _C();
        _R("-", 50);
        _W("STATISTICHE");
        _R("-", 50);

        _W(" - Totale libri: " + totaleLibri);
        _W(" - Totale riviste: " + totaleRiviste);
        _W(" - Elemento con più pagine: " + elementoConPiuPagine.getTitolo());
        _W(" - Media pagine: " + mediaPagine);
        _R("-", 50);

        _W("");
        _R("-", 50);
        _W("Numero di elementi per genere:");
        elementiPerGenere.forEach((genere, count) -> _W(" - " + genere + ": " + count));

        _W("");
        _R("-", 50);
        _W("Numero di elementi per autore:");
        elementiPerAutore.forEach((autore, count) -> _W(" - " + autore + ": " + count));

        _W("");
        _R("-", 50);
        _W("Numero di elementi per anno:");
        elementiPerAnno.forEach((anno, count) -> _W(" - " + anno + ": " + count));

    }

    public void caricaDaFile() {
        catalogo.addAll(JsonImporter.importaDaFile(fileJson));
    }
}
