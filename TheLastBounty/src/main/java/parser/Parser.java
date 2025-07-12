package parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

import grafica.InterfacciaGioco;
import componentiaggiuntivi.Utils;
import comandi.Command;
import comandi.CommandType;
import giocatore.Item;


public class Parser implements Serializable {
    private final Set<String> stopwords;
    private final List<Command> commands;
    private final Command nullCommand = new Command(CommandType.NULL_COMMAND, null);

  
    public Parser(Set<String> stopwords, List<Command> commands) {
        this.stopwords = stopwords;
        this.commands = commands;
    }

    private Command findCommand(final String token) {
        return commands.stream()
                .filter(cmd -> cmd.getName().equals(token) || cmd.getAlias().contains(token))
                .findFirst()
                .orElse(null);
    }

    
    private String[] findItems(final String[] tokens, final Set<Item> items) {
        List<String> itemsFound = new ArrayList<>();
        Map<Pattern, Item> patternMap = new HashMap<>();

        for (Item item : items) {
            Set<String> aliases = item.getAlias();
            aliases.add(item.getName().toLowerCase().trim());
            Pattern pattern = createPrefixRegexItem(new ArrayList<>(aliases));
            patternMap.put(pattern, item);
        }

        StringBuilder currentInput = new StringBuilder();
        StringBuilder lastItemFound = new StringBuilder();
        Pattern lastMatchedPattern = null;

        for (String token : tokens) {
            boolean found = false;
            currentInput.append(token).append(" ");
            String string = currentInput.toString().trim();

            
            if (lastMatchedPattern != null) {
                lastItemFound.append(token).append(" ");
                if (lastMatchedPattern.matcher(lastItemFound.toString()).find()) {
                    currentInput.setLength(0);
                    found = true;
                }
            }

           
            if (!found) {
                found = false;
                for (Pattern pattern : patternMap.keySet()) {
                    if (pattern.matcher(string).find()) {
                        lastMatchedPattern = pattern;
                        found = true;
                        break;
                    }
                }

               
                if (found) {
                    itemsFound.add(currentInput.toString().trim());
                    lastItemFound = new StringBuilder(currentInput.toString());
                    currentInput.setLength(0);
                }
            }
        }

        if (currentInput.length() > 0) {
            itemsFound.add(currentInput.toString().trim());
        }

        return itemsFound.toArray(new String[0]);
    }

 
    private Pattern createPrefixRegexItem(List<String> prefixes) {
        if (prefixes == null || prefixes.isEmpty()) {
            return Pattern.compile("^$");
        }

        prefixes.sort((a, b) -> Integer.compare(b.length(), a.length()));
        return Pattern.compile("^(?:" + String.join("|", prefixes) + ")");
    }

 
    public ParserOutput parse(String input, Set<Item> inventory, Set<Item> roomItems,
            InterfacciaGioco interfaccia) {
        List<String> tokens = Utils.parseString(input, stopwords);

        if (tokens.isEmpty()) {
            return new ParserOutput(nullCommand, null, interfaccia);
        }

 
        Command command = findCommand(tokens.get(0));
        if (command == null) {
            return new ParserOutput(nullCommand, input, interfaccia);
        }

        if (tokens.size() == 1) {
            return new ParserOutput(command, null, interfaccia);
        }

        switch (command.getType()) {
            case PRENDI, USE, OSSERVA -> {
                Set<Item> items = new java.util.HashSet<>();
                if (!command.getType().equals(CommandType.PRENDI)) {
                    items.addAll(inventory);
                }
                items.addAll(roomItems);
                String[] itemsFound = findItems(tokens.subList(1, tokens.size()).toArray(new String[0]), items);
                return new ParserOutput(command, itemsFound, interfaccia);
            }

            default -> {
                return new ParserOutput(command, String.join(" ", tokens.subList(1, tokens.size())), interfaccia);
            }
        }
    }
}