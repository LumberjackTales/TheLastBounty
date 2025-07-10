package observer;

import java.util.ArrayList;
import java.util.List;

import database.Casella;
import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.GameObservable;
import componentiaggiuntivi.GameObserver;
import database.GestioneDB;
import componentiaggiuntivi.StampaTesto;
import parser.ParserOutput;
import comandi.Command;
import comandi.CommandType;

public class TheLastBounty extends GameDescription implements GameObservable {
    private transient List<GameObserver> observer;
    private transient ParserOutput parserOutput;
    private transient List<String> messages;
    private Casella currentCasella;

    @Override
    public void init() throws Exception {
        // Aggiunta comandi
        Command command = new Command(CommandType.MORTE, "muori");
        getCommands().add(command);

        command = new Command(CommandType.HELP, "help");
        command.setAlias(new String[] { "aiuto", "?" });
        getCommands().add(command);

        command = new Command(CommandType.OSSERVA, "osserva");
        command.setAlias(new String[] { "guarda", "vedi" });
        getCommands().add(command);

        command = new Command(CommandType.EST, "est");
        command.setAlias(new String[] { "e", "east" , "destra" ,"est"});
        getCommands().add(command);

        command = new Command(CommandType.OVEST, "ovest");
        command.setAlias(new String[] { "o", "west", "w", "ovest", "sinistra" });
        getCommands().add(command);

        command = new Command(CommandType.NORD, "nord");
        command.setAlias(new String[] { "n", "north", "avanti", "nord" });
        getCommands().add(command);

        command = new Command(CommandType.SUD, "sud");
        command.setAlias(new String[] { "s", "south", "indietro", "sud" });
        getCommands().add(command);

        command = new Command(CommandType.PRENDI, "prendi");
        command.setAlias(new String[] { "raccogli", "prendere" });
        getCommands().add(command);

        command = new Command(CommandType.PARLA, "parla");
        getCommands().add(command);

        command = new Command(CommandType.USE, "usa");
        command.setAlias(new String[] { "utilizza" });
        getCommands().add(command);

        command = new Command(CommandType.SAVE, "salva");
        getCommands().add(command);

        command = new Command(CommandType.CREA, "crea");
        command.setAlias(new String[] { "crafta", "costruisci" });
        getCommands().add(command);

        // load caselle gioco
        GestioneDB db = GestioneDB.getInstance();
        currentCasella = db.loadMappa(getCaselle());
        setCurrentCasella(currentCasella);
        setDialoghi(db.loadDialoghi(false));

        attachObeservers();
    }

    public void attachObeservers() {
        observer = new ArrayList<>();
        messages = new ArrayList<>();
        // aggiunta observer
        attach(new MorteObserver());
        attach(new HelpObserver());
        attach(new OsservaObserver());
        attach(new MuoviObserver());
        attach(new PrendiObserver());
        attach(new ParlaObserver());
        attach(new UsaObserver());
        attach(new SalvaObserver());
        attach(new CreaObserver());
        attach(new FineGiocoObserver());
    }

    @Override
    public void nextMove(ParserOutput p, StampaTesto out) {
        if (p == null || p.getCommand().getType() == CommandType.NULL_COMMAND
                && getLastCommand().getType() != CommandType.PARLA) {
            out.stampa("Parla una lingua comprensibile,per favore!");
        } else {
            parserOutput = p;
            messages.clear();
            notifyObservers();
            if (!messages.isEmpty()) {
                for (String s : messages) {
                    if (s.length() > 0) {
                        out.stampa(s);
                    }
                }
            } else {
                out.stampa("Parla una lingua comprensibile,per favore!");
            }
            setLastCommand(p.getCommand());
        }
    }

    @Override
    public void attach(GameObserver o) {
        if (!observer.contains(o)) {
            observer.add(o);
        }
    }

    @Override
    public void detach(GameObserver o) {
        if (observer.contains(o)) {
            observer.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        for (GameObserver o : observer) {
            String msg = o.update(this, parserOutput);
            if (msg.length() != 0)
                messages.add(msg);
        }
    }

    @Override
    public final String getIntro() {
        return getCurrentCasella().getDescrizione();
    }
}
