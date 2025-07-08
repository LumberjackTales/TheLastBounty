package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
        
public class interfacciaRiconoscimenti extends JFrame {

    private static final Color BLACK_CUSTOM = new Color(32, 32, 35);
    private static final Color WHITE_CUSTOM = new Color(250, 249, 246);
    private static final Color TEXT_COLOR = new Color(06, 06, 06);
    private static final Color RED_CUSTOM = new Color(238, 75, 43);
    private static final Color GREEN_CUSTOM = new Color(9, 121, 105);
    private static final Color BROWN_WARM= new Color(139,69,19);
 
    private final JFrame parentFrame;
    
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JButton closeButton;
    

   
    public interfacciaRiconoscimenti(InterfacciaIniziale parentFrame) {
        this.parentFrame = parentFrame;
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
    
    private void initComponents() {
        //mainPanel = new JPanel(new BorderLayout());
       // mainPanel.setBackground(BLACK_CUSTOM); 
        mainPanel = new javax.swing.JPanel() {
            private java.awt.Image backgroundImage = null;
            {
                java.net.URL imgUrl = getClass().getResource("/resource/img/Lago_Ninfa.png");
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

      //  mainPanel = new JPanel();
       mainPanel.setLayout(new BorderLayout());
        //mainPanel.setBackground(BLACK_CUSTOM);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(BLACK_CUSTOM);
              
        JLabel headerLabel = new JLabel("Riconoscimenti");
        headerLabel.setForeground(WHITE_CUSTOM);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setFont(caricaFontUncial(24f));

        mainPanel.add(headerLabel, BorderLayout.NORTH);
  
 
        JLabel contentLabel = new JLabel("<html><body style='text-align: center; color: " + String.format("#%02x%02x%02x", TEXT_COLOR.getRed(), TEXT_COLOR.getGreen(), TEXT_COLOR.getBlue()) + ";'>"
                + "<p>Grazie per aver giocato a The Last Bounty!</p>"
                + "<p>Un ringraziamento speciale va a:  PippoKill </p>"
                + "<ul>"
                + "<li>Il nostro team di sviluppo : Francesco Miccoli Cattivissimo , Dino Marzulli , Roberto Sivo </li>"
                + "</ul>"
                + "</body></html>");
        contentLabel.setHorizontalAlignment(JLabel.CENTER);
        contentLabel.setFont(caricaFontUncial(30f));
        contentLabel.setForeground(BLACK_CUSTOM);

        mainPanel.add(contentLabel, BorderLayout.CENTER);

        // Close Button
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
                return new Font("Serif", Font.PLAIN, (int) size); // fallback
            }
    }
         
}

