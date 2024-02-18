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
    private double pV = 0.0008;
	private double iV = 0.0000004;
    private double dV = 0;
    public double rearspeed=1960;
    public double frontspeed=1960;

    private boolean firstcycle=false;

    public Launcher() {
        frontWheel=new CANSparkFlex(Constants.LAUNCHER_WHEEL_FRONT, MotorType.kBrushless);
        backWheel=new CANSparkFlex(Constants.LAUNCHER_WHEEL_BACK, MotorType.kBrushless);
        frontEncoder=frontWheel.getEncoder();
        backEncoder=backWheel.getEncoder();
        frontPID=frontWheel.getPIDController();
        rearPID=backWheel.getPIDController();

    }
    public void setPIDvals()
    {
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
        setSpeedPid(frontspeed, rearspeed);

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

    public double getFrontVelocity()
    {
        return frontEncoder.getVelocity();
    }
    public double getBackVelocity()
    {
        return backEncoder.getVelocity();
    }
    public void stop() {
        frontWheel.stopMotor();
        backWheel.stopMotor();
    }
    @Override
    public void periodic()
    {
        if(Constants.debuggymodey)
        {

            double sp=SmartDashboard.getNumber("pV",pV);
            double si=SmartDashboard.getNumber("iV", iV);
            double sd=SmartDashboard.getNumber("dV", dV);
            double vfront=SmartDashboard.getNumber("front velocity", frontspeed);
            double vrear=SmartDashboard.getNumber("rear velocity", rearspeed);


            if(sp!=pV || si!=iV || sd!=dV || vfront!=frontspeed || vrear!=rearspeed)
            {
                pV=sp;
                iV=si;
                dV=sd;
                rearspeed=vrear;
                frontspeed=vfront;
                setPIDvals();
            }
            SmartDashboard.putNumber("pV", pV);
            SmartDashboard.putNumber("iV", iV);
            SmartDashboard.putNumber("dV", dV);
            SmartDashboard.putNumber("front velocity", frontspeed);
            SmartDashboard.putNumber("rear velocity", rearspeed);
            SmartDashboard.putNumber("front velocity real", frontEncoder.getVelocity());
            SmartDashboard.putNumber("rear velocity real", backEncoder.getVelocity());



        }
    }
}
