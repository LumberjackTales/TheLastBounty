package grafica;

import java.io.InputStream;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import componentiaggiuntivi.Musica;
import eccezioni.GetClassificaException;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */

 /**
     *  interfaccia grafica per l'interfaccia iniziale del gioco
     * 
     */

public class InterfacciaIniziale extends javax.swing.JFrame {
    private final Musica musica = new Musica();
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton gioca;
    private javax.swing.JButton carica_partita;
    private javax.swing.JButton impostazioni;
    private javax.swing.JButton esci;
    private javax.swing.JButton riconoscimenti;
    private javax.swing.JButton classifica;

    /**
     *
     */
    public InterfacciaIniziale() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    private Font caricaFontUncial(float size) {
        try (InputStream is = getClass().getResourceAsStream("/resource/fonts/UncialAntiqua-Regular.otf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Serif", Font.PLAIN, (int) size); 
        }
    }

    private void aggiungiIcona(javax.swing.JButton bottone, String pathIcona) {
        java.net.URL imgUrl = getClass().getResource(pathIcona);
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            Image scaled = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            bottone.setIcon(new ImageIcon(scaled));
            bottone.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            bottone.setIconTextGap(10); 
        } else {
            System.err.println("Icona non trovata: " + pathIcona);
        }
    }
    
    private void initComponents() {

        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        musica.playMusic("/resource/audio/musica_intro.wav");
        musica.setVolume(0.3f);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                if (!musica.isPlaying())
                    musica.playMusic("/resource/audio/musica_intro.wav");
            }
        });

        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                if (!musica.isPlaying() && !musica.isInPaused())
                    musica.playMusic("/resource/audio/musica_intro.wav");
            }

            @Override
            public void windowLostFocus(java.awt.event.WindowEvent evt) {

            }
        });

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
  
        classifica = new javax.swing.JButton("Classifica");
        buttonPanel.add(classifica);
        classifica.setFont(caricaFontUncial(30f));
        classifica.setForeground(textColor);
        classifica.setBackground(new Color(100, 150, 150, 155));
        aggiungiIcona(classifica, "/resource/img/icone/icona_classifica.png");
        classifica.addActionListener (e-> {
            try {
                InterfacciaClassifica classificaFrame = new InterfacciaClassifica();
                classificaFrame.setVisible(true);
            } catch (GetClassificaException ex) {
                JOptionPane.showMessageDialog(this, "Errore durante il caricamento della classifica: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        impostazioni = new javax.swing.JButton("Impostazioni");
        buttonPanel.add(impostazioni);
        impostazioni.setFont(caricaFontUncial(30f));
        impostazioni.setForeground(textColor);
        impostazioni.setBackground(new Color(100, 150, 150, 155));
        impostazioni.addActionListener(e -> new InterfacciaImpostazioni(musica).setVisible(true));
        aggiungiIcona(impostazioni, "/resource/img/icone/icona_opzioni.png");


        esci = new javax.swing.JButton("Esci");
        buttonPanel.add(esci);
        esci.setFont(caricaFontUncial(30f));
        esci.setForeground(textColor);
        esci.setBackground(new Color(100, 150, 150, 155));
        aggiungiIcona(esci, "/resource/img/icone/icona_esci.png");
        
        esci.addActionListener(e -> {
            int scelta = JOptionPane.showConfirmDialog(
                    null,
                    "Sei sicuro di voler uscire?",
                    "Conferma Uscita",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (scelta == JOptionPane.YES_OPTION) {
                System.exit(0); 
            }
        });

        riconoscimenti = new javax.swing.JButton("Riconoscimenti");
        buttonPanel.add(riconoscimenti);
        riconoscimenti.setFont(caricaFontUncial(30f));
        riconoscimenti.setForeground(textColor);
        riconoscimenti.setBackground(new Color(100, 150, 150, 155));
        aggiungiIcona(riconoscimenti, "/resource/img/icone/icona_riconoscimenti.png");
        riconoscimenti.addActionListener(e -> new interfacciaRiconoscimenti(this).setVisible(true));
            
        

        
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        jPanel1.add(buttonPanel, gbc);


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }        

    /**
     *
     * @return
     */
    public Musica Musica() {        
        return this.musica;
    }

}
