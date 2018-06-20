using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using DistNet.Models.HomeViewModels;
using DistNet.Models.HomeModels;
using DistNet.Models;
using Microsoft.AspNetCore.Identity;
using DistNet.Models.GroupModel;

namespace DistNet.Controllers
{
    [Produces("application/json")]
    [Route("api/ReadAPI")]
    public class ReadAPIController : Controller
    {
        private readonly IModelHandler _model;
        private readonly UserManager<ApplicationUser> _userManager;

        public ReadAPIController(UserManager<ApplicationUser> userManager, IModelHandler model)
        {
            _model = model;
            _userManager = userManager;
        }
        
        /// <summary>
        /// Retrieves a mail and delivers to the user via a Partial ViewPage
        /// </summary>
        /// <param name="id">The MailID used to retrieve the desired mail</param>
        /// <returns>MessagePartial ViewPage</returns>
        [HttpGet]
        [Route("/api/[controller]/GetRead/{id}")]
        public PartialViewResult GetRead(string id)
        {
            Mail m = _model.GetMail(Guid.Parse(id));
            ReadMail model = new ReadMail(Guid.Parse(id), m.Sender, m.MailTitle, m.Time, m.MailBody);
            return PartialView("~/Views/Home/Partials/MessagePartial.cshtml", model);
        }
        
        /// <summary>
        /// Retrieves all the previews for mail sent by the given id
        /// </summary>
        /// <param name="id">The user ID to retrieve messages previews from</param>
        /// <returns>PreviewPartial ViewPage</returns>
        [HttpGet]
        [Route("/api/[controller]/GetPreviews/{id}")]
        public PartialViewResult GetPreviews(string id)
        {
            string user = _userManager.GetUserName(User);
            List<MessagePreview> list = new List<MessagePreview>();

            List<PreviewsModel> previews = _model.GetPreviews(user, id);
            foreach (PreviewsModel p in previews)
            {
                list.Add(new MessagePreview(p.Id, p.Title, p.Date, p.Unread));
            }

            ReadViewModel model = new ReadViewModel();
            model.MessagePreview = list;

            return PartialView("~/Views/Home/Partials/PreviewPartial.cshtml", model);
        }
        
        /// <summary>
        /// Gets every unique sender for the current user
        /// </summary>
        /// <returns>SendersPartial ViewPage</returns>
        [HttpGet]
        [Route("/api/[controller]/GetSenders")]
        public async Task<PartialViewResult> GetSenders()
        {
            List<SendersModel> list = new List<SendersModel>();

            // For Production
            ApplicationUser user = await _userManager.GetUserAsync(User);
            List<SendersModel> senders = _model.GetAllSenders(user.Email);
            foreach (var sender in senders)
            {
                list.Add(new SendersModel(sender.Id, sender.Name));
            }

            SendersViewModel model = new SendersViewModel
            {
                Senders = list
            };
            return PartialView("~/Views/Home/Partials/SendersPartial.cshtml", model);
        }

        /// <summary>
        /// Removes a mail.
        /// </summary>
        /// <param name="id">MailID to remove</param>
        /// <returns>RemovePartial ViewPage</returns>
        [HttpGet]
        [Route("/api/[controller]/DoRemove/{id}")]
        public PartialViewResult DoRemove(string id)
        {
            _model.RemoveMail(Guid.Parse(id));

            return PartialView("~/Views/Home/Partials/RemovePartial.cshtml");
        }

        /// <summary>
        /// Retrieves all Users emails in the database
        /// </summary>
        /// <returns>WritePartial ViewPage</returns>
        [HttpGet]
        [Route("/api/[controller]/GetU")]
        public PartialViewResult GetU()
        {
            List<ApplicationUser> users = _model.GetAllUsers();
            List<RecipientModel> recipients = new List<RecipientModel>();
            foreach (ApplicationUser u in users)
            {
                recipients.Add(new RecipientModel { Id = u.Id, Name = u.Email });
            }
            RecipientViewModel model = new RecipientViewModel();
            model.Recipients = recipients;
            return PartialView("~/Views/Home/Partials/WritePartial.cshtml", model);
        }

        /// <summary>
        /// Retrieves all Group names in the database
        /// </summary>
        /// <returns>WritePartial ViewPage</returns>
        [HttpGet]
        [Route("/api/[controller]/GetG")]
        public PartialViewResult GetG()
        {
            List<Group> groups = _model.GetAllGroups();
            List<RecipientModel> recipients = new List<RecipientModel>();
            foreach (Group g in groups)
            {
                recipients.Add(new RecipientModel { Id = g.GroupId.ToString(), Name = g.GroupName });
            }
            RecipientViewModel model = new RecipientViewModel();
            model.Recipients = recipients;
            return PartialView("~/Views/Home/Partials/WritePartial.cshtml", model);
        }

        /// <summary>
        /// Retrieves the Message stats for the currently logged in user.
        /// </summary>
        /// <returns>StatsPartial ViewPage</returns>
        [HttpGet]
        [Route("/api/[controller]/GetStats")]
        public PartialViewResult GetStats()
        {
            string user = _userManager.GetUserName(User);
            ReadStats stats = _model.GetReadStats(user);

            StatsViewModel model = new StatsViewModel
            {
                Deleted = stats.Deleted,
                Total = stats.Total,
                Unread = stats.Unread
            };
            return PartialView("~/Views/Home/Partials/StatsPartial.cshtml", model);
        }
    }
}