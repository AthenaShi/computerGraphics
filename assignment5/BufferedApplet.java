
// THIS CLASS HANDLES DOUBLE BUFFERING FOR YOU, SO THAT THE IMAGE
// DOESN'T FLICKER WHILE YOU'RE RENDERING IT.  YOU DON'T REALLY
// NEED TO WORRY ABOUT THIS TOO MUCH, AND YOU'LL PROBABLY NEVER NEED
// TO CHANGE IT.  IT'S REALLY JUST USEFUL LOW LEVEL PLUMBING.

import java.awt.*;

public abstract class BufferedApplet extends java.applet.Applet implements Runnable
{
   // YOU MUST DEFINE A METHOD TO RENDER THE APPLET

   public abstract void render(Graphics g);

   // A BACKGROUND THREAD CALLS REPAINT EVERY 30 MILLISECONDS,

   public void start() { if (t == null) (t = new Thread(this)).start(); }
   public void run()   { try { while (true) { repaint(); t.sleep(30); } }
                         catch(Exception e){}; }

   // WHICH CALLS UPDATE, WHICH CALLS YOUR RENDER METHOD.
   // THE IMAGE YOU'VE RENDERED IS THEN COPIED TO THE SCREEN.

   public void update(Graphics g) {
      if (width != getWidth() || height != getHeight()) {
         image = createImage(width = getWidth(), height = getHeight());
         buffer = image.getGraphics();
      }
      render(buffer);
      g.drawImage(image,0,0,this);
   }

   private Thread t;
   private Image image;
   private Graphics buffer;
   private int width = 0, height = 0; 
}
