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
	double eyeA = 0.25*r;
	double eyeB = 0.45*r;
	double eyeC = 0.25*r;

	public void render(Graphics g) {
		double time = getTime() - startTime;
		width = getWidth();
		height = getHeight();
		scale = width / 2;
		g.setColor(new Color(65, 105, 225));
		g.fillRect(0,0,width,height);
//		g.setColor(Color.black);
//		g.drawLine(0,height/2,width,height/2);
//		g.drawLine(0,height/2-100,width,height/2-100);
//		g.drawLine(0,height/2+100,width,height/2+100);
//		g.drawLine(width/2,0,width/2,height);

		// in (0,0) coordinates drawing...
		// head: ball (0,0,0), r=100
		double headCenter[] = {0,0,0,1};
		// calculate headPatch[][]
		double startX = -0.7;
		double endX = 1.0;
		double bootstrap = 0.001;
		int n = (int)( (endX-startX)/bootstrap) + 1;
		double headPatchs1[][] = new double[n][4];
		double headPatchs2[][] = new double[n][4];
		double x=0,y=0,z=0;
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
		}
		// calculate eyeWhite[][]
		double startY = 0.73;
		double endY = 1.42;
		bootstrap = 0.01;
		int nWhite = (int)( (endY-startY)/bootstrap) + 1;
		double eyeWhite1[][] = new double[nWhite*2][4];
		double eyeWhite2[][] = new double[nWhite*2][4];
		//System.out.println("firstHalf========");
		for (int i=0; i<nWhite; i++) {
			y = startY;
			x = -Math.sqrt(1 - Math.pow((y-1)/(eyeB/r),2)) * eyeA/r + 0.44;
			z = Math.sqrt( 1 - Math.pow((x-1.0/6.0)/(eyeA/r),2) - Math.pow((y-1.15)/(eyeB/r),2) ) * eyeC/r + 0.15;
			startY += bootstrap;
			eyeWhite1[i][0] = x *r/scale;
			eyeWhite1[i][1] = y *r/scale;
			eyeWhite1[i][2] = z *r/scale;
			eyeWhite1[i][3] = 1;
			eyeWhite2[i][0] = x *r/scale;
			eyeWhite2[i][1] = y *r/scale;
			eyeWhite2[i][2] = -z *r/scale;
			eyeWhite2[i][3] = 1;
			//System.out.println("X: "+eyeWhite1[i][0]+"\tY: "+eyeWhite1[i][1]+"\tZ: "+eyeWhite1[i][2]);
		}
		for (int i=nWhite; i<2*nWhite; i++) {
			eyeWhite1[i][0] = eyeWhite1[2*nWhite-i-1][0];
			eyeWhite1[i][1] = eyeWhite1[2*nWhite-i-1][1];
			eyeWhite1[i][2] = 2*0.15*r/scale - eyeWhite1[2*nWhite-i-1][2];
			eyeWhite1[i][3] = 1;
			eyeWhite2[i][0] = eyeWhite2[2*nWhite-i-1][0];
			eyeWhite2[i][1] = eyeWhite2[2*nWhite-i-1][1];
			eyeWhite2[i][2] = -2*0.15*r/scale - eyeWhite2[2*nWhite-i-1][2];
			eyeWhite2[i][3] = 1;
			//System.out.println("X: "+eyeWhite1[i][0]+"\tY: "+eyeWhite1[i][1]+"\tZ: "+eyeWhite1[i][2]);
		}

		// nose: ball ( r,0,0 ), r=100
		double noseCenter[] = {noseDistance*r/scale,0,0,1};
		// eyes: 2 * oval ball (r/6, r*1.15, +/-r*0.15), a = 0.25, b = 0.45, c = 0.25
		double eyeCenter1[] = {r/scale/6.0,r/scale*1.15,r/scale*0.15,1};
		double eyeCenter2[] = {r/scale/6.0,r/scale*1.15,-r/scale*0.15,1};
		// line poles
		double pole1Start[] = { 2.5*r/scale, Math.sqrt( 1-Math.pow(0.1,2)-Math.pow(2.5-noseDistance,2) ) *r/scale, 0.1*r/scale ,1};
		double pole1End[] = { 2.4*r/scale, Math.sqrt( 1-Math.pow(0.2,2)-Math.pow(2.4-noseDistance,2) ) *r/scale, 0.2*r/scale ,1};
		double pole2Start[] = { 2.5*r/scale, Math.sqrt( 1-Math.pow(0.1,2)-Math.pow(2.5-noseDistance,2) ) *r/scale, -0.1*r/scale ,1};
		double pole2End[] = { 2.4*r/scale, Math.sqrt( 1-Math.pow(0.2,2)-Math.pow(2.4-noseDistance,2) ) *r/scale, -0.2*r/scale ,1};
		// eye black	[(y-1.15)/0.1]^2 +[(z-0.15)/0.05]^2 = 1
		double blackX = 0.40;
		double blackYup = Math.sqrt( 1 - Math.pow((blackX-1.0/6.0)/0.25,2) ) * 0.45 + 1.15;//	1.25;
		double blackYlo = -Math.sqrt( 1 - Math.pow((blackX-1.0/6.0)/0.25,2) ) * 0.45 + 1.15;//	1.05;
		double dy = (blackYup-blackYlo)/2.0;
		int nBlack = (int)((blackYup - blackYlo)/0.01) + 1;
		double eyeBlack1[][] = new double[nBlack*2][4];
		double eyeBlack2[][] = new double[nBlack*2][4];
		for (int i=0; i<nBlack; i++) {
			y = blackYlo;
			x = blackX;
			if (Math.pow((y-1.15)/dy,2) >= 1)
				z = 0.15;
			else
				z = Math.sqrt( 1 - Math.pow((y-1.15)/dy,2) ) * 0.08 + 0.15;
			System.out.println("x: "+x+"\ty: "+y+"\tz: "+z);
			blackYlo += 0.01;
			eyeBlack1[i][0] = x *r/scale;
			eyeBlack1[i][1] = y *r/scale;
			eyeBlack1[i][2] = z *r/scale;
			eyeBlack1[i][3] = 1;
			eyeBlack2[i][0] = x *r/scale;
			eyeBlack2[i][1] = y *r/scale;
			eyeBlack2[i][2] = -z *r/scale;
			eyeBlack2[i][3] = 1;
			//System.out.println("X: "+eyeBlack1[i][0]+"\tY: "+eyeBlack1[i][1]+"\tZ: "+eyeBlack1[i][2]);
		}
		for (int i=nBlack; i<2*nBlack; i++) {
			eyeBlack1[i][0] = eyeBlack1[2*nBlack-i-1][0];
			eyeBlack1[i][1] = eyeBlack1[2*nBlack-i-1][1];
			eyeBlack1[i][2] = 2*0.15*r/scale - eyeBlack1[2*nBlack-i-1][2];
			eyeBlack1[i][3] = 1;
			eyeBlack2[i][0] = eyeBlack2[2*nBlack-i-1][0];
			eyeBlack2[i][1] = eyeBlack2[2*nBlack-i-1][1];
			eyeBlack2[i][2] = -2*0.15*r/scale - eyeBlack2[2*nBlack-i-1][2];
			eyeBlack2[i][3] = 1;
			//System.out.println("X: "+eyeBlack1[i][0]+"\tY: "+eyeBlack1[i][1]+"\tZ: "+eyeBlack1[i][2]);
		}
		// dragon 3
		double dragon[][] = {
			{-0.41*r/scale, 0.91*r/scale,0,1},
			{-1*r/scale, 1*r/scale,0,1},
			{-0.85*r/scale, 0.53*r/scale,0,1},
			{-1.4*r/scale, 0.4*r/scale,0,1},
			{-0.999*r/scale, 0.04*r/scale,0,1},
			{-1.4*r/scale, -0.22*r/scale,0,1},
			{-0.9*r/scale, -0.44*r/scale,0,1},
			{-0.999*r/scale, 0.04*r/scale,0,1},
			{-0.85*r/scale, 0.53*r/scale,0,1},
		};

		// moving...?
		double tempHead[] = new double[4];
		int drawHead[] = new int[4];
		// dragon
		double tempDragon[][] = new double[9][4];
		int drawDragon[][] = new int[9][4];
		// head patch
		double tempHeadPatchs1[][] = new double[n][4];
		double tempFrontPatchs1[][] = new double[n][4];
		double tempHeadPatchs2[][] = new double[n][4];
		double tempFrontPatchs2[][] = new double[n][4];
		int drawHeadPatchs1[][] = new int[n][4];
		int drawHeadPatchs2[][] = new int[n][4];
		// eye white
		double tempEyeWhite1[][] = new double[2*nWhite][4];
		double tempFrontWhite1[][] = new double[2*nWhite][4];
		double tempEyeWhite2[][] = new double[2*nWhite][4];
		double tempFrontWhite2[][] = new double[2*nWhite][4];
		int drawEyeWhite1[][] = new int[2*nWhite][4];
		int drawEyeWhite2[][] = new int[2*nWhite][4];
		// eye black
		double tempEyeBlack1[][] = new double[2*nBlack][4];
		double tempEyeBlack2[][] = new double[2*nBlack][4];
		int drawEyeBlack1[][] = new int[2*nBlack][4];
		int drawEyeBlack2[][] = new int[2*nBlack][4];
		// nose
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
	//	m.rotateY(Math.PI/2.0*3.5);

		// trasnform
		m.transform(headCenter,tempHead);
		m.transform(noseCenter,tempNose);
		m.transform(eyeCenter1,tempEye1);
		m.transform(eyeCenter2,tempEye2);
		// line poles
		m.transform(pole1Start,tempPole1start);
		m.transform(pole2Start,tempPole2start);
		m.transform(pole1End,tempPole1end);
		m.transform(pole2End,tempPole2end);
		// dragon
		for (int i=0; i<9; i++) {
			m.transform(dragon[i], tempDragon[i]);
		}
		// eye black
		for (int i=0; i<2*nBlack; i++) {
			m.transform(eyeBlack1[i], tempEyeBlack1[i]);
			m.transform(eyeBlack2[i], tempEyeBlack2[i]);
		}
		// eye white
		int nFrontWhite1 = 0, nFrontWhite2 = 0;
		int frontWhiteStartIndex1 = 0, frontWhiteStartIndex2 = 0;
		double startWhite1[] = new double[4];
		double endWhite1[] = new double[4];
		double startWhite2[] = new double[4];
		double endWhite2[] = new double[4];
		boolean counted1 = false;
		boolean counted2 = false;
		for (int i=0; i<2*nWhite; i++) {
			m.transform(eyeWhite1[i], tempEyeWhite1[i]);
			m.transform(eyeWhite2[i], tempEyeWhite2[i]);
			if (tempEyeWhite1[i][2] > tempEye1[2]) {	// a front white
				if (!counted1) {
					frontWhiteStartIndex1 = i;
					for (int j=0; j<4; j++) {
						startWhite1[j] = tempEyeWhite1[i][j];
						endWhite1[j] = tempEyeWhite1[i][j];
					}
					counted1 = true;
				} else {
					if (Math.abs(tempEyeWhite1[i][1]-endWhite1[1]) > 0.02) {
						if (nFrontWhite1 < 30) {	// abort what we have
							frontWhiteStartIndex1 = i;
							nFrontWhite1 = 0;
							for (int j=0; j<4; j++) {
								startWhite1[j] = tempEyeWhite1[i][j];
								endWhite1[j] = tempEyeWhite1[i][j];
							}
						} else 
							break;
					} else
						for (int j=0; j<4; j++)
							endWhite1[j] = tempEyeWhite1[i][j];
				}	
				nFrontWhite1++;
			}
			//System.out.println("start index: "+frontWhiteStartIndex1);

			if (tempEyeWhite2[i][2] > tempEye2[2]) {	// a front white
				if (!counted2) {
					frontWhiteStartIndex2 = i;
					for (int j=0; j<4; j++) {
						startWhite2[j] = tempEyeWhite2[i][j];
						endWhite2[j] = tempEyeWhite2[i][j];
					}
					counted2 = true;
				} else {
					if (Math.abs(tempEyeWhite2[i][1]-endWhite2[1]) > 0.02) {
						if (nFrontWhite2 < 30) {	// abort what we have
							frontWhiteStartIndex2 = i;
							nFrontWhite2 = 0;
							for (int j=0; j<4; j++) {
								startWhite2[j] = tempEyeWhite2[i][j];
								endWhite2[j] = tempEyeWhite2[i][j];
							}
						} else 
							break;
					} else
						for (int j=0; j<4; j++)
							endWhite2[j] = tempEyeWhite2[i][j];
				}
				nFrontWhite2++;
			}
		}
		// calculate lower white eye	( x-tempEye1[0] / eyeA/r )^2 + ( y-tempEye1[1] / eyeB/r )^2 +( z-tempEye1[2] / eyeC/r )^2 = 1
		int lWhite = 2*nWhite;
		double bootStrap1 = (startWhite1[1]-endWhite1[1])/(double)(lWhite-1);
		//System.out.println("nFront: "+nFrontWhite1);
		//System.out.println("endWhite: "+startWhite1[1]);
		//System.out.println("startWhite: "+endWhite1[1]);
		//System.out.println("bootStrap1: "+bootStrap1);

		double bootStrap2 = (startWhite2[1]-endWhite2[1])/(double)(lWhite-1);
		double lowerWhite1[][] = new double[lWhite][2];
		int drawLowerWhite1[][] = new int[lWhite][2];
		double startYL1 = endWhite1[1];
		//System.out.println("start=========");
		for (int i=0; i<lWhite; i++) {
			y = startYL1/(r/scale);
			if (speed*time % (2*Math.PI) > Math.PI && speed*time % (2*Math.PI) <= Math.PI*3.0/2.0)	// front left x should be minus
				x = -Math.sqrt( 1 - Math.pow( (y-tempEye1[1]/(r/scale))/(eyeB/r), 2 ) ) *(eyeA/r) + tempEye1[0]/(r/scale);
			else
				x = Math.sqrt( 1 - Math.pow( (y-tempEye1[1]/(r/scale))/(eyeB/r), 2 ) ) *(eyeA/r) + tempEye1[0]/(r/scale);
			startYL1 += bootStrap1;
			lowerWhite1[i][0] = x *r/scale;
			lowerWhite1[i][1] = y *r/scale;
		//	System.out.println("X: "+x+"\tY: "+y);
		}
		double lowerWhite2[][] = new double[lWhite][2];
		int drawLowerWhite2[][] = new int[lWhite][2];
		double startYL2 = endWhite2[1];
		for (int i=0; i<lWhite; i++) {
			y = startYL2/(r/scale);
			if (speed*time % (2*Math.PI) > Math.PI*3.0/2.0)	// front left x should be minus
				x = Math.sqrt( 1 - Math.pow( (y-tempEye2[1]/(r/scale))/(eyeB/r), 2 ) ) *(eyeA/r) + tempEye2[0]/(r/scale);
			else
				x = -Math.sqrt( 1 - Math.pow( (y-tempEye2[1]/(r/scale))/(eyeB/r), 2 ) ) *(eyeA/r) + tempEye2[0]/(r/scale);
			startYL2 += bootStrap2;
			lowerWhite2[i][0] = x *r/scale;
			lowerWhite2[i][1] = y *r/scale;
		}

		// head patch 1
		int nFront1 = 0, nFront2 = 0;
		int frontStartIndex1 = 0, frontStartIndex2 = 0;
		double startPatch1[] = new double[4];
		double endPatch1[] = new double[4];
		double startPatch2[] = new double[4];
		double endPatch2[] = new double[4];
		counted1 = false;
		counted2 = false;
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
				// calculate coordinates of lower patches x^2 + y^2 = 1
		int l = n;
		bootStrap1 = (startPatch1[0]-endPatch1[0])/(double)(l-1);
		bootStrap2 = (startPatch2[0]-endPatch2[0])/(double)(l-1);
		double lowerPatchs1[][] = new double[l][2];
		int drawLowerPatchs1[][] = new int[l][2];
		double startXL1 = endPatch1[0];
		for (int i=0; i<l; i++) {
			x = startXL1/(r/scale);
			if (Math.pow(x,2) > 1)
				y = 0;
			else
				y = -Math.sqrt( 1 - Math.pow(x,2) );
			startXL1 += bootStrap1;
			lowerPatchs1[i][0] = x *r/scale;
			lowerPatchs1[i][1] = y *r/scale;
		}
		double lowerPatchs2[][] = new double[l][2];
		int drawLowerPatchs2[][] = new int[l][2];
		double startXL2 = endPatch2[0];
		for (int i=0; i<l; i++) {
			x = startXL2/(r/scale);
			if (Math.pow(x,2) > 1)
				y = 0;
			else
				y = -Math.sqrt( 1 - Math.pow(x,2) );
			startXL2 += bootStrap2;
			lowerPatchs2[i][0] = x *r/scale;
			lowerPatchs2[i][1] = y *r/scale;
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
			// dragon
		for (int i=0; i<9; i++) {
			viewport(tempDragon[i],drawDragon[i]);
		}
			// eye black
		for (int i=0; i<2*nBlack; i++) {
			viewport(tempEyeBlack1[i],drawEyeBlack1[i]);
			viewport(tempEyeBlack2[i],drawEyeBlack2[i]);
		}
			// eye white
		for (int i=0; i<2*nWhite; i++) {
			viewport(tempEyeWhite1[i],drawEyeWhite1[i]);
			viewport(tempEyeWhite2[i],drawEyeWhite2[i]);
		}
		for (int i=0; i<lWhite; i++) {	
			viewport(lowerWhite1[i],drawLowerWhite1[i]);
			viewport(lowerWhite2[i],drawLowerWhite2[i]);
		}
			// head patch
		for (int i=0; i<n; i++) {
			viewport(tempHeadPatchs1[i],drawHeadPatchs1[i]);
			viewport(tempHeadPatchs2[i],drawHeadPatchs2[i]);
		}
		for (int i=0; i<l; i++) {
			viewport(lowerPatchs1[i],drawLowerPatchs1[i]);
			viewport(lowerPatchs2[i],drawLowerPatchs2[i]);
		}
		
		// before draw...
			// dragon
		int dragonX1[] = new int[9];
		int dragonY1[] = new int[9];
		for (int i=0; i<9; i++) {
			dragonX1[i] = drawDragon[i][0];
			dragonY1[i] = drawDragon[i][1];
		}
			// eye black
		int blackX1[] = new int[2*nBlack];
		int blackY1[] = new int[2*nBlack];
		for (int i=0; i<2*nBlack; i++) {
			blackX1[i] = drawEyeBlack1[i][0];
			blackY1[i] = drawEyeBlack1[i][1];
		}
		int blackX2[] = new int[2*nBlack];
		int blackY2[] = new int[2*nBlack];
		for (int i=0; i<2*nBlack; i++) {
			blackX2[i] = drawEyeBlack2[i][0];
			blackY2[i] = drawEyeBlack2[i][1];
		}
			// eye white
		int whiteX1[] = new int[nFrontWhite1+lWhite];
		int whiteY1[] = new int[nFrontWhite1+lWhite];
		for (int i=0; i<nFrontWhite1; i++) {
			whiteX1[i] = drawEyeWhite1[i+frontWhiteStartIndex1][0];
			whiteY1[i] = drawEyeWhite1[i+frontWhiteStartIndex1][1];
		}
		for (int i=nFrontWhite1; i<nFrontWhite1+lWhite; i++) {
			whiteX1[i] = drawLowerWhite1[i-nFrontWhite1][0];
			whiteY1[i] = drawLowerWhite1[i-nFrontWhite1][1];
		}
		int whiteX2[] = new int[nFrontWhite2+lWhite];
		int whiteY2[] = new int[nFrontWhite2+lWhite];
		for (int i=0; i<nFrontWhite2; i++) {
			whiteX2[i] = drawEyeWhite2[i+frontWhiteStartIndex2][0];
			whiteY2[i] = drawEyeWhite2[i+frontWhiteStartIndex2][1];
		}
		for (int i=nFrontWhite2; i<nFrontWhite2+lWhite; i++) {
			whiteX2[i] = drawLowerWhite2[i-nFrontWhite2][0];
			whiteY2[i] = drawLowerWhite2[i-nFrontWhite2][1];
		}
			// head patch
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

		// DRAW!!!!!!!!!
		//if (Math.sin(speed*time) >= 0) {	// back
	//	if ( false ) { 	
		if ( speed*time % (2*Math.PI) <= Math.PI/2.0 ) {	// back right
	//	if (true) {	// back right
			// eye 2
			g.setColor(Color.green);
			int coEye2[] = new int[4];
			coEye2 = coTransform(drawEye2, eyeA, eyeB);
			g.fillOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);		
			// eye circle 2
//			g.setColor(Color.black);
//			g.drawOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);
			// eye 1
			g.setColor(Color.green);
			int coEye1[] = new int[4];
			coEye1 = coTransform(drawEye1, eyeA, eyeB);
			g.fillOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
//			// eye black 1
//			g.setColor(Color.black);
//			g.drawPolygon(blackX1, blackY1,2*nBlack);
//			g.fillPolygon(blackX1, blackY1,2*nBlack);
			// eye white 1
			g.setColor(Color.white);
			g.drawPolygon(whiteX1, whiteY1,nFrontWhite1+lWhite);
			g.fillPolygon(whiteX1, whiteY1,nFrontWhite1+lWhite);
			// eye cirle 1
//			g.setColor(Color.black);
//			g.drawOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
			// draw line poles
			g.setColor(Color.black);
			g.drawLine(drawPole1start[0],drawPole1start[1],drawPole1end[0],drawPole1end[1]);
			g.drawLine(drawPole2start[0],drawPole2start[1],drawPole2end[0],drawPole2end[1]);
			g.setColor(Color.green);
			// draw nose
			g.setColor(Color.green);
			int coNose[] = new int[4];
			coNose = coTransform(drawNose, r, r);
			g.drawOval(coNose[0], coNose[1], coNose[2], coNose[3]);
			g.fillOval(coNose[0], coNose[1], coNose[2], coNose[3]);
			// draw head
			g.setColor(Color.green);
			int coHead[] = new int[4];
			coHead = coTransform(drawHead, r, r);
			g.fillOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			// draw head patch by point
			g.setColor(Color.white);
			g.drawPolygon(patchX1, patchY1,nFront1+l);
			g.fillPolygon(patchX1, patchY1,nFront1+l);
			g.drawPolygon(patchX2, patchY2,nFront2+l);
			g.fillPolygon(patchX2, patchY2,nFront2+l);
			// draw dragon
			g.setColor(Color.red);
			g.drawPolygon(dragonX1, dragonY1,9);
			g.fillPolygon(dragonX1, dragonY1,9);

			// draw head circle
//			g.setColor(Color.black);
//			g.drawOval(coHead[0], coHead[1], coHead[2], coHead[3]);
	//	} else if ( false ) { 	
		} else if ( speed*time % (2*Math.PI) <= Math.PI ) { 	// back left
			// eye 1
			g.setColor(Color.green);
			int coEye1[] = new int[4];
			coEye1 = coTransform(drawEye1, eyeA, eyeB);
			g.fillOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
			// eye cirle 1
//			g.setColor(Color.black);
//			g.drawOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
			// eye 2
			g.setColor(Color.green);
			int coEye2[] = new int[4];
			coEye2 = coTransform(drawEye2, eyeA, eyeB);
			g.fillOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);
			// eye white 2
			g.setColor(Color.white);
			g.drawPolygon(whiteX2, whiteY2,nFrontWhite2+lWhite);
			g.fillPolygon(whiteX2, whiteY2,nFrontWhite2+lWhite);
