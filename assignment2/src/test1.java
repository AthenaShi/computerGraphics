
import java.awt.*;

public class test1 extends BufferedApplet
{
   int counter = 0;

   public void render(Graphics g) {
      int w = getWidth();
      int h = getHeight();

      g.setColor(Color.white);
      g.fillRect(0, 0, w, h);

      g.setColor(Color.pink);
      g.fillRect(100 + counter, 100 + counter, 300, 150);
      g.setColor(Color.cyan);
      g.fillOval(100 + counter, 100 + counter, 300, 150);
      g.setColor(Color.black);
      g.drawOval(100 + counter, 100 + counter, 300, 150);

      counter++;
   }
}

