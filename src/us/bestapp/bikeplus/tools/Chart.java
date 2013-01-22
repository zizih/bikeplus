package us.bestapp.bikeplus.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class Chart extends View {
	private Canvas canvas;
	private Paint paintCurve, paintGrid, paint;
	private PathEffect effect;
	private Path pathCurve;

	private int default_WIDTH = 300, default_HEIGHT = 400;
	public float default_MAX_SPEED = 30;
	public float default_WARNING_SPEED = 20;
	private int default_VERTICAL_LINES = 30, default_HORIZONTAL_LINES = 9;

	private float OX = 5, OY = 5;
	private int WIDTH = default_WIDTH, HEIGHT = default_HEIGHT;
	public float MAX_SPEED = default_MAX_SPEED;
	public float WARNING_SPEED = default_WARNING_SPEED;
	private int VERTICAL_LINES = default_VERTICAL_LINES,
			HORIZONTAL_LINES = default_HORIZONTAL_LINES;

	private float vEXPEND, hEXPEND;
	private float hEXPRESS;
	private float[] speeds = new float[HORIZONTAL_LINES + 1];
	private int i = 0;

	public Chart(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Chart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		this.canvas = canvas;
		initVariable();
		// canvas.drawColor(Color.BLACK);
		drawCurve(canvas);
		drawGrid(canvas);
		drawWarningSpeed(canvas);
	}

	public void initVariable() {
		vEXPEND = WIDTH / HORIZONTAL_LINES;
		hEXPEND = HEIGHT / VERTICAL_LINES;
		hEXPRESS = HEIGHT / MAX_SPEED;
	}

	public void drawGrid(Canvas canvas) {
		paintGrid = new Paint();
		paintGrid.setColor(Color.WHITE);
		paintGrid.setStyle(Paint.Style.STROKE);
		paintGrid.setAlpha(20);
		canvas.drawRect(OX, OY, OX + WIDTH, OY + HEIGHT, paintGrid);
		// 横线
		for (int i = 1; i < VERTICAL_LINES; i++) {
			canvas.drawLine(OX, OY + i * hEXPEND, OX + WIDTH, OY + i * hEXPEND,
					paintGrid);
		}
		// 竖线
		for (int i = 1; i < HORIZONTAL_LINES; i++) {
			canvas.drawLine(OX + vEXPEND * i, OY, OX + vEXPEND * i,
					OY + HEIGHT, paintGrid);
		}

	}

	public void drawWarningSpeed(Canvas canvas) {
		// warning speed
		paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setAlpha(100);
		paint.setColor(Color.RED);
		canvas.drawLine(OX, OY + HEIGHT - WARNING_SPEED * hEXPRESS, OX + WIDTH,
				OY + HEIGHT - WARNING_SPEED * hEXPRESS, paint);
	}

	public void drawCurve(Canvas canvas) {
		// speeds = new float[] { 11.32437658679f, 22.235346547f, 10.5437658f,
		// 28.365476587f, 5.3243f, 12.53453f, 23.4353465f, 10.325345f,
		// 3.345346f, 20.3436254f };
		speeds = getSpeeds();
		paintCurve = new Paint();
		paintCurve.setStyle(Paint.Style.FILL_AND_STROKE);
		paintCurve.setColor(Color.GREEN);
		effect = new CornerPathEffect(10);
		paintCurve.setPathEffect(effect);
		pathCurve = new Path();
		pathCurve.moveTo(OX, OY + HEIGHT);

		// 1 - 10
		for (int i = 0; i <= HORIZONTAL_LINES; i++) {
			pathCurve.lineTo(OX + vEXPEND * i, OY + HEIGHT - speeds[i]
					* hEXPRESS);

		}
		pathCurve.lineTo(OX + WIDTH, OY + HEIGHT);
		pathCurve.lineTo(OX, OY + HEIGHT);
		canvas.translate(0, 0);
		canvas.drawPath(pathCurve, paintCurve);
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension((int) (WIDTH + OX * 3), (int) (HEIGHT + OY * 3));
	}

	public void setSpeed(float speed) {

		if (i < 10) {
			for (int j = i; j > 0; j--) {
				speeds[j] = speeds[j - 1];
			}
			speeds[0] = speed;
			i++;
		} else {
			for (int j = 9; j > 0; j--) {
				speeds[j] = speeds[j - 1];
			}
			speeds[0] = speed;
		}
	}

	public float[] getSpeeds() {
		return this.speeds;
	}

	public void setWarningSpeed(float warnSpeed) {
		this.WARNING_SPEED = warnSpeed;
	}

	public float getWarningSpeed() {
		return this.WARNING_SPEED;
	}

	public float getDefaultWarningSpeed() {
		return this.default_WARNING_SPEED;
	}

	public void setVerticalLines(int verticalLines) {
		this.VERTICAL_LINES = verticalLines;
		initVariable();
	}

	public void setHorizontalLines(int horizontalLines) {
		this.HORIZONTAL_LINES = horizontalLines;
		initVariable();
	}

	public void setMaxSpeed(float speed) {
		this.MAX_SPEED = speed;
		initVariable();
	}

	public float getMaxSpeed() {
		return this.MAX_SPEED;
	}

	public float getDefaultMaxSpeed() {
		return this.default_MAX_SPEED;
	}

	public void resetMaxAndWarnSpeed() {
		this.MAX_SPEED = default_MAX_SPEED;
		this.WARNING_SPEED = default_WARNING_SPEED;
		initVariable();
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getDefaultWIDTH() {
		return default_WIDTH;
	}

	public void setWH(int w, int h) {
		this.WIDTH = w;
		this.HEIGHT = h;
		initVariable();
	}

	public void resetWH() {
		this.WIDTH = default_WIDTH;
		this.HEIGHT = default_HEIGHT;
		initVariable();
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public int getDefaultHEIGHT() {
		return default_HEIGHT;
	}

	public void reset() {
		WIDTH = default_WIDTH;
		HEIGHT = default_HEIGHT;
		MAX_SPEED = default_MAX_SPEED;
		WARNING_SPEED = default_WARNING_SPEED;
		VERTICAL_LINES = default_VERTICAL_LINES;
		HORIZONTAL_LINES = default_HORIZONTAL_LINES;
		initVariable();
		drawGrid(canvas);
	}
}
