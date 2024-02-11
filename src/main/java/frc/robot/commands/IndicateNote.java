package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lights;

public class IndicateNote extends Command {
    BooleanFunc isNoteCaptured;
    Lights lights;


    public IndicateNote(Lights lights, BooleanFunc isNoteCaptured) {
        this.lights=lights;
        this.isNoteCaptured=isNoteCaptured;
        this.addRequirements(lights);
        lights.setDefaultCommand(this);
    }

    @Override
    public void execute() {
        if (isNoteCaptured.test()) {
            lights.paintItUrine();
        } else {
            lights.paintItBlack();
        }
    }
    
    @Override
    public void end(boolean interrupted) {

    }
}
