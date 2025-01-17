package Lorenz;

import java.util.*;

import static Lorenz.Utils.*;

public class Main {
    public static void main(String[] args) throws ElementoNonTrovatoException, ErroreGenericoException {
        Archivio archivio = new Archivio("catalogo.json");

        Scanner scanner = new Scanner(System.in);

        // Esempio di caricamento file JSON
        archivio.caricaDaFile();

        boolean esci = false;

        // Cancella lo schermo all'inizializzazione
        _C();

        while (!esci) {
            _M();
            _Wn("\nSeleziona un'operazione: ");
            String scelta = (String) _LI("string", scanner);

            try {
                switch (scelta.toLowerCase()) {

                    // Elenca tutti gli alemententi
                    case "0" -> {
                        _W("");
                        _R("-", 132);

                        archivio.elencaTuttiGliElementi();

                        _W("");
                        _P();

                    }

                    // Aggiungi elemento
                    case "1" -> {
                        _W("");
                        _R("-", 60);

                        String tipo = "";
                        while (!"L".equals(tipo) && !"R".equals(tipo)) {
                            _Wn("\nVuoi aggiungere un libro o una rivista? (L/R) : ");
                            tipo = ((String) _LI("string", scanner)).toUpperCase();

                            if (!"L".equals(tipo) && !"R".equals(tipo)) {
                                _W("\nTipo non valido.");
                            }
                        }

                        // Esegue la verifica se l'ISBN è già presente
                        String isbn = "";
                        boolean continua = false;
                        while (!continua) {
                            _Wn("\nInserisci ISBN: ");
                            isbn = (String) _LI("string", scanner);

                            if (isbn.length() <= 5) {
                                _W("\nL'ISBN deve essere di almeno 5 caratteri.");
                                continue;
                            }
                            if (archivio.controlloIsbn(isbn)) {
                                _W("\nISBN già presente.");
                                continue;
                            }
                            continua = true;
                        }

                        _Wn("\nInserisci titolo: ");
                        String titolo = (String) _LI("string", scanner);

                        _Wn("\nInserisci anno di pubblicazione: ");
                        int annoPubblicazione = (int) _LI("int", scanner);

                        _Wn("\nInserisci numero di pagine: ");
                        int numeroPagine = (int) _LI("int", scanner);

                        if ("L".equals(tipo)) {
                            _Wn("\nInserisci autore: ");
                            String autore = (String) _LI("string", scanner);

                            _Wn("\nInserisci genere: ");
                            String genere = (String) _LI("string", scanner);

                            Libro libro = new Libro(isbn, titolo, annoPubblicazione, numeroPagine, autore, genere);
                            archivio.aggiungiElemento(libro);

                        } else if ("R".equals(tipo)) {
                            _Wn("\nInserisci periodicità (SETTIMANALE/MENSILE/SEMESTRALE): ");
                            Periodicita periodicita = Periodicita.valueOf(
                                    ((String) _LI("string", scanner)).toUpperCase());

                            Rivista rivista = new Rivista(isbn, titolo, annoPubblicazione, numeroPagine, periodicita);
                            archivio.aggiungiElemento(rivista);

                        }

                        _W("");
                        _R("-", 132);
                        archivio.ricercaPerIsbn(isbn).forEach(System.out::println);

                        _W("");
                        _P();
                    }

                    // Ricerca per ISBN
                    case "2" -> {
                        _W("");
                        _R("-", 60);
                        _Wn("\nInserisci ISBN: ");
                        String isbn = (String) _LI("string", scanner);

                        _W("");
                        _R("-", 132);
                        archivio.ricercaPerIsbn(isbn).forEach(System.out::println);

                        _W("");
                        _P();
                    }

                    // Rimuovi elemento
                    case "3" -> {
                        _W("");
                        _R("-", 60);
                        _Wn("\nInserisci ISBN dell'elemento da rimuovere: ");
                        String isbn = (String) _LI("string", scanner);

                        archivio.rimuoviElemento(isbn);

                        _R("-", 60);
                        _W("");
                        _P();
                    }

                    // Ricerca per anno
                    case "4" -> {
                        _W("");
                        _R("-", 60);

                        _Wn("\nInserisci anno di pubblicazione: ");
                        int anno = (int) _LI("int", scanner);

                        _W("");
                        _R("-", 132);
                        archivio.ricercaPerAnno(anno).forEach(System.out::println);

                        _W("");
                    }

                    // Ricerca per autore
                    case "5" -> {
                        _W("");
                        _R("-", 60);

                        _Wn("\nInserisci autore: ");
                        String autore = (String) _LI("string", scanner);

                        _W("");
                        _R("-", 132);
                        archivio.ricercaPerAutore(autore).forEach(System.out::println);

                        _W("");
                        _P();
                    }

                    // Aggiorna elemento
                    case "6" -> {
                        _W("");
                        _R("-", 60);

                        String tipo = "";
                        while (!"L".equals(tipo) && !"R".equals(tipo)) {
                            _Wn("\nVuoi aggiungere un libro o una rivista? (L/R) : ");
                            tipo = ((String) _LI("string", scanner)).toUpperCase();

                            if (!"L".equals(tipo) && !"R".equals(tipo)) {
                                _W("\nTipo non valido.");
                            }
                        }

                        // Esegue la verifica se l'ISBN è già presente
                        String isbn = "";
                        boolean continua = false;
                        while (!continua) {
                            _Wn("\nInserisci ISBN dell'elemento da aggiornare: ");
                            isbn = (String) _LI("string", scanner);

                            if (isbn.length() <= 5) {
                                _W("\nL'ISBN deve essere di almeno 5 caratteri.");
                                continue;
                            }
                            if (!archivio.controlloIsbn(isbn)) {
                                _W("\nISBN non presente. Verifica e reinserisci");
                                continue;
                            }
                            continua = true;
                        }

                        _Wn("\nInserisci titolo: ");
                        String titolo = (String) _LI("string", scanner);

                        _Wn("\nInserisci anno di pubblicazione: ");
                        int annoPubblicazione = (int) _LI("int", scanner);

                        _Wn("\nInserisci numero di pagine: ");
                        int numeroPagine = (int) _LI("int", scanner);

                        if ("L".equals(tipo)) {
                            _Wn("\nInserisci autore: ");
                            String autore = (String) _LI("string", scanner);

                            _Wn("\nInserisci genere: ");
                            String genere = (String) _LI("string", scanner);

                            Libro libro = new Libro(isbn, titolo, annoPubblicazione, numeroPagine, autore, genere);
                            archivio.aggiornaElemento(isbn, libro);

                        } else if ("R".equals(tipo)) {
                            _Wn("\nInserisci periodicità (SETTIMANALE/MENSILE/SEMESTRALE): ");
                            Periodicita periodicita = Periodicita.valueOf(
                                    ((String) _LI("string", scanner)).toUpperCase());

                            Rivista rivista = new Rivista(isbn, titolo, annoPubblicazione, numeroPagine,
                                    periodicita);
                            archivio.aggiornaElemento(isbn, rivista);

                        }

                        _W("");
                        _R("-", 132);
                        archivio.ricercaPerIsbn(isbn).forEach(System.out::println);

                        _W("");
                        _P();
                    }

                    // Stampa statistiche
                    case "7" -> {
                        _W("");
                        _R("-", 60);

                        archivio.stampaStatistiche();

                        _R("-", 60);
                        _W("");
                        _P();
                    }

                    // AnySearch
                    case "a" -> {
                        _W("");
                        _R("-", 60);
                        _Wn("\nInserisci il testo da ricercare in tutti i campi: ");

                        String query = (String) _LI("string", scanner);
                        archivio.anySearch(query);

                        _W("");
                        _P();
                    }

                    // Cambia formato tabella
                    case "b" -> {
                        _W("");
                        _R("-", 60);

                        archivio.setFormatoTabella(!Archivio.getFormatoTabella());

                        _R("-", 60);
                        _W("");
                    }

                    // Esci
                    case "z" -> {
                        _W("");
                        _R("-", 60);
                        esci = true;
                    }

                    // Opzione non valida
                    default -> {
                        _W("\nOpzione non valida.");
                    }

                }
            } catch (ElementoNonTrovatoException e) {
                _W("");
                _R("!", 60);
                _Rn("!", 3);
                _W(" Errore \"404\": " + e.getMessage() + " ");
                _R("!", 3);
                _R("!", 60);
                _W("");
            } catch (ErroreGenericoException e) {
                _W("");
                _R("!", 60);
                _Rn("!", 3);
                _W(" Errore GENERICO: " + e.getMessage() + " ");
                _R("!", 3);
                _R("!", 60);
                _W("");
            }

        }

        scanner.close();
    }

}
