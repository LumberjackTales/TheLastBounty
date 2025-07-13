package giocatore;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.GestioneDB;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class Dialogo implements Serializable {
    private final int idCasella;
    private String dialogo;
    private String risposta;
    private String messaggioRispCorretta;
    private String messaggioRispErrata;

    /**
     * costruttore della classe Dialogo
     * @param idCasella
     */
    public Dialogo(int idCasella) {
        this.idCasella = idCasella;
        this.dialogo = "";
        this.risposta = "";
        this.messaggioRispCorretta = "";
        this.messaggioRispErrata = "";
    }

    /**
     * imposta le informazioni del dialogo
     * @param dialogo
     * @param risposta
     * @param messaggioRispCorretta
     * @param messaggioRispErrata
     */
    public void setInfoDialogo(String dialogo, String risposta, String messaggioRispCorretta,
            String messaggioRispErrata) {
        this.dialogo = dialogo;
        this.risposta = risposta;
        this.messaggioRispCorretta = messaggioRispCorretta;
        this.messaggioRispErrata = messaggioRispErrata;
    }

    /**
     * restituisce il testo del dialogo
     * @return
     */
    public String getDialogo() {
        return dialogo;
    }

    /**
     * valuta la risposta inserita dall'utente e
     * confronta la risposta inserita con quella del dialogo
     * @param risposta
     * @return
     */
    public boolean valutaRisposta(String risposta) {
        return this.risposta.toLowerCase().replaceAll(" ", "").equals(risposta.toLowerCase().replaceAll(" ", ""));
    }

    /**
     * restituisce l'id della casella a cui è associato il dialogo
     * @return
     */
    public int getIdCasella() {
        return idCasella;
    }

    /**
     * restituisce il messaggio di risposta corretta 
     * @return
     */
    public String getMessaggioRispCorretta() {
        return messaggioRispCorretta;
    }

    /**
     * restituisce il messaggio di risposta errata
     * 
     * @return
     */
    public String getMessaggioRispErrata() {
        return messaggioRispErrata;
    }

    /**
     * imposta la risposta del dialogo
     * @param risposta
     */
    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    /**
     * restituisce la risposta del dialogo
     * @return
     */
    public String getRisposta() {
        return risposta;
    }

    /**
     * imposta il testo del dialogo 
     * @param testo
     */
    public void setTestoDialogo(String testo) {
        dialogo = testo;
    }

    /**
     * cambia il dialogo nel database
     */
    public void changeDialogo() {
        try {
            GestioneDB.getInstance().changeDialogo(Dialogo.this);
        } catch (Exception e) {
            Logger.getLogger(Dialogo.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}