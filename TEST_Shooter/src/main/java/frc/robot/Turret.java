package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret {
    private WPI_TalonFX motor;
    
    Turret(WPI_TalonFX turretMotor){
        motor = turretMotor;
        config();
    }

    public void setAngle(double angle){

        if(angle >= Constants.Turret.maxAngle){
            angle = Constants.Turret.maxAngle;
        }
        else if(angle <= Constants.Turret.minAngle){
            angle = Constants.Turret.minAngle;
        }

        SmartDashboard.putNumber("Turret Angle", getAngle());
        double counts = (angle * Constants.Turret.DEGREETOENCODER);
        setTarget(counts);
    }

    public void setTarget(double setpoint){
        motor.set(ControlMode.Position, setpoint);
    }

    private void config(){
        motor.configFactoryDefault();
        motor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
        motor.setSensorPhase(true);
        motor.setInverted(false);
        motor.configNominalOutputForward(0, 30);
        motor.configNominalOutputReverse(0, 30);
        motor.configPeakOutputForward(0.15, 30);
        motor.configPeakOutputReverse(-0.15, 30);
        motor.configAllowableClosedloopError(0, 120, 30);
        motor.config_kF(0, Constants.Turret.kf);
        motor.config_kP(0, Constants.Turret.kp);
        motor.config_kI(0, Constants.Turret.ki);
        motor.config_kD(0, Constants.Turret.kd);
        motor.configClosedloopRamp(0.2);
        motor.setNeutralMode(NeutralMode.Brake);

    }

    public void init(){
        motor.setSelectedSensorPosition(0);
        SmartDashboard.putNumber("P_Turret", Constants.Turret.kp);
        SmartDashboard.putNumber("I_Turret", Constants.Turret.ki);
        SmartDashboard.putNumber("D_Turret", Constants.Turret.kd);
        SmartDashboard.putNumber("F_Turret", Constants.Turret.kf);
    }

    public double getAngle(){
        double counts = motor.getSelectedSensorPosition();
        return counts * Constants.Turret.ENCODERTODEGREE;
    }
}
