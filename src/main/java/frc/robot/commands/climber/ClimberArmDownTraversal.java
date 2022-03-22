// package frc.robot.commands.climber;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Constants;
// import frc.robot.OI;
// import frc.robot.subsystems.climber.Climber;
// import frc.robot.subsystems.drive.DriveSubsystem;

// public class ClimberArmDownTraversal extends CommandBase{
    
//     DriveSubsystem m_driveSubsystem;
//     Climber m_climber;

//     public ClimberArmDownTraversal(DriveSubsystem m_driveSubsystem, Climber m_climber){
//         this.m_driveSubsystem = m_driveSubsystem;
//         this.m_climber = m_climber;

//         addRequirements(m_driveSubsystem);
//         addRequirements(m_climber);
//     }

//     @Override
//     public void initialize(){
//         m_driveSubsystem.setMotorAmps();
//         m_climber.activatePTO();
//         m_driveSubsystem.resetEncoders();
//     }

//     @Override
//     public void execute(){
//         // if (Math.abs(OI.getClimberLeftY()) > Constants.ControllerConstants.kClimberDeadBandLeftY)
//         //    m_driveSubsystem.arcadeDrive(OI.getClimberLeftY(), 0.0);
//         // else        
//            m_driveSubsystem.arcadeDrive(.8, 0);
//     }

//     @Override
//     public boolean isFinished(){
//             return (Math.abs(m_driveSubsystem.getLeftEncoder().getPosition()) > .9
//                 || Math.abs(m_driveSubsystem.getRightEncoder().getPosition()) > .9);
//     }

//     @Override
//     public void end(boolean interrupted){

//     }

// }
