package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;

import java.lang.Math;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.proto.Wpimath;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PoseEstimatorSubsystem extends SubsystemBase {
    private final PhotonCamera photonCamera;
    private final DriveTrain driveTrain;
    private final SwerveDrivePoseEstimator poseEstimator;
    private double previousTimeStampSeconds = 0;
    private final AprilTagFieldLayout aprilTagFieldLayout; 
    private final Transform3d cameraToRobot;

    public PoseEstimatorSubsystem(PhotonCamera photonCamera, DriveTrain driveTrain) {
        this.photonCamera = photonCamera;
        this.driveTrain = driveTrain;
        this.poseEstimator = new SwerveDrivePoseEstimator(driveTrain.getKinematics(), driveTrain.getGyroscopeRotation(), 
                                driveTrain.getSwerveModulePositions(), new Pose2d());
        aprilTagFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();
        cameraToRobot = new Transform3d(0.0,-0.09525,-1,new Rotation3d()); //fill in Z later
    }

    public Pose2d getPosition() {
        return poseEstimator.getEstimatedPosition();
    }

    @Override
    public void periodic(){
       updatePoseEstimator();
       if (Constants.debuggymodey){
        displayCurrentPosition();
       }
    }

    private Pose2d camToMid(Pose2d campos){
        double cr = campos.getRotation().getRadians(); //modify this to actually translate the coords

        double ty = -0.09525 * Math.sin(cr); // 3.75in = 0.09525m
        double tx = -0.09525 * Math.cos(cr);
        
        return campos.transformBy(new Transform2d(tx, ty, new Rotation2d(0)));

    }
     //MEASURE NOTES
     //17.5 from edge for middle of robit longways
     // 21.25 camera
     // that means its 3.75 inches off
     // OH MY LORT I NEED TO TAKE INTO ACCOUNT DIRECTION
     // longways it is 35in/0.889m
     // shortways it is 29.5in/0.7493m

    private void displayCurrentPosition(){
        var position = poseEstimator.getEstimatedPosition();
        SmartDashboard.putNumber("Robot X", position.getX());
        SmartDashboard.putNumber("Robot Y", position.getY());
        SmartDashboard.putNumber("Robot Rotation degrees", position.getRotation().getDegrees());
    }

    private void updatePoseEstimator(){
        poseEstimator.update(driveTrain.getGyroscopeRotation(), driveTrain.getSwerveModulePositions());
        var pipelineResult = photonCamera.getLatestResult();
        var resultTimeStamp = pipelineResult.getTimestampSeconds();
        if (resultTimeStamp != previousTimeStampSeconds && pipelineResult.hasTargets()){
            previousTimeStampSeconds = resultTimeStamp;
            var target = pipelineResult.getBestTarget();
            if (target.getPoseAmbiguity() <= .2){
                aprilTagFieldLayout.getTagPose(target.getFiducialId()).ifPresent(
                    targetPos -> {
                        Transform3d camToTarget = target.getBestCameraToTarget();
                        Pose3d camPose = targetPos.transformBy(camToTarget.inverse());
                        //FIGGER OUT HOW TO GET THE 3D ROTATION
                        var visionMeasurement = camPose.transformBy(cameraToRobot);
                        poseEstimator.addVisionMeasurement(visionMeasurement.toPose2d(), previousTimeStampSeconds);
                    });
            }
        }
    }

    
}