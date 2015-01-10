import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Random;

public class Grass extends PApplet{
	ArrayList<GrassBlade> grassArray;
	private int width = 800;
	private int height = 600;
	private int grassNumber = 300;
	
	public void setup(){
		size(this.width, this.height);
		background(255,255,255);
		frameRate(30);
		GrassBlade temp;
		Random rand = new Random();
		int minus = -1;
		// instantiate grass blades
		grassArray = new ArrayList<GrassBlade>();
		for(int i=0;i<grassNumber;i++){
			temp = new GrassBlade(this, rand.nextInt(width), 8f, rand.nextInt(20)*minus+100f, rand.nextInt(21)+1);
			//temp = new GrassBlade(this, (float)(width/2*rand.nextGaussian()+width/3), 8f, rand.nextInt(20)*minus+100f, rand.nextInt(21)+1);
			grassArray.add(temp);
			minus*=-1;
		}
	}
	public void draw(){
		stroke(255,255,255);
		fill(255,255,255);
		rect(0,0, this.width, this.height);
		stroke(0,128,0);
		fill(0,128,0);
		rect(0, this.height-5, this.width, 5);
		for(GrassBlade x : grassArray){
			x.paintOut();
			x.swing();
		}
		// try to fill some gap
		translate(10, -5);
		for(GrassBlade x : grassArray){
			x.paintOut();
			x.swing();
		}
	}
	
	private class GrassBlade{
		private float degree;
		private float degreeRange;
		private float height;
		private float width;
		private float leftBottom;
		private PVector slope;
		private Grass parent;
		private int swingDir;
		private int [] color;
		public GrassBlade(Grass parent, float leftBottom, float width, float height, int degree){
			this.parent = parent;
			this.leftBottom = leftBottom;
			this.height = height;
			this.width = width;
			this.slope = new PVector(width/2, -height);
			this.slope.mult(1/3.f);
			this.swingDir = 1;
			Random rand = new Random();
			this.degreeRange = -degree/360.f*PI;
			this.degree = degreeRange*rand.nextFloat();
			// slightly randomize the green
			color = new int[3];
			color[0] = rand.nextInt(20)+0;
			color[1] = rand.nextInt(10)*(rand.nextBoolean()?-1:1)+128;
			color[2] = rand.nextInt(20)+0;
		}
		void paintOut(){
			// draw the grass blade as three parts composing together
			pushMatrix();
			fill(color[0],color[1],color[2]);
			stroke(color[0],color[1],color[2]);
			// first part
			translate(leftBottom, parent.height);
			beginShape();
			vertex(0,0);
			vertex(width, 0);
			vertex(slope.x+width/3*2, slope.y);
			vertex(slope.x, slope.y);
			endShape();
			// second part
			translate(slope.x, slope.y);
			rotate(degree);
			beginShape();
			vertex(0,0);
			vertex(width/3*2, 0);
			vertex(slope.x+width/3, slope.y);
			vertex(slope.x, slope.y);
			endShape();
			// thrid part
			translate(slope.x, slope.y);
			rotate(degree);
			beginShape();
			vertex(0,0);
			vertex(width/3, 0);
			vertex(slope.x, slope.y);
			endShape();
			rotate(-2*degree);
			popMatrix();
		}
		public void swing(){
			// make it swing
			if(degree<=degreeRange){
				swingDir = 1;
			}
			if(degree>=-degreeRange){
				swingDir = -1;
			}
			degree += 0.0087*1*swingDir;
		}
	}
}
