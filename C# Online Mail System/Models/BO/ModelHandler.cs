using System;
using System.Collections.Generic;
using DistNet.Models.GroupModel;
using DistNet.Data.DbEntities;
using DistNet.Data;
using Microsoft.EntityFrameworkCore;
using System.Linq;
using DistNet.Models.HomeModels;

namespace DistNet.Models
{
    public class ModelHandler : IModelHandler
    {
        MailDBContext dbContext = new MailDBContext();
        ApplicationDbContext UserDbContext = new ApplicationDbContext();

        public Mail GetMail(Guid MailId)
        {
            MailUserSpecification spec = dbContext.MailUserSpecification.
                Where(s => s.MailUserSpecificationId == MailId).Include(i => i.Mail).First();
            if(!spec.Deleted)
            {
                spec.Unread = false;
                dbContext.SaveChanges();
                return spec.Mail;
            }
            return null;
        }

        public int UnreadMessages(String User)
        {
            int number = 0;
            List<MailUserSpecification> list = dbContext.MailUserSpecification.
                Where(m => m.UserEmail == User).ToList();
            foreach(MailUserSpecification m in list)
                if (m.Unread) number++;
            return number;
        }

        public int NrOfLogins(String User)
        {
            int number = 0;
            List<Login> List = UserDbContext.Login
                .Where(login => login.DateTime > DateTime.Now.AddDays(-30))
                .Include(login => login.ApplicationUser)
                .Where(login => login.ApplicationUser.Id.ToString().Equals(User))
                .ToList();
    
            foreach(Login login in List)
                number++;
            return number;
        }

        public DateTime LastLogin(String User)
        {
            Login l = UserDbContext.Login
                .Include(u => u.ApplicationUser)
                .Where(u => u.ApplicationUser.Id.ToString().Equals(User))
                .OrderByDescending(o => o.DateTime).FirstOrDefault();
            if (l != null) return l.DateTime;
            else return DateTime.Now;
        }

        public ReadStats GetReadStats(String User)
        {
            ReadStats stat = new ReadStats();
            List<MailUserSpecification> list = dbContext.MailUserSpecification.
                Where(m => m.UserEmail == User).ToList();
            foreach(MailUserSpecification spec in list)
            {
                if (spec.Deleted) stat.Deleted += 1;
                if (spec.Unread) stat.Unread += 1;
            }
            stat.Total = list.Count;
            return stat;
        }
        
        public void RemoveGroup(Guid GroupId)
        {
            Group Group = null;
            List<Group> list = dbContext.Groups.ToList<Group>();
            foreach (Group group in list)
            {
                if (group.GroupId.Equals(GroupId))
                    Group = group;
            }
            dbContext.Remove(Group);
            dbContext.SaveChanges();
        }

        public void RemoveMail(Guid MailId)
        {
            MailUserSpecification spec = dbContext.MailUserSpecification.
                Where(s => s.MailUserSpecificationId == MailId).First();
            spec.Deleted = true;
            dbContext.SaveChanges();
        }

        public void SendMail(String Sender, List<String> Recipients, string MailTitle, string MailBody)
        {
            Mail mail = new Mail(Sender, MailBody, MailTitle);
            dbContext.Add(mail);

            foreach (String recipient in Recipients)
            {
                if (recipient.Contains("@"))
                {
                    if (dbContext.Mapping.Where(m => m.UserEmail.Equals(recipient))
                        .FirstOrDefault() != null)
                    {
                        MailUserSpecification spec = new MailUserSpecification
                        {
                            Mail = mail,
                            UserEmail = recipient,
                            Unread = true,
                            Deleted = false
                        };
                        dbContext.Add(spec);
                    }
                }
                else
                {
                    if (dbContext.Groups.Where(m => m.GroupName.Equals(recipient))
                        .FirstOrDefault() != null)
                    {
                        Group group = dbContext.Groups.Where(g => g.GroupName == recipient).
                        Include(i => i.ListOfUserMapping).FirstOrDefault();
                        if (group != null)
                        {
                            foreach (GroupToUserMapping map in group.ListOfUserMapping)
                            {
                                MailUserSpecification spec = new MailUserSpecification
                                {
                                    Mail = mail,
                                    UserEmail = map.UserEmail,
                                    Unread = true,
                                    Deleted = false
                                };
                                dbContext.Add(spec);
                            }
                        }
                    }
                }
            }
            dbContext.SaveChanges();
        }

        public List<SendersModel> GetAllSenders(String UserName)
        {
            List<SendersModel> list = new List<SendersModel>();
            List<MailUserSpecification> specs = dbContext.MailUserSpecification.
                Where(s => s.UserEmail == UserName).Include(m => m.Mail).ToList();
            List<String> r = new List<string>();
            foreach (MailUserSpecification spec in specs)
            {
                if (!r.Contains(spec.Mail.Sender))
                {
                    if (!spec.Deleted)
                    {
                        list.Add(new SendersModel(spec.Mail.Sender, spec.Mail.Sender));
                        r.Add(spec.Mail.Sender);
                    }
                }
            }
            return list;
        }

        public List<PreviewsModel> GetPreviews(string User, string Name)
        {
            List<PreviewsModel> list = new List<PreviewsModel>();
            List<MailUserSpecification> specs = dbContext.MailUserSpecification.
                Where(s => s.UserEmail == User).Include(m => m.Mail).ToList();
            foreach(MailUserSpecification spec in specs)
            {
                Mail m = spec.Mail;
                if(m.Sender.Equals(Name))
                {
                    if(!spec.Deleted)
                    {
                        list.Add(new PreviewsModel(spec.MailUserSpecificationId.ToString(), m.MailTitle, m.Time, spec.Unread));
                    }
                }
            }

            return list;
        }

        public List<ApplicationUser> GetAllUsers()
        {
            IQueryable<ApplicationUser> rtn = UserDbContext.Users;
            var list = rtn.ToList();
            return list;
        }

        public Boolean CreateGroup(String GroupName)
        {
            List<GroupToUserMapping> ListOfMapping = new List<GroupToUserMapping>();
            Group RecipientGroup = new Group(GroupName);
            List<Group> groups = dbContext.Groups.Where(g => g.GroupName == GroupName).ToList();
            if (groups.Count > 0)
                return false;
            dbContext.Add(RecipientGroup);
            dbContext.SaveChanges();
            return true;
        }

        public void AddToGroup(String UserName, Guid Id)
        {
            Group group = dbContext.Groups.Where(g => g.GroupId == Id).
                Include(g => g.ListOfUserMapping).First();
            group.ListOfUserMapping.Add(new GroupToUserMapping(UserName, group));
            dbContext.SaveChanges();
        }

        public void RemoveFromGroup(String UserName, Guid Id)
        {
            Group group = dbContext.Groups.Where(g => g.GroupId == Id).
                Include(g => g.ListOfUserMapping).First();
            GroupToUserMapping map = group.ListOfUserMapping.Where(m => m.UserEmail == UserName).FirstOrDefault();
            if (map != null)
            {
                group.ListOfUserMapping.Remove(map);
                dbContext.SaveChanges();
            }
        }

        public List<Group> GetAllGroups()
        {
            List<Group> Groups = new List<Group>();
            Groups = dbContext.Groups.Include(g => g.ListOfUserMapping).ToList();
            return Groups;
        }

        
    }
}
