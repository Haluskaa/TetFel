/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetfel;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Administrator
 * 
 */
class SzamJTextField extends JTextField{
   public SzamJTextField(int columns){
      super(columns);
      enableEvents(AWTEvent.KEY_EVENT_MASK);
   }
   @Override
   public void processKeyEvent(KeyEvent e){
     if (e.getID() == KeyEvent.KEY_TYPED){
       char c =e.getKeyChar();
       if ((c < '0' || c >'9') & (c != KeyEvent.VK_BACK_SPACE)) e.consume();
     }
     super.processKeyEvent(e);
   }
}

class Felulet extends JPanel{
      public String nev="";
      public String tetel="";
   Felulet(){  
       setBackground(Color.white);
   }
   @Override
        public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 =(Graphics2D )g;
        g2.setFont(new Font("Times New Roman", Font.BOLD, 60));
        FontMetrics ft =g2.getFontMetrics();
        ft =g2.getFontMetrics();
        int szeles = ft.stringWidth(String.valueOf(nev));
        if(szeles>getWidth()) szeles=getWidth();
        g2.setPaint(Color.BLACK);
        g2.drawString(nev,getSize().width/2-szeles/2,60);
        g2.setPaint(Color.red);
        g2.drawString(tetel,getSize().width/2-szeles/2,getHeight()/2+5);
        }
 }
public class TetFel extends JFrame implements ActionListener{

    /**
     * @param args the command line arguments
     */
    Vector nevek= new Vector();
    Container cp=getContentPane();
    JButton sorsol=new JButton("Kisorsol"),beolvas=new JButton("Beolvas");
    SzamJTextField menyitol=new SzamJTextField(5),meddig=new SzamJTextField(5);
    Felulet f=new Felulet();
    File be;
    TetFel(){
        setLocation(100,100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("TételFeleltetős - készítette Csernok László");
        setSize(900,600);
        beolvas(new File("nev.txt"));
        letrehoz();
        show();
    }
    void letrehoz(){
        JPanel jp=new JPanel();
        jp.setLayout(new BorderLayout());
        JPanel neves=new JPanel();
        neves.setLayout(new BoxLayout(neves, BoxLayout.Y_AXIS));
        for(int i=0;i<nevek.size();i++){
            JCheckBox nev=(JCheckBox)nevek.get(i);
            nev.setBackground(new Color(82,163,139));
            neves.add(nev);
        }
        JScrollPane scr=new JScrollPane(neves);
        JPanel gombok=new JPanel();
        gombok.setLayout(new FlowLayout());
        gombok.setBackground(new Color(82,144,139));
        neves.setBackground(new Color(82,163,139));
        beolvas.addActionListener(this);
        sorsol.addActionListener(this);
        gombok.add(new JLabel("Ettöl"));
        gombok.add(menyitol);
        gombok.add(new JLabel("Eddig"));
        gombok.add(meddig);
        neves.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.lightGray,Color.lightGray));
        gombok.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.lightGray,Color.lightGray));
        menyitol.setText("1");
        meddig.setText("30");
        gombok.add(beolvas);
        gombok.add(sorsol);
        jp.add(scr,"West");
        jp.add(gombok,"North");
        jp.add(f,"Center");
        cp.add(jp);
        show();
    }
    void beolvas(File f){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"Windows-1250"));
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(TetFel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sor;
        try {
            while((sor=br.readLine())!=null){
               JCheckBox jb=new JCheckBox(sor,true);
               nevek.add(jb);
            }
        } catch (IOException ex) {
            Logger.getLogger(TetFel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(TetFel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void betoltes(){
        JFileChooser jf;
      if (be!=null){
       jf = new JFileChooser(be);
      }else{
        jf = new JFileChooser();
      }  
       if( jf.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            nevek.removeAllElements();
            cp.removeAll();
            beolvas.removeActionListener(this);
            sorsol.removeActionListener(this);
            beolvas(jf.getSelectedFile());
            show();
            letrehoz();
           
       }
       be=jf.getCurrentDirectory();
       
    }
    void kisorsol(){
        String ki="";
        Random r = new Random();
        Vector v=new Vector();
        for(int i=0;i<nevek.size();i++){
            JCheckBox h=(JCheckBox) nevek.get(i);
            if(h.isSelected()){
                v.add(h);
            }
        }
        int i=r.nextInt(v.size());
        int mibo=r.nextInt(Integer.parseInt(meddig.getText())+1-Integer.parseInt(menyitol.getText()));
        mibo+=Integer.parseInt(menyitol.getText());
        JCheckBox h=(JCheckBox)v.get(i);
        ki="FELEL: "+h.getText();
        f.nev=ki;
        f.tetel="TÉTEL: "+String.valueOf(mibo);
        f.repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==beolvas){
            betoltes();
            
            
        }
        if(e.getSource()==sorsol){
            kisorsol();
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        TetFel tetFel = new TetFel();
    }

    
    
}
