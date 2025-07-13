package database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.h2.tools.RunScript;

import giocatore.Dialogo;
import giocatore.Item;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class GestioneDB {

    private static Connection conn;
    private Properties proprietaDB;
    private static GestioneDB instance = null;

    
    private GestioneDB() throws SQLException {
        connect();
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public static GestioneDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new GestioneDB();
        }
        return instance;
    }

    /**
     *
     * @throws SQLException
     */
    public void connect() throws SQLException {
        proprietaDB = new Properties();
        proprietaDB.setProperty("user", "francesco");
        proprietaDB.setProperty("password", "cattivissimo");
        conn = DriverManager.getConnection("jdbc:h2:./database", proprietaDB);
    }


    private <T> T executeWithRetry(SqlFunction<T> function) throws SQLException {
        int retryCount = 0, max_retries = 5;
        while (retryCount < max_retries) {
            try {
                return function.apply();
            } catch (SQLException e) {
                if (e.getSQLState().equals("42S04")) {
                    try {
                        crea();
                    } catch (FileNotFoundException e_file) {
                        throw new SQLException("File not found", e_file);
                    }
                } else if (e.getSQLState().equals("08003")) {
                    connect();
                } else {
                    throw e;
                }
                retryCount++;
            }
        }
        throw new SQLException("Operation failed after " + max_retries + " attempts");
    }

    /**
     *
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public void crea() throws SQLException, FileNotFoundException {
        RunScript.execute(conn, new FileReader("src/main/java/database/mondoDB.sql"));
    }

    /**
     * carica la mappa del gioco
     * @param caselle
     * @return
     * @throws SQLException
     */
    public Casella loadMappa(final List<Casella> caselle) throws SQLException {
        return executeWithRetry(() -> {
            Casella start = null;
            String query = "SELECT * FROM Casella";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Casella casella = creaCasellaDaResultSet(rs);
                    List<Item> items = getItems(casella.getId());
                    for (Item item : items) {
                        casella.addOggetto(item, 1);
                    }
                    caselle.add(casella);
                    if (casella.getId() == 100) {
                        start = casella;
                    }
                }
            }
            connectCaselle(caselle);

            return start;
        });
    }


    private Casella creaCasellaDaResultSet(ResultSet rs) throws SQLException {
        Casella casella = new Casella(rs.getInt(1));
        casella.setNome(rs.getString(2));
        casella.setDescrizioneIniziale(rs.getString("descrizioneIniziale"));
        casella.setDescrizioneAggiuntiva(rs.getString("descrizioneAggiuntiva"));
        casella.setDescrizioneAggiornata(rs.getString("descrizioneAggiornata"));
        casella.setEnterable(rs.getBoolean("enterable"));
        return casella;
    }


    private void connectCaselle(List<Casella> caselle) throws SQLException {
        executeWithRetry(() -> {
            Map<Integer, Casella> MapCaselle = new HashMap<>();
            for (Casella c : caselle) {
                MapCaselle.put(c.getId(), c);
            }

            String query = "SELECT * FROM ConnessioniCaselle";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Casella c1 = MapCaselle.get(rs.getInt("casella_id1"));
                    Casella c2 = MapCaselle.get(rs.getInt("casella_id2"));
                    switch (rs.getString("direzione")) {
                        case "n":
                            c1.setNorth(c2);
                            c2.setSouth(c1);
                            break;
                        case "s":
                            c1.setSouth(c2);
                            c2.setNorth(c1);
                            break;
                        case "e":
                            c1.setEast(c2);
                            c2.setWest(c1);
                            break;
                        case "o":
                            c1.setWest(c2);
                            c2.setEast(c1);
                            break;
                        default:
                            break;
                    }
                }
            }

            return null;
        });
    }

    /**
     * carica i dialoghi di gioco
     * @param priority
     * @return
     * @throws SQLException
     */
    public List<Dialogo> loadDialoghi(boolean priority) throws SQLException {
        return executeWithRetry(() -> {
            List<Dialogo> dialoghi = new ArrayList<>();
            String query = "SELECT * FROM Dialoghi WHERE priorita = " + priority + ";";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Dialogo d = new Dialogo(rs.getInt(1));
                    d.setInfoDialogo(rs.getString(3), rs.getString(6), rs.getString(4), rs.getString(5));
                    dialoghi.add(d);
                }
            }
            return dialoghi;
        });
    }

    /**
     * cambia il dialogo in base alla risposta ricevuta
     * @param dialogo
     * @throws SQLException
     */
    public void changeDialogo(Dialogo dialogo) throws SQLException {
        executeWithRetry(() -> {
            String query = "SELECT * FROM Dialoghi WHERE priorita = " + true + " AND id = " + dialogo.getIdCasella()
                    + ";";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    dialogo.setInfoDialogo(rs.getString(3), rs.getString(6), rs.getString(4), rs.getString(5));
                }
            }
            return null;
        });
    }

    /**
     *
     * @return
     * @throws FileNotFoundException
     * @throws SQLException
     */
    public HashMap<Item, Integer> loadStaringItems() throws FileNotFoundException, SQLException {
        return executeWithRetry(() -> {
            HashMap<Item, Integer> items = new HashMap<>();
            String query = "SELECT o.*, oi.quantita FROM Oggetti_Iniziali oi JOIN Oggetto o ON oi.id = o.id";
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(query)) {
                    while (rs.next()) {
                        Item item = creaItemDaResultSet(rs);
                        int quantity = rs.getInt("quantita");
                        items.put(item, quantity);
                    }
                }            
            }
            return items;
        });
    }


    
    private List<Item> getItems(int id) throws SQLException {
        return executeWithRetry(() -> {
            List<Item> items = new ArrayList<>();
            String query = "SELECT * FROM Casella_Oggetto co JOIN Oggetto o ON oggetto_id = id WHERE casella_id = "
                    + id + ";";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    Item item = creaItemDaResultSet(rs);
                    Integer quantity = rs.getInt("quantita");
                    for (int i = 0; i < quantity; i++)
                        items.add(item);
                }
                rs.close();
            }
            return items;
        });
    }


    private Item creaItemDaResultSet(ResultSet rs) throws SQLException {
        Item item = new Item(rs.getString("nome"));
        item.setDescription(rs.getString("descrizione"));
        item.setAlias(getAlias(rs.getInt("id")));
        item.setPickable(rs.getBoolean("pickable"));
        item.setVisible(rs.getBoolean("visible"));
        return item;
    }


    private Set<String> getAlias(int id) throws SQLException {
        return executeWithRetry(() -> {
            String getAlias = "SELECT * FROM Alias WHERE id = " + id + ";";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(getAlias);
            Set<String> alias = new HashSet<>();
            while (rs.next()) {
                alias.add(rs.getString("alias").toLowerCase());
            }
            rs.close();
            return alias;
        });
    }

    /**
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        conn.close();
    }


    private interface SqlFunction<T> {
 
        T apply() throws SQLException;
    }
}

