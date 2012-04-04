public class Material {
	double Argb[] = {0, 0, 0};	// ambient color	0-255
	double Drgb[] = {0, 0, 0};	// diffuse color
	double Srgb[] = {0, 0, 0};	// specular color
	double p = 1;			// specular power
	double mCrgb[] = {0.03, 0.03, 0.03};	// mirror color
	//double mCrgb[] = {1, 1, 1};	// mirror color

	public Material() {
		this.Argb = Argb;
		this.Drgb = Drgb;
		this.Srgb = Srgb;
		this.p = p;
		this.mCrgb = mCrgb;
	} 
}
