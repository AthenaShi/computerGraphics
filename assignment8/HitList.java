import java.util.*;

public class HitList {
	double data[][] = new double[0][3];
	private Vector<double[]> temp = new Vector<double[]>();

	public void Put(double setList[][]) {
		int x = setList.length;
		data = new double[x][3];
		for (int i=0; i<x; i++) {
			data[i][0] = setList[i][0];
			data[i][1] = setList[i][1];
			data[i][2] = setList[i][2];
		}
	}

	public boolean isEmpty() {
		return data.length>0 ? false : true;
	}

	public void Union(double newlist[][]) {
		int firstI = 0, secondI = 0;
		int thisI = 0;
		boolean firstDone = data.length == 0 ? true : false;
		boolean firstOver = true;
		boolean secondDone = newlist.length == 0 ? true : false;
		boolean secondOver = true;
		temp.clear();
		while (!firstDone || !secondDone) {
			// if this element if in the first List
			if (!firstDone && (data[firstI][0] <= newlist[secondI][0] || !secondOver)) {
				// if this element is "in" and next element in other List is "out"
				if ( data[firstI][2] == 1 && ( newlist[secondI][2] == -1 || !secondOver) ) {
					double toAdd[] = {data[firstI][0], data[firstI][1], 1};
					temp.add( toAdd );
				// if this element is "out" and next element in other List is "out"
				} else if ( data[firstI][2] == -1 && ( newlist[secondI][2] == -1 || !secondOver) ) {
					double toAdd[] = {data[firstI][0],data[firstI][1], -1};
					temp.add( toAdd );
				}
				// if the last element in this List
				if (firstI >= data.length-1) {
					firstDone = true;	// mark this List is done!
					// if this List is over (means the last is "out")
					if (data[firstI][2] == 1) {
						firstOver = false;
					} else {
						firstOver = true;
					}
				} else {
					firstI++;
				}
			} else if (!secondDone && ( data[firstI][0] > newlist[secondI][0] || !firstOver)) {
				if ( newlist[secondI][2] == 1  && ( data[firstI][2] == -1 || !firstOver) ) {
					double toAdd[] = {newlist[secondI][0], newlist[secondI][1], 1};
					temp.add( toAdd );
				} else if ( newlist[secondI][2] == -1 && ( data[firstI][2] == -1 || !firstOver) ) {
					double toAdd[] = {newlist[secondI][0], newlist[secondI][1], -1};
					temp.add( toAdd );
				}
				if (secondI >= newlist.length-1) {
					secondDone = true;
					if (newlist[secondI][2] == 1) {
						secondOver = false;
	
					} else {
						secondOver = true;
					}
				} else {
					secondI++;
				}
			} else if ((secondDone && secondOver) || (firstDone && firstOver))
				break;
		}

		int x = temp.size();
		data = new double[x][3];
		for (int i=0; i<x; i++) {
			data[i][0] = temp.get(i)[0];
			data[i][1] = temp.get(i)[1];
			data[i][2] = temp.get(i)[2];
		}
	}

}
