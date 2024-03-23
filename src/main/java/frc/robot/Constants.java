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

  // CANIDS
  public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 1;
  public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 2;
  public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 14;
  public static final double FRONT_LEFT_MODULE_STEER_OFFSET =-0.0419921875;

  public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 5;
  public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 6;
  public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 12;
  public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -0.19873046875;

  public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 3;
  public static final int BACK_LEFT_MODULE_STEER_MOTOR = 4;
  public static final int BACK_LEFT_MODULE_STEER_ENCODER = 11;
  public static final double BACK_LEFT_MODULE_STEER_OFFSET = -0.355712890625;

  public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 7;
  public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 8;
  public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 13;
  public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -0.04736328125;
  //public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -0.044921875;

  public static final double WHEEL_REVOLUTIONS_PER_METER = 3.0;
  public static final double PERCENTAGE_MAX_SPEED = 100.0;

  public static final int LIGHT_ID = 31;

  public static final int LAUNCHER_WHEEL_FRONT=51;
  public static final int LAUNCHER_WHEEL_BACK=50;

  // Intake constants
    public static final int INTAKE_MOTOR_CAN_ID = 15;
    public static final double INTAKE_MOTOR_SPEED = 1;

  // Arms constants
    public static final int LEFT_ARM = 40;
    public static final int RIGHT_ARM = 41;

  // Launcher constants
  // TODO: Add launcher constants

  //Make stuff stop working so errors dont make the drivers whine
    public static final boolean DISABLE_INTAKE = false;
    public static final boolean DISABLE_DRIVETRAIN = false;
    public static final boolean DISABLE_CANDLE = false;
    public static final boolean DISABLE_LAUNCHER = false;
    public static final boolean DISABLE_RUMBLE = false;
    public static final boolean DISABLE_ARMS = false;

  //stop blinding kent
    public static final int BRIGHTNESS_DIVISOR = 10;
    public static double spinuptime=0.5;

    public static boolean debuggymodey=true;
    public static double ArmStowStopCurrent=15;
}

