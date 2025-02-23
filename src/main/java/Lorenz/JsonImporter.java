package Lorenz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static Lorenz.GeneratoreFileJSON.GeneraFileCasuale;

class JsonImporter {

    private String filePath;

    public JsonImporter(String filePath) {
        this.filePath = filePath;
    }

    public List<ElementoCatalogo> importaDaFile() {

        Path path = Paths.get(this.filePath);

        // Prima controlla se il file esiste, se non esiste lo genera
        if (!Files.exists(path)) {
            GeneraFileCasuale(this.filePath);
        }

        // ... e poi lo legge
        ObjectMapper mapper = new ObjectMapper();
        List<ElementoCatalogo> elementi = new ArrayList<>();
        path = Paths.get(filePath);

        try {
            String json = Files.readString(path);
            List<Map<String, Object>> rawData = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
            });

            for (Map<String, Object> data : rawData) {
                if (data.containsKey("autore")) {
                    // È un libro
                    Libro libro = new Libro(
                            (String) data.get("isbn"),
                            (String) data.get("titolo"),
                            (Integer) data.get("annoPubblicazione"),
                            (Integer) data.get("numeroPagine"),
                            (String) data.get("autore"),
                            (String) data.get("genere"));
                    elementi.add(libro);
                } else if (data.containsKey("periodicita")) {
                    // È una rivista
                    Rivista rivista = new Rivista(
                            (String) data.get("isbn"),
                            (String) data.get("titolo"),
                            (Integer) data.get("annoPubblicazione"),
                            (Integer) data.get("numeroPagine"),
                            Periodicita.valueOf((String) data.get("periodicita")));
                    elementi.add(rivista);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file JSON: " + e.getMessage());
        }

        return elementi;
    }
}