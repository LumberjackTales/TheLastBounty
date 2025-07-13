package componentiaggiuntivi;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StampaTesto implements Runnable {

    private String[] paroleDaStampare;
    private int parolaCorrente;
    private static int ATTESA = 50;
    private final JTextArea textAreaOutput;
    private final JTextField textFieldInput;
    private final JButton skip;
    private Thread thread;
    private static StampaTesto instance = null;
    private boolean isFinal;

    private StampaTesto(JTextField textFieldInput, JTextArea textAreaOutput, JButton skip) {
        this.textAreaOutput = textAreaOutput;
        this.textFieldInput = textFieldInput;
        this.skip = skip;
        this.isFinal = false;
    }


    public static StampaTesto getInstance(JTextField textFieldInput, JTextArea textAreaOutput, JButton skip) {
        instance = new StampaTesto(textFieldInput, textAreaOutput, skip);
        return instance;
    }

   
    public static StampaTesto getInstance() {
        return instance;
    }

   
    public void stampa(String testo) {
        this.paroleDaStampare = testo.split(" ");
        if (thread != null && thread.isAlive()) {
            interrupt();
        }
        thread = new Thread(this);
        textFieldInput.setEnabled(false);
        thread.start();
    }

    public void join(Runnable r){
        new Thread(() -> {
            if (thread != null && thread.isAlive()) {
                try {
                    thread.join();
                    textFieldInput.setEnabled(false);
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StampaTesto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            r.run();
        }).start();
        
    }

    
    public void stampaFinale(String testo) {
        isFinal = true;
        stampa(testo);
    }

    
    public boolean isAlive() {
        return thread != null && thread.isAlive();
    }


    public void interrupt() {
        thread.interrupt();
    }

    
    public void printRemaining() {
        for (; parolaCorrente < paroleDaStampare.length; parolaCorrente++) {
            if (paroleDaStampare[parolaCorrente].equals("\\n"))
                textAreaOutput.append("\n");
            else
                textAreaOutput.append(paroleDaStampare[parolaCorrente] + " ");
        }
    }

    
    public static void setAttesa(int attesa) {
        ATTESA = attesa;
    }

  
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
