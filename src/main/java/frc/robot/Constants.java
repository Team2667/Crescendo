// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants c`
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final double DRIVETRAIN_TRACKWIDTH_METERS = 1.0;
  /**
   * The front-to-back distance between the drivetrain wheels.
   *
   * Should be measured from center to center.
   */
  private static final double pi=3.141592;
   public static final int PigeonId=31;
  public static final double DRIVETRAIN_WHEELBASE_METERS = 1.0;
  public static final double MAX_INPUT_SPEED = 1; //4.14528;


  public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 1;
  public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 2;
  public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 14;
  public static final double FRONT_LEFT_MODULE_STEER_OFFSET =-0.04052734375;

  public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 5;
  public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 6;
  public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 12;
  public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -0.199462890625;

  public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 3;
  public static final int BACK_LEFT_MODULE_STEER_MOTOR = 4;
  public static final int BACK_LEFT_MODULE_STEER_ENCODER = 11;
  public static final double BACK_LEFT_MODULE_STEER_OFFSET = -0.350830078125;

  public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 7;
  public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 8;
  public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 13;
  public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -0.044921875;

  public static final double WHEEL_REVOLUTIONS_PER_METER = 3.0;
  public static final double PERCENTAGE_MAX_SPEED = 100.0;
  public static final double INTAKE_MOTOR_SPEED = 0;
public static final int INTAKE_MOTOR_CAN_ID = 0;
}
