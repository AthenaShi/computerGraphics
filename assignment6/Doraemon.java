import java.awt.*;

public class Doraemon extends BufferedApplet
{
	int width = 0;
	int height = 0;
	double f = 1500;
	double scale = 6;
	double startTime = getTime();
	double speed = 3.1;
	double moveSpeed = 0.5;
	int torsoI = 110;
	int headI = 210;
//	int torsoI = 50;
//	int headI = 100;
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
	Matrix tempM = new Matrix();
	Color chooseC = Color.blue;
	boolean copter = false;
	// movement!!!!!!!
	double moveShoulderX[][] = { 
		{0*moveSpeed,0}, 
		{1*moveSpeed,Math.PI/5}, 
		{3*moveSpeed,-Math.PI/5}, 
		{5*moveSpeed,Math.PI/5}, 
		{7*moveSpeed,-Math.PI/5}, 
		{8*moveSpeed,0},
		{29*moveSpeed,0}, 
		{30*moveSpeed,Math.PI/5}, 
		{32*moveSpeed,-Math.PI/5}, 
		{34*moveSpeed,Math.PI/5}, 
		{36*moveSpeed,-Math.PI/5}, 
		{37*moveSpeed,0},
	};
	Movement MoveShoulder1 = new Movement(moveShoulderX);
	double rotShoulder1;

	double moveShoulderZ[][] = { 
		{8.5*moveSpeed,Math.PI/5}, 
		{9.5*moveSpeed, Math.PI/5.5},
		{10.5*moveSpeed, Math.PI/5}, 
		{11.5*moveSpeed,Math.PI},
		{12.5*moveSpeed,0},

		{14.5*moveSpeed,Math.PI-Math.PI/6},
		{16.5*moveSpeed,Math.PI+Math.PI/6},
		{18.5*moveSpeed,Math.PI-Math.PI/6},
		{20.5*moveSpeed,Math.PI+Math.PI/6},
		{22.5*moveSpeed,Math.PI-Math.PI/6},
		{24.5*moveSpeed,Math.PI+Math.PI/6},

		{26.5*moveSpeed,Math.PI},
		{27.5*moveSpeed,Math.PI/5},
		{28.5*moveSpeed,0}

	};
	Movement MoveShoulder2 = new Movement(moveShoulderZ);
	double rotShoulder2;

	double moveAll[][] = { 
		{11.5*moveSpeed,0}, 
		{15.5*moveSpeed,10},
		{20.5*moveSpeed,10}, 
		{25.5*moveSpeed,0} 
	};
	Movement MoveAll = new Movement(moveAll);
	double moveAllY;

	double moveBody[][] = { 
		{11.5*moveSpeed,0}, 
		{15.5*moveSpeed,Math.PI/10},
		{20.5*moveSpeed,Math.PI/10}, 
		{25.5*moveSpeed,0} 
	};
	Movement MoveBody = new Movement(moveBody);
	double rotBodyX;

	Geometry bambooCopterLeft, bambooCopterRight, bambooCopterCenter,
		 bambooCopterStick, head, nose, torso, bell, armLeft, armRight, 
		 handLeft, handRight, legRight, legLeft, 
		 footRight, footLeft;
	Geometry world, all, body, neck, shoulderLeft, shoulderRight, 
		 hipLeft, hipRight, bambooCopter;

	public Doraemon() {
		initialize();

	}

	public void render(Graphics g) {
		double time = getTime() - startTime;
		width = getWidth();
		height = getHeight();
		g.setColor(background);
		g.fillRect(0,0,width,height);
	//	grass
		g.setColor(Color.green);
		g.fillRect(0,height/3,width,height*2/3);

		animate(time);
		// traverse and draw
		world.globalMatrix.identity();
		drawIt(all, world.globalMatrix, g);
	}
	public void drawIt(Geometry object, Matrix matrix, Graphics g) {
		int childNumber = object.getNumChildren();
		Matrix relativeMatrix = object.getMatrix();
		matrix.multiply(relativeMatrix);
		object.globalMatrix.copy(matrix);
		if (childNumber == 0) {
			int what = object.what;
			switch (what) {
				case 0:
					transformAndRender(object, matrix, g, Color.blue);
					break;
				case 1:
					transformAndRender(object, matrix, g, bamboo);
					break;
				case 2:
					drawHead(object, matrix, g);
					break;
				case 3:
					transformAndRender(object, matrix, g, Color.red);
					break;
				case 4:
					transformAndRender(object, matrix, g, Color.yellow);
					break;
				case 5:
					drawBody(object, matrix, g);
					break;
				case 6:
					transformAndRender(object, matrix, g, Color.white);
					break;
			}
		} else {
			for (int i=0; i<childNumber; i++) {
				Geometry child = object.getChild(i);
				tempM.copy(object.globalMatrix);
				drawIt(child, tempM, g);
			}
		}
	}
			
