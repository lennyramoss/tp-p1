package juego;

import java.awt.Color;
import entorno.Entorno;

public class ZombieGrinch {


    private double x;
    private int y;
    private int velocidad;

    ZombieGrinch(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocidad = 1;
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarCirculo(this.x, this.y, 75, Color.red);
    }

    public void caminar() {
        this.x = this.x - this.velocidad;
    }
}