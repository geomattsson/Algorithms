using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.HomeViewModels
{
    public class StatsViewModel
    {
        public int Unread { get; set; }
        public int Deleted { get; set; }
        public int Total { get; set; }
    }
}
