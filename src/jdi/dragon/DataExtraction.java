package jdi.dragon;

import caprica.datatypes.Num;
import caprica.system.Output;
import java.util.HashMap;


public class DataExtraction {

    public static HashMap< String , String > extractData( String rawData ){
        
        HashMap< String , String > data = new HashMap<>();
        
        for ( String line : rawData.split( "\n" ) ){
            
            if ( line.contains( "K-EFFECTIVE" ) ){
                
                String kEffectiveRaw = line.split( "=" )[ 1 ].trim();
                
                double kEffective = Double.parseDouble( kEffectiveRaw );
                
                data.put( "K-EFFECTIVE" , "" + kEffective );
                
            }
            if ( line.contains( "K-INFINITE" ) ){
                
                String kInfiniteRaw = line.split( "=" )[ 1 ].trim();
                
                double kInfinite = Double.parseDouble( kInfiniteRaw );
                
                data.put( "K-INFINITE" , "" + kInfinite );
                
            }
            else if ( match( line , "DENS" ) ){
            
                String rawDens = line.split( "=" )[ 1 ].split( "\\(" )[ 0 ].trim();
                
                double density = Double.parseDouble( rawDens );
                
                data.put( "DENS" , "" + density );
                
            }

        }
        
        return data;
        
    }
    
    public static boolean match( String line , String check ){
        
        String[] split = line.split( " " );
        
        for ( String section : split ){
            
            if ( section.equals( check ) ){
            
                return true;
                
            }
                
        }
        
        return false;
        
    }
    
}
