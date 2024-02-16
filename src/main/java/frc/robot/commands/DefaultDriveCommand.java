package frc.robot.commands;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.DoubleSupplier;

public class DefaultDriveCommand extends Command {
    private final DriveTrain m_drivetrainSubsystem;

    private final DoubleSupplier m_translationXSupplier;
    private final DoubleSupplier m_translationYSupplier;
    private final DoubleSupplier m_rotationSupplier;

    private SlewRateLimiter xSRL;
    private SlewRateLimiter ySRL;

    public DefaultDriveCommand(DriveTrain drivetrainSubsystem,
                               DoubleSupplier translationXSupplier,
                               DoubleSupplier translationYSupplier,
                               DoubleSupplier rotationSupplier
                               ) {
        this.m_drivetrainSubsystem = drivetrainSubsystem;
        this.m_translationXSupplier = translationXSupplier;
        this.m_translationYSupplier = translationYSupplier;
        this.m_rotationSupplier = rotationSupplier;
        this.xSRL=new SlewRateLimiter(1);
        this.ySRL=new SlewRateLimiter(1);
        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void execute() {
        m_drivetrainSubsystem.drive(
                 ChassisSpeeds.fromFieldRelativeSpeeds(
                         xSRL.calculate(m_translationXSupplier.getAsDouble()),
                         ySRL.calculate(m_translationYSupplier.getAsDouble()),
                         -1 * m_rotationSupplier.getAsDouble(),
                         m_drivetrainSubsystem.getGyroscopeRotation()
                 )
         );
         
         
    }

    @Override
    public void end(boolean interrupted) {
        m_drivetrainSubsystem.stop();
    }
    @Override
    public boolean isFinished()
    {
        return false;
    }
}