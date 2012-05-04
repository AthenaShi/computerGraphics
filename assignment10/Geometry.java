public class Geometry {
	double vertices[][];
	int faces[][];
	double normals[][];
	double colors[][];

	public Geometry clone() {
		Geometry temp = new Geometry();
		int vertN = this.vertices.length;
		int faceN = this.faces.length;
		temp.vertices	= new double[vertN][4];
		temp.normals	= new double[vertN][3];
		temp.faces	= new int[faceN][4];
		temp.colors	= new double[vertN][3];
		for (int i=0; i<vertN; i++) {
			temp.vertices[i][0]	= this.vertices[i][0];
			temp.vertices[i][1]	= this.vertices[i][1];
			temp.vertices[i][2]	= this.vertices[i][2];
			temp.vertices[i][3]	= this.vertices[i][3];
			temp.normals[i][0]	= this.normals[i][0];
			temp.normals[i][1]	= this.normals[i][1];
			temp.normals[i][2]	= this.normals[i][2];
		}
		for (int i=0; i<faceN; i++) {
			temp.faces[i][0]	= this.faces[i][0];
			temp.faces[i][1]	= this.faces[i][1];
			temp.faces[i][2]	= this.faces[i][2];
			temp.faces[i][3]	= this.faces[i][3];
		}
		return temp;
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
				{-1,-1,-1, 1},{ 1,-1,-1, 1},{ 1,-1, 1, 1},{-1,-1, 1, 1},	// low face
				{-1,-1,-1, 1},{-1, 1,-1, 1},{ 1, 1,-1, 1},{ 1,-1,-1, 1},	// back face
				{-1,-1,-1, 1},{-1,-1, 1, 1},{-1, 1, 1, 1},{-1, 1,-1, 1},	// left face
				{ 1, 1, 1, 1},{ 1, 1,-1, 1},{-1, 1,-1, 1},{-1, 1, 1, 1},	// up face
				{ 1, 1, 1, 1},{-1, 1, 1, 1},{-1,-1, 1, 1},{ 1,-1, 1, 1},	// front face
				{ 1, 1, 1, 1},{ 1,-1, 1, 1},{ 1,-1,-1, 1},{ 1, 1,-1, 1},	// right face
		};
		normals = new double[][] {	// all vertices in one face are counterclockwise
				{ 0,-1, 0},{ 0,-1, 0},{ 0,-1, 0},{ 0,-1, 0},	// low face
				{ 0, 0,-1},{ 0, 0,-1},{ 0, 0,-1},{ 0, 0,-1},	// back face
				{-1, 0, 0},{-1, 0, 0},{-1, 0, 0},{-1, 0, 0},	// left face
				{ 0, 1, 0},{ 0, 1, 0},{ 0, 1, 0},{ 0, 1, 0},	// up face
				{ 0, 0, 1},{ 0, 0, 1},{ 0, 0, 1},{ 0, 0, 1},	// front face
				{ 1, 0, 0},{ 1, 0, 0},{ 1, 0, 0},{ 1, 0, 0},	// right face
		};
		faces = new int[][] { {0,1,2,3},{4,5,6,7},{8,9,10,11},{12,13,14,15},{16,17,18,19},{20,21,22,23} };
		colors = new double[24][3];
	}	// by default it just give a unit cube

	public void globe (int m) {
		this.globe (m, m);
	}
	public void globe(int m, int n) {
		vertices = new double[(m+1)*(n+1)][4];
		normals  = new double[(m+1)*(n+1)][3];
		colors   = new double[(m+1)*(n+1)][3];
		faces = new int[m*n][4];
		for (int i=0; i<m+1; i++) {
			for (int j=0; j<n+1; j++) {
				double theta = 2.0*Math.PI*i/m;
				double phy = -Math.PI/2.0 + Math.PI*j/n;
				vertices[i+(m+1)*j][0] = Math.cos(theta)*Math.cos(phy);
				normals[i+(m+1)*j][0]  = Math.cos(theta)*Math.cos(phy);
				vertices[i+(m+1)*j][1] = Math.sin(theta)*Math.cos(phy);
				normals[i+(m+1)*j][1]  = Math.sin(theta)*Math.cos(phy);
				vertices[i+(m+1)*j][2] = Math.sin(phy);
				normals[i+(m+1)*j][2]  = Math.sin(phy);
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

	public void cylinder (int n) {
		this.cylinder (n, n, 1);
	}
	public void cylinder(int n, int z, int r) {
		vertices = new double[(n+1)*(z+1)+2*(n+1)*(r+1)][4];
		normals  = new double[(n+1)*(z+1)+2*(n+1)*(r+1)][3];
		colors   = new double[(n+1)*(z+1)+2*(n+1)*(r+1)][3];
		faces = new int[n*z+2*n*r][4];
		// tubular side
		for (int i=0; i<n+1; i++) {
			for (int j=0; j<z+1; j++) {
				double theta = 2.0*Math.PI*i/n;
				//double zco = -1.0 + 2.0*(double)j/(double)z;
				double zco = -1.0 + 2.0*j/z;
				vertices[i+(n+1)*j][0] = Math.cos(theta);
				normals[i+(n+1)*j][0]  = Math.cos(theta);
				vertices[i+(n+1)*j][1] = Math.sin(theta);
				normals[i+(n+1)*j][1]  = Math.sin(theta);
				vertices[i+(n+1)*j][2] = zco;
				normals[i+(n+1)*j][2]  = zco;
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
				normals[i+(n+1)*j+(n+1)*(z+1)][0]  = 0;
				vertices[i+(n+1)*j+(n+1)*(z+1)][1] = rco*Math.sin(theta);
				normals[i+(n+1)*j+(n+1)*(z+1)][1]  = 0;
				vertices[i+(n+1)*j+(n+1)*(z+1)][2] = 1.0;
				normals[i+(n+1)*j+(n+1)*(z+1)][2]  = 1.0;
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
				normals[i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1)][0]  = 0;
				vertices[i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1)][1] = rco*Math.sin(-theta);
				normals[i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1)][1]  = 0;
				vertices[i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1)][2] = -1.0;
				normals[i+(n+1)*j+(n+1)*(z+1)+(n+1)*(r+1)][2]  = -1.0;
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
