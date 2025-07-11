package observer;

import javax.swing.ImageIcon;

import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import comandi.CommandType;

public class HelpObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.HELP) {
            String imagePath = "src/main/resources/resource/img/help.png";
            msg = """
                    Comandi:
                        - Aiuto/Help: mostra tutta la lista dei comandi che puoi effettuare;
                        - Osserva: mostra una descrizione del luogo in cui ti trovi, se non hai già visistato la stanza, altrimenti la descrizione mostrata sarà uguale ad essa;
                        - Prendi/Raccogli [nome oggetto]: prende un oggetto che si trova nella stanza, occhio peroò che non tutti gli oggetti sono prendibili;
                        - Usa/Utilizza <oggetto>: utilizza un oggetto in una stanza;
                        - Parla: parla con il personaggio presente nella stanza, alcune volte potrebbe essere necessario avere un oggetto specifico per poter parlare con lui;;
                        - Nord/N/North/avanti : ti muovi verso Nord;
                        - Sud/S/South/indietro : ti muovi verso Sud;
                        - Est/E/East/destra : ti muovi verso Est;
                        - Ovest/O/West/W/sinistra : ti muovi verso Ovest;
                        - Muori: non ti condiglio di farlo, ma se vuoi puoi farlo per vedere come funziona la schermata di morte;
                        - Salva [nomefile]: salva i dati attuali su un file (specificare il nome del file, l'estensione verrà aggiunta automaticamente);
                    """;
            parserOutput.getInterfacciaGioco().changeImageViewer(new ImageIcon(imagePath));
        }

        return msg;
    }

}
