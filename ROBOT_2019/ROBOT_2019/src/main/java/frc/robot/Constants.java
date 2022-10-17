// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public final class Shooter {
        public final class Turret {
            public static final int CANid = 9;

            public static final double kp = 0;
            public static final double ki = 0;
            public static final double kd = 0;
            public static final double kf = 0;

            public static final double DEGREETOENCODER = 0;
            public static final double ENCODERTODEGREE = 1 / DEGREETOENCODER;

            public static final double MAXDEGREE = 45;
            public static final double MINDEGREE = -45;
        }

        public final class Flywheel {
            public static final int CANid = 7;
            public static final boolean ksensorPhase = true;
            public static final boolean kmotorInverted = false;

            public static final double kp = 0;
            public static final double kd = 0;
            public static final double ki = 0;
            public static final double kf = 0.196;

            public static final double kMaxRPM = 5000;
            public static final double kSensorUnitsPerRotation = 2048;
            public static final double kGearRatio = 1;
            public static final double kPeakSensorVelocity = (kMaxRPM / 600) * (kSensorUnitsPerRotation / kGearRatio);

            public static final double VELOCITYTORPM = 600 / 2048;
            public static final double RPMTOVELOCITY = 1 / VELOCITYTORPM;
        }

        public final class Hood {
            public static final int CANid = 0;

            public static final double minimumStep = 10;
            public static final double maxAngle = 40;
    
            public static final double minAngle = 0.1;
    
            public static final double kp = 0.8;
            public static final double ki = 0;
            public static final double kd = 0.5;
            public static final double kf = 0;
    
            public static final double DEGREETOENCODER = (12 * 360) / (2048 * 20 * 31.25);
            public static final double ENCODERTODEGREE = 1/DEGREETOENCODER;
        }
    }

    public final class Feeder{
        public static final int inputCANid = 8;
        public static final int outputCANid = 1;
        public static final int examineCANid = 10;
    }

}
