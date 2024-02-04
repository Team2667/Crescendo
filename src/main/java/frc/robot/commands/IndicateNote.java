package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lights;

public class IndicateNote extends Command {
    BooleanFunc isNoteCaptured;


    public IndicateNote(Lights lights, BooleanFunc isNoteCaptured) {
        // TODO: Add lights as a requirement for this command.
        // TODO: make this the default command for lights.
        // Create a member variable of type Lights and assign it the value of the lights parameter

    }

    @Override
    public void execute() {
        // isNoteCaptured.test() is true, turn lights on, otherwise turn lights off
    }
    
    @Override
    public void end(boolean interrupted) {

    }
}
