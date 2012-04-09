/*<pre>
   This is a simple example to show how to use the MISApplet
   to make your own pixel-by-pixel framebuffer.

   The two methods you would override from the MISApplet base
   class are initFrame and setPixel.
*/

public class ExampleMISApplet extends MISApplet {

//----- THESE TWO METHODS OVERRIDE METHODS IN THE BASE CLASS

    public void initFrame(double time) { // INITIALIZE ONE ANIMATION FRAME
       centerX = 50 * Math.cos(.2 * time);
       centerY = 50 * Math.sin(.2 * time);  // IN THIS CASE: COMPUTE DISK CENTER
    }
    public void setPixel(int x, int y, int rgb[]) { // SET ONE PIXEL'S COLOR

       double X = x-W/2 + centerX;
       double Y = y-H/2 + centerY;
       double moon = disk(X*X+Y*Y, W/4);

       X = x-W/2;
       Y = y-4*H;
       double sky = 1 - disk(X*X+Y*Y, 3.6*W);

       rgb[0] = rgb[1] = rgb[2] = (int)(155 * moon * sky);
       rgb[0] += (int)(80 * (1-sky));
       rgb[1] += (int)(60 * (1-sky));
       rgb[2] += (int)(100 * sky);
    }
//---------------------------------------------------------

    // COMPUTE COVERAGE OF ONE PIXEL OF A SMOOTH-EDGED DISK

    private double disk(double rr, double R) {
       double dd = rr - R*R;
       return dd >= 2*R ? 0 : dd <= -2*R ? 1 : (2*R - dd) / (4*R);
    }
    private double centerX=0, centerY=0;
}
