package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno = new Entorno(this, "Proyecto para TP", 1024, 768);
	private InterfazPrueba interfaz;
	//private Celda celda;
	private Celda[] celdas;
	
	Juego() {
		this.interfaz = new InterfazPrueba(512,84,168,1024,Color.gray);
		this.celda = new Celda(84,228,120,120,Color.green);
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
				this.celdas[cont] = new Celda(x, y, 98, 98, Color.green);
				cont++;
				}
				
		}
		this.entorno.iniciar();
	}


	public void tick(){
		this.interfaz.dibujar(entorno);
		for (int i=0;i<celdas.length;i++) {
			this.celdas[i].dibujar(entorno);
		}
		
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
