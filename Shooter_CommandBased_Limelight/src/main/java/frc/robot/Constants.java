// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class Turret{
        public static final int ID = 9;
        public static final double DEGREE_TO_ENCODER = 125.15555555555555555555555555556;
        public static final double ENCODER_TO_DEGREE = 1/DEGREE_TO_ENCODER;
        public static final double minAngle = -45;
        public static final double maxAngle = 45;

        public static final boolean defaultSensorPhase = true;
        public static final boolean defaultDirection = false;

        public static final double peakOutputForward = 0.15;
        public static final double peakOutputReverse = -0.15;
        public static final double closedLoopRamp = 0.2;
        public static final NeutralMode neutralMode = NeutralMode.Brake;

        public static final double kp = 0.8;
        public static final double ki = 0.001;
        public static final double kd = 0.5;
        public static final double kf = 0;
    }
}
