package observer;

import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import comandi.Command;
import comandi.CommandType;
import giocatore.Dialogo;
import giocatore.Item;

public class UsaObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.USE) {
            Object args = parserOutput.getParams();
            if (args == null) {
                msg = "Ma parliamo la stessa lingua? Usa il comando 'Usa' seguito dal nome dell'oggetto che vuoi usare.";
                return msg;
            }
            if (args instanceof String[]) {
                String[] items = (String[]) args;
                if (items.length != 1) {
                    return "Puoi usare solo un oggetto per volta.";
                }
                String nameOBJ = items[0].toLowerCase();
                Item itemToUse = description.getInventarioSet().stream()
                        .filter(i -> i.getName().toLowerCase().equals(nameOBJ) || i.getAlias().contains(nameOBJ))
                        .findFirst()
                        .orElse(null);
                if (itemToUse != null) {
                    switch (description.getCurrentCasella().getId()) {
                        case 101 ->{
                            if(nameOBJ.equals("coltellino svizzero") || nameOBJ.equals("coltellino")|| nameOBJ.equals("coltello")) {
                                msg = "Hai ottenuto un ramo di legno di quercia bianca!";
                                Item palettoItem = new Item("legno di quercia bianca");
                                description.getInventario().addOggetto(palettoItem, 1);
                            } else {
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui";
                            }
                        }

                        case 113 ->{
                            if(nameOBJ.equals("tomoe") || nameOBJ.equals("libro") || nameOBJ.equals("libro antico") || nameOBJ.equals("manoscritto")){
                                description.getCurrentCasella().setUpdated(true);
                                Dialogo dialogo = description.getDialoghi().stream()
                                    .filter(d -> description.getCurrentCasella().getId() == d.getIdCasella())
                                    .findAny()
                                    .orElse(null);
                                dialogo.changeDialogo();
                                msg = "Grazie al tomoe riesci a capire la lingua della ninfa! Ora puoi rispondere alle sue domande!";
                            } else {
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui";
                            }
                        }
                        case 130 -> {
                            if((nameOBJ.equals("paletto") || nameOBJ.equals("paletto di legno") || nameOBJ.equals("legnetto") || nameOBJ.equals("paletto legno"))){
                                description.getCurrentCasella().getNorth().setEnterable(true);
                                description.getInventario().remove(itemToUse);
                                msg = "Hai ucciso il guardiano del tempio, hai fatto bene, faceva troppe domande!";
                            } else if ((nameOBJ.equals("paletto magico") || nameOBJ.equals("arma finale") || nameOBJ.equals("paletto di quercia bianca") || nameOBJ.equals("paletto bianco") || nameOBJ.equals("paletto di quercia"))&& (description.getInventario().contains("Paletto di quercia bianca"))){
                                description.getInventario().remove(itemToUse);
                                msg = "Hai sprecato la tua unica possibilità di vittoria, complimenti!";
                            }else{
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui";
                            }
                        }

                        case 344 -> {
                            if((nameOBJ.equals("paletto") || nameOBJ.equals("paletto di legno") || nameOBJ.equals("legnetto") || nameOBJ.equals("paletto legno"))){
                                description.getCurrentCasella().setUpdated(true);
                                description.getInventario().remove(itemToUse);
                                msg = "Hai ucciso una delle guardie del tempio, complimenti giovante hunter!";
                            } else if ((nameOBJ.equals("paletto magico") || nameOBJ.equals("arma finale") || nameOBJ.equals("paletto di quercia bianca") || nameOBJ.equals("paletto bianco") || nameOBJ.equals("paletto di quercia"))&& (description.getInventario().contains("Paletto di quercia bianca"))){
                                description.getInventario().remove(itemToUse);
                                msg = "Hai sprecato la tua unica possibilità di vittoria, complimenti!";
                            }else{
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui";
                            }
                        }

                        case 356 -> {
                            if((nameOBJ.equals("paletto") || nameOBJ.equals("paletto di legno") || nameOBJ.equals("legnetto") || nameOBJ.equals("paletto legno"))){
                                description.getCurrentCasella().setUpdated(true);
                                description.getInventario().remove(itemToUse);
                                msg = "Hai ucciso una delle guardie del tempio, complimenti giovante hunter!\nSembra anche che abbia lasciato cadere una chiave, forse può esserti utile.";
                        

                                Item chiave = description.getCurrentCasella().getOggetti().stream()
                                .filter(i -> i.getName().equalsIgnoreCase("chiave finale"))
                                .findFirst()
                                .orElse(null);

                                chiave.setVisible(true);
                                chiave.setPickable(true);
                            } else if ((nameOBJ.equals("paletto magico") || nameOBJ.equals("arma finale") || nameOBJ.equals("paletto di quercia bianca") || nameOBJ.equals("paletto bianco") || nameOBJ.equals("paletto di quercia"))&& (description.getInventario().contains("Paletto di quercia bianca"))){
                                description.getInventario().remove(itemToUse);
                                msg = "Hai sprecato la tua unica possibilità di vittoria, complimenti!";
                            }else{
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui";
                            }
                        }

                        case 338 -> {
                            if((nameOBJ.equals("paletto") || nameOBJ.equals("paletto di legno") || nameOBJ.equals("legnetto") || nameOBJ.equals("paletto legno"))){
                                description.getCurrentCasella().setUpdated(true);
                                description.getInventario().remove(itemToUse);
                                msg = "Hai ucciso una delle guardie del tempio, complimenti giovante hunter!";
                            } else if ((nameOBJ.equals("paletto magico") || nameOBJ.equals("arma finale") || nameOBJ.equals("paletto di quercia bianca") || nameOBJ.equals("paletto bianco") || nameOBJ.equals("paletto di quercia"))&& (description.getInventario().contains("Paletto di quercia bianca"))){
                                description.getInventario().remove(itemToUse);
                                msg = "Hai sprecato la tua unica possibilità di vittoria, complimenti!";
                            }else{
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui";
                            }
                        }

                        case 331 -> {
                            if (nameOBJ.equals("chiave finale") || nameOBJ.equals("chiave")) {
                                description.getCurrentCasella().setUpdated(true);
                                description.getCurrentCasella().getNorth().setEnterable(true);
                                msg = "La porta d'avanti a te è aperta! Puoi proseguire!";
                            } else {
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui";
                            }
                        }

                       
                        case 333 -> {
                            if ((nameOBJ.equals("paletto magico") || nameOBJ.equals("arma finale") || nameOBJ.equals("paletto di quercia bianca") || nameOBJ.equals("paletto bianco") || nameOBJ.equals("paletto di quercia"))&& (description.getInventario().contains("Paletto di quercia bianca"))) {
                                description.getInventario().remove(itemToUse);
                                parserOutput.setCommand(new Command(CommandType.THE_END, null));
                            } else if((nameOBJ.equals("paletto") || nameOBJ.equals("paletto di legno") || nameOBJ.equals("legnetto") || nameOBJ.equals("paletto legno"))) {
                                description.getInventario().remove(itemToUse);
                                msg = "Il paletto di legno non è abbastanza potente per sconfiggere il mostro, devi trovare un'arma più potente!, ma misà che sei bello che morto!";
                                parserOutput.setCommand(new Command(CommandType.MORTE, null));
                            }else{
                                msg = "Non puoi usare l'oggetto " + nameOBJ + " qui, sbrigati!";
                            }
                        }
                    }
                } else {
                    msg = "L'oggetto " + nameOBJ + " non è presente nel tuo inventario, o non esiste.";
                }
            } else {
                msg = "Ma parliamo la stessa lingua? Usa il comando 'Usa' seguito dal nome dell'oggetto che vuoi usare.";
            }
        }
        return msg;
    }

}
