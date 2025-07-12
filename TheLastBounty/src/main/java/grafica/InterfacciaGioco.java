package grafica;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;

import comandi.CommandType;
import eccezioni.GameFileException;
import eccezioni.GameNotAvailableException;
import componentiaggiuntivi.Chrono;
import componentiaggiuntivi.GameDescription;
import componentiaggiuntivi.Musica;
import componentiaggiuntivi.StampaTesto;
import componentiaggiuntivi.Utils;
import parser.Parser;
import parser.ParserOutput;
import observer.TheLastBounty;

/**
 * Classe che implementa l'interfaccia grafica del gioco.
 * Gestisce la visualizzazione del testo, l'input del giocatore, la musica,
 * il cronometro e tutti i vari observer del gioco.
 *
 * @author francescomiccoli
 * @author marzullidino
 * @author sivoroberto
 */
public class InterfacciaGioco extends javax.swing.JFrame {
    private final Font FONT = new Font("Serif", Font.PLAIN, 20);
    private final String HUNTER = "src/main/resources/resource/img/cacciatore.png";

    private final Color BACKGROUND_PULSANTI = new Color(100, 150, 150, 155);
    private final Color WHITE = new Color(250, 249, 246);
    private final Color BLACK = new Color(32, 32, 35);
    private javax.swing.JButton esci;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JPanel imageViewer;
    private javax.swing.JButton inventario;
    private javax.swing.JPanel macroPanel;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton skip;
    private javax.swing.JButton torna_menu;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField textBox;
    private javax.swing.JPanel underPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton impostazioni;
    private javax.swing.JLabel cronometro;

    Musica musica = new Musica();

    private Parser parser = null;

    private GameDescription game;

    private final StampaTesto stampa;

    private Chrono chrono = new Chrono();

    private final JFrame parentFrame;

  
    public InterfacciaGioco(JFrame parentFrame) throws Exception {
        initComponents();
        this.parentFrame = parentFrame;

        setSize(1440,900);
        setLocationRelativeTo(null);
        stampa = StampaTesto.getInstance(textBox, textArea, skip);
        mainComponents(false, null);
    }

  
    public InterfacciaGioco(JFrame parentFrame, File f) throws Exception {
        initComponents();
        this.parentFrame = parentFrame;
        
        setSize(1440,900);
        setLocationRelativeTo(null);
        stampa = StampaTesto.getInstance(textBox, textArea, skip);
        mainComponents(true, f);
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

    private Font caricaFontUncial(float size) {
        try (InputStream is = getClass().getResourceAsStream("/resource/fonts/UncialAntiqua-Regular.otf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Serif", Font.PLAIN, (int) size); 
        }
    }

    public void mainComponents(boolean loadGame, File f) throws Exception {
        game = new TheLastBounty();
        game.setChrono(chrono);

        try {
            if (!loadGame) {
                game.init();
            } else {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                    TheLastBounty temp = (TheLastBounty) ois.readObject();
                    temp.attachObeservers();
                    game = temp;
                    chrono = game.getChrono();
                    chrono.startAgain(chrono.getElapsedTime());
                    ois.close();
                } catch (Exception ex) {
                    throw new GameFileException();
                }
            }
            try {
                File file = new File("src/main/resources/resource/stopword/stopwords");
                Set<String> stopwords = Utils.loadFileListInSet(file);
                parser = new Parser(stopwords, game.getCommands());
            } catch (IOException ex) {
                throw ex;
            }
            
            stampa.stampa(game.getIntro());
        } catch (GameFileException ex) {
            musica.stopMusica();
            throw ex;
        } catch (Exception ex) {          
            Logger.getLogger(InterfacciaGioco.class.getName()).log(Level.SEVERE, null, ex);
            musica.stopMusica();
            throw new GameNotAvailableException();
        }
    }

