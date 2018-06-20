using DistNet.Models.GroupModel;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace DistNet.Models
{
    public class Mail
    {
        public Guid MailId { get; set; }
        public DateTime Time { get; set; }
        public String Sender { get; set; }
        [Required]
        public String MailBody { get; set; }
        [Required]
        public String MailTitle { get; set; }
        public ICollection<MailUserSpecification> Specifications { get; set; }

        public Mail(String Sender, String MailBody, String MailTitle)
        {
            this.MailId = Guid.NewGuid();
            this.MailTitle = MailTitle;
            this.MailBody = MailBody;
            this.Time = DateTime.Now;
            this.Sender = Sender;
        }

        public Mail()
        {
        }
    }
}
