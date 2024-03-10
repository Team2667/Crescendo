package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arms;

public class MoveArms extends Command{
    private Arms arms;
    private XboxController controller;

    public MoveArms(Arms arms, XboxController controller) {
        addRequirements(arms);
        this.arms = arms;
        this.controller = controller;
        // TODO: Make this command the default command for Arms
    }

    @Override
    public void execute() {
        int pov=controller.getPOV();
        switch (pov) { //switch case my beloved
            case -1:
                arms.stop();
                break;
            case 0:
                arms.reach();
                break;
            case 180:
                arms.retract();
                break;
            default:
                break;
        }
    }
    
}
