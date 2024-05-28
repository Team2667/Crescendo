package frc.robot.subsystems.swerveSupport;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants;

public class SwerveModule {

    private RelativeEncoderResetTracker relativeEncoderTracker = new RelativeEncoderResetTracker();
    private CANcoder absoluteSteerEncoder;
    private CANSparkMax steerMotor;
    private CANSparkFlex driveMotor;
    private RelativeEncoder steerRelativeEncoder;
    private SwerveModuleConfiguration cfg;
    private static final double MAX_VOLTAGE = 12.0;

    public SwerveModule(SwerveModuleConfiguration config) {
        cfg = config;
        absoluteSteerEncoder = createAbsoluteCanEncoder(cfg.steerAbsoluteEncoderCanId, cfg.steeringOffsetInRadians);
        steerMotor = new CANSparkMax(cfg.steerMotorCanId, MotorType.kBrushless);
        setPIDValues(steerMotor, config.steerP, config.steerI, config.steerD);
        driveMotor = new CANSparkFlex(cfg.driveMotorCanId, MotorType.kBrushless);
        setPIDValues(driveMotor, cfg.driveP, cfg.driveI, cfg.driveD);
        driveMotor.setInverted(config.driveInverted);
        steerRelativeEncoder = steerMotor.getEncoder();
        double temp_PosConvFactor=2.0 * Math.PI * cfg.steerReduction;//A silly code golf remove if code is more broken than is expected
        steerRelativeEncoder.setPositionConversionFactor(temp_PosConvFactor);
        steerRelativeEncoder.setVelocityConversionFactor(temp_PosConvFactor / 60.0);
        resetSteerRelativeEncoder();

        RelativeEncoder driveEncoder = driveMotor.getEncoder();
        double positionConversionFactor = Math.PI * cfg.wheelDiameter * cfg.driveReduction;
        driveEncoder.setPositionConversionFactor(0.04691941901);
        driveEncoder.setVelocityConversionFactor(positionConversionFactor / 60.0);
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        // Optimize the reference state to avoid spinning further than 90 degrees
        if (relativeEncoderTracker.isTimeToResetRelativeEncoder(steerRelativeEncoder.getVelocity())){
            resetSteerRelativeEncoder();
        }

        SwerveModuleState state = SwerveModuleState.optimize(desiredState, new Rotation2d(steerMotor.getEncoder().getPosition()));
        if (Math.abs(state.speedMetersPerSecond) < 0.01) {
            stop();
            return;
        }
        setDriveVelocity(state.speedMetersPerSecond);
        setReferenceAngle(state.angle.getRadians());
    }

    public void stop(){
        driveMotor.set(0);
        steerMotor.set(0);
    }
      
    public double getAbsoluteAngle() {
        double angle = (absoluteSteerEncoder.getAbsolutePosition().getValueAsDouble())*2*Math.PI;
       // angle %= 2.0 * Math.PI;
       // if (angle < 0.0) {
       //     angle += 2.0 * Math.PI;
       // }
        return angle;
    }

    public void outputSteerAnglesToDashboard(){
        SmartDashboard.putNumber(getSteerLogLabel("Relative Encoder"), steerMotor.getEncoder().getPosition());
        SmartDashboard.putNumber(getSteerLogLabel("Absolute Encoder"), getAbsoluteAngle());
    }

    public double getWheelPosition(){
        return driveMotor.getEncoder().getPosition();
    }

    private void setPIDValues(CANSparkMax motor, double proportional, double integral, double derivative) {
        var pidController = motor.getPIDController();
        pidController.setP(proportional);
        pidController.setI(integral);
        pidController.setD(derivative);
    }

    private void setPIDValues(CANSparkFlex motor, double proportional, double integral, double derivative) {
        var pidController = motor.getPIDController();
        pidController.setP(proportional);
        pidController.setI(integral);
        pidController.setD(derivative);
    }
    

    private void setReferenceAngle(double referenceAngleRadians) {
        steerMotor.getPIDController().setReference(referenceAngleRadians, CANSparkMax.ControlType.kPosition);
    }

    private void setDriveVelocity(double metersPerSecond) {
        var voltage = (metersPerSecond / Constants.MAX_INPUT_SPEED * MAX_VOLTAGE) * (Constants.PERCENTAGE_MAX_SPEED / 100);
        SmartDashboard.putNumber(getVelocityLabel("Voltage"), voltage);
        driveMotor.setVoltage(voltage);
     }
     private void ResetEncoder()
     {
     }
     private void setReferencePosition(double meters) {
        driveMotor.getPIDController().setReference(meters, CANSparkMax.ControlType.kPosition);
     }

    private void resetSteerRelativeEncoder() {
        steerRelativeEncoder.setPosition(getAbsoluteAngle());
    }

    private CANcoder createAbsoluteCanEncoder(int canId, double offset) {
        CANcoderConfiguration config = new CANcoderConfiguration();
        config.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;
        config.MagnetSensor.MagnetOffset = offset;
        config.MagnetSensor.SensorDirection = SensorDirectionValue.Clockwise_Positive;

        CANcoder encoder = new CANcoder(canId);
        
        encoder.getConfigurator().apply(config);
        return encoder;
    }

    // Reset the NEO's encoder periodically when the module is not rotating.
    // Sometimes (~5% of the time) when we initialize, the absolute encoder isn't fully set up, and we don't
    // end up getting a good reading. If we reset periodically this won't matter anymore.
    // This method tells us if its time to reset the relative encoder.
    private class RelativeEncoderResetTracker {
        private final int ENCODER_RESET_ITERATIONS = 500;
        private final double ENCODER_RESET_MAX_ANGULAR_VELOCITY = Math.toRadians(0.5);
        private int resetIteration = 0;

        public boolean isTimeToResetRelativeEncoder(double turingVelocity) {
            if (turingVelocity < ENCODER_RESET_MAX_ANGULAR_VELOCITY) {
                resetIteration += 1;
                if (resetIteration >= ENCODER_RESET_ITERATIONS) {
                    resetIteration = 0;
                    return true;
                }
            } else {
                resetIteration = 0;
            }
            return false;
        }
    }

    private String getSteerLogLabel(String propertyName) {
       return cfg.label + "-Steer-" + propertyName;
    }

    private String getVelocityLabel(String propertyName) {
        return cfg.label + "-Velocity-" + propertyName;
     }
}