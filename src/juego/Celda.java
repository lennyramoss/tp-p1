package juego;

import entorno.Entorno;
import java.awt.Color;

public class Celda {
	private int x;
	private int y;	
	private int largo;
	private int ancho;
	private Color color;
	private boolean ocupada;
	
	
	Celda (int x, int y, int largo, int ancho, Color c) {
		this.x = x;
		this.y = y;
		this.largo = largo;
		this.ancho = ancho;
		this.color = c;
		this.ocupada = false;
	}
	
	public void dibujar (Entorno e) {
		e.dibujarRectangulo(this.x,  this.y, this.ancho, this.largo, 0.0, this.color);
	}
	
	public boolean puntoEstaDentro(int px, int py) {
		return px >= this.x - this.ancho / 2 && 
		       px <= this.x + this.ancho / 2 &&
		       py >= this.y - this.largo / 2 && 
		       py <= this.y + this.largo / 2;
	}
	public void setX(int x) {	
		this.x = x;
	}
	
	public boolean estaOcupada() {
		return this.ocupada;
	}
	
	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}
	
	public int getX() { 
		return this.x; 
		}
	
	public int getY() {
		return this.y;
		}
	
	
}