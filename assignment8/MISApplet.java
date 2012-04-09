//<pre>
/*
   This is the support code that implements a frame buffer in a Java Applet,
   by using a Memory Image Source.

   You can probably use this class without ever needing to change it.
*/

import java.applet.*;
import java.awt.*;
import java.awt.image.*;

public class MISApplet extends Applet implements Runnable {
    public int W, H;

    // YOUR APPLET CAN OVERRIDE THE FOLLOWING TWO METHODS:

    public void initialize() {}

    public void initFrame(double time) { }            // INITIALIZE EACH FRAME
    public void setPixel(int x, int y, int rgb[]) { } // SET COLOR AT EACH PIXEL

    // INITIALIZE THINGS WHEN APPLET STARTS UP

    public void init() {
        setLayout(new BorderLayout());

        W = getBounds().width;      // FIND THE RESOLUTION OF THE JAVA APPLET
        H = getBounds().height;
        pix = new int[W*H];         // ALLOCATE A FRAME BUFFER IMAGE
        mis = new MemoryImageSource(W, H, pix, 0, W);
        mis.setAnimated(true);
        im = createImage(mis);      // MAKE MEMORY IMAGE SOURCE FOR FRAME BUFFER

	initialize();

	startTime = clockTime();    // FETCH CLOCK TIME WHEN APPLET STARTS
        new Thread(this).start();   // START THE BACKGROUND RENDERING THREAD
    }

    // UPDATE DISPLAY AT EACH FRAME, BY DRAWING FROM MEMORY IMAGE SOURCE

    public void update(Graphics g) {
        g.drawImage(im, 0, 0, null);
    }

    // BACKGROUND THREAD: COMPUTE AND DRAW FRAME, EVERY 30 MILLISEC

    public void run() {
        while(true) {
            computeImage(clockTime() - startTime);
            mis.newPixels(0, 0, W, H, true);
            repaint();
            try {
                Thread.sleep(30);
            }
            catch(InterruptedException ie) {}
        }
    }

    // COMPUTE IMAGE, GIVEN ANIMATION TIME

    private int rgb[] = new int[3];
    public void computeImage(double time) {
	initFrame(time);                 // INITIALIZE COMPUTATION FOR FRAME
        int i = 0;
        for(int y = 0; y < H; y++)
        for(int x = 0; x < W; x++) { // COMPUTE COLOR FOR EACH PIXEL
           setPixel(x, y, rgb);
	   pix[i++] = pack(rgb[0],rgb[1],rgb[2]);
        }
    }

    public int pack(int red, int grn, int blu) {
       return 255<<24 | clip(red,0,255)<<16
		      | clip(grn,0,255)<< 8
		      | clip(blu,0,255)    ;
    }

    public int unpack(int packedRGB, int component) {
       return packedRGB >> 8*(2-component) & 255;
    }

    public int xy2i(int x, int y) { return x + W * y; }

    int clip(int t, int lo, int hi) { return t<lo ? lo : t>hi ? hi : t; }

    // RETURN THE TIME, IN SECONDS, ON THE CLOCK

    double clockTime() { return System.currentTimeMillis() / 1000.; }

    // PRIVATE DATA

    public int[] pix;              // THE FRAME BUFFER ARRAY

    private MemoryImageSource mis;  // MEMORY IMAGE SOURCE CONTAINING FRAME BUFFER
    private Image im;               // IMAGE CONTAINING THE MEMORY IMAGE SOURCE
    private double startTime;       // CLOCK TIME THAT THE APPLET STARTED
}
