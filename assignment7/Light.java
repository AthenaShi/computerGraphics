public class Light {
	double Lxyz[] = {10, 10, 10};	// light direction
	//double Irgb[] = {1, 1, 1};	// illuminance of the light	0-1
	double Irgb[] = {0.6, 0.6, 0.6};	// illuminance of the light	0-1

	public Light() {
		this.Lxyz = Lxyz;
		this.Irgb = Irgb;
	} 
}
