package giocatore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import database.GestioneDB;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class Inventario implements Serializable {
    private final HashMap<Item, Integer> inventario;

    /**
     * costruttore della classe inventario e inizializza l'inventario con gli oggetti di partenza
     */
    public Inventario() {
        inventario = new HashMap<>();
        try {
            GestioneDB db = GestioneDB.getInstance();
            HashMap<Item, Integer> items = db.loadStaringItems();
            for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                this.addOggetto(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * aggiunge un oggetto all'inventario e incrementa la quantità se già presente
     * @param oggetto
     * @param quantity
     */
    public void addOggetto(Item oggetto, int quantity) {
        if (inventario.containsKey(oggetto)) {
            inventario.put(oggetto, inventario.get(oggetto) + quantity);
        } else {
            inventario.put(oggetto, quantity);
        }
    }

    /**
     * rimuove un oggetto dall'inventario, decrementando la quantità se maggiore di 1
     * @param oggetto
     */
    public void remove(Item oggetto) {
        if (inventario.containsKey(oggetto)) {
            if (inventario.get(oggetto) > 1) {
                inventario.put(oggetto, inventario.get(oggetto) - 1);
            } else {
                inventario.remove(oggetto);
            }
        }
    }

    /**
     * restituisce la dimensione dell'inventario
     * ovvero il numero di oggetti presenti
     * @return
     */
    public int getSize() {
        return inventario.size();
    }

    /**
     * restituisce un set di oggetti presenti nell'inventario
     * ovvero le chiavi della mappa inventario
     * @return
     */
    public Set<Item> getOggetti() {
        return inventario.keySet();
    }

    /**
     * verifica se l'inventario contiene un oggetto specifico
     * @param oggetto
     * @return
     */
    public boolean contains(Item oggetto) {
        return inventario.containsKey(oggetto);
    }

    /**
     * verifica se l'inventario contiene uno o più oggetti specifici
     * confrontando i nomi e gli alias degli oggetti
     * @param oggetto
     * @return
     */
    public boolean contains(String... oggetto) {
        for (String nameItem : oggetto) {
            boolean found = false;
            nameItem = nameItem.toLowerCase().trim();
            for (Item item : inventario.keySet()) {
                if (item.getName().equals(nameItem) || item.getAlias().contains(nameItem)) {
                    found = true;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * restituisce la quantità di un oggetto specifico nell'inventario
     * @param oggetto
     * @return
     */
    public int getQuantity(Item oggetto) {
        if (!inventario.containsKey(oggetto)) {
            return 0;
        }
        return inventario.get(oggetto);
    }

    /**
     * restituisce i dati dell'inventario in un formato adatto per JTable.
     * ogni riga contiene il nome dell'oggetto e la quantità
     * @return
     */
    public String[][] getInventarioToJTableData() {
        String[][] invS = new String[inventario.size()][2];
        int i = 0;

        for (Item item : inventario.keySet()) {
            invS[i][0] = Pattern.compile("^.").matcher(item.getName()).replaceFirst(m -> m.group().toUpperCase());
            invS[i][1] = inventario.get(item).toString();
            i++;
        }
        return invS;
    }

    /**
     * restituisce l'inventario come una mappa di oggetti e quantità
     * @return
     */
    public HashMap<Item, Integer> getInventario() {
        return inventario;
    }
}
