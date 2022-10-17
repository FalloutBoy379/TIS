// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {

  WPI_TalonFX motor;
  /** Creates a new Turret. */
  public Turret() {
    motor = new WPI_TalonFX(Constants.Turret.ID);


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


  private void setTurretMode(WPI_TalonFX turretMotor){
    turretMotor.configFactoryDefault();
    turretMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    turretMotor.setSensorPhase(Constants.Turret.defaultSensorPhase);
    turretMotor.setInverted(Constants.Turret.defaultDirection);
    turretMotor.configNominalOutputForward(0, 30);
    turretMotor.configNominalOutputReverse(0, 30);
    turretMotor.configPeakOutputForward(Constants.Turret.peakOutputForward, 30);
    turretMotor.configPeakOutputReverse(Constants.Turret.peakOutputReverse, 30);
    turretMotor.configAllowableClosedloopError(0, 120, 30);
    turretMotor.config_kF(0, Constants.Turret.kf);
    turretMotor.config_kP(0, Constants.Turret.kp);
    turretMotor.config_kI(0, Constants.Turret.ki);
    turretMotor.config_kD(0, Constants.Turret.kd);
    turretMotor.configClosedloopRamp(Constants.Turret.closedLoopRamp);
    turretMotor.setNeutralMode(Constants.Turret.neutralMode);
  }
}
