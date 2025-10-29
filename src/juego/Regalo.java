package juego;
import java.awt.Color;

import entorno.Entorno;

public class Regalo {
	private int x;
	private int y;	
	private int largo;
	private int ancho;
	private Color color;
	
	
	Regalo (int x, int y, int largo, int ancho, Color c) {
		this.x = x;
		this.y = y;
		this.largo = largo;
		this.ancho = ancho;
		this.color = c;
	}
	
	public void dibujar (Entorno e) {
		e.dibujarRectangulo(this.x,  this.y, this.ancho, this.largo, 0.0, this.color);
	}
	
	public boolean colisionaConCirculo(ZombieGrinch z) {
		if(z==null) {
			return false;
		}
		int bordeIzquierdo=this.x - (this.ancho/2);
		int bordeDerecho=this.x + (this.ancho/2);
		int bordeSuperior=this.y - (this.largo/2);
		int bordeInferior=this.y + (this.largo/2);		
		
		int xCercano=Math.max(bordeIzquierdo, Math.min(bordeDerecho,this.x));
		int yCercano=Math.max(bordeSuperior, Math.min(bordeInferior,this.y));
		int difX=(int) (xCercano-z.getX());
		int difY=(int) (yCercano-z.getY());
		int distancia=(int) Math.sqrt((difX*difX)+(difY*difY));
		
		if(distancia<=z.getDiametro()/2) {
			return true;
		}else {
			return false;
		}			
	}
	
    
    public int getX() {
    	return x; 
    }
    
    public int getY() { 
    	return y; 
    }
    
    public int getLargo() { 
    	return largo; 
    }
    
    public int getAncho() { 
    	return ancho; 
    }
    
    public Color getColor() { 
    	return color; 
    }

    
    public void setX(int x) { 
    	this.x = x; 
    }
    
    public void setY(int y) { 
    	this.y = y; 
    }
    
    public void setLargo(int largo) { 
    	this.largo = largo; 
    }
    public void setAncho(int ancho) {
    	this.ancho = ancho;
    }
    public void setColor(Color color) { 
    	this.color = color; 
    }

}