using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models
{
    public class MailUserSpecification
    {
        public Guid MailUserSpecificationId { get; set; }
        public String UserEmail { get; set; }
        public Boolean Unread { get; set; }
        public Boolean Deleted { get; set; }
        public Mail Mail { get; set; }

        public MailUserSpecification()
        {

        }

    }
}
