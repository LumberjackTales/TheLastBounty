package observer;

import java.util.regex.Pattern;

import javax.swing.ImageIcon;

import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import comandi.CommandType;
import giocatore.Dialogo;
import giocatore.Item;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
//questo osserrvatore gestisce la raccolta degli oggetti
public class PrendiObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        String imagePath;
        if (parserOutput.getCommand().getType() == CommandType.PRENDI) {
            if (description.getCurrentCasella().getId() != 104) {
                if (description.getCurrentCasella().getId() == 333){
                    description.getCurrentCasella().setUpdated(true);
                    Dialogo dialogo = description.getDialoghi().stream()
                            .filter(d -> description.getCurrentCasella().getId() == d.getIdCasella())
                            .findAny()
                            .orElse(null);
                    dialogo.changeDialogo();
                    msg = "Ma sei scemo, grazie a questa tua mossa geniale Vangrath si è svegliato \n";
                    imagePath= "src/main/resources/resource/img/boss.png";
                    parserOutput.getInterfacciaGioco().changeImageViewer(new ImageIcon(imagePath));
                }
                Object args = parserOutput.getParams();
                if (args == null) {
                    System.out.println("Scemo, Non hai specificato l'oggetto da prendere");
                    msg = "Non hai specificato l'oggetto da prendere, usa il comando 'Prendi' seguito dal nome dell'oggetto. \n";
                    return msg;
                }
                if (args instanceof String[]) {
                    String[] items = (String[]) args;
                    if (items.length == 1){
                        String nameOBJ = items[0].toLowerCase();
                        Item pickUpItem = description.getCurrentCasella().getOggetti().stream()
                                .filter(i -> i.getName().equalsIgnoreCase(nameOBJ) || i.getAlias().contains(nameOBJ))
                                .findFirst()
                                .orElse(null);
                        
                        if (pickUpItem != null && pickUpItem.isVisible()) {
                            int quantity = description.getCurrentCasella().removeOggetto(pickUpItem);
                            msg += "Hai raccolto ";
                            msg += Pattern.compile("^.").matcher(pickUpItem.getName())
                                    .replaceFirst(m -> m.group().toUpperCase());
                            
                            description.getInventario().addOggetto(pickUpItem, quantity);
                           
                        } else {
                            msg = "Non c'è nessun oggetto con quel nome qui,secondo me sei pazzo! Prova a guardare meglio.";
                        }
                    } else {
                        msg = "Puoi prendere solo un oggetto per volta!";
                    }
                }else{
                    msg = "Non puoi raccogliere nulla, sei per caso pazzo?";
                }
            } else {
                msg = "Non hai specificato l'oggetto da prendere, usa il comando 'Prendi' seguito dal nome dell'oggetto.";
            }
        }
        return msg;
    }
}