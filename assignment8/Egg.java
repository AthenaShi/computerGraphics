import java.util.*;

public class Egg extends MISApplet {
	double f = 10;
	double scale = 10;
	double norm = 0;
	double sqrt = 0;
	double temp = 0;
	double epsilon = 0.01;
	double background[] = {0.0,0.0,0.0};
	int geoI = 1;

	Geometry geoOrigin[] = new Geometry[geoI];
	Geometry geometrys[] = new Geometry[geoI];
	Material materials[] = new Material[geoI];
	Light lights[] = new Light[2];

	double v[] = new double[3];	// original of the ray
	double w[] = new double[3];	// direction of the ray

	public Egg() {
		initialize();
	}

	public void initialize() {
		geoOrigin[0] = new Geometry();
		geoOrigin[0].Cylinder();
		geoOrigin[0].Plane(0,1,0,-1);
		geoOrigin[0].Plane(0,-1,0,-1);
		materials[0] = new Material();
		materials[0].setArgb(1.0,0.0,0.0);	// red
		materials[0].setDrgb(1.0,1.0,1.0);
		materials[0].setSrgb(1.0,1.0,1.0);
		materials[0].setP(1);
		materials[0].setMCrgb(0);

		lights[0] = new Light();
		lights[0].setPosition(10, 20, 10);
		lights[0].setIrgb(0.7, 0.7, 0.7);
		normalize(lights[0].Lxyz);

		lights[1] = new Light();
		lights[1].setPosition(-10,-20, -10);
		lights[1].setIrgb(0.7,0.7,0.7);
		//lights[1].setIrgb(0.3, 0.3, 0.3);
		normalize(lights[1].Lxyz); 
	}

	public void initFrame(double time) { // add animation here!!!!!!
		for (int iG=0; iG<geoOrigin.length; iG++) {
			geometrys[iG] = geoOrigin[iG].clone();
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
	//	System.out.println("W:\t"+WW+"\t"+HH);
		System.out.println("x:\t"+x+"\t"+y);
		System.out.println("v:\t"+v[0]+"\t"+v[1]+"\t"+v[2]);
		System.out.println("w:\t"+w[0]+"\t"+w[1]+"\t"+w[2]);
		double colorRatio[] = getRayColor(v,w);
		rgb[0] = Math.max(0, Math.min(255, (int)(colorRatio[0] * 255)));
		rgb[1] = Math.max(0, Math.min(255, (int)(colorRatio[1] * 255)));
		rgb[2] = Math.max(0, Math.min(255, (int)(colorRatio[2] * 255)));
	}

	private double[] getRayColor(double v[], double w[]) {
		double p = 1.0;
		double t1 = 0;			// temp value of the ray
		double t2 = 0;			// temp value of the ray
		double t = 0;			// temp value of the ray
		boolean interact = false, nearestInter = false;
		double tNearest = 1000000000;
		double S[] = new double[3];	// surface of the interact
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
		double A = 0, B = 0, C = 0;
		double a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0, i = 0, j = 0;
		HitList hitList = new HitList();

		for (int iG=0; iG<geometrys.length; iG++) {
			for (int iSubG=0; iSubG<geometrys[iG].a.size(); iSubG++) {
				a = geometrys[iG].a.get(iSubG);
				b = geometrys[iG].b.get(iSubG);
				c = geometrys[iG].c.get(iSubG);
				d = geometrys[iG].d.get(iSubG);
				e = geometrys[iG].e.get(iSubG);
				f = geometrys[iG].f.get(iSubG);
				g = geometrys[iG].g.get(iSubG);
				h = geometrys[iG].h.get(iSubG);
				i = geometrys[iG].i.get(iSubG);
				j = geometrys[iG].j.get(iSubG);
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
						t1 = (-B - Math.sqrt(sqrt))/(2.0*A);
						t2 = (-B + Math.sqrt(sqrt))/(2.0*A);
						if (t1 > 0) {	// t2 > 0
							S[0] = w[0]*t1+v[0];
							S[1] = w[1]*t1+v[1];
							S[2] = w[2]*t1+v[2];
							N[0] = 2.0*a*S[0] + f*S[1] + e*S[2] + g;
							N[1] = 2.0*b*S[1] + d*S[2] + f*S[0] + h;
							N[2] = 2.0*c*S[2] + e*S[0] + d*S[1] + i;
							int in1 = dot(w,N)<0 ? 1 : -1;
							System.out.println("S:\t"+S[0]+"\t"+S[1]+"\t"+S[2]);
							System.out.println("N:\t"+N[0]+"\t"+N[1]+"\t"+N[2]);
							System.out.println("dot:\t"+dot(w,N));

							S[0] = w[0]*t2+v[0];
							S[1] = w[1]*t2+v[1];
							S[2] = w[2]*t2+v[2];
							N[0] = 2.0*a*S[0] + f*S[1] + e*S[2] + g;
							N[1] = 2.0*b*S[1] + d*S[2] + f*S[0] + h;
							N[2] = 2.0*c*S[2] + e*S[0] + d*S[1] + i;
							int in2 = dot(w,N)<0 ? 1 : -1;
							System.out.println("S:\t"+S[0]+"\t"+S[1]+"\t"+S[2]);
							System.out.println("N:\t"+N[0]+"\t"+N[1]+"\t"+N[2]);
							System.out.println("dot:\t"+dot(w,N));

							double thisList[][] = { {t1, iSubG, in1}, {t2, iSubG, in2} };
							System.out.println("setList1:\t"+thisList[0][0]+"\t"+thisList[0][1]+"\t"+thisList[0][2]);
							System.out.println("setList2:\t"+thisList[1][0]+"\t"+thisList[1][1]+"\t"+thisList[1][2]);
							if (iSubG == 0) {
								hitList.Put(thisList);
							} else {
								if (hitList.isEmpty())
									break;
								else
									hitList.Union(thisList);
							}
							if (hitList.isEmpty())
								break;
						}
					}
		//		       	else {
		//				break;
		//			}
				} else {
					t = -(g*v[0] + h*v[1] + i*v[2] + j)/(g*w[0] + h*w[1] + i*w[2]);
					System.out.println("t:\t"+t);
					if (t>0) {
						S[0] = w[0]*t+v[0];
						S[1] = w[1]*t+v[1];
						S[2] = w[2]*t+v[2];
						N[0] = 2.0*a*S[0] + f*S[1] + e*S[2] + g;
						N[1] = 2.0*b*S[1] + d*S[2] + f*S[0] + h;
						N[2] = 2.0*c*S[2] + e*S[0] + d*S[1] + i;
						int in = dot(w,N)<0 ? 1 : -1;
						System.out.println("S:\t"+S[0]+"\t"+S[1]+"\t"+S[2]);
						System.out.println("N:\t"+N[0]+"\t"+N[1]+"\t"+N[2]);
						System.out.println("dot:\t"+dot(w,N));
						double thisList[][] = { {t, iSubG, in} };
						System.out.println("setList:\t"+thisList[0][0]+"\t"+thisList[0][1]+"\t"+thisList[0][2]);
						if (iSubG == 0) {
							hitList.Put(thisList);
						} else {
							if (hitList.isEmpty())
								break;
							else
								hitList.Union(thisList);
						}
						if (hitList.isEmpty())
							break;
					} 
				//	else {
				//		break;
				//	}
				}
			}

			if (!hitList.isEmpty())
				interact = true;
		}
		System.out.println("Interact:\t"+interact);

