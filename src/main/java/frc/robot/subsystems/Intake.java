package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkLimitSwitch;
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
    //Am I the Harvard Science Center or what because my code is fire
    public void enableLimitSwitch() { //make the feed start sneeding(detecting limit switch in normal human terms)
        SparkLimitSwitch sneed = intakeMotor.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);
        sneed.enableLimitSwitch(true);
    }

    public void disableLimitSwitch(boolean b) { //make the feed crippled
        SparkLimitSwitch sneed = intakeMotor.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);
        sneed.enableLimitSwitch(false);
    }


    public boolean isLimitSwitchEngaged() { //Tell if pushed
        SparkLimitSwitch sneed = intakeMotor.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);
        return sneed.isPressed();
    }
}
