// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.goToTarget;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.HangerSubSystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  public Joystick joystick1 = new Joystick(0);

  JoystickButton cross = new JoystickButton(joystick1, 1);
  public JoystickButton square = new JoystickButton(joystick1, 3);
  JoystickButton triangle = new JoystickButton(joystick1, 4);
  JoystickButton circle = new JoystickButton(joystick1, 2);


  private final int hanger1CANID = 10;
  private final int hanger2CANID = 2;

  public double hanger1Setpoint;

  private final int PCMCANID = 0;
  private final int hanger1SolenoidChannel = 1;
  private final int hanger2SolenoidChannel = 2;

  private Compressor comp = new Compressor(PneumaticsModuleType.CTREPCM);


  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final HangerSubSystem hanger1 = new HangerSubSystem(hanger1CANID, PCMCANID, hanger1SolenoidChannel);
  private final HangerSubSystem hanger2 = new HangerSubSystem(hanger2CANID, PCMCANID, hanger2SolenoidChannel);

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    comp.enableDigital();
    hanger2.setLimits(0, 240000);
    // hanger2.setPIDF(1.1, 0, 0, 0);

    hanger1.setLimits(0, 253000);
    // hanger1.setPIDF(1.1, 0, 0, 0);

    // Configure the button bindings
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // cross.whenPressed();
    // triangle.whenPressed();

    // circle.whenPressed();
    // square.whenPressed();

    cross.whenPressed(new SequentialCommandGroup(new goToTarget(hanger1, 10000), new goToTarget(hanger2, 10000), new goToTarget(hanger1, 0), new goToTarget(hanger2, 0)));

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
}
