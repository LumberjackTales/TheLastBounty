/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 
package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    private JButton musicToggleButton;
    private JLabel volumeLabel;
    private JButton volumeUpButton;
    private JButton volumeDownButton;
    private JPanel textSpeedPanel;
    private JLabel textSpeedLabel;
    private JButton slowTextButton;
    private JButton mediumTextButton;
    private JButton fastTextButton;

   
    public interfacciaRiconoscimenti(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        RiconoscimentiFrame();
    }

    
    private void RiconoscimentiFrame() {
        setTitle("Riconoscimenti");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resource/img/Capanna.png"));
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
    };

*/