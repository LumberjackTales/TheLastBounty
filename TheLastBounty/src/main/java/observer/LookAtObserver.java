package observer;

import java.util.HashSet;
import java.util.Set;

import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import comandi.CommandType;
import giocatore.Item;

public class LookAtObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.LOOK_AT) {
            System.out.println("LookAtObserver osservazione in corso...");
            Object args = parserOutput.getParams();
            if (args == null){
                msg = description.getCurrentCasella().getDescrizione();
            } else if (args instanceof String[]){
                String[] items = (String[]) args;
                if (items.length != 1){
                    msg = "Mmh... non so cosa guardare, specifica un solo oggetto da osservare.";
                } else {
                    String nameOBJ = items[0].toLowerCase();
                    Set<Item> allItems = new HashSet<>();
                    allItems.addAll(description.getCurrentCasella().getOggetti());
                    allItems.addAll(description.getInventario().getOggetti());

                    Item item = allItems.stream()
                    .filter(i -> i.getName().equalsIgnoreCase(nameOBJ) || i.getAlias().contains(nameOBJ))
                    .findFirst()
                    .orElse(null);

                    if (item != null) {
                        msg = item.getDescription();
                    } else {
                        msg = nameOBJ + " non è un oggetto che puoi osservare qui. Secondo me sei pazzo!";
                    }
                }
            }
        }
        return msg;
    }

}
