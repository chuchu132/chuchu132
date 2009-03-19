
import java.io.InputStream;
import java.util.Random;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;




class AutitosCel extends Canvas implements Runnable {

	private int sleepTime;
	private int deltaX,deltaY;
	private Sprite autito = new Sprite(4,Sprite.MODE_NORM);
	private Sprite pista = new Sprite(3,Sprite.MODE_FULL);
	private Sprite vaca = new Sprite(1,Sprite.MODE_NORM);
	private Random generator = new Random();
	private  byte pistaActual = 1;
    private int puntos = 1;
    public  AutitosCel(){
		setTitle(" Esquiva la Vaca man!");
		
	}

	void iniciar() {
		repaint();
		serviceRepaints();
// Cargamos los sprites
		
		autito.addFrame(1,"/autito.png",this);
		autito.addFrame(2,"/autitoi.png",this);
		autito.addFrame(3,"/autitod.png",this);
		autito.addFrame(4,"/autitoF.png",this);
		pista.addFrame(1, "/Pista0.png",this);
		pista.addFrame(2, "/Pista1.png",this);
		pista.addFrame(3, "/Pista2.png",this);
		vaca.addFrame(1, "/Vaca.png",this);
		
		// Iniciamos los Sprites
		
		pista.on();
		autito.on();
		vaca.on();
								
		sleepTime = 50;
		autito.setX(getWidth()/2);
		autito.setY(getHeight()-25);

		pista.setX(getWidth()/2);
		pista.setY(getHeight()/2);


		vaca.setX(getWidth()/2);
		vaca.setY(getHeight() + vaca.getH());

		deltaX=0;
		deltaY=0;

        puntos = -50;

	}



	void computePlayer() {
		
		if (autito.getX()+deltaX>autito.getW()/2 && autito.getX()+deltaX<(getWidth()- autito.getW()/2)){
			autito.setX(autito.getX()+deltaX);}
		if (sleepTime + deltaY < 60 && sleepTime + deltaY >=0){
			sleepTime+=deltaY;
		}   

		if (vaca.getY()> getHeight()){
			vaca.setY(getHeight()*2/5);
			generator.setSeed(System.currentTimeMillis());
			float f = generator.nextFloat();
			f*=getWidth();
			int lateral = getWidth()/10;
			int lateral2 = getWidth() -lateral;
			if(f< lateral)f+=lateral;
			else
			if( f > lateral2)f-=lateral;
			
			vaca.setX((int)f);
			
			puntos += (100 - sleepTime);
			
		}else{
			vaca.setY(vaca.getY() + 3);
			
		}

		if (autito.collide(vaca)){
			vaca.setY(getHeight() + vaca.getH());
			puntos -= 180;
			try{
				InputStream in = getClass().getResourceAsStream("/vaca.wav");
				Player sonido = Manager.createPlayer(in, "audio/x-wav");
				sonido.start();

			}catch(Exception e){};
		}
         
         
		if(pistaActual == 3){
			pistaActual=1;
		}
		else pistaActual++;
		pista.selFrame(pistaActual);
	}


	// thread que contiene el game loop
	public void run() {
		iniciar();
		while (true) {



			// Actualizar posición del jugador
			computePlayer();

			// Actualizar pantalla
			repaint();
			serviceRepaints();

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
		}
	}  

	public void keyReleased(int keyCode) {
		int action=getGameAction(keyCode);

		switch (action) {

		case LEFT:{
			deltaX=0;
			autito.selFrame(1);}
			break;
		case RIGHT:{
			deltaX=0;
			autito.selFrame(1);}
			break;
		case UP:
			deltaY=0;
			break;
		case DOWN:  {     
			deltaY=0;
			autito.selFrame(1);}
			break;
		}
	}

	public void keyPressed(int keyCode) {

		int action=getGameAction(keyCode);

		switch (action) {

		case LEFT:{
			autito.selFrame(2);
			deltaX=-3;}
			break;
		case RIGHT:{
			autito.selFrame(3);
			deltaX=3;}
			break;
		case UP:{
			autito.selFrame(1);
			deltaY=-3;}
			break;
		case DOWN:{
			autito.selFrame(4);
			deltaY=3;}
			break;
		}
	}

	public void paint(Graphics g) {
		g.setColor(0,0, 0);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(255,255,255);
		
      if(puntos == 1){
    	  
    	try {
    		    		
  			Image logo = Image.createImage("/logo.png");
  			g.drawImage(logo,getWidth()/2, getHeight()/2, Graphics.HCENTER | Graphics.VCENTER);
  			
  			Font font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD,  Font.SIZE_MEDIUM);
  			g.setFont (font);
  			g.setColor(255,255,255);
  			g.drawString("Loading..",getWidth()/2,getHeight() - 15, Graphics.BOTTOM | Graphics.HCENTER );
  		} catch (Exception e) {};
      
  		        
      } else{
      
		

		pista.draw(g);
	
		// Dibujar el jugador
		autito.draw(g);

		//Dibujar vaca
		vaca.draw(g);
		
		//Pintar puntos
		String points = "Puntos "+puntos;
		Font font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD,  Font.SIZE_MEDIUM);
		g.setFont (font);
		g.drawString(points,0,0, Graphics.TOP | Graphics.LEFT);
		
      }
	}
}
