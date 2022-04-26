package mcm.edu.ph.dones_physicscalculator.Controller;

import android.content.Context;

import java.text.DecimalFormat;

public class Calculation {

    private static final double R = 0.0821; // ideal gas constant

    // geometry formulas
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

    // for resizing rectangle
    public int dpToPx( double dp, Context context ) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    // physics formulas
    public double getSpeed( double d, double t ){ return roundTwoDecimals( d * t ); }
    public double getDistance( double s, double t ){ return roundTwoDecimals( s / t ); }
    public double getTime( double s, double d){ return roundTwoDecimals( s / d ); }

    public double getGasPressure( double n, double T, double V ){ return roundTwoDecimals( (n * R * T) / V ); }
    public double getGasVolume( double n, double T, double P ){ return roundTwoDecimals( (n * R * T) / P ); }
    public double getGasMoles( double P, double V, double T ){ return roundTwoDecimals( (P * V) / (R * T) ); }
    public double getGasTemperature( double P, double V, double n ){ return roundTwoDecimals( (P * V) / (R * n) ); }

    public double getKE( double m, double v ){ return roundTwoDecimals( 0.5 * m * (v * v) ); }
    public double getKEMass( double KE, double v ){ return roundTwoDecimals( (2 * KE) / (v * v) ); }
    public double getKEVelocity( double KE, double m ){ return roundTwoDecimals( Math.sqrt((2 * KE) / m) ); }

    public double getPE( double m, double h ){ return roundTwoDecimals( m * 9.8 * h ); }
    public double getPEMass( double PE, double h ){ return roundTwoDecimals( PE / (9.8 * h) ); }
    public double getPEHeight( double PE, double m ){ return roundTwoDecimals( PE / (9.8 * m) ); }

    // rounding off
    public double roundTwoDecimals( double d ) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.parseDouble(twoDForm.format(d));
    }




}

