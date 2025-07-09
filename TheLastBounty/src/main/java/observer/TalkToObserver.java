package observer;

import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import rest.QuizAPI;
import comandi.CommandType;
import giocatore.Dialogo;
import giocatore.Inventario;
import giocatore.Item;

public class TalkToObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.TALK_TO
                && parserOutput.getParams() != null) {
            msg = "Non puoi parlare con un muro, prova a parlare con qualcuno di reale! Usa il comando 'Parla' senza mettere nulla dopo";
        } else if (parserOutput.getCommand().getType() == CommandType.TALK_TO) {
            Dialogo dialogo = description.getDialoghi().stream()
                    .filter(d -> description.getCurrentCasella().getId() == d.getIdCasella())
                    .findAny()
                    .orElse(null);
            if (dialogo != null) {
                if (description.getCurrentCasella().getId() == 410) {
                    dialogo = QuizAPI.getQuiz(dialogo);
                }
                msg = dialogo.getDialogo();
            } else {
                msg = "Non c'è nessuno con cui parlare qui, forse dovresti provare a tornare a casa o a cercare qualcuno di reale!";
            }
        } else if (description.getLastCommand().getType() == CommandType.TALK_TO
                && parserOutput.getCommand().getType() == CommandType.NULL_COMMAND) {
            Dialogo dialogo = description.getDialoghi().stream()
                    .filter(d -> description.getCurrentCasella().getId() == d.getIdCasella())
                    .findAny()
                    .orElse(null);
            if (dialogo != null) {
                if (dialogo.valutaRisposta((String) parserOutput.getParams()) && !dialogo.getRisposta().isBlank()) {
                    msg = dialogo.getMessaggioRispCorretta();
                    switch (description.getCurrentCasella().getId()) {
                        case 903 -> {
                            Item moneta = new Item("moneta d'oro");
                            Inventario inv = description.getInventario();
                            if (inv.contains(moneta)) {
                                inv.remove(moneta);
                                dialogo.changeDialogo();
                            } else {
                                msg = "\"Ma non hai monete vigliacco! Mi hai mentito!\" (non hai monete d'oro, prova a tornare a casa)";
                            }
                        }

                        case 908 -> {
                            dialogo.changeDialogo();
                            description.getCurrentCasella().getNorth().setEnterable(true);
                        }

                        case 110 -> {
                            Item moneta = new Item("moneta d'oro");
                            Inventario inv = description.getInventario();
                            if (inv.contains(moneta)) {
                                inv.remove(moneta);
                                dialogo.changeDialogo();
                                msg += "\nHai speso 1 Moneta d'oro per ripare il tuo Liuto!";
                            } else {
                                msg = "\"Non hai abbastanza monete ragazzo, sarà per la prossima volta\" (non hai abbastanza monete d'oro per riparare il Liuto, prova a tornare a casa)";
                            }
                        }

                        case 112 -> {
                            Item moneta = new Item("moneta d'oro");
                            Inventario inv = description.getInventario();
                            if (inv.contains(moneta)) {
                                dialogo.changeDialogo();
                                inv.remove(moneta);
                                Item item = description.getCurrentCasella().getOggetti()
                                        .stream()
                                        .filter(i -> i.getName().equals("coltello"))
                                        .findFirst()
                                        .orElse(null);

                                item.setVisible(true);
                                description.getCurrentCasella().clearOggetto(item);
                                inv.addOggetto(item, 1);
                                msg += "\nHai speso 1 Moneta d'oro per un coltello! Il Coltello è stato aggiunto al tuo inventario";
                            } else {
                                msg = "\"Non hai abbastanza soldi ragazzo, sarà per la prossima volta\" (non hai abbastanza monete d'oro per comprare il coltello, prova a tornare a casa)";
                            }
                        }

                    }
                    description.setLastCommand(parserOutput.getCommand());
                } else {
                    msg = dialogo.getMessaggioRispErrata();
                }
            }
        }
        return msg;
    }

}
