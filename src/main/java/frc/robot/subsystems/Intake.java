package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;


public class Intake extends SubsystemBase{
    private CANSparkMax intakeMotor;

    // This is the constructor for the Intake subsystem. Its purpose is to initialize
    // class fields which will be used by other methods in this class.
    //        
    public Intake(){

        // assign intakeMotor to a new CANSparkMax. 
        // see https://github.com/Team2667/kit-bot-2024/blob/master/src/main/java/frc/robot/subsystems/CANLauncher.java#L20
        // Note: A constant for the Intake can ID has already been created.
        //  Type "Constants." and you should see a popup menu that will list several
        // constants. Slect the obvous one.
    }

    //
    // Start the motor running. The motor speed is determined by the a the constant
    // INTAKE_MOTOR_SPEED.
    //
    public void start() {
        // Set the speed on the class field intakeMotor.
        // See example: https://github.com/Team2667/kit-bot-2024/blob/master/src/main/java/frc/robot/subsystems/CANLauncher.java#L51

    }

    public void stop() {
        // Call stop on the class field intakeMotor.
    }
}
