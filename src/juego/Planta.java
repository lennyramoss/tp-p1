package juego;

import entorno.Entorno;
import java.awt.Color;

public class Planta {
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private Color color;
    private int vida;     

    Planta(int x, int y, int ancho, int alto, Color c, int vida) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.color = c;
        this.vida = vida;
    }

    public void dibujar(Entorno e) {
        e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0.0, this.color);
    }


    public boolean estaViva() {
        return this.vida > 0;
    }

}
