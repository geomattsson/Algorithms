using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.HomeModels
{
    public class MessagePreview
    {
        public MessagePreview(String ID, String title, DateTime date, Boolean unread)
        {
            this.ID = ID;
            this.Title = title;
            this.Date = date;
            this.Unread = unread;
        }

        public String ID { get; private set; }
        public String Sender { get; private set; }
        public String Title { get; private set; }
        public DateTime Date { get; private set; }
        public Boolean Unread { get; private set; }
    }
}
