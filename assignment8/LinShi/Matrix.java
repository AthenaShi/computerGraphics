
public class Matrix {
	
	double data[][] = new double[4][4];
	double multiplyTemp[][] = new double[4][4];
	double invertM[][] = new double[4][4];
	double transM[][] = new double[4][4];
	double temp[][] = new double[4][4];

	// basic operation
	public void set(int i, int j, double value) {	// Set one value
		data[i][j] = value; 
	}
	public double get(int i, int j) {		// Get one value
		return data[i][j]; 
	}
	public void copy(Matrix src) {			// copy Matrix
		for (int row = 0 ; row < 4 ; row++)
			for (int col = 0 ; col < 4 ; col++)
				data[row][col] = src.get(row,col);
	}

	public void createIdentityData(double dst[][]) {
		for (int row = 0 ; row < 4 ; row++) {
			for (int col = 0 ; col < 4 ; col++) {
				if (row == col)
					dst[row][col] = 1;
				else
					dst[row][col] = 0;
			}
		}
	}

	public void createHermiteData(double dst[][]) {
		dst[0][0] =  2; dst[0][1] = -2; dst[0][2] =  1; dst[0][3] =  1; 
		dst[1][0] = -3; dst[1][1] =  3; dst[1][2] = -2; dst[1][3] = -1; 
		dst[2][0] =  0; dst[2][1] =  0; dst[2][2] =  1; dst[2][3] =  0; 
		dst[3][0] =  1; dst[3][1] =  0; dst[3][2] =  0; dst[3][3] =  0; 
	}

	public void createTranslationData(double a, double b, double c, double dst[][]) {
		createIdentityData(dst);
		dst[0][3] = a;
		dst[1][3] = b;
		dst[2][3] = c;
	}
	public void createXRotationData(double theta, double dst[][]) {
		createIdentityData(dst);
		dst[1][1] = Math.cos(theta);
		dst[1][2] = -Math.sin(theta);
		dst[2][1] = Math.sin(theta);
		dst[2][2] = Math.cos(theta);
	}
	public void createYRotationData(double theta, double dst[][]) {
		createIdentityData(dst);
		dst[0][0] = Math.cos(theta);
		dst[0][2] = Math.sin(theta);
		dst[2][0] = -Math.sin(theta);
		dst[2][2] = Math.cos(theta);
	}
	public void createZRotationData(double theta, double dst[][]) {
		createIdentityData(dst);
		dst[0][0] = Math.cos(theta);
		dst[0][1] = -Math.sin(theta);
		dst[1][0] = Math.sin(theta);
		dst[1][1] = Math.cos(theta);
	}
	public void createScaleData(double x, double y, double z, double dst[][]) {
		createIdentityData(dst);
		dst[0][0] = x;
		dst[1][1] = y;
		dst[2][2] = z;
	}
	public void createPerspectData(double f, double dst[][]) {
		createIdentityData(dst);
		dst[3][2] = -1.0/f;
	}

	public void multiply(Matrix src) {
		multiply(src.data);
	}

	public void multiply(double src[][]) {
		// FIRST COPY MY ORIGINAL DATA TO A TEMPORARY LOCATION
		for (int row = 0 ; row < 4 ; row++)
			for (int col = 0 ; col < 4 ; col++)
				multiplyTemp[row][col] = data[row][col];
		// USE TEMP TO DO THE MATRIX MULTIPLICATION
		for (int row = 0 ; row < 4 ; row++) {
			for (int col = 0 ; col < 4 ; col++) {
				data[row][col] = 0;
				for (int i = 0 ; i < 4 ; i++)
					data[row][col] += multiplyTemp[row][i] * src[i][col];
			}
		}
	}

	public void multiply(double left[][], double right[][], double result[][]) {
		// FIRST COPY MY ORIGINAL DATA TO A TEMPORARY LOCATION
		// USE TEMP TO DO THE MATRIX MULTIPLICATION
		for (int row = 0 ; row < 4 ; row++) {
			for (int col = 0 ; col < 4 ; col++) {
				result[row][col] = 0;
				for (int i = 0 ; i < 4 ; i++)
					result[row][col] += left[row][i] * right[i][col];
			}
		}
	}

	public void identity() {
		createIdentityData(data);
	}
	
	public void hermiteMatrix() {
		createHermiteData(data);
	}

	public void translate(double a, double b, double c) {
		createTranslationData(a,b,c,temp);
		multiply(temp);
	}
	public void rotateX(double theta) {
		createXRotationData(theta, temp);
		multiply(temp);
	}
	public void rotateY(double theta) {
		createYRotationData(theta, temp);
		multiply(temp);
	}
	public void rotateZ(double theta) {
		createZRotationData(theta, temp);
		multiply(temp);
	}
	public void scale(double x, double y, double z) {
		createScaleData(x, y, z, temp);
		//printMatrix(temp);
		multiply(temp);
	}
	public void perspect(double f) {
		createPerspectData(f, temp); 
		multiply(temp);
	}

