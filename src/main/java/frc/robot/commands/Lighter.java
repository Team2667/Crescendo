package frc.robot.commands;
import frc.robot.subsystems.Lights;
import edu.wpi.first.wpilibj2.command.Command;



public class Lighter extends Command {
    private final Lights lighter;
    private int thingy;

    public Lighter(Lights lighter, int thingy) {
        this.lighter=lighter;
        this.thingy=thingy;
    }
    

    @Override
    public void execute() {
        if (thingy==1) {
            lighter.paintItUrine();
        } else if (thingy==2) {
            lighter.paintItPurblePlace();
        }
    }

    
    @Override
    public void end(boolean interrupted) {
        lighter.paintItBlack();
    }
}