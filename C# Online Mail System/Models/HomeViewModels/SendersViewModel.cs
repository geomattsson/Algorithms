using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DistNet.Models.HomeModels;

namespace DistNet.Models.HomeViewModels
{
    public class SendersViewModel
    {
        public IEnumerable<SendersModel> Senders { get; set; }
        public int TotalMessages { get; set; }
        public int RemovedMessages { get; set; }
        public int UnreadMessages { get; set; }
    }
}
