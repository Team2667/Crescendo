package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Arms;

public class MoveArmsUntilResistance extends Command{
    private Arms arms;
    private XboxController controller;
    private boolean leftStopped=false;
    private boolean rightStopped=false;
    private boolean afteronesecond=true;

    public MoveArmsUntilResistance(Arms arms, XboxController controller) {
        addRequirements(arms);
        this.arms = arms;
        this.controller = controller;
    }


    @Override
    public void initialize()
    {
        arms.retractLeft(0.1);
        arms.retractRight(0.1);
    }
    @Override
    public void execute() {
        if(arms.getLeftCurrent()>Constants.ArmStowStopCurrent && afteronesecond)
        {
            arms.stopLeft();
            leftStopped=true;
        }
        if(arms.getRightCurrent()>Constants.ArmStowStopCurrent && afteronesecond)
        {
            arms.stopRight();
            rightStopped=true;
        }
    }
    @Override
    public void end(boolean interrupted)
    {
        if(interrupted)
            afteronesecond=true;
    }
    @Override
    public boolean isFinished()
    {
        return leftStopped && rightStopped;
    }
    
}
