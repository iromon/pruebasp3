package etsisi.ems2020.trabajo3.lineadehorizonte;

import javax.sound.sampled.Line;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


/*
 Clase fundamental.
 Sirve para hacer la lectura del fichero de entrada que contiene los datos de como
 están situados los edificios en el fichero de entrada. xi, xd, h, siendo. Siendo
 xi la coordenada en X origen del edificio iésimo, xd la coordenada final en X, y h la altura del edificio.
 
 */
public class Ciudad {
	
    private ArrayList <Edificio> ciudad;
    
    private int alturaAnteriorPuntoUno;
    private int alturaAnteriorPuntoDos;
    private int ultimaAlturaAnterior;
    private LineaHorizonte salidaFusion;
    
    private int x, z, y;

	private Punto paux = new Punto ();
	
    
    private void anadirCoordenadaX(Punto p,int x) {
        p.setX(x);
    }
    
    private void anadirCoordenadaY(Punto p,int y) {
         p.setY(y);
    }
    
    private int devolverCoordenadaX(Punto p) {
    	return p.getX();
    }
    
    private int devolverCoordenadaY(Punto p) {
    	return p.getY();
    }
    
    private void anadirPunto(LineaHorizonte linea,Punto p) {
    	linea.addPunto(p);
    }
    
    private void borrarPunto(LineaHorizonte linea,int z) {
    	linea.borrarPunto(z);
    }
    
    private Punto getPunto(LineaHorizonte linea, int z) {
    	return linea.getPunto(z);
    }
    
	public Ciudad(){
	    	
	    	/*
	    	 * Generamos una ciudad de manera aleatoria para hacer 
	    	 * pruebas.
	    	 */
		ciudad = new ArrayList <Edificio>();
		int n = 5;
		
		metodoRandom(n);
		        
		ciudad = new ArrayList <Edificio>();
	}
    
        
    public Edificio getEdificio(int i) {
        return (Edificio)this.ciudad.get(i);
    }
    
       
    public void addEdificio (Edificio e)
    {
        ciudad.add(e);
    }
    public void removeEdificio(int i)
    {
        ciudad.remove(i);
    }
    
    public int size()
    {
        return ciudad.size();
    }
    
    public LineaHorizonte getLineaHorizonte()
    {
    	// pi y pd, representan los edificios de la izquierda y de la derecha.
        int pi = 0;                       
        int pd = ciudad.size()-1;      
        return crearLineaHorizonte(pi, pd);  
    }

    public LineaHorizonte crearLineaHorizonte(int pi, int pd) {

        LineaHorizonte linea = new LineaHorizonte(); // LineaHorizonte de salida
        
        // Caso base, la ciudad solo tiene un edificio, el perfil es el de ese edificio.
        if (pi == pd) {
            aniadirPuntosEdificio(linea,pi);
        } else {
            // Edificio mitad
            int medio = (pi + pd) / 2;
            linea = LineaHorizonteFussion(crearLineaHorizonte(pi, medio), crearLineaHorizonte(medio + 1, pd));
        }
        return linea;
    }
    
    
    public void aniadirPuntosEdificio(LineaHorizonte linea, int pi) {
    	
    	Edificio edificio = this.getEdificio(pi); // obtener el edificio del caso base
        
        Punto p1 = new Punto(edificio.getXi(),edificio.getY());   
        Punto p2 = new Punto(edificio.getXd(),0);
        
        anadirPunto(linea,p1);
        anadirPunto(linea,p2);

    }
    
    /**
     * Función encargada de fusionar los dos LineaHorizonte obtenidos por la técnica divide y
     * vencerás. Es una función muy compleja ya que es la encargada de decidir si un
     * edificio solapa a otro, si hay edificios contiguos, etc. y solucionar dichos
     * problemas para que el LineaHorizonte calculado sea el correcto.
     */

    public LineaHorizonte LineaHorizonteFussion(LineaHorizonte s1,LineaHorizonte s2)
    {    
        inicializarVariables();
        imprimirBanner(s1,s2);

        utilizarAmbasLineaHorizonte(s1,s2);
        utilizarUnaLineaHorizonte(s1);
        utilizarUnaLineaHorizonte(s2);
        return salidaFusion;
    }
    
    private void inicializarVariables() {
    	this.alturaAnteriorPuntoUno = -1;
    	this.alturaAnteriorPuntoDos = -1;
    	this.ultimaAlturaAnterior = -1;
    	this.salidaFusion = new LineaHorizonte();
    }
    
    
    private void imprimirBanner(LineaHorizonte uno, LineaHorizonte dos) {
        System.out.println("==== S1 ====");
        uno.imprimir();
        System.out.println("==== S2 ====");
        dos.imprimir();
        System.out.println("\n");
    }

    private void utilizarAmbasLineaHorizonte(LineaHorizonte uno, LineaHorizonte dos) {
    	
    	//Mientras tengamos elementos en s1 y en s2
        while (!uno.isEmpty() && !dos.isEmpty()) {
        	
            Punto p1 = getPunto(uno,0); // guardamos el primer elemento de s1
            Punto p2 = getPunto(dos,0); // guardamos el primer elemento de s2

            if (devolverCoordenadaX(p1) < devolverCoordenadaX(p2)) { // si X del s1 es menor que la X del s2
                utilizarPrimerHorizonte(p1,uno);
            }
            else if (devolverCoordenadaX(p1) > devolverCoordenadaX(p2)) { // si X del s1 es mayor que la X del s2
                utilizarSegundoHorizonte(p2,dos);
            }
            else { // si la X del s1 es igual a la X del s2
                utilizarHorizonteMasAlto(p1,p2);
                borrarPunto(uno,0);
                borrarPunto(dos,0);
            }
        }
    }
    
