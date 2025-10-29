package juego;

import entorno.Entorno;
import java.awt.Color;

public class Planta {
    private int x;
    private int y;
    private int diametro;
    private Color color;
    private int vida;     

    Planta(int x, int y, int diametro, Color c, int vida) {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
        this.color = c;
        this.vida = vida;
    }

    public void dibujar(Entorno e) {
    	e.dibujarCirculo(this.x, this.y, this.diametro, this.color);
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

}

//buscar colission en moodle
