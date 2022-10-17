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

public class Flywheel extends SubsystemBase {
  WPI_TalonFX flywheel = new WPI_TalonFX(Constants.Shooter.Flywheel.CANid);

  boolean enabled = false;
  public double speed = 0; 

  /** Creates a new Flywheel. */
  public Flywheel() {
    flywheel.configFactoryDefault();
    flywheel.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    flywheel.setSensorPhase(Constants.Shooter.Flywheel.ksensorPhase);
    flywheel.setInverted(Constants.Shooter.Flywheel.kmotorInverted);
    flywheel.configAllowableClosedloopError(0, 0, 30);
    flywheel.config_kF(0, Constants.Shooter.Flywheel.kf);
    flywheel.config_kP(0, Constants.Shooter.Flywheel.kp);
    flywheel.config_kD(0, Constants.Shooter.Flywheel.kd);
    flywheel.config_kI(0, Constants.Shooter.Flywheel.ki);
    flywheel.setNeutralMode(NeutralMode.Coast);
    flywheel.configClosedloopRamp(5);
  }

  public void spinFlywheel(double velocity) {
    if (enabled) {
      flywheel.set(TalonFXControlMode.Velocity, velocity);
    } else {
      flywheel.stopMotor();
    }
  }
  // flywheel_speed = flywheel.getSelectedSensorVelocity() * 600 / 2048;
  public void spinToRPM(double rpm){
    double velocity = rpm * Constants.Shooter.Flywheel.RPMTOVELOCITY;
    spinFlywheel(velocity);
  }

  public void enable(){
    enabled = true;
  }

  public void disable(){
    enabled = false;
    flywheel.stopMotor();
  }

  @Override
  public void periodic() {
    speed = flywheel.getSelectedSensorVelocity();
    SmartDashboard.putNumber("Speed of Flywheel", speed);
  }
}
