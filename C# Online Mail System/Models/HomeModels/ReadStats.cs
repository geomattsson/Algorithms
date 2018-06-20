using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.HomeModels
{
    public class ReadStats
    {
        public int Total { get; set; }
        public int Unread { get; set; }
        public int Deleted { get; set; }
    }
}
