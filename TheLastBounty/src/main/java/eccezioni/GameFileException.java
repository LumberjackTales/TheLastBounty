package eccezioni;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class GameFileException extends Exception {

    /**
     *
     * @return
     */
    @Override
    public String getMessage() {
        return "File di salvataggio corrotto/non trovato";
    }

}
