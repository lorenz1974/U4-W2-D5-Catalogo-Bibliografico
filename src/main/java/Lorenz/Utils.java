package Lorenz;

import java.util.Scanner;

public class Utils {
    public static Object _LI(String tipo, Scanner scanner) {
        Object risultato = null;
        boolean inputValido = false;

        while (!inputValido) {
            try {
                String input = scanner.nextLine();

                switch (tipo.toLowerCase()) {
                    case "int":
                        risultato = Integer.parseInt(input);
                        break;
                    case "double":
                        risultato = Double.parseDouble(input);
                        break;
                    case "string":
                        risultato = input;
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo non supportato: " + tipo);
                }
                inputValido = true;
            } catch (Exception e) {
                _W("Input non valido, riprova. " + e.getMessage());
            }
        }

        return risultato;
    }

    // Stampa testo
    public static void _W(String text) {
        System.out.println(text);
    }

    public static void _Wn(String text) {
        System.out.print(text);
    }

    // Ripete carattere
    public static void _R(String character, int times) {
        _Wn(character.repeat(times));
        _W("");
    }

    // Ripete carattere senza ritorno a capo
    public static void _Rn(String character, int times) {
        _Wn(character.repeat(times));
    }

    // Cancella schermo
    public static void _C() {
        for (int i = 0; i < 50; i++) {
            _W("");
        }
    }

    // Menu
    public static void _M() {
        _R("*", 50);
        _Rn("*", 3);
        _Wn(" CATALOGO BIBLIOGRAFICO ");
        _Rn(" ", 20);
        _R("*", 3);
        _R("*", 50);
        _W("0. Elenca tutti gli elementi");
        _W("1. Aggiungi elemento");
        _W("2. Ricerca per ISBN");
        _W("3. Rimuovi elemento");
        _W("4. Ricerca per anno");
        _W("5. Ricerca per autore");
        _W("6. Aggiorna elemento");
        _W("7. Stampa statistiche");
        _R("-", 15);
        _W("A. AnySearch!");
        _W("B. Setta formato tabella" + (Archivio.getFormatoTabella() ? " ON" : " OFF"));
        _R("-", 15);
        _W("Z. Esci");
        _R("-", 50);
        _W("");
    }

    // Attende la pressione di un carattere per proseguire
    @SuppressWarnings("resource")
    public static void _P() {
        _W("Premi INVIO per continuare...");
        new Scanner(System.in).nextLine();
        _W("");
    }
}
