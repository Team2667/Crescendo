package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.PoseEstimatorSubsystem;

public class SavePosition extends Command {
    private DriveTrain driveTrain;
    private PoseEstimatorSubsystem poseEstimator;

    public SavePosition(DriveTrain driveTrain, PoseEstimatorSubsystem poseEstimator) {
        addRequirements(driveTrain);
        this.driveTrain=driveTrain;
        this.poseEstimator=poseEstimator;
    }

    @Override
    public void initialize(){
        Pose2d pose=poseEstimator.getPosition();
        double x=pose.getX();
        double y=pose.getY();
        double rotation=pose.getRotation().getDegrees();
        RobotContainer.locations[RobotContainer.locationCount][0]=x;
        RobotContainer.locations[RobotContainer.locationCount][1]=y;
        RobotContainer.locations[RobotContainer.locationCount][2]=rotation;
        //RobotContainer.locationCount++;
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
