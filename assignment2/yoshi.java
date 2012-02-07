import java.awt.*;

public class yoshi extends BufferedApplet
{
   double startTime = getTime();

   public void render(Graphics g) {
      double time = getTime() - startTime;

      int w = getWidth();
      int h = getHeight();

      int x = 200;
      int y = 100;

      g.setColor(Color.white);
      g.fillRect(0, 0, w, h);

      g.setColor(Color.green);
      g.drawOval(x, y, 100, 100);	// head
      g.fillOval(x, y, 100, 100);	// head
      g.drawOval(x+80, y, 100, 100);	// big nose
      g.fillOval(x+80, y, 100, 100);	// big nose
      g.drawOval(x+40, y-35, 30, 60);	// eyes
      g.fillOval(x+40, y-35, 30, 60);	// eyes
//      g.drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) 
      /*
      g.setColor(Color.green);
      g.drawRect(x+30, y+85, 55, 37);
      g.fillRect(x+30, y+85, 55, 37);

      g.drawArc(x-100, y+48, 185, 150, 0, -130);
      g.fillArc(x-100, y+48, 185, 150, 25, -155);

      g.setColor(Color.white);
      g.drawArc(x-150, y+12, 180, 170, 0, -95); 
      g.fillArc(x-150, y+12, 180, 170, 0, -95);  */


//      drawPolygon(int[] xPoints, int[] yPoints, int nPoints)
      g.setColor(Color.black);
      int bodyStartX = x+31;
      int bodyStartY = y+95;

      int[] bodyXs = {bodyStartX,bodyStartX-10,bodyStartX-20,bodyStartX-30,bodyStartX-40,bodyStartX-45};
      int[] bodyYs = {bodyStartY,bodyStartY+20,bodyStartY+40,bodyStartY+60,bodyStartY+70,bodyStartY+75};

      g.drawPolygon(bodyXs, bodyYs, 6);


      
   }

   double getTime() {
      return System.currentTimeMillis() / 1000.0;
   }
}

