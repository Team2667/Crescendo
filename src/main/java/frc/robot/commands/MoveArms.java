package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Arms;

public class MoveArms extends Command{
    private Arms arms;
    private CommandXboxController controller;

    public MoveArms(Arms arms, CommandXboxController controller) {
        this.arms = arms;
        this.controller = controller;
        // TODO: Add Arms as a requirement for this command
        // TODO: Make this command the default command for Arms
    }

    @Override
    public void execute() {
        // TODO: call controller.getPOV() This will return values for the dpad
        // If getPOV() returns -1, call arms.stop()
        // If getpov() returns 0 make arms reach
        // If getPOV() returns 180, make arms retract
    }
    
}
