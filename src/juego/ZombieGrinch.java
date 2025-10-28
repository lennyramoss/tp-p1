package juego;

import java.awt.Color;
import entorno.Entorno;

public class ZombieGrinch {


    private double x;
    private int y;
    private double velocidad;
    private int vida;
    private double diametro;
    
    ZombieGrinch(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocidad = 1;
        this.vida = 5;
        this.diametro = 75;
        }

    public void dibujar(Entorno entorno) {
        entorno.dibujarCirculo(this.x, this.y, diametro, Color.red);
    }

    public void caminar() {
        this.x = this.x - this.velocidad;
    }
    
    public void recibirDanio() {
    	this.vida = this.vida - 1;
    }
    
    public double getX() {
    	return this.x;
    	}
    
    public double getY() {
    	return this.y;
    	}
    public double getDiametro() {
    	return this.diametro;
    }
    public boolean estaVivo() {
    	if (this.vida > 0) {
            return true;
        } 
    	else {
            return false;
    	}
    }
    public double getVida() {
    	return this.vida;
    	}
}