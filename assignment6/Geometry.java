import java.util.ArrayList;

public class Geometry {
	double vertices[][];
	int faces[][];
	int draw[][];
	int what = 0;
	ArrayList<Geometry> children = new ArrayList<Geometry>();
	Matrix globalMatrix = new Matrix();
	Matrix relativeMatrix = new Matrix();

	public Geometry() {
		globalMatrix.identity();
		relativeMatrix.identity();
	} 

/*	public void asWorld() {
		globalMatrix.identity();
		relativeMatrix.identity();
	} */

	public Matrix getMatrix() {
		return relativeMatrix;
	}

	public Geometry add() {
		Geometry Child = new Geometry();
		children.add(Child);
		return Child;
	}

	public void remove(Geometry Child) {
		children.remove(Child);
	}

	public int getNumChildren() {
		return children.size();
	}

	public Geometry getChild(int index) {
		return children.get(index);
	}	

	public void copy(double vertice[][], int face[][]) {			// copy Matrix
		for (int row = 0; row < vertices.length ; row++)
			for (int col = 0 ; col < vertices[row].length ; col++)
				vertice[row][col] = vertices[row][col];
		for (int row = 0; row < faces.length ; row++)
			for (int col = 0 ; col < faces[row].length ; col++)
				face[row][col] = faces[row][col];
	}

	public void cube() {
		vertices = new double[][] {	// all vertices in one face are counterclockwise
				{-1,-1,-1,1},{1,-1,-1,1},{1,-1,1,1},{-1,-1,1,1},	// low face
				{-1,-1,-1,1},{-1,1,-1,1},{1,1,-1,1},{1,-1,-1,1},	// back face
				{-1,-1,-1,1},{-1,-1,1,1},{-1,1,1,1},{-1,1,-1,1},	// left face
				{1,1,1,1},{1,1,-1,1},{-1,1,-1,1},{-1,1,1,1},	// up face
				{1,1,1,1},{-1,1,1,1},{-1,-1,1,1},{1,-1,1,1},	// front face
				{1,1,1,1},{1,-1,1,1},{1,-1,-1,1},{1,1,-1,1},	// right face
		};
		faces = new int[][] { {0,1,2,3},{4,5,6,7},{8,9,10,11},{12,13,14,15},{16,17,18,19},{20,21,22,23} };
		draw = new int[24][4];
	}	// by default it just give a unit cube

	public void globe (int m) {
		this.globe (m, m);
	}
	public void globe(int m, int n) {
		vertices = new double[(m+1)*(n+1)][4];
		faces = new int[m*n][4];
		draw = new int[(m+1)*(n+1)][4];
		for (int i=0; i<m+1; i++) {
			for (int j=0; j<n+1; j++) {
				double theta = 2.0*Math.PI*i/m;
				double phy = -Math.PI/2.0 + Math.PI*j/n;
				vertices[i+(m+1)*j][0] = Math.cos(theta)*Math.cos(phy);
				vertices[i+(m+1)*j][1] = Math.sin(theta)*Math.cos(phy);
				vertices[i+(m+1)*j][2] = Math.sin(phy);
				vertices[i+(m+1)*j][3] = 1;
				if (i<m && j<n) {
					faces[i+m*j][0] = i+(m+1)*j;
					faces[i+m*j][1] = i+(m+1)*j+1;
					faces[i+m*j][2] = i+(m+1)*(j+1)+1;
					faces[i+m*j][3] = i+(m+1)*(j+1);
				}
			}
		}
	}

	public void donut(int m, int n, double b) {
		vertices = new double[(m+1)*(n+1)][4];
		faces = new int[m*n][4];
		for (int i=0; i<m+1; i++) {
			for (int j=0; j<n+1; j++) {
				double theta = 2.0*Math.PI*i/m;
				double phy = 2.0*Math.PI*j/n;
				vertices[i+(m+1)*j][0] = (1.0+b*Math.cos(phy))*Math.cos(theta);
				vertices[i+(m+1)*j][1] = (1.0+b*Math.cos(phy))*Math.cos(theta);
				vertices[i+(m+1)*j][2] = b*Math.sin(phy);
				vertices[i+(m+1)*j][3] = 1;
				if (i<m && j<n) {
					faces[i+m*j][0] = i+(m+1)*j;
					faces[i+m*j][1] = i+(m+1)*j+1;
					faces[i+m*j][2] = i+(m+1)*(j+1)+1;
					faces[i+m*j][3] = i+(m+1)*(j+1);
					System.out.println((i+m*j)+": {" + faces[i+m*j][0]+","+faces[i+m*j][1]+","+faces[i+m*j][2]+","+faces[i+m*j][3]+"}");
				}
			}
		}
	}

