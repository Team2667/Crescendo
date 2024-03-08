package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arms extends SubsystemBase {
    private CANSparkMax left;
    private CANSparkMax right;

    public Arms(){
        left = new CANSparkMax(Constants.LEFT_ARM, MotorType.kBrushless);
        right = new CANSparkMax(Constants.RIGHT_ARM, MotorType.kBrushless);

        //TODO: One of the motors will need to be inverted
    }

    public void retract(){
        // TODO: make left and right motor spin in a direction that will pull the robot up.
    }

    public void reach(){
        // TODO: Make the left and right motors spin in a direction that will extend the arms
    }

    public void stop() {
        // TODO: Stop both arm motors
    }
    
}
