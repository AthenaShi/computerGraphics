import java.util.*;

public class Geometry {
	double a[], b[], c[], d[], e[], f[], g[], h[], i[], j[];
	int number;
	int maxN = 10;


	public Geometry() {
		a = new double[maxN];
		b = new double[maxN];
		c = new double[maxN];
		d = new double[maxN];
		e = new double[maxN];
		f = new double[maxN];
		g = new double[maxN];
		h = new double[maxN];
		i = new double[maxN];
		j = new double[maxN];
		number = 0;
	}

	public int getSubNumber() {
		return number;
	}

	public void Sphere() {
		a[number] = 1;
		b[number] = 1;
		c[number] = 1;
		d[number] = 0;
		e[number] = 0;
		f[number] = 0;
		g[number] = 0;
		h[number] = 0;
		i[number] = 0;
		j[number] = -1;
		number++;
	} 

	public void Cylinder() {
		a[number] = 1;
		b[number] = 0;
		c[number] = 1;
		d[number] = 0;
		e[number] = 0;
		f[number] = 0;
		g[number] = 0;
		h[number] = 0;
		i[number] = 0;
		j[number] = -1;
		number++;
	}

	public void Plane(double x, double y, double z, double t) {
		a[number] = 0;
		b[number] = 0;
		c[number] = 0;
		d[number] = 0;
		e[number] = 0;
		f[number] = 0;
		g[number] = x;
		h[number] = y;
		i[number] = z;
		j[number] = t;
		number++;
	}

	public void Plane() {
		a[number] = 0;
		b[number] = 0;
		c[number] = 0;
		d[number] = 0;
		e[number] = 0;
		f[number] = 0;
		g[number] = -1;
		h[number] = 0;
		i[number] = 0;
		j[number] = -1;
		number++;
	}

	public Geometry clone() {
		Geometry temp = new Geometry();
		for (int ii=0; ii<this.number; ii++) {
			temp.a[ii] = this.a[ii];
			temp.b[ii] = this.b[ii];
			temp.c[ii] = this.c[ii];
			temp.d[ii] = this.d[ii];
			temp.e[ii] = this.e[ii];
			temp.f[ii] = this.f[ii];
			temp.g[ii] = this.g[ii];
			temp.h[ii] = this.h[ii];
			temp.i[ii] = this.i[ii];
			temp.j[ii] = this.j[ii];
			temp.number = this.number;
		}
		return temp;
	}

	public void setA(double aa) {
		a[number-1] = aa;
	}

	public void setB(double bb) {
		b[number-1] = bb;
	}

	public void setC(double cc) {
		c[number-1] = cc;
	}

	public void setD(double dd) {
		d[number-1] = dd;
	}

	public void setE(double ee) {
		e[number-1] = ee;
	}

	public void setF(double ff) {
		f[number-1] = ff;
	}

	public void setG(double gg) {
		g[number-1] = gg;
	}

	public void setH(double hh) {
		h[number-1] = hh;
	}

	public void setI(double ii) {
		i[number-1] = ii;
	}

	public void setJ(double jj) {
		j[number-1] = jj;
	}

}
