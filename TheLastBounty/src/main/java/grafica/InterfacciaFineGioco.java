package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import classifica.Client;
import classifica.Record;

import componentiaggiuntivi.Musica;
        
public class InterfacciaFineGioco extends JFrame {

    private static final Color BLACK_CUSTOM = new Color(32, 32, 35);
    private static final Color WHITE_CUSTOM = new Color(250, 249, 246);
    private static final Color RED_CUSTOM = new Color(238, 75, 43);
    private Musica musica = new Musica();

 
    private final JFrame parentFrame;
    
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JButton tornaMenu;
    

   
    public InterfacciaFineGioco(JFrame parentFrame, long tempo) {
        this.parentFrame = parentFrame;
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());

        String giocatore = System.getProperty("user.name");

        Client client = new Client();
        Record record = new Record(giocatore, tempo, formatted);
        try {
            client.sendRecord(record);
            musica.playMusic("/resource/audio/fineGioco.wav");
        } catch (Exception e) {
            JButton okButton = new JButton("Ok");
            okButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    dispose();
                    musica.playMusic("/resource/audio/fineGioco.wav");
                }
            });
            JOptionPane info = new JOptionPane(e.getMessage(), JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, null, new Object[] { okButton });
            info.createDialog(InterfacciaFineGioco.this, "Info").setVisible(true);

        }
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FineGiocoFrame();
    }

    private void FineGiocoFrame() {
        setTitle("Fine del gioco");
        setIconImage(Toolkit.getDefaultToolkit().getImage("/resource/img/schermata_principale.png"));
        setSize(900, 900);
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
        
        mainPanel = new javax.swing.JPanel() {
            private java.awt.Image backgroundImage = null;
            {
                java.net.URL imgUrl = getClass().getResource("/resource/img/Vittoria.png");
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
        bottomPanel.setBackground(BLACK_CUSTOM);
              

        // Torna al menu Button
        tornaMenu = new JButton("Torna al menu");
        tornaMenu.setBackground(RED_CUSTOM);
        tornaMenu.setForeground(WHITE_CUSTOM);
        tornaMenu.addActionListener(e -> dispose());

        bottomPanel.add(tornaMenu);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
        tornaMenu.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

    }
         
}


