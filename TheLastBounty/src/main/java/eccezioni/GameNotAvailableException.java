package eccezioni;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class GameNotAvailableException extends Exception {

    /**
     *
     * @return
     */
    @Override
    public String getMessage() {
        return "Errore nell'inizializzazione del gioco";
    }

}
