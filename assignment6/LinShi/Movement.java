import java.util.ArrayList;

public class Movement {
	double move[][];	//	move = { {t0,y0}, {t1,y1}, ..., {tn, yn} }
	Matrix HermiteMatrix = new Matrix();
	double fourIn[][];	//	four[i] = { p1, p4, r1, r4 }
	double fourOut[][];	//	four[i] = { a, b, c, d }
	double slope_at_time[];
	int innerI;
	double t;

	public Movement(double move[][]) {
		this.move = move;
		HermiteMatrix.hermiteMatrix();
		// calculate p1, p4, r1, r4
		innerI = move.length-1;
		fourIn = new double[innerI][4];
		fourOut = new double[innerI][4];
		slope_at_time = new double[innerI+1];
		slope_at_time[innerI] = 2.0 * (move[innerI][1]-move[innerI-1][1]) / (move[innerI][0]-move[innerI-1][0]);
			
		for (int i=0; i<innerI; i++) {
			fourIn[i][0] = move[i][1];
			fourIn[i][1] = move[i+1][1];
			if (i == 0) {
				slope_at_time[i] = 2.0 * (move[i+1][1]-move[i][1]) / (move[i+1][0]-move[i][0]);
			} else {
				slope_at_time[i] = (move[i+1][1]-move[i-1][1]) / (move[i+1][0]-move[i-1][0]);
			}
			fourIn[i][2] = slope_at_time[i] / (move[i+1][0]-move[i][0]);
			fourIn[i][3] = slope_at_time[i+1] / (move[i+1][0]-move[i][0]);
			HermiteMatrix.transform(fourIn[i],fourOut[i]);
		}
	} 

	public double getMove(double time) {
		double thisMove = 0;
		for (int i=0; i<innerI; i++) {
			if (time <= move[i+1][0] && time > move[i][0]) {
				t = (time-move[i][0]) / (move[i+1][0]-move[i][0]);
			//	System.out.println("t is: "+t);
				thisMove = fourOut[i][0]*Math.pow(t,3) + fourOut[i][1]*Math.pow(t,2) + fourOut[i][2]*t + fourOut[i][3];
				break;
			}
		}
		return thisMove;
	}
}
