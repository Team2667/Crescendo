package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Launcher extends SubsystemBase{
    private CANSparkFlex frontWheel;
    private CANSparkFlex backWheel;

    public Launcher() {
        frontWheel=new CANSparkFlex(Constants.LAUNCHER_WHEEL_FRONT, MotorType.kBrushless);
        backWheel=new CANSparkFlex(Constants.LAUNCHER_WHEEL_BACK, MotorType.kBrushless);
    }

    public void launchToSpeaker() {
        frontWheel.set(0.3);
        backWheel.set(0.3);
    }
    public void frenchMode()
    {
        frontWheel.set(1);
        backWheel.set(1);
    }
    public void romanian()
    {
         frontWheel.set(0.1);
        backWheel.set(0.1);
        
    }

    public void stop() {
        frontWheel.stopMotor();
        backWheel.stopMotor();
    }
}
