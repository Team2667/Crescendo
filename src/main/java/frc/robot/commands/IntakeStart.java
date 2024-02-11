package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class IntakeStart extends Command{
    private Intake intake;
    
    // This is the constructor for IntakeStart. Its purpose is
    // to assign values to the class fields. 
    public IntakeStart(Intake intakeParam) {
        //TODO: add intake as a requirement for this com.sun.media.sound
        // See https://github.wpilib.org/allwpilib/docs/beta/java/edu/wpi/first/wpilibj2/command/Command.html#addRequirements(edu.wpi.first.wpilibj2.command.Subsystem...) 
        intake=intakeParam;
    }

    // Depending on the purpose of a command, the initialize method is used for starting
    // things like motors. Have this implementation of intialize start the intake 
    // motor.
    @Override
    public void initialize() {
        // TODO: enable the forward limit switch
    
        intake.start();

    }

    @Override
    public boolean isFinished() {
        // TODO: Return true if the limit switch is engaged, false otherwise.
        return intake.isLimitSwitchEngaged();
    }

    // End methods do everything necessary to end a command. Typically this means
    // stopping all motors. Have this implementation on the end method stop the intake
    // motor. Note that the end method has a parameter called isInterrupted. You can
    // ignore it for this implementation.
    @Override
    public void end(boolean isInterrupted) {
        intake.stop();
        intake.limitSwitchTrickery();
    }
}
