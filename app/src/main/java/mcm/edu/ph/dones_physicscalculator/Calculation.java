package mcm.edu.ph.dones_physicscalculator;

import android.content.Context;

import java.text.DecimalFormat;

public class Calculation {

    public double getSqPerimeter( double s ) { return roundTwoDecimals( 4 * s ); }
    public double getRePerimeter( double l, double w ) { return roundTwoDecimals( 2 * (l + w) ); }
    public double getCircumference( double r ) { return roundTwoDecimals( 2 * Math.PI * r); }

    public double getSqArea( double s ) { return roundTwoDecimals( s * s ); }
    public double getReArea( double l, double w ) { return roundTwoDecimals( l * w ); }
    public double getCiArea( double r ) { return roundTwoDecimals( Math.PI * r * r ); }
    public double getTrArea( double a ) { return roundTwoDecimals( (Math.sqrt(3) / 4) * ( a * a ) ); }

    public double getCueVolume( double s ){ return roundTwoDecimals( s * s * s ); }
    public double getCudVolume( double l, double w, double h ){ return roundTwoDecimals( l * w * h ); }
    public double getSpVolume( double r ){ return roundTwoDecimals( ( 4.0 / 3.0 ) * Math.PI * ( r * r * r ) ); }
    public double getCyVolume( double r, double h ){ return roundTwoDecimals( Math.PI * (( r * r ) * h) ); }
    public double getCoVolume( double r, double h ){ return roundTwoDecimals( Math.PI * (( r * r ) * ( h / 3 )) ); }

    public double getReWidth( double l, double w ){
        double n;
        if ( l > w ) { n = roundTwoDecimals((250 * w) / l); }
        else if ( w > l ) { n = 150; }
        else { n = 150; }
        return n;
    }

    public double getReLength( double l, double w ){
        double n;
        if ( l > w ) { n = 250; }
        else if ( w > l ) { n = roundTwoDecimals( (150 * l) / w ); }
        else { n = 150; }
        return n;
    }

    public double roundTwoDecimals( double d ) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.parseDouble(twoDForm.format(d));
    }
    public int dpToPx( double dp, Context context ) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

}

