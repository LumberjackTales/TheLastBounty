package parser;

import grafica.InterfacciaGioco;
import comandi.Command;


public class ParserOutput {

    private Command command;
    private InterfacciaGioco interfaccia;
    private Object params;

   
    public ParserOutput(Command command, Object params, InterfacciaGioco interfaccia) {
        this.command = command;
        this.params = params;
        this.interfaccia = interfaccia;
    }

    
    public Command getCommand() {
        return command;
    }

 
    public void setCommand(Command command) {
        this.command = command;
    }

   
    public InterfacciaGioco getInterfacciaGioco() {
        return interfaccia;
    }

    
    public void setInterfacciaGioco(InterfacciaGioco interfaccia) {
        this.interfaccia = interfaccia;
        this.interfaccia = interfaccia;
    }

   
    public Object getParams() {
        return params;
    }
}
