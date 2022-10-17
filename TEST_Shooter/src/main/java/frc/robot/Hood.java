package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Hood {
    private WPI_TalonFX motor;

    Hood(WPI_TalonFX hoodMotor){
        motor = hoodMotor;

    }

    public void setAngle(double angle){
        double counts = angle;
        setTarget(counts);
    }

    public void setTarget(double setpoint){
        motor.set(ControlMode.Position, setpoint);
    }

    public double getPosition(){

        return 
    }
}
