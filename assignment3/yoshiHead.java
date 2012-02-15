import java.awt.*;

public class yoshiHead extends BufferedApplet
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
		// calculate headPatch[][]
		double startX = -0.7;
		double endX = 1.0;
		double bootstrap = 0.1;
		int n = (int)( (endX-startX)/bootstrap) + 1;
		double headPatchs1[][] = new double[n][4];
		double headPatchs2[][] = new double[n][4];
		double x=0,y=0,z=0;
		//System.out.println("start calculate");
		for (int i=0; i<n; i++) {
			x = startX;
			y = Math.sqrt( 1 - Math.pow(x-0.3,2) ) - 0.7;
			if (1 - Math.pow(x,2) - Math.pow(y,2) >= 0)
				z = Math.sqrt( 1 - Math.pow(x,2) - Math.pow(y,2) );
			else
				z = 0;
			startX += bootstrap;
			headPatchs1[i][0] = x *r/scale;
			headPatchs1[i][1] = y *r/scale;
			headPatchs1[i][2] = z *r/scale;
			headPatchs1[i][3] = 1;
			headPatchs2[i][0] = x *r/scale;
			headPatchs2[i][1] = y *r/scale;
			headPatchs2[i][2] = -z *r/scale;
			headPatchs2[i][3] = 1;
			//System.out.println("X: "+x+"\tY: "+y+"\tZ: "+z);
		}
		//System.out.println("end calculate");
			
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
		double tempHeadPatchs1[][] = new double[n][4];
		double tempFrontPatchs1[][] = new double[n][4];
		double tempHeadPatchs2[][] = new double[n][4];
		double tempFrontPatchs2[][] = new double[n][4];
		int drawHeadPatchs1[][] = new int[n][4];
		int drawHeadPatchs2[][] = new int[n][4];
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
			// head patch 1
		int nFront1 = 0, nFront2 = 0;
		int frontStartIndex1 = 0, frontStartIndex2 = 0;
		double startPatch1[] = new double[4];
		double endPatch1[] = new double[4];
		double startPatch2[] = new double[4];
		double endPatch2[] = new double[4];
		boolean counted1 = false;
		boolean counted2 = false;
		for (int i=0; i<n; i++) {
			m.transform(headPatchs1[i], tempHeadPatchs1[i]);
			m.transform(headPatchs2[i], tempHeadPatchs2[i]);
			if (tempHeadPatchs1[i][2] >= 0) {	// a front patch
				nFront1++;
				if (!counted1) {
					frontStartIndex1 = i;
					for (int j=0; j<4; j++)
						startPatch1[j] = tempHeadPatchs1[i][j];
					counted1 = true;
				} else
					for (int j=0; j<4; j++)
						endPatch1[j] = tempHeadPatchs1[i][j];	
			}
			if (tempHeadPatchs2[i][2] >= 0) {	// a front patch
				nFront2++;
				if (!counted2) {
					frontStartIndex2 = i;
					for (int j=0; j<4; j++)
						startPatch2[j] = tempHeadPatchs2[i][j];
					counted2 = true;
				} else
					for (int j=0; j<4; j++)
						endPatch2[j] = tempHeadPatchs2[i][j];	
			}
		}
				// calculate coordinates of lower patches x^2 + y^2 +z^2 = 1
		int l = 18;
		double bootStrap1 = (startPatch1[0]-endPatch1[0])/(double)(l-1);
		double bootStrap2 = (startPatch2[0]-endPatch2[0])/(double)(l-1);

		double lowerPatchs1[][] = new double[l][2];
		int drawLowerPatchs1[][] = new int[l][2];
		double startXL1 = endPatch1[0];
		double xL=0,yL=0;
		for (int i=0; i<l; i++) {
			xL = startXL1/(r/scale);
			yL = -Math.sqrt( 1 - Math.pow(xL,2) );
			startXL1 += bootStrap1;
			lowerPatchs1[i][0] = xL *r/scale;
			lowerPatchs1[i][1] = yL *r/scale;
		}
		double lowerPatchs2[][] = new double[l][2];
		int drawLowerPatchs2[][] = new int[l][2];
		double startXL2 = endPatch2[0];
		xL=0;yL=0;
		for (int i=0; i<l; i++) {
			xL = startXL2/(r/scale);
			yL = -Math.sqrt( 1 - Math.pow(xL,2) );
			startXL2 += bootStrap2;
			lowerPatchs2[i][0] = xL *r/scale;
			lowerPatchs2[i][1] = yL *r/scale;
		}
	
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
		for (int i=0; i<n; i++) {
			viewport(tempHeadPatchs1[i],drawHeadPatchs1[i]);
			viewport(tempHeadPatchs2[i],drawHeadPatchs2[i]);
		}
		for (int i=0; i<l; i++) {
			viewport(lowerPatchs1[i],drawLowerPatchs1[i]);
			viewport(lowerPatchs2[i],drawLowerPatchs2[i]);
		}

		int patchX1[] = new int[nFront1+l];
		int patchY1[] = new int[nFront1+l];
		for (int i=0; i<nFront1; i++) {
			patchX1[i] = drawHeadPatchs1[i+frontStartIndex1][0];
			patchY1[i] = drawHeadPatchs1[i+frontStartIndex1][1];
		}
		for (int i=nFront1; i<nFront1+l; i++) {
			patchX1[i] = drawLowerPatchs1[i-nFront1][0];
			patchY1[i] = drawLowerPatchs1[i-nFront1][1];
		}
		int patchX2[] = new int[nFront2+l];
		int patchY2[] = new int[nFront2+l];
		for (int i=0; i<nFront2; i++) {
			patchX2[i] = drawHeadPatchs2[i+frontStartIndex2][0];
			patchY2[i] = drawHeadPatchs2[i+frontStartIndex2][1];
		}
		for (int i=nFront2; i<nFront2+l; i++) {
			patchX2[i] = drawLowerPatchs2[i-nFront2][0];
			patchY2[i] = drawLowerPatchs2[i-nFront2][1];
		}
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

			// draw head
			g.setColor(Color.blue);
			int coHead[] = new int[4];
			coHead = coTransform(drawHead, r, r);
			g.drawOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			g.fillOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			// draw head patch by point
			g.setColor(Color.white);
			g.drawPolygon(patchX1, patchY1,nFront1+l);
			g.fillPolygon(patchX1, patchY1,nFront1+l);
			g.drawPolygon(patchX2, patchY2,nFront2+l);
			g.fillPolygon(patchX2, patchY2,nFront2+l);

		} else {				// front
			// draw head
			g.setColor(Color.blue);
			int coHead[] = new int[4];
			coHead = coTransform(drawHead, r, r);
			g.drawOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			g.fillOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			g.setColor(Color.green);
			// draw head patch by point
			g.setColor(Color.white);
			g.drawPolygon(patchX1, patchY1,nFront1+l);
			g.fillPolygon(patchX1, patchY1,nFront1+l);
			g.drawPolygon(patchX2, patchY2,nFront2+l);
			g.fillPolygon(patchX2, patchY2,nFront2+l);

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

