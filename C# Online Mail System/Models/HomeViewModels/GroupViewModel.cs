using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.HomeViewModels
{
    public class GroupViewModel
    {
        public GroupViewModel() { }
        public GroupViewModel(Guid Id, string Name, Boolean Remove)
        {
            this.Id = Id;
            this.Name = Name;
            this.Remove = Remove;
        }

        public Guid Id { get; set; }
        public string Name { get; set; }
        public Boolean Remove { get; set; }
    }
}
