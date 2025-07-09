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
                if (description.getCurrentCasella().getId() == 130) {
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
                        case 113 -> {
                            Item tomoe = new Item("tomoe");
                            Inventario inv = description.getInventario();
                            if (inv.contains(tomoe)) {                              
                                dialogo.changeDialogo();
                            } else {
                                dialogo.getDialogo();
                            }
                        }

                        case 333 -> {
                            Item gemma = new Item("gemma");
                            Inventario inv = description.getInventario();
                            if (inv.contains(gemma)) {                              
                                dialogo.changeDialogo();
                                msg += "Complimenti, sei riuscito a svegliare Vangrath, misà che sei bello che spacciato!";
                            } else if (description.getLastCommand().getType() == CommandType.USE){
                                msg += "Questo paletto non ha sortito alvun effetto, misà che sei spacciato!"; 
                                dialogo.changeDialogo();
                            }else{
                                dialogo.getDialogo();
                            }
                        }

                        case 338 -> {
                            dialogo.getDialogo();
                        }

                        case 344 ->{
                            dialogo.getDialogo();
                        }

                        case 356 -> {
                            dialogo.getDialogo();
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
