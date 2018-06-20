using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.HomeViewModels
{
    public class GroupsViewModel
    {
        public string Id { get; set; }
        [Required]
        public Boolean Remove { get; set; }
        public List<GroupViewModel> Active { get; set; }
        public List<GroupViewModel> Available { get; set; }
    }
}
