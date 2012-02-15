import java.awt.*;

public class egg extends BufferedApplet
{
	int width = 0;
	int height = 0;
	double scale = 0;
	double startTime = getTime();
	double speed = 1;
	double r = 100;	// acctual r = 100/scale
	double noseDistance = 1.7;

	public void render(Graphics g) {
		double time = getTime() - startTime;

		width = getWidth();
		height = getHeight();
		scale = width / 2;
		g.setColor(Color.white);
		g.fillRect(0,0,width,height);
		g.setColor(Color.black);
		g.drawLine(0,height/2,width,height/2);
		g.drawLine(0,height/2-100,width,height/2-100);
		g.drawLine(0,height/2+100,width,height/2+100);
		g.drawLine(width/2,0,width/2,height);

		// in (0,0) coordinates drawing...
		// head: ball (0,0,0), r=100
		double headCenter[] = {0,0,0,1};
		double headPatch[] = {0.3*r/scale, -0.7*r/scale, 0, 1};	//=========
		// nose: ball ( r,0,0 ), r=100
		double noseCenter[] = {noseDistance*r/scale,0,0,1};
		// eyes: 2 * oval ball (r/6, r*1.15, +/-r*0.15), a = 0.25, b = 0.45, c = 0.25
		double eyeCenter1[] = {r/scale/6,r/scale*1.15,r/scale*0.15,1};
		double eyeCenter2[] = {r/scale/6,r/scale*1.15,-r/scale*0.15,1};
		double eyeA = 0.25*r;
		double eyeB = 0.45*r;
		double eyeC = 0.25*r;
		// line poles
		double pole1Start[] = { 2.5*r/scale, Math.sqrt( 1-Math.pow(0.1,2)-Math.pow(2.5-noseDistance,2) ) *r/scale, 0.1*r/scale ,1};
		double pole1End[] = { 2.4*r/scale, Math.sqrt( 1-Math.pow(0.2,2)-Math.pow(2.4-noseDistance,2) ) *r/scale, 0.2*r/scale ,1};
		double pole2Start[] = { 2.5*r/scale, Math.sqrt( 1-Math.pow(0.1,2)-Math.pow(2.5-noseDistance,2) ) *r/scale, -0.1*r/scale ,1};
		double pole2End[] = { 2.4*r/scale, Math.sqrt( 1-Math.pow(0.2,2)-Math.pow(2.4-noseDistance,2) ) *r/scale, -0.2*r/scale ,1};

		// moving...?
		double tempHead[] = new double[4];
		int drawHead[] = new int[4];
		double tempNose[] = new double[4];
		int drawNose[] = new int[4];
		double tempEye1[] = new double[4];
		int drawEye1[] = new int[4];
		double tempEye2[] = new double[4];
		int drawEye2[] = new int[4];
			// line poles
		double tempPole1start[] = new double[4];
		int drawPole1start[] = new int[4];
		double tempPole2start[] = new double[4];
		int drawPole2start[] = new int[4];
		double tempPole1end[] = new double[4];
		int drawPole1end[] = new int[4];
		double tempPole2end[] = new double[4];
		int drawPole2end[] = new int[4];
			// head patch
		double tempHeadPatch[] = new double[4];
		int drawHeadPatch[] = new int[4];

		Matrix m = new Matrix();
		m.identity();
		m.rotateY(speed * time);
	//	m.rotateY(Math.PI/2*3.5);

		m.transform(headCenter,tempHead);
		m.transform(noseCenter,tempNose);
		m.transform(eyeCenter1,tempEye1);
		m.transform(eyeCenter2,tempEye2);
			// line poles
		m.transform(pole1Start,tempPole1start);
		m.transform(pole2Start,tempPole2start);
		m.transform(pole1End,tempPole1end);
		m.transform(pole2End,tempPole2end);
			// head patch
		m.transform(headPatch,tempHeadPatch);
	
		// change to weight * height view point
		viewport(tempHead,drawHead);
		viewport(tempNose,drawNose);
		viewport(tempEye1,drawEye1);
		viewport(tempEye2,drawEye2);
			// line poles
		viewport(tempPole1start,drawPole1start);
		viewport(tempPole2start,drawPole2start);
		viewport(tempPole1end,drawPole1end);
		viewport(tempPole2end,drawPole2end);
			// head patch
		viewport(tempHeadPatch,drawHeadPatch);
		System.out.println("headPatch: X: " + tempHeadPatch[0]+"\tY: " + tempHeadPatch[1]);
		System.out.println("afterPatch: X: " + drawHeadPatch[0]+"\tY: " + drawHeadPatch[1]);

	
		if (Math.sin(speed*time) >= 0) {	// back
		//if (false) {	// back
			// draw nose
			g.setColor(Color.green);
			int coNose[] = new int[4];
			coNose = coTransform(drawNose, r, r);
			g.drawOval(coNose[0], coNose[1], coNose[2], coNose[3]);
			g.fillOval(coNose[0], coNose[1], coNose[2], coNose[3]);

			// draw line poles
			g.setColor(Color.black);
			g.drawLine(drawPole1start[0],drawPole1start[1],drawPole1end[0],drawPole1end[1]);
			g.drawLine(drawPole2start[0],drawPole2start[1],drawPole2end[0],drawPole2end[1]);
			g.setColor(Color.green);

			// draw eyes
			g.setColor(Color.green);
			int coEye1[] = new int[4];
			int coEye2[] = new int[4];
			coEye1 = coTransform(drawEye1, eyeA, eyeB);
			coEye2 = coTransform(drawEye2, eyeA, eyeB);
			g.drawOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
			g.fillOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
			g.drawOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);
			g.fillOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);

