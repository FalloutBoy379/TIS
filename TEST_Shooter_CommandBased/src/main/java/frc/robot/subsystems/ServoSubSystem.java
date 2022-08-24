// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import javax.swing.text.StyleContext.SmallAttributeSet;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ServoSubSystem extends SubsystemBase {
    private final Servo xServo;
    private final Servo yServo;
  /** Creates a new ExampleSubsystem. */
  public ServoSubSystem() {
      this.xServo = new Servo(0);
      this.yServo = new Servo(1);
      this.xServo.setAngle(90);
      this.yServo.setAngle(90);
  }

  @Override
  public void periodic() {

  }

  public void setServoAngles(double[] angles){
      SmartDashboard.putNumber("xAngle", angles[0]);
      SmartDashboard.putNumber("yAngle", angles[1]);
      this.xServo.setAngle(angles[0]);
      this.yServo.setAngle(angles[1]);
  } 

  @Override
  public void simulationPeriodic() {
  }
}
