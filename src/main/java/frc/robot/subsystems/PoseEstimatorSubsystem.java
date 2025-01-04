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
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
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
        SmartDashboard.putNumber("Yaw",driveTrain.getRawYaw());
        SmartDashboard.putNumber("Adjusted Yaw",driveTrain.getYawAdjusted());
   
    }

    private void updatePoseEstimator(){
        poseEstimator.update(driveTrain.getGyroscopeRotation(), driveTrain.getSwerveModulePositions());
        var pipelineResult = photonCamera.getLatestResult();
        var resultTimeStamp = pipelineResult.getTimestampSeconds();        
        if (resultTimeStamp != previousTimeStampSeconds && pipelineResult.hasTargets()){
            SmartDashboard.putNumber("vision timestamp", resultTimeStamp);
            previousTimeStampSeconds = resultTimeStamp;
            var target = pipelineResult.getBestTarget();
            SmartDashboard.putNumber("vision ambiguity", target.getPoseAmbiguity());

            if (target.getPoseAmbiguity() <= .2){
                aprilTagFieldLayout.getTagPose(target.getFiducialId()).ifPresent(
                    targetPos -> {
                        Transform3d camToTarget = target.getBestCameraToTarget();
                        if(Constants.debuggymodey){
                            SmartDashboard.putNumber("cam-to-target-transform-x", camToTarget.getX());
                            SmartDashboard.putNumber("cam-to-target-transform-y", camToTarget.getY());
                            SmartDashboard.putNumber("cam-to-target-transform-z", camToTarget.getZ());
                        }
                        Pose3d camPose = targetPos.transformBy(camToTarget.inverse());
                        if(Constants.debuggymodey){
                            SmartDashboard.putNumber("camPose-x", camPose.getX());
                            SmartDashboard.putNumber("camPose-y", camPose.getY());
                            SmartDashboard.putNumber("camPose-z", camPose.getZ());
                            SmartDashboard.putNumber("camPose-rotation", camPose.getRotation().getAngle());
                        }

                        //FIGGER OUT HOW TO GET THE 3D ROTATION
                        var visionMeasurement = camPose.transformBy(cameraToRobot);
                        var visionPos = visionMeasurement.toPose2d();
                        if(Constants.debuggymodey) {
                            SmartDashboard.putNumber("Vision X", visionPos.getX());
                            SmartDashboard.putNumber("Vision Y", visionPos.getY());
                            SmartDashboard.putNumber("Vision rotation", visionPos.getRotation().getDegrees());
                            SmartDashboard.putNumber("Vision timestamp", resultTimeStamp);
                        }
                       
                        var rotation = Rotation2d.fromDegrees((visionPos.getRotation().getDegrees() + 180) % 360);
                        poseEstimator.addVisionMeasurement(new Pose2d(visionPos.getX(), visionPos.getY(), rotation), 
                                    previousTimeStampSeconds);
                        //poseEstimator.addVisionMeasurement(new Pose2d(visionPos.getX(), 8.218-visionPos.getY(), rotation), 
                        //                        previousTimeStampSeconds);
                        //poseEstimator.addVisionMeasurement(new Pose2d(visionPos.getX(),visionPos.getY(),visionPos.getRotation()), previousTimeStampSeconds);
                       //poseEstimator.resetPosition(driveTrain.getGyroscopeRotation(), driveTrain.getSwerveModulePositions(), getPosition());
                    });
            }
        }
    }   
}