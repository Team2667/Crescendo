package frc.robot.commands;
import frc.robot.subsystems.Rumble;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;



public class Rumbly extends Command {
    Rumble rumble;
    public Rumbly(XboxController boxobees) {
        this.rumble=new Rumble(boxobees);
    }
    
    @Override
    public void initialize() {
        rumble.startRumble();
    }

    
    @Override
    public void end(boolean interrupted) {
        rumble.stopRumble();
    }
}