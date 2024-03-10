package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class ResetIMU extends Command {
    private DriveTrain driveTrain;

    public ResetIMU(DriveTrain driveTrain) {
        addRequirements(driveTrain);
        this.driveTrain=driveTrain;
    }

    @Override
    public void initialize(){
        driveTrain.resetIMU();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
