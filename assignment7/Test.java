import java.awt.*;

public class Test extends BufferedApplet
{
	int width = 0;
	int height = 0;
	double f = 10;
	double scale = 10;
<<<<<<< HEAD
	double norm = 0;
	double sqrt = 0;
	double temp = 0;
	double epsilon = 0.01;
	double tempArray[] = new double[3];
	Color background = Color.black;

	Sphere spheres[] = new Sphere[3];
	Material materials[] = new Material[3];
	Light lights[] = new Light[2];

	Color drawRGB;
	double v[] = new double[3];	// original of the ray
	double w[] = new double[3];	// direction of the ray
	
=======
	double norm;
	double sqrt;
	double temp;
	double tempArray[] = new double[3];
	Color background = Color.black;

	Sphere sphere1;
	Material material1;
	Light light1;

	Color drawRGB;

	double v[] = new double[3];	// original of the ray
	double w[] = new double[3];	// direction of the ray
	double S[] = new double[3];	// surface of the sphere
	double c[] = new double[3];	// center of each sphere
	double r;			// radius of each sphere
	double t;			// temp value of the ray
	double N[] = new double[3];	// surface normal for a sphere
	double R[] = new double[3];	// reflection vector
	double phong[] = new double[3];
	double reflection[] = new double[3];
	double mc[] = new double[3];
	

>>>>>>> 02e67171e915d78a556f1af21015f49051904748
	public Test() {
		initialize();
	}

	public void render(Graphics g) {
		width = getWidth();
		height = getHeight();
		g.setColor(background);
<<<<<<< HEAD
=======

>>>>>>> 02e67171e915d78a556f1af21015f49051904748
		rayTracingAndDraw(g);
	}

	public void initialize() {
<<<<<<< HEAD
		spheres[0] = new Sphere(0,1,0,1);
		materials[0] = new Material();
		materials[0].setArgb(228.0/255,229.0/255,231.0/255);	// silver
		materials[0].setDrgb(197.0/255,198.0/255,200.0/255);
		materials[0].setSrgb(255.0/255,255.0/255,255.0/255);
/*		materials[0].setArgb(1.0,0.0,0.0);	// red
		materials[0].setDrgb(1.0,1.0,1.0);
		materials[0].setSrgb(1.0,1.0,1.0);	*/
		//materials[0].setSrgb(0.1,0.1,0.1);
		materials[0].setP(1);
		materials[0].setMCrgb(0.2);

		spheres[1] = new Sphere(-2,-1,0,1);
		materials[1] = new Material();
		materials[1].setArgb(255.0/255,200.0/255,100.0/255);	// 24k gold 218,178,115
		materials[1].setDrgb(70.0/255,40.0/255,0.0);
		materials[1].setSrgb(255.0/255,240.0/255,215.0/255);
/*		materials[1].setArgb(0.0,1.0,0.0);	// green
		materials[1].setDrgb(1.0,1.0,1.0);
		materials[1].setSrgb(1.0,1.0,1.0);	*/
		materials[1].setP(100);
		//materials[1].setMCrgb(0.751, 0.664, 0.289);
		materials[1].setMCrgb(0.01);

		spheres[2] = new Sphere(2,-1,0,1);
		materials[2] = new Material();
		materials[2].setArgb(255.0/255,214.0/255,196.0/255);	// Copper
		materials[2].setDrgb(225.0/255,87.0/255,51.0/255);
		materials[2].setSrgb(255.0/255,214.0/255,196.0/255);
/*		materials[2].setArgb(0.0,0.0,1.0);	// 24k gold 218,178,115
		materials[2].setDrgb(1.0,1.0,1.0);
		materials[2].setSrgb(1.0,1.0,1.0);	*/
		materials[2].setP(30);
		//materials[2].setP(1);
		materials[2].setMCrgb(0.05); 

		lights[0] = new Light();
		lights[0].setPosition(10, 20, 10);
		lights[0].setIrgb(0.7, 0.7, 0.7);
		normalize(lights[0].Lxyz);

		lights[1] = new Light();
		lights[1].setPosition(-10,-20, -10);
		lights[1].setIrgb(0.7,0.7,0.7);
		//lights[1].setIrgb(0.3, 0.3, 0.3);
		normalize(lights[1].Lxyz); 

/*		lights[2] = new Light();
		lights[2].setPosition(14, 12, 0.5);
		lights[2].setIrgb(0.5, 0, 0);
		normalize(lights[2].Lxyz);  */
=======
		sphere1 = new Sphere();
		sphere1.c[0] = 0;
		sphere1.c[1] = 0;
		sphere1.c[2] = 0;
		material1 = new Material();
		material1.Argb[0] = 1;
		material1.Argb[1] = 0;
		material1.Argb[2] = 0;
		material1.Drgb[0] = 0.5;
		material1.Drgb[1] = 0.5;
		material1.Drgb[2] = 0.9;
		material1.Srgb[0] = 0.8;
		material1.Srgb[1] = 0.8;
		material1.Srgb[2] = 0.8;
		material1.p = 7;
		light1 = new Light();
		normalize(light1.Lxyz);
>>>>>>> 02e67171e915d78a556f1af21015f49051904748
	}

