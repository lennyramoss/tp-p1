package juego;

import entorno.Entorno;
import java.awt.Color;

public class Celda {
	private int x;
	private int y;
	private int largo;
	private int ancho;
	private Color color;
	
	
	Celda (int x, int y, int largo, int ancho, Color c) {
		this.x = x;
		this.y = y;
		this.largo = largo;
		this.ancho = ancho;
		this.color = c;
	}
	
	public void dibujar (Entorno e) {
		e.dibujarRectangulo(this.x,  this.y, this.ancho, this.largo, 0.0, this.color);
	}

}