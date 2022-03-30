package edu.escuelaing.arep;

public class Calculator {
    public static Calculator initCalculator(){
        return new Calculator();
    }

    public double cos(Double valor){
        double rta = 0;
        rta = Math.cos(valor);
        return rta;
    }

    public double tan(Double valor){
        double rta = 0;
        rta = Math.tan(valor);
        return rta;
    }
}
