package jdi.graphics;

import caprica.datatypes.SystemFile;
import caprica.datatypes.Vector;
import caprica.system.Output;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import jdi.dragon.CodeCoordinator;


public class ContentFrame extends JFrame {
    
    private MenuBar menuBar;
    
    private JTextPane inputTextArea;
    private JTextArea outputTextArea;
    
    private SystemFile lastOpen = null;
    
    private boolean autoUpdate = false;
    private CodeCoordinator encoder = null;
    
    private HotKeyListener keyListener;
    
    public ContentFrame( Vector screenSize ){
        
        super( "Java Dragon Interface" );
        
        this.setPreferredSize( screenSize.asDimension() );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        keyListener = new HotKeyListener( this );
        
        menuBar = new MenuBar( this );
        
        this.setJMenuBar( menuBar );
        
        JTabbedPane tabbedPane = new JTabbedPane();

        inputTextArea = new JTextPane(); //these dimensions doesn't matter
        outputTextArea = new JTextArea( 40 , 100 ); 

        inputTextArea.setFont( new Font( "Consolas" , Font.PLAIN , 14 ) );
        inputTextArea.addKeyListener( keyListener );
        
        outputTextArea.setFont( new Font( "Consolas" , Font.PLAIN , 14 ) );
        outputTextArea.setLineWrap( true );
        outputTextArea.addKeyListener( keyListener );

        outputTextArea.setWrapStyleWord( true );
        
        tabbedPane.addTab( "Input" ,  new JScrollPane( inputTextArea ) );
        tabbedPane.addTab( "Output" ,  new JScrollPane( outputTextArea ) );
        tabbedPane.addKeyListener( keyListener );
        
        this.add( tabbedPane );
        
        this.addKeyListener( keyListener );
        
        this.pack();
        this.setVisible( true );
        
        encoder = new CodeCoordinator( inputTextArea );
        
    }
    
    public void openFile(){
        
        FileHandling.openInputFile( this );
        
    }
    
    public void saveFile(){
        
        FileHandling.saveInputFile( this );
        
    }
    
    public void setEncoder( CodeCoordinator encoder ){
        
        this.encoder = encoder;
        
    }
    
    public CodeCoordinator getEncoder(){
        
        return encoder;
        
    }
    
    public void refreshInput(){
        
        if ( encoder != null ){
            
            encoder.refresh();
            
        }
        
    }
    
    public void setAutoUpdate( boolean autoUpdate ){
        
        this.autoUpdate = autoUpdate;
        
        if ( encoder != null ){
            
            encoder.setAutoUpdate( autoUpdate );
            
        }
        
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
