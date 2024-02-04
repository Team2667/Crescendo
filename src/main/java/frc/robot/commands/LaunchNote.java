package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Launcher;

public class LaunchNote extends Command {

    public LaunchNote(Launcher launcher) {
        // TODO: initialize a member variable to the parameter launcher
        // Add Launcher as a requirement for this command.
        // See https://github.wpilib.org/allwpilib/docs/beta/java/edu/wpi/first/wpilibj2/command/Command.html#addRequirements(edu.wpi.first.wpilibj2.command.Subsystem...) 
    }

    @Override
    public void initialize() {
        // Start the launcher
    }

    @Override
    public void end(boolean interrupted) {
        // TODO: stop the launcher
    }
    
}
