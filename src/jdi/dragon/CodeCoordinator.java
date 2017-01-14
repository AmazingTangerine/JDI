package jdi.dragon;

import caprica.drawingtypes.Color;
import caprica.system.Output;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class CodeCoordinator {
    
    private JTextPane textArea;
    
    private static Color BLACK = new Color( 0 , 0 , 0 );
    private static Color VARIABLE = new Color( 0 , 0 , 200 );
    
    public CodeCoordinator( JTextPane textArea ){
        
        this.textArea = textArea;
        
    }
    
    public void processCode( String text ){
        
        String compound = "";
        String individualCompound = "";
        
        for ( char character : text.toCharArray() ){
    
            if ( compound.contains( ":=" ) ){ //Everything after will be variable
               
                if ( character == ':' ){ //End of variable
            
                    append( compound , BLACK );
                    append( individualCompound , VARIABLE );
                    append( ":" , BLACK );
                    
                    individualCompound = "";
                    compound = "";
                    
                }
                else {
                    
                    if ( character != '=' ){
                    
                        individualCompound += character;
                    
                    }
                    
                }
                
            }
            else {
                
                compound += character;
                
            }
            
        }
        
        if ( compound.length() > 0 ){
            
            append( compound , BLACK );
            
        }
        
    }
    
    private void append( String text , Color color ){
        
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet asset = styleContext.addAttribute( SimpleAttributeSet.EMPTY , StyleConstants.Foreground , color.asColor() );

        asset = styleContext.addAttribute( asset , StyleConstants.FontFamily , "Lucida Console" );
        asset = styleContext.addAttribute( asset , StyleConstants.Alignment , StyleConstants.ALIGN_JUSTIFIED );

        int length = textArea.getDocument().getLength();
        
        textArea.setCaretPosition( length );
        textArea.setCharacterAttributes( asset , false );
        textArea.replaceSelection( text );
        
    }
    
}
