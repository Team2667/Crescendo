package frc.robot.subsystems.swerveSupport;

import frc.robot.Constants;

public class SwerveModuleConfiguration {
    public int steerMotorCanId;
    public int steerAbsoluteEncoderCanId;
    public double steeringOffsetInRadians;
    public int driveMotorCanId;
    public double revolutionsPerMeter;
    public double steerP = 0.5;
    public double steerI = 0.0;
    public double steerD = 0.0;
    public double driveP = 1;
    public double driveI = 0.0;
    public double driveD = 0.0;
    /*
    public double steerP = 1.0;
    public double steerI = 0.0;
    public double steerD = 0.1;
    public double driveP = 1.0;
    public double driveI = 0.0;
    public double driveD = 0.1;
       */
    public double steerReduction = (14.0 / 50.0) * (10.0 / 60.0);
    public String label;
    public boolean driveInverted = false;
    public double wheelDiameter = 0.10033;
    public double driveReduction = (14.0 / 50.0) * (25.0 / 19.0) * (15.0 / 45.0);

    public static SwerveModuleConfiguration frontLeftConfig() {
        var moduleConfig = new SwerveModuleConfiguration();
        moduleConfig.steerMotorCanId = Constants.FRONT_LEFT_MODULE_STEER_MOTOR;
        moduleConfig.steerAbsoluteEncoderCanId = Constants.FRONT_LEFT_MODULE_STEER_ENCODER;
        moduleConfig.steeringOffsetInRadians = Constants.FRONT_LEFT_MODULE_STEER_OFFSET;
        moduleConfig.driveMotorCanId = Constants.FRONT_LEFT_MODULE_DRIVE_MOTOR;
        moduleConfig.revolutionsPerMeter = Constants.WHEEL_REVOLUTIONS_PER_METER;
        moduleConfig.label = "Front Left";

        return moduleConfig;
    }

    public static SwerveModuleConfiguration frontRightConfig() {
        var moduleConfig = new SwerveModuleConfiguration();
        moduleConfig.steerMotorCanId = Constants.FRONT_RIGHT_MODULE_STEER_MOTOR;
        moduleConfig.steerAbsoluteEncoderCanId = Constants.FRONT_RIGHT_MODULE_STEER_ENCODER;
        moduleConfig.steeringOffsetInRadians = Constants.FRONT_RIGHT_MODULE_STEER_OFFSET;
        moduleConfig.driveMotorCanId = Constants.FRONT_RIGHT_MODULE_DRIVE_MOTOR;
        moduleConfig.revolutionsPerMeter = Constants.WHEEL_REVOLUTIONS_PER_METER;
        moduleConfig.label = "Front Right";

        return moduleConfig;
    }

    public static SwerveModuleConfiguration backRightConfig() {
        var moduleConfig = new SwerveModuleConfiguration();
        moduleConfig.steerMotorCanId = Constants.BACK_RIGHT_MODULE_STEER_MOTOR;
        moduleConfig.steerAbsoluteEncoderCanId = Constants.BACK_RIGHT_MODULE_STEER_ENCODER;
        moduleConfig.steeringOffsetInRadians = Constants.BACK_RIGHT_MODULE_STEER_OFFSET;
        moduleConfig.driveMotorCanId = Constants.BACK_RIGHT_MODULE_DRIVE_MOTOR;
        moduleConfig.revolutionsPerMeter = Constants.WHEEL_REVOLUTIONS_PER_METER;
        moduleConfig.label = "Back Right";
        moduleConfig.driveInverted=true;
        return moduleConfig;
    }

    public static SwerveModuleConfiguration backLeftConfig() {
        var moduleConfig = new SwerveModuleConfiguration();
        moduleConfig.steerMotorCanId = Constants.BACK_LEFT_MODULE_STEER_MOTOR;
        moduleConfig.steerAbsoluteEncoderCanId = Constants.BACK_LEFT_MODULE_STEER_ENCODER;
        moduleConfig.steeringOffsetInRadians = Constants.BACK_LEFT_MODULE_STEER_OFFSET;
        moduleConfig.driveMotorCanId = Constants.BACK_LEFT_MODULE_DRIVE_MOTOR;
        moduleConfig.revolutionsPerMeter = Constants.WHEEL_REVOLUTIONS_PER_METER;
        moduleConfig.label = "Back Left";

        return moduleConfig;
    }
}
