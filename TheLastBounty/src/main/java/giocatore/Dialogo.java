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
     *
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
     *
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
     *
     * @return
     */
    public String getDialogo() {
        return dialogo;
    }

    /**
     *
     * @param risposta
     * @return
     */
    public boolean valutaRisposta(String risposta) {
        return this.risposta.toLowerCase().replaceAll(" ", "").equals(risposta.toLowerCase().replaceAll(" ", ""));
    }

    /**
     *
     * @return
     */
    public int getIdCasella() {
        return idCasella;
    }

    /**
     *
     * @return
     */
    public String getMessaggioRispCorretta() {
        return messaggioRispCorretta;
    }

    /**
     *
     * @return
     */
    public String getMessaggioRispErrata() {
        return messaggioRispErrata;
    }

    /**
     *
     * @param risposta
     */
    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    /**
     *
     * @return
     */
    public String getRisposta() {
        return risposta;
    }

    /**
     *
     * @param testo
     */
    public void setTestoDialogo(String testo) {
        dialogo = testo;
    }

    /**
     *
     */
    public void changeDialogo() {
        try {
            GestioneDB.getInstance().changeDialogo(Dialogo.this);
        } catch (Exception e) {
            Logger.getLogger(Dialogo.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}