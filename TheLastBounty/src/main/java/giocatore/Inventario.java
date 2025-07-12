package giocatore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import database.GestioneDB;


public class Inventario implements Serializable {
    private final HashMap<Item, Integer> inventario;

    
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

    
    public void addOggetto(Item oggetto, int quantity) {
        if (inventario.containsKey(oggetto)) {
            inventario.put(oggetto, inventario.get(oggetto) + quantity);
        } else {
            inventario.put(oggetto, quantity);
        }
    }

    
    public void remove(Item oggetto) {
        if (inventario.containsKey(oggetto)) {
            if (inventario.get(oggetto) > 1) {
                inventario.put(oggetto, inventario.get(oggetto) - 1);
            } else {
                inventario.remove(oggetto);
            }
        }
    }

    
    public int getSize() {
        return inventario.size();
    }

    
    public Set<Item> getOggetti() {
        return inventario.keySet();
    }

    
    public boolean contains(Item oggetto) {
        return inventario.containsKey(oggetto);
    }

   
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


    public int getQuantity(Item oggetto) {
        if (!inventario.containsKey(oggetto)) {
            return 0;
        }
        return inventario.get(oggetto);
    }


  
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

 
    public HashMap<Item, Integer> getInventario() {
        return inventario;
    }
}
