package observer;

import javax.swing.ImageIcon;

import database.Casella;
import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import comandi.CommandType;

public class MuoviObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        Integer totalMoves = 0, maxMoves;
        CommandType cmdt = parserOutput.getCommand().getType();
        if (cmdt == CommandType.EST || cmdt == CommandType.OVEST || cmdt == CommandType.SUD
                || cmdt == CommandType.NORD) {
            System.out.println("MoveObserver spostamento verso: " + cmdt);
            Casella toMove;
            Object arg = parserOutput.getParams();

            if (arg == null) {
                maxMoves = 1;
            }else {
                try {
                    String argString = (String) arg;
                    maxMoves = Integer.valueOf(argString.replaceFirst("[x | X]", ""));
                } catch (NumberFormatException e) {
                    msg = "Il numero di caselle da muovere non è valido.";
                    return msg;
                }
            }

            while (totalMoves < maxMoves) {
                toMove = null;
                switch (cmdt) {
                    case EST -> toMove = description.getCurrentCasella().getEast();
                    case OVEST -> toMove = description.getCurrentCasella().getWest();
                    case SUD -> toMove = description.getCurrentCasella().getSouth();
                    case NORD -> toMove = description.getCurrentCasella().getNorth();
                    default -> {
                    }
                }
          
                if (toMove == null && totalMoves == 0) {
                    // Controllo sulla zona per l'errore di direzione
                    String defaultDescrizione = Integer.toString(description.getCurrentCasella().getId());
                    msg = switch (defaultDescrizione.charAt(0)) {
                        case '1' -> "Il bosco è troppo fitto per andare oltre.";
                        case '2' -> "Non puoi andare oltre le mura del Tempio.";
                        case '3' -> "Non puoi andare oltre le mura della cripta.";
                        default -> "Non puoi andare in quella direzione.";
                    };
                    totalMoves = maxMoves;
                } else if (toMove == null || !toMove.isEnterable()) {
                    if (msg.length() == 0) {
                        msg = "La porta sembra essere chiusa, prova ad esplorare meglio il tempio..\n"
                                + description.getCurrentCasella().getDescrizione();
                    }
                    if (totalMoves != 0) {
                        msg += "\nSei riuscito a muoverti verso "
                                + cmdt.toString().toLowerCase()
                                + " di solo "
                                + totalMoves + " casella rispetto alle " + maxMoves + " indicate.";
                    }
                    totalMoves = maxMoves;
                } else {
                    totalMoves++;
                    description.setCurrentCasella(toMove);
                    msg = toMove.getDescrizione();
                }
            }

            
            String imagePath;
        switch (description.getCurrentCasella().getId()) {

            case 100 ->
                imagePath = "/resource/img/Entrata_bosco.png";

            case 101 ->
                imagePath = "/resource/img/quercia_bianca.png";

            case 104 ->
                imagePath = "/resource/img/Falegname.png";

            case 107 ->
                imagePath = "/resource/img/entrata_cripta.png";

            case 108 ->
                imagePath = "/resource/img/Cartello1.png";

            case 109,112,123 ->{
                description.getChrono().addMinute(5);
                imagePath = "/resource/img/trappola.png";
            }

            case 113 ->
                imagePath = "/resource/img/Lago_Ninfa.png";

            case 114 ->
                imagePath = "/resource/img/Cartello2.png";

            case 116 ->
                imagePath = "/resource/img/Cartello3.png";

            case 118 ->
                imagePath = "/resource/img/Cripta2.png";
            
            case 119 ->
                imagePath = "/resource/img/Cartello4.png";

            case 129 ->
                imagePath = "/resource/img/Cripta_tomo.png";

            case 130 ->
                imagePath = "/resource/img/entrata_tempio.png";

            case 102, 103, 105, 106, 110, 111, 115, 117, 120, 121, 122, 124, 125, 126, 127, 128 ->
                imagePath = "/resource/img/sentiero_Bosco.png";

            case 210,211,215 ->
                imagePath = "/resource/img/Stanza_cripta.png";

            case 214 ->
                imagePath = "/resource/img/Cripta_triangolo.png";

            case 208,209,212,213 ->
                imagePath = "/resource/img/Corridoio_cripta.png";

            case 330 ->
                imagePath = "/resource/img/Stanza_Tempio.png";
            
            case 331 ->
                imagePath = "/resource/img/porta_tempio.png";

            case 332 ->{
                imagePath = "/resource/img/stanza_boss.png";
            }

            case 333 ->
                imagePath = "/resource/img/boss.png";

            case 338 ->{

                if (!description.getCurrentCasella().isUpdated()) {
                    imagePath = "/resource/img/guardia_inutile.png";
                } else {
                    imagePath = "/resource/img/Corridoio_tempio.png";
                }
                
            }

            case 344 -> {
                if (!description.getCurrentCasella().isUpdated()) {
                    imagePath = "/resource/img/guardia_stupida.png";
                } else {
                    imagePath = "/resource/img/Corridoio_tempio.png";
                }
            }
            case 356 -> {
                if (!description.getCurrentCasella().isUpdated()) {
                    imagePath = "/resource/img/guardia_chiave.png";
                } else {
                    imagePath = "/resource/img/Corridoio_tempio.png";
                }
            }

            case 348 -> 
                imagePath = "/resource/img/quadro_tobia.png";

            case 350 -> 
                imagePath = "/resource/img/quadro_sospetto.png";

            case 352 -> 
                imagePath = "/resource/img/lore_boss.png";

            case 334,335,336,337,339,340,341,342,343,345,346,347,349,351,353,354,355 ->
                imagePath = "/resource/img/Corridoio_tempio.png";
            
            default ->
                imagePath = "/resource/img/Cacciatore.png";
        }
        parserOutput.getInterfacciaGioco().changeImageViewer(new ImageIcon(imagePath));
    }
        System.out.println(description.getCurrentCasella().getId());
        return msg;
    }

}
