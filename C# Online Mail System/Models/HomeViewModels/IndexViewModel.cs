using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.HomeViewModels
{
    public class IndexViewModel
    {
        public String username { get; set; }
        public DateTime lastLogin { get; set; }
        public int loginsLastMonth { get; set; }
        public int unreadMessages { get; set; }
    }
}
