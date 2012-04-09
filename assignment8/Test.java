import java.util.*;

public class Test extends MISApplet {
//	private int first[][] = new int[6][2];
//	private int second[][] = new int[6][2];
	private int first[][] = new int[2][2];
	private int second[][] = new int[2][2];
	private int temp[][] = new int[first.length+second.length][2];
	private Vector<int[]> third = new Vector<int[]>();

//----- THESE TWO METHODS OVERRIDE METHODS IN THE BASE CLASS

    public void initFrame(double time) { // INITIALIZE ONE ANIMATION FRAME
	    first[0][0] = 200;
	    first[0][1] = 1;
	    first[1][0] = 500;
	    first[1][1] = -1;
/*	    first[2][0] = 450;
	    first[2][1] = -1;
	    first[3][0] = 550;
	    first[3][1] = 1;
	    first[4][0] = 350;
	    first[4][1] = 1;
	    first[5][0] = 500;
	    first[5][1] = -1; */

	    second[0][0] = 300;
	    second[0][1] = -1;
	    second[1][0] = 400;
	    second[1][1] = 1;
/*	    second[2][0] = 300;
	    second[2][1] = 1;
	    second[3][0] = 400;
	    second[3][1] = -1;
	    second[4][0] = 450;
	    second[4][1] = 1;
	    second[5][0] = 600;
	    second[5][1] = -1; */

	int firstI = 0, secondI = 0;
	int thisI = 0;
	boolean firstDone = first.length == 0 ? true : false;
	boolean firstOver = true;
	boolean secondDone = second.length == 0 ? true : false;
	boolean secondOver = true;
	third.clear();

	while (!firstDone || !secondDone) {
		// if this element if in the first List
		if (!firstDone && (first[firstI][0] <= second[secondI][0] || !secondOver)) {
			// if this element is "in" and next element in other List is "out"
			if ( first[firstI][1] == 1 && ( second[secondI][1] == -1 || !secondOver) ) {
				int toAdd[] = {first[firstI][0], 1};
				third.add( toAdd );
			// if this element is "out" and next element in other List is "out"
			} else if ( first[firstI][1] == -1 && ( second[secondI][1] == -1 || !secondOver) ) {
				int toAdd[] = {first[firstI][0], -1};
				third.add( toAdd );
			}
			// if the last element in this List
			if (firstI >= first.length-1) {
				firstDone = true;	// mark this List is done!
				// if this List is over (means the last is "out")
				if (first[firstI][1] == 1) {
					firstOver = false;
				} else {
					firstOver = true;
				}
			} else {
				firstI++;
			}
		} else if (!secondDone && ( first[firstI][0] > second[secondI][0] || !firstOver)) {
			if ( second[secondI][1] == 1  && ( first[firstI][1] == -1 || !firstOver) ) {
				int toAdd[] = {second[secondI][0], 1};
				third.add( toAdd );
			} else if ( second[secondI][1] == -1 && ( first[firstI][1] == -1 || !firstOver) ) {
				int toAdd[] = {second[secondI][0], -1};
				third.add( toAdd );
			}
			if (secondI >= second.length-1) {
				secondDone = true;
				if (second[secondI][1] == 1) {
					secondOver = false;

				} else {
					secondOver = true;
				}
			} else {
				secondI++;
			}
		}
	}
    }

    public void setPixel(int x, int y, int rgb[]) { // SET ONE PIXEL'S COLOR
	    if (y >= 100 && y <= 120) {
		    for (int i=0; i<first.length; i++) {
			    if (i == 0 && (first[i][1] == -1 && x <= first[i][0] || ( i == 0 && first[i][1] == 1 && x >= first[i][0] && x <= first[i+1][0]) ) ) {
					    rgb[0] = 255;
					    rgb[1] = 0;
					    rgb[2] = 0;
				            break;
			    } else if (i == first.length-1 && first[i][1] == 1 && x >= first[i][0] ) {
					       rgb[0] = 255;
					       rgb[1] = 0;
					       rgb[2] = 0;
					       break;
			     } else if (first[i][1] == 1 && x >= first[i][0] && x <= first[i+1][0] ) {
				       rgb[0] = 255;
				       rgb[1] = 0;
				       rgb[2] = 0;
				       break;
			     } 
			     else {
				       rgb[0] = rgb[1] = rgb[2] = 255;
			//	       break;
			     }
		       }
	    }
	    else if (y >= 200 && y <= 220) {
		    for (int i=0; i<second.length; i++) {
			    if (i == 0 && (second[i][1] == -1 && x <= second[i][0] || ( i == 0 && second[i][1] == 1 && x >= second[i][0] && x <= second[i+1][0]) ) ) {
					    rgb[0] = 0;
					    rgb[1] = 0;
					    rgb[2] = 255;
				            break;
			    } else if (i == second.length-1 && second[i][1] == 1 && x >= second[i][0] ) {
					       rgb[0] = 0;
					       rgb[1] = 0;
					       rgb[2] = 255;
					       break;
			     } else if (second[i][1] == 1 && x >= second[i][0] && x <= second[i+1][0] ) {
				       rgb[0] = 0;
				       rgb[1] = 0;
				       rgb[2] = 255;
				       break;
			     } 
			     else {
				       rgb[0] = rgb[1] = rgb[2] = 255;
			//	       break;
			     }
		       }
	    }
	    else if (y >= 300 && y <= 320) {
		    for (int i=0; i<third.size(); i++) {
			    if (i == 0 && (third.elementAt(i)[1] == -1 && x <= third.elementAt(i)[0] || ( i == 0 && third.elementAt(i)[1] == 1 && x >= third.elementAt(i)[0] && x <= third.elementAt(i+1)[0]) ) ) {
					    rgb[0] = 255;
					    rgb[1] = 0;
					    rgb[2] = 255;
				            break;
			    } else if (i == third.size()-1 && third.elementAt(i)[1] == 1 && x >= third.elementAt(i)[0] ) {
					       rgb[0] = 255;
					       rgb[1] = 0;
					       rgb[2] = 255;
					       break;
			     } else if (third.elementAt(i)[1] == 1 && x >= third.elementAt(i)[0] && x <= third.elementAt(i+1)[0] ) {
				       rgb[0] = 255;
				       rgb[1] = 0;
				       rgb[2] = 255;
				       break;
			     } 
			     else {
				       rgb[0] = rgb[1] = rgb[2] = 255;
			//	       break;
			     }
		       }	    
	    }
	    else rgb[0] = rgb[1] = rgb[2] = 255;


/*       for (int i=0; i<third.size(); i++) {
	       if (y >= 300 && y <= 320) {
		       if (i == 0) {
			       if (third.elementAt(i)[1] == -1 && x <= third.elementAt(i)[0] ) {
				       rgb[0] = 255;
				       rgb[1] = 0;
				       rgb[2] = 255;
				       break;
			       }
		       } else if (i == third.size()-1) {
			       if (third.elementAt(i)[1] == 1 && x >= third.elementAt(i)[0]) {
				       rgb[0] = 255;
				       rgb[1] = 0;
				       rgb[2] = 255;
				       break;
			       }
		       } else if (third.elementAt(i)[1] == 1 && x >= third.elementAt(i)[0] && x <= third.elementAt(i+1)[0] ) {
			       rgb[0] = 255;
			       rgb[1] = 0;
			       rgb[2] = 255;
			       break;
		       } else {
			       rgb[0] = 255;
			       rgb[1] = 255;
			       rgb[2] = 255;
		       }
	       }
       } */

    }
}
