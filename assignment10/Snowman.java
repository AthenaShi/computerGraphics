import java.util.*;

public class Snowman extends MISApplet {
	// globel view parameters
	double viewX = 0;
	double viewY = 0;
	double viewZ = 10;
	double scale = 50;
	double speed = 1;
	double background[] = {0.4, 0.4, 0.4};
	int large = Integer.MAX_VALUE;

	// objects parameters
	int geoI = 12;		///////////////////////
	int globeI = 160;
	Geometry g[] = new Geometry[geoI];
	Geometry t[] = new Geometry[geoI];
	Material materials[] = new Material[geoI];
	Light lights[] = new Light[2];
	double white = 0.7;
	double black = 0.07;
	// foundation
	double foundW = 5;
	double foundH = 0.1;
//	double foundH = 1;
	double foundPosition = -2;
	double foundP = 1;	// draw parameters
	int foundI = 10;
	// largest ball
	double ballP = 50;
	double ballLR = 1.5;
	double ballLPosition = foundPosition + 0.9;
	double ballLX = -1;
	double ballLZ = -1;
	// middle ball
	double ballMR = 1.2;
	double ballMPosition = ballLPosition + 1.4;
	// head ball
	double ballHR = 0.9;
	double ballHPosition = ballMPosition + 1.5;
	// hat
	int hatI = 50;
	double hatR = 0.6;
	double hatPosition = ballHPosition + 1.3;
	double hatP = 50;
	// hat bottom
	double hatBR = 1.2;
	double hatBH = 0.02;
	double hatBPosition = hatPosition - hatR;
	// eyes
	int eyeI = 10;
	double eyeH = 0.15;
	double eyeW = 0.08;
	double eyeT = 0.05;
	double eyeX = 0.2;
	double eyeY = 0.2;
	// nose
	double noseR = 0.1;
	double noseL = 0.4;
	// botton
	double bottonR = 0.08;
	double bottonT = 0.05;
	double bottonY = 0.4;

	int trapezoid[][] = new int[4][3];
	int trapezoidRGB[][] = new int[4][3];
	int enlarge = (int)Math.pow(2,12);
	int pz;
	Triangle draw = new Triangle();
	Matrix m = new Matrix();

