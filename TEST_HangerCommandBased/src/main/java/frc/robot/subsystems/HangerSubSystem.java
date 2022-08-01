package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HangerSubSystem extends SubsystemBase {

    private WPI_TalonFX motor;
    private Solenoid sol;

    private double p = 0;
    private double i = 0;
    private double d = 0;
    private double f = 0;
    private String canid;
    private double MAXPOSITION = 0;
    private double MINPOSITION = 0;

    private double TargetPoint = 0;

    public HangerSubSystem(int CANID, int CANIDPCM, int SolenoidChannel) {
        this.canid = String.valueOf(CANID);

        this.motor = new WPI_TalonFX(CANID);
        this.sol = new Solenoid(CANIDPCM, PneumaticsModuleType.CTREPCM, SolenoidChannel);

        this.motor.configFactoryDefault();
        this.motor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);
        this.motor.setSensorPhase(true);
        this.motor.setInverted(false);
        this.motor.configNominalOutputForward(0, 30);
        this.motor.configNominalOutputReverse(0, 30);
        this.motor.configPeakOutputForward(0.9, 30);
        this.motor.configPeakOutputReverse(-0.9, 30);
        this.motor.configAllowableClosedloopError(0, 0, 30);
        this.motor.config_kF(0, f);
        this.motor.config_kP(0, p);
        this.motor.config_kI(0, i);
        this.motor.config_kD(0, d);
        this.motor.setNeutralMode(NeutralMode.Brake);
        this.motor.setSelectedSensorPosition(0);
        this.setPIDF(0.6, 0, 0, 0);
        SmartDashboard.putNumber("P" + canid, this.p);
        SmartDashboard.putNumber("I" + canid, this.i);
        SmartDashboard.putNumber("D" + canid, this.d);
        SmartDashboard.putNumber("F" + canid, this.f);

        this.sol.set(false);
    }

    public void setPIDF(double p2, double i2, double d2, double f2) {
        this.p = p2;
        this.i = i2;
        this.d = d2;
        this.f = f2;
        this.motor.config_kF(0, this.f);
        this.motor.config_kP(0, this.p);
        this.motor.config_kI(0, this.i);
        this.motor.config_kD(0, this.d);
    }

    public void setTarget(double target) {
        if (target > MAXPOSITION) {
            target = MAXPOSITION;
        } else if (target < MINPOSITION) {
            target = MINPOSITION;
        }
        this.TargetPoint = target;
    }

    public void setInverted(boolean state){
        motor.setInverted(state);
    }
    
    public void setPiston(boolean state) {
        this.sol.set(state);
    }

    public void togglePiston() {
        this.sol.toggle();
    }

    public void setLimits(double MIN, double MAX) {
        this.MAXPOSITION = MAX;
        this.MINPOSITION = MIN;
    }

    public boolean isBusy(){
        double percent = motor.getMotorOutputPercent();
        if(percent < 0.1){
            return false;
        }
        else{
            return true;
        }
        
    }

    @Override
    public void periodic() {
        double p = SmartDashboard.getNumber("P"+canid, 0);
        double i = SmartDashboard.getNumber("I"+canid, 0);
        double d = SmartDashboard.getNumber("D"+canid, 0);
        double f = SmartDashboard.getNumber("F"+canid, 0);
        this.setPIDF(p, i, d, f);
        

        SmartDashboard.putNumber("Motor Current"+canid, motor.getStatorCurrent());
        SmartDashboard.putNumber("target point"+canid, this.TargetPoint);
        SmartDashboard.putNumber("Error"+canid, motor.getClosedLoopError(0));
        SmartDashboard.putNumber("Closed Loop Target"+canid, motor.getClosedLoopTarget(0));
        motor.set(TalonFXControlMode.Position,this.TargetPoint);
        
    }

}
