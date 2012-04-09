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
<<<<<<< HEAD
	}

	public void setArgb(double r, double g, double b) {
		Argb[0] = r;
		Argb[1] = g;
		Argb[2] = b;
	}

	public void setDrgb(double r, double g, double b) {
		Drgb[0] = r;
		Drgb[1] = g;
		Drgb[2] = b;
	}

	public void setMCrgb(double r) {
		mCrgb[0] = r;
		mCrgb[1] = r;
		mCrgb[2] = r;
	}

	public void setMCrgb(double r, double g, double b) {
		mCrgb[0] = r;
		mCrgb[1] = g;
		mCrgb[2] = b;
	}

	public void setSrgb(double r, double g, double b) {
		Srgb[0] = r;
		Srgb[1] = g;
		Srgb[2] = b;
	}

	public void setP(double pp) {
		p = pp;
	}
=======
	} 
>>>>>>> 02e67171e915d78a556f1af21015f49051904748
}
