package Legacy2;
 
import robocode.*;
import java.awt.*;
 

public class Mike_Legacy extends AdvancedRobot {
	int moveDirection=1;//which way to move
	
	 // main run function
	
	public void run() {
		setAdjustRadarForRobotTurn(true);//radar still while we turn
		setColors(Color.black, Color.gray, Color.orange);
		setScanColor(Color.red);
		setAdjustGunForRobotTurn(true); //gun still when we turn
		turnRadarRightRadians(Double.POSITIVE_INFINITY);//turn radar right
		}
 

	public void onScannedRobot(ScannedRobotEvent e) {
		double absBearing=e.getBearingRadians()+getHeadingRadians();//enemies bearing
		double latVel=e.getVelocity() * Math.sin(e.getHeadingRadians() -absBearing);//enemies velocity
		double gunTurnAmt;//amount to turn gun
		setTurnRadarLeftRadians(getRadarTurnRemainingRadians());//lock on the radar
		setColors(Color.black, Color.gray, Color.orange);
		if(Math.random()>.9){
			setMaxVelocity((12*Math.random())+12);//random speed change
		}
		if (e.getDistance() > 150) {//if distance is greater than 150
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/22);//amount to turn our gun, lead just a little bit
			setTurnGunRightRadians(gunTurnAmt); //turn our gun
			setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+latVel/getVelocity()));//drive towards the enemies predicted future location
			setAhead((e.getDistance() - 140)*moveDirection);//move forward
			setColors(Color.black, Color.red, Color.white);
			setBulletColor(Color.white);
			setFire(1.0);//fire
			
		}
	
	else if (e.getDistance() > 100) {//if distance is less than 150 greater than 100
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/15);//amount to turn our gun, lead just a little bit
			setTurnGunRightRadians(gunTurnAmt);//turn gun
			setTurnLeft(-90-e.getBearing()); //turn perpendicular to the enemy
			setAhead((e.getDistance() - 140)*moveDirection);//move forward
			setColors(Color.blue, Color.white, Color.red);
			setBulletColor(Color.orange);
			setFire(2.5);//fire
			
		}
	

		else{//if fairly close
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/15);//amount to turn our gun, lead just a little bit
			setTurnGunRightRadians(gunTurnAmt);//turn gun
			setTurnLeft(-90-e.getBearing()); //turn perpendicular to the enemy
			setAhead((e.getDistance() - 140)*moveDirection);//move forward
			setColors(Color.black, Color.green, Color.green);
			setBulletColor(Color.green);
			setFire(3.0);//fire
			
		}	
	}
	public void onHitWall(HitWallEvent e){
		moveDirection=-moveDirection;//reverse direction
	}

	public void onWin(WinEvent e) {
		setColors(Color.black, Color.gray, Color.orange);
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
		}
	}

}