	public void cylinder (int n) {
		this.cylinder (n, n, 1);
	}
	public void cylinder(int n, int z, int r) {
		vertices = new double[(n+1)*(z+1)+2*(n+1)*(r+1)][4];
		faces = new int[n*z+2*n*r][4];
		draw = new int[(n+1)*(z+1)+2*(n+1)*(r+1)][4];
		// tubular side
		for (int i=0; i<n+1; i++) {
			for (int j=0; j<z+1; j++) {
				double theta = 2.0*Math.PI*i/n;
				//double zco = -1.0 + 2.0*(double)j/(double)z;
				double zco = -1.0 + 2.0*j/z;
				vertices[i+(n+1)*j][0] = Math.cos(theta);
				vertices[i+(n+1)*j][1] = Math.sin(theta);
				vertices[i+(n+1)*j][2] = zco;
				vertices[i+(n+1)*j][3] = 1;
				if (i<n && j<z) {
					faces[i+n*j][0] = i+(n+1)*j;
					faces[i+n*j][1] = i+(n+1)*j+1;
					faces[i+n*j][2] = i+(n+1)*(j+1)+1;
					faces[i+n*j][3] = i+(n+1)*(j+1);
				//	System.out.println((i+n*j)+": {" + faces[i+n*j][0]+","+faces[i+n*j][1]+","+faces[i+n*j][2]+","+faces[i+n*j][3]+"}");
				}
			}
		}
		// front cap
		for (int i=0; i<n+1; i++) {
			for (int j=0; j<r+1; j++) {
				double theta = 2.0*Math.PI*i/n;
				double rco = (double)j/r;
				vertices[i+(n+1)*j+(n+1)*(z+1)][0] = rco*Math.cos(theta);
				vertices[i+(n+1)*j+(n+1)*(z+1)][1] = rco*Math.sin(theta);
				vertices[i+(n+1)*j+(n+1)*(z+1)][2] = 1.0;
				vertices[i+(n+1)*j+(n+1)*(z+1)][3] = 1;
				if (i<n && j<r) {
					faces[i+n*j+n*z][0] = i+(n+1)*j+(n+1)*(z+1);
					faces[i+n*j+n*z][1] = i+(n+1)*j+(n+1)*(z+1)+1;
					faces[i+n*j+n*z][2] = i+(n+1)*(j+1)+(n+1)*(z+1)+1;
					faces[i+n*j+n*z][3] = i+(n+1)*(j+1)+(n+1)*(z+1);
				//	System.out.println((i+n*j)+": {" + faces[i+n*j][0]+","+faces[i+n*j][1]+","+faces[i+n*j][2]+","+faces[i+n*j][3]+"}");
				}
			}
		}
		// back cap
		for (int i=0; i<n+1; i++) {
			for (int j=0; j<r+1; j++) {
				double theta = 2.0*Math.PI*i/n;
				double rco = (double)j/r;
				vertices[i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1)][0] = rco*Math.cos(-theta);
				vertices[i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1)][1] = rco*Math.sin(-theta);
				vertices[i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1)][2] = -1.0;
				vertices[i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1)][3] = 1;
				if (i<n && j<r) {
					faces[i+n*j+n*z+n*r][0] = i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1);
					faces[i+n*j+n*z+n*r][1] = i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1)+1;
					faces[i+n*j+n*z+n*r][2] = i+(n+1)*(j+1)+(n+1)*(z+1)+(n+1)*(r+1)+1;
					faces[i+n*j+n*z+n*r][3] = i+(n+1)*(j+1)+(n+1)*(z+1)+(n+1)*(r+1);
				//	System.out.println((i+n*j)+": {" + faces[i+n*j][0]+","+faces[i+n*j][1]+","+faces[i+n*j][2]+","+faces[i+n*j][3]+"}");
				}
			}
		}


	}

}
