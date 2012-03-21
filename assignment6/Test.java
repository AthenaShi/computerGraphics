import java.awt.*;

public class Test extends BufferedApplet
{
	int width = 0;
	int height = 0;
	double f = 80;
	double scale = 6;
	double startTime = getTime();
	double speed = 3.1;
	int torsoI = 110;
	int headI = 210;
	int bambooCopterI = 15;
	int limbI = 50;
	double bambooSize = 0.9;
	double headSize = 10;
	double torsoSize = headSize * 0.7;
	double limbSize = headSize * 0.2;
	Color background = new Color(135, 206, 235);
	Color bamboo = new Color(255, 215, 0);
	double temp[] = new double[4];
	Matrix m = new Matrix();
  
	Geometry bambooCopterLeft, bambooCopterRight, bambooCopterCenter,
		 bambooCopterStick, head, nose, torso, bell, armLeft, armRight, 
		 handLeft, handRight, legRight, legLeft, 
		 footRight, footLeft;
	Geometry all, body, neck, shoulderLeft, shoulderRight, 
		 hipLeft, hipRight, bambooCopter;

	public Test () {
//		all		= new Geometry();
		System.out.println("in Hear!!!1");
		initialize();

	}

	public void initialize() {
		all = new Geometry();
		all.globe(10);
		System.out.println("in Hear!!!2");
		
	}


	public void render(Graphics g) {
		double time = getTime() - startTime;
		width = getWidth();
		height = getHeight();
		g.setColor(background);
		g.fillRect(0,0,width,height);
//		all		= new Geometry();
//		all.relativeMatrix.identity();
//		all.relativeMatrix.printMatrix(all.relativeMatrix);
//		System.out.println();


		animate(time);
		// traverse and draw
		Matrix mm = new Matrix();
		mm.identity();
		drawIt(all, mm, g);
	}

	public void drawIt(Geometry object, Matrix matrix, Graphics g) {
		int childNumber = object.getNumChildren();
		Matrix relativeMatrix = object.getMatrix();
		matrix.multiply(relativeMatrix);
		object.globalMatrix.copy(matrix);
		if (childNumber == 0) {
			transformAndRender(object, matrix, g, Color.blue);
		} else {
			System.out.println("in Hear!!!3");
			for (int i=0; i<childNumber; i++) {
				Geometry child = object.getChild(i);
				drawIt(child, object.globalMatrix, g);
			}
		}
	}
	public void animate(double time) {
		m = all.getMatrix();			// all!!!
		m.identity();
		m.scale(10,10,10);
		m.printMatrix(m);
		System.out.println();


/*		m.perspect(f);
		m.rotateY(Math.sin(time)*0.2);
		m.rotateY(time);
		m.translate(0,0,headSize*2);
		m.rotateY(-time);
		m.translate(0, 20.0*bambooSize, 0); */
	}
   public void transformAndRender(Geometry geo, Matrix m, Graphics g, Color color) {
	   g.setColor(color);
//	   int draw[][] = new int[geo.vertices.length][geo.vertices[0].length];		// changed this array into Geometry class!
	   for (int i=0; i<geo.vertices.length; i++) {
		m.transform(geo.vertices[i],temp);
		temp[0] = temp[0]/temp[3];
		temp[1] = temp[1]/temp[3];
		temp[2] = temp[2]/temp[3];
		viewport(temp,geo.draw[i]);
	   }
	   for (int i=0; i<geo.faces.length; i++){
		for (int j=0; j<geo.faces[i].length; j++) {
			if (j<geo.faces[i].length-1) {
				g.drawLine((int)geo.draw[geo.faces[i][j]][0], (int)geo.draw[geo.faces[i][j]][1], (int)geo.draw[geo.faces[i][j+1]][0], (int)geo.draw[geo.faces[i][j+1]][1]);
			} else {
				g.drawLine((int)geo.draw[geo.faces[i][j]][0], (int)geo.draw[geo.faces[i][j]][1], (int)geo.draw[geo.faces[i][0]][0], (int)geo.draw[geo.faces[i][0]][1]);
			}
		}
	   }
   }


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

