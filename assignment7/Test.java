import java.awt.*;

public class Test extends BufferedApplet
{
	int width = 0;
	int height = 0;
	double f = 10;
	double scale = 10;
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
	

	public Test() {
		initialize();
	}

	public void render(Graphics g) {
		width = getWidth();
		height = getHeight();
		g.setColor(background);

		rayTracingAndDraw(g);
	}

	public void initialize() {
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
			}
		}
	}

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
