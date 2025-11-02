package juego;

import java.awt.Color;

import entorno.Entorno;

public class Carta {
	private int x;
	private int y;	
	private int largo;
	private int ancho;
	private Color color;
	private int tiempoRecargaTotal = 120;
	private int contadorRecarga = 0;
	
	
	Carta (int x, int y, int largo, int ancho, Color c) {
		this.x = x;
		this.y = y;
		this.largo = largo;
		this.ancho = ancho;
		this.color = c;
		this.contadorRecarga = 0;
	}
	
	public void dibujar (Entorno e) {
		e.dibujarRectangulo(this.x,  this.y, this.ancho, this.largo, 0.0, this.color);
		if (!estaDisponible()){
			double porcentajeRecarga = (double)this.contadorRecarga/this.tiempoRecargaTotal;
			double altoRecarga = this.largo * porcentajeRecarga;
			e.dibujarRectangulo(this.x, this.y - (this.largo/2) + (altoRecarga/2), this.ancho, altoRecarga, 0, new Color(50, 50, 50, 200));
		}
	}
	
	public boolean puntoEstaDentro(int px, int py) {
		return px >= this.x - this.ancho / 2 && px <= this.x + this.ancho / 2 && py >= this.y - this.largo / 2 && py <= this.y + this.largo / 2;
	}
	
	public void actualizar() {
		if (this.contadorRecarga > 0) {
			this.contadorRecarga--;
		}
	}
	
	public boolean estaDisponible() {
		return this.contadorRecarga <= 0;
	}
	
	public void iniciarRecarga() {
		this.contadorRecarga = this.tiempoRecargaTotal;
	}
}
