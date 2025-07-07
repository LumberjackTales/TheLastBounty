package componentiaggiuntivi;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StampaTesto implements Runnable {

    private String[] paroleDaStampare;
    private int parolaCorrente;
    private int ATTESA = 50;
    private final JTextArea textAreaOutput;
    private final JTextField textFieldInput;
    private final JButton skip;
    private Thread thread;
    private static StampaTesto instance = null;
    private boolean isFinal;

    /**
     * Costruttore privato per implementare il pattern Singleton.
     * 
     * @param textFieldInput Campo di testo per l'input
     * @param textAreaOutput Area di testo per l'output
     * @param skip Pulsante per saltare l'animazione
     */
    private StampaTesto(JTextField textFieldInput, JTextArea textAreaOutput, JButton skip) {
        this.textAreaOutput = textAreaOutput;
        this.textFieldInput = textFieldInput;
        this.skip = skip;
        this.isFinal = false;
    }

    /**
     * Metodo factory per ottenere l'istanza della classe.
     * 
     * @param textFieldInput Campo di testo per l'input
     * @param textAreaOutput Area di testo per l'output
     * @param skip Pulsante per saltare l'animazione
     * @return Istanza della classe StampaTesto
     */
    public static StampaTesto getInstance(JTextField textFieldInput, JTextArea textAreaOutput, JButton skip) {
        instance = new StampaTesto(textFieldInput, textAreaOutput, skip);
        return instance;
    }

    /**
     * Restituisce l'istanza esistente della classe.
     * 
     * @return Istanza della classe StampaTesto
     */
    public static StampaTesto getInstance() {
        return instance;
    }

    /**
     * Avvia la stampa del testo specificato.
     * 
     * @param testo Testo da stampare
     */
    public void stampa(String testo) {
        this.paroleDaStampare = testo.split(" ");
        if (thread != null && thread.isAlive()) {
            interrupt();
        }
        thread = new Thread(this);
        textFieldInput.setEnabled(false);
        thread.start();
    }

    /**
     * Avvia la stampa finale del testo specificato.
     * 
     * @param testo Testo da stampare come finale
     */
    public void stampaFinale(String testo) {
        isFinal = true;
        stampa(testo);
    }

    /**
     * Verifica se il thread di stampa è attivo.
     * 
     * @return true se il thread è attivo, false altrimenti
     */
    public boolean isAlive() {
        return thread != null && thread.isAlive();
    }

    /**
     * Interrompe il thread di stampa corrente.
     */
    public void interrupt() {
        thread.interrupt();
    }

    /**
     * Stampa le parole rimanenti senza attesa.
     */
    public void printRemaining() {
        for (; parolaCorrente < paroleDaStampare.length; parolaCorrente++) {
            if (paroleDaStampare[parolaCorrente].equals("\\n"))
                textAreaOutput.append("\n");
            else
                textAreaOutput.append(paroleDaStampare[parolaCorrente] + " ");
        }
    }

    /**
     * Imposta il tempo di attesa tra la stampa di una parola e l'altra.
     * 
     * @param attesa Tempo di attesa in millisecondi
     */
    public void setAttesa(int attesa) {
        ATTESA = attesa;
    }

    /**
     * Metodo che gestisce l'esecuzione del thread e la stampa delle parole.
     * Le parole vengono stampate una alla volta con un intervallo di tempo specificato.
     * Se viene interrotto, stampa tutte le parole rimanenti.
     * Al termine della stampa, riabilita l'input dell'utente se non è una stampa finale.
     */
    @Override
    public void run() {
        textFieldInput.setEnabled(false);
        skip.setEnabled(true);

        for (parolaCorrente = 0; parolaCorrente < paroleDaStampare.length; parolaCorrente++) {
            try {
                Thread.sleep(ATTESA);
                if (paroleDaStampare[parolaCorrente].equals("\\n"))
                    textAreaOutput.append("\n");
                else
                    textAreaOutput.append(paroleDaStampare[parolaCorrente] + " ");

                textAreaOutput.setCaretPosition(textAreaOutput.getDocument().getLength());
            } catch (InterruptedException ex) {
                printRemaining();
            }
        }
        if (!isFinal) {
            textAreaOutput.append("\n\nCome vuoi procedere?\n ➤ ");
            textFieldInput.setEnabled(true);
            skip.setEnabled(false);
            textFieldInput.requestFocus();
        } else {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(StampaTesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
