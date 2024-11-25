package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class StopDriveTrainCommand extends Command{
    private DriveTrain driveTrain;

    public StopDriveTrainCommand(DriveTrain driveTrain){
        this.driveTrain = driveTrain;
    }

    @Override
    public void end(boolean isInterruppted) {
        driveTrain.stop();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
    
}
