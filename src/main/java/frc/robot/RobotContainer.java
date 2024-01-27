// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.Lighter;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.DriveFieldRelative;
import frc.robot.commands.IntakeStart;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //private final Intake intake= new Intake();    
  //private final IntakeStart intakestart = new IntakeStart(intake);
 DriveFieldRelative forwardCommand;
    DriveFieldRelative leftCommand;
    DriveFieldRelative backCommand;
    DriveFieldRelative rightCommand;
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private DriveTrain drivetrain;
  private Lights candle;    
  private Lighter redcandle ;
  private Lighter bluecandle;

  private Intake intake;   
  private IntakeStart intakestart; 
    // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final XboxController m_controller=new XboxController(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_DriveTrain=new DriveTrain();
    m_DriveTrain.setDefaultCommand(new DefaultDriveCommand(m_DriveTrain,
    () -> -modifyAxis(m_controller.getLeftY()),
    () -> modifyAxis(m_controller.getLeftX()),
    () -> -modifyAxis(m_controller.getRightX())
  ));
    // Configure the trigger bindings
    configureDriveTrainBindings(Constants.DISABLE_DRIVETRAIN);
    configureIntakeBindings(Constants.DISABLE_INTAKE);
    configureCandleBindings(Constants.DISABLE_CANDLE);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
    private static double deadband(double value,double deadband) {
    if (Math.abs(value)>deadband) {
      if (value>0.0) {
        return (value-deadband)/(1.0-deadband);
      } else {
        return (value+deadband)/(1.0-deadband);
      }
    } else {
      return 0.0;
    }
}
  private double modifyAxis(double input)
    {
      input=deadband(input,0.05);
      // Square the axis
      return Math.copySign(input * input, input);
    }

  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
  //  new Trigger(m_exampleSubsystem::exampleCondition)
  //      .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
   // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
   //m_driverController.a().toggleOnTrue(intakestart);
   forwardCommand=new DriveFieldRelative(m_DriveTrain, 0, 1);
   backCommand =new DriveFieldRelative(m_DriveTrain, Math.PI, 1);
   leftCommand = new DriveFieldRelative(m_DriveTrain, (2 * Math.PI * 3)/4, 1);
   rightCommand =new DriveFieldRelative(m_DriveTrain, Math.PI / 2, 1);
         JoystickButton forwardCommandButton=new JoystickButton(m_controller, XboxController.Button.kY.value);
      forwardCommandButton.whileTrue(forwardCommand);
      JoystickButton leftCommandButton=new JoystickButton(m_controller, XboxController.Button.kX.value);
      leftCommandButton.whileTrue(leftCommand);
      JoystickButton downCommandButton=new JoystickButton(m_controller, XboxController.Button.kA.value);
      downCommandButton.whileTrue(backCommand);
      JoystickButton rightCommandButton=new JoystickButton(m_controller, XboxController.Button.kB.value);
      rightCommandButton.whileTrue(rightCommand);
  private void configureIntakeBindings(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled the Intake system.");
    } else {
        this.intake = new Intake(); 
        this.intakestart = new IntakeStart(intake);
        m_driverController.x().toggleOnTrue(intakestart);
    }
  }
  private void configureDriveTrainBindings(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled the DriveTrain system.");
    } else {
      this.drivetrain= new DriveTrain();
    }
  }
  private void configureCandleBindings(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled the CANdle system.");
    } else {
      this.candle = new Lights();
      this.redcandle= new Lighter(candle,1);
      this.bluecandle= new Lighter(candle,2);
      m_driverController.a().whileTrue(redcandle);
      m_driverController.b().whileTrue(bluecandle);
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
  //  return Autos.exampleAuto(m_exampleSubsystem);
  return null;
  }
}
