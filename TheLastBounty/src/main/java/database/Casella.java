package database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import giocatore.Item;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
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

    /**
     *
     * @param id
     */
    public Casella(int id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param nome
     * @param descrizione
     */
    public Casella(int id, String nome, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.descrizioneIniziale = descrizione;
    }

    /**
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * legge tutte le descrizioni dela casella corrente dal database
     * @return
     */
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

    /**
     * imposta la descrizione iniziale dal database
     * @param descrizione
     */
    public void setDescrizioneIniziale(String descrizione) {
        this.descrizioneIniziale = descrizione;
    }

    /**
     * imposta la descrizione aggiornata dal database
     * @param descrizioneAggiornata
     */
    public void setDescrizioneAggiornata(String descrizioneAggiornata) {
        this.descrizioneAggiornata = descrizioneAggiornata;
    }

    /**
     * imposta la desrizione aggiuntiva dal database
     * @param descrizioneAggiuntiva
     */
    public void setDescrizioneAggiuntiva(String descrizioneAggiuntiva) {
        this.descrizioneAggiuntiva = descrizioneAggiuntiva;
    }

    /**
     * verifica se una casella è accessibile
     * @return
     */
    public boolean isEnterable() {
        return enterable;
    }

    /**
     *
     * @param visible
     */
    public void setEnterable(boolean visible) {
        this.enterable = visible;
    }

    /**
     *
     * @return
     */
    public Casella getSouth() {
        return south;
    }

    /**
     *
     * @param south
     */
    public void setSouth(Casella south) {
        this.south = south;
    }

    /**
     *
     * @return
     */
    public Casella getNorth() {
        return north;
    }

    /**
     *
     * @param north
     */
    public void setNorth(Casella north) {
        this.north = north;
    }

    /**
     *
     * @return
     */
    public Casella getEast() {
        return east;
    }

    /**
     *
     * @param east
     */
    public void setEast(Casella east) {
        this.east = east;
    }

    /**
     *
     * @return
     */
    public Casella getWest() {
        return west;
    }

    /**
     *
     * @param west
     */
    public void setWest(Casella west) {
        this.west = west;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
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

    /**
     *
     * @return
     */
    public Set<Item> getOggetti() {
        return oggetti.keySet();
    }

    /**
     *
     * @param oggetto
     * @param quantity
     */
    public void addOggetto(Item oggetto, int quantity) {
        if (oggetti.containsKey(oggetto)) {
            oggetti.put(oggetto, oggetti.get(oggetto) + quantity);
        } else {
            oggetti.put(oggetto, quantity);
        }
    }

    /**
     *
     * @param oggetto
     * @return
     */
    public int removeOggetto(Item oggetto) {
        int quantity = 0;
        if (oggetti.containsKey(oggetto)) {
            quantity = oggetti.get(oggetto);
            oggetti.remove(oggetto);
        }
        return quantity;
    }

    /**
     *
     * @param oggetto
     */
    public void clearOggetto(Item oggetto) {
        oggetti.remove(oggetto);
    }

    /**
     *
     * @return
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     *
     * @param updated
     */
    public void setUpdated(boolean updated) {
        visited = true;
        this.updated = updated;
    }

    /**
     *
     * @return
     */
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
