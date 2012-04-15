import java.util.*;

public class Egg extends MISApplet {
	double f = 10;
	double scale = 10;
	double norm = 0;
	double sqrt = 0;
	double temp = 0;
	double epsilon = 0.01;
	double speed = 0.5;
//	double background[] = {255.0, 255.0, 255.0};
	double background[] = {0, 0, 0};
//	double background[] = {65.0, 105.0, 225.0};
	int geoI = 4;
	int traceTime = 0;
	///////////// object parameters /////////////
	// parameter of foundation
	double foundW = 4;
	double foundH = 0.3;
	double foundPosition = -2;
	double foundP = 1;
	double foundMC = 0.3;
	// parameter of pipe
	double pipeOut = 1;
	double pipeIn = pipeOut*2.0/3.0;
	double pipeH = pipeOut;
	double pipeX = -2;
	double pipeZ = -1;
	double pipeMC = 0.3;
	double pipeP = 10;

	Geometry geoOrigin[] = new Geometry[geoI];
	Geometry geometrys[] = new Geometry[geoI];
	Material materials[] = new Material[geoI];
	Light lights[] = new Light[1];
	Matrix m = new Matrix();

	double v[] = {0,0,0};	// original of the ray
	double w[] = {0,0,0};	// direction of the ray

	public Egg() {
		initialize();
	}

	public void initialize() {
		//////////// object //////////////////
		// the foundation
		geoOrigin[0] = new Geometry();
		geoOrigin[0].Sphere();
		m.identity();
		m.translate(0,foundPosition,0);
		m.scale(foundW,foundW,foundW);
		m.transform(geoOrigin[0]);
		geoOrigin[0].Plane(0,1,0,-foundPosition);
		geoOrigin[0].Plane(0,-1,0,foundPosition-foundH); 

		materials[0] = new Material();
		materials[0].setArgb(156.0/255.0, 102.0/255.0, 31.0/255.0);	// brick
		materials[0].setDrgb(1.0,1.0,1.0);
		materials[0].setSrgb(1.0,1.0,1.0);
		materials[0].setP(foundP);
		materials[0].setMCrgb(foundMC);

		// the pipe
		geoOrigin[1] = new Geometry();
		geoOrigin[1].Cylinder();
		m.identity();
		m.translate(pipeX,0,pipeZ);
		m.scale(pipeOut,pipeOut,pipeOut);
		m.transform(geoOrigin[1]);
		geoOrigin[1].Cylinder();
		m.identity();
		m.translate(pipeX,0,pipeZ);
		m.scale(pipeIn, pipeIn, pipeIn);
		m.transform(geoOrigin[1]);
		geoOrigin[1].reverse();
		geoOrigin[1].Plane(0,1,0,-foundPosition-pipeH);
		geoOrigin[1].Plane(0,-1,0,foundPosition+pipeH*4.0/5); 

		materials[1] = new Material();
		materials[1].setArgb(0,1,0);	// green
		materials[1].setDrgb(1.0,1.0,1.0);
		materials[1].setSrgb(1.0,1.0,1.0);
		materials[1].setP(pipeP);
		materials[1].setMCrgb(pipeMC);

		geoOrigin[2] = new Geometry();
		geoOrigin[2].Cylinder();
		m.identity();
		m.translate(pipeX,0,pipeZ);
		m.scale(pipeOut*0.85,pipeOut*0.85,pipeOut*0.85);
		m.transform(geoOrigin[2]);
		geoOrigin[2].Cylinder();
		m.identity();
		m.translate(pipeX,0,pipeZ);
		m.scale(pipeIn, pipeIn, pipeIn);
		m.transform(geoOrigin[2]);
		geoOrigin[2].reverse();
		geoOrigin[2].Plane(0,1,0,-foundPosition-pipeH*4.0/5);
		geoOrigin[2].Plane(0,-1,0,foundPosition); 

		materials[2] = new Material();
		materials[2].setArgb(0,1,0);	// green
		materials[2].setDrgb(1.0,1.0,1.0);
		materials[2].setSrgb(1.0,1.0,1.0);
		materials[2].setP(pipeP);
		materials[2].setMCrgb(pipeMC);

		geoOrigin[3] = new Geometry();
		geoOrigin[3].Sphere();
		m.identity();
		m.translate(0.5,-1,pipeZ);
		m.transform(geoOrigin[3]);

		materials[3] = new Material();
		materials[3].setArgb(0,0,1);	// green
		materials[3].setDrgb(1.0,1.0,1.0);
		materials[3].setSrgb(1.0,1.0,1.0);
		materials[3].setP(pipeP);
		materials[3].setMCrgb(pipeMC);


		/////////////////// light //////////////////
		lights[0] = new Light();
		lights[0].setPosition(10, 0, 0);
		lights[0].setIrgb(0.7, 0.7, 0.7);
		normalize(lights[0].Lxyz);

/*		lights[1] = new Light();
		lights[1].setPosition(-10,0, 0);
		lights[1].setIrgb(0.1, 0.1, 0.1);
		normalize(lights[1].Lxyz); */	
	}

