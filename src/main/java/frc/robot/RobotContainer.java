// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.BooleanFunc;
import frc.robot.commands.Lighter;
import frc.robot.commands.MoveArms;
import frc.robot.commands.MoveArmsUntilResistance;
import frc.robot.commands.MoveToPosition;
import frc.robot.commands.ResetIMU;
import frc.robot.commands.Rumbly;
import frc.robot.commands.SavePosition;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.SpinUpLauncher;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.DriveFieldRelative;
import frc.robot.commands.FeedNoteToLauncher;
import frc.robot.commands.IndicateNote;
import frc.robot.commands.IntakeReverse;
import frc.robot.commands.IntakeReverseRegardless;
import frc.robot.commands.IntakeStart;
import frc.robot.commands.LaunchNote;
import frc.robot.subsystems.Arms;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.PoseEstimatorSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
  public static double[][] locations;
  public static int locationCount=0;
  // The robot's subsystems and commands are defined here...
  // private final Intake intake= new Intake();
  // private final IntakeStart intakestart = new IntakeStart(intake);
  DriveFieldRelative forwardCommand;
  DriveFieldRelative leftCommand;
  DriveFieldRelative backCommand;
  DriveFieldRelative rightCommand;
  ResetIMU resetIMUCommand;
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private DriveTrain drivetrain;
  private Lights candle;
  private IndicateNote notelight;
  private Intake intake;
  private PoseEstimatorSubsystem poseEstimatorSubsystem;
  private IntakeStart intakestart;
  private Launcher launcher;
  private Arms arms;
  private MoveArms moveArms;
  private SendableChooser<Command> mailman;
  private Supplier<Pose2d> wheretogo;
  private TrajectoryCommandFactory trajectoryCommandFactory;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_cmdcontroller = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);
  // Use CommandXboxController unless it is missing functions that XboxController has like .setRumble
  private final XboxController m_controller = new XboxController(0);
  // Avoid usage unless needed

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the trigger bindings
    configureDriveTrainBindings(Constants.DISABLE_DRIVETRAIN);
    configureIntakeBindings(Constants.DISABLE_INTAKE);
    configureCandleBindings(Constants.DISABLE_CANDLE || Constants.DISABLE_INTAKE);
    configureLauncherBindings(Constants.DISABLE_LAUNCHER);
    configurePoseEstimatorSubsystem(drivetrain);
    configureArms(Constants.DISABLE_ARMS);
    configureCompoundCommands(Constants.DISABLE_INTAKE || Constants.DISABLE_LAUNCHER);
    this.trajectoryCommandFactory = new TrajectoryCommandFactory(drivetrain, poseEstimatorSubsystem);
    populateMailbox();
     SmartDashboard.putData("autonomous modee", mailman);
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
      this.intake = new Intake();
      this.intakestart = new IntakeStart(intake,Constants.INTAKE_MOTOR_SPEED);
      m_cmdcontroller.leftBumper().toggleOnTrue(
        intakestart.andThen(
           new Rumbly(m_controller).withTimeout(0.5))
           .andThen(new IntakeReverse(intake))
        .andThen(new IntakeStart(intake, 1))
        .andThen(new IntakeReverse(intake))
        .andThen(new IntakeStart(intake,1))
        .andThen(new IntakeReverse(intake))
        .andThen(new IntakeStart(intake,0.3)));
      m_cmdcontroller.back().whileTrue(new IntakeReverseRegardless(intake));
    }
  }

  private void configureLauncherBindings(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled the launcher subsystem");
      return;
    }
    launcher=new Launcher();
    m_cmdcontroller.start().toggleOnTrue(new LaunchNote(launcher));


  }

  private void configureDriveTrainBindings(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled the DriveTrain system.");
    } else {
      drivetrain = new DriveTrain();
      drivetrain.setDefaultCommand(new DefaultDriveCommand(drivetrain,
          () -> -modifyAxis(m_controller.getLeftY()),
          () -> -modifyAxis(m_controller.getLeftX()),
          () -> modifyAxis(m_controller.getRightX())));
      forwardCommand = new DriveFieldRelative(drivetrain, 0, .5);
      backCommand = new DriveFieldRelative(drivetrain, Math.PI, .5);
      leftCommand = new DriveFieldRelative(drivetrain, (2 * Math.PI * 3) / 4, .5);
      rightCommand = new DriveFieldRelative(drivetrain, Math.PI / 2, .5);
      resetIMUCommand = new ResetIMU(drivetrain);

      m_cmdcontroller.rightStick().onTrue(resetIMUCommand);

    }
  }

  private void configurePoseEstimatorSubsystem(DriveTrain driveTrain) {
    if ( !(Constants.DISABLE_DRIVETRAIN || Constants.DISABLE_POSE_ESTIMATOR) ) {
      var photonCamera = new PhotonCamera("USB_webcam");
      poseEstimatorSubsystem = new PoseEstimatorSubsystem(photonCamera, driveTrain);
    }
  }



  private void configureCandleBindings(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled the CANdle system.");
    } else {
      this.candle = new Lights();
      this.notelight = new IndicateNote(candle, ()->intake.isLimitSwitchEngaged());

    }
  }

  private void configureArms(boolean disabled) {
    if (disabled) {

    } else {
      arms = new Arms();
      moveArms = new MoveArms(arms, m_controller);   
      
      arms.setDefaultCommand(moveArms);

    }
  }

  private void configureCompoundCommands(boolean disabled) {
    if (disabled) {
      System.out.println("Disabled compound commands");
    }

    m_cmdcontroller.b().onTrue(new SpinUpLauncher(launcher,intake,4000,3500,true,false).withTimeout(8)
      .andThen(new FeedNoteToLauncher(intake).alongWith(new SpinUpLauncher(launcher,intake,4000,3500,false,true)).withTimeout(8).andThen(new Rumbly(m_controller).withTimeout(0.3))));

    m_cmdcontroller.a().onTrue(new SpinUpLauncher(launcher,intake,3500,2500,true,false).withTimeout(8)
      .andThen(new FeedNoteToLauncher(intake).alongWith(new SpinUpLauncher(launcher,intake,3500,2500,false,true)).withTimeout(8).andThen(new Rumbly(m_controller).withTimeout(0.3))));
    
      // TODO: Bind a command to the right bumper that:
    // 1. Runs LaunchNote for .5 secons.
    // 2. Runs FeedNoteToLauncher and LaunchNote togeter for 2 seconds
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */


  private void populateMailbox()
  {
    if (Constants.DISABLE_LAUNCHER) {
      System.out.println("Disabled mailbox because launcher is disabled.");
      System.out.println("WE WOULDNT WANT ANY FATAL ERRORS, WOULD WE ETHAN?");
      System.out.println("*cough cough*");
    } else {
      mailman=new SendableChooser<>();
     // Command intakeCommand=new IntakeStart(intake,1).andThen(new IntakeReverse(intake).andThen(new IntakeStart(intake, 0.3)));
     // Command launchCommand=new SpinUpLauncher(launcher,6000,6000,true).withTimeout(4).andThen(new SpinUpLauncher(launcher,6000,6000,false).alongWith(new FeedNoteToLauncher(intake))).withTimeout(4);
      Command move=new DriveFieldRelative(drivetrain, 0, 0.25);
      //mailman.setDefaultOption("launch",intakeCommand.andThen(launchCommand));
      mailman.addOption("launch",
        new IntakeReverse(intake)
        .andThen(new IntakeStart(intake, 0.3)
        .andThen(new SpinUpLauncher(launcher,intake,6000,6000,true,false))
        .andThen( (new SpinUpLauncher(launcher,intake,6000,6000,false,true).alongWith(new FeedNoteToLauncher(intake))).withTimeout(6) )
          ));
      mailman.addOption("launch+move",
        new IntakeReverse(intake)
        .andThen(new IntakeStart(intake, 0.3)
        .andThen(new SpinUpLauncher(launcher,intake,6000,6000,true,false))
        .andThen( (new SpinUpLauncher(launcher,intake,6000,6000,false,true).alongWith(new FeedNoteToLauncher(intake))).withTimeout(1) )
        .andThen(move.withTimeout(2))
          ));
        mailman.addOption("launch+move+intake",
        new IntakeReverse(intake)
        .andThen(new IntakeStart(intake, 0.3)
        .andThen(new SpinUpLauncher(launcher,intake,6000,6000,true,false))
        .andThen( (new SpinUpLauncher(launcher,intake,6000,6000,false,true).alongWith(new FeedNoteToLauncher(intake))).withTimeout(1) )
        .andThen(new DriveFieldRelative(drivetrain, 0, 0.25).alongWith(new IntakeStart(intake,1)).withTimeout
        (2))));
      mailman.addOption("back", new DriveFieldRelative(drivetrain, 0, 0.15));//.withTimeout(15));
      mailman.addOption("nothing", null);
      mailman.addOption("DO NOT USE ON FIELD!!!!! arm retract (for the pit)",new MoveArmsUntilResistance(arms, m_controller));

      mailman.addOption("move with waypoints", createTrajectoryCommandWithWayPoints());
      mailman.addOption("move without waypoints", createTrajectoryCommandNoWayPoints());
      mailman.addOption("move with waypoints starting from diff location", createTrajectoryCommandStartDiffThanRobotPosition());
      SmartDashboard.putData("autonomous mode", mailman);
    }
  }
  
  public Command getAutonomousCommand(){
    return mailman.getSelected();
  }

  public Command createTrajectoryCommandWithWayPoints() {
    Transform2d transformStart = new Transform2d(3, 3, new Rotation2d(0));
    Pose2d startingPos = poseEstimatorSubsystem.getPosition();
    Pose2d endingPos = startingPos.transformBy(transformStart);
    List<Translation2d> wayPoints = new ArrayList<>();
    wayPoints.add( new Translation2d(1.66, 1));
    wayPoints.add(new Translation2d(1.1,1));
    return trajectoryCommandFactory.createTrajectoryCommand(poseEstimatorSubsystem.getPosition(), wayPoints, endingPos);
  }

  public Command createTrajectoryCommandNoWayPoints() {
    Transform2d transformStart = new Transform2d(3, 3, new Rotation2d(0));
    Pose2d startingPos = poseEstimatorSubsystem.getPosition();
    Pose2d endingPos = startingPos.transformBy(transformStart);
    List<Translation2d> wayPoints = new ArrayList<>();
    return trajectoryCommandFactory.createTrajectoryCommand(poseEstimatorSubsystem.getPosition(), wayPoints, endingPos);
  }

  public Command createTrajectoryCommandStartDiffThanRobotPosition() {
    Transform2d transformStart = new Transform2d(3, 3, new Rotation2d(0));
    Pose2d startingPos = poseEstimatorSubsystem.getPosition().transformBy((new Transform2d(.5, 1, new Rotation2d(0))));
    Pose2d endingPos = startingPos.transformBy(transformStart);
    List<Translation2d> wayPoints = new ArrayList<>();
    wayPoints.add( new Translation2d(1.66, 1));
    wayPoints.add(new Translation2d(1.1,1));
    return trajectoryCommandFactory.createTrajectoryCommand(poseEstimatorSubsystem.getPosition(), wayPoints, endingPos);
  }
}
