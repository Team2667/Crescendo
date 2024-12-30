package frc.robot;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.PoseEstimatorSubsystem;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class TrajectoryCommandFactory {
    private static TrapezoidProfile.Constraints X_CONSTRAINTS = new TrapezoidProfile.Constraints(3,2 );
    private static TrapezoidProfile.Constraints Y_CONSTRAINTS = new TrapezoidProfile.Constraints(3,2 );
    private static TrapezoidProfile.Constraints OMEGA_CONSTRAINTS = new TrapezoidProfile.Constraints(4,3 );

    DriveTrain driveTrain;
    PoseEstimatorSubsystem poseEstimator;
    private  PIDController xController = new PIDController(0.5, 0.00000001, 0);
    private  PIDController yController = new PIDController(0.5, 0, 0);
    private  ProfiledPIDController omegaController = new ProfiledPIDController(0.010, 0, 0, OMEGA_CONSTRAINTS);


     public TrajectoryCommandFactory(DriveTrain driveTrain, PoseEstimatorSubsystem poseEstimator) {
        this.driveTrain = driveTrain;
        this.poseEstimator = poseEstimator;
     }

    public SwerveControllerCommand createTrajectoryCommand(Pose2d startingPos, List<Translation2d> wayPoints, Pose2d endingPos) {
        Trajectory trajectory = driveTrain.generateTrajectory(startingPos, wayPoints, endingPos);
        Supplier<Pose2d> supplier = () -> poseEstimator.getPosition();
        HolonomicDriveController controller = new
                HolonomicDriveController(xController, yController, omegaController);
        Consumer<SwerveModuleState[]> consumeModuleStates =
            (SwerveModuleState[] states) -> driveTrain.drive(states);
        return new SwerveControllerCommand(trajectory, supplier, driveTrain.getKinematics(),
            controller, consumeModuleStates, driveTrain, poseEstimator);
    }    
}
