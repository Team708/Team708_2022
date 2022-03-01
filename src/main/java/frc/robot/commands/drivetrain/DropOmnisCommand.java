package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.DriveSubsystem;

public class DropOmnisCommand extends CommandBase{

    private final DriveSubsystem m_robotDrive;

  public DropOmnisCommand(DriveSubsystem subsystem) {
    m_robotDrive = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_robotDrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_robotDrive.dropWheels();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
    
}
