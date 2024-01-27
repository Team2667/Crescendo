package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class DriveFieldRelative extends Command {
    DriveTrain driveTrain;
    double angle, magnitude;
    public DriveFieldRelative(DriveTrain driveTrain, double angle, double magnitude) {
        this.driveTrain=driveTrain;
        this.angle=angle;
        this.magnitude=magnitude;
        addRequirements(this.driveTrain);
    }

    @Override
    public void execute () {
        driveTrain.moveFieldRelative(magnitude, angle);
    }

    @Override
    public void end (boolean IsInterrupt) {
        driveTrain.stop();
    }
}