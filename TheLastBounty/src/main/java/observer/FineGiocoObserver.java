package observer;

import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObserver;
import parser.ParserOutput;
import comandi.CommandType;

public class FineGiocoObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.THE_END) {
            description.getChrono().stop();
            parserOutput.getInterfacciaGioco().fineGioco();
        }

        return msg;
    }

}
