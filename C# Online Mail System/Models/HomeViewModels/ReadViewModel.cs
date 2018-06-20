using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DistNet.Models.HomeModels;

namespace DistNet.Models.HomeViewModels
{
    public class ReadViewModel
    {
        public IEnumerable<MessagePreview> MessagePreview { get; set; }
    }
}
