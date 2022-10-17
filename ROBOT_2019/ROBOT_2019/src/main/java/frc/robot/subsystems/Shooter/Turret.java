// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {
  WPI_TalonFX turret = new WPI_TalonFX(Constants.Shooter.Turret.CANid);
  public double position_counts = 0;
  public double position_degrees = 0;


  /** Creates a new Turret. */
  public Turret() {
    turret.configFactoryDefault();
    turret.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    turret.setSensorPhase(true);
    turret.setInverted(false);
    turret.configNominalOutputForward(0, 30);
    turret.configNominalOutputReverse(0, 30);
    turret.configPeakOutputForward(0.15, 30);
    turret.configPeakOutputReverse(-0.15, 30);
    turret.configAllowableClosedloopError(0, 120, 30);
    turret.config_kF(0, Constants.Shooter.Turret.kf);
    turret.config_kP(0, Constants.Shooter.Turret.kp);
    turret.config_kI(0, Constants.Shooter.Turret.ki);
    turret.config_kD(0, Constants.Shooter.Turret.kd);
    turret.configClosedloopRamp(0.2);
    turret.setNeutralMode(NeutralMode.Brake);
  }

  public void setTurretPosition(double pos){
    turret.set(TalonFXControlMode.Position, pos);
  }

  public void setTurretDegree(double degree){
    if(degree >= Constants.Shooter.Turret.MAXDEGREE){
      degree = Constants.Shooter.Turret.MAXDEGREE;
    }
    else if(degree <= Constants.Shooter.Turret.MINDEGREE){
      degree = Constants.Shooter.Turret.MINDEGREE;
    }
    double counts = degree * Constants.Shooter.Turret.DEGREETOENCODER;
    setTurretPosition(counts);
  }

  @Override
  public void periodic() {
    position_counts = turret.getSelectedSensorPosition();
    position_degrees = position_counts * Constants.Shooter.Turret.ENCODERTODEGREE;
    SmartDashboard.putNumber("Turret Position(Degrees)", position_degrees);
  }
}
