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
     *
     * @param name
     */
    public Item(String name) {
        this.name = name;
        description = "";
        alias = new HashSet<>();
    }

    /**
     *
     * @param name
     * @param description
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        alias = new HashSet<>();
    }

    /**
     *
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
     *
     * @return
     */
    public Set<String> getAlias() {
        return alias;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
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
     *
     * @return
     */
    public boolean isPickable() {
        return pickable;
    }

    /**
     *
     * @param consumable
     */
    public void setPickable(boolean consumable) {
        this.pickable = consumable;
    }

    /**
     *
     * @return
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     *
     * @param visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     *
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
     *
     * @return
     */
    public int hashCode() {
        return name.hashCode();
    }
}

