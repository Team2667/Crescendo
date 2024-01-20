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
        
    }
    public void start() {
        intakeMotor.set(Constants.INTAKE_MOTOR_SPEED);
    }

    public void stop() {
        intakeMotor.stopMotor();
    }
}
