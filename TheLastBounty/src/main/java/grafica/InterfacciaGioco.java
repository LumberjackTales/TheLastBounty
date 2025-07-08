/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package grafica;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

import grafica.InterfacciaImpostazioni;

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
    private final String HUNTER = "/resource/img/cacciatore.png";

    private final Color BACKGROUND_BEIGE = new Color(237, 232, 208);
    private final Color BACKGROUND_BLACK = new Color(54, 69, 79);
    private final Color TEXT = new Color(06, 06, 06);
    private final Color WHITE = new Color(250, 249, 246);
    private final Color RED = new Color(238, 75, 43);
    private final Color GREEN = new Color(9, 121, 105);
    private javax.swing.JFrame confermaChiusura;
    private javax.swing.JButton esci;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JPanel imageViewer;
    private javax.swing.JButton inventario;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JPanel macroPanel;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton skip;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField textBox;
    private javax.swing.JPanel underPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem impostazioni;
    private javax.swing.JLabel cronometro;

    Musica musica = new Musica();

    private Parser parser = null;

    private GameDescription game;

    private final StampaTesto stampa;

    private Chrono chrono = new Chrono();

    private final JFrame parentFrame;

    /**
     * Costruttore per una nuova partita.
     * 
     * @param parentFrame Il frame genitore (menu principale)
     * @throws Exception Se si verifica un errore durante l'inizializzazione del gioco
     */
    public InterfacciaGioco(JFrame parentFrame) throws Exception {
        initComponents();
        this.parentFrame = parentFrame;
        stampa = StampaTesto.getInstance(textBox, textArea, skip);
        mainComponents(false, null);
    }

    /**
     * Costruttore per caricare una partita salvata.
     * 
     * @param parentFrame Il frame genitore (menu principale)
     * @param f Il file di salvataggio da caricare
     * @throws Exception Se si verifica un errore durante il caricamento del gioco
     */
    public InterfacciaGioco(JFrame parentFrame, File f) throws Exception {
        initComponents();
        this.parentFrame = parentFrame;
        stampa = StampaTesto.getInstance(textBox, textArea, skip);
        mainComponents(true, f);
    }

    public void mainComponents(boolean loadGame, File f) throws Exception {
        game = new TheLastBounty();
        game.setChrono(chrono);

        try {

            try {
                Set<String> stopwords = Utils.loadFileListInSet(new File("/resource/stopwords/stopwords"));
                parser = new Parser(stopwords, game.getCommands());
            } catch (IOException ex) {
                throw ex;
            }

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
            textArea.append(
                    """
                            \t\t┌──────────────────────────────────┐
                            \t\t│         "The Last Bounty"        │
                            \t\t└──────────────────────────────────┘\n\n
                                    """);
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

        confermaChiusura = new javax.swing.JFrame();
        panel = new javax.swing.JPanel();
        buttonPanelExit = new javax.swing.JPanel();
        jTextArea2 = new javax.swing.JTextArea();
        macroPanel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        scrollPane.setViewportBorder(javax.swing.BorderFactory.createLineBorder(WHITE, 4));
        textArea = new javax.swing.JTextArea();
        scrollPane.scrollRectToVisible(textArea.getVisibleRect());
        imageViewer = new javax.swing.JPanel();
        imageLabel = new javax.swing.JLabel();
        underPanel = new javax.swing.JPanel();
        textBox = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();

        skip = new javax.swing.JButton();
        inventario = new javax.swing.JButton();
        esci = new javax.swing.JButton();

        cronometro = new javax.swing.JLabel();
        impostazioni = new javax.swing.JMenuItem();

        confermaChiusura.setIconImage(
                Toolkit.getDefaultToolkit().getImage("/resource/img/schermata_principale.png"));
        confermaChiusura.setResizable(false);
        confermaChiusura.setSize(new java.awt.Dimension(700, 400));
        confermaChiusura.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                continueGame(evt);
            }
        });

        panel.setBackground(BACKGROUND_BLACK);
        panel.setLayout(new java.awt.BorderLayout());


        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(FONT);
        jTextArea2.setRows(5);
        jTextArea2.setText("\n\n\n\n\n                  Sei sicuro di voler abbandonare? \n\t                  ");
        jTextArea2.setFocusable(false);
        jTextArea2.setBorder(null);
        panel.add(jTextArea2, java.awt.BorderLayout.CENTER);

        confermaChiusura.getContentPane().add(panel, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("The Last Bounty");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/logo_Arcadia.png"));
        setResizable(false);

        macroPanel.setBackground(BACKGROUND_BLACK);

        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAutoscrolls(true);
        scrollPane.setPreferredSize(new java.awt.Dimension(900, 700));

        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(BACKGROUND_BEIGE);
        textArea.setColumns(20);
        textArea.setForeground(TEXT);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(null);
        textArea.setCaretPosition(textArea.getDocument().getLength());
        textArea.setFont(FONT);
        textArea.setBorder(javax.swing.BorderFactory.createLineBorder(WHITE, 4));
        scrollPane.setViewportView(textArea);

        imageViewer.setBackground(WHITE);

        //imageLabel.setIcon(getScaledImage(new ImageIcon(LUCAPG)));
        imageViewer.add(imageLabel);

        underPanel.setBackground(WHITE);
        underPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        textBox.setPreferredSize(new java.awt.Dimension(650, 30));
        textBox.addActionListener(this::elaborateInput);
        underPanel.add(textBox);

        skip.setBackground(BACKGROUND_BEIGE);
        skip.setForeground(TEXT);
        skip.setText("Skip");
        skip.setIcon(new ImageIcon(new ImageIcon("/resource/img/icone/icona_skip.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        skip.setPreferredSize(new java.awt.Dimension(90, 30));
        skip.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                skipMouseReleased(evt);
            }
        });
        underPanel.add(skip);

        inventario.setBackground(BACKGROUND_BEIGE);
        inventario.setForeground(TEXT);
        inventario.setIcon(new ImageIcon(new ImageIcon("/resource/img/icone/icona_inventario.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        inventario.setText("Inventario");
        inventario.setPreferredSize(new java.awt.Dimension(150, 30));
        inventario.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventarioMouseClicked(evt);
            }
        });
        underPanel.add(inventario);

        impostazioni.setBackground(BACKGROUND_BEIGE);
        impostazioni.setForeground(TEXT);
        impostazioni.setIcon(new ImageIcon(new ImageIcon("resource/img/icons/options_icon.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        impostazioni.setText("Impostazioni");
        impostazioni.setPreferredSize(new java.awt.Dimension(105, 30));
        impostazioni.addActionListener(e -> new InterfacciaImpostazioni(musica).setVisible(true));

        chrono.start();

        // JLabel cronometro = new JLabel(chrono.getTimeFormatted());
        cronometro.setText(chrono.getTimeFormatted());
        cronometro.setForeground(TEXT);
        cronometro.setBackground(WHITE);
        cronometro.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        cronometro.setVerticalAlignment(javax.swing.JLabel.CENTER);
        cronometro.setPreferredSize(new java.awt.Dimension(90, 30));

        // Ogni secondo, aggiorna il cronometro con il tempo trascorso
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            cronometro.setText(chrono.getTimeFormatted());
        }, 0, 1, TimeUnit.SECONDS);

        menuBar.add(impostazioni);
        menuBar.add(javax.swing.Box.createHorizontalGlue());
        menuBar.add(cronometro);

        menuBar.setBackground(WHITE);
        menuBar.setForeground(TEXT);
        setJMenuBar(menuBar);

        // Avvio musica
        musica.playMusic("/resource/audio/background_music.wav");
        // Imposta il volume della musica
        musica.setVolume(0.5f);
        // Imposta il loop della musica
        //music.setLoop(true);
        

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent evt) {
                musica.stopMusica();
            }
        });


        esci.setBackground(BACKGROUND_BEIGE);
        esci.setForeground(TEXT);
        esci.setText("Esci");
        esci.setIcon(new ImageIcon(new ImageIcon("resource/img/icone/icona_esci.png").getImage()
                .getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        esci.setPreferredSize(new java.awt.Dimension(90, 30));
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

        javax.swing.GroupLayout macroPanelLayout = new javax.swing.GroupLayout(macroPanel);
        macroPanel.setLayout(macroPanelLayout);
        macroPanelLayout.setHorizontalGroup(
                macroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, macroPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(macroPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(underPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(macroPanelLayout.createSequentialGroup()
                                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 900,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(imageViewer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap()));
        macroPanelLayout.setVerticalGroup(
                macroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(macroPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(macroPanelLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(imageViewer, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(underPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        getContentPane().add(macroPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private void inventarioMouseClicked(java.awt.event.MouseEvent evt) {
        InterfacciaInventario inventario = new InterfacciaInventario(game.getInventario());
        inventario.setVisible(true);
    }

    private void continueGame(java.awt.event.ComponentEvent evt) {
        setEnabled(true);
        setVisible(true);
        confermaChiusura.setVisible(false);
    }

    private void elaborateInput(java.awt.event.ActionEvent evt) {
        String input = textBox.getText().toLowerCase().trim();
        if (!input.isBlank()) {
            textArea.append(input + "\n");
            textBox.setText("");
            ParserOutput p = parser.parse(input.toLowerCase(), game.getInventario().getOggetti(), game.getCurrentCasella().getOggetti(), this);
            game.nextMove(p, stampa);
        }
    }


    private void skipMouseReleased(java.awt.event.MouseEvent evt) {
        if (skip.isEnabled()) {
            stampa.interrupt();
            skip.setEnabled(false);
        }
    }

    private void jButton1goToMenu(java.awt.event.MouseEvent evt) {
        parentFrame.setVisible(true);
        confermaChiusura.dispose();
        dispose();
    }

    private void jButton2dontClose(java.awt.event.MouseEvent evt) {
        setEnabled(true);
        confermaChiusura.setVisible(false);
    }

    private void esciMouseClicked(java.awt.event.MouseEvent evt) {
        setEnabled(false);
        confermaChiusura.setLocationRelativeTo(null);
        confermaChiusura.setTitle("The Last Bounty - Uscita");
        confermaChiusura.setVisible(true);
    }

    public void endGame() {
        InterfacciaFinale interfacciaFinale = new InterfacciaFinale(parentFrame, game.getChrono().getElapsedTime());
        interfacciaFinale.setVisible(true);
        dispose();
    }

    private ImageIcon getScaledImage(ImageIcon image) {
        return new ImageIcon(image.getImage().getScaledInstance(450, 700, Image.SCALE_DEFAULT));
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