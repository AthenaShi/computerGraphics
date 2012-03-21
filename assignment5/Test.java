import java.awt.*;

public class Test extends BufferedApplet
{
	int width = 0;
	int height = 0;
	double scale = 50;
	double startTime = getTime();
	double speed = 1;
	double r = 50.0;	// acctual r = 100/scale
   
	Geometry torso        = addNewGeometry();

	public void render(Graphics g) {
		double time = getTime() - startTime;
		width = getWidth();
		height = getHeight();
	//	scale = width / 2;
		g.setColor(Color.white);
		g.fillRect(0,0,width,height);

		g.setColor(Color.black);
		torso.donut(10,10,0.1);
//		torso.cube();

	   update(time,g);

	}
	   
   public void update(double time, Graphics g) {
      // CLEAR THE MATRIX STACK FOR THIS ANIMATION FRAME
      mclear();
//      m().rotateY(time);
//      m().scale(r, r, r);
      
      // UPDATE THE TORSO SHAPE
    //  mpush();
         transformAndRender(torso, m(),g);
   //   mpop();
   }
	   
   public void transformAndRender(Geometry geo, Matrix m, Graphics g) {
	  // Graphics g;
	   g.setColor(Color.black);
	   int draw[][] = new int[geo.vertices.length][geo.vertices[0].length];
//	   System.out.println(geo.vertices.length+"\t"+geo.vertices[0].length);
	   double temp[] = new double[4];
	   for (int i=0; i<geo.vertices.length; i++) {
		m.transform(geo.vertices[i],temp);
		temp[0] = temp[0]/temp[3];
		temp[1] = temp[1]/temp[3];
		temp[2] = temp[2]/temp[3];
		viewport(temp,draw[i]);
	   }
	   for (int i=0; i<geo.faces.length; i++){
		for (int j=0; j<geo.faces[i].length; j++) {
			if (j<geo.faces[i].length-1) {
				g.drawLine((int)draw[geo.faces[i][j]][0], (int)draw[geo.faces[i][j]][1], (int)draw[geo.faces[i][j+1]][0], (int)draw[geo.faces[i][j+1]][1]);
			} else {
				g.drawLine((int)draw[geo.faces[i][j]][0], (int)draw[geo.faces[i][j]][1], (int)draw[geo.faces[i][0]][0], (int)draw[geo.faces[i][0]][1]);
			}
		}
	   }
   }

   public Geometry addNewGeometry () {
	   Geometry temp = new Geometry();
	   return temp;
   }
   // ALL ABOUT STACK **************
   final static int MATRIX_STACK_SIZE = 100;
   int matrixStackTop = 0;
   Matrix matrixStack[] = new Matrix[MATRIX_STACK_SIZE];
   {
      for (int n = 0 ; n < MATRIX_STACK_SIZE ; n++)
         matrixStack[n] = new Matrix();
   }
   // CLEAR THE STACK BEFORE PROCESSING THIS ANIMATION FRAME
   public void mclear() {
      matrixStackTop = 0;
      matrixStack[0].identity();
   }
   // PUSH/COPY THE MATRIX ON TOP OF THE STACK -- RETURN false IF OVERFLOW
   public boolean mpush() {
      if (matrixStackTop + 1 >= MATRIX_STACK_SIZE)
	 return false;
      matrixStack[matrixStackTop + 1].copy(matrixStack[matrixStackTop]);
      matrixStackTop++;
      return true;
   }
   // POP OFF THE MATRIX ON TOP OF THE STACK -- RETURN false IF UNDERFLOW
   public boolean mpop() {
      if (matrixStackTop <= 0)
	 return false;
      --matrixStackTop;
      return true;
   }
   // RETURN THE MATRIX CURRENTLY ON TOP OF THE STACK
   public Matrix m() {
      return matrixStack[matrixStackTop];
   }
   // STACK END************

	double getTime() {
		return System.currentTimeMillis() / 1000.0;
	}
	public void viewport(double src[], int dst[]) {
		dst[0] = (int) ( 0.5 * width  + src[0] * scale );
		dst[1] = (int) ( 0.5 * height - src[1] * scale );
	}
	public int[] coTransform(double middle[], double a, double b) {
		int array[] = { (int)(middle[0]-a), (int)(middle[1]-b), (int)(2*a), (int)(2*b) };
		return array;
	}
	public int[] coTransform(int middle[], double a, double b) {
		int array[] = { (int)(middle[0]-a), (int)(middle[1]-b), (int)(2*a), (int)(2*b) };
		return array;
	}
}

