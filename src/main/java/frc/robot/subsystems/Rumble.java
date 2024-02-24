package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdleConfiguration;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Rumble extends SubsystemBase{
    private XboxController boxobees;
    // This is the constructor for lights Its purpose is to inialize the class fields.
    public Rumble(XboxController boxobees) {
        this.boxobees=boxobees;
    }
                                                                        //1=left, 2=right, 3=both
    public void wibblewobble(float strength, int which){
        switch (which) {
            case 1:
                boxobees.setRumble(RumbleType.kLeftRumble, strength);
                break;
            case 2:
                boxobees.setRumble(RumbleType.kRightRumble, strength);
                break;
            case 3:
                boxobees.setRumble(RumbleType.kBothRumble, strength);
                break;    
            default:
                System.out.println("The which variable in wibble wobble is only 1 to 3, doofus.");
                System.out.println("In my magnanity I will allow it to function.");
                System.out.println("You are permitted to be grateful."); //aren't i so nice
                break;
        }
    }
}