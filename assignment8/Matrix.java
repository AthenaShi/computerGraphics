
public class Matrix {
	
	double data[][] = new double[4][4];
	double multiplyTemp[][] = new double[4][4];
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


	public void transform(double src[], double dst[]) {
		for (int row = 0 ; row < dst.length ; row++) {
			dst[row] = 0;
			for (int i = 0 ; i < src.length ; i++)
				dst[row] += data[row][i] * src[i];
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
