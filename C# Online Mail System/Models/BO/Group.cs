using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace DistNet.Models.GroupModel
{
    public class Group
    {
        public Guid GroupId { get; set; }
        [Required]
        public string GroupName { get; set; }
        public ICollection<GroupToUserMapping> ListOfUserMapping { get; set; }
        public ICollection<Mail> Mail { get; set; }

        public Group(String GroupName)
        {
            GroupId = Guid.NewGuid();
            this.GroupName = GroupName;
        }

        public Group() {}
    }
}

