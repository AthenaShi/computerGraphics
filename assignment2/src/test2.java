
import java.awt.*;

public class test2 extends BufferedApplet
{
   double startTime = getTime();

   public void render(Graphics g) {
      double time = getTime() - startTime;

      int w = getWidth();
      int h = getHeight();

      g.setColor(Color.white);
      g.fillRect(0, 0, w, h);

      double ax = 100 + 50 * Math.cos(6 * time);
      double ay = 100 + 50 * Math.sin(6 * time);

      double bx = 100 + 50 * Math.sin(6 * time);
      double by = 100 + 50 * Math.cos(6 * time);

      g.setColor(Color.black);
      g.drawLine((int)ax, (int)ay, (int)bx, (int)by);
      g.fillOval((int)bx - 5, (int)by - 5, 10, 10);
      g.drawString("" + time, (int)ax, (int)ay);
   }

   double getTime() {
      return System.currentTimeMillis() / 1000.0;
   }
}

