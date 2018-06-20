using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.HomeModels
{
    public class SendersModel
    {
        public SendersModel() { }
        public SendersModel(string Id, string Name)
        {
            this.Id = Id;
            this.Name = Name;
        }

        public string Id { get; set; }
        public string Name { get; set; }
    }
}
