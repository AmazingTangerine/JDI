/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdi.graphics;

import caprica.datatypes.SystemFile;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.Subroutine;
import caprica.system.ThreadRoutine;
import java.io.File;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import jdi.dragon.CodeCoordinator;
import jdi.dragon.DataExtraction;

/**
 *
 * @author Julia
 */
public class FileHandling {
    
    static void saveInputFile( ContentFrame contentFrame ){
        
        if ( contentFrame.getLastOpenedFile() != null ){
            
            SystemFile tempFile = new SystemFile( contentFrame.getLastOpenedFile().getFilePath().replace( contentFrame.getLastOpenedFile().getFileType() , "tmp" ) );
            
            if ( tempFile.exists() ){
                
                tempFile.delete();
                
            }
            
            tempFile.create();
            
            try {
                
                tempFile.write( contentFrame.getEncoder().getActualText() , false );
                
                String filePath = contentFrame.getLastOpenedFile().getFilePath();
                
                contentFrame.getLastOpenedFile().delete();
                
                tempFile.rename( filePath );
                
                contentFrame.setLastOpenedFile( tempFile );
                
            }
            catch( Exception exception ){
            
                Popup.errorPopup( contentFrame , "Could not save file" );
            
            }
            
            tempFile.delete();
            
        }
        else {
            
            Popup.errorPopup( contentFrame , "No active file" );
            
        }
        
    }
    
    static void saveInputFileAs( ContentFrame contentFrame ){
        
        SystemFile selectedFile = getSaveFileDialog( contentFrame );
        
        if ( selectedFile != null ){
            
            if ( selectedFile.exists() ){
                
                selectedFile.delete();
                
            }
            
            selectedFile.create();
            
            try {
                
                selectedFile.write( contentFrame.getEncoder().getActualText() , false );
                
                contentFrame.setLastOpenedFile( selectedFile );
                
            }
            catch( Exception exception ){
            
                Popup.errorPopup( contentFrame , "Could not save file" );
            
            }
            
        }
        
    }
    
    static void saveOutputFile( ContentFrame contentFrame ){
        
        SystemFile selectedFile = getSaveFileDialog( contentFrame );
        
        if ( selectedFile != null ){
            
            if ( selectedFile.exists() ){
                
                selectedFile.delete();
                
            }
            
            selectedFile.create();
            
            try {
                
                selectedFile.write( contentFrame.getOutputArea().getText() , false );
                
            }
            catch( Exception exception ){
            
                Popup.errorPopup( contentFrame , "Could not save output file" );
            
            }
            
        }
        
    }
    
    static void openInputFile( ContentFrame contentFrame ){
        
        SystemFile selectedFile = getFileDialog( contentFrame );
        
        if ( selectedFile != null ){
            
            contentFrame.setLastOpenedFile( selectedFile );
            
            String data = selectedFile.toString();

            //contentFrame.getInputArea().setText( data );
            
            contentFrame.getEncoder().processCode( data );
            
        }
        
    }
    
    static void runFile( ContentFrame contentFrame ){
        
        new Subroutine( new DragonExecute( contentFrame ) ).runOnce();
        
    }
    
    private static SystemFile getFileDialog( ContentFrame contentFrame ){
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory( new File( System.getProperty( "user.dir" ) + "/dragon/" ) );
        
        int result = fileChooser.showOpenDialog( contentFrame );
        
        if ( result == JFileChooser.APPROVE_OPTION ){

            return new SystemFile( fileChooser.getSelectedFile() );
            
        }
        else {
            
            return null;
            
        }

    }
    
    private static SystemFile getSaveFileDialog( ContentFrame contentFrame ){
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory( new File( System.getProperty( "user.dir" ) + "/dragon/" ) );
        
        int result = fileChooser.showSaveDialog( contentFrame );
        
        if ( result == JFileChooser.APPROVE_OPTION ){

            return new SystemFile( fileChooser.getSelectedFile() );
            
        }
        else {
            
            return null;
            
        }

    }
    
    private static class DragonExecute implements ThreadRoutine {

        ContentFrame contentFrame;
        
        private DragonExecute( ContentFrame contentFrame ){
            
            this.contentFrame = contentFrame;
            
        }
        
        @Override
        public void run() {

            String inputData = contentFrame.getEncoder().getActualText();
      
            if ( inputData != null ){

                this.contentFrame.getDataPanel().clear();
                
                String[] deletes = new String[]{ "picture.ps" , "res" , "TRKBIN" , "picture.pdf" };
                        
                for ( String delete : deletes ){
                    
                    new SystemFile( System.getProperty( "user.dir" ).replace( "\\" , "/" ) + "/" + delete ).delete();
                    new SystemFile( System.getProperty( "user.dir" ).replace( "\\" , "/" ) + "/dragon/" + delete ).delete();
                                
                }
                
                boolean worked = false;
                
                for ( int i = 0 ; i < 3 ; i++ ){
                
                    SystemFile tempInputFile = new SystemFile( System.getProperty( "user.dir" ).replace( "\\" , "/" ) + "/dragon/temp.inp" );
                    SystemFile tempOutputFile = new SystemFile( System.getProperty( "user.dir" ).replace( "\\" , "/" ) + "/dragon/temp.txt" );
   
                    if ( tempOutputFile.exists() ){
                    
                        tempOutputFile.delete();
                    
                    }
                
                    if ( tempInputFile.exists() ){
                    
                        tempInputFile.delete();
                    
                    }
                
                    try {
                
                        tempInputFile.write( inputData , false );
             
                        SystemFile tempBatFile = new SystemFile( System.getProperty( "user.dir" ).replace( "\\" , "/" ) + "/dragon/temp.bat" );
            
                        try {
     
                            tempBatFile.write( "@echo off \n dragon\\dragon304s.exe <dragon\\temp.inp> dragon\\temp.txt" , false );
                        
                            String dragonCommand = "cmd /C start " + tempBatFile.getFilePath();
           
                            Control.exec( dragonCommand , true );
                            Control.exec( "taskkill /f /im cmd.exe" , true );
                  
                            tempInputFile.delete();
                            tempBatFile.delete();
                   
                            if ( tempOutputFile.exists() ){
                    
                                String outputContent = tempOutputFile.toString();
                            
                                contentFrame.getOutputArea().setText( outputContent );
                            
                                contentFrame.getDataPanel().displayData( DataExtraction.extractData( outputContent ) );
                                
                                Control.sleep( 1.5 );
                            
                                boolean deleted = tempOutputFile.delete();
                                
                                worked = true;
                                
                                break;
                    
                            }
                            
                        }
                        catch( Exception ePrime ){
                        
                            Popup.errorPopup( contentFrame , "Could not create batch file" );
                        
                            tempBatFile.delete();
                 
                            break;
                            
                        }
                
                    }
                    catch( Exception exception ){
                
                        Popup.errorPopup( contentFrame , "Could not write temporary file" );
                    
                        tempInputFile.delete();
                
                    }
            
                }

                if ( !worked ){
                    
                    Popup.errorPopup( contentFrame , "DRAGON could not run file" );
                    
                }
            
            }
            else {
                
                Popup.errorPopup( contentFrame , "No input data to run" );
                
            }
            
        }
         
    }
    
}
 