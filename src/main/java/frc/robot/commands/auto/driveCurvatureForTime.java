package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveCurvatureForTime;
import frc.robot.commands.drivetrain.DriveStraightCommand;
import frc.robot.subsystems.drive.DriveSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class driveCurvatureForTime extends SequentialCommandGroup {
    public final DriveSubsystem m_DriveSubsystem;

    public  driveCurvatureForTime(DriveSubsystem subsystem) {
        m_DriveSubsystem = subsystem;
    	
    	//                         (xSpeed, zRotation, isQuickTurn, time);
    	new DriveCurvatureForTime(.5, .5, false, 3, m_DriveSubsystem);

    }
}
