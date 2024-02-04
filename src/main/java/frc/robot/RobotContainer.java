// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.Lighter;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.DriveFieldRelative;
import frc.robot.commands.FeedNoteToLauncher;
import frc.robot.commands.IndicateNote;
import frc.robot.commands.IntakeStart;
import frc.robot.commands.LaunchNote;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final Intake intake= new Intake();
  // private final IntakeStart intakestart = new IntakeStart(intake);
  DriveFieldRelative forwardCommand;
  DriveFieldRelative leftCommand;
  DriveFieldRelative backCommand;
  DriveFieldRelative rightCommand;
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private DriveTrain drivetrain;
  private Lights candle;
  private Lighter redcandle;
  private Lighter bluecandle;

  private Intake intake;
  private IntakeStart intakestart;
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);
  private final XboxController m_controller = new XboxController(0);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the trigger bindings
    configureDriveTrainBindings(Constants.DISABLE_DRIVETRAIN);
    configureIntakeBindings(Constants.DISABLE_INTAKE);
    configureCandleBindings(Constants.DISABLE_CANDLE || Constants.DISABLE_INTAKE);
    configureLauncherBindings(Constants.DISABLE_LAUNCHER);
    configureCompoundCommands(Constants.DISABLE_INTAKE || Constants.DISABLE_LAUNCHER);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private static double deadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  private double modifyAxis(double input) {
    input = deadband(input, 0.05);
    // Square the axis
    return Math.copySign(input * input, input);
  }

  private void configureIntakeBindings(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled the Intake system.");
    } else {
      //TODO: Create a member variable for a FeedNoteToLauncher command. Initialize that member variable variable to a new instance of FeedNoteToLauncher.
      this.intake = new Intake();
      this.intakestart = new IntakeStart(intake);
      m_driverController.leftBumper().toggleOnTrue(intakestart);
    }
  }

  private void configureLauncherBindings(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled the launcher subsystem");
      return;
    }
    // TODO: create a member variable for the Launcher subsystem. Initialize that member variable to a new instance of Launcher
    // TODO: Create a member variable for the LaunchNote command. Initialize that member variable to a new instance of LaunchNote
  }

  private void configureDriveTrainBindings(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled the DriveTrain system.");
    } else {
      drivetrain = new DriveTrain();
      drivetrain.setDefaultCommand(new DefaultDriveCommand(drivetrain,
          () -> -modifyAxis(m_controller.getLeftY()),
          () -> modifyAxis(m_controller.getLeftX()),
          () -> -modifyAxis(m_controller.getRightX())));
      forwardCommand = new DriveFieldRelative(drivetrain, 0, .5);
      backCommand = new DriveFieldRelative(drivetrain, Math.PI, .5);
      leftCommand = new DriveFieldRelative(drivetrain, (2 * Math.PI * 3) / 4, .5);
      rightCommand = new DriveFieldRelative(drivetrain, Math.PI / 2, .5);

      JoystickButton forwardCommandButton = new JoystickButton(m_controller, XboxController.Button.kY.value);
      forwardCommandButton.whileTrue(forwardCommand);
      JoystickButton leftCommandButton = new JoystickButton(m_controller, XboxController.Button.kX.value);
      leftCommandButton.whileTrue(leftCommand);
      JoystickButton downCommandButton = new JoystickButton(m_controller, XboxController.Button.kA.value);
      downCommandButton.whileTrue(backCommand);
      JoystickButton rightCommandButton = new JoystickButton(m_controller, XboxController.Button.kB.value);
      rightCommandButton.whileTrue(rightCommand);
    }
  }

  private void configureCandleBindings(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled the CANdle system.");
    } else {
      this.candle = new Lights();
      this.redcandle = new Lighter(candle, 1);
      this.bluecandle = new Lighter(candle, 2);
      m_driverController.a().whileTrue(redcandle);
      m_driverController.b().whileTrue(bluecandle);
      //TODO: create a mber variable for IndicateNote and assign it to a new instance of IndicateNote. The second parameter
      // Will be ()-> intake.isLimitSwitchEngaged()      
    }
  }

  private void configureCompoundCommands(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled compound commands");
    }
    // TODO: Bind a command to the right bumper that:
    // 1. Runs LaunchNote for .5 secons.
    // 2. Runs FeedNoteToLauncher and LaunchNote togeter for 2 secons
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    Command driveBackward = new DriveFieldRelative(drivetrain, 0, .5);
    return driveBackward.withTimeout(3.0);
  }
}