    private void initComponents() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                parentFrame.setVisible(true);
                dispose();
            }
        });

        panel = new javax.swing.JPanel();  
        macroPanel = new javax.swing.JPanel(){
            private java.awt.Image backgroundImage = null;
            {
                java.net.URL imgUrl = getClass().getResource("/resource/img/libro_sfondo.png");
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
        scrollPane = new javax.swing.JScrollPane();
        scrollPane.setViewportBorder(javax.swing.BorderFactory.createLineBorder(WHITE, 0));
        textArea = new javax.swing.JTextArea();
        scrollPane.scrollRectToVisible(textArea.getVisibleRect());
        imageViewer = new javax.swing.JPanel();
        imageLabel = new javax.swing.JLabel();
        underPanel = new javax.swing.JPanel();
        textBox = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();

        skip = new javax.swing.JButton("Skip");
        inventario = new javax.swing.JButton("Inventario");
        esci = new javax.swing.JButton("Esci");
        torna_menu = new javax.swing.JButton("Torna al menù");

        cronometro = new javax.swing.JLabel();
        
        impostazioni = new javax.swing.JButton("Impostazioni");

        panel.setOpaque(false);
        panel.setSize(new java.awt.Dimension(1920, 1500));
        panel.setLayout(new java.awt.BorderLayout());


        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("The Last Bounty");
        setIconImage(Toolkit.getDefaultToolkit().getImage("/resource/img/schermata_principale.png"));
        setResizable(false);


        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAutoscrolls(true);
        scrollPane.setPreferredSize(new java.awt.Dimension(900, 700));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setViewportView(textArea);

        textArea.setEditable(false);
        textArea.setFocusable(false);

        textArea.setBackground(new Color(0,0,0,0));
  
        textArea.setColumns(20);
        textArea.setForeground(Color.BLACK);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(textArea.getDocument().getLength());
        textArea.setFont(FONT);

        textArea.setPreferredSize(null);
        
        imageViewer.setBackground(new Color(0,0,0,0));

        imageLabel.setIcon(getScaledImage(new ImageIcon(HUNTER)));
        imageViewer.add(imageLabel);


        underPanel.setBackground(new Color(0,0,0,0));
        underPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        textBox.setPreferredSize(new java.awt.Dimension(650, 30));
        textBox.addActionListener(this::elaborateInput);
        


        skip.setFont(caricaFontUncial(13f));
        skip.setPreferredSize(new java.awt.Dimension(90, 30));
        skip.setBackground(BACKGROUND_PULSANTI);
        skip.setForeground(BLACK);
   
        aggiungiIcona(esci, "/resource/img/icone/icona_skip.png");

        skip.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                skipMouseReleased(evt);
            }
        });
        underPanel.add(skip);
        underPanel.add(textBox);
        


        aggiungiIcona(inventario, "/resource/img/icone/icona_inventario.png");

        inventario.setFont(caricaFontUncial(13f));
        inventario.setPreferredSize(new java.awt.Dimension(175, 30));
        inventario.setForeground(BLACK);
        inventario.setBackground(BACKGROUND_PULSANTI);

        inventario.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventarioMouseClicked(evt);
            }
        });
        underPanel.add(inventario);

        aggiungiIcona(impostazioni, "/resource/img/icone/icona_opzioni.png");

        
        
        impostazioni.setFont(caricaFontUncial(13f));
        impostazioni.setForeground(BLACK);
        impostazioni.setBackground(BACKGROUND_PULSANTI);

        impostazioni.setPreferredSize(new java.awt.Dimension(175, 30));
        impostazioni.addActionListener(e -> new InterfacciaImpostazioni(musica).setVisible(true));

        chrono.start();

        cronometro.setText(chrono.getTimeFormatted());
        cronometro.setForeground(BLACK);
        cronometro.setBackground(WHITE);
        cronometro.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        cronometro.setVerticalAlignment(javax.swing.JLabel.CENTER);
        cronometro.setPreferredSize(new java.awt.Dimension(175, 30));

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            cronometro.setText(chrono.getTimeFormatted());
        }, 0, 1, TimeUnit.SECONDS);

        // Impostazioni stile menuBar
        menuBar.setBackground(WHITE);
        menuBar.setForeground(WHITE);

        // Aggiungi impostazioni a sinistra
        menuBar.add(impostazioni);

        // Spazio flessibile al centro
        menuBar.add(javax.swing.Box.createHorizontalGlue());

        // Cronometro a destra con padding
        javax.swing.JPanel cronometroPanel = new javax.swing.JPanel();
        cronometroPanel.setOpaque(false);
        cronometroPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 2));
        cronometroPanel.add(cronometro);
        menuBar.add(cronometroPanel);


        
        setJMenuBar(menuBar);

        // Avvio musica
        musica.playMusic("/resource/audio/musica_gioco.wav");
        // Imposta il volume della musica
        musica.setVolume(0.3f);
        

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent evt) {
                musica.stopMusica();
            }
        });


        esci.setBackground(BACKGROUND_PULSANTI);
        esci.setForeground(BLACK);
        aggiungiIcona(esci, "/resource/img/icone/icona_esci.png");
        esci.setFont(caricaFontUncial(13f));
        esci.setPreferredSize(new java.awt.Dimension(150, 35));
        esci.addActionListener(e -> {
            int scelta = JOptionPane.showConfirmDialog(
                    null,
                    "Sei sicuro di voler uscire?",
                    "Conferma Uscita",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (scelta == JOptionPane.YES_OPTION) {
                System.exit(0); // chiude il programma
            }
        });
        underPanel.add(esci);


        torna_menu.setBackground(BACKGROUND_PULSANTI);
        torna_menu.setForeground(BLACK);
        torna_menu.setFont(caricaFontUncial(13f));
        torna_menu.setPreferredSize(new java.awt.Dimension(150, 35));

        torna_menu.addActionListener(e -> {
            int scelta = JOptionPane.showConfirmDialog(
                    null,
                    "Sei sicuro di voler tornare al menù principale? \nHai almeno salvato?",
                    "Conferma Uscita",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (scelta == JOptionPane.YES_OPTION) {
                parentFrame.setVisible(true);
                dispose();
            }
        });

        underPanel.add(torna_menu);

        javax.swing.GroupLayout macroPanelLayout = new javax.swing.GroupLayout(macroPanel);
            macroPanel.setLayout(macroPanelLayout);

            macroPanelLayout.setHorizontalGroup(
                macroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, macroPanelLayout.createSequentialGroup()
                        .addGap(120) // margine sinistro
                        .addGroup(macroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(underPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(macroPanelLayout.createSequentialGroup()
                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(200) // distanza centrale (rilegatura)
                                .addComponent(imageViewer, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30)) // margine destro simmetrico
            );

            macroPanelLayout.setVerticalGroup(
                macroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(macroPanelLayout.createSequentialGroup()
                        .addGap(60) // margine superiore
                        .addGroup(macroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imageViewer, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(140)
                        .addComponent(underPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0))
            );

            getContentPane().add(macroPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private void inventarioMouseClicked(java.awt.event.MouseEvent evt) {
        InterfacciaInventario inventario = new InterfacciaInventario(game.getInventario());
        inventario.setVisible(true);
    }

    private void elaborateInput(java.awt.event.ActionEvent evt) {
        String input = textBox.getText().toLowerCase().trim();
        if (!input.isBlank()) {
            textArea.append(input + "\n");
            textBox.setText("");
            ParserOutput p = parser.parse(input.toLowerCase(), game.getInventario().getOggetti(), game.getCurrentCasella().getOggetti(), this);
            game.nextMove(p, stampa);
            if (p.getCommand().getType() == CommandType.MORTE){
                stampa.join(()->morte());          
            } else if(p.getCommand().getType() == CommandType.THE_END){
                stampa.join(()->fineGioco());            
            }
        }
    }


    private void skipMouseReleased(java.awt.event.MouseEvent evt) {
        if (skip.isEnabled()) {
            stampa.interrupt();
            skip.setEnabled(false);
        }
    }


    public void fineGioco() {
        InterfacciaFineGioco InterfacciaFineGioco = new InterfacciaFineGioco(parentFrame, game.getChrono().getElapsedTime());
        InterfacciaFineGioco.setVisible(true);
        dispose();
    }

    public void morte(){
        InterfacciaMorte InterfacciaMorte = new InterfacciaMorte(parentFrame);
        InterfacciaMorte.setVisible(true);
        dispose();
    }

    private ImageIcon getScaledImage(ImageIcon image) {
        return new ImageIcon(image.getImage().getScaledInstance(450, 600, Image.SCALE_DEFAULT));
    }


    public void changeImageViewer(ImageIcon image) {
        imageLabel.setIcon(getScaledImage(image));
    }

    public final Musica getMusica() {
        return musica;
    }

    public Chrono getChrono() {
        return chrono;
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }

    
}