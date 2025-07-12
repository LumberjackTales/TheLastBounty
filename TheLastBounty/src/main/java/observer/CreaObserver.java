package observer;

import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import comandi.CommandType;
import giocatore.Inventario;
import giocatore.Item;

public class CreaObserver implements GameObserver{
    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.CREA) {
            Object args = parserOutput.getParams();
            if (args == null) {
                msg = "Collega il tuo cervello, non hai specificato cosa vuoi creare! Usa il comando 'Crea' seguito dal nome dell'oggetto.";
                return msg;
            }
            String nameOBJ = (String) args;
            Item itemToCreate = description.getInventario().getOggetti().stream()
                    .filter(i -> i.getName().equalsIgnoreCase(nameOBJ) || i.getAlias().contains(nameOBJ))
                    .findFirst()
                    .orElse(null);
            
           
                switch (description.getCurrentCasella().getId()) {
                    case 104 -> {
                        if (itemToCreate != null) {
                            msg = "Hai già creato l'oggetto " + nameOBJ + ".";
                        } else {
                            Inventario inv = description.getInventario();
                            
                            Item creato = description.getCurrentCasella().getOggetti().stream()
                                .filter(i -> i.getName().equalsIgnoreCase(nameOBJ) || i.getAlias().contains(nameOBJ))
                                .findFirst()
                                .orElse(null);
                            if(inv.contains("legno di quercia bianca")) {
                                msg = "Vuoi che fabbrichi un paletto usando questo strano legno?? \n Va bene,se ti può essere utile per proseguire la tua avventura, lo farò. \n \n";
                                msg += "Il falegname ha creato per te un Paletto di Quercia Bianca!";
                                description.getInventario().remove(inv.getOggetti().stream()
                                    .filter(i -> i.getName().equalsIgnoreCase("legno di quercia bianca"))
                                    .findFirst()
                                    .orElse(null));
                                description.getInventario().addOggetto(creato, 1);
                            } else {
                                msg = "Non hai gli oggetti necessari per creare un " + nameOBJ + ".";
                            }
                        }
                    }
                    default -> {
                        msg = "Non hai le conoscenze necessarie per creare un oggetto in questa casella.";
                    }
               }
            
        }
        return msg;
    }
}
