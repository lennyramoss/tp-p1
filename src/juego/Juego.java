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
	private Planta[] plantas;
	private Bala[] balas;
	private Carta carta;
	private boolean mouseIzqPrevio = false; //tick actualiza constantemente, me conviene que detecte el tick anterior con un boolean
	private Planta previewPlanta = null;
	Random random = new Random();
	private int contadorTicks = 0;
	private int intervaloSpawn =180; // empieza cada -3 seg.
	private int ronda =1;
	
	Juego() {
		//----DECLARO LOS OBJETOS Y LISTAS----
		interfaz = new InterfazPrueba(512,84,168,1024,Color.gray);
		carta = new Carta(100,84,100,70,Color.red);
		regalos = new Regalo[5];
		zombies = new ZombieGrinch[15];
		plantas = new Planta[10];
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
		carta.dibujar(entorno);
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
		if (entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
		    previewPlanta = new Planta(entorno.mouseX(), entorno.mouseY(), 80, Color.cyan,3);
		    //si izq==true sigue al mouse y dibuja la preview
		}
		else {
		    // si se suelta(mIP() se vuelve true), coloca la real 
		    if (mouseIzqPrevio && previewPlanta != null) {
		        for (int i = 0; i < plantas.length; i++) {
		            if (plantas[i] == null) {
		                plantas[i] = new Planta(previewPlanta.getX(), previewPlanta.getY(), 80, Color.PINK, 3);
		                break; //sin esto imprime una sola planta
		            }
		        }
		        previewPlanta = null; //vuelve null la preview
		    }
		}
		
		//----DIBUJAR LAS PLANTAS----
		for (int i = 0; i < plantas.length; i++) {
		    if (plantas[i] != null) {
		        plantas[i].dibujar(entorno);
				boolean zombieEnFila = false;
				for (ZombieGrinch z: zombies) {
					if (z !=null && Math.abs(z.getY()-plantas[i].getY())<20 && z.getX()>plantas[i].getX()) {
						zombieEnFila= true;
						break;
						
					}
				}
				if (zombieEnFila && plantas[i].puedeDisparar()) {
					for(int j=0; j < balas.length;j++) {
						if(balas[j]== null || !balas[j].estaActiva()) {
							balas[j]=plantas[i].disparar();
							break;
						}
					}
				}
		    }
		}

		//----DIBUJAR LA PREVIEW----
		if (previewPlanta != null) {
		    previewPlanta.dibujar(entorno);
		}
		
		// vuelve a dejarlo en false 
		mouseIzqPrevio = entorno.estaPresionado(entorno.BOTON_IZQUIERDO);



		// --- Mover y dibujar balas ---
		for (int i = 0; i < balas.length; i++) {
		    Bala b = balas[i];
		    if (b != null && b.estaActiva()) {
		        b.mover();
		        b.dibujar(entorno);
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
		        // Si sale del entorno, desactivarla
		        if (b.fueradelrecuadro(entorno)) {
		        	b.desactivar();
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

		//----COLISION PLANTA CON ZOMBIE
		for (int i=0; i<plantas.length;i++) {
			for (int j=0; j<plantas.length; j++) {
				if (this.plantas[i] != null && plantas[i].colisionaConZombie(zombies[j])) {
					plantas[i] = null;
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