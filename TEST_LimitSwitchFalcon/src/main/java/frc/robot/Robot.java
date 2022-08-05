// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public WPI_TalonFX motor = new WPI_TalonFX(1);

  public XboxController controller = new XboxController(0); 

  public int p=0, i=0, d=0, f=0;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    motor.configFactoryDefault();
    motor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    motor.setSensorPhase(false);
    motor.setInverted(false);
    motor.configNominalOutputForward(0, 30);
    motor.configNominalOutputReverse(0, 30);
    motor.configPeakOutputForward(1, 30);
    motor.configPeakOutputReverse(-1, 30);
    motor.configAllowableClosedloopError(0, 0, 30);
    motor.config_kF(0, f);
    motor.config_kP(0, p);
    motor.config_kD(0, d);
    motor.config_kI(0, i);
    motor.setNeutralMode(NeutralMode.Coast);
    motor.configClosedloopRamp(5);

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    SmartDashboard.putNumber("Target RPM", 0);
    SmartDashboard.putNumber("P", 0);
    SmartDashboard.putNumber("I", 0);
    SmartDashboard.putNumber("D", 0);
    SmartDashboard.putNumber("F", 0);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double targetRPM = SmartDashboard.getNumber("Target RPM", 0);
    double p = SmartDashboard.getNumber("P", 0);
    double i = SmartDashboard.getNumber("I", 0);
    double d = SmartDashboard.getNumber("D", 0);
    double f = SmartDashboard.getNumber("F", 0);

    motor.config_kP(0, p);
    motor.config_kI(0, i);
    motor.config_kD(0, d);
    motor.config_kF(0, f);

    
    if(controller.getPOV() == 135){
      motor.set(ControlMode.Velocity, targetRPM);
    }
    else if(controller.getBButton()){
      motor.set(ControlMode.Velocity, targetRPM);
    }

    SmartDashboard.putNumber("Encoder counts/100ms", motor.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Button Value ", controller.getPOV());
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
