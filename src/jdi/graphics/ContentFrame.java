package jdi.graphics;

import caprica.datatypes.SystemFile;
import caprica.datatypes.Vector;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class ContentFrame extends JFrame {
    
    private MenuBar menuBar;
    
    private JTextPane inputTextArea;
    private JTextArea outputTextArea;
    
    private SystemFile lastOpen = null;
    
    public ContentFrame( Vector screenSize ){
        
        super( "Java Dragon Interface" );
        
        this.setPreferredSize( screenSize.asDimension() );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        
        
        menuBar = new MenuBar( this );
        
        this.setJMenuBar( menuBar );
        
        JTabbedPane tabbedPane = new JTabbedPane();

        inputTextArea = new JTextPane(); //these dimensions doesn't matter
        outputTextArea = new JTextArea( 40 , 100 ); 
     
        inputTextArea.setFont( new Font( "Consolas" , Font.PLAIN , 11 ) );
        outputTextArea.setFont( new Font( "Consolas" , Font.PLAIN , 11 ) );
        outputTextArea.setLineWrap( true );

        outputTextArea.setWrapStyleWord( true );
        
        tabbedPane.addTab( "Input" ,  new JScrollPane( inputTextArea ) );
        tabbedPane.addTab( "Output" ,  new JScrollPane( outputTextArea ) );
        
        this.add( tabbedPane );
        
        this.pack();
        this.setVisible( true );
        
    }

    public void setLastOpenedFile( SystemFile openedFile ){
        
        this.lastOpen = openedFile;
        
    }
    
    public SystemFile getLastOpenedFile(){
        
        return this.lastOpen;
        
    }
    
    public JTextPane getInputArea(){
    
        return inputTextArea;
        
    }
    
    public JTextArea getOutputArea(){
        
        return outputTextArea;
        
    }
    

}