	public static void invert(double src[][], double dst[][]) {
		for (int i = 0 ; i < 3 ; i++)
		for (int j = 0 ; j < 3 ; j++) {
			int iu = (i + 1) % 3, iv = (i + 2) % 3;
			int ju = (j + 1) % 3, jv = (j + 2) % 3;
			dst[j][i] = src[iu][ju] * src[iv][jv] - src[iu][jv] * src[iv][ju];
		}
		double det = src[0][0]*dst[0][0] + src[1][0]*dst[0][1] + src[2][0]*dst[0][2];
		for (int i = 0 ; i < 3 ; i++)
		for (int j = 0 ; j < 3 ; j++)
			dst[i][j] /= det;
		for (int i = 0 ; i < 3 ; i++)
			dst[i][3] = -dst[i][0]*src[0][3] - dst[i][1]*src[1][3] - dst[i][2]*src[2][3];
		for (int i = 0 ; i < 4 ; i++)
			dst[3][i] = i < 3 ? 0 : 1;
	}

	public static void transpose(double src[][], double dst[][]) {
		for (int i = 0 ; i < 4 ; i++)
		for (int j = 0 ; j < 4 ; j++)
			dst[i][j] = src[j][i];
	}


	public void transform(double src[], double dst[]) {
		for (int row = 0 ; row < dst.length ; row++) {
			dst[row] = 0;
			for (int i = 0 ; i < src.length ; i++)
				dst[row] += data[row][i] * src[i];
		}
	}

	public void transform(Geometry Geo) {
		int iS = Geo.getSubNumber()-1;
		temp[0][0]=Geo.a[iS]; temp[0][1]=Geo.f[iS]; temp[0][2]=Geo.e[iS]; temp[0][3]=Geo.g[iS];
		temp[1][0]=0	    ; temp[1][1]=Geo.b[iS]; temp[1][2]=Geo.d[iS]; temp[1][3]=Geo.h[iS];
		temp[2][0]=0        ; temp[2][1]=0        ; temp[2][2]=Geo.c[iS]; temp[2][3]=Geo.i[iS];
		temp[3][0]=0        ; temp[3][1]=0        ; temp[3][2]=0        ; temp[3][3]=Geo.j[iS];

		invert(data, invertM);
		transpose(invertM, transM);
		multiply(temp, invertM, multiplyTemp);
		multiply(transM, multiplyTemp, temp);
		for (int row=0; row<4; row++) {
			for (int col=0; col<4; col++) {
				if (row < col) {	// if upper right
					temp[row][col] += temp[col][row];
				}
			}
		}
		Geo.a[iS] = temp[0][0];
		Geo.b[iS] = temp[1][1];
		Geo.c[iS] = temp[2][2];
		Geo.d[iS] = temp[1][2];
		Geo.e[iS] = temp[0][2];
		Geo.f[iS] = temp[0][1];
		Geo.g[iS] = temp[0][3];
		Geo.h[iS] = temp[1][3];
		Geo.i[iS] = temp[2][3];
		Geo.j[iS] = temp[3][3];
	}


	public void transform(Geometry srcGeo, Geometry dstGeo) {
		for (int iS = 0 ; iS < srcGeo.getSubNumber() ; iS++) {
			temp[0][0]=srcGeo.a[iS]; temp[0][1]=srcGeo.f[iS]; temp[0][2]=srcGeo.e[iS]; temp[0][3]=srcGeo.g[iS];
			temp[1][0]=0	       ; temp[1][1]=srcGeo.b[iS]; temp[1][2]=srcGeo.d[iS]; temp[1][3]=srcGeo.h[iS];
			temp[2][0]=0           ; temp[2][1]=0           ; temp[2][2]=srcGeo.c[iS]; temp[2][3]=srcGeo.i[iS];
			temp[3][0]=0           ; temp[3][1]=0           ; temp[3][2]=0           ; temp[3][3]=srcGeo.j[iS];

			invert(data, invertM);
			transpose(invertM, transM);
			multiply(temp, invertM, multiplyTemp);
			multiply(transM, multiplyTemp, temp);
			for (int row=0; row<4; row++) {
				for (int col=0; col<4; col++) {
					if (row < col) {	// if upper right
						temp[row][col] += temp[col][row];
		//			} else if (row > col) {	// if lower left
		//				temp[row][col] = 0;
					}
				}
			}
			dstGeo.a[iS] = temp[0][0];
			dstGeo.b[iS] = temp[1][1];
			dstGeo.c[iS] = temp[2][2];
			dstGeo.d[iS] = temp[1][2];
			dstGeo.e[iS] = temp[0][2];
			dstGeo.f[iS] = temp[0][1];
			dstGeo.g[iS] = temp[0][3];
			dstGeo.h[iS] = temp[1][3];
			dstGeo.i[iS] = temp[2][3];
			dstGeo.j[iS] = temp[3][3];
		}
	}

	public void printMatrix(Matrix src) {
		printMatrix(src.data);
	}

	public void printMatrix(double v[]) {
		for (int i = 0; i < v.length; i++)
			System.out.print(v[i]+"\t");
		System.out.println();
	}
	public void printMatrix(double m[][]) {
		for (int i = 0; i<m.length; i++) {
			for (int j = 0; j<m[i].length; j++) {
				System.out.print(m[i][j]+"\t");
			}
			System.out.println();
		}
	}
	public void printMatrix(int m[][]) {
		for (int i = 0; i<m.length; i++) {
			for (int j = 0; j<m[i].length; j++) {
				System.out.print(m[i][j]+"\t");
			}
			System.out.println();
		}
	}
}
