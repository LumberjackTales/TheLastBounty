package observer;

import java.util.regex.Pattern;

import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import comandi.CommandType;
import giocatore.Item;

public class PickUpObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.PICK_UP) {
            Object args = parserOutput.getParams();
            if (args == null) {
                System.out.println("Scemo, Non hai specificato l'oggetto da prendere");
                msg = "Non hai specificato l'oggetto da prendere, usa il comando 'Prendi' seguito dal nome dell'oggetto.";
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
                        msg = "Hai raccolto ";
                        msg += Pattern.compile("^.").matcher(pickUpItem.getName())
                                .replaceFirst(m -> m.group().toUpperCase());
                        
                        description.getInventario().addOggetto(pickUpItem, quantity);

                        if (description.getInventario().contains("Legno quercia bianca", "Coltellino svizzero")) {
                            description.getCaselle().stream()
                                    .filter(c -> c.getId() == 104)
                                    .forEach(c -> {
                                        c.setUpdated(true);
                                    });
                        }
                    } else {
                        msg = "Non c'è nessun oggetto con quel nome qui,secondo me sei pazzo! Prova a guardare meglio.";
                    }
                } else {
                    msg = "Puoi prendere solo un oggetto per volta!";
                }
            } else {
                msg = "Non hai specificato l'oggetto da prendere, usa il comando 'Prendi' seguito dal nome dell'oggetto.";
            }
        }
        return msg;
    }
}