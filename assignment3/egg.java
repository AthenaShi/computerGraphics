import java.awt.*;

public class egg extends BufferedApplet
{
	int width = 0;
	int height = 0;
	double scale = 0;
	double startTime = getTime();
	double speed = 1;
	double r = 100;
	double eyeA = 0.25*r;
	double eyeB = 0.45*r;
	double eyeC = 0.25*r;

	public void render(Graphics g) {
		double time = getTime() - startTime;

		width = getWidth();
		height = getHeight();
		scale = width / 20;
		g.setColor(Color.white);
		g.fillRect(0,0,width,height);

		g.setColor(Color.black);
		double headCenter[] = {0,0,0,1};

		// calculate headPatch[][]
		double startX = -0.7;
		double endX = 1.0;
		double bootstrap = 0.1;
		int n = (int)( (endX-startX)/bootstrap) + 1;
		double headPatchs[][] = new double[n][4];
		double x=0,y=0,z=0;
		for (int i=0; i<n; i++) {
			x = startX;
			y = Math.sqrt( 1 - Math.pow(x-0.3,2) ) - 0.7;
			if (1 - Math.pow(x,2) - Math.pow(y,2) >= 0)
				z = Math.sqrt( 1 - Math.pow(x,2) - Math.pow(y,2) );
			else
				z = 0;
			startX += bootstrap;
			headPatchs[i][0] = x *r/scale;
			headPatchs[i][1] = y *r/scale;
			headPatchs[i][2] = z *r/scale;
			headPatchs[i][3] = 1;
			//System.out.println("X: "+x+"\tY: "+y+"\tZ: "+z);
		}
		System.out.println("start calculate");

				// calculate eyeWhite[][]
		double startY = 0.7;
		double endY = 1.6;
		bootstrap = 0.01;
		int nWhite = (int)( (endY-startY)/bootstrap) + 1;
		double eyeWhite1[][] = new double[nWhite*2][4];
		double eyeWhite2[][] = new double[nWhite*2][4];
		for (int i=0; i<nWhite; i++) {
			y = startY;
			x = -Math.sqrt(1 - Math.pow((y-1)/(eyeB/r),2)) * eyeA/r + 0.44;
			if (1 - Math.pow((x-1.0/6.0)/0.25,2) - Math.pow((y-1.15)/0.45,2) < 0) {
				System.out.println(Math.pow((x-1.0/6.0)/0.25,2) +"+"+ Math.pow((y-1.15)/0.45,2));

			}

			z = Math.sqrt( 1 - Math.pow((x-1.0/6.0)/0.25,2) - Math.pow((y-1.15)/0.45,2) ) * eyeC/r + 0.15;
		//	else
		//		z = 0;
			startY += bootstrap;
			eyeWhite1[i][0] = x *r/scale;
			eyeWhite1[i][1] = y *r/scale;
			eyeWhite1[i][2] = z *r/scale;
			eyeWhite1[i][3] = 1;
			eyeWhite2[i][0] = x *r/scale;
			eyeWhite2[i][1] = y *r/scale;
			eyeWhite2[i][2] = -z *r/scale;
			eyeWhite2[i][3] = 1;
			System.out.print("X: "+x+"\tY: "+y+"\tZ: "+z);
			
			if ( Math.pow((x-1.0/6.0)/0.25,2) + Math.pow((y-1.15)/0.45,2) <= 1 )
				System.out.println("\tYES!");
			else
				System.out.println();
		}




		
		double tempHead[] = new double[4];
		int drawHead[] = new int[4];
		double tempHeadPatchs[][] = new double[n][4];
		int drawHeadPatch[][] = new int[n][4];

		Matrix m = new Matrix();
		m.identity();
	//	m.rotateY(speed * time);
	//	m.rotateY(Math.PI/2*3.5);

		m.transform(headCenter,tempHead);
		for (int i=0; i<n; i++) {
			m.transform(headPatchs[i], tempHeadPatchs[i]);
			viewport(tempHeadPatchs[i],drawHeadPatch[i]);
		}
		viewport(tempHead,drawHead);

		g.setColor(Color.blue);
		int coHead[] = new int[4];
		coHead = coTransform(drawHead, r, r);
		g.drawOval(coHead[0], coHead[1], coHead[2], coHead[3]);
	//	g.fillOval(coHead[0], coHead[1], coHead[2], coHead[3]);
		int patchX[] = new int[n];
		int patchY[] = new int[n];
		for (int i=0; i<n; i++) {
			patchX[i] = drawHeadPatch[i][0];
			patchY[i] = drawHeadPatch[i][1];
		}
		g.drawPolygon(patchX, patchY,n);

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

