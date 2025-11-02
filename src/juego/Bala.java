package juego;


import java.awt.Color;
import entorno.Entorno;

public class Bala {
    private double x, y;
    private double velocidad;
    private double diametro;
    private Color color;
    private boolean activa;
    private int daño;

    
    Bala(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocidad = 1;
        this.daño = 1;
        this.activa = true;
        this.diametro = 10;
    }
    public void dibujar(Entorno entorno) {
        entorno.dibujarCirculo(this.x, this.y, diametro, Color.blue);
    }

    public boolean estaActiva() {
        return activa;
    }
    
    public void desactivarBala() {
    	activa= false;
    }
    public void mover() {
        if (activa) {
            this.x += velocidad;
        }
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void desactivar() {
        activa = false;
    }

    public boolean fueradelrecuadro(Entorno e) {
        return this.x - this.diametro / 2 > e.ancho();
    }
    
    
    public boolean colisionZombie(ZombieGrinch z) {
        if (z == null || !activa) return false;
        double distancia = Math.sqrt(Math.pow(x - z.getX(), 2)+ Math.pow(y - z.getY(), 2));
        return distancia < (this.diametro/2 + z.getDiametro()/2);
    }
    public int getDaño() {
        return daño;
    }
}