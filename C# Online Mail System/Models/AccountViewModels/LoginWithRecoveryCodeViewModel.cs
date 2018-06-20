using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models.AccountViewModels
{
    public class LoginWithRecoveryCodeViewModel
    {
        public LoginWithRecoveryCodeViewModel()
        {
        }

        [Required]
            [DataType(DataType.Text)]
            [Display(Name = "Recovery Code")]
            public string RecoveryCode { get; set; }
    }
}
