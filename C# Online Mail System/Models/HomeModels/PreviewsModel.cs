using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.HomeModels
{
    public class PreviewsModel
    {
        public PreviewsModel(string id, string title, DateTime date, Boolean unread)
        {
            this.Id = id;
            this.Title = title;
            this.Date = date;
            this.Unread = unread;
        }

        public string Id { get; set; }
        public string Title { get; set; }
        public DateTime Date { get; set; }
        public Boolean Unread { get; set; }
    }
}
