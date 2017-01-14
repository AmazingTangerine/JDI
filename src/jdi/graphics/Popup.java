package jdi.graphics;

import caprica.system.Output;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Popup {
    
    public static void infoPopup( ContentFrame parent , String message ){
        
        JOptionPane.showMessageDialog( parent , message );
        
        Output.print( message );
        
    }
    
    public static void errorPopup( ContentFrame parent , String message ){
        
        JOptionPane.showMessageDialog( parent , message , "Error" , JOptionPane.ERROR_MESSAGE );
        
        Output.print( message );
        
    }
    
}
