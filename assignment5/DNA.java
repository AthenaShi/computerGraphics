import java.awt.*;

public class DNA extends BufferedApplet
{
	int width = 0;
	int height = 0;
	double scale = 1;
	double startTime = getTime();
	double speed = 1.5;
	int n = 10;
	int nTotal = 20;
	double r = 25;
	double rMiddle = r/3.0;
	double rBonds = r/4.0;
	double rSmall = r/2.2;
	double rBig = r/1.5;
	double rLength = r;
	double theta = Math.PI/15.0;
	double dy = Math.sqrt( Math.pow(rBig+rSmall,2) - Math.pow((rLength*2+rMiddle+rBig)*Math.sin(theta),2) );

	public void render(Graphics g) {
		/* ************* SET UP ************ */
		double time = getTime() - startTime;
		width = getWidth();
		height = getHeight();
		g.setColor(Color.black);
		g.fillRect(0,0,width,height);
		Geometry geo = new Geometry();
		Matrix matrix = new Matrix();
		matrix.identity();

		/* ************* INITIALIZE ************ */
	for (int ii=0; ii<nTotal; ii++) {
		// the middle molecular
		geo.globe(n,n);
		double middleV[][] = new double[(n+1)*(n+1)][4];
		int middleF[][] = new int[n*n][4];
		double middleAfterV[][] = new double[(n+1)*(n+1)][4];
		int middleDrawV[][] = new int[(n+1)*(n+1)][4];
		geo.copy(middleV, middleF);
		// tranform molecular
		matrix.rotateY(speed * time);
		matrix.rotateY(theta*2.0*ii);
		matrix.translate(0,dy*2.0*ii,0);
		matrix.scale(rMiddle,rMiddle,rMiddle);
		for (int i=0; i<middleV.length; i++) {
			matrix.transform(middleV[i],middleAfterV[i]);
			viewport(middleAfterV[i],middleDrawV[i]);
		}
		matrix.identity();	

		// two bonds
		geo.cylinder(n,n,n);
		double bond1V[][] = new double[(n+1)*(n+1)+2*(n+1)*(n+1)][4];
		double bond1AfterV[][] = new double[(n+1)*(n+1)+2*(n+1)*(n+1)][4];
		int bond1DrawV[][] = new int[(n+1)*(n+1)+2*(n+1)*(n+1)][4];
		int bond1F[][] = new int[n*n+2*n*n][4];
		double bond2V[][] = new double[(n+1)*(n+1)+2*(n+1)*(n+1)][4];
		double bond2AfterV[][] = new double[(n+1)*(n+1)+2*(n+1)*(n+1)][4];
		int bond2DrawV[][] = new int[(n+1)*(n+1)+2*(n+1)*(n+1)][4];
		int bond2F[][] = new int[n*n+2*n*n][4];
		geo.copy(bond1V, bond1F);
		geo.copy(bond2V, bond2F);
		// transform bonds
		matrix.rotateY(speed * time);
		matrix.rotateY(theta*2.0*ii);
		matrix.translate(-rLength-rMiddle,dy*2.0*ii,0);
		matrix.rotateY(Math.PI/2.0);
		matrix.scale(rBonds,rBonds,rLength);
		for (int i=0; i<bond1V.length; i++) {
			matrix.transform(bond1V[i],bond1AfterV[i]);
			viewport(bond1AfterV[i],bond1DrawV[i]);
		}
		matrix.identity();
		matrix.rotateY(speed * time);
		matrix.rotateY(theta*2.0*ii);
		matrix.translate(rLength+rMiddle,dy*2.0*ii,0);
		matrix.rotateY(-Math.PI/2.0);
		matrix.scale(rBonds,rBonds,rLength);
		for (int i=0; i<bond2V.length; i++) {
			matrix.transform(bond2V[i],bond2AfterV[i]);
			viewport(bond2AfterV[i],bond2DrawV[i]);
		}
		matrix.identity();

		// four outside moleculars
		geo.globe(n,n);
		double outBigLeftV[][] = new double[(n+1)*(n+1)][4];
		double outBigLeftAfterV[][] = new double[(n+1)*(n+1)][4];
		int outBigLeftDrawV[][] = new int[(n+1)*(n+1)][4];
		int outBigLeftF[][] = new int[n*n][4];
		double outSmallLeftV[][] = new double[(n+1)*(n+1)][4];
		double outSmallLeftAfterV[][] = new double[(n+1)*(n+1)][4];
		int outSmallLeftDrawV[][] = new int[(n+1)*(n+1)][4];
		int outSmallLeftF[][] = new int[n*n][4];

		double outBigRightV[][] = new double[(n+1)*(n+1)][4];
		double outBigRightAfterV[][] = new double[(n+1)*(n+1)][4];
		int outBigRightDrawV[][] = new int[(n+1)*(n+1)][4];
		int outBigRightF[][] = new int[n*n][4];
		double outSmallRightV[][] = new double[(n+1)*(n+1)][4];
		double outSmallRightAfterV[][] = new double[(n+1)*(n+1)][4];
		int outSmallRightDrawV[][] = new int[(n+1)*(n+1)][4];
		int outSmallRightF[][] = new int[n*n][4];

		geo.copy(outBigLeftV, outBigLeftF);
		geo.copy(outSmallLeftV, outSmallLeftF);
		geo.copy(outBigRightV, outBigRightF);
		geo.copy(outSmallRightV, outSmallRightF);
		// transform two big moleculars
		matrix.rotateY(speed * time);
		matrix.rotateY(theta*2.0*ii);
		matrix.translate(-rLength*2-rMiddle-rBig,dy*2.0*ii,0);
		matrix.scale(rBig,rBig,rBig);
		for (int i=0; i<outBigLeftV.length; i++) {
			matrix.transform(outBigLeftV[i],outBigLeftAfterV[i]);
			viewport(outBigLeftAfterV[i],outBigLeftDrawV[i]);
		}
		matrix.identity();
		matrix.rotateY(speed * time);
		matrix.rotateY(theta*2.0*ii);
		matrix.translate(rLength*2+rMiddle+rBig,dy*2.0*ii,0);
		matrix.scale(rBig,rBig,rBig);
		for (int i=0; i<outBigRightV.length; i++) {
			matrix.transform(outBigRightV[i],outBigRightAfterV[i]);
			viewport(outBigRightAfterV[i],outBigRightDrawV[i]);
		}
		matrix.identity();
		// transform two small moleculars 
		matrix.rotateY(speed * time);
		matrix.rotateY(theta*2.0*ii);
		matrix.translate(0,dy*2.0*ii,0);
		matrix.rotateY(theta);
		matrix.translate(-rLength*2-rMiddle-rBig,dy,0);
		matrix.scale(rSmall,rSmall,rSmall);
		for (int i=0; i<outSmallLeftV.length; i++) {
			matrix.transform(outSmallLeftV[i],outSmallLeftAfterV[i]);
			viewport(outSmallLeftAfterV[i],outSmallLeftDrawV[i]);
		}
		matrix.identity();
		matrix.rotateY(speed * time);
		matrix.rotateY(theta);
		matrix.rotateY(theta*2.0*ii);
		matrix.translate(0,dy*2.0*ii,0);
		matrix.translate(rLength*2+rMiddle+rBig,dy,0);
		matrix.scale(rSmall,rSmall,rSmall);
		for (int i=0; i<outSmallRightV.length; i++) {
			matrix.transform(outSmallRightV[i],outSmallRightAfterV[i]);
			viewport(outSmallRightAfterV[i],outSmallRightDrawV[i]);
		}
		matrix.identity();

			/* ************* DRAW ************ */
		if ( outBigLeftAfterV[0][2] >= outBigRightAfterV[0][2]) {	// draw right first
				// draw right outside moleculars
			g.setColor(Color.green);
			for (int i=0; i<outBigRightF.length; i++){
				for (int j=0; j<outBigRightF[i].length; j++) {
					if (j<outBigRightF[i].length-1) {
						g.drawLine((int)outSmallRightDrawV[outSmallRightF[i][j]][0], (int)outSmallRightDrawV[outSmallRightF[i][j]][1], (int)outSmallRightDrawV[outSmallRightF[i][j+1]][0], (int)outSmallRightDrawV[outSmallRightF[i][j+1]][1]);
						g.drawLine((int)outBigRightDrawV[outBigRightF[i][j]][0], (int)outBigRightDrawV[outBigRightF[i][j]][1], (int)outBigRightDrawV[outBigRightF[i][j+1]][0], (int)outBigRightDrawV[outBigRightF[i][j+1]][1]);
					} else {
						g.drawLine((int)outSmallRightDrawV[outSmallRightF[i][j]][0], (int)outSmallRightDrawV[outSmallRightF[i][j]][1], (int)outSmallRightDrawV[outSmallRightF[i][0]][0], (int)outSmallRightDrawV[outSmallRightF[i][0]][1]);
						g.drawLine((int)outBigRightDrawV[outBigRightF[i][j]][0], (int)outBigRightDrawV[outBigRightF[i][j]][1], (int)outBigRightDrawV[outBigRightF[i][0]][0], (int)outBigRightDrawV[outBigRightF[i][0]][1]);
					}
				}
			}
				// draw right bond
			g.setColor(Color.red);
			for (int i=0; i<bond2F.length; i++){
				for (int j=0; j<bond2F[i].length; j++) {
					if (j<bond2F[i].length-1) {
						g.drawLine((int)bond2DrawV[bond2F[i][j]][0], (int)bond2DrawV[bond2F[i][j]][1], (int)bond2DrawV[bond2F[i][j+1]][0], (int)bond2DrawV[bond2F[i][j+1]][1]);
					} else {
						g.drawLine((int)bond2DrawV[bond2F[i][j]][0], (int)bond2DrawV[bond2F[i][j]][1], (int)bond2DrawV[bond2F[i][0]][0], (int)bond2DrawV[bond2F[i][0]][1]);
					}
				}
			}
				// draw middle molecular
			g.setColor(Color.blue);
			for (int i=0; i<middleF.length; i++){
				for (int j=0; j<middleF[i].length; j++) {
					if (j<middleF[i].length-1) {
						g.drawLine(middleDrawV[middleF[i][j]][0], (int)middleDrawV[middleF[i][j]][1], (int)middleDrawV[middleF[i][j+1]][0], (int)middleDrawV[middleF[i][j+1]][1]);
					} else {
						g.drawLine((int)middleDrawV[middleF[i][j]][0], (int)middleDrawV[middleF[i][j]][1], (int)middleDrawV[middleF[i][0]][0], (int)middleDrawV[middleF[i][0]][1]);
					}
				}
			}
				// draw left bond
			g.setColor(Color.red);
			for (int i=0; i<bond1F.length; i++){
				for (int j=0; j<bond1F[i].length; j++) {
					if (j<bond1F[i].length-1) {
						g.drawLine((int)bond1DrawV[bond1F[i][j]][0], (int)bond1DrawV[bond1F[i][j]][1], (int)bond1DrawV[bond1F[i][j+1]][0], (int)bond1DrawV[bond1F[i][j+1]][1]);
					} else {
						g.drawLine((int)bond1DrawV[bond1F[i][j]][0], (int)bond1DrawV[bond1F[i][j]][1], (int)bond1DrawV[bond1F[i][0]][0], (int)bond1DrawV[bond1F[i][0]][1]);
					}
				}
			}
				// draw left outside moleculars
			g.setColor(Color.blue);
			for (int i=0; i<outBigLeftF.length; i++){
				for (int j=0; j<outBigLeftF[i].length; j++) {
					if (j<outBigLeftF[i].length-1) {
						g.drawLine((int)outBigLeftDrawV[outBigLeftF[i][j]][0], (int)outBigLeftDrawV[outBigLeftF[i][j]][1], (int)outBigLeftDrawV[outBigLeftF[i][j+1]][0], (int)outBigLeftDrawV[outBigLeftF[i][j+1]][1]);
						g.drawLine((int)outSmallLeftDrawV[outSmallLeftF[i][j]][0], (int)outSmallLeftDrawV[outSmallLeftF[i][j]][1], (int)outSmallLeftDrawV[outSmallLeftF[i][j+1]][0], (int)outSmallLeftDrawV[outSmallLeftF[i][j+1]][1]);
					} else {
						g.drawLine((int)outBigLeftDrawV[outBigLeftF[i][j]][0], (int)outBigLeftDrawV[outBigLeftF[i][j]][1], (int)outBigLeftDrawV[outBigLeftF[i][0]][0], (int)outBigLeftDrawV[outBigLeftF[i][0]][1]);
						g.drawLine((int)outSmallLeftDrawV[outSmallLeftF[i][j]][0], (int)outSmallLeftDrawV[outSmallLeftF[i][j]][1], (int)outSmallLeftDrawV[outSmallLeftF[i][0]][0], (int)outSmallLeftDrawV[outSmallLeftF[i][0]][1]);
					}
				}
			}
		} else {
				// draw left outside moleculars
			g.setColor(Color.blue);
			for (int i=0; i<outBigLeftF.length; i++){
				for (int j=0; j<outBigLeftF[i].length; j++) {
					if (j<outBigLeftF[i].length-1) {
						g.drawLine((int)outSmallLeftDrawV[outSmallLeftF[i][j]][0], (int)outSmallLeftDrawV[outSmallLeftF[i][j]][1], (int)outSmallLeftDrawV[outSmallLeftF[i][j+1]][0], (int)outSmallLeftDrawV[outSmallLeftF[i][j+1]][1]);
						g.drawLine((int)outBigLeftDrawV[outBigLeftF[i][j]][0], (int)outBigLeftDrawV[outBigLeftF[i][j]][1], (int)outBigLeftDrawV[outBigLeftF[i][j+1]][0], (int)outBigLeftDrawV[outBigLeftF[i][j+1]][1]);
					} else {
						g.drawLine((int)outSmallLeftDrawV[outSmallLeftF[i][j]][0], (int)outSmallLeftDrawV[outSmallLeftF[i][j]][1], (int)outSmallLeftDrawV[outSmallLeftF[i][0]][0], (int)outSmallLeftDrawV[outSmallLeftF[i][0]][1]);
						g.drawLine((int)outBigLeftDrawV[outBigLeftF[i][j]][0], (int)outBigLeftDrawV[outBigLeftF[i][j]][1], (int)outBigLeftDrawV[outBigLeftF[i][0]][0], (int)outBigLeftDrawV[outBigLeftF[i][0]][1]);
					}
				}
			}
				// draw left bond
			g.setColor(Color.red);
			for (int i=0; i<bond1F.length; i++){
				for (int j=0; j<bond1F[i].length; j++) {
					if (j<bond1F[i].length-1) {
						g.drawLine((int)bond1DrawV[bond1F[i][j]][0], (int)bond1DrawV[bond1F[i][j]][1], (int)bond1DrawV[bond1F[i][j+1]][0], (int)bond1DrawV[bond1F[i][j+1]][1]);
					} else {
						g.drawLine((int)bond1DrawV[bond1F[i][j]][0], (int)bond1DrawV[bond1F[i][j]][1], (int)bond1DrawV[bond1F[i][0]][0], (int)bond1DrawV[bond1F[i][0]][1]);
					}
				}
			}
				// draw middle molecular
			g.setColor(Color.blue);
			for (int i=0; i<middleF.length; i++){
				for (int j=0; j<middleF[i].length; j++) {
					if (j<middleF[i].length-1) {
						g.drawLine(middleDrawV[middleF[i][j]][0], (int)middleDrawV[middleF[i][j]][1], (int)middleDrawV[middleF[i][j+1]][0], (int)middleDrawV[middleF[i][j+1]][1]);
					} else {
						g.drawLine((int)middleDrawV[middleF[i][j]][0], (int)middleDrawV[middleF[i][j]][1], (int)middleDrawV[middleF[i][0]][0], (int)middleDrawV[middleF[i][0]][1]);
					}
				}
			}
				// draw right bond
			g.setColor(Color.red);
			for (int i=0; i<bond2F.length; i++){
				for (int j=0; j<bond2F[i].length; j++) {
					if (j<bond2F[i].length-1) {
						g.drawLine((int)bond2DrawV[bond2F[i][j]][0], (int)bond2DrawV[bond2F[i][j]][1], (int)bond2DrawV[bond2F[i][j+1]][0], (int)bond2DrawV[bond2F[i][j+1]][1]);
					} else {
						g.drawLine((int)bond2DrawV[bond2F[i][j]][0], (int)bond2DrawV[bond2F[i][j]][1], (int)bond2DrawV[bond2F[i][0]][0], (int)bond2DrawV[bond2F[i][0]][1]);
					}
				}
			}
				// draw right outside moleculars
			g.setColor(Color.green);
			for (int i=0; i<outBigRightF.length; i++){
				for (int j=0; j<outBigRightF[i].length; j++) {
					if (j<outBigRightF[i].length-1) {
						g.drawLine((int)outSmallRightDrawV[outSmallRightF[i][j]][0], (int)outSmallRightDrawV[outSmallRightF[i][j]][1], (int)outSmallRightDrawV[outSmallRightF[i][j+1]][0], (int)outSmallRightDrawV[outSmallRightF[i][j+1]][1]);
						g.drawLine((int)outBigRightDrawV[outBigRightF[i][j]][0], (int)outBigRightDrawV[outBigRightF[i][j]][1], (int)outBigRightDrawV[outBigRightF[i][j+1]][0], (int)outBigRightDrawV[outBigRightF[i][j+1]][1]);
					} else {
						g.drawLine((int)outSmallRightDrawV[outSmallRightF[i][j]][0], (int)outSmallRightDrawV[outSmallRightF[i][j]][1], (int)outSmallRightDrawV[outSmallRightF[i][0]][0], (int)outSmallRightDrawV[outSmallRightF[i][0]][1]);
						g.drawLine((int)outBigRightDrawV[outBigRightF[i][j]][0], (int)outBigRightDrawV[outBigRightF[i][j]][1], (int)outBigRightDrawV[outBigRightF[i][0]][0], (int)outBigRightDrawV[outBigRightF[i][0]][1]);
					}
				}
			}
		}
		}
	}

	double getTime() {
		return System.currentTimeMillis() / 1000.0;
	}
	public void viewport(double src[], int dst[]) {
		dst[0] = (int) ( 0.5 * width  + src[0] * scale );
		//dst[1] = (int) ( 0.5 * height - src[1] * scale );
		dst[1] = (int) ( height - src[1] * scale );
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
