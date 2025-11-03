package juego;


import java.awt.Color;
import java.util.Random;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno = new Entorno(this, "Proyecto para TP", 1024, 768);
	private InterfazPrueba interfaz;
	private Celda[] celdas;
	private ZombieGrinch[] zombies;
	private Regalo[] regalos;
	private Planta[] plantas;
	private Bala[] balas;
	private Carta carta;
	private boolean mouseIzqPrevio = false; //tick actualiza constantemente, me conviene que detecte el tick anterior con un boolean
	private Planta previewPlanta = null;
	private Planta plantaSeleccionada = null;
	Random random = new Random();
	private int contadorTicks = 0;
	private int intervaloSpawn =180; // empieza cada -3 seg.
	private int ronda = 1;
	private int totalZombiesParaGanar = 50;
	private int zombiesEliminados = 0;
		private boolean juegoTerminado = false;
	
	Juego() {
		//----DECLARO LOS OBJETOS Y LISTAS----
		interfaz = new InterfazPrueba(512,84,168,1024,Color.gray);
		carta = new Carta(100,84,100,70,Color.red);
		regalos = new Regalo[5];
		zombies = new ZombieGrinch[15];
		plantas = new Planta[100];
		balas = new Bala[50];


		//----CREAR CELDAS----
		int filas = 5;
		int columnas = 10;
		int posX = 75;
		int posY = 250;
		this.celdas = new Celda[filas * columnas];
		int cont = 0;
		for (int fil = 0; fil < filas; fil++) {
			for (int colum = 0; colum < columnas; colum++) {
				int x = (colum * 100) + posX;
				int y = (fil * 100) + posY;
				Color colorCelda;
				if((fil+colum) %2 ==0){
					colorCelda= new Color(102,204,102);
				}
				else {colorCelda = new Color (0,153,0);
				}
				this.celdas[cont]= new Celda(x,y,98,98, colorCelda);
				cont++;
				}
		
		}
		
		//----CREAR REGALOS----
		int x = 75;
		int[] posFila = {250, 350, 450, 550, 650}; //misma que la de zombies
		for (int i=0;i<regalos.length;i++) {
			regalos[i] = new Regalo(x,posFila[i],50,50,Color.orange);
		}

		this.entorno.iniciar();
	}


	public void tick(){
		
		if (juegoTerminado) {
			return;
		}
		
		interfaz.dibujar(entorno);
		carta.actualizar();
		carta.dibujar(entorno);
		int segundos = entorno.tiempo() / 1000;
		int enemigosRestantes = totalZombiesParaGanar - zombiesEliminados;
		entorno.escribirTexto("Tiempo de juego: " + segundos, 300, 90);
		entorno.escribirTexto("Eliminados: " + zombiesEliminados, 500, 90);
		entorno.escribirTexto("Restantes: " + enemigosRestantes, 700, 90);
		//----DIBUJAR CELDAS----
		for (int i=0;i<celdas.length;i++) {
			this.celdas[i].dibujar(entorno);
		}
		
		
		//----DIBUJAR ZOMBIES----
		for (int i = 0; i < zombies.length; i++) {
			if (zombies[i] != null) {
				zombies[i].dibujar(entorno);
				zombies[i].caminar();
			}	
		}
		

		
		//----DIBUJAR LAS PLANTAS CON EL CLICK----
		if (entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			if (previewPlanta == null && mouseIzqPrevio == false) {
				if (carta.puntoEstaDentro(entorno.mouseX(), entorno.mouseY()) && carta.estaDisponible()) {
					previewPlanta = new Planta(entorno.mouseX(), entorno.mouseY(), 80, Color.cyan,3);
					plantaSeleccionada = null;
				}
				else {
					boolean clicEnPlanta = false;
					for (int i = 0; i < plantas.length; i++) {
						if (plantas[i] != null && plantas[i].puntoEstaDentro(entorno.mouseX(), entorno.mouseY())) {
							plantaSeleccionada = plantas[i];
							clicEnPlanta = true;
							break;
						}
				}
				if (!clicEnPlanta) {
					plantaSeleccionada = null;
					}
				}	
			}
		    //si izq==true sigue al mouse y dibuja la preview
			if (previewPlanta != null) {
				previewPlanta.setX(entorno.mouseX()); 
				previewPlanta.setY(entorno.mouseY());
			}
			mouseIzqPrevio = true;
			}
			else {
		    // si se suelta(mIP() se vuelve true), coloca la real 
				if (mouseIzqPrevio && previewPlanta != null) {
					Celda celdaSoltada = null;
					for (int c = 0; c < celdas.length; c++) {
						if (celdas[c].puntoEstaDentro(entorno.mouseX(), entorno.mouseY())) {
							celdaSoltada = celdas[c];
							break; //sin esto imprime una sola planta
							}
						}
						if (celdaSoltada != null && !celdaSoltada.estaOcupada()) {
							for (int p = 0; p < plantas.length; p++) {
								if (plantas[p] == null) {
									int centroX = celdaSoltada.getX();
									int centroY = celdaSoltada.getY();
									plantas[p] = new Planta(centroX, centroY, 80, Color.PINK, 3);
									celdaSoltada.setOcupada(true); 
									carta.iniciarRecarga();
									break;
								}
							}
						}
						previewPlanta = null;
					}
					mouseIzqPrevio = false;
				}
		//----DIBUJAR LAS PLANTAS----
		for (int i = 0; i < plantas.length; i++) {
		    if (plantas[i] != null) {
		        plantas[i].dibujar(entorno);
				boolean zombieEnFila = false;
				for (ZombieGrinch z: zombies) {
					if (z !=null && Math.abs(z.getY()-plantas[i].getY())<20 && z.getX()>plantas[i].getX()) {
						zombieEnFila= true;
						break;
						
					}
				}
				if (zombieEnFila && plantas[i].puedeDisparar()) {
					Bala nuevaBala= plantas[i].disparar();
					
					for(int j=0; j < balas.length;j++) {
						if(balas[j]== null || !balas[j].estaActiva()) {
							balas[j]= nuevaBala;
							break;
						}
					}
				}
		    }
		}

		//----DIBUJAR LA PREVIEW----
		if (previewPlanta != null) {
		    previewPlanta.dibujar(entorno);
		}
		
		//esto es para mostrar la planta que seleccionamos para mover con WASD
		if (plantaSeleccionada != null) {
			Color colorSeleccion = new Color(255, 255, 0, 150);
			entorno.dibujarCirculo(plantaSeleccionada.getX(), plantaSeleccionada.getY(), plantaSeleccionada.getDiametro() + 10, colorSeleccion);
		}
		
if (plantaSeleccionada != null) {
			
			int dx = 0;
			int dy = 0;

			boolean teclaArriba = entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.sePresiono('w');
			boolean teclaAbajo = entorno.sePresiono(entorno.TECLA_ABAJO) || entorno.sePresiono('s');
			boolean teclaIzquierda = entorno.sePresiono(entorno.TECLA_IZQUIERDA) || entorno.sePresiono('a');
			boolean teclaDerecha = entorno.sePresiono(entorno.TECLA_DERECHA) || entorno.sePresiono('d');

			if (teclaArriba) {
				dy = -100;
			}
			else if (teclaAbajo) {
				dy = 100;
			}
			else if (teclaIzquierda) {
				dx = -100;
			}
			else if (teclaDerecha) {
				dx = 100;
			}

			if (dx != 0 || dy != 0) {
				
				int xActual = plantaSeleccionada.getX();
				int yActual = plantaSeleccionada.getY();
				int xNueva = xActual + dx;
				int yNueva = yActual + dy;

				Celda celdaActual = null;
				Celda celdaNueva = null;
				
				for (int i = 0; i < celdas.length; i++) {
					if (celdas[i].getX() == xActual && celdas[i].getY() == yActual) {
						celdaActual = celdas[i];
					}
					if (celdas[i].getX() == xNueva && celdas[i].getY() == yNueva) {
						celdaNueva = celdas[i];
					}
					if (celdaActual != null && celdaNueva != null) {
						break;
					}
				}
				
				if (celdaActual != null && celdaNueva != null && !celdaNueva.estaOcupada()) {
					plantaSeleccionada.setX(xNueva);
					plantaSeleccionada.setY(yNueva);
					celdaActual.setOcupada(false);
					celdaNueva.setOcupada(true);
				}
			}
		}

		// --- Mover y dibujar balas ---
		for (int i = 0; i < balas.length; i++) {
		    Bala b = balas[i];
		    if (b != null && b.estaActiva()) {
		        b.mover();
		        b.dibujar(entorno);
	        	//zombie en fila
		        for (int j = 0; j < zombies.length; j++) {
		        	if (zombies[j] != null && b.colisionZombie(zombies[j])) {
		        		b.desactivar();
		        		balas[i]= null;
		        		
		        		zombies[j].recibirDanio(); // asumimos que ZombieGrinch tiene método restarVida()
		        		if (!zombies[j].estaVivo()) {
		        			zombies[j] = null;
		        			zombiesEliminados++;
		        			if (zombiesEliminados >= totalZombiesParaGanar) {
		        				juegoTerminado = true;
		        			}
		        		}
		        		break;
		        	}
		        // Si sale del entorno, bala es null
		        if (b.fueradelrecuadro(entorno)) {
		        	balas[i]= null;
		             }
		         }
		     }


        }

		//----DIBUJAR REGALOS----
		for (int i = 0; i < regalos.length; i++) {
			if(this.regalos[i] != null)
				regalos[i].dibujar(entorno);			
		}
		
		
		
		//control de Spawn
		contadorTicks++;
		if (contadorTicks >= intervaloSpawn) {
			contadorTicks=0;
			// aumentar dificultad progresivamente 
			if(intervaloSpawn >60);
			intervaloSpawn-=1; //se acelera hasta 1 seg aprox.
			if (ronda <10);
			ronda++;
		agregarZombie();
		}

		
		

		//----COLISION REGALO Y ZOMBIE----
		for (int i=0; i < regalos.length; i++) {
			for (int j=0; j<zombies.length; j++) {
				if (this.regalos[i] != null && regalos[i].colisionaConCirculo(zombies[j])) { //checkeo el null siempre que se elimine el objeto
					regalos[i] = null;	
				}
			}
		}
		
		for (int i = 0; i < regalos.length; i++) {
			if (regalos[i] == null) {
				juegoTerminado = true;
				break;
			}
		}
		
		//----COLISION PLANTA CON ZOMBIE
		for (int i = 0; i < plantas.length; i++) {
			if (plantas[i] == null) {
				continue; 
			}
			
			for (int j = 0; j < zombies.length; j++) {
				if (zombies[j] != null && plantas[i].colisionaConZombie(zombies[j])) {
					int xPlantaMuerta = plantas[i].getX();
					int yPlantaMuerta = plantas[i].getY();
					plantas[i] = null;
					
					for (int c = 0; c < celdas.length; c++) {
						if (celdas[c].getX() == xPlantaMuerta && celdas[c].getY() == yPlantaMuerta) {
							celdas[c].setOcupada(false); 
							break;
						}
				}
				break; 
			}
		}
	}

		
//		for (Regalo r : this.regalos) {
//			for (int j=0; j<zombies.length; j++) {
//				if (r != null && r.colisionaConCirculo(zombies[j])) { //checkeo el null siempre que se elimine el objeto
//					r = null;	
//				}
//			}
//		}
		
		
		
	}
	

	//arreglar null de regalos y que la bala salga de la planta
	
	private void agregarZombie() {
        int[] posFila = {250, 350, 450, 550, 650}; 
        int filaRandom = random.nextInt(5);
        
        for (int i = 0; i < zombies.length; i++) {
            if (zombies[i] == null) {
                zombies[i] = new ZombieGrinch(entorno.ancho(), posFila[filaRandom]);
                return; 
            }
        }

    }

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}