	public void initialize() {
		////////////////// objects initialization ///////////////
		// the foundation
		g[0] = new Geometry();
		g[0].cylinder(foundI);
//		g[0].globe(globeI);

		materials[0] = new Material();
		materials[0].setArgb(white,white,white);	// white
		materials[0].setDrgb(1.0,1.0,1.0);
		materials[0].setSrgb(1.0,1.0,1.0);
		materials[0].setP(foundP);
		// the largest ball
		g[1] = new Geometry();
		g[1].globe(globeI);
		materials[1] = new Material();
		materials[1].setArgb(white,white,white);	// white
		materials[1].setDrgb(1.0,1.0,1.0);
		materials[1].setSrgb(1.0,1.0,1.0);
		materials[1].setP(ballP);
		// the middle ball
		g[2] = new Geometry();
		g[2].globe(globeI);
		materials[2] = new Material();
		materials[2].setArgb(white,white,white);	// white
		materials[2].setDrgb(1.0,1.0,1.0);
		materials[2].setSrgb(1.0,1.0,1.0);
		materials[2].setP(ballP);
		// the head ball
		g[3] = new Geometry();
		g[3].globe(globeI);
		materials[3] = new Material();
		materials[3].setArgb(white,white,white);	// white
		materials[3].setDrgb(1.0,1.0,1.0);
	//	materials[3].setSrgb(1.0,1.0,1.0);
		materials[3].setSrgb(0.5,0.5,0.5);
		materials[3].setP(ballP);
		// the hat
		g[4] = new Geometry();
		g[4].cylinder(hatI);
		materials[4] = new Material();
		materials[4].setArgb(black,black,black);	// black
		materials[4].setDrgb(0.1,0.1,0.1);
		materials[4].setSrgb(1.0,1.0,1.0);
		materials[4].setP(hatP);
		// the hat bottom
		g[5] = new Geometry();
		g[5].cylinder(hatI);
		materials[5] = new Material();
		materials[5].setArgb(black,black,black);	// black
		materials[5].setDrgb(0.1,0.1,0.1);
		materials[5].setSrgb(1.0,1.0,1.0);
		materials[5].setP(hatP);
		// the eye 
		g[6] = new Geometry();
		g[6].globe(eyeI);
		materials[6] = new Material();
		materials[6].setArgb(black,black,black);	// black
		materials[6].setDrgb(0.1,0.1,0.1);
		materials[6].setSrgb(1.0,1.0,1.0);
		materials[6].setP(hatP);
		// the eye 
		g[7] = new Geometry();
		g[7].globe(eyeI);
		materials[7] = new Material();
		materials[7].setArgb(black,black,black);	// black
		materials[7].setDrgb(0.1,0.1,0.1);
		materials[7].setSrgb(1.0,1.0,1.0);
		materials[7].setP(hatP);
		// the nose 
		g[8] = new Geometry();
		g[8].cylinder(eyeI);
		materials[8] = new Material();
		materials[8].setArgb(255.0/255, 160.0/255, 0);	// oriange
		materials[8].setDrgb(0.1,0.1,0.1);
		materials[8].setSrgb(1.0,1.0,1.0);
		materials[8].setP(hatP);
		// the bottons 
		g[9] = new Geometry();
		g[9].globe(eyeI);
		materials[9] = new Material();
		materials[9].setArgb(black,black,black);	// oriange
		materials[9].setDrgb(0.1,0.1,0.1);
		materials[9].setSrgb(1.0,1.0,1.0);
		materials[9].setP(hatP);
		g[10] = new Geometry();
		g[10].globe(eyeI);
		materials[10] = new Material();
		materials[10].setArgb(black,black,black);	// oriange
		materials[10].setDrgb(0.1,0.1,0.1);
		materials[10].setSrgb(1.0,1.0,1.0);
		materials[10].setP(hatP);
		g[11] = new Geometry();
		g[11].globe(eyeI);
		materials[11] = new Material();
		materials[11].setArgb(black,black,black);	// oriange
		materials[11].setDrgb(0.1,0.1,0.1);
		materials[11].setSrgb(1.0,1.0,1.0);
		materials[11].setP(hatP);

		// copy g[] to t[]
		for (int i=0; i<geoI; i++) {
			t[i] = g[i].clone();
		}

		/////////////////// light //////////////////
		lights[0] = new Light();
		lights[0].setPosition(20, 5, 5);
	//	lights[0].setPosition(10, 20, 10);
		lights[0].setIrgb(0, 0, 1);	// red
		normalize(lights[0].Lxyz);

		lights[1] = new Light();
		lights[1].setPosition(-20, 0, -5);
		lights[1].setIrgb(1, 1, 1);	// green
		normalize(lights[1].Lxyz); 
	}

