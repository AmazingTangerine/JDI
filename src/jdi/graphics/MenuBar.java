package jdi.graphics;

import caprica.datatypes.SystemFile;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.Subroutine;
import caprica.system.ThreadRoutine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MenuBar extends JMenuBar {

    private ContentFrame contentFrame;
    
    public MenuBar( ContentFrame contentFrame ){
        
        this.contentFrame = contentFrame;
        
        FileActionListener fileActionListener = new FileActionListener();
        
        JMenu fileMenu = new JMenu( "File" );

        String[] fileTexts = new String[]{ "Open input" , "Save input" , "Open output" , "Save output" , "Run" };
        
        for ( String fileText : fileTexts ){
            
            JMenuItem fileMenuItem = new JMenuItem( fileText );
            fileMenuItem.addActionListener( fileActionListener );
            fileMenu.add( fileMenuItem );
            
        }
        
        this.add( fileMenu );

        
    }
    
    private class FileActionListener implements ActionListener {

        @Override
        public void actionPerformed( ActionEvent event ) {
          
            String buttonText = event.getActionCommand();
            
            if ( buttonText.equals( "Open input" ) ){
                
                openInputFile();
                
            }
            else if ( buttonText.equals( "Save output" ) ){
                
                saveOutputFile();
                
            }
            else if ( buttonText.equals( "Run" ) ){
                
                runFile();
                
            }
            
        }
        
        
    }
    
    private void saveOutputFile(){
        
        SystemFile selectedFile = getFileDialog( this );
        
        if ( selectedFile != null ){
            
            if ( selectedFile.exists() ){
                
                selectedFile.delete();
                
            }
            
            selectedFile.create();
            
            try {
                
                selectedFile.write( contentFrame.getOutputArea().getText() , false );
                
            }
            catch( Exception exception ){}
            
        }
        
    }
    
    private void openInputFile(){
        
        SystemFile selectedFile = getFileDialog( this );
        
        if ( selectedFile != null ){
            
            contentFrame.getInputArea().setText( selectedFile.toString() );
            
        }
        
    }
    

        
    
    
    private void runFile(){
        
        new Subroutine( new DragonExecute() ).runOnce();
        
    }
    
    private SystemFile getFileDialog( JComponent parent ){
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory( new File( System.getProperty( "user.dir" ) + "/dragon/" ) );
        
        int result = fileChooser.showOpenDialog( parent );
        
        if ( result == JFileChooser.APPROVE_OPTION ){

            return new SystemFile( fileChooser.getSelectedFile() );
            
        }
        else {
            
            return null;
            
        }

    }
    
    private SystemFile getSaveFileDialog( JComponent parent ){
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory( new File( System.getProperty( "user.dir" ) + "/dragon/" ) );
        
        int result = fileChooser.showSaveDialog( parent );
        
        if ( result == JFileChooser.APPROVE_OPTION ){

            return new SystemFile( fileChooser.getSelectedFile() );
            
        }
        else {
            
            return null;
            
        }

    }
    
    private class DragonExecute implements ThreadRoutine {

        @Override
        public void run() {

            String inputData = contentFrame.getInputArea().getText();
        
            if ( inputData != null ){
            
                SystemFile tempInputFile = new SystemFile( System.getProperty( "user.dir" ) + "/dragon/temp.inp" );
                SystemFile tempOutputFile = new SystemFile( System.getProperty( "user.dir" ) + "/dragon/temp.txt" );
                
                if ( tempOutputFile.exists() ){
                    
                    tempOutputFile.delete();
                    
                }
                
                if ( tempInputFile.exists() ){
                    
                    tempInputFile.delete();
                    
                }
                
                try {
                
                    tempInputFile.write( inputData , false );
             
                    try {
                        
                        SystemFile tempBatFile = new SystemFile( System.getProperty( "user.dir" ).replace( "\\" , "/" ) + "/dragon/temp.bat" );
                        tempBatFile.write( "@echo off \n dragon\\dragonr.exe <dragon\\temp.inp> dragon\\temp.txt" , false );
                        
                        String dragonCommand = "cmd /C start " + tempBatFile.getFilePath();
           
                        Control.exec( dragonCommand , true );
                        Control.exec( "taskkill /f /im cmd.exe" , true );
                  
                        tempInputFile.delete();
                        tempBatFile.delete();
                    
                    
                        if ( tempOutputFile.exists() ){
                    
                            String outputContent = tempOutputFile.toString();
                            
                            contentFrame.getOutputArea().setText( outputContent );
                            
                            Control.sleep( 1 );
                            
                            boolean deleted = tempOutputFile.delete();
                    
                        }
                        else {
                    
                            //Throw error
                    
                        }
                        
                    }
                    catch( Exception ePrime ){
                        
                        
                    }

                
                }
                catch( Exception exception ){
                
                    //Catch this later
                
                }
            
            }
            
        }
         
    }
    
}
