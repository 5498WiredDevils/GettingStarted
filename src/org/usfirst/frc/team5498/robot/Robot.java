/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5498.robot;


// Make a change for staging

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
private TalonSRX _talonLeft = new TalonSRX(0);
	private TalonSRX _talonRight = new TalonSRX(1);
	private TalonSRX _talonRight2 = new TalonSRX(3);
	private TalonSRX _talonLeft2 = new TalonSRX(4);		
	private Joystick m_stick = new Joystick(0);
	private Timer m_timer = new Timer();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		System.out.println("Initialize robot");
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		m_timer.reset();
		m_timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		if (m_timer.get() < 2.0) {
//			m_robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
			_talonLeft.set(ControlMode.PercentOutput, 0.5);
			_talonRight.set(ControlMode.PercentOutput, 0.5);
		} else {
//			m_robotDrive.stopMotor(); // stop robot
			_talonLeft.set(ControlMode.PercentOutput, 0);
			_talonRight.set(ControlMode.PercentOutput, 0);
		}
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		// This won't work with Talon SRX
//		m_robotDrive.arcadeDrive(m_stick.getY(), m_stick.getX());
		double forward = -1 * m_stick.getY();
		double turn = m_stick.getX();
		forward = Deadband(forward);
		turn = Deadband(turn);
		
		_talonLeft.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, +turn);
		_talonRight.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);
		System.out.print("Set left and right motors.");
	}
	
	double Deadband(double value) {
		/* Upper deadband */
		if (value >= +0.05) 
			return value;
		
		/* Lower deadband */
		if (value <= -0.05)
			return value;
		
		/* Outside deadband */
		return 0;
	}


	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	public TalonSRX get_talonRight2() {
		return _talonRight2;
	}

	public void set_talonRight2(TalonSRX _talonRight2) {
		this._talonRight2 = _talonRight2;
	}

	public TalonSRX get_talonLeft2() {
		return _talonLeft2;
	}

	public void set_talonLeft2(TalonSRX _talonLeft2) {
		this._talonLeft2 = _talonLeft2;
	}
}