//			// eye black 2
//			g.setColor(Color.black);
//			g.drawPolygon(blackX2, blackY2,2*nBlack);
//			g.fillPolygon(blackX2, blackY2,2*nBlack);			
			// eye circle 2
//			g.setColor(Color.black);
//			g.drawOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);
			// draw line poles
			g.setColor(Color.black);
			g.drawLine(drawPole1start[0],drawPole1start[1],drawPole1end[0],drawPole1end[1]);
			g.drawLine(drawPole2start[0],drawPole2start[1],drawPole2end[0],drawPole2end[1]);
			g.setColor(Color.green);
			// draw nose
			g.setColor(Color.green);
			int coNose[] = new int[4];
			coNose = coTransform(drawNose, r, r);
			g.drawOval(coNose[0], coNose[1], coNose[2], coNose[3]);
			g.fillOval(coNose[0], coNose[1], coNose[2], coNose[3]);
			// draw head
			g.setColor(Color.green);
			int coHead[] = new int[4];
			coHead = coTransform(drawHead, r, r);
			g.fillOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			// draw head patch by point
			g.setColor(Color.white);
			g.drawPolygon(patchX1, patchY1,nFront1+l);
			g.fillPolygon(patchX1, patchY1,nFront1+l);
			g.drawPolygon(patchX2, patchY2,nFront2+l);
			g.fillPolygon(patchX2, patchY2,nFront2+l);
			// draw dragon
			g.setColor(Color.red);
			g.drawPolygon(dragonX1, dragonY1,9);
			g.fillPolygon(dragonX1, dragonY1,9);
			// draw head circle