	public void initFrame(double time) { // add animation here!!!!!!
		boolean rotate = false;
		rotate = true;
		// fundation
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(0,foundPosition,0);
		m.scale(foundW, foundH, foundW);
		m.rotateX(-Math.PI/2);
		m.transform(g[0], t[0]);
		// largest ball
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(ballLX,ballLPosition,ballLZ);
		m.scale(ballLR,ballLR,ballLR);
		m.rotateX(Math.PI/2);
		m.transform(g[1], t[1]);
		// middle ball
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(ballLX,ballMPosition,ballLZ);
		m.scale(ballMR,ballMR,ballMR);
		m.rotateX(Math.PI/2);
		m.transform(g[2], t[2]);
		// head ball
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(ballLX,ballHPosition,ballLZ);
		m.scale(ballHR,ballHR,ballHR);
		m.rotateX(Math.PI/2);
		m.transform(g[3], t[3]);
		// hat
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(ballLX,ballHPosition,ballLZ);
		m.rotateZ(-Math.PI/18);
		m.translate(0,hatPosition-ballHPosition,0);
		m.scale(hatR,hatR,hatR);
		m.rotateX(Math.PI/2);
		m.transform(g[4], t[4]);
		// hat bottom
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(ballLX,ballHPosition,ballLZ);
		m.rotateZ(-Math.PI/18);
		m.translate(0,hatBPosition-ballHPosition,0);
		m.scale(hatBR,hatBH,hatBR);
		m.rotateX(Math.PI/2);
		m.transform(g[5], t[5]);
		// eye
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(eyeX,eyeY,Math.sqrt(Math.pow(ballHR,2)-Math.pow(eyeX,2)-Math.pow(eyeY,2)));
		m.translate(ballLX,ballHPosition,ballLZ);
		m.scale(eyeW,eyeH,eyeT);
		m.rotateZ(Math.PI/18);
		m.rotateX(Math.PI/2);
		m.transform(g[6], t[6]);
		// eye
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(-eyeX,eyeY,Math.sqrt(Math.pow(ballHR,2)-Math.pow(eyeX,2)-Math.pow(eyeY,2)));
		m.translate(ballLX,ballHPosition,ballLZ);
		m.scale(eyeW,eyeH,eyeT);
		m.rotateZ(-Math.PI/18);
		m.rotateX(Math.PI/2);
		m.transform(g[7], t[7]);
		// nose
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(ballLX,ballHPosition,ballLZ+ballHR);
		m.scale(noseR,noseR,noseL);
		m.transform(g[8], t[8]);
		// botton 1
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(0,bottonY,Math.sqrt(Math.pow(ballMR,2)-Math.pow(bottonY,2)));
		m.translate(ballLX,ballMPosition,ballLZ);
		m.scale(bottonR,bottonR,bottonT);
		m.rotateX(Math.PI/2);
		m.transform(g[9], t[9]);
		// botton 2
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(0,-bottonY/2,Math.sqrt(Math.pow(ballMR,2)-Math.pow(bottonY/2,2)));
		m.translate(ballLX,ballMPosition,ballLZ);
		m.scale(bottonR,bottonR,bottonT);
		m.rotateX(Math.PI/2);
		m.transform(g[10], t[10]);
		// botton 3
		m.identity();
		if (rotate)
			m.rotateY(time * speed);
		m.translate(0,bottonY,Math.sqrt(Math.pow(ballLR,2)-Math.pow(bottonY,2)));
		m.translate(ballLX,ballLPosition,ballLZ);
		m.scale(bottonR,bottonR,bottonT);
		m.rotateX(Math.PI/2);
		m.transform(g[11], t[11]);
	}

