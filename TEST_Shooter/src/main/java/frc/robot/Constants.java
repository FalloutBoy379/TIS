package frc.robot;

public class Constants {
    public static class Shooter{
        public static final int CANid = 4;

        public static final double kMaxRPM = 5000;
        public static final double kSensorUnitsPerRotation = 2048;
        public static final double kGearRatio = 1;
        public static final double kPeakSensorVelocity = (kMaxRPM  / 600) * (kSensorUnitsPerRotation / kGearRatio);

        

        public static final boolean ksensorPhase = true;
        public static final boolean kmotorInverted = false;

        public static double kp = 0;
        public static double kd = 0;
        public static double ki = 0;
        public static double kf = 0.196;
        // public static double kp = 0.1;
        // public static double kd = 0.8;
        // public static double ki = 1E-07;
        // public static double kf = 0.0438;
    } 

    public static class Hood{
        public static final int CANid = 7;

        public static final double minimumStep = 0.3;
        public static final double maxAngle = 40;

        public static final double minAngle = 0.1;

        public static final double kp = 0.8;
        public static final double ki = 0;
        public static final double kd = 0.5;
        public static final double kf = 0;

        public static double DEGREETOENCODER = (12 * 360) / (2048 * 20 * 31.25);
    } 

    public static class Turret{
        public static final int CANid = 9;
        public static final int movingAverageXTaps = 100;
        public static final int movingAverageYTaps = 100;
        public static final double DEGREETOENCODER = 125.15555555555555555555555555556;
        public static final double ENCODERTODEGREE = 1/DEGREETOENCODER;
        public static final double minAngle = -45;
        public static final double maxAngle = 45;
        public static final double minimumStep = 15;
        public static final double kp = 0.8;
        public static final double ki = 0.001;
        public static final double kd = 0.5;
        public static final double kf = 0;
    }
    
}