		if (!interact) {	// return background color r g b in 0-1 scope
			colorReturn[0] = (double)(background[0]/255.0);
			colorReturn[1] = (double)(background[1]/255.0);
			colorReturn[2] = (double)(background[2]/255.0);
			return colorReturn;
		} else {

		for (int iG=0; iG<geometrys.length; iG++) {	//////////////////////////???!!!
			mc[0] = materials[iG].mCrgb[0];
			mc[1] = materials[iG].mCrgb[1];
			mc[2] = materials[iG].mCrgb[2];
			a = geometrys[iG].a.get(0);
			b = geometrys[iG].b.get(0);
			c = geometrys[iG].c.get(0);
			d = geometrys[iG].d.get(0);
			e = geometrys[iG].e.get(0);
			f = geometrys[iG].f.get(0);
			g = geometrys[iG].g.get(0);
			h = geometrys[iG].h.get(0);
			i = geometrys[iG].i.get(0);
			j = geometrys[iG].j.get(0);
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
				if (sqrt >= 0) {	// if interact with sphere iG
					t = (-B - Math.sqrt(sqrt))/(2.0*A);
					if (t < tNearest && t > 0) {
						nearestInter = true;
						tNearest = t;
					} else
						nearestInter = false;
				}
			} else {
				t = -(g*v[0] + h*v[1] + i*v[2] + j)/(g*w[0] + h*w[1] + i*w[2]);
				if (t < tNearest && t > 0) {
					nearestInter = true;
					tNearest = t;
				} else
					nearestInter = false;
			}

				if (nearestInter) {
				//	System.out.println(a+"\t"+b+"\t"+c+"\t"+d+"\t"+e+"\t"+f+"\t"+g+"\t"+h+"\t"+i+"\t"+j);
				//	System.out.println("v:\t"+v[0]+"\t"+v[1]+"\t"+v[2]);
				//	System.out.println("w:\t"+w[0]+"\t"+w[1]+"\t"+w[2]);
				//	System.out.println("A:\t"+A+"\t"+B+"\t"+C+"\t"+sqrt);
					S[0] = w[0]*t+v[0];
					S[1] = w[1]*t+v[1];
					S[2] = w[2]*t+v[2];
				//	System.out.println("S:\t"+S[0]+"\t"+S[1]+"\t"+S[2]);
					N[0] = 2.0*a*S[0] + f*S[1] + e*S[2] + g;
					N[1] = 2.0*b*S[1] + d*S[2] + f*S[0] + h;
					N[2] = 2.0*c*S[2] + e*S[0] + d*S[1] + i;
					normalize(N);
				//	System.out.println("N:\t"+N[0]+"\t"+N[1]+"\t"+N[2]);
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
