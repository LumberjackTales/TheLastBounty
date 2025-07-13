package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
        
/**
 * @author Francesco Pio Miccoli
 * @author Leonardo Nicola Marzulli
 * @author Roberto Sivo
 */

 /**
     * interfaccia della schermata dei riconoscimenti del gioco.
     */

public class interfacciaRiconoscimenti extends JFrame {

    private static final Color BLACK_CUSTOM = new Color(32, 32, 35);
    private static final Color WHITE_CUSTOM = new Color(250, 249, 246);
    private static final Color RED_CUSTOM = new Color(238, 75, 43);
 
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JButton closeButton;
    
    /**
     *
     * @param parentFrame
     */
    public interfacciaRiconoscimenti(InterfacciaIniziale parentFrame) {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        RiconoscimentiFrame();
    }

    private void RiconoscimentiFrame() {
        setTitle("Riconoscimenti");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/icone/icona_riconoscimenti.png"));
        setSize(800, 600);
        setLocationRelativeTo(null); 
        setResizable(false);
        setBackground(BLACK_CUSTOM);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                dispose();
            }
        });

    }
    // inizializza i componenti dell'interfaccia riconoscimenti
    private void initComponents() {
        
        mainPanel = new javax.swing.JPanel() {
            private java.awt.Image backgroundImage = null;
            {
                java.net.URL imgUrl = getClass().getResource("/resource/img/riconoscimenti.png");
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

      
       mainPanel.setLayout(new BorderLayout());
     

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(0,0,0,0));
      
        JLabel headerLabel = new JLabel("Riconoscimenti");
        headerLabel.setForeground(WHITE_CUSTOM);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setFont(caricaFontUncial(24f));

        mainPanel.add(headerLabel, BorderLayout.NORTH);
  
       
        JLabel testo = new JLabel("<html>"
                + "<div style='display: inline-block; text-align: center; color: yellow; "
                + "padding: 30px; font-family: sans-serif; font-size: 14px;'>"
                + "<h2 style='margin: 0;'>\n\nGrazie per aver giocato a <span style='color: orange;'>The Last Bounty</span>!</h2>"
                + "<p style='margin: 10px 0;'>Un ringraziamento speciale va a: <b style='color: #cc0000;'>PippoKill</b></p>"
                + "<p style='margin: 10px 0;'>Il nostro team di sviluppo:</p>"
                + "<p style='margin: 4px 0;'>• Francesco Miccoli <i>(Cattivissimo)</i></p>"
                + "<p style='margin: 4px 0;'>• Dino Marzulli</p>"
                + "<p style='margin: 4px 0;'>• Roberto Sivo</p>"
                + "</div></html>");
        testo.setHorizontalAlignment(JLabel.CENTER);
        testo.setFont(caricaFontUncial(20f));
        testo.setForeground(Color.YELLOW);
        testo.setOpaque(true);
        testo.setBackground(new Color(0, 0, 0, 100)); 

        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setOpaque(false);
        textWrapper.add(testo, BorderLayout.CENTER);
        textWrapper.setBorder(javax.swing.BorderFactory.createEmptyBorder(130, 150, 130, 150));

        mainPanel.add(textWrapper, BorderLayout.CENTER);

        
        closeButton = new JButton("Chiudi");
        closeButton.setBackground(RED_CUSTOM);
        closeButton.setForeground(WHITE_CUSTOM);
        closeButton.addActionListener(e -> dispose());


        bottomPanel.add(closeButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }
     private Font caricaFontUncial(float size) {
            try (InputStream is = getClass().getResourceAsStream("/resource/fonts/UncialAntiqua-Regular.otf")) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, is);
                return font.deriveFont(size);
            } catch (Exception e) {
                return new Font("Serif", Font.PLAIN, (int) size); 
            }
    }
         
}

