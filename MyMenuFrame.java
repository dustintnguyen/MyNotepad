//Dustin Nguyen
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;


public class MyMenuFrame extends JFrame{
    
    private final JRadioButtonMenuItem[] fonts;
    private final JCheckBoxMenuItem[] styleItems; 
    private final JTextArea TextArea;
    private final ButtonGroup fontButtonGroup;
    private int style;
    //to open webpage for homepage
    public static void openWebpage (String urlString) {
        try {
            Desktop.getDesktop().browse(new
            URL(urlString).toURI());
        }
            catch (IOException | URISyntaxException e) {
                e.printStackTrace();
 }
}
    
    public MyMenuFrame(){
        
        //Set title 
        super("MyNotepad");
        //file menu
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        //Open menuitem
        JMenuItem open = new JMenuItem ("Open");
        open.setMnemonic('O');
        open.setAccelerator(KeyStroke.getKeyStroke('O',CTRL_DOWN_MASK));
        fileMenu.add(open);
        fileMenu.addSeparator();
        open.addActionListener(
                //actionlistener for file chooser
            new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //choose file window
                JFileChooser fc = new JFileChooser();
                int i = fc.showOpenDialog(MyMenuFrame.this);
        
                if (i == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
            
                    try (Scanner scanner= new Scanner (new BufferedReader(new FileReader(file)))){
                        String content = "";
                        while(scanner.hasNextLine()){
                            content += scanner.nextLine() + "\n";
                }
                        TextArea.setText(content);
                    } catch(FileNotFoundException ex){
                        Logger.getLogger(MyMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
            }
                
            }
        );
        
                
        //Save menu item
        JMenuItem save = new JMenuItem("Save");
        save.setMnemonic('S');
        save.setAccelerator(KeyStroke.getKeyStroke('S',CTRL_DOWN_MASK));
        fileMenu.add(save);
        fileMenu.addSeparator();
        //save action listner to save file and open location window
        save.addActionListener(
            new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // save file
                JFileChooser fc = new JFileChooser();
                int answer = fc.showSaveDialog(MyMenuFrame.this);
        
                if(answer ==JFileChooser.APPROVE_OPTION){
                    String path = fc.getSelectedFile().getPath();
                    try(FileWriter writer = new FileWriter(path)){
                        writer.write(TextArea.getText());
                    }catch(IOException ex){
                        Logger.getLogger(MyMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            }
                
    
            });
        
        //Exit menu item
        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic('X');
        exit.setAccelerator(KeyStroke.getKeyStroke('X',CTRL_DOWN_MASK));
        fileMenu.add(exit);
        //add action to exit app 
        exit.addActionListener(
                new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }
                }
        );
        
        //make menu bar 
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);// add to notepad app
        bar.add(fileMenu);
        
        //create edit menu
        JMenu edit = new JMenu("Edit");
        edit.setMnemonic('D');
        //create color menu
        JMenu color = new JMenu("Color");
        color.setMnemonic('C');
        edit.add(color);
        edit.addSeparator();
                