	public void computeImage(double time) {
		initFrame(time);
		// initial each pixar pz depth
		for (int i=0; i<W*H; i++) {
			pz_pix[i] = 0;
		}
		// initialize each pixar color to background color
		for (int i = 0; i<H*W; i++)
			pix[i] = pack((int)(background[0]*255),(int)(background[1]*255),(int)(background[2]*255));
		// for each shapes
		for (int i=0; i<geoI; i++) {
			double vertex[][] = t[i].vertices;
			double normal[][] = t[i].normals;
			int face[][] = t[i].faces;
			// calculate rgb color for each vertice
			for (int j=0; j<vertex.length; j++) {
				normalize(normal[j]);
				double I[] = { vertex[i][0]-viewX, vertex[i][1]-viewY, vertex[i][2]-viewZ };
				normalize(I);
				double dotP = dot(normal[j],I);
				double R[] = {I[0] - 2.0*dotP*normal[j][0], I[1] - 2.0*dotP*normal[j][1], I[2] - 2.0*dotP*normal[j][2]};
				normalize(R);
				double p = materials[i].p;
				t[i].colors[j][0] = materials[i].Argb[0];
				t[i].colors[j][1] = materials[i].Argb[1];
				t[i].colors[j][2] = materials[i].Argb[2];
				// for each light!
				for (int iL=0; iL<lights.length; iL++) {
					double dotLN = Math.max(0,dot(lights[iL].Lxyz,normal[j]));
					double dotLV = Math.pow(Math.max(0,dot(lights[iL].Lxyz,R)),p);
					t[i].colors[j][0] += lights[iL].Irgb[0]*(materials[i].Drgb[0]*dotLN+materials[i].Srgb[0]*dotLV);
					t[i].colors[j][1] += lights[iL].Irgb[1]*(materials[i].Drgb[1]*dotLN+materials[i].Srgb[1]*dotLV);
					t[i].colors[j][2] += lights[iL].Irgb[2]*(materials[i].Drgb[2]*dotLN+materials[i].Srgb[2]*dotLV);
				}
			}
			for (int j=0; j<face.length; j++) {
			// first half => triangle
				draw.set1vertex(vertex[face[j][0]][0], vertex[face[j][0]][1], vertex[face[j][0]][2]);
				draw.set2vertex(vertex[face[j][1]][0], vertex[face[j][1]][1], vertex[face[j][1]][2]);
				draw.set3vertex(vertex[face[j][2]][0], vertex[face[j][2]][1], vertex[face[j][2]][2]);
				draw.set1rgb(t[i].colors[face[j][0]][0],t[i].colors[face[j][0]][1],t[i].colors[face[j][0]][2]);
				draw.set2rgb(t[i].colors[face[j][1]][0],t[i].colors[face[j][1]][1],t[i].colors[face[j][1]][2]);
				draw.set3rgb(t[i].colors[face[j][2]][0],t[i].colors[face[j][2]][1],t[i].colors[face[j][2]][2]);
				perspective(draw);
				draw.toTrapezoids();
				interpTri(draw);
			// second half => triangle
				draw.set1vertex(vertex[face[j][0]][0], vertex[face[j][0]][1], vertex[face[j][0]][2]);
				draw.set2vertex(vertex[face[j][2]][0], vertex[face[j][2]][1], vertex[face[j][2]][2]);
				draw.set3vertex(vertex[face[j][3]][0], vertex[face[j][3]][1], vertex[face[j][3]][2]);
				draw.set1rgb(t[i].colors[face[j][0]][0],t[i].colors[face[j][0]][1],t[i].colors[face[j][0]][2]);
				draw.set2rgb(t[i].colors[face[j][2]][0],t[i].colors[face[j][2]][1],t[i].colors[face[j][2]][2]);
				draw.set3rgb(t[i].colors[face[j][3]][0],t[i].colors[face[j][3]][1],t[i].colors[face[j][3]][2]);
				perspective(draw);
				draw.toTrapezoids();
				interpTri(draw);
			}
		}
	}
		
	public void viewport(double src[], int dst[]) {
		dst[0] = (int) ( 0.5 * W + src[0] * scale );
		dst[1] = (int) ( 0.5 * H - src[1] * scale );
	}

	public void perspective(Triangle tri) {
		for (int i=0; i<3; i++) {
			tri.vertex[i][0] *= viewZ/(viewZ-tri.vertex[i][2]);
			tri.vertex[i][1] *= viewZ/(viewZ-tri.vertex[i][2]);
			tri.vertex[i][2] = 1.0/(viewZ-tri.vertex[i][2]);
		}
	}

	public void normalize(double src[]) {
		double norm = Math.sqrt(dot(src,src));
		for (int i=0; i<src.length; i++) {
			src[i] /= norm;
		}
	}

	public double dot(double src[], double srr[]) {
		double temp = 0;
		for (int i=0; i<src.length; i++) {
			temp += src[i]*srr[i];
		}
		return temp;
	}

