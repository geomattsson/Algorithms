using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.HomeModels
{
    public class ReadMail
    {
        public ReadMail() { }

        public ReadMail(Guid ID, string sender, string title, DateTime date, string message)
        {
            this.ID = ID;
            this.Sender = sender;
            this.Title = title;
            this.Date = date;
            this.Message = message;
        }

        public Guid ID { get; set; }
        public String Sender { get; set; }
        public String Title { get; set; }
        public DateTime Date { get; set; }
        public String Message { get; set; }
    }
}
