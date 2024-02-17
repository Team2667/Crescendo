package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkBase;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Launcher extends SubsystemBase{
    private CANSparkFlex frontWheel;
    private CANSparkFlex backWheel;
    private RelativeEncoder frontEncoder;
    private RelativeEncoder backEncoder;
    private SparkPIDController frontPID;
    private SparkPIDController rearPID;
    private double pV = 4e-1;
	private double iV = 0;
    private double dV = 0;
    private double rearspeed;
    private double frontspeed;

    public Launcher() {
        frontWheel=new CANSparkFlex(Constants.LAUNCHER_WHEEL_FRONT, MotorType.kBrushless);
        backWheel=new CANSparkFlex(Constants.LAUNCHER_WHEEL_BACK, MotorType.kBrushless);
        frontEncoder=frontWheel.getEncoder();
        backEncoder=backWheel.getEncoder();
        frontPID=frontWheel.getPIDController();
        rearPID=backWheel.getPIDController();
        frontPID.setP(pV);
        frontPID.setI(iV);
        frontPID.setD(dV);

        rearPID.setP(pV);
        rearPID.setI(iV);
        rearPID.setD(dV);
        

    }

    public void setSpeedPid(double front, double rear)
    {
        frontPID.setReference(front, CANSparkBase.ControlType.kVelocity);
        rearPID.setReference(rear, CANSparkBase.ControlType.kVelocity );

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
    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("front wheel",frontEncoder.getVelocity());
        SmartDashboard.putNumber("rear wheel",backEncoder.getVelocity());

    }
}
