package frc.robot;

public class Constants {
    public static final class Shooter{
        public static final double kMaxRPM = 5000;
        public static final double kSensorUnitsPerRotation = 2048;
        public static final double kGearRatio = 1;
        public static final double kPeakSensorVelocity = (kMaxRPM  / 600) * (kSensorUnitsPerRotation / kGearRatio);

        public static final boolean ksensorPhase = false;
        public static final boolean kmotorInverted = false;
    } 
    
}
