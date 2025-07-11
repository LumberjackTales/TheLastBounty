package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.InputStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import giocatore.Inventario;

public class InterfacciaInventario extends javax.swing.JFrame {
   
    private static final Color WHITE_CUSTOM = new Color(250, 249, 246);
    private static final Color RED_CUSTOM = new Color(238, 75, 43);
    private final Color HEADER_BROWN = new Color(139, 69, 19);

    private javax.swing.JPanel mainPanel;
   
    private JPanel underPanel;
    private javax.swing.JButton close;
    private javax.swing.JTable oggetti;

    public InterfacciaInventario(Inventario inv) {
        initComponents(inv);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(550,455);
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


    private void initComponents(Inventario inv) {

        setTitle(" - Inventario -");

        mainPanel = new javax.swing.JPanel() {
            private java.awt.Image backgroundImage = null;
            {
                java.net.URL imgUrl = getClass().getResource("/resource/img/imm_inventario.png");
                if (imgUrl != null) {
                    backgroundImage = new javax.swing.ImageIcon(imgUrl).getImage();
                }
            }
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, -95, -170,740,735, this);
                }
            }
        };;

        

        String[] column = { "Oggetti", "Quantità"};
        String[][] data = inv.getInventarioToJTableData(); 

        DefaultTableModel nonEditableModel = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        oggetti = new javax.swing.JTable(nonEditableModel);

        // Rendi la tabella trasparente
        oggetti.setOpaque(false);
        oggetti.setForeground(WHITE_CUSTOM);
        oggetti.setBackground(new Color(0,0,0,0)); // trasparente
        oggetti.setSelectionBackground(new Color(0,0,0,0)); // trasparente
        
        //oggetti.getTableHeader().setOpaque(false);
        oggetti.getTableHeader().setBackground(HEADER_BROWN);
        oggetti.getTableHeader().setForeground(Color.BLACK); // colore del testo dell'header
        oggetti.setGridColor(new Color(0,0,0,0));
        oggetti.setIntercellSpacing(new java.awt.Dimension(0, 0)); 

        oggetti.setFont(caricaFontUncial(15f));
        oggetti.getTableHeader().setFont(caricaFontUncial(15f));

        // ...existing code...
        oggetti.setIntercellSpacing(new java.awt.Dimension(0, 0)); // Nessuno spazio tra le celle
        oggetti.setBorder(null); // Nessun bordo per la tabella
        oggetti.setFocusable(false); // Nessun focus border

        // Header senza bordo
        oggetti.getTableHeader().setBorder(null);

        // Crea uno JScrollPane trasparente
        JScrollPane scrollPane = new JScrollPane(oggetti);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(
            new Dimension(450, 200) // Dimensione preferita dello scroll pane
        );
       
        underPanel = new JPanel();
        underPanel.setOpaque(false);

        
        close = new javax.swing.JButton("Chiudi");
        close.setBackground(RED_CUSTOM);
        close.setForeground(WHITE_CUSTOM);
        close.setPreferredSize(new Dimension(80, 30));
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dispose(); 
            }
        });

        
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {}
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                dispose(); 
            }
        });

        
        underPanel.add(close);

        JPanel centerPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 110));
        centerPanel.setOpaque(false); // Trasparente per vedere lo sfondo
        centerPanel.add(scrollPane);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(underPanel, BorderLayout.SOUTH); 

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack(); 
        setLocationRelativeTo(null); 
    }
}
