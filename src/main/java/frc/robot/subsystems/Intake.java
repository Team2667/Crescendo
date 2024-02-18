package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkLimitSwitch;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;


public class Intake extends SubsystemBase{
    private CANSparkMax intakeMotor;
    private boolean hasnote=false;

    // This is the constructor for the Intake subsystem. Its purpose is to initialize
    // class fields which will be used by other methods in this class.
    //
    public Intake(){
        intakeMotor= new CANSparkMax(Constants.INTAKE_MOTOR_CAN_ID, MotorType.kBrushless);        
    }
    public void start(double speed) {
        intakeMotor.set(-speed);
    }
    public void startReverse() {
        intakeMotor.set(0.5);
    }
    

    public void stop() {
        intakeMotor.stopMotor();
    }

   

    
    //Am I the Harvard Science Center or what because my code is fire
    public void enableLimitSwitch() { //make the feed start sneeding(detecting limit switch in normal human terms)
        SparkLimitSwitch sneed = intakeMotor.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);
        sneed.enableLimitSwitch(true);
    }

    public void disableLimitSwitch() { //make the feed crippled
        SparkLimitSwitch sneed = intakeMotor.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);
        sneed.enableLimitSwitch(false);
    }


    public boolean isLimitSwitchEngaged() { //Tell if pushed
        SparkLimitSwitch sneed = intakeMotor.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);
        return sneed.isPressed();
    }
}