			// draw head patch
			g.setColor(Color.white);
			int coHeadPatch[] = new int[4];
			coHeadPatch = coTransform(drawHeadPatch, r, r);
			g.drawOval(coHeadPatch[0], coHeadPatch[1], coHeadPatch[2], coHeadPatch[3]);
			g.fillOval(coHeadPatch[0], coHeadPatch[1], coHeadPatch[2], coHeadPatch[3]);
			g.setColor(Color.green);
			// draw head
			g.setColor(Color.blue);
			int coHead[] = new int[4];
			coHead = coTransform(drawHead, r, r);
			g.drawOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			g.fillOval(coHead[0], coHead[1], coHead[2], coHead[3]);
		} else {				// front
			// draw head
			g.setColor(Color.blue);
			int coHead[] = new int[4];
			coHead = coTransform(drawHead, r, r);
			g.drawOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			g.fillOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			g.setColor(Color.green);
			// draw head patch
			g.setColor(Color.white);
			int coHeadPatch[] = new int[4];
			coHeadPatch = coTransform(drawHeadPatch, r, r);
			g.drawOval(coHeadPatch[0], coHeadPatch[1], coHeadPatch[2], coHeadPatch[3]);
			g.fillOval(coHeadPatch[0], coHeadPatch[1], coHeadPatch[2], coHeadPatch[3]);
			g.setColor(Color.green);

			// draw eyes
			g.setColor(Color.green);
			int coEye1[] = new int[4];
			int coEye2[] = new int[4];
			coEye1 = coTransform(drawEye1, eyeA, eyeB);
			coEye2 = coTransform(drawEye2, eyeA, eyeB);
			g.drawOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
			g.drawOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);
			g.fillOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
			g.fillOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);

			// draw nose
			g.setColor(Color.green);
			int coNose[] = new int[4];
			coNose = coTransform(drawNose, r, r);
			g.drawOval(coNose[0], coNose[1], coNose[2], coNose[3]);
			g.fillOval(coNose[0], coNose[1], coNose[2], coNose[3]);
			
			// draw line poles
			g.setColor(Color.black);
			g.drawLine(drawPole1start[0],drawPole1start[1],drawPole1end[0],drawPole1end[1]);
			g.drawLine(drawPole2start[0],drawPole2start[1],drawPole2end[0],drawPole2end[1]);
			g.setColor(Color.green);
		
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

