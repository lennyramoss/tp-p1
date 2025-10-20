package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno = new Entorno(this, "Proyecto para TP", 1024, 768);
	private InterfazPrueba interfaz;
	private Celda celda;
	private Celda[] celdas;
	
	Juego() {
		this.interfaz = new InterfazPrueba(512,84,168,1024,Color.gray);
		this.celda = new Celda(84,228,120,120,Color.green);
		this.celdas = new Celda[5];
		this.entorno.iniciar();
	}
	
	public void iniciar() {
		int x = 60;
		for (int i = 0; i < celdas.length; i++) {
			this.celdas[i] = new Celda(x,60,120,120, Color.green);
			x++;
		}
	}


	public void tick(){
		this.interfaz.dibujar(entorno);
		
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
