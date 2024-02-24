package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Launcher;

public class LaunchNote extends Command {
    Launcher launcher;
    public LaunchNote(Launcher launcher) {
        this.launcher=launcher;
        this.addRequirements(launcher);
        // TODO: initialize a member variable to the parameter launcher
        // Add Launcher as a requirement for this command.
        // See https://github.wpilib.org/allwpilib/docs/beta/java/edu/wpi/first/wpilibj2/command/Command.html#addRequirements(edu.wpi.first.wpilibj2.command.Subsystem...) 
    }

    @Override
    public void initialize() {

        launcher.launchToSpeaker();
    }

    @Override
    public boolean isFinished()
    {
        //double tolerance=150;
        //return (launcher.getBackVelocity()>(launcher.rearspeed-tolerance)) && (launcher.getFrontVelocity()>=(launcher.frontspeed-tolerance));
        return false;
    }
    @Override
    public void end(boolean interrupted) {
        launcher.stop();
        // TODO: stop the launcher
    }
    
}