	public void rayTracingAndDraw(Graphics g) {
		for (int row=0; row<height; row++) {
			for (int col=0; col<width; col++) {
				v[0] = 0;
				v[1] = 0;
				v[2] = f;
				w[0] = (col-0.5*width)/width*scale;
				w[1] = (0.5*height-row)/width*scale;
				w[2] = -f;
				normalize(w);
<<<<<<< HEAD
				double colorRatio[] = getRayColor(v,w);
				int colorR = Math.max(0, Math.min(255, (int)(colorRatio[0] * 255)));
				int colorG = Math.max(0, Math.min(255, (int)(colorRatio[1] * 255)));
				int colorB = Math.max(0, Math.min(255, (int)(colorRatio[2] * 255)));
				drawRGB = new Color(colorR, colorG, colorB);
				g.setColor(drawRGB);
				g.drawRect(col,row,1,1);
=======
				c[0] = sphere1.c[0];
				c[1] = sphere1.c[1];
				c[2] = sphere1.c[2];
				r = sphere1.r;
				mc = material1.mCrgb;
				sqrt = Math.pow(dot(w,minus(v,c)),2) - dot(minus(v,c),minus(v,c)) + Math.pow(r,2);
				if (sqrt >= 0) {
					t = -dot(w,minus(v,c))-Math.sqrt(sqrt);
					S = plus(multiply(w,t),v);
					N = divide(minus(S,c),r);
					R = minus(w, multiply(N,2*dot(N,w)));
					//double part[] = plus(multiply(material1.Drgb,Math.max(0,-dot(light1.Lxyz,N))), 
					//		multiply(material1.Srgb, Math.pow(Math.max(0,-dot(light1.Lxyz,R)),material1.p)));
					double part[] = plus(multiply(material1.Drgb,-dot(light1.Lxyz,N)), 
							multiply(material1.Srgb, Math.pow(-dot(light1.Lxyz,R),material1.p)));
					double part2[] = new double[3];
					part2[0] = part[0] * light1.Irgb[0];
					part2[1] = part[1] * light1.Irgb[1];
					part2[2] = part[2] * light1.Irgb[2];
					phong = plus(material1.Argb, part2);
					double color[] = {phong[0]*(1.0-mc[0])+light1.Irgb[0]*mc[0], phong[1]*(1.0-mc[1])+light1.Irgb[1]*mc[1], phong[2]*(1.0-mc[2])+light1.Irgb[2]*mc[2]};
					int colorR = Math.max(0, Math.min(255, (int)(color[0] * 255)));
					int colorG = Math.max(0, Math.min(255, (int)(color[1] * 255)));
					int colorB = Math.max(0, Math.min(255, (int)(color[2] * 255)));
					drawRGB = new Color(colorR, colorG, colorB);
					g.setColor(drawRGB);
					g.drawRect(col,row,1,1);
				} else {
					g.setColor(background);
					g.drawRect(col,row,1,1);
				}
>>>>>>> 02e67171e915d78a556f1af21015f49051904748
			}
		}
	}

<<<<<<< HEAD
	public double[] getRayColor(double v[], double w[]) {
	double p = 1.0;
	boolean interact = false;
	boolean nearestInter = false;
	double tNearest = 1000000000;
	double S[] = new double[3];	// surface of the sphere
	double c[] = new double[3];	// center of each sphere
	double r = 2;			// radius of each sphere
	double t = 0;			// temp value of the ray
	double N[] = new double[3];	// surface normal for a sphere
	double R[] = new double[3];	// reflection vector
	double phong[] = new double[3];
	double colorReturn[] = new double[3];
	double reflection[] = new double[3];
	double mc[] = new double[3];
	double Argb[] = new double[3];
	double Drgb[] = new double[3];
	double Srgb[] = new double[3];
	double Lxyz[] = new double[3];
	double Irgb[] = new double[3];

		for (int iS=0; iS<spheres.length; iS++) {
			c = spheres[iS].c;
			r = spheres[iS].r;
			//mc = materials[iS].mCrgb;
			mc[0] = materials[iS].mCrgb[0];
			mc[1] = materials[iS].mCrgb[1];
			mc[2] = materials[iS].mCrgb[2];
			sqrt = Math.pow( w[0]*(v[0]-c[0])+w[1]*(v[1]-c[1])+w[2]*(v[2]-c[2]),2) -
				((v[0]-c[0])*(v[0]-c[0]) + (v[1]-c[1])*(v[1]-c[1]) + (v[2]-c[2])*(v[2]-c[2])) +
				Math.pow(r,2);
			if (sqrt >= 0) {	// if interact with sphere iS
				t = -(w[0]*(v[0]-c[0])+w[1]*(v[1]-c[1])+w[2]*(v[2]-c[2])) - Math.sqrt(sqrt);
				if (t>0)
					interact = true;
			}
		}

		if (!interact) {	// return background color r g b in 0-1 scope
			colorReturn[0] = (double)(background.getRed()/255.0);
			colorReturn[1] = (double)(background.getGreen()/255.0);
			colorReturn[2] = (double)(background.getBlue()/255.0);
			return colorReturn;
		} else {

		for (int iS=0; iS<spheres.length; iS++) {
			c = spheres[iS].c;
			r = spheres[iS].r;
			mc[0] = materials[iS].mCrgb[0];
			mc[1] = materials[iS].mCrgb[1];
			mc[2] = materials[iS].mCrgb[2];
			sqrt = Math.pow( w[0]*(v[0]-c[0])+w[1]*(v[1]-c[1])+w[2]*(v[2]-c[2]),2) -
				((v[0]-c[0])*(v[0]-c[0]) + (v[1]-c[1])*(v[1]-c[1]) + (v[2]-c[2])*(v[2]-c[2])) +
				Math.pow(r,2);
			if (sqrt >= 0) {	// if interact with sphere iS
				t = -(w[0]*(v[0]-c[0])+w[1]*(v[1]-c[1])+w[2]*(v[2]-c[2])) - Math.sqrt(sqrt);
				if (t < tNearest && t > 0) {
					nearestInter = true;
					tNearest = t;
				} else
					nearestInter = false;
				if (nearestInter) {
					S[0] = w[0]*t+v[0];
					S[1] = w[1]*t+v[1];
					S[2] = w[2]*t+v[2];
					N[0] = (S[0]-c[0]) / r;
					N[1] = (S[1]-c[1]) / r;
					N[2] = (S[2]-c[2]) / r;
					R[0] = w[0] - 2.0*dot(N,w)*N[0];
					R[1] = w[1] - 2.0*dot(N,w)*N[1];
					R[2] = w[2] - 2.0*dot(N,w)*N[2];
					Argb = materials[iS].Argb;
					Drgb = materials[iS].Drgb;
					Srgb = materials[iS].Srgb;
					p = materials[iS].p;
					phong[0] = Argb[0];
					phong[1] = Argb[1];
					phong[2] = Argb[2];
					// for each light!
					for (int iL=0; iL<lights.length; iL++) {
						Lxyz = lights[iL].Lxyz;
						Irgb = lights[iL].Irgb;
						double dotLN = Math.max(0,dot(Lxyz,N));
						double dotLV = Math.max(0,Math.pow(dot(Lxyz,R),p));
						phong[0] += Irgb[0]*(Drgb[0]*dotLN+Srgb[0]*dotLV);
						phong[1] += Irgb[1]*(Drgb[1]*dotLN+Srgb[1]*dotLV);
						phong[2] += Irgb[2]*(Drgb[2]*dotLN+Srgb[2]*dotLV);
					} 
					double newV[] = new double[3];
					newV[0] = S[0] + epsilon*R[0];
					newV[1] = S[1] + epsilon*R[1];
					newV[2] = S[2] + epsilon*R[2];
					double newW[] = new double[3];
					newW[0] = R[0];
					newW[1] = R[1];
					newW[2] = R[2];
					normalize(newW);
					reflection = getRayColor(newV, newW);
					colorReturn[0] = phong[0]*(1.0-mc[0]) + reflection[0]*mc[0];
					colorReturn[1] = phong[1]*(1.0-mc[1]) + reflection[1]*mc[1];
					colorReturn[2] = phong[2]*(1.0-mc[2]) + reflection[2]*mc[2]; 
			/*		colorReturn[0] = phong[0];
					colorReturn[1] = phong[1];
					colorReturn[2] = phong[2]; */
				}
			}
		}
		return colorReturn;
		}
=======
	public double[] plus(int src[], double d) {
		for (int i=0; i<src.length; i++) {
			tempArray[i] = (double)src[i]+d;
		}
		return tempArray;
	}

	public double[] plus(int src[], double srr[]) {
		for (int i=0; i<src.length; i++) {
			tempArray[i] = (double)src[i]+srr[i];
		}
		return tempArray;
	}

	public double[] plus(double src[], double srr[]) {
		for (int i=0; i<src.length; i++) {
			tempArray[i] = src[i]+srr[i];
		}
		return tempArray;
	}

	public double[] minus(double d, double src[]) {
		for (int i=0; i<src.length; i++) {
			tempArray[i] = d-src[i];
		}
		return tempArray;
	}

	public double[] minus(int src[], double srr[]) {
		for (int i=0; i<src.length; i++) {
			tempArray[i] = (double)src[i]-srr[i];
		}
		return tempArray;
	}

	public double[] minus(double src[], double srr[]) {
		for (int i=0; i<src.length; i++) {
			tempArray[i] = src[i]-srr[i];
		}
		return tempArray;
	}

	public double[] divide(double src[], double d) {
		for (int i=0; i<src.length; i++) {
			tempArray[i] = src[i]/d;
		}
		return tempArray;
	}

	public double[] multiply(int src[], double d) {
		for (int i=0; i<src.length; i++) {
			tempArray[i] = (double)src[i]*d;
		}
		return tempArray;
	}

	public double[] multiply(double src[], double srr[]) {
		for (int i=0; i<src.length; i++) {
			tempArray[i] = src[i]*srr[i];
		}
		return tempArray;
	}

	public double[] multiply(double src[], double d) {
		for (int i=0; i<src.length; i++) {
			tempArray[i] = src[i]*d;
		}
		return tempArray;
>>>>>>> 02e67171e915d78a556f1af21015f49051904748
	}

	public double dot(int src[], double srr[]) {
		temp = 0;
		for (int i=0; i<src.length; i++) {
			temp += (double)src[i]*srr[i];
		}
		return temp;
	}

	public double dot(double src[], double srr[]) {
		temp = 0;
		for (int i=0; i<src.length; i++) {
			temp += src[i]*srr[i];
		}
		return temp;
	}
	
	public void normalize(double src[]) {
		norm = Math.sqrt(dot(src,src));
		for (int i=0; i<src.length; i++) {
			src[i] /= norm;
		}
	}
	public void viewport(double src[], int dst[]) {
		dst[0] = (int) ( 0.5 * width  + src[0] * scale );
		dst[1] = (int) ( 0.5 * height - src[1] * scale );
	}
}
