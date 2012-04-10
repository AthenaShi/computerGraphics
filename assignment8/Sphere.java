public class Sphere {
	double r = 2;
	double c[] = {0, 0, 0};

	public Sphere() {
		this.r = r;
		this.c = c;
	} 

	public Sphere(double cX, double cY, double cZ, double radius) {
		this.r = radius;
		this.c[0] = cX;
		this.c[1] = cY;
		this.c[2] = cZ;
	}

	public Sphere(double center[], double radius) {
		this.r = radius;
		this.c = center;
	}

	public Sphere(double radius) {
		this.r = radius;
		this.c = c;
	}

	public Sphere(double center[]) {
		this.r = r;
		this.c = center;
	}
}