/*Whohahahao
                                           :!?JY55Y?!^
                                          ^YGGGGGBBBGP?:
                              :::::::::~7!YGGGGBBBBBBBB!
                     :^^^!!!7777??77YPB#&#GBBBGGBBBBBB#5
                   ^7??JYGGGGGGGP55PB&&&&BBBGPPGPGGGGBB7
                   ?GPGGGBBBBBBGGGGB&&&BPGGBGPPPPPPPG##J~^:
                  :JPPPPBBBBBBBBGGG#&#PY55GBBGGGGGGPGBBGYJJJ7~^
            ::^~!7?5PPPGBBBBBB#BGGG##5PPPGGBBBBBBGGGBBGGGPBBBGP5?:
         :~!!77?JYYPGGGG#######BGGG#BGGPPGBB#BBBBBBBBGPGGB#BBB##GY7~:
      ^~?J?????7?YYGGGGB&&&####BBBBBBBGPPPGBB#BGGGBBG5GGBBBBB##GGGGP5?:
   :!?YYYJ!?JJJ?7?5GY?J5PGB##&&#BB#GGGGPPPGBB#BBBBBB5PBBBBGB#BPPPPGB5~:~!!~!???7:
 :!J55YYYJ7??????JPJ!?55YYY55YPGBB#BGPPPGGBBBBGGGGP5GB####B#BPPPPGPP!:75PGPPGG5?^
!Y555555YYYYJJJY5GBGJ5PP5555?!YGGB#BGGGGPPPPPPPPPPPB#&&&&&&#GGGGG5YYJ77!~^:^YGGGGGGPPPP5?:
PP55555YYY5555PGGP5?7Y5PPPPP?YGBGGGGGGGGGGGGGGGGGGBGGGBBBBBGPGGGPPPPP55YYY?!!~^:^^^^YGGGGGP5GBBBGBP!
?5PPPP55555PGGPJ~^J75555555J?5GBGGGGGGGGGGGGGGGGBG55?7Y55555Y?5BGGGGGPP555Y5Y7!!!!!!PBGGGPPPPGGGBBBP
:!77?JJY555PP!:  ~B555PPPPJ!YGBBBBBBBBBBBBBBBBBGGP5PY?5PPPPPPYP&#BBBGGGPPP5PJ7777777JPGGBGGBGPP5Y?!^
 ~!!!!!!!!!7?7:  ^J7?Y55PG55GBB##BBBBBBBB#####G55?JP55PGGP5GJ!~JPB###BGGGPPPY????JJYY55555Y7^::
 ^7!!!!!!!!!!7?: :7J555YY5Y?YG###############BP5PJJPPP555YJY:    ^!YGB#BBGPPPYJYYY5555555?^
  !7!!!!!!!!!!?~  !BGGPP5Y!?5G##B###########PY?YP5PGGGGPGPGY:       :^7YGBBGGP55PPPP55J!:
  :!7!!!!!!!!!77: ^5BBBBBPYGB#&BY555555555555PJ?5PPPPP5YG#Y:            :~YPPPPPPPY7~:
   :!!!!!!!!!!!7~:PGGBBBBB###&&#55555PPP55555PPPGGPPPPPB#?                 :::^^~^:
    ^!!!!!~~~~~!7JGGGBBGBB#####B555PPGGGP55555PGGGP555G#P::
     :!!!~~~~!7??JPGB#YYGGBB####555PPGGGPP555PPGGGPP55555J?7!^:
      :!!~!?Y5PPP55PGYJ5555PPGBB5555PPPPP55555PPPPP555PPPP55YYJ?7~:
       :?5PPPPPPPPPPPPYYY55PPPGG5555555555555555555555PPPPPPPPPPPP5J!^
        ~PPPPPPPPPPGGGG5Y5PPGBBB5555555555555555555555PPPPPPPPPPPPPGGPY7^:
         ?PPPPPGGPGBGGGGPPPGBBBBPPPPPPPPPPPPPPPPPPPPPPPPPPPPGGGGGGPGGGGG5J~
         :YGGPGBBGGGGGGGGGPGBBBBBBBBBBBBBBBBBBBBBBBBBBGGGPPPGGGGGGGGGGGGBG57
          JGGGGGGPGGGGBBGBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB#BBBBBBBGGGGGGGGBB#B5:
        :!JPGPGGGPPGBGBB##BBBBBBBBBBBBBBBBBBBBBBBBBG!!7?JY5PP55GGGGGGGGB##5?~
      :!J55PPPPGGGGG#B#BY!^~~YBBBBBBBBBBBBBBBBBBBBB!         ^YPGGGGGGGGBBBP7:
    :~?Y5PPPPGGGGBBB#G7:     :YBBBBBBBBBBBBBBBBB#G7        ^7PGGGGGGGGGGBBGGP^
   ~?Y5PGGGGGGGBB##G?:         ~5BBBBBBBBBBBBB#B?:         ?GGGGGGGGGBBBGGBB?
 :755PGGGGGGGBBBBG?:             !5B#BBB#####BJ^           ^JGGGGGGGGGGGGBG~
 ?PPPPGGGGGGGBB#BY:                !YYJJJJJJJ~              JBBGGGGGGB###5:
 ^YGGGGGGGGBBBBBBBJ                                        :GBBBBB##5?JY?
  :YBGGGGGBBBBBBBB5:                                       :PBBBB#&5:
    JBGGGGGGGGGBP!:                                      :!JBBBB#&5:
     755PBBBBB#B5:                                       7PGGGBB##7
        JBB##BB##!                                      !PGGGGGGG#Y
        ?GGGBBB#P:                                     :JGGGGGGGBB~
       ~PGGGGGB#P:                                      ?GGGGGGGBP:
       75GGGGGBBBJ                                     ^GBGGGGGGGG5~
   ^~~!75PGBBBBB#G:                                    ?BBBBBBGBBGY!:
  ?GBBBBBBGGGBB##B?                                    YBBBBBBBBBGGP55555Y^
 ~PGGGGGGGGGGBPPP5?:                                  !PGGBBGGGGGGGBBBGGGBY^
 ^7??????7777!:                                       ~77?JYJJYY555PPPPPPGP~*/