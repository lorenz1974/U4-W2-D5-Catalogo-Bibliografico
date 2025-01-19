package Lorenz;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Lorenz.Utils._W;

public class GeneratoreFileJSON {

    private static final Random random = new Random();

    public static void GeneraFileCasuale(String filePath) {
        List<ElementoCatalogo> elementi = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            elementi.add(generaLibroCasuale());
            elementi.add(generaRivistaCasuale());
        }

        scriviSuFileJSON(elementi, filePath);
    }

    private static Libro generaLibroCasuale() {
        String isbn = generaISBN();
        String titolo = generaTitoloLibroCasuale();
        int anno = random.nextInt(2020 - 1900) + 1900;
        int pagine = random.nextInt(900) + 100;
        String autore = generaNomeAutoreCasuale();
        String genere = generaGenereCasuale();

        return new Libro(isbn, titolo, anno, pagine, autore, genere);
    }

    private static Rivista generaRivistaCasuale() {
        String isbn = generaISBN();
        String titolo = generaTitoloRivistaCasuale();
        int anno = random.nextInt(2020 - 1900) + 1900;
        int pagine = random.nextInt(50) + 20;
        Periodicita periodicita = Periodicita.values()[random.nextInt(Periodicita.values().length)];

        return new Rivista(isbn, titolo, anno, pagine, periodicita);
    }

    private static String generaISBN() {
        return "978-" + (random.nextInt(9000000) + 1000000);
    }

    private static String generaTitoloLibroCasuale() {
        String[] titoli = {
                "Il mistero della foresta incantata",
                "Le cronache del sole nascente",
                "La biblioteca dei segreti perduti",
                "Il viaggio dell'eroe dimenticato",
                "L'ombra oltre il confine",
                "La ricerca della verità eterna",
                "I sogni di un tempo lontano",
                "L'arte di cambiare il destino",
                "La danza delle stelle spezzate",
                "Il canto della fenice dorata"
        };
        return titoli[random.nextInt(titoli.length)];
    }

    private static String generaTitoloRivistaCasuale() {
        String[] titoli = {
                "Scienza e Innovazione Oggi",
                "L'Esploratore della Natura",
                "Tecnologia e Futuro",
                "Mondi Antichi e Moderni",
                "Viaggi e Avventure",
                "Arte e Cultura Contemporanea",
                "Il Giornale della Conoscenza",
                "Storia e Miti",
                "Il Pensiero Scientifico",
                "Visioni del Domani"
        };
        return titoli[random.nextInt(titoli.length)];
    }

    private static String generaNomeAutoreCasuale() {
        String[] nomi = {
                "Mario Rossi", "Luigi Bianchi", "Carla Verdi", "Giulia Neri", "Paolo Fontana", "Anna Moretti",
                "Francesco De Luca", "Silvia Conti"
        };
        return nomi[random.nextInt(nomi.length)];
    }

    private static String generaGenereCasuale() {
        String[] generi = { "Narrativa", "Fantasy", "Giallo", "Storico", "Fantascienza", "Biografia", "Romanzo Rosa" };
        return generi[random.nextInt(generi.length)];
    }

    static void scriviSuFileJSON(List<ElementoCatalogo> elementi, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        Path path = Paths.get(filePath);

        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(elementi);
            Files.writeString(path, json);
            _W("File JSON salvato correttamente: " + filePath);

        } catch (IOException e) {
            System.err.println("Errore nella scrittura del file JSON: " + e.getMessage());
        }
    }
}
