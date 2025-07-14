# The Last Bounty 
#### Progettazione avventura testuale per esame Metodo Avanzati di Programmazione
#### Miccoli Francesco Pio
#### Marzulli Leonardo Nicola
#### Sivo Roberto
## Dettagli Implementativi

### 1. Utilizzo dei File
    
        carica_partita = new javax.swing.JButton("Carica Partita");
        buttonPanel.add(carica_partita);
        carica_partita.setFont(caricaFontUncial(30f));
        carica_partita.setForeground(textColor);
        carica_partita.setBackground(new Color(100, 150, 150, 155));
        aggiungiIcona(carica_partita, "/resource/img/icone/icona_carica_partita.png");

        JFileChooser fileChooser = new javax.swing.JFileChooser();

        fileChooser.setDialogTitle("Caricamento Salvataggio");
        fileChooser.setCurrentDirectory(new java.io.File("src/main/resources/resource/salvataggi"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Files .dat", "dat"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        carica_partita.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                int valore = fileChooser.showOpenDialog(InterfacciaIniziale.this);
                if (valore == javax.swing.JFileChooser.APPROVE_OPTION) {
                    try {
                        java.io.File file = fileChooser.getSelectedFile();
                        InterfacciaGioco gioco = new InterfacciaGioco(InterfacciaIniziale.this, file);
                        gioco.setVisible(true);
                        musica.stopMusica();
                        setVisible(false);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(InterfacciaIniziale.this, e.getMessage(), "Errore",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    
Snippet di codice contenuto in [InterfacciaIniziale.java](../src/main/java/grafica/InterfacciaIniziale.java) carica la partita salvata in un file .dat utilizzando l'import JFileChooser.

    
    public class SalvaObserver implements GameObserver {

    @Override
    public String update(GameDescription description, ParserOutput parserOutput) {
        String msg = "";
        if (parserOutput.getCommand().getType() == CommandType.SAVE) {
            Object arg = parserOutput.getParams();
            if (arg == null) {
                msg = "Collega il tuo cervello, non hai specificato il nome del file di salvataggio! Usa il comando 'Salva' seguito dal nome del file.";
                return msg;
            }
            String nameFile = (String) arg;
            try {
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(System.getProperty("user.dir") + File.separator + "src/main/resources/resource/salvataggi"
                                + File.separator + nameFile + ".dat"));
                description.getChrono().stop();
                out.writeObject(description);
                description.getChrono().startAgain(description.getChrono().getElapsedTime());
                out.close();
                msg = "Salvataggio effettuato in " + System.getProperty("user.dir") + File.separator + "resource"
                        + File.separator + "salvataggi" + File.separator + nameFile + ".dat";
            } catch (IOException e) {
                msg = "[ERRORE] Salvataggio del gioco non riuscito!";
            }
        }
        return msg;
    }
    }
    

Questa classe SalvaObserver crea il file di salvataggio .dat utilizzato.

### 2. Utilizzo dei Database/JDBC
    
    public void connect() throws SQLException {
        proprietaDB = new Properties();
        proprietaDB.setProperty("user", "francesco");
        proprietaDB.setProperty("password", "cattivissimo");
        conn = DriverManager.getConnection("jdbc:h2:./database", proprietaDB);
    }

Snippet di codice contenuto in [GestioneDB.java](../src/main/java/database/GestioneDB.java) effettua la connessione al Database SQL [mondoDB.sql](../src/main/java/database/mondoDB.sql).

### 3. Utilizzo dei Thread e della Programmazione Concorrente
    public void stampa(String testo) {
        this.paroleDaStampare = testo.split(" ");
        if (thread != null && thread.isAlive()) {
            interrupt();
        }
        thread = new Thread(this);
        textFieldInput.setEnabled(false);
        thread.start();
    }

    /**
     *
     * @param r
     */
    public void join(Runnable r){
        new Thread(() -> {
            if (thread != null && thread.isAlive()) {
                try {
                    thread.join();
                    textFieldInput.setEnabled(false);
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StampaTesto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            r.run();
        }).start();
        
    }

Grazie all'utilizzo dei thread stampiamo a video tutto sulle caselle.

### 4. Utilizzo delle Socket e/o delle REST
    
    private static ResponseAPI getResponseAPI() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://opentdb.com/api.php");
		Response resp = target.queryParam("amount", 1)
				.queryParam("category", 20)
				.queryParam("type", "multiple")
				.request(MediaType.APPLICATION_JSON).get();
		String jsonString = resp.readEntity(String.class);
		Gson gson = new Gson();
		return gson.fromJson(jsonString, ResponseAPI.class);
	}

Questa rest è stata utilizzata per il sistema di gestione delle domande del gioco.

### 5. Utilizzo delle Swing
       
       jPanel1 = new javax.swing.JPanel() {
            private java.awt.Image backgroundImage = null;
            {
                java.net.URL imgUrl = getClass().getResource("/resource/img/schermata_principale.png");
                if (imgUrl != null) {
                    backgroundImage = new javax.swing.ImageIcon(imgUrl).getImage();
                }
            }
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        jPanel1.setLayout(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1920, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1080, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

    

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout()); 

       
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.GridLayout(3, 2, 30, 30));
        buttonPanel.setOpaque(false); 

        
        java.awt.Color textColor = java.awt.Color.BLACK;


        gioca = new javax.swing.JButton("Gioca");
        buttonPanel.add(gioca);
        gioca.setFont(caricaFontUncial(30f));
        gioca.setForeground(textColor);
        aggiungiIcona(gioca, "/resource/img/icone/icona_gioca.png");

        gioca.setBackground(new Color(100, 150, 150, 155)); 
       

        gioca.addActionListener(e -> {
            musica.stopMusica();
            InterfacciaGioco gioco = null;
            try {
                gioco = new InterfacciaGioco(this);
                gioco.setVisible(true);
                setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                musica.playMusic("/resource/audio/musica_gioco.wav");
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
       
Questa è la swing principale utilizzata in [InterfacciaIniziale.java](../src/main/java/grafica/InterfacciaIniziale.java). 
Per ogni interfaccia abbiamo utilizzato impostazioni diverse.

### 6. Utilizzo delle lambda Expression, Stream e Pipeline
    
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
Grazie all'utilizzo delle lambda expression , siamo riusciti a creare la rete di connessione tra le caselle.


    