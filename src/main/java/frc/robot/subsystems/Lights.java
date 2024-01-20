package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdleConfiguration;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lights extends SubsystemBase{
    private CANdle candle;
    private CANdleConfiguration lightsOnConfig = getLightsOnConfig();
    private CANdleConfiguration lightsOffConfig = getLightsOffConfig();
    
    
    // This is the constructor for lights Its purpose is to inialize the class fields.
    public Lights() {
        candle=new CANdle(Constants.LIGHT_ID);
        candle.setLEDs(0,0,0,0,Constants.LIGHT_ID,1);
        // initialize the candle class field
        // You will need to add a valuie to constants
    }

    public void paintItBlack(){
        candle.setLEDs(0,0,0);
    }

    public void paintItRed(){
        candle.setLEDs(255,0,0);
        // Step 1: Use the led method on the candle class variable to turn the lights red.
        // You can use this color picker to determine what values to set. https://rgbcolorpicker.com/

        // Step 2: call the configAllSettings method and pass it the lightsOnConfig.

        // Please note: noone on team 2667 has worked with the CANdle. This may require some trial and error.
    }

    public void paintItBlue() {
        candle.setLEDs(0,0,255);
        // Use the led metod on the candle class variable to turn the lights blue.

         // Step 2: call the configAllSettings method and pass it the lightsOnConfig.
        
    }

    private static CANdleConfiguration getLightsOnConfig() {
         CANdleConfiguration config = new CANdleConfiguration();
         config.stripType = LEDStripType.RGB; // set the strip type to RGB
         config.brightnessScalar = 1.0; // dim the LEDs to half brightness
         return config;
    }

    private static CANdleConfiguration getLightsOffConfig() {
         CANdleConfiguration config = new CANdleConfiguration();
         config.stripType = LEDStripType.RGB; // set the strip type to RGB
         config.brightnessScalar = 1.0; // dim the LEDs to half brightness
         return config;
    }

}
