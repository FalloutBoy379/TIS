//**********************IGNORE THIS FILE, FOR CONVERSION TO COMMAND BASED *******************/



// package frc.robot;

// import com.ctre.phoenix.motorcontrol.NeutralMode;
// import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
// import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

// import edu.wpi.first.math.filter.LinearFilter;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class Turret {
//     private static final WPI_TalonFX turret = new WPI_TalonFX(Constants.Turret.CANid);
//     private static final LinearFilter xFilter = LinearFilter.movingAverage(Constants.Turret.movingAverageXTaps);
//     private static final LinearFilter yFilter = LinearFilter.movingAverage(Constants.Turret.movingAverageYTaps);
//     private static double turret_target = 0;

//     Turret() {
//         turret.configFactoryDefault();
//         turret.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
//         turret.setSensorPhase(true);
//         turret.setInverted(false);
//         turret.configNominalOutputForward(0, 30);
//         turret.configNominalOutputReverse(0, 30);
//         turret.configPeakOutputForward(0.15, 30);
//         turret.configPeakOutputReverse(-0.15, 30);
//         turret.configAllowableClosedloopError(0, 120, 30);
//         turret.config_kF(0, Constants.Turret.kf);
//         turret.config_kP(0, Constants.Turret.kp);
//         turret.config_kI(0, Constants.Turret.ki);
//         turret.config_kD(0, Constants.Turret.kd);
//         turret.configClosedloopRamp(0.2);
//         turret.setNeutralMode(NeutralMode.Brake);

//         turret.setSelectedSensorPosition(0);
//     }

//     private static double limitAngle(double angle, double upperLimit, double lowerLimit) {
//         if (angle >= upperLimit) {
//             return upperLimit;
//         } else if (angle <= lowerLimit) {
//             return lowerLimit;
//         } else {
//             return angle;
//         }
//     }

//     public static void updatePID() {
//         turret.config_kP(0, SmartDashboard.getNumber("P_Turret", Constants.Turret.kp));
//         turret.config_kI(0, SmartDashboard.getNumber("I_Turret", Constants.Turret.ki));
//         turret.config_kD(0, SmartDashboard.getNumber("D_Turret", Constants.Turret.kd));
//         turret.config_kF(0, SmartDashboard.getNumber("F_Turret", Constants.Turret.kf));
//     }

//     public static void resetPID(){
//         turret.config_kP(0, Constants.Turret.kp);
//         turret.config_kI(0, Constants.Turret.ki);
//         turret.config_kD(0, Constants.Turret.kd);
//         turret.config_kF(0, Constants.Turret.kf);
//     }

//     public static void goToAngle(double degrees) {
//         degrees = limitAngle(degrees, 45, -45);
//         double turret_setpoint = (degrees * Constants.Turret.DEGREETOENCODER);
//         turret.set(TalonFXControlMode.Position, turret_setpoint);
//     }
// }
