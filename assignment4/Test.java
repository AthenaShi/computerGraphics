import java.awt.*;

public class Test extends BufferedApplet
{
	int width = 0;
	int height = 0;
	double scale = 1;
	double startTime = getTime();
	double speed = 1;
	double r = 100;	// acctual r = 100/scale

	public void render(Graphics g) {
		double time = getTime() - startTime;
		width = getWidth();
		height = getHeight();
	//	scale = width / 2;
		g.setColor(Color.white);
		g.fillRect(0,0,width,height);

		g.setColor(Color.black);
		
		Matrix matrix = new Matrix();
		matrix.identity();
//		matrix.rotateY(Math.PI/2.0);
//		matrix.rotateZ(speed * time);
//		matrix.rotateX(speed * time);
		matrix.rotateY(speed * time);
		matrix.scale(50,50,100);
	//	m.rotateY(Math.PI/2.0*3.5);

		double verticesOld[][] = new double[24][4];
		double vertices[][] = new double[24][4];
		int drawVertices[][] = new int[24][4];
		int faces[][] = new int[6][4];

		// cube test
		Geometry geo = new Geometry();
		geo.cube();
		geo.copy(verticesOld, faces);
		System.out.println("before");
		System.out.println(verticesOld.length);
		System.out.println(vertices.length);
		matrix.printMatrix(verticesOld);
		for (int i=0; i<vertices.length; i++) {
			matrix.transform(verticesOld[i],vertices[i]);
			viewport(vertices[i],drawVertices[i]);
		}
		System.out.println("after");
		matrix.printMatrix(vertices);
		System.out.println("afterAll");
		matrix.printMatrix(drawVertices);

			// draw cube
		for (int i=0; i<faces.length; i++){
			for (int j=0; j<faces[i].length; j++) {
				if (j<faces[i].length-1) {
					g.drawLine((int)drawVertices[faces[i][j]][0], (int)drawVertices[faces[i][j]][1], (int)drawVertices[faces[i][j+1]][0], (int)drawVertices[faces[i][j+1]][1]);
				} else {
					g.drawLine((int)drawVertices[faces[i][j]][0], (int)drawVertices[faces[i][j]][1], (int)drawVertices[faces[i][0]][0], (int)drawVertices[faces[i][0]][1]);
				}
	//			System.out.println("x: "+(int)drawVertices[faces[i][j]][0] + "\ty:"+(int)drawVertices[faces[i][j]][1]);
			}
		}

/*		// globe test
		g.setColor(Color.red);
	//	Geometry geo2 = new Geometry();
		int m=10,n=10;
		geo.globe(m,n);
		verticesOld = new double[(m+1)*(n+1)][4];
		vertices = new double[(m+1)*(n+1)][4];
		drawVertices = new int[(m+1)*(n+1)][4];
		faces = new int[m*n][4];
		geo.copy(verticesOld, faces);
		for (int i=0; i<vertices.length; i++) {
			matrix.transform(verticesOld[i],vertices[i]);
			viewport(vertices[i],drawVertices[i]);
		}
			// draw globe
		for (int i=0; i<faces.length; i++){
			for (int j=0; j<faces[i].length; j++) {
				if (j<faces[i].length-1) {
					g.drawLine((int)drawVertices[faces[i][j]][0], (int)drawVertices[faces[i][j]][1], (int)drawVertices[faces[i][j+1]][0], (int)drawVertices[faces[i][j+1]][1]);
				} else {
					g.drawLine((int)drawVertices[faces[i][j]][0], (int)drawVertices[faces[i][j]][1], (int)drawVertices[faces[i][0]][0], (int)drawVertices[faces[i][0]][1]);
				}
	//			System.out.println("x: "+(int)drawVertices[faces[i][j]][0] + "\ty:"+(int)drawVertices[faces[i][j]][1]);
			}
		} 

		// cylinder test
		g.setColor(Color.blue);
//		Geometry geo3 = new Geometry();
		int z=10,r=10;
	//	int n=10,z=10,r=10;
		geo.cylinder(n,z,r);
		verticesOld = new double[(n+1)*(z+1)+2*(n+1)*(r+1)][4];
		vertices = new double[(n+1)*(z+1)+2*(n+1)*(r+1)][4];
		drawVertices = new int[(n+1)*(z+1)+2*(n+1)*(r+1)][4];
		faces = new int[n*z+2*n*r][4];
		geo.copy(verticesOld, faces);
		for (int i=0; i<vertices.length; i++) {
			matrix.transform(verticesOld[i],vertices[i]);
			viewport(vertices[i],drawVertices[i]);
		}
			// draw globe
		for (int i=0; i<faces.length; i++){
			for (int j=0; j<faces[i].length; j++) {
				if (j<faces[i].length-1) {
					g.drawLine((int)drawVertices[faces[i][j]][0], (int)drawVertices[faces[i][j]][1], (int)drawVertices[faces[i][j+1]][0], (int)drawVertices[faces[i][j+1]][1]);
				} else {
					g.drawLine((int)drawVertices[faces[i][j]][0], (int)drawVertices[faces[i][j]][1], (int)drawVertices[faces[i][0]][0], (int)drawVertices[faces[i][0]][1]);
				}
	//			System.out.println("x: "+(int)drawVertices[faces[i][j]][0] + "\ty:"+(int)drawVertices[faces[i][j]][1]);
			}
		} */











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

