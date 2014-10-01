package Legacy1;
 
import robocode.*;
import robocode.util.*;
import java.awt.*;
 

public class Legacy1 extends AdvancedRobot {
	static int HGShots;     
	static int LGShots;   
	static int HGHits;   
	static int LGHits;     
	boolean gunIdent;   
	int dir = 1;
	double energy;
	static int enemyFireCount = 0;
 
	public void run() {
		setAdjustRadarForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setBodyColor(Color.black);
		setGunColor(Color.black);
		setRadarColor(Color.orange);
		setBulletColor(Color.cyan);
		setScanColor(Color.cyan);
 
		setTurnRadarRight(Double.POSITIVE_INFINITY);

		turnLeft(getHeading() % 90);
 
		while (true) {

				if (Utils.isNear(getHeadingRadians(), 0D) || Utils.isNear(getHeadingRadians(), Math.PI)) {
					ahead((Math.max(getBattleFieldHeight() - getY(), getY()) - 28) * dir);
				} else {
					ahead((Math.max(getBattleFieldWidth() - getX(), getX()) - 28) * dir);
				}
			turnRight(90 * dir);
			
		}
	}
 

	public void onScannedRobot(ScannedRobotEvent e) {
		double absBearing = e.getBearingRadians() + getHeadingRadians();               
		double latVel = e.getVelocity() * Math.sin(e.getHeadingRadians() - absBearing); 
		double radarTurn = absBearing - getRadarHeadingRadians();                      
 
		double HGRating = (double) HGHits / HGShots;
		double LGRating = (double) LGHits / LGShots;
 
		if (energy > (energy = e.getEnergy())) {
			enemyFireCount++;
			if (enemyFireCount % 5 == 0) {
				dir = -dir;
				if (Utils.isNear(getHeadingRadians(), 0D) || Utils.isNear(getHeadingRadians(), Math.PI)) {
					setAhead((Math.max(getBattleFieldHeight() - getY(), getY()) - 28) * dir);
				} else {
					setAhead((Math.max(getBattleFieldWidth() - getX(), getX()) - 28) * dir);
				}
			}
		}
 
		setMaxVelocity(Math.random() * 12);
 
		if ((getRoundNum() == 0 || LGRating > HGRating) && getRoundNum() != 1){ 
			double bulletPower = Math.min(3, e.getEnergy() / 4);
			setTurnGunRightRadians(Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + Math.asin(latVel / (20 - 3 * bulletPower))));
			LGShots++;
			gunIdent = true;
			setFire(bulletPower);
		} else { // 
			setTurnGunRightRadians(Utils.normalRelativeAngle(absBearing - getGunHeadingRadians()));
			HGShots++;
			gunIdent = false;
			setFire(e.getEnergy() / 4);
		}
		setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn) * 2); 
	}
 
	public void onBulletHit(BulletHitEvent e) {
		if(gunIdent) {
			LGHits = LGHits+1;
		} else {
			HGHits = HGHits+1;
		}
	}
}