	public void interpTri(Triangle draw) {
		for (int i=0; i<4; i++) {
			viewport(draw.trapezoidV[i],trapezoid[i]);
			trapezoid[i][2] = (int)(draw.trapezoidV[i][2] * enlarge);
			trapezoidRGB[i][0] = (int)(draw.trapezoidRGB[i][0] * enlarge);
			trapezoidRGB[i][1] = (int)(draw.trapezoidRGB[i][1] * enlarge);
			trapezoidRGB[i][2] = (int)(draw.trapezoidRGB[i][2] * enlarge);
		}
		interpTrape(trapezoid[0],trapezoid[0],trapezoid[1],trapezoid[2],trapezoidRGB[0],trapezoidRGB[0],trapezoidRGB[1],trapezoidRGB[2]);
		interpTrape(trapezoid[1],trapezoid[2],trapezoid[3],trapezoid[3],trapezoidRGB[1],trapezoidRGB[2],trapezoidRGB[3],trapezoidRGB[3]);
	}

	public void interpTrape(int tl[], int tr[], int bl[], int br[], int tlRGB[], int trRGB[], int blRGB[], int brRGB[]) {
		int n = bl[1]-tl[1];	// BLy - TLy
		if (n == 0) {
			n = large;
		} 
		int dL_dy_x = ( bl[0] - tl[0]) * enlarge / n;	// BL - TL
	        int dL_dy_y = enlarge;
		int dL_dy_z = ( bl[2] - tl[2]) * enlarge / n;
		int dL_dy_r = ( blRGB[0] - tlRGB[0]) / n;
		int dL_dy_g = ( blRGB[1] - tlRGB[1]) / n;
		int dL_dy_b = ( blRGB[2] - tlRGB[2]) / n;
		int dR_dy_x = ( br[0] - tr[0]) * enlarge / n;	// BR - TR
		int dR_dy_y = enlarge;
		int dR_dy_z = ( br[2] - tr[2]) * enlarge / n;
		int dR_dy_r = ( brRGB[0] - trRGB[0]) / n;
		int dR_dy_g = ( brRGB[1] - trRGB[1]) / n;
		int dR_dy_b = ( brRGB[2] - trRGB[2]) / n;
		int L_x = tl[0] * enlarge;	// TL
		int L_y = tl[1] * enlarge;
		int L_z = tl[2] * enlarge;
		int L_r = tlRGB[0];
		int L_g = tlRGB[1];
		int L_b = tlRGB[2];
		int R_x = tr[0] * enlarge;	// TR
		int R_y = tr[1] * enlarge;
		int R_z = tr[2] * enlarge;
		int R_r = trRGB[0];
		int R_g = trRGB[1];
		int R_b = trRGB[2];
		for (int y=tl[1]; y<bl[1]; y++) {		// TL to BL
			L_x += dL_dy_x;	L_y += dL_dy_y;	L_z += dL_dy_z;
			L_r += dL_dy_r;	L_g += dL_dy_g;	L_b += dL_dy_b;
			R_x += dR_dy_x;	R_y += dR_dy_y;	R_z += dR_dy_z;
			R_r += dR_dy_r;	R_g += dR_dy_g;	R_b += dR_dy_b;
			int m = (R_x - L_x);
			if (m == 0) {
				m = enlarge;
			} 
			int d_dx_x = (R_x - L_x) * enlarge / m;
		        int d_dx_y = (R_y - L_y) * enlarge / m;
			int d_dx_z = (R_z - L_z) * enlarge / m;
			int d_dx_r = (R_r - L_r) * enlarge / m;
			int d_dx_g = (R_g - L_g) * enlarge / m;
			int d_dx_b = (R_b - L_b) * enlarge / m;
			rgb[0] = L_r;
			rgb[1] = L_g;
			rgb[2] = L_b;
			pz = L_z;
			for (int x=L_x/enlarge; x<=R_x/enlarge; x++) {
				rgb[0] += d_dx_r;
				rgb[1] += d_dx_g;
				rgb[2] += d_dx_b;
				pz += d_dx_z;
				if (0 <= y && y < H && 0 <= x && x < W && pz > pz_pix[y*W+x]) {
					pz_pix[y*W+x] = pz;
					pix[y*W+x] = pack(rgb[0]*255/enlarge,rgb[1]*255/enlarge,rgb[2]*255/enlarge);
				}
			}
		}
	}
}
