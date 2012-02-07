import java.awt.*;

public class egg extends BufferedApplet
{
   double startTime = getTime();
   // start position of the egg
   int x = 10;
   int y = 200;
   // parameter for the egg
   double startY = -4.0;
   double endY = 4.0;
   double bootstrap = 0.1;
   int scale = 20;
   int n = (int) ((endY - startY) / bootstrap + 1);
   int[] eggX = new int[n*2];
   int[] eggY = new int[n*2];

   public egg() {
	   double dy = startY;
	   for (int i = 0; i < n; i++) {
		   eggY[i] = (int) (dy*scale) + y;
		   eggX[i] = (int) (scale * Math.sqrt( (144-9*dy*dy)/(17-2*dy) )) + x;
		   dy += bootstrap;
	   }
	   for (int i = n; i < 2*n; i++) {
		   eggY[i] = eggY[2*n-1-i];
		   eggX[i] = -eggX[2*n-1-i]+2*x;
	   }

   }
   public void render(Graphics g) {
	   double time = getTime() - startTime;

	   int w = getWidth();
	   int h = getHeight();
	   g.setColor(Color.blue);
	   g.fillRect(0, 0, w, h);
      
	   double ay = 100 - 100 * Math.abs(Math.cos(6 * time));
	   double ax = 250 * time;
	   if (ax > w)
		   startTime = getTime();

	   int[] eggXdraw = new int[n*2];
	   int[] eggYdraw = new int[n*2];

	   for (int i = 0; i < 2*n; i++) {
		   eggXdraw[i] = eggX[i] + (int)ax;
		   eggYdraw[i] = eggY[i] + (int)ay;
	   }

	   g.setColor(Color.black);
	   g.drawPolygon(eggXdraw, eggYdraw, 2*n);
	   g.setColor(Color.white);
	   g.fillPolygon(eggXdraw, eggYdraw, 2*n);

	   g.setColor(Color.green);
	   g.drawOval(x-5+(int)ax, y-55+(int)ay, 40,40);
	   g.fillOval(x-5+(int)ax, y-55+(int)ay, 40,40);
	   g.drawOval(x-60+(int)ax, y-10+(int)ay, 55,55);
	   g.fillOval(x-60+(int)ax, y-10+(int)ay, 55,55);
	   g.drawOval(x+10+(int)ax, y+20+(int)ay, 45,45);
	   g.fillOval(x+10+(int)ax, y+20+(int)ay, 45,45); 
   }

   double getTime() {
     return System.currentTimeMillis() / 1000.0;
   }
}

