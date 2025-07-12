package observer;

import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import rest.QuizAPI;
import comandi.CommandType;
import giocatore.Dialogo;
import giocatore.Inventario;
import giocatore.Item;

public class ParlaObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        Inventario inv = description.getInventario();
        if (parserOutput.getCommand().getType() == CommandType.PARLA
                && parserOutput.getParams() != null) {
            msg = "Non puoi parlare con un muro, prova a parlare con qualcuno di reale! Usa il comando 'Parla' senza mettere nulla dopo";
        } else if (parserOutput.getCommand().getType() == CommandType.PARLA) {
            Dialogo dialogo = description.getDialoghi().stream()
                    .filter(d -> description.getCurrentCasella().getId() == d.getIdCasella())
                    .findAny()
                    .orElse(null);
            if (dialogo != null) {
                if ((description.getCurrentCasella().getId() == 130)&& !description.getCurrentCasella().isUpdated()) {
                    dialogo = QuizAPI.getQuiz(dialogo);
                }
                msg = dialogo.getDialogo();
                if (((description.getCurrentCasella().getId() == 338)||(description.getCurrentCasella().getId() == 344)||(description.getCurrentCasella().getId() == 356))&& description.getCurrentCasella().isUpdated()) {
                    msg = "Con chi vuoi parlare che hai già ucciso la guardia, scemo! \n";
                }else if ((description.getCurrentCasella().getId() == 130)&& description.getCurrentCasella().isUpdated()){
                    msg = "Dopo quello che gli hai fatto, vuoi provare pure a parlarci? sei proprio un mostro.";
                }

                if ((description.getCurrentCasella().getId() == 104)){
                    boolean stato = description.getCurrentCasella().isUpdated();
                    if (!stato){
                        msg = dialogo.getDialogo(); 
                        description.getCurrentCasella().setUpdated(true);
                    }else{
                        if (inv.contains("legno di quercia bianca")){ 
                            msg = "Vedo che hai raccolto un tipo di legno particolare giovane hunter, sento che trabocca di potere magico, cosa vuoi che ci faccia?";
                        }else{
                            msg = "Dimmi tutto giovane hunter";
                        }
                    }
                     
                }
            } else {
                msg = "Non c'è nessuno con cui parlare qui, forse dovresti provare a tornare a casa o a cercare qualcuno di reale!";
            }
        } else if (description.getLastCommand().getType() == CommandType.PARLA
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
                            if (inv.contains(tomoe)) {                              
                                dialogo.changeDialogo();
                            } else {
                                dialogo.getDialogo();
                            }
                        }

                        case 333 -> {
                            Item gemma = new Item("gemma");
                            if (inv.contains(gemma)) {                              
                                msg += "Complimenti, sei riuscito a svegliare Vangrath, misà che sei bello che spacciato!";
                            } else if (description.getLastCommand().getType() == CommandType.USE){
                                msg += "Questo paletto non ha sortito alvun effetto, misà che sei spacciato!"; 
                                dialogo.changeDialogo();
                            }else{
                                dialogo.getDialogo();
                            }
                        }

                        case 130 -> 
                            description.getCurrentCasella().getNorth().setEnterable(true);               
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
