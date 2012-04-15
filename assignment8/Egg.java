import java.util.*;

public class Egg extends MISApplet {
	double f = 10;
	double scale = 10;
	double norm = 0;
	double sqrt = 0;
	double temp = 0;
	double epsilon = 0.01;
	double background[] = {0.0,0.0,0.0};
	int geoI = 3;
	int traceTime = 0;

	Geometry geoOrigin[] = new Geometry[geoI];
	Geometry geometrys[] = new Geometry[geoI];
	Material materials[] = new Material[geoI];
//	HitList geoList[] = new HitList[geoI];
	Light lights[] = new Light[1];

	double v[] = {0,0,0};	// original of the ray
	double w[] = {0,0,0};	// direction of the ray

	public Egg() {
		initialize();
	}

	public void initialize() {
		geoOrigin[0] = new Geometry();
		geoOrigin[0].Cylinder();
		geoOrigin[0].setG(-6);;
		geoOrigin[0].setJ(8);
		geoOrigin[0].Plane(0,1,0,-2);
		geoOrigin[0].Plane(0,-1,0,1); 

/*		geoOrigin[0].Plane(0,0,1,-5);
		geoOrigin[0].Plane(0,0,-1,0);
		geoOrigin[0].Plane(0,-1,0,-3);
		geoOrigin[0].Plane(0,1,0,-3);
		geoOrigin[0].Plane(1,0,0,-7);
		geoOrigin[0].Plane(-1,0,0,2); */ 

		materials[0] = new Material();
		materials[0].setArgb(0,0,1);	// blue
		materials[0].setDrgb(1.0,1.0,1.0);
		materials[0].setSrgb(1.0,1.0,1.0);
//		materials[0].setDrgb(0.5,0.5,0.5);
//		materials[0].setSrgb(0.5,0.5,0.5);
		materials[0].setP(1);
		materials[0].setMCrgb(0.3);

		geoOrigin[1] = new Geometry();
		geoOrigin[1].Cylinder();
		geoOrigin[1].Plane(0,-1,0,-1);
		geoOrigin[1].Plane(0,1,0,-3);

		geoOrigin[1].Cylinder();
		geoOrigin[1].setA(-1);
		geoOrigin[1].setB(-1);
		geoOrigin[1].setC(0);
		geoOrigin[1].setJ(0.5);

		materials[1] = new Material();
		materials[1].setArgb(1,0,0);	// red
		materials[1].setDrgb(1.0,1.0,1.0);
		materials[1].setSrgb(1.0,1.0,1.0);
//		materials[1].setDrgb(0.5,0.5,0.5);
//		materials[1].setSrgb(0.5,0.5,0.5);
		materials[1].setP(1);
		materials[1].setMCrgb(0); 

		geoOrigin[2] = new Geometry();
		geoOrigin[2].Sphere();
		geoOrigin[2].setG(4);
		geoOrigin[2].setJ(3);

//		geoOrigin[2].Plane(0,-1,0,-1);
//		geoOrigin[2].Plane(0,1,0,-3);
		materials[2] = new Material();
		materials[2].setArgb(0,1,0);	// green
		materials[2].setDrgb(1.0,1.0,1.0);
		materials[2].setSrgb(1.0,1.0,1.0);
//		materials[2].setDrgb(0.5,0.5,0.5);
//		materials[2].setSrgb(0.5,0.5,0.5);
		materials[2].setP(1);
		materials[2].setMCrgb(0.3); 

/*		geoOrigin[3] = new Geometry();
		geoOrigin[3].Sphere();
		geoOrigin[3].h[0] = 4;
		geoOrigin[3].j[0] = 3;

//		geoOrigin[3].Plane(0,-1,0,-1);
//		geoOrigin[3].Plane(0,1,0,-3);
		materials[3] = new Material();
		materials[3].setArgb(0,1,0);	// blue
		materials[3].setDrgb(1.0,1.0,1.0);
		materials[3].setSrgb(1.0,1.0,1.0);
//		materials[3].setDrgb(0.5,0.5,0.5);
//		materials[3].setSrgb(0.5,0.5,0.5);
		materials[3].setP(1);
		materials[3].setMCrgb(0.3);  */
		
		lights[0] = new Light();
		lights[0].setPosition(10, 30, 10);
		lights[0].setIrgb(0.7, 0.7, 0.7);
	//	lights[0].setIrgb(0, 0, 1);
		normalize(lights[0].Lxyz);

/*		lights[1] = new Light();
		lights[1].setPosition(-10,-20, -10);
	//	lights[1].setIrgb(0,0,1.0);
		lights[1].setIrgb(1, 0, 0);
		normalize(lights[1].Lxyz);	*/ 
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
	//	System.out.println("x:\t"+x+"\t"+y);
	//	System.out.println("v:\t"+v[0]+"\t"+v[1]+"\t"+v[2]);
	//	System.out.println("w:\t"+w[0]+"\t"+w[1]+"\t"+w[2]);
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
	//	System.out.println("Ray Tracing "+traceTime+" times!");

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
				//				System.out.println("print hit list!!! For: "+iG+" : "+iSubG);
				//				for (int ii=0; ii<hitList.data.length; ii++)
				//					System.out.println("{ "+hitList.data[ii][0]+", "+hitList.data[ii][1]+", "+hitList.data[ii][2]+" }");
							} else {
								if (hitList.isEmpty())
									break;
								else {
									hitList.Union(thisList);
				//				System.out.println("print hit list!!! For: "+iG+" : "+iSubG);
				//				System.out.println("this: ");
				//				for (int ii=0; ii<thisList.length; ii++)
				//					System.out.print("{ "+thisList[ii][0]+", "+thisList[ii][1]+", "+thisList[ii][2]+" }");
				//				System.out.println();
				//				for (int ii=0; ii<hitList.data.length; ii++)
				//					System.out.println("{ "+hitList.data[ii][0]+", "+hitList.data[ii][1]+", "+hitList.data[ii][2]+" }");

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
				//				System.out.println("print hit list!!! For: "+iG+" : "+iSubG);
				//				for (int ii=0; ii<hitList.data.length; ii++)
				//					System.out.println("{ "+hitList.data[ii][0]+", "+hitList.data[ii][1]+", "+hitList.data[ii][2]+" }");
							} else {
								if (hitList.isEmpty())
									break;
								else {
									hitList.Union(thisList);
				//				System.out.println("print hit list!!! For: "+iG+" : "+iSubG);
				//				System.out.println("this: ");
				//				for (int ii=0; ii<thisList.length; ii++)
				//					System.out.print("{ "+thisList[ii][0]+", "+thisList[ii][1]+", "+thisList[ii][2]+" }");
				//				System.out.println();
				//				for (int ii=0; ii<hitList.data.length; ii++)
				//					System.out.println("{ "+hitList.data[ii][0]+", "+hitList.data[ii][1]+", "+hitList.data[ii][2]+" }");
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
				//				System.out.println("print hit list!!! For: "+iG+" : "+iSubG);
				//				for (int ii=0; ii<hitList.data.length; ii++)
				//					System.out.println("{ "+hitList.data[ii][0]+", "+hitList.data[ii][1]+", "+hitList.data[ii][2]+" }");
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
		//						System.out.println("print hit list!!! For: "+iG+" : "+iSubG);
		//						for (int ii=0; ii<hitList.data.length; ii++)
		//							System.out.println("{ "+hitList.data[ii][0]+", "+hitList.data[ii][1]+", "+hitList.data[ii][2]+" }");
					} else {
						if (hitList.isEmpty())
							break;
						else {
							hitList.Union(thisList);
		//						System.out.println("print hit list!!! For: "+iG+" : "+iSubG);
		//						System.out.println("this: ");
		//						for (int ii=0; ii<thisList.length; ii++)
		//							System.out.print("{ "+thisList[ii][0]+", "+thisList[ii][1]+", "+thisList[ii][2]+" }");
		//						System.out.println();
		//						for (int ii=0; ii<hitList.data.length; ii++)
		//							System.out.println("{ "+hitList.data[ii][0]+", "+hitList.data[ii][1]+", "+hitList.data[ii][2]+" }");
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
		//		System.out.println("---hit first---for "+iG);
		//		System.out.println("--"+t);
		//		System.out.println("--"+hitFirestT[iG][1]);
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

				//	System.out.println(a+"\t"+b+"\t"+c+"\t"+d+"\t"+e+"\t"+f+"\t"+g+"\t"+h+"\t"+i+"\t"+j);
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
				////	System.out.println(iG+":\t"+Argb[0]+"\t"+Argb[1]+"\t"+Argb[2]);
				////	System.out.println(iG+":\t"+Drgb[0]+"\t"+Drgb[1]+"\t"+Drgb[2]);
				////	System.out.println(iG+":\t"+Srgb[0]+"\t"+Srgb[1]+"\t"+Srgb[2]);
				////	System.out.println(iG+":\t"+mc[0]+"\t"+mc[1]+"\t"+mc[2]);
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
		//			System.out.println(iG+"S:\t"+S[0]+"\t"+S[1]+"\t"+S[2]);
		//			System.out.println(iG+"v:\t"+v[0]+"\t"+v[1]+"\t"+v[2]);
		//			System.out.println(iG+"w:\t"+w[0]+"\t"+w[1]+"\t"+w[2]);
		//			System.out.println(iG+"N:\t"+N[0]+"\t"+N[1]+"\t"+N[2]);
		//			System.out.println(iG+"R:\t"+R[0]+"\t"+R[1]+"\t"+R[2]);
		//			System.out.println(iG+"newV:\t"+newV[0]+"\t"+newV[1]+"\t"+newV[2]);
		//			System.out.println(iG+"newW:\t"+newW[0]+"\t"+newW[1]+"\t"+newW[2]);

					reflection = getRayColor(newV, newW);
					colorReturn[0] = phong[0]*(1.0-mc[0]) + reflection[0]*mc[0];
					colorReturn[1] = phong[1]*(1.0-mc[1]) + reflection[1]*mc[1];
					colorReturn[2] = phong[2]*(1.0-mc[2]) + reflection[2]*mc[2]; 
			/*		colorReturn[0] = phong[0];
					colorReturn[1] = phong[1];
					colorReturn[2] = phong[2]; */
		//			System.out.println(iG+"Return:\t"+colorReturn[0]+"\t"+colorReturn[1]+"\t"+colorReturn[2]);
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
