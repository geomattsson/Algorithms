using System.ComponentModel.DataAnnotations;

namespace DistNet.Models.HomeViewModels
{
    public class WriteViewModel
    {
        [Display(Name = "Recipients")]
        [DataType(DataType.Text)]
        [Required]
        public string Recipients { get; set; }

        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "Title")]
        public string Title { get; set; }

        [DataType(DataType.MultilineText)]
        [Display(Name = "Message")]
        public string Message { get; set; }
    }
}