//			g.setColor(Color.black);
//			g.drawOval(coHead[0], coHead[1], coHead[2], coHead[3]);
	//	} else if ( false ) { 	// front left
		} else if ( speed*time % (2*Math.PI) <= Math.PI*3.0/2.0 ) { 	// front left
			// draw dragon
			g.setColor(Color.red);
			g.drawPolygon(dragonX1, dragonY1,9);
			g.fillPolygon(dragonX1, dragonY1,9);
			// draw head
			g.setColor(Color.green);
			int coHead[] = new int[4];
			coHead = coTransform(drawHead, r, r);
			g.fillOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			// draw head patch by point
			g.setColor(Color.white);
			g.drawPolygon(patchX1, patchY1,nFront1+l);
			g.fillPolygon(patchX1, patchY1,nFront1+l);
			g.drawPolygon(patchX2, patchY2,nFront2+l);
			g.fillPolygon(patchX2, patchY2,nFront2+l);
			// draw head circle
//			g.setColor(Color.black);
//			g.drawOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			// eye 1
			g.setColor(Color.green);
			int coEye1[] = new int[4];
			coEye1 = coTransform(drawEye1, eyeA, eyeB);
			g.fillOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
			// eye white 1
			g.setColor(Color.white);
			g.drawPolygon(whiteX1, whiteY1,nFrontWhite1+lWhite);
			g.fillPolygon(whiteX1, whiteY1,nFrontWhite1+lWhite);
			// eye black 1
			g.setColor(Color.black);
			g.drawPolygon(blackX1, blackY1,2*nBlack);
			g.fillPolygon(blackX1, blackY1,2*nBlack);
			// eye cirle 1
//			g.setColor(Color.black);
//			g.drawOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
			// eye 2
			g.setColor(Color.green);
			int coEye2[] = new int[4];
			coEye2 = coTransform(drawEye2, eyeA, eyeB);
			g.fillOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);
			// eye white 2
			g.setColor(Color.white);
			g.drawPolygon(whiteX2, whiteY2,nFrontWhite2+lWhite);
			g.fillPolygon(whiteX2, whiteY2,nFrontWhite2+lWhite);
			// eye black 2
			g.setColor(Color.black);
			g.drawPolygon(blackX2, blackY2,2*nBlack);
			g.fillPolygon(blackX2, blackY2,2*nBlack);			
			// eye circle 2
