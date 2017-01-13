package jdi.graphics;

import caprica.datatypes.Vector;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ContentFrame extends JFrame {
    
    public static final double SCREEN_SCALE = 1;
    
    private MenuBar menuBar;
    
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    
    public ContentFrame(){
        
        super( "Java Dragon Interface" );
        
        this.setPreferredSize( new Vector( 1600 , 900 ).scale( SCREEN_SCALE , SCREEN_SCALE ).asDimension() );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        
        
        menuBar = new MenuBar( this );
        
        this.setJMenuBar( menuBar );
        
        JTabbedPane tabbedPane = new JTabbedPane();

        inputTextArea = new JTextArea( 40 , 100 ); //this dimension doesn't matter
        outputTextArea = new JTextArea( 40 , 100 ); 
     
        inputTextArea.setLineWrap( true );
        inputTextArea.setFont( new Font( "Consolas" , Font.PLAIN , 11 ) );
        outputTextArea.setFont( new Font( "Consolas" , Font.PLAIN , 11 ) );
        outputTextArea.setLineWrap( true );
        inputTextArea.setWrapStyleWord( true );
        outputTextArea.setWrapStyleWord( true );
        
        tabbedPane.addTab( "Input" ,  new JScrollPane( inputTextArea ) );
        tabbedPane.addTab( "Output" ,  new JScrollPane( outputTextArea ) );
        
        this.add( tabbedPane );
        
        this.pack();
        this.setVisible( true );
        
    }

    public JTextArea getInputArea(){
    
        return inputTextArea;
        
    }
    
    public JTextArea getOutputArea(){
        
        return outputTextArea;
        
    }
    

}