	public void initFrame(double time) { // add animation here!!!!!!
//		m.identity();
//		m.rotateX(time * speed);
		for (int iG=0; iG<geoOrigin.length; iG++) {
			geometrys[iG] = geoOrigin[iG].clone();
//			m.transform(geoOrigin[iG], geometrys[iG]);
		}
	}


	public void setPixel(int x, int y, int rgb[]) { // SET ONE PIXEL'S COLOR
		WW = (double)W;
		HH = (double)H;
		v[0] = 0;
		v[1] = 0;
		v[2] = f;
		w[0] = (double)(x-0.5*WW)/WW*scale;
		w[1] = (double)(0.5*HH-y)/WW*scale;
		w[2] = -f;
		normalize(w);
		traceTime = 0;
		double colorRatio[] = getRayColor(v,w);
		rgb[0] = Math.max(0, Math.min(255, (int)(colorRatio[0] * 255)));
		rgb[1] = Math.max(0, Math.min(255, (int)(colorRatio[1] * 255)));
		rgb[2] = Math.max(0, Math.min(255, (int)(colorRatio[2] * 255)));
	}

	private double[] getRayColor(double v[], double w[]) {
		double p = 1.0;
		double t1 = 0, t2 = 0, t = 0;
		boolean interact = false, nearestInter = false;
		double tNearest = Double.MAX_VALUE;
		double S[] = {0,0,0};	// surface of the interact
		double N[] = {0,0,0};	// surface normal for a sphere
		double R[] = {0,0,0};	// reflection vector
		double phong[] = {0,0,0};
		double colorReturn[] = {0,0,0};
		double reflection[] = {0,0,0};
		double mc[] = {0,0,0};
		double Argb[] = {0.0,0.0,0.0}, Drgb[] = {0.0,0.0,0.0}, Srgb[] = {0.0,0.0,0.0}, Lxyz[] = {0.0,0.0,0.0}, Irgb[] = {0.0,0.0,0.0};
		double A = 0, B = 0, C = 0;
		double a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0, i = 0, j = 0;
		HitList hitList = new HitList();
		double hitFirestT[][] = new double[geoI][2];
		traceTime++;

		for (int iG=0; iG<geometrys.length; iG++) {
			for (int iSubG=0; iSubG<geometrys[iG].getSubNumber(); iSubG++) {
				a = geometrys[iG].a[iSubG];
				b = geometrys[iG].b[iSubG];
				c = geometrys[iG].c[iSubG];
				d = geometrys[iG].d[iSubG];
				e = geometrys[iG].e[iSubG];
				f = geometrys[iG].f[iSubG];
				g = geometrys[iG].g[iSubG];
				h = geometrys[iG].h[iSubG];
				i = geometrys[iG].i[iSubG];
				j = geometrys[iG].j[iSubG];
				if (a!=0 || b!=0 || c!=0 || d!=0 || e!=0 || f!=0 ) {
					A = a*w[0]*w[0] + b*w[1]*w[1] + c*w[2]*w[2] + 
						d*w[1]*w[2] + e*w[2]*w[0] + f*w[0]*w[1];
					B = 2.0*a*w[0]*v[0] + 2.0*b*w[1]*v[1] + 2.0*c*w[2]*v[2] +
						d*v[1]*w[2]+d*w[1]*v[2] + e*v[2]*w[0]+e*w[2]*v[0]+ f*v[0]*w[1]+f*w[0]*v[1] +
						g*w[0] + h*w[1] + i*w[2];
					C = a*v[0]*v[0] + b*v[1]*v[1] + c*v[2]*v[2] +
						d*v[1]*v[2] + e*v[2]*v[0] + f*v[0]*v[1] +
						g*v[0] + h*v[1] + i*v[2] + j;
					sqrt = B*B-4.0*A*C;
					if (sqrt > 0) {	// if interact with sphere iG
						double tempT1 = (-B - Math.sqrt(sqrt))/(2.0*A);
						double tempT2 = (-B + Math.sqrt(sqrt))/(2.0*A);
						t1 = tempT1 < tempT2 ? tempT1 : tempT2;
						t2 = tempT1 > tempT2 ? tempT1 : tempT2;
						if (t1 > 0) {	// t2 > 0
							S[0] = w[0]*t1+v[0]; S[1] = w[1]*t1+v[1]; S[2] = w[2]*t1+v[2];
							N[0] = 2.0*a*S[0] + f*S[1] + e*S[2] + g;
							N[1] = 2.0*b*S[1] + d*S[2] + f*S[0] + h;
							N[2] = 2.0*c*S[2] + e*S[0] + d*S[1] + i;
							int in1 = dot(w,N)<0 ? 1 : -1;
							S[0] = w[0]*t2+v[0]; S[1] = w[1]*t2+v[1]; S[2] = w[2]*t2+v[2];
							N[0] = 2.0*a*S[0] + f*S[1] + e*S[2] + g;
							N[1] = 2.0*b*S[1] + d*S[2] + f*S[0] + h;
							N[2] = 2.0*c*S[2] + e*S[0] + d*S[1] + i;
							int in2 = dot(w,N)<0 ? 1 : -1;
							double thisList[][] = { {t1, iSubG, in1}, {t2, iSubG, in2} };
							if (iSubG == 0) {
								hitList.Put(thisList);
							} else {
								if (hitList.isEmpty())
									break;
								else {
									hitList.Union(thisList);
								}
							}
							if (hitList.isEmpty())
								break;
						} else if (t2 > 0) {
							S[0] = w[0]*t2+v[0]; S[1] = w[1]*t2+v[1]; S[2] = w[2]*t2+v[2];
							N[0] = 2.0*a*S[0] + f*S[1] + e*S[2] + g;
							N[1] = 2.0*b*S[1] + d*S[2] + f*S[0] + h;
							N[2] = 2.0*c*S[2] + e*S[0] + d*S[1] + i;
							int in2 = dot(w,N)<0 ? 1 : -1;
							double thisList[][] = { {t2, iSubG, in2} };
							if (iSubG == 0) {
								hitList.Put(thisList);
							} else {
								if (hitList.isEmpty())
									break;
								else {
									hitList.Union(thisList);
								}
							}
							if (hitList.isEmpty())
								break;
						} else
							hitList.empty();
					} else {		// if not interact than empty the hitList
						if (A < 0) {	// if it's a "reversed" graph
							double thisList[][] = { {-1, iSubG, 1} };
							if (iSubG == 0) {
								hitList.Put(thisList);
							} else {
								if (hitList.isEmpty())
									break;
								else {
									hitList.Union(thisList);
								}
							}
						} else
							hitList.empty();
					}
				} else {	// if it's plane, it must have a hitList (if t<0, than start from t=-1
					double thisList[][] = { {-1,iSubG, 0} };
					if (g*w[0] + h*w[1] + i*w[2] == 0) {	// if ray is parallel with the plane
						thisList[0][2] = (g*v[0] + h*v[1] + i*v[2] + j)<0 ? 1 : -1;
					} else {
						t = -(g*v[0] + h*v[1] + i*v[2] + j)/(g*w[0] + h*w[1] + i*w[2]);
						S[0] = w[0]*t+v[0]; S[1] = w[1]*t+v[1]; S[2] = w[2]*t+v[2];
						N[0] = g;
						N[1] = h;
						N[2] = i;
						thisList[0][2] = dot(w,N)<0 ? 1 : -1;
						if (t>0) 	// if interact with the plane
							thisList[0][0] = t;
					}
					if (iSubG == 0) {
						hitList.Put(thisList);
					} else {
						if (hitList.isEmpty())
							break;
						else {
							hitList.Union(thisList);
						}
					}
					if (hitList.isEmpty())
						break;
				}
			}
			// after search each part in this geometry
			if (!hitList.isEmpty()) {
				interact = true;
				hitFirestT[iG][0] = hitList.data[0][0];
				hitFirestT[iG][1] = hitList.data[0][1];
			} else {
		//		interact = false;
				hitFirestT[iG][0] = Double.MAX_VALUE;
			}
		}

		if (!interact || traceTime>5 ) {	// return background color r g b in 0-1 scope
			colorReturn[0] = (double)(background[0]/255.0);
			colorReturn[1] = (double)(background[1]/255.0);
			colorReturn[2] = (double)(background[2]/255.0);
			traceTime = 0;
			return colorReturn;
		} else {
			for (int iG=0; iG<geometrys.length; iG++) {	//////////////////////////???!!!
				// if hit
				t = hitFirestT[iG][0];
				if (t < tNearest && t >= 0) {
					nearestInter = true;
					tNearest = t;
					int iSubG = (int)hitFirestT[iG][1];
					mc[0] = materials[iG].mCrgb[0];
					mc[1] = materials[iG].mCrgb[1];
					mc[2] = materials[iG].mCrgb[2];
					a = geometrys[iG].a[iSubG];
					b = geometrys[iG].b[iSubG];
					c = geometrys[iG].c[iSubG];
					d = geometrys[iG].d[iSubG];
					e = geometrys[iG].e[iSubG];
					f = geometrys[iG].f[iSubG];
					g = geometrys[iG].g[iSubG];
					h = geometrys[iG].h[iSubG];
					i = geometrys[iG].i[iSubG];
					j = geometrys[iG].j[iSubG];

					S[0] = w[0]*t+v[0];
					S[1] = w[1]*t+v[1];
					S[2] = w[2]*t+v[2];
					N[0] = 2.0*a*S[0] + f*S[1] + e*S[2] + g;
					N[1] = 2.0*b*S[1] + d*S[2] + f*S[0] + h;
					N[2] = 2.0*c*S[2] + e*S[0] + d*S[1] + i;
					normalize(N);
					R[0] = w[0] - 2.0*dot(N,w)*N[0];
					R[1] = w[1] - 2.0*dot(N,w)*N[1];
					R[2] = w[2] - 2.0*dot(N,w)*N[2];
					normalize(R);
					Argb = materials[iG].Argb;
					Drgb = materials[iG].Drgb;
					Srgb = materials[iG].Srgb;
					p = materials[iG].p;
					phong[0] = Argb[0];
					phong[1] = Argb[1];
					phong[2] = Argb[2];
					// for each light!
					for (int iL=0; iL<lights.length; iL++) {
						Lxyz = lights[iL].Lxyz;
						Irgb = lights[iL].Irgb;
						double dotLN = Math.max(0,dot(Lxyz,N));
						double dotLV = Math.pow(Math.max(0,dot(Lxyz,R)),p);
						phong[0] += Irgb[0]*(Drgb[0]*dotLN+Srgb[0]*dotLV);
						phong[1] += Irgb[1]*(Drgb[1]*dotLN+Srgb[1]*dotLV);
						phong[2] += Irgb[2]*(Drgb[2]*dotLN+Srgb[2]*dotLV);
					} 
					double newV[] = {0,0,0};
					newV[0] = S[0] + epsilon*R[0];
					newV[1] = S[1] + epsilon*R[1];
					newV[2] = S[2] + epsilon*R[2];
					double newW[] = {0,0,0};
					newW[0] = R[0];
					newW[1] = R[1];
					newW[2] = R[2];
					normalize(newW);
					reflection = getRayColor(newV, newW);
					colorReturn[0] = phong[0]*(1.0-mc[0]) + reflection[0]*mc[0];
					colorReturn[1] = phong[1]*(1.0-mc[1]) + reflection[1]*mc[1];
					colorReturn[2] = phong[2]*(1.0-mc[2]) + reflection[2]*mc[2]; 
				} else {
					nearestInter = false;
				}
			}
			return colorReturn;
		}
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
	private double WW = 0, HH = 0;

}