	public void initialize() {
		world		= getWorld();
		all		= world.add();
		body		= all.add();

		shoulderRight	= body.add();
		hipRight	= body.add(); 
		armRight	= shoulderRight.add();
		handRight	= shoulderRight.add();
		legRight	= hipRight.add();
		footRight	= hipRight.add();

		neck		= body.add();
		head		= neck.add();
		nose		= neck.add();
		torso		= body.add();
		bell		= body.add();

		shoulderLeft	= body.add();
		hipLeft		= body.add();
		armLeft		= shoulderLeft.add();
		handLeft	= shoulderLeft.add();
		legLeft		= hipLeft.add();
		footLeft	= hipLeft.add();

		head.globe(headI);				// what = 2
		head.what = 2;
		nose.globe(25);					// what = 3	red
		nose.what = 3;
		bell.globe(25);					// what = 4	yellow
		bell.what = 4;
		torso.cylinder(torsoI);				// what = 5
		torso.what = 5;
		armLeft.cylinder(limbI);			// what = 0
		armRight.cylinder(limbI);			// what = 0
		handLeft.globe(limbI);				// what = 6	white
		handLeft.what = 6;
		handRight.globe(limbI);				// what = 6	white
		handRight.what = 6;
		legLeft.cylinder(limbI);			// what = 0
		legRight.cylinder(limbI);			// what = 0
		footLeft.globe(limbI);				// what = 6	white
		footLeft.what = 6;
		footRight.globe(limbI);				// what = 6	white
		footRight.what = 6;

		m = head.getMatrix();		// head!!!
		m.translate(0,headSize,0);
		m.scale(headSize, headSize, headSize);

		m = nose.getMatrix();		// nose!!!
		m.translate(0, 0.4*headSize, Math.sqrt(Math.pow(1.0*headSize,2)-Math.pow(0.4*headSize,2)) );
		m.translate(0,headSize,0);
		m.scale(headSize*0.1, headSize*0.1, headSize*0.1);

		m = torso.getMatrix();		// torso!!!
		m.rotateX(-Math.PI / 2);
		m.scale(torsoSize, torsoSize*0.5, torsoSize*0.9);

		m = bell.getMatrix();		// bell!!!
		m.translate( 0, Math.sqrt(Math.pow(1.0*headSize,2)-Math.pow(0.85*headSize,2)),0.85*headSize/2 );
		m.scale(headSize*0.1, headSize*0.1, headSize*0.1);

		m = armLeft.getMatrix();		// armLeft!!!
		m.rotateX(Math.PI / 2);
		m.translate(0, 0, 1.6*limbSize);
		m.scale(limbSize*0.6, limbSize*0.6, 1.6*limbSize);

		m = handLeft.getMatrix();		// handLeft!!!
		m.translate(0,-limbSize-3.2*limbSize,0);
                m.rotateX(Math.PI / 2);
                m.scale(limbSize, limbSize, limbSize);

		m = armRight.getMatrix();		// armRight!!!
		m.rotateX(Math.PI / 2);
		m.translate(0, 0, 1.6*limbSize);
		m.scale(limbSize*0.6, limbSize*0.6, 1.6*limbSize);

		m = handRight.getMatrix();		// handRight!!!
		m.translate(0,-limbSize-3.2*limbSize,0);
                m.rotateX(Math.PI / 2);
                m.scale(limbSize, limbSize, limbSize);

		m = footLeft.getMatrix();		// footLeft!!!
		m.translate( 0,-limbSize*0.8-limbSize*0.6,limbSize*0.5);
                m.scale(limbSize*1.9, limbSize*0.8, limbSize*2.3);

		m = legLeft.getMatrix();		// legLeft!!!
		m.rotateX(-Math.PI / 2);
		m.translate(0, 0, -limbSize*0.6);
                m.scale(limbSize*1.64, limbSize*1.64, limbSize*0.6);

		m = footRight.getMatrix();		// footLeft!!!
		m.translate( 0,-limbSize*0.8-limbSize*0.6,limbSize*0.5);
                m.scale(limbSize*1.9, limbSize*0.8, limbSize*2.3);

		m = legRight.getMatrix();		// legRight!!!
		m.rotateX(-Math.PI / 2);
		m.translate(0, 0, -limbSize*0.6);
                m.scale(limbSize*1.64, limbSize*1.64, limbSize*0.6);
	}

