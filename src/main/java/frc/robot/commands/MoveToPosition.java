package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.PoseEstimatorSubsystem;

public class MoveToPosition extends Command {
    private static TrapezoidProfile.Constraints X_CONSTRAINTS = new TrapezoidProfile.Constraints(3,2 );
    private static TrapezoidProfile.Constraints Y_CONSTRAINTS = new TrapezoidProfile.Constraints(3,2 );
    private static TrapezoidProfile.Constraints OMEGA_CONSTRAINTS = new TrapezoidProfile.Constraints(8,8 );

    private static final Transform3d TAG_TO_GOAL = // Where we want to be with respect to the tag goal
        new Transform3d(
            new Translation3d(1.5, 0.0, 0.0),
            new Rotation3d(0.0, 0.0, Math.PI));
    private static DriveTrain driveTrain;
    private static PoseEstimatorSubsystem poseEstimatorSubsystem;
    private static ProfiledPIDController xController = new ProfiledPIDController(3, 0, 0, X_CONSTRAINTS);
    private static ProfiledPIDController yController = new ProfiledPIDController(3, 0, 0, Y_CONSTRAINTS);
    private static ProfiledPIDController omegaController = new ProfiledPIDController(3, 0, 0, OMEGA_CONSTRAINTS);
    private Pose2d goalPose;


    public MoveToPosition(DriveTrain driveTrain, PoseEstimatorSubsystem poseEstimatorSubsystem) {
        this.driveTrain = driveTrain;
        this.poseEstimatorSubsystem = poseEstimatorSubsystem;

        xController.setTolerance(0.2);
        yController.setTolerance(0.2);
        omegaController.setTolerance(Units.degreesToRadians(3));
        omegaController.enableContinuousInput(-Math.PI, Math.PI);
        addRequirements(driveTrain);

        goalPose = new Pose2d(2,2,new Rotation2d(Math.PI));
    }

    @Override
    public void initialize() {
        //lastTarget
        var robotPose = poseEstimatorSubsystem.getPosition();
        omegaController.reset(robotPose.getRotation().getRadians());
        xController.reset(robotPose.getX());
        yController.reset(robotPose.getY());

        omegaController.setGoal(goalPose.getRotation().getRadians());
        xController.setGoal(goalPose.getX());
        yController.setGoal(goalPose.getY());
    }

    @Override
    public void execute() {
        var robotPose = poseEstimatorSubsystem.getPosition();
        var xSpeed = xController.atGoal() ? 0 : xController.calculate(robotPose.getX());
        var ySpeed = yController.atGoal() ? 0 : yController.calculate(robotPose.getY());
        var omegaSpeed = omegaController.atGoal() ? 0 : omegaController.calculate(robotPose.getRotation().getRadians());

        driveTrain.drive(
            ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed,ySpeed,omegaSpeed, robotPose.getRotation())
        );
    }

    @Override
    public boolean isFinished() {
        return xController.atGoal() && yController.atGoal() && omegaController.atGoal();
    }

    @Override
    public void end(boolean isInterrupted) {
        driveTrain.stop();
    }
}
