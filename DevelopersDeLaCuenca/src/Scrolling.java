import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;


public class Scrolling extends MIDlet implements CommandListener {

	private Command exitCommand;
	private Display display;
	private AutitosCel screen;

	public Scrolling() {
		display=Display.getDisplay(this);
		exitCommand = new Command("Salir",Command.SCREEN,2);

		screen=new AutitosCel();

		screen.addCommand(exitCommand);
		screen.setCommandListener(this);
		new Thread(screen).start();
	}

	public void startApp() throws MIDletStateChangeException {
		display.setCurrent(screen);
	}

	public void pauseApp() {}

	public void destroyApp(boolean unconditional) {}

	public void commandAction(Command c, Displayable s) {

		if (c == exitCommand) {
			destroyApp(false);
			notifyDestroyed();
		}
	}
}


