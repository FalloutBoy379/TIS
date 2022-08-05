// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  double p1, i1, d1, f1;
  double p_flywheel, i_flywheel, d_flywheel, f_flywheel;
  WPI_TalonFX hood = new WPI_TalonFX(7);
  WPI_TalonFX flywheel = new WPI_TalonFX(1);

  double rpm = 0;
  double flywheel_speed;
  boolean flywheel_flag = false;
  double increment1;
  double setpoint1;
  Joystick Joy1 = new Joystick(0);

  double DEGREETOENCODER = (12 * 360)/(2048 * 20 * 31.25);

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    p1 = 1;
    i1 = 0;
    d1 = 0.5;
    f1 = 0;

    p_flywheel = 0;
    i_flywheel = 0;
    d_flywheel = 0;
    f_flywheel = 0.19002;

    hood.configFactoryDefault();
    hood.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
        0,
        30);
    hood.setSensorPhase(true);
    hood.setInverted(false);
    hood.configNominalOutputForward(0, 30);
    hood.configNominalOutputReverse(0, 30);
    hood.configPeakOutputForward(0.7, 30);
    hood.configPeakOutputReverse(-0.7, 30);
    hood.configAllowableClosedloopError(0, 10, 30);
    hood.config_kF(0, f1);
    hood.config_kP(0, p1);
    hood.config_kI(0, i1);
    hood.config_kD(0, d1);
    hood.configClosedloopRamp(0.1);
    hood.setNeutralMode(NeutralMode.Brake);

    flywheel.configFactoryDefault();
    flywheel.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    flywheel.setSensorPhase(false);
    flywheel.setInverted(false);
    flywheel.configNominalOutputForward(0, 30);
    flywheel.configNominalOutputReverse(0, 30);
    flywheel.configPeakOutputForward(1, 30);
    flywheel.configPeakOutputReverse(-1, 30);
    flywheel.configAllowableClosedloopError(0, 0, 30);
    flywheel.config_kF(0, f_flywheel);
    flywheel.config_kP(0, p_flywheel);
    flywheel.config_kD(0, d_flywheel);
    flywheel.config_kI(0, i_flywheel);
    flywheel.setNeutralMode(NeutralMode.Coast);
    flywheel.configClosedloopRamp(5);

    SmartDashboard.putNumber("P1", p1);
    SmartDashboard.putNumber("I1", i1);
    SmartDashboard.putNumber("D1", d1);
    SmartDashboard.putNumber("F1", f1);
    SmartDashboard.putNumber("P_Flywheel", p_flywheel);
    SmartDashboard.putNumber("I_Flywheel", i_flywheel);
    SmartDashboard.putNumber("D_Flywheel", d_flywheel);
    SmartDashboard.putNumber("F_Flywheel", f_flywheel);
    SmartDashboard.putNumber("Speed of Flywheel", flywheel.getSelectedSensorVelocity());

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different
   * autonomous modes using the dashboard. The sendable chooser code works with
   * the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the
   * chooser code and
   * uncomment the getString line to get the auto name from the text box below the
   * Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure
   * below with additional strings. If using the SendableChooser make sure to add
   * them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    increment1 = 0;
    setpoint1 = 0;
    hood.setSelectedSensorPosition(0);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double kp1 = SmartDashboard.getNumber("P1", 0);
    double ki1 = SmartDashboard.getNumber("I1", 0);
    double kd1 = SmartDashboard.getNumber("D1", 0);
    double kf1 = SmartDashboard.getNumber("F1", 0);

    if ((kp1 != p1)) {
      hood.config_kP(0, kp1);
      p1 = kp1;
    }
    if ((ki1 != i1)) {
      hood.config_kI(0, ki1);
      i1 = ki1;
    }
    if ((kd1 != d1)) {
      hood.config_kD(0, kd1);
      d1 = kd1;
    }
    if ((kf1 != f1)) {
      hood.config_kF(0, kf1);
      f1 = kf1;
    }

    p_flywheel = SmartDashboard.getNumber("P_Flywheel", 0);
    i_flywheel = SmartDashboard.getNumber("I_Flywheel", 0);
    d_flywheel = SmartDashboard.getNumber("D_Flywheel", 0);
    f_flywheel = SmartDashboard.getNumber("F_Flywheel", 0);
    flywheel.config_kF(0, f_flywheel);
    flywheel.config_kP(0, p_flywheel);
    flywheel.config_kD(0, d_flywheel);
    flywheel.config_kI(0, i_flywheel);

    if (Joy1.getRawButton(4) && !Joy1.getRawButton(3)) {
      if (increment1 <= 40) {
        increment1 = increment1 + 0.3;
      } else {
        increment1 = 40;
      }
    } else if (Joy1.getRawButton(3) && !Joy1.getRawButton(4)) {
      if (increment1 >= 0) {
        increment1 = increment1 - 0.3;
      } else {
        increment1 = 0;
      }
    }
    
    if(Joy1.getRawButtonPressed(1)){
      rpm = rpm + 100;
    }
    else if(Joy1.getRawButtonPressed(2)){
      rpm = rpm - 100;
    }
    if(rpm < 0){
      rpm = 0;
    }

    if (Joy1.getRawButtonPressed(5)) {
      if (flywheel_flag == true) {
        flywheel_flag = false;
      } else if (flywheel_flag == false) {
        flywheel_flag = true;
      }
    }

    
    setpoint1 = (increment1 / DEGREETOENCODER);
    double degree = hood.getSelectedSensorPosition() * DEGREETOENCODER;
    SmartDashboard.putNumber("Increment", increment1);
    SmartDashboard.putNumber("Stator Current", hood.getStatorCurrent());
    SmartDashboard.putNumber("Encoder Position", hood.getSelectedSensorPosition());
    SmartDashboard.putNumber("Hood Degree", degree);
    SmartDashboard.putNumber("Output precent", hood.getMotorOutputPercent());
    SmartDashboard.putNumber("CLosed Loop ERROR", hood.getClosedLoopError(0));
    SmartDashboard.putNumber("Speed of Flywheel", flywheel.getSelectedSensorVelocity() * 600/2048);
    SmartDashboard.putNumber("Target RPM of Flywheel ", rpm);
    SmartDashboard.putBoolean("Flag value", flywheel_flag);

    hood.set(TalonFXControlMode.Position, setpoint1);

    if (flywheel_flag == true) {
      flywheel.set(TalonFXControlMode.Velocity, rpm);
    }
    else{
      flywheel.set(TalonFXControlMode.Velocity, 0);
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {

  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}