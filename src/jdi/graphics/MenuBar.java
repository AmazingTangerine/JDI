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
import java.util.HashMap;
import javax.swing.AbstractButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MenuBar extends JMenuBar {

    private ContentFrame contentFrame;
    
    public MenuBar( ContentFrame contentFrame ){
        
        this.contentFrame = contentFrame;
  
        HashMap< String , Object[] > menuItems = new HashMap<>();

        String[] fileTexts = new String[]{ "Open input" , "Save input" , "Save input as" , "." , "Open output" , "Save output" };
        String[] runTexts = new String[]{ "Run" };
        String[] updateTexts = new String[]{ "Update" , "Auto Update|" };

        menuItems.put( "Files" , new Object[]{ fileTexts , new FileActionListener() } );
        menuItems.put( "Run" , new Object[]{ runTexts , new RunActionListener() } );
        menuItems.put( "Update" , new Object[]{ updateTexts , new UpdateActionListener() } );
        
 
        String[] keyOrder = new String[]{ "Files" , "Run" , "Update" };
        
        for ( String key : keyOrder ){
            
            Object[] dataPair = menuItems.get( key );
            
            String[] menuNames = ( String[] ) dataPair[ 0 ];
            ActionListener listener = ( ActionListener ) dataPair[ 1 ];
            
            JMenu menu = new JMenu( key );
            
            for ( String name : menuNames ){
                
                if ( name.contains( "|" ) ){
                    
                    name = name.replace( "|" , "" );
                    
                    JMenuItem menuItem = new JCheckBoxMenuItem( name );
                    menuItem.addActionListener( listener );
                
                    menu.add( menuItem );
                    
                }
                else if ( name.equals( "." ) ){
                    
                    menu.addSeparator();
                    
                }
                else {
                
                    JMenuItem menuItem = new JMenuItem( name );
                    menuItem.addActionListener( listener );
                
                    menu.add( menuItem );
                
                }
                
            }

            this.add( menu );
            
        }
        
    }
    
    private class UpdateActionListener implements ActionListener {
        
        @Override
        public void actionPerformed( ActionEvent event ) {
          
            String buttonText = event.getActionCommand();
            
            if ( buttonText.equals( "Update" ) ){
                
                contentFrame.refreshInput();
                
            }
            else if ( buttonText.equals( "Auto Update" ) ){
                
                AbstractButton button = ( AbstractButton ) event.getSource();
                boolean selected = button.getModel().isSelected();
                
                contentFrame.setAutoUpdate( selected );
                
            }
        
        }
        
    }
    
    private class RunActionListener implements ActionListener {
        
        @Override
        public void actionPerformed( ActionEvent event ) {
          
            String buttonText = event.getActionCommand();
            
            if ( buttonText.equals( "Run" ) ){
                
                FileHandling.runFile( contentFrame );
                
            }
        
        }
        
    }
    
    private class FileActionListener implements ActionListener {

        @Override
        public void actionPerformed( ActionEvent event ) {
          
            String buttonText = event.getActionCommand();
            
            if ( buttonText.equals( "Open input" ) ){
                
                FileHandling.openInputFile( contentFrame );
                
            }
            else if ( buttonText.equals( "Save input" ) ){
                
                FileHandling.saveInputFile( contentFrame );
                
            }
            else if ( buttonText.equals( "Save input as" ) ){
                
                FileHandling.saveInputFileAs( contentFrame );
                
            }
            else if ( buttonText.equals( "Save output" ) ){
                
                FileHandling.saveOutputFile( contentFrame );
                
            }
            
        }

    }
   
}
