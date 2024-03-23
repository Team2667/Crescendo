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
        double speed=0.6;
        left.set(speed);
        right.set(speed);
        // TODO: make left and right motor spin in a diirection that will pull the robot up.
    }

    public void reach(){
        double speed=0.6;
        left.set(-speed);
        right.set(-speed);
        // TODO: Make the left and right motors spin in a direction that will extend the arms
    }
    public void reachLeft(double speed){
        left.set(-speed);
    }
    public void reachRight(double speed)
    {
        right.set(-speed);
    }
    public void retractLeft(double speed){
        left.set(speed);
    }
    public void retractRight(double speed)
    {
        right.set(speed);
    }
    public void stopLeft()
    {
        left.stopMotor();
    }
    public void stopRight()
    {
        right.stopMotor();
    }

    public void stop() {
        left.stopMotor();
        right.stopMotor();
        // TODO: Stop both arm motors
    }
    public double getLeftCurrent(){
        return left.getOutputCurrent();
    }
    public double getRightCurrent(){
        return right.getOutputCurrent();
    }
    
}
