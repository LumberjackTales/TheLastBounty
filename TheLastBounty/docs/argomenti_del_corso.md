# The Last Bounty 
#### Progettazione avventura testuale per esame Metodo Avanzati di Programmazione
#### Miccoli Francesco Pio
#### Marzulli Leonardo Nicola
#### Sivo Roberto

```java

```

## Dettagli Implementativi

### 1. Utilizzo dei File
    ```java
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
    ```
Questo pezzo di codice presente in [InterfacciaIniziale.java](./src/main/java/grafica/InterfacciaIniziale.java) serve a caricare la partita presente in un file di salvataggio .dat utilizzando l'import JFileChooser.

( Qui sotto andrà messa la parte di codice che crea il file .dat)

    ```java

    ```

### 2. Utilizzo dei Database/JDBC
    ```java
    public void connect() throws SQLException {
        proprietaDB = new Properties();
        proprietaDB.setProperty("user", "francesco");
        proprietaDB.setProperty("password", "cattivissimo");
        conn = DriverManager.getConnection("jdbc:h2:./database", proprietaDB);
    }
     ```
Questa è la parte di codice in [GestioneDB.java](./src/main/java/database/GestioneDB.java) che serve a collegare il [mondoDB.sql](./src/main/java/database/mondoDB.sql).

### 3. Utilizzo dei Thread e della Programmazione Concorrente
    ```java
 
     ```

### 4. Utilizzo delle Socket e/o delle REST
    ```java
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
    ```
Questa rest è stata utilizzata per il sistema di gestione delle domande del gioco.

### 5. Utilizzo delle Swing
       ```java
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
       ```
Questa è la swing principale utilizzata in [InterfacciaIniziale.java](./src/main/java/grafica/InterfacciaIniziale.java). 
Per ogni interfaccia abbiamo utilizzato impostazioni diverse.

### 6. Utilizzo delle lambda Expression, Stream e Pipeline
    ```java

     ```


    