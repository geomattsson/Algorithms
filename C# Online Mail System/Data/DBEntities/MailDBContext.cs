using DistNet.Models;
using DistNet.Models.GroupModel;
using Microsoft.EntityFrameworkCore;


namespace DistNet.Data.DbEntities
{
    public class MailDBContext : DbContext
    {
        public DbSet<Mail> Mails { get; set; }
        public DbSet<Group> Groups { get; set; }
        public DbSet<GroupToUserMapping> Mapping { get; set; }
        public DbSet<MailUserSpecification> MailUserSpecification { get; set; }


        public MailDBContext(){}

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                var connection = @"Server=(localdb)\mssqllocaldb;Database=DistNetDB;Trusted_Connection=True;";
                optionsBuilder.UseSqlServer(connection);
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Group>()
                .HasMany(c => c.ListOfUserMapping)
                .WithOne(g => g.Group)
                .OnDelete(DeleteBehavior.Cascade);

   
            modelBuilder.Entity<Mail>()
                .HasMany(mail => mail.Specifications)
                .WithOne(mail => mail.Mail)
                .OnDelete(DeleteBehavior.SetNull);
        }
    }
}
