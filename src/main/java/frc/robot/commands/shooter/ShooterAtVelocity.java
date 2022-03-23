// package frc.robot.commands.shooter;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.shooter.Shooter;

// /**
//  * When using the command linked with a controller button, you have to do
//  * new Eject().withTimeout(seconds);
//  * 
//  * Run with a .andThen(new StopShooterCommand())
//  */
// public class ShooterAtVelocity extends CommandBase{
    
//     Shooter m_shooter;
//     double velocity;

//     public ShooterAtVelocity(Shooter m_shooter, double velocity){
//         this.m_shooter = m_shooter;
//         this.velocity = velocity;
//     }

//     @Override
//     public void initialize(){

//     }

//     @Override
//     public void execute(){
//         m_shooter.shootAtVelocity(velocity);
//     }

//     @Override
//     public boolean isFinished(){
//         return false;
//     }

//     @Override
//     public void end(boolean interrupted){
//     }

// }