        //create color change menu item
        JMenuItem color_change = new JMenuItem("Change Color");
        color_change.setAccelerator(KeyStroke.getKeyStroke('C',CTRL_DOWN_MASK));
        //add to the color menu item
        color.add(color_change);
        //add action listener to pop up color chooser
        color_change.addActionListener(
                new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // change color font
                Color color = JColorChooser.showDialog(MyMenuFrame.this, "Select a color", Color.RED);
                TextArea.setForeground(color);
            }
                }
        );
        
        //create font menu and list array of fonts
        String[] fontNames = {"Times New Roman","Arial","Serif"};
        JMenu font = new JMenu("Font");
        font.setMnemonic('F');
        //create radio buttons of fonts
         fonts = new JRadioButtonMenuItem[fontNames.length];
         fontButtonGroup = new ButtonGroup();
         ItemHandler itemHandler = new ItemHandler();
         
         //display the radio buttons
         for (int count = 0; count<fonts.length;count++){
             fonts[count] = new JRadioButtonMenuItem(fontNames[count]);
             font.add(fonts[count]);
             fontButtonGroup.add(fonts[count]);
             fonts[count].addActionListener(itemHandler);//add item handler
         }
         fonts[0].setSelected(true);//select first font of array
         font.addSeparator();
         
         
         //list of sytles
         String[] styleNames = {"Bold", "Italic"};
         styleItems = new JCheckBoxMenuItem[styleNames.length];
         StyleHandler styleHandler = new StyleHandler();
         
         //create style checkbox menu items
         for (int count=0;count<styleNames.length;count++){
             styleItems[count] = new JCheckBoxMenuItem(styleNames[count]);
             font.add(styleItems[count]);//add to font menu 
             styleItems[count].addItemListener(styleHandler);
         }
         
        edit.add(font);//add font menu to edit menu
        bar.add(edit);//add edit menu to the bar menu
        
        //create print menu items
        JMenu print = new JMenu("Print");
        print.setMnemonic('P');
        
        JMenuItem send_to_printer = new JMenuItem("Send to printer");
        send_to_printer.setAccelerator(KeyStroke.getKeyStroke('P',CTRL_DOWN_MASK));
        send_to_printer.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // displays print option
                int n = JOptionPane.showOptionDialog(MyMenuFrame.this, "Do you want to print this file?", "Confirmation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null,null,null);
                if (n == 0 ){
                    //displays success message
                    JOptionPane.showMessageDialog(MyMenuFrame.this, "The file was successfully printed", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
         }
            }
        }
        );
        //add menus
        print.add(send_to_printer);
        bar.add(print);
        
        //create help menu
        JMenu help = new JMenu("Help");
        help.setMnemonic('H');
        //create about menu item
        JMenuItem about = new JMenuItem("About");
        about.setAccelerator(KeyStroke.getKeyStroke('A',CTRL_DOWN_MASK));
        about.addActionListener(
                new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //pops about message 
                JOptionPane.showMessageDialog(MyMenuFrame.this, "This software was developed in 2019 \n Version is 1.0", "About", JOptionPane.INFORMATION_MESSAGE);
            }
                    
                }
        );
        //add to help menu
        help.add(about);
        help.addSeparator();
        
        //create visit homepage
        JMenuItem visit_HP = new JMenuItem("Visit Homepage");
        visit_HP.setAccelerator(KeyStroke.getKeyStroke('v',CTRL_DOWN_MASK));
        visit_HP.addActionListener(
                new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //open webpage on web bowser
                openWebpage( "http://www.microsoft.com");
            }
                }
        );
        help.add(visit_HP);
        bar.add(help);
        TextArea = new JTextArea();
        TextArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(TextArea, BorderLayout.CENTER);
        
        }
    
   //innerclass to handle item listener
   private class ItemHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //process to selected fonts
            for (int count=0;count<fonts.length;count++){
                if (e.getSource()==fonts[count]){
                    TextArea.setFont(new Font(fonts[count].getText(),style, 20));
                }
            }
            repaint();
        }
       
   }//end itemhandler
   //inner class to handle stylehandler
   private class StyleHandler implements ItemListener{
       //process to handle style of font
        @Override
        public void itemStateChanged(ItemEvent e) {
           String name = TextArea.getFont().getName();
           Font font;
           //determine which check box are selected
           if(styleItems[0].isSelected() && 
                styleItems[1].isSelected())
    			font = new Font(name, Font.BOLD +Font.ITALIC, 20);
    		else if (styleItems[0].isSelected())
    			font = new Font(name, Font.BOLD, 20);
    		else if (styleItems[1].isSelected())
    			font = new Font(name,Font.ITALIC, 20);
    		else
    			font = new Font(name, Font.PLAIN, 20);
    		TextArea.setFont(font);
    		repaint();//redraw on textarea
        }
   }

   
   public static void main(String[] arg){
       MyMenuFrame m = new MyMenuFrame();
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setSize(600,400);
        m.setVisible(true);
   }
}
