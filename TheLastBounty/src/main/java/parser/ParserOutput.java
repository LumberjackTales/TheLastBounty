package parser;

import grafica.InterfacciaGioco;
import comandi.Command;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class ParserOutput {

    private Command command;
    private InterfacciaGioco interfaccia;
    private Object params;

    /**
     * Costruttore del ParserOutput.
     * @param command
     * @param params
     * @param interfaccia
     */
    public ParserOutput(Command command, Object params, InterfacciaGioco interfaccia) {
        this.command = command;
        this.params = params;
        this.interfaccia = interfaccia;
    }

    /**
     *  * Restituisce il comando associato a questo ParserOutput.
     * @return
     */
    public Command getCommand() {
        return command;
    }

    /**
     * 
     * @param command
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     *
     * @return
     */
    public InterfacciaGioco getInterfacciaGioco() {
        return interfaccia;
    }

    /**
     *
     * @param interfaccia
     */
    public void setInterfacciaGioco(InterfacciaGioco interfaccia) {
        this.interfaccia = interfaccia;
        this.interfaccia = interfaccia;
    }

    /**
     *
     * @return
     */
    public Object getParams() {
        return params;
    }
}
