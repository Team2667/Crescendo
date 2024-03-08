package frc.robot.subsystems;
import com.kauailabs.navx.frc.*;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.Pigeon2;
import static frc.robot.Constants.*;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.swerveSupport.SwerveModule;
import frc.robot.subsystems.swerveSupport.SwerveModuleConfiguration;

public class DriveTrain extends SubsystemBase {
    Pigeon2 pigeon = new Pigeon2(Constants.PigeonId); //CHANGE ME ASAP
    private final AHRS m_navx = new AHRS(SPI.Port.kMXP, (byte) 200); // NavX connected over MXP

    private final SwerveModule m_frontLeftModule;
    private final SwerveModule m_frontRightModule;
    private final SwerveModule m_backLeftModule;
    private final SwerveModule m_backRightModule;
    private ChassisSpeeds m_chassisSpeeds = new ChassisSpeeds(0.0, 0.0, 0.0);
    SwerveDrivePoseEstimator m_PosEstimator;
    public static final double MAX_VELOCITY_METERS_PER_SECOND = 6380.0 / 60.0 *
                            (14.0 / 50.0) * (25.0 / 19.0) * (15.0 / 45.0) * 0.10033 * Math.PI;
    private double headingOffset = 0;

    public static final double MAX_VOLTAGE = 12.0;

    private final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
        // Front left
        new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0),
        // Front right
        new Translation2d(DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0),
        // Back left
        new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, DRIVETRAIN_WHEELBASE_METERS / 2.0),
        // Back right
        new Translation2d(-DRIVETRAIN_TRACKWIDTH_METERS / 2.0, -DRIVETRAIN_WHEELBASE_METERS / 2.0)
    );

    public Rotation2d getGyroscopeRotation() {    
        double yaww=pigeon.getYaw().getValueAsDouble();
        if(yaww<0)
            yaww+=360;
            yaww=360-yaww;
        return Rotation2d.fromDegrees(yaww-headingOffset);
    }

    public void setRotationalOffsetToCurrent(){
        headingOffset = getGyroscopeRotation().getDegrees();
    }

    public void resetIMU() {
        //TODO: set the yaw on the pigeon to 0
    }
      
    public DriveTrain(){
        m_frontLeftModule = new SwerveModule(SwerveModuleConfiguration.frontLeftConfig());
        m_frontRightModule = new SwerveModule(SwerveModuleConfiguration.frontRightConfig());
        m_backLeftModule  = new SwerveModule(SwerveModuleConfiguration.backLeftConfig());
        m_backRightModule = new SwerveModule(SwerveModuleConfiguration.backRightConfig());
        m_PosEstimator = new SwerveDrivePoseEstimator(m_kinematics, 
                                    getGyroscopeRotation(), getSwerveModulePositions(), new Pose2d());
    }

    public void drive (ChassisSpeeds chassisSpeeds){
        m_chassisSpeeds = chassisSpeeds;
        SwerveModuleState[] states = m_kinematics.toSwerveModuleStates(m_chassisSpeeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, MAX_VELOCITY_METERS_PER_SECOND);

        m_frontLeftModule.setDesiredState(states[2]);
        m_frontRightModule.setDesiredState(states[3]);
        m_backLeftModule.setDesiredState(states[0]);
        m_backRightModule.setDesiredState(states[1]);
    }

    public void moveFieldRelative(double speedMetersPerSecond, double angleInRadians) {
      var moduleState = new SwerveModuleState(speedMetersPerSecond, new Rotation2d(angleInRadians));
      m_frontLeftModule.setDesiredState(moduleState);
      m_frontRightModule.setDesiredState(moduleState);
      m_backRightModule.setDesiredState(moduleState);
      m_backLeftModule.setDesiredState(moduleState);
    }

    public void stop() {
        m_frontLeftModule.stop();
        m_frontRightModule.stop();
        m_backRightModule.stop();
        m_backLeftModule.stop();
    }

    public Pose2d getEstimatedPosition(){
        return m_PosEstimator.getEstimatedPosition();
    }

    @Override
    public void periodic() {
        writeWheelPositions();
    }

    public SwerveModulePosition[] getSwerveModulePositions() {
        SwerveModulePosition modulePositions[] =
        {
            new SwerveModulePosition(m_frontLeftModule.getWheelPosition(), Rotation2d.fromRadians(m_frontLeftModule.getAbsoluteAngle())),
            new SwerveModulePosition(m_frontRightModule.getWheelPosition(), Rotation2d.fromRadians(m_frontRightModule.getAbsoluteAngle())),
            new SwerveModulePosition(m_backLeftModule.getWheelPosition(), Rotation2d.fromRadians(m_backLeftModule.getAbsoluteAngle())),
            new SwerveModulePosition(m_backRightModule.getWheelPosition(), Rotation2d.fromRadians(m_backRightModule.getAbsoluteAngle()))
        };
        return modulePositions;
    }


    public void writeWheelPositions() {
        SmartDashboard.putNumber("Radish-FL",m_frontLeftModule.getAbsoluteAngle());
        SmartDashboard.putNumber("Radish-FR",m_frontRightModule.getAbsoluteAngle());
        SmartDashboard.putNumber("Radish-BL",m_backLeftModule.getAbsoluteAngle());
        SmartDashboard.putNumber("Radish-BR",m_backRightModule.getAbsoluteAngle());
    }
}