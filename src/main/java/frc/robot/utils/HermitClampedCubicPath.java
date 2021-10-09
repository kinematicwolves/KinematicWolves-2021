package frc.robot.utils;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.geometry.*;

public class HermitClampedCubicPath {
    private Pose2d startPose;
    private Pose2d endPose;
    private ArrayList<Translation2d> interiorWaypoints;
    private double maxSpeed;
    private double maxAcceleration;

    public HermitClampedCubicPath(Pose2d startPose, ArrayList<Translation2d> interiorWaypoints, Pose2d endPose, double maxSpeed, double maxAcceleration) {
        this.startPose = startPose;
        this.endPose = endPose;
        this.interiorWaypoints = interiorWaypoints;
        this.maxSpeed = maxSpeed;
        this.maxAcceleration = maxAcceleration;
    }

    public Pose2d getStartPose() {
        return(startPose);
    }

    public Pose2d getEndPose() {
        return(endPose);
    }

    public Translation2d getWayPoint(int index) {
        return(interiorWaypoints.get(index));
    }

    public ArrayList<Translation2d> getWayPoints() {
        return(interiorWaypoints);
    }

    // Returns node count of the path
    public int getNodeCount() {
        return(interiorWaypoints.size()+2);
    }

    public double getMaxSpeed() {
        return(maxSpeed);
    }

    public double getMaxAcceleration() {
        return(maxAcceleration);
    }
}