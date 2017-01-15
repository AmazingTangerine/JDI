package jdi.dragon;

import caprica.drawingtypes.Color;
import caprica.system.Output;
import caprica.system.Subroutine;
import caprica.system.ThreadRoutine;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class CodeCoordinator {
    
    private JTextPane textArea;
    
    private static Color BLACK = new Color( 0 , 0 , 0 );
    private static Color VARIABLE = new Color( 0 , 0 , 200 );
    
    private static int SELECT_LENGTH = 20;
    
    private TextAreaUpdater textUpdater = null;
    
    private boolean updating = false;
    private boolean autoUpdate = false;
    
    public CodeCoordinator( JTextPane textArea ){
        
        this.textArea = textArea;
        
        this.textArea.setText( "" );
        
        if ( textUpdater != null ){
        
            textArea.getDocument().removeDocumentListener( textUpdater );
        
        }
        
        textUpdater = new TextAreaUpdater();
        
        textArea.getDocument().addDocumentListener( textUpdater );
        
    }
    
    public void setAutoUpdate( boolean autoUpdate ){
        
        this.autoUpdate = autoUpdate;
        
    }
    
    public void refresh(){
        
        String currentText = textArea.getText();
        
        textArea.setText( "" );
        
        processCode( currentText );

    }
    
    public void processCode( String text ){
 
        updating = true;
        
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
        
        updating = false;
        
    }
    
    private void append( String text , Color color ){
        
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet asset = styleContext.addAttribute( SimpleAttributeSet.EMPTY , StyleConstants.Foreground , color.asColor() );

        asset = styleContext.addAttribute( asset , StyleConstants.FontFamily , "Lucida Console" );
        asset = styleContext.addAttribute( asset , StyleConstants.Alignment , StyleConstants.ALIGN_JUSTIFIED );

        int length = textArea.getCaretPosition();
        
        textArea.setCaretPosition( length );
        textArea.setCharacterAttributes( asset , false );
        textArea.replaceSelection( text );
        
        textArea.setCaretPosition( length + text.length() );
        
    }
    
    private class TextUpdate implements ThreadRoutine {

        private DocumentEvent event;
        
        private TextUpdate( DocumentEvent event ){
            
            this.event = event;
            
        }
        
        @Override
        public void run() {

            updating = true;
            
            String text = textArea.getText();
        
            int offset = event.getOffset();
            
            int start = offset - SELECT_LENGTH;
            int end = offset + SELECT_LENGTH;
            
            if ( start < 1 ){
                
                start = 1;
                
            }
            
            if ( end >= text.length() ){
                
                end = text.length() - 1;
                
            }
            
            textArea.select( start , end );
            
            String selectedText = textArea.getSelectedText();
            
            textArea.replaceSelection( "" );
            textArea.setCaretPosition( start );

            processCode( selectedText );
            
            //textArea.replaceSelection( selectedText );
            
            textArea.setCaretPosition( offset + 1 );
            
            updating = false;
            
        }
            
    }
    
    private class TextAreaUpdater implements DocumentListener {

        @Override
        public void insertUpdate( DocumentEvent event ) { 
          
            if ( !updating && autoUpdate ){
                
                new Subroutine( new TextUpdate( event ) ).runOnce();
      
            }
            
        }

        @Override
        public void removeUpdate(DocumentEvent e) {}

        @Override
        public void changedUpdate( DocumentEvent event ) {}
 
    }
    
}
