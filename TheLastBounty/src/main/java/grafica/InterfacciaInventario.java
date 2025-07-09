package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import giocatore.Inventario;

public class InterfacciaInventario extends javax.swing.JFrame {

  
    private class ImagePanel extends JPanel {
        private Image backgroundImage;

       
        public ImagePanel(String imagePath) {
           
            ImageIcon icon = new ImageIcon(imagePath);
            this.backgroundImage = icon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

           
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    private final Color BACKGROUND = new Color(237, 232, 208, 200);
    private final Color TEXT_COLOR = new Color(6, 6, 6);
    private final Color HEADER_BROWN = new Color(139, 69, 19);

   
    private JPanel underPanel;
    private javax.swing.JButton close;
    private javax.swing.JTable oggetti;

    public InterfacciaInventario(Inventario inv) {
        initComponents(inv);
    }

    private void initComponents(Inventario inv) {

        setTitle(" - Inventario");
       
        String imagePath = "resource/img/imm_inventario.png"; 

   
        setIconImage(Toolkit.getDefaultToolkit().getImage(imagePath));
        setPreferredSize(new Dimension(600, 550));
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

       
        ImagePanel backgroundPanel = new ImagePanel(imagePath);
        backgroundPanel.setLayout(new BorderLayout()); 
        setContentPane(backgroundPanel); 

        String[] column = { "Oggetti", "Quantità" };
        String[][] data = inv.getInventarioToJTableData(); 

        DefaultTableModel nonEditableModel = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        oggetti = new javax.swing.JTable(nonEditableModel);

     
        oggetti.setOpaque(false); 
        oggetti.setBackground(BACKGROUND); 
        oggetti.setForeground(TEXT_COLOR);
        oggetti.getTableHeader().setBackground(HEADER_BROWN);
        oggetti.getTableHeader().setForeground(Color.WHITE);
        oggetti.setGridColor(HEADER_BROWN);
        oggetti.setRowHeight(30);

        
        oggetti.getColumnModel().getColumn(0).setPreferredWidth(380);
        oggetti.getColumnModel().getColumn(1).setPreferredWidth(70);
        oggetti.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        oggetti.getTableHeader().setResizingAllowed(false);
        oggetti.getTableHeader().setReorderingAllowed(false);

     
        JScrollPane scrollPane = new JScrollPane(oggetti);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); 

       
        underPanel = new JPanel();
        underPanel.setOpaque(false);

        
        close = new javax.swing.JButton("Chiudi");
        close.setBackground(new Color(237, 232, 208));
        close.setForeground(TEXT_COLOR);
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
        backgroundPanel.add(scrollPane, BorderLayout.CENTER); 
        backgroundPanel.add(underPanel, BorderLayout.SOUTH); 

        pack(); 
        setLocationRelativeTo(null); 
    }
}
