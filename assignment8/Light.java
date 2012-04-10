public class Light {
	double Lxyz[] = {10, 10, 10};	// light direction
	//double Irgb[] = {1, 1, 1};	// illuminance of the light	0-1
	double Irgb[] = {0.6, 0.6, 0.6};	// illuminance of the light	0-1

	public Light() {
		this.Lxyz = Lxyz;
		this.Irgb = Irgb;
	}
	
	public void setIrgb(double r, double g, double b) {
		Irgb[0] = r;
		Irgb[1] = g;	
		Irgb[2] = b;
	}	
	public void setPosition(double x, double y, double z) {
		Lxyz[0] = x;	
		Lxyz[1] = y;	
		Lxyz[2] = z;
	}	
}
