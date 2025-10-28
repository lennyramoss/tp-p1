package juego;


import java.awt.Color;
import java.util.Random;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno = new Entorno(this, "Proyecto para TP", 1024, 768);
	private InterfazPrueba interfaz;
	//private Celda celda;
	private Celda[] celdas;
	private ZombieGrinch[] zombies;
	private Regalo[] regalos;
	private Planta[] listaPlanta;
	private Planta planta;
	Random random = new Random();
	private int contadorTicks = 0;
	private int intervaloSpawn =180; // empieza cada -3 seg.
	private int ronda =1;
	private Bala[] balas;
	
	Juego() {
		this.interfaz = new InterfazPrueba(512,84,168,1024,Color.gray);
		this.planta = new Planta(75, 250, 50, Color.orange, 3);
		//this.celda = new Celda(84,228,120,120,Color.green);
		int filas = 5;
		int columnas = 10;
		int posX = 75;
		int posY = 250;
		
		// --- DISPARO AUTOMÁTICO ---
		boolean hayZombieEnFila = false;
		for (ZombieGrinch z : zombies) {
		    if (z != null && Math.abs(z.getY() - planta.getY()) < 20 && z.getX() > planta.getX()) {
		        hayZombieEnFila = true;
		        break;
		    }
		}

		if (hayZombieEnFila) {
		    // Buscar espacio libre para una nueva bala
		    for (int i = 0; i < balas.length; i++) {
		        if (balas[i] == null || !balas[i].estaActiva()) {
		            balas[i] = new Bala(this.X+ 30, this.y);
		            break;
		        }
		    }
		}

		// --- ACTUALIZAR Y DIBUJAR BALAS ---
		for (int i = 0; i < balas.length; i++) {
		    Bala b = balas[i];
		    if (b != null && b.estaActiva()) {
		        b.mover();
		        b.dibujar(entorno);

		        // Colisión con zombies
		        for (int j = 0; j < zombies.length; j++) {
		            ZombieGrinch z = zombies[j];
		            if (z != null && b.colisionaConZombie(z)) {
		                z.recibirDanio();
		                b.desactivar();
		                if (z.getVida() <= 0) {
		                    zombies[j] = null;
		                }
		                break;
		            }
		        }

		        // Si sale de la pantalla por la derecha
		        if (b.fueradelrecuadro(entorno)) {
		            b.desactivar();
		        }
		    }
		}

		
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
		
		regalos = new Regalo[5];
		int x = 75;
        //int y = 250;
		int[] posFila = {250, 350, 450, 550, 650}; //misma que la de zombies
 
		for (int i=0;i<regalos.length;i++) {
			regalos[i] = new Regalo(x,posFila[i],50,50,Color.orange);
			//regalos[i] = new Regalo(x,y,100,200,Color.cyan);
			//y+=100;
		}
		this.zombies = new ZombieGrinch[15];
		this.listaPlanta = new Planta[10];
		this.entorno.iniciar();
		this.balas = new Bala[50];
	}


	public void tick(){
		
		
		interfaz.dibujar(entorno);
		// dibujar celdas 
		for (int i=0;i<celdas.length;i++) {
			this.celdas[i].dibujar(entorno);
		}
		
		for (int i = 0; i < zombies.length; i++) {
			if (zombies[i] != null) {
				zombies[i].dibujar(entorno);
				zombies[i].caminar();
			}	
		}
		for (int i = 0; i < regalos.length; i++) {
		    Regalo r = regalos[i];
		    r.dibujar(entorno);
		}

		
		
		//control de Spawn
		contadorTicks++;
		if (contadorTicks >= intervaloSpawn) {
			agregarZombie();
			contadorTicks=0;
			// aumentar dificultad progresivamente 
			if(intervaloSpawn >60);
			intervaloSpawn-=1; //se acelera hasta 1 seg aprox.
			if (ronda <10);
			ronda++;
		agregarZombie();
		}

		
		
		this.planta.dibujar(entorno);
		int diametro = 80;
		if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
			planta = new Planta(entorno.mouseX(), entorno.mouseY(), diametro, Color.PINK, 3);
		}
		if (planta != null) {
		    planta.dibujar(entorno);
		}
		
		
	}

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