	public void animate(double time) {
		m = all.getMatrix();			// all!!!
		m.identity();
		//m.perspect(f);
		m.rotateY(Math.PI/6);

		if (time >= 11.5*moveSpeed && !copter) {
			//bambooCopter = all.add();
			bambooCopter = body.add();
			bambooCopterLeft	= bambooCopter.add();
			bambooCopterRight	= bambooCopter.add();
			bambooCopterStick	= bambooCopter.add();	
			bambooCopterCenter	= bambooCopter.add();
			bambooCopterLeft.cylinder(bambooCopterI);	// what = 1
			bambooCopterLeft.what = 1;
			bambooCopterRight.cylinder(bambooCopterI);	// what = 1
			bambooCopterRight.what = 1;
			bambooCopterCenter.globe(bambooCopterI);	// what = 1
			bambooCopterCenter.what = 1;
			bambooCopterStick.cylinder(bambooCopterI);	// what = 1	bamboo
			bambooCopterStick.what = 1;

			m = bambooCopterCenter.getMatrix();// bambooCopterCenter!!!
			m.translate(0,(4.0+0.35)*bambooSize,0);
			m.scale(0.35*bambooSize, 0.35*bambooSize, 0.35*bambooSize);

			m = bambooCopterStick.getMatrix();
			m.rotateX(Math.PI / 2); 	// bambooCopterStick!!!
			m.translate(0, 0, -2.0*bambooSize);
			m.scale(0.2*bambooSize, 0.2*bambooSize, 2.0*bambooSize);

			m = bambooCopterLeft.getMatrix();
			m.translate((2.0+0.35)*bambooSize,(4.0+0.35)*bambooSize,0);
			m.rotateY(Math.PI / 2); 	// bambooCopterLeft!!!
			m.scale(0.25*bambooSize, 0.25*bambooSize, 2.0*bambooSize);

			m = bambooCopterRight.getMatrix();
			m.translate(-(2.0+0.35)*bambooSize,(4.0+0.35)*bambooSize,0);
			m.rotateY(-Math.PI / 2); 	// bambooCopterRight!!!
			m.scale(0.25*bambooSize, 0.25*bambooSize, 2.0*bambooSize);

			copter = true;
		}
		if (time >= 26.5*moveSpeed && copter) {
			copter = false;
			//all.remove(bambooCopter);
			body.remove(bambooCopter);
		}
		if (copter) {
			m = bambooCopter.getMatrix();			// bambooCopter!!!
			m.identity();
			m.rotateY(speed*time*3.1);
			m.translate(0, headSize*2+4*bambooSize, 0);

			m = all.getMatrix();			// all!!!
			m.identity();
			//m.perspect(f);
			m.rotateY(Math.PI/6);
			moveAllY = MoveAll.getMove(time);
			m.translate(0,moveAllY,0);

			m = body.getMatrix();			// body!!!
			m.identity();
			rotBodyX = MoveBody.getMove(time);
			m.rotateX(rotBodyX);
		} else {
			m = body.getMatrix();			// body!!!
			m.identity();
		}

		m = neck.getMatrix();			// neck!!!
		m.identity();
		m.translate(0, torsoSize*0.55*0.9, 0);

		m = shoulderLeft.getMatrix();			// shoulderLeft!!!
		m.identity();
		m.translate(0,torsoSize*0.6,0);
		m.translate( -torsoSize-limbSize/2, 0, 0);
		rotShoulder1 = MoveShoulder1.getMove(time);
		rotShoulder2 = MoveShoulder2.getMove(time);
		m.rotateX(rotShoulder1);
		m.rotateZ(rotShoulder2);

		m = shoulderRight.getMatrix();			// shoulderRight!!!
		m.identity();
		m.translate(0,torsoSize*0.6,0);
		m.translate( torsoSize+limbSize/2, 0, 0);
		m.rotateX(-rotShoulder1);

		m = hipLeft.getMatrix();			// hipLeft!!!
		m.identity();
		m.translate(0,-torsoSize*0.8,0);
		m.translate( -torsoSize*0.5, 0, 0);
		m.rotateX(-rotShoulder1);

		m = hipRight.getMatrix();			// hipRight!!!
		m.identity();
		m.translate(0,-torsoSize*0.8,0);
		m.translate( torsoSize*0.5, 0, 0);
		m.rotateX(rotShoulder1);

		if (time >= 28.5*moveSpeed) {
			startTime = getTime();
		}
	}

