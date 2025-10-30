package juego;


import java.awt.Color;
import java.util.Random;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno = new Entorno(this, "Proyecto para TP", 1024, 768);
	private InterfazPrueba interfaz;
	private Celda[] celdas;
	private ZombieGrinch[] zombies;
	private Regalo[] regalos;
	private Planta[] listaPlanta;
	private Bala[] balas= new Bala[50];
	private Planta planta;
	Random random = new Random();
	private int contadorTicks = 0;
	private int intervaloSpawn =180; // empieza cada -3 seg.
	private int ronda =1;
	
	Juego() {
		//----DECLARO LOS OBJETOS Y LISTAS----
		interfaz = new InterfazPrueba(512,84,168,1024,Color.gray);
		planta = new Planta(75, 250, 50, Color.orange, 3);
		regalos = new Regalo[5];
		zombies = new ZombieGrinch[15];
		listaPlanta = new Planta[10];
		balas = new Bala[50];


		//----CREAR CELDAS----
		int filas = 5;
		int columnas = 10;
		int posX = 75;
		int posY = 250;
		this.celdas = new Celda[filas * columnas];
		int cont = 0;
		for (int fil = 0; fil < filas; fil++) {
			for (int colum = 0; colum < columnas; colum++) {
				int x = (colum * 100) + posX;
				int y = (fil * 100) + posY;
				Color colorCelda;
				if((fil+colum) %2 ==0){
					colorCelda= new Color(102,204,102);
				}
				else {colorCelda = new Color (0,153,0);
				}
				this.celdas[cont]= new Celda(x,y,98,98, colorCelda);
				cont++;
				}
		
		}
		
		//----CREAR REGALOS----
		int x = 75;
		int[] posFila = {250, 350, 450, 550, 650}; //misma que la de zombies
		for (int i=0;i<regalos.length;i++) {
			regalos[i] = new Regalo(x,posFila[i],50,50,Color.orange);
		}

		this.entorno.iniciar();
	}


	public void tick(){
		
		
		interfaz.dibujar(entorno);
		//----DIBUJAR CELDAS----
		for (int i=0;i<celdas.length;i++) {
			this.celdas[i].dibujar(entorno);
		}
		
		
		//----DIBUJAR ZOMBIES----
		for (int i = 0; i < zombies.length; i++) {
			if (zombies[i] != null) {
				zombies[i].dibujar(entorno);
				zombies[i].caminar();
			}	
		}
		
		//----DIBUJAR LAS PLANTAS CON EL CLICK----
		this.planta.dibujar(entorno);
		int diametro = 80;
		if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
			planta = new Planta(entorno.mouseX(), entorno.mouseY(), diametro, Color.PINK, 3);
		}
		if (planta != null) {
		    planta.dibujar(entorno);
		}
		boolean zombieEnFila = false;
		for (ZombieGrinch z: zombies) {
			if (z !=null && Math.abs(z.getY()-planta.getY())<20 && z.getX()>planta.getX()) {
				zombieEnFila= true;
				break;
				
			}
		}
		//si hay zombie la planta dispara
		if (zombieEnFila && planta.puedeDisparar()) {
			for(int i=0; i < balas.length;i++) {
				if(balas[i]== null || !balas[i].estaActiva()) {
					balas[i]=planta.disparar();
					break;
				}
			}
		}
		// --- Mover y dibujar balas ---
		for (int i = 0; i < balas.length; i++) {
		    Bala b = balas[i];
		    if (b != null && b.estaActiva()) {
		        b.mover();
		        b.dibujar(entorno);
		 // Si sale del entorno, desactivarla
		        if (b.fueradelrecuadro(entorno)) {
		        	b.desactivar();
		        	//zombie en fila
		        	 for (int j = 0; j < zombies.length; j++) {
		                 if (zombies[j] != null && b.colisionZombie(zombies[j])) {
		                     b.desactivar();
		                     zombies[j].recibirDanio(); // asumimos que ZombieGrinch tiene método restarVida()
		                     if (!zombies[j].estaVivo()) {
		                         zombies[j] = null;
		                     }
		                     break;
		                 }
		             }
		         }
		     }


        }

		//----DIBUJAR REGALOS----
		for (int i = 0; i < regalos.length; i++) {
			if(this.regalos[i] != null)
				regalos[i].dibujar(entorno);			
		}
		
		
		
		//control de Spawn
		contadorTicks++;
		if (contadorTicks >= intervaloSpawn) {
			contadorTicks=0;
			// aumentar dificultad progresivamente 
			if(intervaloSpawn >60);
			intervaloSpawn-=1; //se acelera hasta 1 seg aprox.
			if (ronda <10);
			ronda++;
		agregarZombie();
		}

		
		

		//----COLISION REGALO Y ZOMBIE----
		for (int i=0; i<regalos.length; i++) {
			for (int j=0; j<zombies.length; j++) {
				if (this.regalos[i] != null && regalos[i].colisionaConCirculo(zombies[j])) { //checkeo el null siempre que se elimine el objeto
					regalos[i] = null;	
				}
			}
		}

//		for (Regalo r : this.regalos) {
//			for (int j=0; j<zombies.length; j++) {
//				if (r != null && r.colisionaConCirculo(zombies[j])) { //checkeo el null siempre que se elimine el objeto
//					r = null;	
//				}
//			}
//		}
		
		
		
	}
	

	//arreglar null de regalos y que la bala salga de la planta
	
	private void agregarZombie() {
        int[] posFila = {250, 350, 450, 550, 650}; 
        int filaRandom = random.nextInt(5);
        
        for (int i = 0; i < zombies.length; i++) {
            if (zombies[i] == null) {
                zombies[i] = new ZombieGrinch(entorno.ancho(), posFila[filaRandom]);
                return; 
            }
        }

    }

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}