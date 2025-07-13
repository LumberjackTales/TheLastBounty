package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import componentiaggiuntivi.Musica;
import componentiaggiuntivi.StampaTesto;

/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */
public class InterfacciaImpostazioni extends JFrame {

    private Musica musica;

    private static final Color BLACK_CUSTOM = new Color(32, 32, 35);
    private static final Color WHITE_CUSTOM = new Color(250, 249, 246);
    private static final Color RED_CUSTOM = new Color(238, 75, 43); 
    private static final Color GREEN_CUSTOM = new Color(9, 121, 105); 
    private static final Color BROWN_WARM = new Color(139, 69, 19, 180); 
    private static final Color TEXT_BUTTON_COLOR = Color.WHITE; 

    private JPanel mainPanel;
    private JLabel titleLabel;
    private JPanel controlsPanel; 
    private JPanel musicPanel;
    private JButton musicToggleButton;
    private JPanel volumePanel;
    private JLabel volumeLabel;
    private JButton volumeUpButton;
    private JButton volumeDownButton;
    private JPanel textSpeedPanel;
    private JLabel textSpeedLabel;
    private JButton slowTextButton;
    private JButton mediumTextButton;
    private JButton fastTextButton;
    private JPanel closeButtonPanel;
    private JButton closeButton;
    private JLabel currentVolumeLevel;

    /**
     *
     * @param musica
     */
    public InterfacciaImpostazioni(Musica musica) {
        this.musica = musica;
        initializeFrameSettings();
        initComponents();
        cambiaStatoPulsante();
    }

