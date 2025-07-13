package eccezioni;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class SendRecordException extends Exception {

    /**
     *
     * @return
     */
    @Override
    public String getMessage() {
        return "Errore durante l'invio del record al Server\no Server non disponibile";
    }

}
