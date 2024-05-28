package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdleConfiguration;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;

public class Rumble extends SubsystemBase{
    private XboxController boxobees;
    // This is the constructor for lights Its purpose is to inialize the class fields.
    public Rumble(XboxController boxobees) {
        this.boxobees=boxobees;
    }


    public void startRumble(){
       boxobees.setRumble(RumbleType.kBothRumble, 10);  
    }

    public void stopRumble(){
       boxobees.setRumble(RumbleType.kBothRumble, 0);  
    }

                                                                        //1=left, 2=right, 3=both
}