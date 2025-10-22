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
	Random random = new Random();
	private int contadorTicks = 0;
	private int intervaloSpawn =180; // empieza cada -3 seg.
	private int ronda =1;
	
	Juego() {
		this.interfaz = new InterfazPrueba(512,84,168,1024,Color.gray);
		//this.celda = new Celda(84,228,120,120,Color.green);
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
				
		this.zombies = new ZombieGrinch[15];
		this.entorno.iniciar();
	}


	public void tick(){
		this.interfaz.dibujar(entorno);
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
		//control de Spawn
		contadorTicks++;
		if (contadorTicks >= intervaloSpawn) {
			agregarZombie();
			contadorTicks=0;
			// aumentar dificultad progresivamente 
			if(intervaloSpawn >60);
			intervaloSpawn-=10; //se acelera hasta 1 seg aprox.
			if (ronda <10);
			ronda++;
		agregarZombie();
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