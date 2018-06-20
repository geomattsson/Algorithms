using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models
{
    public class Login
    {
        public Guid LoginID { get; set; }
        public DateTime DateTime { get; set; }
        public ApplicationUser ApplicationUser { get; set; }
    
        public Login(DateTime dateTime, String userName)
        {
            this.DateTime = dateTime;
        }

        public Login() { }
    }
}