//			g.setColor(Color.black);
//			g.drawOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]); 
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
		} else { 	// front right
			// draw dragon
			g.setColor(Color.red);
			g.drawPolygon(dragonX1, dragonY1,9);
			g.fillPolygon(dragonX1, dragonY1,9);
			// draw head
			g.setColor(Color.green);
			int coHead[] = new int[4];
			coHead = coTransform(drawHead, r, r);
			g.fillOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			// draw head patch by point
			g.setColor(Color.white);
			g.drawPolygon(patchX1, patchY1,nFront1+l);
			g.fillPolygon(patchX1, patchY1,nFront1+l);
			g.drawPolygon(patchX2, patchY2,nFront2+l);
			g.fillPolygon(patchX2, patchY2,nFront2+l);
			// draw head circle
//			g.setColor(Color.black);
//			g.drawOval(coHead[0], coHead[1], coHead[2], coHead[3]);
			// eye 2
			g.setColor(Color.green);
			int coEye2[] = new int[4];
			coEye2 = coTransform(drawEye2, eyeA, eyeB);
			g.fillOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);
			// eye white 2
			g.setColor(Color.white);
			g.drawPolygon(whiteX2, whiteY2,nFrontWhite2+lWhite);
			g.fillPolygon(whiteX2, whiteY2,nFrontWhite2+lWhite);
			// eye black 2
			g.setColor(Color.black);
			g.drawPolygon(blackX2, blackY2,2*nBlack);
			g.fillPolygon(blackX2, blackY2,2*nBlack);			
			// eye circle 2
//			g.setColor(Color.black);
//			g.drawOval(coEye2[0], coEye2[1], coEye2[2], coEye2[3]);
			// eye 1
			g.setColor(Color.green);
			int coEye1[] = new int[4];
			coEye1 = coTransform(drawEye1, eyeA, eyeB);
			g.fillOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]);
			// eye white 1
			g.setColor(Color.white);
			g.drawPolygon(whiteX1, whiteY1,nFrontWhite1+lWhite);
			g.fillPolygon(whiteX1, whiteY1,nFrontWhite1+lWhite);
			// eye black 1
			g.setColor(Color.black);
			g.drawPolygon(blackX1, blackY1,2*nBlack);
			g.fillPolygon(blackX1, blackY1,2*nBlack);
			// eye cirle 1
//			g.setColor(Color.black);
//			g.drawOval(coEye1[0], coEye1[1], coEye1[2], coEye1[3]); 
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

