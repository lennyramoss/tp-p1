package juego;

import entorno.Entorno;
import java.awt.Color;

public class Planta {
    private int x;
    private int y;
    private int diametro;
    private Color color;
    private int vida;     
    private int tiempoRecarga = 120;
    private int contadorDisparo=0;
    
    Planta(int x, int y, int diametro, Color c, int vida) {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
        this.color = c;
        this.vida = vida;
    }

    public void dibujar(Entorno e) {
    	e.dibujarCirculo(this.x, this.y, this.diametro, this.color);
    	if (contadorDisparo >0) {
    		contadorDisparo--;
    	}
    }
    public boolean colisionaConZombie(ZombieGrinch z) {
        if (z == null) return false;
        double dx = this.getX() - z.getX();
        double dy = this.getY() - z.getY();
        double r  = this.getDiametro()/2.0 + z.getDiametro()/2.0;
        return dx*dx + dy*dy <= r*r;
    }
    public boolean puedeDisparar() {
    	return contadorDisparo <=0;
    }
    public Bala disparar() {
    	contadorDisparo= tiempoRecarga;
    	return new Bala(this.x + this.diametro/2, this.y);
    }
    public boolean estaViva() {
        return this.vida > 0;
    }
    
    public int getX() {
    	return x; 
    }
    
    public int getY() { 
    	return y; 
    }

    public int getDiametro() {
    	return diametro;
    }
    
    public void setX(int x) {
    	this.x = x; 
    	}
    
    public void setY(int y) {
    	this.y = y; 
    	}
}

//buscar colission en moodle
