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
   // scratched egg
   double scratch = 1.2;
   int[] eggYs = new int[n*2];


   public egg() {
	   double dy = startY;
	   for (int i = 0; i < n; i++) {
		   eggY[i] = (int) (dy*scale) + y;
		   eggX[i] = (int) (scale * Math.sqrt( (144-9*dy*dy)/(17-2*dy) )) + x;
		   eggYs[i] = (int) ((dy/scratch + endY - endY/scratch)*scale) + y;
		   dy += bootstrap;
	   }
	   for (int i = n; i < 2*n; i++) {
		   eggY[i] = eggY[2*n-1-i];
		   eggX[i] = -eggX[2*n-1-i]+2*x;
		   eggYs[i] = eggYs[2*n-1-i];
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

	   int right = 50;	// right side that should bound
	   if (ax > 0 && ax <= w-right) {
		   ax = 250 * time;
	   } else if (ax > w-right & ax < 2*(w-right)) {
		   ax = 2*(w-right) - 250 * time;
	   } else if (ax > 2*(w-right-right)) {
		   startTime = getTime();
		   ax = 250 * time;
	   }

	   // draw the egg
	   int[] eggXdraw = new int[n*2];
	   int[] eggYdraw = new int[n*2];
	   int px1 = x-5+(int)ax;
	   int px2 = x-58+(int)ax;
	   int px3 = x+10+(int)ax;
	   int py1 = 0;
	   int py2 = 0;
	   int py3 = 0;
	   int h1 = 40;
	   int h2 = 55;
	   int h3 = 45;
	   // if low draw bounced egg
	   if (ay > 70) {
		   for (int i = 0; i < 2*n; i++) {
			   eggXdraw[i] = eggX[i] + (int)ax;
			   eggYdraw[i] = eggYs[i] + (int)ay;
		   }
		   py1 = (int)(y-55 + ay + (1-1/scratch)*(endY*scale+55-40/scratch/2));
		   py2 = (int)(y-10 + ay + (1-1/scratch)*(endY*scale+10-55/scratch/2));
		   py3 = (int)(y+20 + ay + (1-1/scratch)*(endY*scale-20-45/scratch/2));
	   } else {
		   for (int i = 0; i < 2*n; i++) {
			   eggXdraw[i] = eggX[i] + (int)ax;
			   eggYdraw[i] = eggY[i] + (int)ay;
		   }
		   py1 = y-55+(int)ay;
		   py2 = y-10+(int)ay;
		   py3 = y+20+(int)ay;
	   }
	   g.setColor(Color.black);
	   g.drawPolygon(eggXdraw, eggYdraw, 2*n);
	   g.setColor(Color.white);
	   g.fillPolygon(eggXdraw, eggYdraw, 2*n);

	   g.setColor(Color.green);
	   g.drawOval(px1, py1, 40,40);
	   g.fillOval(px1, py1, 40,40);
	   g.drawOval(px2, py2, 55,55);
	   g.fillOval(px2, py2, 55,55);
	   g.drawOval(px3, py3, 45,45);
	   g.fillOval(px3, py3, 45,45);
   }

   double getTime() {
     return System.currentTimeMillis() / 1000.0;
   }
}

