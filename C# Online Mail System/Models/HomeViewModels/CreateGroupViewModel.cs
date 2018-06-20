using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.HomeViewModels
{
    public class CreateGroupViewModel
    {
        [Required]
        [DataType(DataType.Text)]
        public string Name { get; set; }
    }
}
