package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Launcher extends SubsystemBase{
    private CANSparkFlex topWheel;
    private CANSparkFlex bottomWheel;

    public Launcher() {
        // TODO initialize topWheel and bottomWheel
    }

    public void launchToSpeaker() {
        // TODO: Start the top and bottom moters at a speed to fire the
        // note into the speaker
    }

    public void stop() {
        // TODO: stop the motors
    }
}
