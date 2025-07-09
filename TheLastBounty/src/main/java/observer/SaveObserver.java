package observer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import comandi.CommandType;

public class SaveObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.SAVE) {
            Object arg = parserOutput.getParams();
            if (arg == null) {
                msg = "Collega il tuo cervello, non hai specificato il nome del file di salvataggio! Usa il comando 'Salva' seguito dal nome del file.";
                return msg;
            }
            String nameFile = (String) arg;
            try {
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(System.getProperty("user.dir") + File.separator + "src/main/resources/resource/salvataggi"
                                + File.separator + nameFile + ".dat"));
                description.getChrono().stop();
                out.writeObject(description);
                description.getChrono().startAgain(description.getChrono().getElapsedTime());
                out.close();
                msg = "Salvataggio effettuato in " + System.getProperty("user.dir") + File.separator + "resource"
                        + File.separator + "salvataggi" + File.separator + nameFile + ".dat";
            } catch (IOException e) {
                msg = "[ERRORE] Salvataggio del gioco non riuscito!";
            }
        }
        return msg;
    }
}
