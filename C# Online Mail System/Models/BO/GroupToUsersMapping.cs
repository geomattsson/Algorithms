using DistNet.Models.GroupModel;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace DistNet.Models
{
    public class GroupToUserMapping
    {
        public GroupToUserMapping() { }
        public GroupToUserMapping(String user, Group group)
        {
            this.MapId = Guid.NewGuid();
            this.UserEmail = user;
            this.Group = group;
        }

        [Key]
        public Guid MapId { get; set; }
        public String UserEmail { get; set; }
        public Group Group { get; set; }
    }
}

