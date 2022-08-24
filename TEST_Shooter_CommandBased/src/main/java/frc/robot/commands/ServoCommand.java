// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ServoSubSystem;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ServoCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ServoSubSystem servoSubsystem;
  private double xError = 0, yError = 0;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ServoCommand(ServoSubSystem subsystem) {
    servoSubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    xError = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    yError = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    double[] errorArray = {xError, yError};
    servoSubsystem.setServoAngles(errorArray);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if((Math.abs(xError) <= 2) || (Math.abs(yError) <= 2)){
    //   return true;
    // }
    // else{
      return false;
  // }
}
}
