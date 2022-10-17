// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Feeder;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {
  WPI_TalonFX input = new WPI_TalonFX(Constants.Feeder.inputCANid);
  WPI_TalonFX output = new WPI_TalonFX(Constants.Feeder.outputCANid);
  WPI_TalonFX examine = new WPI_TalonFX(Constants.Feeder.examineCANid);

  DigitalInput BeamBreakerIntake = new DigitalInput(1);
  DigitalInput BeamBreakerMiddle = new DigitalInput(0);
  DigitalInput BeamBreakerUpper = new DigitalInput(2);

  Joystick joy1 = new Joystick(0);

  private final I2C.Port i2cport = I2C.Port.kOnboard;
  public final ColorSensorV3 colorSensor = new ColorSensorV3(i2cport);
  private final ColorMatch colorMatcher = new ColorMatch();
  private final Color blueTarget = new Color(0,0,255);
  private final Color redTarget = new Color(255,0,0);
  private final Color greentarget = new Color(0,255,0);
  
  boolean bIntake, bMiddle, bUpper;

  int color;

  boolean enabled = false;


  /** Creates a new Feeder. */
  public Feeder() {
    output.configFactoryDefault();
    output.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    output.setSensorPhase(true);
    output.setInverted(false);
    output.configNominalOutputForward(0, 30);
    output.configNominalOutputReverse(0, 30);
    output.configPeakOutputForward(1, 30);
    output.configPeakOutputReverse(-1, 30);
    output.configAllowableClosedloopError(0, 0, 30);
    output.config_kF(0, 0.0, 0);
    output.config_kP(0, 0.02, 30);
    output.config_kI(0, 0, 30);
    output.config_kD(0, 0, 30);
    output.configClosedloopRamp(0.1);
    output.configOpenloopRamp(0.1);
    

    input.configFactoryDefault();
    input.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    input.setSensorPhase(true);
    input.setInverted(false);
    input.configNominalOutputForward(0, 30);
    input.configNominalOutputReverse(0, 30);
    input.configPeakOutputForward(1, 30);
    input.configPeakOutputReverse(-1, 30);
    input.configAllowableClosedloopError(0, 0, 30);
    input.config_kF(0, 0.0, 0);
    input.config_kP(0, 0.02, 30);
    input.config_kD(0, 0, 30);
    input.config_kI(0, 0, 30);
    input.configClosedloopRamp(0.1);
    input.configOpenloopRamp(0.1);



    examine.configFactoryDefault();
    examine.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
    examine.setSensorPhase(true);
    examine.setInverted(false);
    examine.configNominalOutputForward(0, 30);
    examine.configNominalOutputReverse(0, 30);
    examine.configPeakOutputForward(1, 30);
    examine.configPeakOutputReverse(-1, 30);
    examine.configAllowableClosedloopError(0, 0, 30);
    examine.config_kF(0, 0.0, 0);
    examine.config_kP(0, 0.02, 30);
    examine.config_kD(0, 0, 30);
    examine.config_kI(0, 0, 30);
    examine.configClosedloopRamp(0.5);
    examine.configOpenloopRamp(0.2);

    output.setSelectedSensorPosition(0);
    colorMatcher.addColorMatch(blueTarget);
    colorMatcher.addColorMatch(greentarget);
    colorMatcher.addColorMatch(redTarget);

  }


  public void enable(){
    enabled = true;
  }

  public void disable(){
    enabled = false;
  }

  @Override
  public void periodic() {
    if(enabled){
    Color detectedColor = colorSensor.getColor();
    ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

    bIntake = BeamBreakerIntake.get();
    bMiddle = BeamBreakerMiddle.get();
    bUpper = BeamBreakerUpper.get();

    if(match.color == redTarget){
      color = 1;
    }
    else if(match.color == blueTarget){
      color = 2;
    }


    if(bIntake == true && bMiddle == false && bUpper == false){
      input.set(TalonFXControlMode.PercentOutput, 0.2);
      if(match.color == redTarget){
        input.set(TalonFXControlMode.PercentOutput, 0.2);
        examine.set(TalonFXControlMode.PercentOutput, -0.9);
      }
      else if(match.color == blueTarget){
        input.set(TalonFXControlMode.PercentOutput, 0.2);
        examine.set(TalonFXControlMode.PercentOutput, 0.9);
      }


    }
    else if(bMiddle == true && bUpper == false && bIntake == false){
      input.set(TalonFXControlMode.PercentOutput, 0.2);
        examine.set(TalonFXControlMode.PercentOutput, -0.9);
    }


    else if (bIntake == true && bMiddle == false && bUpper == true){
      if(match.color == redTarget){
        input.set(TalonFXControlMode.PercentOutput, 0.2);
        examine.set(TalonFXControlMode.PercentOutput, -0.9);
      }
      else if(match.color == blueTarget){
        input.set(TalonFXControlMode.PercentOutput, 0.2);
        examine.set(TalonFXControlMode.PercentOutput, 0.9);
      }
    }

    else if (bIntake == false && bMiddle == true && bUpper == true && color == 1){
      input.set(TalonFXControlMode.PercentOutput, 0);
      examine.set(TalonFXControlMode.PercentOutput, 0);
    }

    else if (bIntake == false && bMiddle == true && bUpper == true && color == 2){
      input.set(TalonFXControlMode.PercentOutput, 0.2);
      examine.set(TalonFXControlMode.PercentOutput, 0.9);
    }

    else if (bIntake == true && bMiddle == true && bUpper == true){
      input.set(TalonFXControlMode.PercentOutput, 0);
      examine.set(TalonFXControlMode.PercentOutput, 0);
    }
    // This method will be called once per scheduler run
  }
}
}
