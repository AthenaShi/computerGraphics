import java.util.*;

public class TestM extends MISApplet {
	Matrix m = new Matrix();
	double invertM[][] = new double[4][4];
	double transM[][] = new double[4][4];
	Geometry geo = new Geometry();
	Geometry dst = new Geometry();

	public void initFrame(double time) { // INITIALIZE ONE ANIMATION FRAME
		geo.Sphere();

		m.identity();
		m.translate(2,6,1);
		m.scale(3,1,9);
		System.out.println("\nOutput Matrix");
		m.printMatrix(m);
		System.out.println("\nOutput Inverted Matrix");
		m.invert(m.data, invertM);
		m.printMatrix(invertM);
		System.out.println("\nOutput Transposed Matrix");
		m.transpose(m.data, transM);
		m.printMatrix(transM);
		System.out.println("\nOutput Inverted Transposed Matrix");
		m.transpose(invertM, transM);
		m.printMatrix(transM);

		m.transform(geo,dst);
		System.out.println(dst.a[0]);
		System.out.println(dst.b[0]);
		System.out.println(dst.c[0]);
		System.out.println(dst.d[0]);
		System.out.println(dst.e[0]);
		System.out.println(dst.f[0]);
		System.out.println(dst.g[0]);
		System.out.println(dst.h[0]);
		System.out.println(dst.i[0]);
		System.out.println(dst.j[0]);
	}
	public void setPixel(int x, int y, int rgb[]) { // SET ONE PIXEL'S COLOR
	}
}
