package database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import giocatore.Item;


public class Casella implements Serializable {
    private final int id;
    private String nome;
    private String descrizioneIniziale;
    private String descrizioneAggiornata;
    private String descrizioneAggiuntiva;
    private boolean enterable = true;
    private boolean visited = false;
    private boolean updated = false;

    private Casella south = null;
    private Casella north = null;
    private Casella east = null;
    private Casella west = null;

    private final Map<Item, Integer> oggetti = new HashMap<>();

    
    public Casella(int id) {
        this.id = id;
    }

    
    public Casella(int id, String nome, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.descrizioneIniziale = descrizione;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public String getDescrizione() {
        if (visited) {
            if (descrizioneAggiornata.length() != 0 && updated)
                return descrizioneAggiornata + printItems();
            else
                return descrizioneAggiuntiva + printItems();
        }

        this.visited = true;
        return descrizioneIniziale;
    }

    
    public void setDescrizioneIniziale(String descrizione) {
        this.descrizioneIniziale = descrizione;
    }

    
    public void setDescrizioneAggiornata(String descrizioneAggiornata) {
        this.descrizioneAggiornata = descrizioneAggiornata;
    }

    
    public void setDescrizioneAggiuntiva(String descrizioneAggiuntiva) {
        this.descrizioneAggiuntiva = descrizioneAggiuntiva;
    }

    
    public boolean isEnterable() {
        return enterable;
    }

    
    public void setEnterable(boolean visible) {
        this.enterable = visible;
    }

    
    public Casella getSouth() {
        return south;
    }

    
    public void setSouth(Casella south) {
        this.south = south;
    }

   
    public Casella getNorth() {
        return north;
    }

    public void setNorth(Casella north) {
        this.north = north;
    }

    
    public Casella getEast() {
        return east;
    }

    
    public void setEast(Casella east) {
        this.east = east;
    }

    
    public Casella getWest() {
        return west;
    }

    
    public void setWest(Casella west) {
        this.west = west;
    }

    
    public int getId() {
        return id;
    }

    
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

   
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Casella other = (Casella) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
    public Set<Item> getOggetti() {
        return oggetti.keySet();
    }

    
    public void addOggetto(Item oggetto, int quantity) {
        if (oggetti.containsKey(oggetto)) {
            oggetti.put(oggetto, oggetti.get(oggetto) + quantity);
        } else {
            oggetti.put(oggetto, quantity);
        }
    }

   
    public int removeOggetto(Item oggetto) {
        int quantity = 0;
        if (oggetti.containsKey(oggetto)) {
            quantity = oggetti.get(oggetto);
            oggetti.remove(oggetto);
        }
        return quantity;
    }

   
    public void clearOggetto(Item oggetto) {
        oggetti.remove(oggetto);
    }

  
    public boolean isUpdated() {
        return updated;
    }

  
    public void setUpdated(boolean updated) {
        visited = true;
        this.updated = updated;
    }


    public String printItems() {
        String msg = "";

        for (Item item : oggetti.keySet()) {
            if (item.isVisible()) {
                msg += "    " + Pattern.compile("^.").matcher(item.getName()).replaceFirst(m -> m.group().toUpperCase())
                        + " x" + oggetti.get(item) + "\n";
            }
        }

        if (msg.length() != 0) {
            msg = "\nNella stanza sono presenti i seguenti oggetti:\n" + msg;
        }

        return msg;
    }


}
