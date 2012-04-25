import java.util.*;

public class TriangleTest extends MISApplet {
	double viewX = 0;
	double viewY = 0;
	double viewZ = 10;
	double scale = 100;
	double speed = 1;
	int trapezoid[][] = new int[4][3];
	int trapezoidRGB[][] = new int[4][3];
	int enlarge = (int)Math.pow(2,12);
	double background[] = {0, 0, 0};
	Triangle triangle = new Triangle();
	Triangle draw = new Triangle();
	Matrix m = new Matrix();

	public void initialize() {
		triangle.set1vertex(0, 1, 0);
		triangle.set2vertex(-1.3, -1, 0);
		triangle.set3vertex(1.3, -1, 0);
		triangle.set1rgb(1,0,0);
		triangle.set2rgb(0,1,0);
		triangle.set3rgb(0,0,1);
	}

	public void initFrame(double time) { // add animation here!!!!!!
		m.identity();
//		m.perspect(viewZ);
		m.rotateY(time * speed);
		draw = triangle.clone();
		m.transform(triangle, draw);
		perspective(draw);
		draw.toTrapezoids();
	}

	public void computeImage(double time) {
		initFrame(time);
		for (int i = 0; i<H*W; i++)
			pix[i] = pack((int)(background[0]*255),(int)(background[1]*255),(int)(background[2]*255));

		for (int i=0; i<4; i++) {
			viewport(draw.trapezoidV[i],trapezoid[i]);
			trapezoid[i][2] = (int)(draw.trapezoidV[i][2] * enlarge);
			trapezoidRGB[i][0] = (int)(draw.trapezoidRGB[i][0] * enlarge);
			trapezoidRGB[i][1] = (int)(draw.trapezoidRGB[i][1] * enlarge);
			trapezoidRGB[i][2] = (int)(draw.trapezoidRGB[i][2] * enlarge);
		}

		int n = trapezoid[1][1]-trapezoid[0][1];	// BLy - TLy
	if (n!=0) {
		int dL_dy_x = ( trapezoid[1][0] - trapezoid[0][0]) * enlarge / n;
	        int dL_dy_y = enlarge;
		int dL_dy_z = ( trapezoid[1][2] - trapezoid[0][2]) / n;
		int dL_dy_r = ( trapezoidRGB[1][0] - trapezoidRGB[0][0]) / n;
		int dL_dy_g = ( trapezoidRGB[1][1] - trapezoidRGB[0][1]) / n;
		int dL_dy_b = ( trapezoidRGB[1][2] - trapezoidRGB[0][2]) / n;
		int dR_dy_x = ( trapezoid[2][0] - trapezoid[0][0]) * enlarge / n;
		int dR_dy_y = enlarge;
		int dR_dy_z = ( trapezoid[2][2] - trapezoid[0][2]) / n;
		int dR_dy_r = ( trapezoidRGB[2][0] - trapezoidRGB[0][0]) / n;
		int dR_dy_g = ( trapezoidRGB[2][1] - trapezoidRGB[0][1]) / n;
		int dR_dy_b = ( trapezoidRGB[2][2] - trapezoidRGB[0][2]) / n;

		int L_x = trapezoid[0][0] * enlarge,	L_y = trapezoid[0][1] * enlarge,	L_z = trapezoid[0][2];
		int R_x = trapezoid[0][0] * enlarge,	R_y = trapezoid[0][1] * enlarge,	R_z = trapezoid[0][2];
		int L_r = trapezoidRGB[0][0],	L_g = trapezoidRGB[0][1],	L_b = trapezoidRGB[0][2];
		int R_r = trapezoidRGB[0][0],	R_g = trapezoidRGB[0][1],	R_b = trapezoidRGB[0][2];

		for (int y=trapezoid[0][1]; y<=trapezoid[1][1]; y++) {
			L_x += dL_dy_x;	L_y += dL_dy_y;	L_z += dL_dy_z;
			L_r += dL_dy_r;	L_g += dL_dy_g;	L_b += dL_dy_b;
			R_x += dR_dy_x;	R_y += dR_dy_y;	R_z += dR_dy_z;
			R_r += dR_dy_r;	R_g += dR_dy_g;	R_b += dR_dy_b;

			int m = (R_x - L_x);
			int d_dx_x = (R_x - L_x) * enlarge / m, d_dx_y = (R_y - L_y) * enlarge / m, d_dx_z = (R_z - L_z) * enlarge / m;
			int d_dx_r = (R_r - L_r) * enlarge / m, d_dx_g = (R_g - L_g) * enlarge / m, d_dx_b = (R_b - L_b) * enlarge / m;

			rgb[0] = L_r;
			rgb[1] = L_g;
			rgb[2] = L_b;
	//		pz = L_z;

			for (int x=L_x/enlarge; x<R_x/enlarge; x++) {
				rgb[0] += d_dx_r;
				rgb[1] += d_dx_g;
				rgb[2] += d_dx_b;
	//			pz += d_dx_z;
				pix[y*W+x] = pack(rgb[0]*255/enlarge,rgb[1]*255/enlarge,rgb[2]*255/enlarge);
			}
		}
	}
		n = trapezoid[3][1]-trapezoid[1][1];	// BLy - TLy
	if (n!=0) {
		int dL_dy_x = ( trapezoid[3][0] - trapezoid[1][0]) * enlarge / n;
	       	int dL_dy_y = enlarge;
		int dL_dy_z = ( trapezoid[3][2] - trapezoid[1][2]) / n;
		int dL_dy_r = ( trapezoidRGB[3][0] - trapezoidRGB[1][0]) / n;
		int dL_dy_g = ( trapezoidRGB[3][1] - trapezoidRGB[1][1]) / n;
		int dL_dy_b = ( trapezoidRGB[3][2] - trapezoidRGB[1][2]) / n;
		int dR_dy_x = ( trapezoid[3][0] - trapezoid[2][0]) * enlarge / n;
		int dR_dy_y = enlarge;
		int dR_dy_z = ( trapezoid[3][2] - trapezoid[2][2]) / n;
		int dR_dy_r = ( trapezoidRGB[3][0] - trapezoidRGB[2][0]) / n;
		int dR_dy_g = ( trapezoidRGB[3][1] - trapezoidRGB[2][1]) / n;
		int dR_dy_b = ( trapezoidRGB[3][2] - trapezoidRGB[2][2]) / n;

		int L_x = trapezoid[1][0] * enlarge,	L_y = trapezoid[1][1] * enlarge,	L_z = trapezoid[1][2];
		int R_x = trapezoid[2][0] * enlarge,	R_y = trapezoid[2][1] * enlarge,	R_z = trapezoid[2][2];
		int L_r = trapezoidRGB[1][0],	L_g = trapezoidRGB[1][1],	L_b = trapezoidRGB[1][2];
		int R_r = trapezoidRGB[2][0],	R_g = trapezoidRGB[2][1],	R_b = trapezoidRGB[2][2];

		for (int y=trapezoid[1][1]+1; y<trapezoid[3][1]; y++) {
			L_x += dL_dy_x;	L_y += dL_dy_y;	L_z += dL_dy_z;
			L_r += dL_dy_r;	L_g += dL_dy_g;	L_b += dL_dy_b;
			R_x += dR_dy_x;	R_y += dR_dy_y;	R_z += dR_dy_z;
			R_r += dR_dy_r;	R_g += dR_dy_g;	R_b += dR_dy_b;

			int m = (R_x - L_x);
//			System.out.println(R_x+"\t"+L_x);
			int d_dx_x = (R_x - L_x) * enlarge / m, d_dx_y = (R_y - L_y) * enlarge / m, d_dx_z = (R_z - L_z) * enlarge / m;
			int d_dx_r = (R_r - L_r) * enlarge / m, d_dx_g = (R_g - L_g) * enlarge / m, d_dx_b = (R_b - L_b) * enlarge / m;

			rgb[0] = L_r;
			rgb[1] = L_g;
			rgb[2] = L_b;
	//		pz = L_z;

			for (int x=L_x/enlarge; x<=R_x/enlarge; x++) {
				rgb[0] += d_dx_r;
				rgb[1] += d_dx_g;
				rgb[2] += d_dx_b;
	//			pz += d_dx_z;
				pix[y*W+x] = pack(rgb[0]*255/enlarge,rgb[1]*255/enlarge,rgb[2]*255/enlarge);
			}
		}
	}
	}

	public void viewport(double src[], int dst[]) {
		dst[0] = (int) ( 0.5 * W + src[0] * scale );
		dst[1] = (int) ( 0.5 * H - src[1] * scale );
	}

	public void perspective(Triangle tri) {
		for (int i=0; i<3; i++) {
			tri.vertex[i][0] *= viewZ/(viewZ-tri.vertex[i][2]);
			tri.vertex[i][1] *= viewZ/(viewZ-tri.vertex[i][2]);
			tri.vertex[i][2] = 1.0/(viewZ-tri.vertex[i][2]);
		}
	}
}
