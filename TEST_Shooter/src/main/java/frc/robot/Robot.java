// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Robot extends TimedRobot {

  double p_flywheel, i_flywheel, d_flywheel, f_flywheel;
  WPI_TalonFX hood = new WPI_TalonFX(Constants.Hood.CANid);
  WPI_TalonFX flywheel = new WPI_TalonFX(Constants.Shooter.CANid);
  WPI_TalonFX turret = new WPI_TalonFX(9);

  LinearFilter limelightXFilter = LinearFilter.movingAverage(60);
  LinearFilter limelightYFilter = LinearFilter.movingAverage(60);

  SerialPort arduino = new SerialPort(115200, Port.kUSB1);

  double rpm = 0;
  double flywheel_speed;
  boolean flywheel_flag = false;
  double increment1;
  double setpoint1;

  double turret_increment;
  Joystick Joy1 = new Joystick(0);
  // PS4Controller PS5 = new PS4Controller(0);
  Faults fault = new Faults();


  List<Integer> velocityList = new ArrayList<Integer>();
  boolean storeVelocityFlag = false;


  

  double prevYerror = 0, prevXError = 0;

  @Override
  public void robotInit() {

    hood.configFactoryDefault();
    hood.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    hood.setSensorPhase(true);
    hood.setInverted(false);
    hood.configNominalOutputForward(0, 30);
    hood.configNominalOutputReverse(0, 30);
    hood.configPeakOutputForward(0.1, 30);
    hood.configPeakOutputReverse(-0.1, 30);
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

    turret.configFactoryDefault();
    turret.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    turret.setSensorPhase(true);
    turret.setInverted(false);
    turret.configNominalOutputForward(0, 30);
    turret.configNominalOutputReverse(0, 30);
    turret.configPeakOutputForward(0.15, 30);
    turret.configPeakOutputReverse(-0.15, 30);
    turret.configAllowableClosedloopError(0, 120, 30);
    turret.config_kF(0, Constants.Turret.kf);
    turret.config_kP(0, Constants.Turret.kp);
    turret.config_kI(0, Constants.Turret.ki);
    turret.config_kD(0, Constants.Turret.kd);
    turret.configClosedloopRamp(0.2);
    turret.setNeutralMode(NeutralMode.Brake);
    hood.setSelectedSensorPosition(0);

    //Initial angle for hood is 40 degrees
    increment1 = 40;
    setpoint1 = (increment1 / Constants.Hood.DEGREETOENCODER);
    hood.set(TalonFXControlMode.Position, setpoint1);
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
    turret_increment = 0;
    // hood.setSelectedSensorPosition(0);
    turret.setSelectedSensorPosition(0);

    SmartDashboard.putNumber("P_Hood", Constants.Hood.kp);
    SmartDashboard.putNumber("I_Hood", Constants.Hood.ki);
    SmartDashboard.putNumber("D_Hood", Constants.Hood.kd);
    SmartDashboard.putNumber("F_Hood", Constants.Hood.kf);

    SmartDashboard.putNumber("P_Turret", Constants.Turret.kp);
    SmartDashboard.putNumber("I_Turret", Constants.Turret.ki);
    SmartDashboard.putNumber("D_Turret", Constants.Turret.kd);
    SmartDashboard.putNumber("F_Turret", Constants.Turret.kf);

    SmartDashboard.putNumber("P_Flywheel", Constants.Shooter.kp);
    SmartDashboard.putNumber("I_Flywheel", Constants.Shooter.ki);
    SmartDashboard.putNumber("D_Flywheel", Constants.Shooter.kd);
    SmartDashboard.putNumber("F_Flywheel", Constants.Shooter.kf);
    

    
  }


  @Override
  public void teleopPeriodic() {

    if (arduino.getBytesReceived() > 0) {
      SmartDashboard.putString("Received: ", arduino.readString());
    }

    double xError = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double yError = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);

    SmartDashboard.putNumber("Limelight x Error = ", xError);
    SmartDashboard.putNumber("Limelight y Error = ", yError);
    double xError1 = limelightXFilter.calculate(xError);
    xError1 = xError1 * 3;

    p_flywheel = SmartDashboard.getNumber("P_Flywheel", Constants.Shooter.kp);
    i_flywheel = SmartDashboard.getNumber("I_Flywheel", Constants.Shooter.ki);
    d_flywheel = SmartDashboard.getNumber("D_Flywheel", Constants.Shooter.kd);
    f_flywheel = SmartDashboard.getNumber("F_Flywheel", Constants.Shooter.kf);

    flywheel.config_kP(0, p_flywheel);
    flywheel.config_kI(0, i_flywheel);
    flywheel.config_kD(0, d_flywheel);
    flywheel.config_kF(0, f_flywheel);

      if(Joy1.getRawButton(4) && !Joy1.getRawButton(3)){
      if (increment1 < Constants.Hood.maxAngle) {
        increment1 = increment1 + Constants.Hood.minimumStep;
      } else if (increment1 >= Constants.Hood.maxAngle) {
        increment1 = Constants.Hood.maxAngle;
      }
    } else if (Joy1.getRawButton(3) && !Joy1.getRawButton(4)) {
      if (increment1 > Constants.Hood.minAngle) {
        increment1 = increment1 - Constants.Hood.minimumStep;
      } else if (increment1 <= 0) {
        increment1 = 0;
      }
    }
    double yError1 = limelightYFilter.calculate(yError);



    // if(Math.abs(yError1 - prevYerror) < 2){                                //Uncomment this line to make turret and hood stay at last position when Limelight suddenly drops to 0
      increment1 = 40 - yError1;                                         
      turret_increment = xError1;


      //LIMIT THE ANGLES FOR HOOD
      if(increment1 >= 40){
        increment1 = 40;
      }
      else if(increment1 <= 0){
        increment1 = 0;
      }
    // }
    

    if (Joy1.getRawButtonPressed(1)) {
      rpm = rpm + 100;
    } else if (Joy1.getRawButtonPressed(2)) {
      rpm = rpm - 100;
    }
    if (rpm < 0) {
      rpm = 0;
    }

    if (Joy1.getPOV() == 90) {
      if (flywheel_flag == true) {
        flywheel_flag = false;
      } else if (flywheel_flag == false) {
        flywheel_flag = true;
      }
    }

    setpoint1 = (increment1 / Constants.Hood.DEGREETOENCODER);


    double degree = hood.getSelectedSensorPosition() * Constants.Hood.DEGREETOENCODER;
    flywheel_speed = flywheel.getSelectedSensorVelocity() * 600 / 2048;

    

    if(Joy1.getRawButtonPressed(8)){
      storeVelocityFlag = false;
      int dropVel = Collections.min(velocityList);
      SmartDashboard.putNumber("Smallest Velocity", dropVel);
      velocityList.clear();
    }

    if(Joy1.getPOV() == 180){
      storeVelocityFlag = true;
    }

    if(storeVelocityFlag == true){
      velocityList.add((int) flywheel_speed);
    }


    SmartDashboard.putNumber("Hood Command", increment1);
    SmartDashboard.putNumber("Hood Actual Degree", degree);
    SmartDashboard.putNumber("Speed of Flywheel", flywheel_speed);
    SmartDashboard.putNumber("Target RPM of Flywheel ", rpm);
    SmartDashboard.putBoolean("Flag value", flywheel_flag);
    SmartDashboard.putBoolean("Storing Velocity in List", storeVelocityFlag);

    hood.config_kP(0, SmartDashboard.getNumber("P_Hood", Constants.Hood.kp));
    hood.config_kI(0, SmartDashboard.getNumber("I_Hood", Constants.Hood.ki));
    hood.config_kD(0, SmartDashboard.getNumber("D_Hood", Constants.Hood.kd));
    hood.config_kF(0, SmartDashboard.getNumber("F_Hood", Constants.Hood.kf));

    hood.set(TalonFXControlMode.Position, setpoint1);

    if (flywheel_flag == true) {

      flywheel.set(TalonFXControlMode.Velocity, rpm);
    } else {
      flywheel.stopMotor();
    }

    if(Joy1.getRawButtonPressed(6)){
      if (turret_increment < Constants.Turret.maxAngle) {
        turret_increment = turret_increment + Constants.Turret.minimumStep;
      } else if (turret_increment >= Constants.Turret.maxAngle) {
        turret_increment = Constants.Turret.maxAngle;
      }
    } else if (Joy1.getRawButtonPressed(5)) {
      if (turret_increment > Constants.Turret.minAngle) {
        turret_increment = turret_increment - Constants.Turret.minimumStep;
      } else if (turret_increment <= 0) {
        turret_increment = Constants.Turret.minAngle;
      }
    }

    

    if(turret_increment>=Constants.Turret.maxAngle){
      turret_increment = Constants.Turret.maxAngle;
    }
    else if(turret_increment<=Constants.Turret.minAngle){
      turret_increment = Constants.Turret.minAngle;
    }

    double turret_setpoint = (turret_increment * Constants.Turret.DEGREETOENCODER);

    
    SmartDashboard.putNumber("turret target Pos", turret_increment);
    SmartDashboard.putNumber("turret target Pos in counts", turret_setpoint);
    SmartDashboard.putNumber("Turret Current Pos", Constants.Turret.ENCODERTODEGREE * turret.getSelectedSensorPosition());
    turret.set(TalonFXControlMode.Position, turret_setpoint);

    prevXError = xError1;
    prevYerror = yError1;

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