    private void initializeFrameSettings() {
        setTitle("Impostazioni di Gioco");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resource/img/Capanna_2.0_.png")));
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 450)); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setResizable(false);
        setBackground(BLACK_CUSTOM);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BLACK_CUSTOM);
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
            mainPanel = new javax.swing.JPanel() {
            private java.awt.Image backgroundImage = null;
            {
                java.net.URL imgUrl = getClass().getResource("/resource/img/Capanna_2.0_.png");
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

        titleLabel = new JLabel("Impostazioni", SwingConstants.CENTER);
        titleLabel.setFont(caricaFontUncial(36f));
        titleLabel.setForeground(WHITE_CUSTOM);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        controlsPanel = new JPanel();
        controlsPanel.setLayout(new javax.swing.BoxLayout(controlsPanel, javax.swing.BoxLayout.Y_AXIS));
        controlsPanel.setOpaque(false); 
        controlsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 50, 30, 50));

        setupMusicControls();
        setupVolumeControls();
        setupTextSpeedControls();

        controlsPanel.add(musicPanel);
        controlsPanel.add(javax.swing.Box.createVerticalStrut(30)); 
        controlsPanel.add(volumePanel);
        controlsPanel.add(javax.swing.Box.createVerticalStrut(30)); 
        controlsPanel.add(textSpeedPanel);

        mainPanel.add(controlsPanel, BorderLayout.CENTER);

        closeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        closeButtonPanel.setOpaque(false);
        setupCloseButton();
        closeButtonPanel.add(closeButton);
        mainPanel.add(closeButtonPanel, BorderLayout.SOUTH);

        add(mainPanel); 
    }

    private Font caricaFontUncial(float size) {
        try (InputStream is = getClass().getResourceAsStream("/resource/fonts/UncialAntiqua-Regular.otf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            return new Font("Serif", Font.PLAIN, (int) size); 
        }
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor, float fontSize, Dimension size) {
        button.setFont(caricaFontUncial(fontSize));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false); 
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size); 
    }

    private void setupMusicControls() {
        musicPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        musicPanel.setOpaque(false); 

        JLabel musicLabel = new JLabel("Musica:");
        musicLabel.setFont(caricaFontUncial(20f));
        musicLabel.setForeground(WHITE_CUSTOM);
        musicPanel.add(musicLabel);

        musicToggleButton = new JButton("Mute");
        styleButton(musicToggleButton, BROWN_WARM, RED_CUSTOM, 16f, new Dimension(150, 40));

        try {
            ImageIcon musicIcon = new ImageIcon(new ImageIcon(getClass().getResource("/resource/img/icone/icona_volume.png"))
                    .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            musicToggleButton.setIcon(musicIcon);
        } catch (Exception e) {
            System.err.println("Icona musica non trovata: /resource/img/icone/icona_volume.png");
        }

        musicToggleButton.addActionListener(e -> {
             if (musica != null) {
                 if (musica.isPlaying()) {
                     musica.pausaMusica();
                     musicToggleButton.setText("Play");
                     musicToggleButton.setForeground(GREEN_CUSTOM); 
                 } else {
                     musica.riprendiMusica();
                     musicToggleButton.setText("Mute");
                     musicToggleButton.setForeground(RED_CUSTOM);
                 }
             }
         });

        musicPanel.add(musicToggleButton);
    }

    private void setupVolumeControls() {
        volumePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        volumePanel.setOpaque(false);

        volumeLabel = new JLabel("Volume:");
        volumeLabel.setFont(caricaFontUncial(20f));
        volumeLabel.setForeground(WHITE_CUSTOM);
        volumePanel.add(volumeLabel);

        volumeDownButton = new JButton("-");
        styleButton(volumeDownButton, BROWN_WARM, WHITE_CUSTOM, 20f, new Dimension(60, 40));
        volumeDownButton.addActionListener(e -> {
            musica.setVolume(musica.getVolume() - 0.1f);
            percentualeToIntero();
        });
        volumePanel.add(volumeDownButton);

        currentVolumeLevel = new JLabel();
        percentualeToIntero();
        currentVolumeLevel.setFont(caricaFontUncial(18f));
        currentVolumeLevel.setForeground(WHITE_CUSTOM);
        currentVolumeLevel.setPreferredSize(new Dimension(80, 40));
        currentVolumeLevel.setHorizontalAlignment(SwingConstants.CENTER);
        volumePanel.add(currentVolumeLevel);

        volumeUpButton = new JButton("+");
        styleButton(volumeUpButton, BROWN_WARM, WHITE_CUSTOM, 20f, new Dimension(60, 40));
        volumeUpButton.addActionListener(e -> {
            musica.setVolume(musica.getVolume() + 0.1f);
            percentualeToIntero();
         });
        volumePanel.add(volumeUpButton);
    }

    private void setupTextSpeedControls() {
        textSpeedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        textSpeedPanel.setOpaque(false);

        textSpeedLabel = new JLabel("Velocità Testo:");
        textSpeedLabel.setFont(caricaFontUncial(20f));
        textSpeedLabel.setForeground(WHITE_CUSTOM);
        textSpeedPanel.add(textSpeedLabel);

        slowTextButton = new JButton("Lento");
        styleButton(slowTextButton, BROWN_WARM, TEXT_BUTTON_COLOR, 16f, new Dimension(100, 40));
        slowTextButton.addActionListener(e -> {
            StampaTesto.setAttesa(150);
         });
        textSpeedPanel.add(slowTextButton);

        mediumTextButton = new JButton("Medio");
        styleButton(mediumTextButton, BROWN_WARM, TEXT_BUTTON_COLOR, 16f, new Dimension(100, 40));
        mediumTextButton.addActionListener(e -> {
            StampaTesto.setAttesa(50);
        });
        textSpeedPanel.add(mediumTextButton);

        fastTextButton = new JButton("Veloce");
        styleButton(fastTextButton, BROWN_WARM, TEXT_BUTTON_COLOR, 16f, new Dimension(100, 40));
        fastTextButton.addActionListener(e -> {
            StampaTesto.setAttesa(10);
        });
        textSpeedPanel.add(fastTextButton);
    }

    private void setupCloseButton() {
        closeButton = new JButton("Chiudi");
        styleButton(closeButton, RED_CUSTOM, WHITE_CUSTOM, 20f, new Dimension(150, 50));
        closeButton.addActionListener(e -> dispose());
    }

    private void cambiaStatoPulsante(){
        if (musica != null) {
                 if (!musica.isPlaying()) {
                     musicToggleButton.setText("Play");
                     musicToggleButton.setForeground(GREEN_CUSTOM); 
                 } else {
                     musicToggleButton.setText("Mute");
                     musicToggleButton.setForeground(RED_CUSTOM);
                 }
        }
    }

    /**
     *
     */
    public void percentualeToIntero(){
        int volume = (int) ((musica.getVolume()) * 100);
        currentVolumeLevel.setText(volume + "%");       
    }
}
