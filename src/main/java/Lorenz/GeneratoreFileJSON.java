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

        for (int i = 0; i < 250; i++) {
            int tipo = random.nextInt(2);
            if (tipo == 0)
                elementi.add(generaLibroCasuale());
            else
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
                "Il canto della fenice dorata",
                "Le terre del crepuscolo",
                "La torre degli enigmi",
                "Il custode dei ricordi",
                "Le ombre del passato",
                "L'eco della valle segreta",
                "Il risveglio del drago",
                "L'isola senza tempo",
                "La maledizione del re oscuro",
                "Il segreto della luna argentata",
                "Le cronache della città perduta",
                "Il richiamo delle sirene",
                "La profezia del mago",
                "Il guardiano delle anime",
                "Il destino dei cavalieri",
                "La spada della verità",
                "Il labirinto delle illusioni",
                "Le cronache del vento del nord",
                "Il viaggiatore delle stelle",
                "La rinascita del mondo antico",
                "La leggenda del re di ghiaccio",
                "Il mistero delle caverne dorate",
                "La guerra degli dei",
                "Il cuore del vulcano",
                "Le memorie dell'infinito",
                "Il portale degli universi",
                "Il ritorno del lupo bianco",
                "La saga delle terre lontane",
                "L'ultimo canto del cigno",
                "Il libro degli incantesimi",
                "La chiave del tempo",
                "Il regno dei sogni",
                "L'illusione della realtà",
                "Il diario del viandante",
                "Le stelle del destino",
                "Il crepuscolo degli eroi",
                "La danza delle fiamme",
                "L'arte della guerra dimenticata",
                "Il segreto delle acque profonde",
                "Il cavaliere della luna piena",
                "Il risveglio delle antiche leggende",
                "La strada verso l'eternità"
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
                "Visioni del Domani",
                "Energia e Sostenibilità",
                "La Rivista dell'Astronomia",
                "Scoperte Archeologiche",
                "Nuovi Orizzonti Medici",
                "Tecnologie Verdi",
                "Esplorazione dello Spazio",
                "La Vita Marina",
                "Stili di Vita e Salute",
                "Scienza dei Materiali",
                "Innovazione in Agricoltura",
                "Moda e Design",
                "Architettura e Innovazione",
                "Fotografia Contemporanea",
                "Musica e Tendenze",
                "Il Mondo degli Animali",
                "Letteratura e Critica",
                "Linguaggi del Cinema",
                "Misteri della Scienza",
                "Tecnologia dell'Informazione",
                "Clima e Ambiente",
                "Psicologia Moderna",
                "Educazione e Formazione",
                "Economia Globale",
                "Filosofia e Scienza",
                "Energia Rinnovabile",
                "Geologia e Paesaggi",
                "Tecniche di Comunicazione",
                "Biotecnologie Avanzate",
                "Il Futuro della Robotica",
                "Turismo Sostenibile",
                "Cucina Internazionale",
                "Ecosistemi in Evoluzione",
                "Linguistica Applicata",
                "Progresso Medico",
                "Innovazioni Digitali",
                "Conservazione della Natura",
                "Analisi Sociologica",
                "Il Mondo della Robotica",
                "Nuove Frontiere della Fisica",
                "Chimica e Innovazione",
                "Energia del Futuro"
        };
        return titoli[random.nextInt(titoli.length)];
    }

    private static String generaNomeAutoreCasuale() {
        String[] nomi = {
                "Mario Rossi", "Luigi Bianchi", "Carla Verdi", "Giulia Neri", "Paolo Fontana", "Anna Moretti",
                "Francesco De Luca", "Silvia Conti", "Marco Turchi", "Elena Ferri", "Luca Bruni", "Chiara Galli",
                "Alessandro Greco", "Federica Pini", "Giorgio Lombardi", "Martina Sala", "Riccardo Barbieri",
                "Valeria Vitale"
        };
        return nomi[random.nextInt(nomi.length)];
    }

    private static String generaGenereCasuale() {
        String[] generi = { "Narrativa", "Fantasy", "Giallo", "Storico", "Fantasy", "Biografia", "Romanzo" };
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
