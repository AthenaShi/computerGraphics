import java.awt.*;

public class egg extends BufferedApplet
{
	int width = 0;
	int height = 0;
	double scale = 0;
	double startTime = getTime();

	public void render(Graphics g) {
		double time = getTime() - startTime;

		width = getWidth();
		height = getHeight();
		scale = width / 20;
		g.setColor(Color.white);
		g.fillRect(0,0,width,height);

		g.setColor(Color.black);

		double Rect3D[][] = { 
			{-1, 1, 1, 1},
			{-1, 1,-1, 1},
			{ 1, 1, 1, 1},
			{ 1, 1,-1, 1},
			{-1,-1, 1, 1},
			{-1,-1,-1, 1},
			{ 1,-1, 1, 1},
			{ 1,-1,-1, 1}
		};
		double tempRect3D[][] = new double[8][3];
		int drawRect3D[][] = new int[8][3];

		Matrix m = new Matrix();
		m.identity();
//		m.translate(time,time,0);
		m.rotateY(1 * time);
		m.rotateX(1 * time);
//		m.rotateZ(1 * time);
		m.scale(time,time,time);
		

		for (int i = 0; i<8; i++) {
			m.transform(Rect3D[i],tempRect3D[i]);
			viewport(tempRect3D[i],drawRect3D[i]);
		}

		g.drawLine(drawRect3D[0][0],drawRect3D[0][1],drawRect3D[1][0],drawRect3D[1][1]);
		g.drawLine(drawRect3D[0][0],drawRect3D[0][1],drawRect3D[2][0],drawRect3D[2][1]);
		g.drawLine(drawRect3D[0][0],drawRect3D[0][1],drawRect3D[4][0],drawRect3D[4][1]);

		g.drawLine(drawRect3D[3][0],drawRect3D[3][1],drawRect3D[1][0],drawRect3D[1][1]);
		g.drawLine(drawRect3D[3][0],drawRect3D[3][1],drawRect3D[2][0],drawRect3D[2][1]);
		g.drawLine(drawRect3D[3][0],drawRect3D[3][1],drawRect3D[7][0],drawRect3D[7][1]);

		g.drawLine(drawRect3D[5][0],drawRect3D[5][1],drawRect3D[1][0],drawRect3D[1][1]);
		g.drawLine(drawRect3D[5][0],drawRect3D[5][1],drawRect3D[4][0],drawRect3D[4][1]);
		g.drawLine(drawRect3D[5][0],drawRect3D[5][1],drawRect3D[7][0],drawRect3D[7][1]);

		g.drawLine(drawRect3D[6][0],drawRect3D[6][1],drawRect3D[2][0],drawRect3D[2][1]);
		g.drawLine(drawRect3D[6][0],drawRect3D[6][1],drawRect3D[4][0],drawRect3D[4][1]);
		g.drawLine(drawRect3D[6][0],drawRect3D[6][1],drawRect3D[7][0],drawRect3D[7][1]);

	}

	double getTime() {
		return System.currentTimeMillis() / 1000.0;
	}

	public void viewport(double src[], int dst[]) {
		dst[0] = (int) ( 0.5 * width  + src[0] * scale );
		dst[1] = (int) ( 0.5 * height - src[1] * scale );
	}
}

