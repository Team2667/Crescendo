package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class FeedNoteToLauncher extends Command{
    Intake intake;
    public FeedNoteToLauncher(Intake intake) {
        this.intake=intake;
        this.addRequirements(intake);
        //TODO: Initialize a member variable to intake
        //TODO: Add intake as a requirement for this command
        // See https://github.wpilib.org/allwpilib/docs/beta/java/edu/wpi/first/wpilibj2/command/Command.html#addRequirements(edu.wpi.first.wpilibj2.command.Subsystem...) 
    }

    @Override
    public void initialize()
    {
        intake.disableLimitSwitch();
        intake.start(Constants.INTAKE_MOTOR_SPEED);
    }

    @Override
    public void end(boolean isFinished)
    {
        intake.enableLimitSwitch();
        intake.stop();
    }
    public boolean isFinished()
    {
        return !intake.isLimitSwitchEngaged();
    }    
}
