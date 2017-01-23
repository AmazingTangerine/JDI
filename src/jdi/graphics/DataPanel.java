package jdi.graphics;

import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DataPanel extends JPanel {

    public DataPanel(){
        
        this.setLayout( new BoxLayout( this , BoxLayout.PAGE_AXIS ) );
        this.setAlignmentX( LEFT_ALIGNMENT );
        
    }
    
    public void displayData( HashMap< String , String > data ){
        
        this.removeAll();
        
        int count = 0;
        int xSpacing = this.getY() / 20;
        
        for ( String key : data.keySet() ){
            
            String datum = data.get( key );
            
            JLabel line = new JLabel();
            line.setText( key + ":  " + datum );
            
            this.add( line );
            
        }
        
    }
    
    public void clear(){
        
        this.removeAll();
        
    }
    
}
