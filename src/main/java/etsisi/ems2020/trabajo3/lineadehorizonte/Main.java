package etsisi.ems2020.trabajo3.lineadehorizonte;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) {
		// Variables que necesitar� para posteriomente 
		// llamar a los distintos m�todos que he ido creando 
		// en las clases .
		Punto p=null;
		FileWriter fileWriter=null; 
		PrintWriter out=null;
		int i=0;
		
		
		
		/*
		 Empezamos a ejecutar el c�digo para intentar hacer el ejercicio
		 que nos piden, calcular la l�nea del horizonte de una ciudad.
		 */
        Ciudad c = new Ciudad();
        c.cargarEdificios("ciudad1.txt");
        
        // Creamos l�nea del horizonte
        LineaHorizonte linea = new LineaHorizonte();
        linea = c.getLineaHorizonte();
        //Guardamos la linea del horizonte
        
        linea.guardaLineaHorizonte("salida.txt");
        System.out.println("-- Proceso finalizado Correctamente --");
        Punto  p2 = new Punto(5,6);
        System.out.println(p2);
	}

}
