package observer;

import grafica.InterfacciaMorte;
import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import comandi.CommandType;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class MorteObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.MORTE) {
            parserOutput.getInterfacciaGioco().getMusica().stopMusica();
            parserOutput.getInterfacciaGioco().getMusica().riproduciClip("src/main/resources/resource/audio/audio_morte.wav");
            InterfacciaMorte morte = new InterfacciaMorte(parserOutput.getInterfacciaGioco().getParentFrame());
            morte.setVisible(true);
            parserOutput.getInterfacciaGioco().dispose();
        }
        return msg;
    }

}
