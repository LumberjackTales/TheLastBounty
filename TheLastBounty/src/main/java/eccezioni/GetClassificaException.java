package eccezioni;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class GetClassificaException extends Exception {

    /**
     *
     * @return
     */
    @Override
    public String getMessage() {
        return "Errore nella richiesta della classifica al Server\no Server al momento non disponibile";
    }

}
