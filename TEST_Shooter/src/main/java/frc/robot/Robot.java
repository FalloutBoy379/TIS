// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFXPIDSetConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Robot extends TimedRobot {

  double p_flywheel, i_flywheel, d_flywheel, f_flywheel;
  WPI_TalonFX hood = new WPI_TalonFX(Constants.Hood.CANid);
  WPI_TalonFX flywheel = new WPI_TalonFX(Constants.Shooter.CANid);

  SerialPort arduino = new SerialPort(115200, Port.kUSB1);

  double rpm = 0;
  double flywheel_speed;
  boolean flywheel_flag = false;
  double increment1;
  double setpoint1;
  // Joystick Joy1 = new Joystick(0);
  PS4Controller PS5 = new PS4Controller(0);
  Faults fault = new Faults();


  List<Integer> velocityList = new ArrayList<Integer>();
  boolean storeVelocityFlag = false;


  double DEGREETOENCODER = (12 * 360) / (2048 * 20 * 31.25);

  @Override
  public void robotInit() {

    p_flywheel = 0;
    i_flywheel = 0;
    d_flywheel = 0;
    f_flywheel = 0.19002;

    hood.configFactoryDefault();
    hood.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    hood.setSensorPhase(true);
    hood.setInverted(false);
    hood.configNominalOutputForward(0, 30);
    hood.configNominalOutputReverse(0, 30);
    hood.configPeakOutputForward(0.7, 30);
    hood.configPeakOutputReverse(-0.7, 30);
    hood.configAllowableClosedloopError(0, 10, 30);
    hood.config_kF(0, Constants.Hood.kf);
    hood.config_kP(0, Constants.Hood.kp);
    hood.config_kI(0, Constants.Hood.ki);
    hood.config_kD(0, Constants.Hood.kd);
    hood.configClosedloopRamp(0.1);
    hood.setNeutralMode(NeutralMode.Brake);

    flywheel.configFactoryDefault();
    flywheel.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    flywheel.setSensorPhase(Constants.Shooter.ksensorPhase);
    flywheel.setInverted(Constants.Shooter.kmotorInverted);
    flywheel.configAllowableClosedloopError(0, 0, 30);
    flywheel.config_kF(0, Constants.Shooter.kf);
    flywheel.config_kP(0, Constants.Shooter.kp);
    flywheel.config_kD(0, Constants.Shooter.kd);
    flywheel.config_kI(0, Constants.Shooter.ki);
    flywheel.setNeutralMode(NeutralMode.Coast);
    flywheel.configClosedloopRamp(5);
    flywheel.getFaults(fault);

    SmartDashboard.putNumber("Speed of Flywheel", flywheel.getSelectedSensorVelocity());

  }

  @Override
  public void robotPeriodic() {
  }

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

    SmartDashboard.putNumber("P_Flywheel", Constants.Shooter.kp);
    SmartDashboard.putNumber("I_Flywheel", Constants.Shooter.ki);
    SmartDashboard.putNumber("D_Flywheel", Constants.Shooter.kd);
    SmartDashboard.putNumber("F_Flywheel", Constants.Shooter.kf);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    if (arduino.getBytesReceived() > 0) {
      SmartDashboard.putString("Received: ", arduino.readString());
    }

    p_flywheel = SmartDashboard.getNumber("P_Flywheel", Constants.Shooter.kp);
    i_flywheel = SmartDashboard.getNumber("I_Flywheel", Constants.Shooter.ki);
    d_flywheel = SmartDashboard.getNumber("D_Flywheel", Constants.Shooter.kd);
    f_flywheel = SmartDashboard.getNumber("F_Flywheel", Constants.Shooter.kf);

    flywheel.config_kP(0, p_flywheel);
    flywheel.config_kI(0, i_flywheel);
    flywheel.config_kD(0, d_flywheel);
    flywheel.config_kF(0, f_flywheel);

      if(PS5.getCircleButton()){
      if (increment1 < Constants.Hood.maxAngle) {
        increment1 = increment1 + Constants.Hood.minimumStep;
      } else if (increment1 >= Constants.Hood.maxAngle) {
        increment1 = Constants.Hood.maxAngle;
      }
    // } else if (Joy1.getRawButton(3) && !Joy1.getRawButton(4)) {
    }else if(PS5.getSquareButton()){
      if (increment1 > Constants.Hood.minAngle) {
        increment1 = increment1 - Constants.Hood.minimumStep;
      } else if (increment1 <= 0) {
        increment1 = 0;
      }
    }

    // if (Joy1.getRawButtonPressed(1)) {
      if(PS5.getTriangleButtonPressed()){
      rpm = rpm + 100;
    // } else if (Joy1.getRawButtonPressed(2)) {
    }else if(PS5.getCrossButtonPressed()){
      rpm = rpm - 100;
    }
    if (rpm < 0) {
      rpm = 0;
    }

    // if (Joy1.getRawButtonPressed(5)) {
      if(PS5.getL1ButtonPressed()){
      if (flywheel_flag == true) {
        flywheel_flag = false;
      } else if (flywheel_flag == false) {
        flywheel_flag = true;
      }
    }

    setpoint1 = (increment1 / DEGREETOENCODER);

    double degree = hood.getSelectedSensorPosition() * DEGREETOENCODER;
    flywheel_speed = flywheel.getSelectedSensorVelocity() * 600 / 2048;

    

    // if(Joy1.getRawButtonPressed(8)){
      if(PS5.getR1ButtonPressed()){
      storeVelocityFlag = false;
      int dropVel = Collections.min(velocityList);
      SmartDashboard.putNumber("Smallest Velocity", dropVel);
      velocityList.clear();
    }

    // if(Joy1.getRawButtonPressed(6)){
      if(PS5.getTouchpadPressed()){
      storeVelocityFlag = true;
    }

    if(storeVelocityFlag == true){
      velocityList.add((int) flywheel_speed);
    }


    SmartDashboard.putNumber("Increment", increment1);
    SmartDashboard.putNumber("Hood Degree", degree);
    SmartDashboard.putNumber("Speed of Flywheel", flywheel_speed);
    SmartDashboard.putNumber("Target RPM of Flywheel ", rpm);
    SmartDashboard.putBoolean("Flag value", flywheel_flag);
    SmartDashboard.putBoolean("SensorOutOfPhase", fault.SensorOutOfPhase);
    SmartDashboard.putBoolean("Storing Velocity in List", storeVelocityFlag);


    hood.set(TalonFXControlMode.Position, setpoint1);

    if (flywheel_flag == true) {

      flywheel.set(TalonFXControlMode.Velocity, rpm);
    } else {
      flywheel.stopMotor();
      // flywheel.set(TalonFXControlMode.Velocity, 0);
    }

    // if (Joy1.getPOV() == 90) {
      if(PS5.getPOV() == 90){
      flywheel.stopMotor();
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