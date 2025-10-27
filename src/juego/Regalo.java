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
	
	public boolean colisionaConCirculo(Regalo p) {
		if(p==null) {
			return false;
		}
		int bordeIzquierdo=this.x - (this.ancho/2);
		int bordeDerecho=this.x + (this.ancho/2);
		int bordeSuperior=this.y - (this.largo/2);
		int bordeInferior=this.y + (this.largo/2);		
		
		int xCercano=Math.max(bordeIzquierdo, Math.min(bordeDerecho,this.x));
		int yCercano=Math.max(bordeSuperior, Math.min(bordeInferior,this.y));
		
		int difX=xCercano-p.getX();
		int difY=yCercano-p.getY();
		int distancia=(int) Math.sqrt((difX*difX)+(difY*difY));
		
		if(distancia<=p.getDiametro()/2) {
			return true;
		}else {
			return false;
		}			
	}
	
    //buscar colission en moodle
    
	public void setX(int x) {	
		this.x = x;
	}

}