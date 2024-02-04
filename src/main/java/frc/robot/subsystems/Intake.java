package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;


public class Intake extends SubsystemBase{
    private CANSparkMax intakeMotor;

    // This is the constructor for the Intake subsystem. Its purpose is to initialize
    // class fields which will be used by other methods in this class.
    //        
    public Intake(){
        intakeMotor= new CANSparkMax(Constants.INTAKE_MOTOR_CAN_ID, MotorType.kBrushless);
        intakeMotor.setInverted(true);
        
    }
    public void start() {
        intakeMotor.set(Constants.INTAKE_MOTOR_SPEED);
    }

    public void stop() {
        intakeMotor.stopMotor();
    }

    public void enableLimitSwitch() {
        // TODO: turn the limit switch on for the intake motor. 
        // See https://codedocs.revrobotics.com/java/com/revrobotics/cansparkbase#getForwardLimitSwitch(com.revrobotics.SparkLimitSwitch.Type)
        //
    }

    public void disableLimitSwitch() {
        // TODO: turn the limit switch on for the intake motor. 
        // See https://codedocs.revrobotics.com/java/com/revrobotics/cansparkbase#getForwardLimitSwitch(com.revrobotics.SparkLimitSwitch.Type)
        //
    }

    public boolean isLimitSwitchEngaged() {
        // TODO: Return true if the limit switch is turned on, false otherwise
        // See https://codedocs.revrobotics.com/java/com/revrobotics/cansparkbase#getForwardLimitSwitch(com.revrobotics.SparkLimitSwitch.Type)
        return false;
    }
}
