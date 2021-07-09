// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  // Drive Controllers
  public static final int kLeftMotorFrontPort = 0; // PWM on victors, can id on Falcons
  public static final int kLeftMotorRearPort = 2; // PWM on victors, can id on Falcons
  public static final int kRightMotorFrontPort = 1; // PWM on victors, can id on Falcons
  public static final int kRightMotorRearPort = 3; // PWM on victors, can id on Falcons
  private final Victor m_LeftMotor = new Victor(kLeftMotorFrontPort);
  private final Victor m_LeftFollowerMotor = new Victor(kLeftMotorRearPort);
  private final SpeedControllerGroup m_LeftMotors = new SpeedControllerGroup(m_LeftMotor, m_LeftFollowerMotor);
  private final Victor m_rightMotor = new Victor(kRightMotorFrontPort);
  private final Victor m_rightFollowerMotor = new Victor(kRightMotorRearPort);
  private final SpeedControllerGroup m_RightMotors = new SpeedControllerGroup(m_rightMotor, m_rightFollowerMotor);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_LeftMotors, m_RightMotors);
  private final XboxController m_GamePad = new XboxController(0);
  private final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_LeftMotors.setInverted(true);
    m_RightMotors.setInverted(false);
    m_robotDrive.setRightSideInverted(false);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotDrive.tankDrive(.5, .5); // drive forwards half speed
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(m_GamePad.getTriggerAxis(GenericHID.Hand.kRight) - m_GamePad.getTriggerAxis(GenericHID.Hand.kLeft), m_GamePad.getX(GenericHID.Hand.kLeft));
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}
