package jdi.main;

import caprica.datatypes.Vector;
import caprica.system.Output;
import caprica.system.SystemInformation;
import jdi.graphics.ContentFrame;

public class Main {

    public static void main( String[] arguments ) {

        String operatingDirectory = System.getProperty( "user.dir" );
        
        Vector screenSize = new Vector( 1600 , 900 );
        Vector actualSize = SystemInformation.screenSize();
        
        double reduction = 1;
        
        while ( actualSize.getX().less( screenSize.getX() ) || actualSize.getY().less( screenSize.getY() ) ){
            
            reduction -= 0.1;
            
            screenSize = screenSize.scale( reduction , reduction );
            
        }
        
        new ContentFrame( screenSize );
        
    }
    
}
