package jdi.graphics;

import caprica.system.Output;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class HotKeyListener implements KeyListener {

    private ContentFrame contentFrame;
    
    public HotKeyListener( ContentFrame contentFrame ){
        
        this.contentFrame = contentFrame;
        
    }
    
    @Override
    public void keyTyped( KeyEvent event ) {
    
        int code = ( int ) event.getKeyChar();
  
        if ( event.isControlDown() ){
            
            //s = 19
            //o = 15
            
            if ( code == 15 ){
                
                contentFrame.openFile();
                
            }
            
        }
    
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
