import java.util.*;

public class Geometry {
/*	double a = 0, 
	       b = 0,
	       c = 0,
	       d = 0,
	       e = 0,
	       f = 0,
	       g = 0,
	       h = 0,
	       i = 0,
	       j = 0; */
	Vector<Double> a = new Vector<Double>();
	Vector<Double> b = new Vector<Double>();
	Vector<Double> c = new Vector<Double>();
	Vector<Double> d = new Vector<Double>();
	Vector<Double> e = new Vector<Double>();
	Vector<Double> f = new Vector<Double>();
	Vector<Double> g = new Vector<Double>();
	Vector<Double> h = new Vector<Double>();
	Vector<Double> i = new Vector<Double>();
	Vector<Double> j = new Vector<Double>();

	public void Sphere() {
		a.add(new Double(1));
		b.add(new Double(1));
		c.add(new Double(1));
		d.add(new Double(0));
		e.add(new Double(0));
		f.add(new Double(0));
		g.add(new Double(0));
		h.add(new Double(0));
		i.add(new Double(0));
		j.add(new Double(-1));
	} 

	public void Cylinder() {
		a.add(new Double(1));
		b.add(new Double(0));
		c.add(new Double(1));
		d.add(new Double(0));
		e.add(new Double(0));
		f.add(new Double(0));
		g.add(new Double(0));
		h.add(new Double(0));
		i.add(new Double(0));
		j.add(new Double(-1));
	}

	public void Plane(double x, double y, double z, double t) {
		a.add(new Double(0));
		b.add(new Double(0));
		c.add(new Double(0));
		d.add(new Double(0));
		e.add(new Double(0));
		f.add(new Double(0));
		g.add(new Double(x));
		h.add(new Double(y));
		i.add(new Double(z));
		j.add(new Double(t));
	}

	public void Plane() {
		a.add(new Double(0));
		b.add(new Double(0));
		c.add(new Double(0));
		d.add(new Double(0));
		e.add(new Double(0));
		f.add(new Double(0));
		g.add(new Double(-1));
		h.add(new Double(0));
		i.add(new Double(0));
		j.add(new Double(-1));
	}

	public Geometry clone() {
		Geometry temp = new Geometry();
		for (int i=0; i<this.a.size(); i++) {
			temp.a.add(this.a.get(i));
			temp.b.add(this.b.get(i));
			temp.c.add(this.c.get(i));
			temp.d.add(this.d.get(i));
			temp.e.add(this.e.get(i));
			temp.f.add(this.f.get(i));
			temp.g.add(this.g.get(i));
			temp.h.add(this.h.get(i));
			temp.i.add(this.i.get(i));
			temp.j.add(this.j.get(i));
		}
		return temp;
	}

}
