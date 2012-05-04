public class Triangle {
	double vertex[][] = { {0,0,0,0}, {0,0,0,0}, {0,0,0,0} };
	double rgb[][] = { {0,0,0}, {0,0,0}, {0,0,0} };
	double trapezoidV[][] = new double[4][3];
	double trapezoidRGB[][] = new double[4][3];

	public Triangle() {
		vertex[0][0] =-1;
		vertex[0][1] = 0;
		vertex[0][2] = 0;
		vertex[0][3] = 1;
		vertex[1][0] = 1;
		vertex[1][1] = 0;
		vertex[1][2] = 0;
		vertex[1][3] = 1;
		vertex[2][0] = 0;
		vertex[2][1] = 1;
		vertex[2][2] = 0;
		vertex[2][3] = 1;

		rgb[0][0] = 1;	//red
		rgb[0][1] = 0;
		rgb[0][2] = 0;
		rgb[1][0] = 0;	//green
		rgb[1][1] = 1;
		rgb[1][2] = 0;
		rgb[2][0] = 0;	//blue
		rgb[2][1] = 0;
		rgb[2][2] = 1;
	}

	public Triangle clone() {
		Triangle temp = new Triangle();
		for (int i=0; i<3; i++) {
			temp.vertex[i][0] = this.vertex[i][0]; temp.vertex[i][1] = this.vertex[i][1]; 
			temp.vertex[i][2] = this.vertex[i][2]; temp.vertex[i][3] = this.vertex[i][3];
			temp.rgb[i][0] = this.rgb[i][0]; temp.rgb[i][1] = this.rgb[i][1]; temp.rgb[i][2] = this.rgb[i][2];
		}
		return temp;
	}

	public void set1rgb(double r, double g, double b) {
		rgb[0][0] = r;
		rgb[0][1] = g;
		rgb[0][2] = b;
	}

	public void set2rgb(double r, double g, double b) {
		rgb[1][0] = r;
		rgb[1][1] = g;
		rgb[1][2] = b;
	}

	public void set3rgb(double r, double g, double b) {
		rgb[2][0] = r;
		rgb[2][1] = g;
		rgb[2][2] = b;
	}

	public void set1vertex(double x, double y, double z) {
		vertex[0][0] = x;
		vertex[0][1] = y;
		vertex[0][2] = z;
	}

	public void set2vertex(double x, double y, double z) {
		vertex[1][0] = x;
		vertex[1][1] = y;
		vertex[1][2] = z;
	}

	public void set3vertex(double x, double y, double z) {
		vertex[2][0] = x;
		vertex[2][1] = y;
		vertex[2][2] = z;
	}

	public void toTrapezoids() {
		int intTop = vertex[0][1] > vertex[1][1] ? (vertex[0][1] > vertex[2][1] ? 0 : 2) : (vertex[1][1] > vertex[2][1] ? 1 : 2);
		int intBot = vertex[0][1] > vertex[1][1] ? (vertex[1][1] > vertex[2][1] ? 2 : 1) : (vertex[0][1] > vertex[2][1] ? 2 : 0);
		int intMid = vertex[0][1] > vertex[1][1] ? (vertex[0][1] > vertex[2][1] ? (vertex[1][1] > vertex[2][1] ? 1 : 2) : 0) : (vertex[1][1] > vertex[2][1] ? (vertex[0][1] > vertex[2][1] ? 0 : 2) : 1);

		double t = (vertex[intMid][1] - vertex[intTop][1]) / (vertex[intBot][1] - vertex[intTop][1]);

		double midX = lerp(t,vertex[intTop][0], vertex[intBot][0]);
		double midY = vertex[intMid][1];
		double midZ = lerp(t,vertex[intTop][2], vertex[intBot][2]);
		double midR = lerp(t,rgb[intTop][0], rgb[intBot][0]);
		double midG = lerp(t,rgb[intTop][1], rgb[intBot][1]);
		double midB = lerp(t,rgb[intTop][2], rgb[intBot][2]);
		boolean leftTriangle = midX < vertex[intMid][0] ? true : false;

		trapezoidV[0][0] = vertex[intTop][0]; trapezoidV[0][1] = vertex[intTop][1]; trapezoidV[0][2] = vertex[intTop][2];
		trapezoidRGB[0][0] = rgb[intTop][0]; trapezoidRGB[0][1] = rgb[intTop][1]; trapezoidRGB[0][2] = rgb[intTop][2];
		if (leftTriangle) {
			trapezoidV[1][0] = midX; trapezoidV[1][1] = midY; trapezoidV[1][2] = midZ;
			trapezoidRGB[1][0] = midR; trapezoidRGB[1][1] = midG; trapezoidRGB[1][2] = midB;
			trapezoidV[2][0] = vertex[intMid][0]; trapezoidV[2][1] = vertex[intMid][1]; trapezoidV[2][2] = vertex[intMid][2];
			trapezoidRGB[2][0] = rgb[intMid][0]; trapezoidRGB[2][1] = rgb[intMid][1]; trapezoidRGB[2][2] = rgb[intMid][2];
		} else {
			trapezoidV[1][0] = vertex[intMid][0]; trapezoidV[1][1] = vertex[intMid][1]; trapezoidV[1][2] = vertex[intMid][2];
			trapezoidRGB[1][0] = rgb[intMid][0]; trapezoidRGB[1][1] = rgb[intMid][1]; trapezoidRGB[1][2] = rgb[intMid][2];
			trapezoidV[2][0] = midX; trapezoidV[2][1] = midY; trapezoidV[2][2] = midZ;
			trapezoidRGB[2][0] =  midR; trapezoidRGB[2][1] = midG; trapezoidRGB[2][2] = midB;
		}
		trapezoidV[3][0] = vertex[intBot][0]; trapezoidV[3][1] = vertex[intBot][1]; trapezoidV[3][2] = vertex[intBot][2];
		trapezoidRGB[3][0] = rgb[intBot][0]; trapezoidRGB[3][1] = rgb[intBot][1]; trapezoidRGB[3][2] = rgb[intBot][2];
	}

	public double lerp(double t, double A, double B) {
		return (A + t*(B-A));
	}

}
