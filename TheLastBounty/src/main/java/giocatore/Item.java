package giocatore;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class Item implements Serializable {

    private String name;
    private String description;
    private Set<String> alias;
    private boolean pickable = false;
    private boolean visible = false;

    /**
     *  costruttore di item
     * @param name
     */
    public Item(String name) {
        this.name = name;
        description = "";
        alias = new HashSet<>();
    }

    /**
     * costruttore di item con nome e descrizione
     * @param name
     * @param description
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        alias = new HashSet<>();
    }

    /**
     * costruttore di item con nome, descrizione e alias
     * @param name
     * @param description
     * @param alias
     */
    public Item(String name, String description, Set<String> alias) {
        this.name = name;
        this.description = description;
        this.alias = alias;
    }

    /**
     * * restituisce un set di alias dell'oggetto
     * @return
     */
    public Set<String> getAlias() {
        return alias;
    }

    /**
     * * restituisce il nome dell'oggetto
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * * imposta il nome dell'oggetto
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * * restituisce la descrizione dell'oggetto
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @param alias
     */
    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }

    /**
     * 
     * @param alias
     */
    public void setAlias(String[] alias) {
        this.alias = new HashSet<>(Arrays.asList(alias));
    }

    /**
     * * restituisce se l'oggetto è pickable
     * @return
     */
    public boolean isPickable() {
        return pickable;
    }

    /**
     *  * imposta se l'oggetto è pickable
     * @param consumable
     */
    public void setPickable(boolean consumable) {
        this.pickable = consumable;
    }

    /**
     * * restituisce se l'oggetto è visibile
     * @return
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * * imposta se l'oggetto è visibile
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * * verifica se due oggetti sono uguali confrontando i loro nomi
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (obj.getClass() != this.getClass())
            return false;

        final Item other = (Item) obj;
        return this.name.equals(other.name);
    }

    /**
     * * restituisce l'hashcode dell'oggetto basato sul nome
     * @return
     */
    public int hashCode() {
        return name.hashCode();
    }
}

