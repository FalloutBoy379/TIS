// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hood extends SubsystemBase {
  WPI_TalonFX hood = new WPI_TalonFX(Constants.Shooter.Hood.CANid);
  double position = 0;

  public Hood() {
    hood.configFactoryDefault();
    hood.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    hood.setSensorPhase(true);
    hood.setInverted(false);
    hood.configNominalOutputForward(0, 30);
    hood.configNominalOutputReverse(0, 30);
    hood.configPeakOutputForward(0.1, 30);
    hood.configPeakOutputReverse(-0.1, 30);
    hood.configAllowableClosedloopError(0, 10, 30);
    hood.config_kF(0, Constants.Shooter.Hood.kf);
    hood.config_kP(0, Constants.Shooter.Hood.kp);
    hood.config_kI(0, Constants.Shooter.Hood.ki);
    hood.config_kD(0, Constants.Shooter.Hood.kd);
    hood.configClosedloopRamp(0.1);
    hood.setNeutralMode(NeutralMode.Brake);   
  }


  public void setHoodAngle(double angle){
    double pos = angle * Constants.Shooter.Hood.DEGREETOENCODER;
    setHoodPos(pos);
  }

  public void setHoodPos(double pos){
    hood.set(TalonFXControlMode.Position, pos);
  }

  @Override
  public void periodic() {
    position = hood.getSelectedSensorPosition();
  }
}
