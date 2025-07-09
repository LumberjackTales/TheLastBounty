package thelastbounty;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import classifica.Server;
import grafica.InterfacciaIniziale;

public class TheLastBounty {
    public static void main(String[] args) {
        try {
            InterfacciaIniziale interfacciaInizio = new InterfacciaIniziale();
            interfacciaInizio.setVisible(true);
            Server server = new Server();
            boolean running = false;
            for (int i = 0; i < 5 && !running; i++) {
                try {
                    server.start();
                    running = true;
                } catch (Exception e) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TheLastBounty.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (IOException e) {
            Logger.getLogger(TheLastBounty.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

