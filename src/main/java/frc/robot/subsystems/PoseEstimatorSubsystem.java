package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
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
        cameraToRobot = new Transform3d(0,0,0,new Rotation3d());
    }

    @Override
    public void periodic(){
       updatePoseEstimator();
       if (Constants.debuggymodey){
        displayCurrentPosition();
       }
    }

    private void displayCurrentPosition(){
        var position = poseEstimator.getEstimatedPosition();
        SmartDashboard.putNumber("Robot X", position.getX());
        SmartDashboard.putNumber("Robot Y", position.getY());
        SmartDashboard.putNumber("Robot Rotation degrees", position.getRotation().getDegrees());
    }

    private void updatePoseEstimator(){
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
                        var visionMeasurement = camPose.transformBy(cameraToRobot);
                        poseEstimator.addVisionMeasurement(visionMeasurement.toPose2d(), previousTimeStampSeconds);
                    });
            }
        }
        poseEstimator.update(driveTrain.getGyroscopeRotation(), driveTrain.getSwerveModulePositions());
    }

    
}