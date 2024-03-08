package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class ResetIMU extends Command {
    private DriveTrain driveTrain;

    public ResetIMU(DriveTrain driveTrain) {
        // TODO: initialize member var driveTrain to parameter driveTrain
        // TODO: add drive train as arequirement
    }

    @Override
    public void initialize(){
        // TODO: call resetIMU on driveTrain
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
