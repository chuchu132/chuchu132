

import javax.microedition.lcdui.*;


import java.io.*;

class Sprite {
	static  int MODE_NORM = 0;
	static  int  MODE_FULL = 1;
	private int mode = MODE_NORM;
	private int posx,posy;
	private boolean active;
	private int frame,nframes;
	private Image[] sprites;

	// constructor. 'nframes' es el número de frames del Sprite.
	public Sprite(int nframes,int mode) {

		// El Sprite no está activo por defecto.
		active=false;
		frame=1;
		this.nframes=nframes;
		sprites=new Image[nframes+1];
		this.mode = mode;
	}


	public void setX(int x) {
		posx=x;
	}

	public void setY(int y) {
		posy=y;
	}

	int getX() {
		return posx;
	}

	int getY() {
		return posy;
	}

	int getW() {
		return sprites[nframes].getWidth();
	}

	int getH() {
		return sprites[nframes].getHeight();
	}

	public void on() {
		active=true;
	}

	public void off() {
		active=false;
	}

	public boolean isActive() {
		return active;
	}

	public void selFrame(int frameno) {
		frame=frameno;
	}

	public int frames() {
		return nframes;
	}

	// Carga un archivo tipo .PNG y lo añade al sprite en
	// el frame indicado por 'frameno'
	public void addFrame(int frameno, String path,Canvas pantalla) {
		if(mode == MODE_NORM){
			try {
				sprites[frameno]=Image.createImage(path);
			} catch (IOException e) {
				System.err.println("Can`t load the image " + path + ": " + e.toString());
			}
		}else{
			try {
				Image imagenOriginal = Image.createImage(path);
				int originalAncho= imagenOriginal.getWidth();
				int originalAlto= imagenOriginal.getHeight();
				int nuevoAncho = pantalla.getWidth();
				int nuevoAlto = pantalla.getHeight();

				Image imagenFinal = Image.createImage(nuevoAncho, nuevoAlto);
				Graphics g = imagenFinal.getGraphics();
				//Bucles que tratan el reescalado
				for(int y=0; y < nuevoAlto; y++){
					for(int x=0; x < nuevoAncho; x++) {
						g.setClip(x, y, 1, 1);
						int xAux = x * originalAncho/ nuevoAncho;
						int yAux = y * originalAlto/ nuevoAlto;
						g.drawImage(imagenOriginal, x-xAux, y-yAux, Graphics.LEFT | Graphics.TOP);
					}
				}
				sprites[frameno] = Image.createImage(imagenFinal);	

			} catch (IOException e) {
				System.err.println("Can`t load the image " + path + ": " + e.toString());
			}
		}
	}

	boolean collide(Sprite sp) {
		int w1,h1,w2,h2,x1,y1,x2,y2;

		w1=getW();  // ancho del sprite1
		h1=getH();  // altura del sprite1
		w2=sp.getW(); // ancho del sprite2
		h2=sp.getH(); // alto del sprite2
		x1=getX();  // pos. X del sprite1
		y1=getY();  // pos. Y del sprite1
		x2=sp.getX(); // pos. X del sprite2
		y2=sp.getY(); // pos. Y del sprite2

		if (((x1+w1)>x2)&&((y1+h1)>y2)&&((x2+w2)>x1)&&((y2+h2)>y1)) {
			return true;
		} else {
			return false;
		}
	}


	// Dibujamos el Sprite
	public void draw(Graphics g) {
		g.drawImage(sprites[frame],posx,posy,Graphics.HCENTER|Graphics.VCENTER);
	} 
}