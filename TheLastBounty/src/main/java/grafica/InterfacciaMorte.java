package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
        
public class InterfacciaMorte extends JFrame {

    private static final Color BLACK_CUSTOM = new Color(32, 32, 35);
    private static final Color WHITE_CUSTOM = new Color(250, 249, 246);
    private static final Color RED_CUSTOM = new Color(238, 75, 43);

 
    private final JFrame parentFrame;
    
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JButton tornaMenu;
    

   
    public InterfacciaMorte(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MorteFrame();
    }

    private void MorteFrame() {
        setTitle("Sei morto!");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/icone/icona_morte.png"));
        setSize(400, 300);
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
                java.net.URL imgUrl = getClass().getResource("/resource/img/Sei_Scarso.png");
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