	public Geometry getWorld() {
		Geometry World = new Geometry();
		World.globalMatrix.identity();
		World.relativeMatrix.identity();
		return World;
	}

   public void transformAndRender(Geometry geo, Matrix m, Graphics g, Color color) {
	   g.setColor(color);
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

   public void drawBody(Geometry geo, Matrix m, Graphics g) {
	   for (int i=0; i<geo.vertices.length; i++) {
		m.transform(geo.vertices[i],temp);
		temp[0] = temp[0]/temp[3];
		temp[1] = temp[1]/temp[3];
		temp[2] = temp[2]/temp[3];
		viewport(temp,geo.draw[i]);
	   }
	   for (int i=0; i<geo.faces.length; i++){
		double indexX = geo.vertices[geo.faces[i][0]][0];
		double indexY = geo.vertices[geo.faces[i][0]][1];
		double indexZ = geo.vertices[geo.faces[i][0]][2];
		double mouthCo = Math.pow(indexX,2) + Math.pow(indexZ-0.12,2);
		double faceCo = Math.pow(indexX,2) + Math.pow(indexZ-0.05,2);
		if ( indexZ > 0.85) {	// draw collar
			g.setColor(Color.red);
		} else if (  -Math.sqrt(0.54) < indexX && indexX <= Math.sqrt(0.54) && indexY < 0 && indexZ >= 0.12 && indexZ < 0.12 + 0.03) {	// draw pocket
			g.setColor(Color.black);
		} else if (  0.51 < mouthCo && mouthCo <= 0.54 && indexY < 0 && indexZ < 0.12) {	// draw pocket
			g.setColor(Color.black);
		} else if (  0.88 < faceCo && faceCo <= 0.9 && indexY < 0) {	// draw bally
			g.setColor(Color.black);
		} else if ( faceCo <= 0.9  && indexY < 0 ) {
			g.setColor(Color.white);
		} else {
			g.setColor(Color.blue);
		}
		for (int j=0; j<geo.faces[i].length; j++) {
			if (j<geo.faces[i].length-1) {
				g.drawLine((int)geo.draw[geo.faces[i][j]][0], (int)geo.draw[geo.faces[i][j]][1], (int)geo.draw[geo.faces[i][j+1]][0], (int)geo.draw[geo.faces[i][j+1]][1]);
			} else {
				g.drawLine((int)geo.draw[geo.faces[i][j]][0], (int)geo.draw[geo.faces[i][j]][1], (int)geo.draw[geo.faces[i][0]][0], (int)geo.draw[geo.faces[i][0]][1]);
			}
		}
	   }
   }

   public void drawHead(Geometry geo, Matrix m, Graphics g) {
	   for (int i=0; i<geo.vertices.length; i++) {
		m.transform(geo.vertices[i],temp);
		temp[0] = temp[0]/temp[3];
		temp[1] = temp[1]/temp[3];
		temp[2] = temp[2]/temp[3];
		viewport(temp,geo.draw[i]);
	   }
	   for (int i=0; i<geo.faces.length; i++){
		double indexX = geo.vertices[geo.faces[i][0]][0];
		double indexY = geo.vertices[geo.faces[i][0]][1];
		double indexZ = geo.vertices[geo.faces[i][0]][2];
		double leftEyeBallCo = Math.pow(indexX+0.15,2)*1.5 + Math.pow(indexY-0.6,2);
		double mouthCo = Math.pow(indexX,2)*1.5 + Math.pow(indexY,2);
		double leftEyeCo = Math.pow(indexX+0.23,2)*1.5 + Math.pow(indexY-0.6,2);
		double rightEyeCo = Math.pow(indexX-0.23,2)*1.5 + Math.pow(indexY-0.6,2);
		double faceCo = Math.pow(indexX,2) + Math.pow(indexY+0.2,2);
		if ( (-0.47-0.25) <= indexX && indexX <= (-0.47+0.25) && indexX-2.4*indexY <= (-0.48+0.015) && indexX-2.4*indexY >= (-0.48-0.015) && indexZ > 0) {	// draw beard left down
			g.setColor(Color.black);
		} else if ( (0.47-0.25) <= indexX && indexX <= (0.47+0.25) && indexX+2.4*indexY <= (0.48+0.015) && indexX+2.4*indexY >= (0.48-0.015) && indexZ > 0) {	// draw beard right down
			g.setColor(Color.black);
		} else if ( (-0.575-0.275) <= indexX && indexX <= (-0.575+0.275) && indexY <= (0.18+0.005) && indexY >= (0.18-0.005) && indexZ > 0) {	// draw beard left middle
			g.setColor(Color.black);
		} else if ( (0.575-0.275) <= indexX && indexX <= (0.575+0.275) && indexY <= (0.18+0.005) && indexY >= (0.18-0.005) && indexZ > 0) {	// draw beard right middle
			g.setColor(Color.black);
		} else if ( (-0.47-0.25) <= indexX && indexX <= (-0.47+0.25) && indexX+2.0*indexY <= (0.25+0.015) && indexX+2.0*indexY >= (0.25-0.015) && indexZ > 0) {	// draw beard left up
			g.setColor(Color.black);
		} else if ( (0.47-0.25) <= indexX && indexX <= (0.47+0.25) && indexX-2.0*indexY <= (-0.25+0.015) && indexX-2.0*indexY >= (-0.25-0.015) && indexZ > 0) {	// draw beard right up
			g.setColor(Color.black);
		} else if ( (0.23-Math.sqrt(0.006/1.5)) <= indexX && indexX <= (0.23) && indexX-indexY <= -0.375 && indexX-indexY >= -0.405 && indexZ > 0) {	// draw eyeball
			g.setColor(Color.black);
		} else if ( (0.23) <= indexX && indexX <= (0.23+Math.sqrt(0.006/1.5)) && indexX+indexY >= 0.835 && indexX+indexY <= 0.865 && indexZ > 0) {	// draw eyeball
			g.setColor(Color.black);
		} else if ( leftEyeBallCo <= 0.006 && indexZ > 0) {	// draw eyeball
			g.setColor(Color.black);
		} else if ( 0.06 < leftEyeCo && leftEyeCo <= 0.07 && indexZ > 0) {	// draw eyes
			g.setColor(Color.black);
		} else if ( leftEyeCo < 0.07 && indexZ > 0) {
			g.setColor(Color.white);
		} else if ( 0.06 < rightEyeCo && rightEyeCo <= 0.07 && indexZ > 0) {
			g.setColor(Color.black);
		} else if ( rightEyeCo < 0.07 && indexZ > 0) {
			g.setColor(Color.white);
		} else if ( -0.01 <= indexX && indexX <= 0.01 && -0 <= indexY && indexY <= 0.4 && indexZ > 0) {	// draw nose line
			g.setColor(Color.black);
		} else if (  mouthCo <= 0.4 && indexY < -0 && indexZ > 0) {	// draw mouth
			g.setColor(Color.red);
		} else if (  0.68 < faceCo && faceCo <= 0.7 && indexZ > 0) {	// draw face
			g.setColor(Color.black);
		} else if ( faceCo <= 0.7 && indexZ > 0) {
			g.setColor(Color.white);
		} else {
			g.setColor(Color.blue);
		}
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
