package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class MoveToPosition extends Command {
    private static TrapezoidProfile.Constraints X_CONSTRAINTS = new TrapezoidProfile.Constraints(3,2 );
    private static TrapezoidProfile.Constraints Y_CONSTRAINTS = new TrapezoidProfile.Constraints(3,2 );
    private static TrapezoidProfile.Constraints OMEGA_CONSTRAINTS = new TrapezoidProfile.Constraints(4,3 );

    private static final Transform3d TAG_TO_GOAL = // Where we want to be with respect to the tag goal
        new Transform3d(
            new Translation3d(1.5, 0.0, 0.0),
            new Rotation3d(0.0, 0.0, Math.PI));
    private  DriveTrain driveTrain;
    private  ProfiledPIDController xController = new ProfiledPIDController(0.5, 0.00000001, 0, X_CONSTRAINTS);
    private  ProfiledPIDController yController = new ProfiledPIDController(0.5, 0, 0, Y_CONSTRAINTS);
    private  ProfiledPIDController omegaController = new ProfiledPIDController(0.010, 0, 0, OMEGA_CONSTRAINTS);
    private  Supplier<Pose2d> robotPoseSupplier;
    private Pose2d goalPose;


    public MoveToPosition(DriveTrain driveTrain, Supplier<Pose2d> robotPoseSupplier,double x, double y,double rotation) {
        this.driveTrain = driveTrain;
        this.robotPoseSupplier = robotPoseSupplier;


        xController.setTolerance(0.005);
        yController.setTolerance(0.005);
        omegaController.setTolerance(Units.degreesToRadians(3));
        omegaController.enableContinuousInput(-Math.PI, Math.PI);
        addRequirements(driveTrain);

        goalPose = new Pose2d(x,y,new Rotation2d(Math.toRadians(rotation)));
    }

    @Override
    public void initialize() {
        //lastTarget
        var robotPose = robotPoseSupplier.get();
        omegaController.reset(robotPose.getRotation().getRadians());
        xController.reset(robotPose.getX());
        yController.reset(robotPose.getY());

        omegaController.setGoal(goalPose.getRotation().getRadians());
        xController.setGoal(goalPose.getX());
        yController.setGoal(goalPose.getY());
    }

    @Override
    public void execute() {
        var robotPose = robotPoseSupplier.get();
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
