package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Launcher;

public class SpinUpLauncher extends Command {
    Launcher launcher;
    boolean StopAtSetpoint;
    double TopSpeed;
    double BottomSpeed;
    public SpinUpLauncher(Launcher launcher,double TopSpeed, double BottomSpeed, boolean StopAtSetpoint) {
        this.launcher=launcher;
        this.StopAtSetpoint=StopAtSetpoint;
        this.TopSpeed=TopSpeed;
        this.BottomSpeed=BottomSpeed;
        this.addRequirements(launcher);
        // TODO: initialize a member variable to the parameter launcher
        // Add Launcher as a requirement for this command.
        // See https://github.wpilib.org/allwpilib/docs/beta/java/edu/wpi/first/wpilibj2/command/Command.html#addRequirements(edu.wpi.first.wpilibj2.command.Subsystem...) 
    }

    @Override
    public void initialize() {
   //front is top
        //rear is bottom
        launcher.setSpeedPid(TopSpeed,BottomSpeed);
    }
//ethan likes thigh-high socks

    @Override
    public boolean isFinished()
    {

        if(!StopAtSetpoint)
            return false;
        double tolerance=130;
        return (launcher.getBackVelocity()>(launcher.rearspeed-tolerance)) && (launcher.getFrontVelocity()>=(launcher.frontspeed-tolerance));
    }
    @Override
    public void end(boolean interrupted) {
        launcher.stop();
        // TODO: stop the launcher
    }
    
}
