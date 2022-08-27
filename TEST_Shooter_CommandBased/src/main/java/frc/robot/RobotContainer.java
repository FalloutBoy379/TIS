// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ServoCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ServoSubSystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  public static XboxController joy1 = new XboxController(0);
  public static NetworkTable table;
  public static NetworkTableEntry tv, tx, ty, ta;

  

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ServoSubSystem servoSubSystem = new ServoSubSystem();

  JoystickButton moveButton = new JoystickButton(joy1, 1);
  JoystickButton initButton = new JoystickButton(joy1, 6); 

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final ServoCommand servoCommand = new ServoCommand(servoSubSystem);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    RobotContainer.table = NetworkTableInstance.getDefault().getTable("limelight");
    RobotContainer.tv = RobotContainer.table.getEntry("tv");
    RobotContainer.tx = RobotContainer.table.getEntry("tx");
    RobotContainer.ty = RobotContainer.table.getEntry("ty");
    RobotContainer.ta = RobotContainer.table.getEntry("ta");
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
      moveButton.whenActive();

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

public static boolean getTarget() {
  RobotContainer.tv = RobotContainer.table.getEntry("tv");
  return tv.getBoolean(false);
}

public static double getDistanceToGoal() {
  RobotContainer.ty = RobotContainer.table.getEntry("ty");
  return ty.getDouble(0);
}

public static double getDistanceGyroSeparationFromGoal() {
  RobotContainer.tx = RobotContainer.table.getEntry("tx");
  return tx.getDouble(0);
}

public double getAreaOfGoal() {
  RobotContainer.ta = RobotContainer.table.getEntry("ta");
  return ta.getDouble(0);
}

}