    private void utilizarUnaLineaHorizonte(LineaHorizonte linea) {
    	
    	while(!linea.isEmpty()) //si aun nos quedan elementos en la linea horizonte
        {
             paux = getPunto(linea,0); // guardamos en paux el primer punto
            
             if (devolverCoordenadaY(paux)!=ultimaAlturaAnterior) // si paux no tiene la misma altura del segmento ultimaAlturaAnteriorio
             {
                 anadirPunto(salidaFusion,paux);
                 ultimaAlturaAnterior = devolverCoordenadaY(paux);    // y actualizamos ultimaAlturaAnterior
             }
             borrarPunto(linea,0);
        }
    }
    
    private Punto crearPuntoAuxiliar(Punto punto, int alturaAnteriorPunto) {
    	
    	Punto paux = new Punto();
    	
    	anadirCoordenadaX(paux,devolverCoordenadaX(punto));
    	anadirCoordenadaY(paux,Math.max(devolverCoordenadaY(punto), alturaAnteriorPunto));
        return paux;
        
    }
    
    private void utilizarPrimerHorizonte(Punto punto, LineaHorizonte linea) {
    	
    	paux = crearPuntoAuxiliar(punto,this.alturaAnteriorPuntoDos);
    	
        if (devolverCoordenadaY(paux)!=ultimaAlturaAnterior) // si este maximo no es igual al del segmento anterior
        {
        	anadirPunto(salidaFusion,paux);
            ultimaAlturaAnterior = devolverCoordenadaY(paux);    // actualizamos ultimaAlturaAnterior
        }
        alturaAnteriorPuntoUno = devolverCoordenadaY(punto);   // actualizamos la altura alturaAnteriorPuntoUno
        borrarPunto(linea,0);
    }
    
    private void utilizarSegundoHorizonte(Punto punto, LineaHorizonte linea) {
       
    	paux = crearPuntoAuxiliar(punto,this.alturaAnteriorPuntoUno);
    	
        if (devolverCoordenadaY(paux)!=ultimaAlturaAnterior) // si este maximo no es igual al del segmento anterior
        {
        	anadirPunto(salidaFusion,paux);
            ultimaAlturaAnterior = devolverCoordenadaY(paux);    // actualizamos ultimaAlturaAnterior
        }
        alturaAnteriorPuntoDos = devolverCoordenadaY(punto);   // actualizamos la altura alturaAnteriorPuntoDos
        borrarPunto(linea,0);
    }
    
    private void utilizarHorizonteMasAlto(Punto uno, Punto dos) {
    	if ((devolverCoordenadaY(uno) > devolverCoordenadaY(dos)) && (devolverCoordenadaY(uno)!=ultimaAlturaAnterior)) { // guardaremos aquel punto que tenga la altura mas alta
    		anadirPunto(salidaFusion,uno);
            ultimaAlturaAnterior = devolverCoordenadaY(uno);
        }
        if ((devolverCoordenadaY(uno) <= devolverCoordenadaY(dos)) && (devolverCoordenadaY(dos)!=ultimaAlturaAnterior)){
            anadirPunto(salidaFusion,dos);
            ultimaAlturaAnterior = devolverCoordenadaY(dos);
        }
        alturaAnteriorPuntoUno = devolverCoordenadaY(uno);   // actualizamos la alturaAnteriorPuntoUno e alturaAnteriorPuntoDos
        alturaAnteriorPuntoDos = devolverCoordenadaY(dos);

    }
    
    /*
     Método que carga los edificios que me pasan en el
     archivo cuyo nombre se encuentra en "fichero".
     El formato del fichero nos lo ha dado el profesor en la clase del 9/3/2020,
     pocos días antes del estado de alarma.
     */

	public void cargarEdificios (String fichero){
	//    	int n = 6;
	//    	int i=0;
	//        int xi,y,xd;
	//        for(i=0;i<n;i++)
	//        {
	//            xi=(int)(Math.random()*100);
	//            y=(int)(Math.random()*100);
	//            xd=(int)(xi+(Math.random()*100));
	//            this.addEdificio(new Edificio(xi,y,xd));
	//        }
	    	
        try
        {
            Scanner sr = new Scanner(new File(fichero));

            while(sr.hasNext())
            {
                Edificio Salida = new Edificio(sr.nextInt(), sr.nextInt(), sr.nextInt());
                this.addEdificio(Salida);
            }
        }
        catch(Exception e){} 
           
    }

    
    public void metodoRandom(int n)
    {
        int i=0;
        int xi,y,xd;
        for(i=0;i<n;i++)
        {
            xi=(int)(Math.random()*100);
            y=(int)(Math.random()*100);
            xd=(int)(xi+(Math.random()*100));
            this.addEdificio(new Edificio(xi,y,xd));
        }
    }
}


