using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using DistNet.Models;
using DistNet.Models.HomeViewModels;
using DistNet.Models.HomeModels;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using DistNet.Models.GroupModel;

namespace DistNet.Controllers
{
    [Authorize]
    public class HomeController : Controller
    {
        private readonly UserManager<ApplicationUser> _userManager;
        private readonly IModelHandler _model;

        /// <summary>
        /// Constructor for HomeController.cs
        /// </summary>
        /// <param name="userManager">Used to retrive the curreent user</param>
        /// <param name="model">Used to communicate with the database.</param>
        public HomeController(UserManager<ApplicationUser> userManager, IModelHandler model)
        {
            _userManager = userManager;
            _model = model;
        }

        /// <summary>
        /// Retrives and populate the Index.cshtml page with 
        /// correct information.
        /// </summary>
        /// <returns>Index ViewPage</returns>
        [HttpGet]
        public async Task<IActionResult> Index()
        {
            var user = await _userManager.GetUserAsync(User);
            if (user == null)
            {
                throw new ApplicationException($"Unable to load user with ID '{_userManager.GetUserId(User)}'.");
            }

            IndexViewModel model = new IndexViewModel
            {
                username = user.UserName,
                unreadMessages = _model.UnreadMessages(user.Email),
                loginsLastMonth = _model.NrOfLogins(user.Id),
                lastLogin = _model.LastLogin(user.Id)
            };
            return View(model);
        }

        /// <summary>
        /// Retrives and populate the Read.cshtml page
        /// </summary>
        /// <returns>Read ViewPage</returns>
        [Route("[action]")]
        public async Task<IActionResult> Read()
        {
            List<SendersModel> list = new List<SendersModel>();

            ApplicationUser user = await _userManager.GetUserAsync(User);
            List<SendersModel> senders = _model.GetAllSenders(user.Email);
            foreach (var sender in senders)
            {
                list.Add(new SendersModel(sender.Id, sender.Name));
            }

            ReadStats stats = _model.GetReadStats(user.Email);

            SendersViewModel model = new SendersViewModel
            {
                Senders = list,
                TotalMessages = stats.Total,
                RemovedMessages = stats.Deleted,
                UnreadMessages = stats.Unread
            };
            return View(model);
        }

        /// <summary>
        /// Retrives the Write.cshtml page
        /// </summary>
        /// <returns>Write ViewPage</returns>
        [HttpGet]
        [Route("[action]")]
        public IActionResult Write()
        {
            return View();
        }

        /// <summary>
        /// Hanldes Post requests from Write.cshtml
        /// Sends a message to the specified recipients if a title has been given
        /// </summary>
        /// <param name="model">Model containing the users input arguments</param>
        /// <returns>Write ViewPage</returns>
        [HttpPost]
        [Route("[action]")]
        public async Task<IActionResult> Write(WriteViewModel model)
        {
            if (ModelState.IsValid)
            {
                ApplicationUser user = await _userManager.GetUserAsync(User);
                List<String> recipients = model.Recipients.Split(", ").ToList();
                recipients.RemoveAt(recipients.Count - 1);

                _model.SendMail(user.Email, recipients, model.Title, model.Message);

                ViewData["MSG-TITLE"] = model.Title;
                ViewData["MSG-RECIPIENTS"] = string.Join(", ", recipients);
                ViewData["MSG-TIME"] = DateTime.Now.ToShortTimeString() + " " + DateTime.Now.ToShortDateString();
            }
            ModelState.Clear();
            model.Message = "";
            return View(model);
        }

        /// <summary>
        /// Retrives and build the Groups.cshtml page
        /// Gives the user its currently active and availalbe groups.
        /// </summary>
        /// <returns>Groups ViewPage</returns>
        [HttpGet]
        [Route("[action]")]
        public IActionResult Groups()
        {
            string user = _userManager.GetUserName(User);

            List<GroupViewModel> activeList = new List<GroupViewModel>();
            List<GroupViewModel> availalbeList = new List<GroupViewModel>();

            Boolean inGroup;
            List<Group> Groups = _model.GetAllGroups();
            foreach (Group g in Groups)
            {
                inGroup = false;
                if (g.ListOfUserMapping != null)
                {
                    foreach (GroupToUserMapping m in g.ListOfUserMapping)
                    {
                        if (m.UserEmail.Equals(user))
                        {
                            activeList.Add(new GroupViewModel(Id: g.GroupId, Name: g.GroupName, Remove: true));
                            inGroup = true;
                        }
                    }
                }
                if (!inGroup)
                    availalbeList.Add(new GroupViewModel(Id: g.GroupId, Name: g.GroupName, Remove: false));
            }

            GroupsViewModel model = new GroupsViewModel
            {
                Active = activeList,
                Available = availalbeList
            };
            ModelState.Clear();
            return View(model);
        }

        /// <summary>
        /// Handles Post requests from Groups.cshtml
        /// Either removes the currently active user from the specified group 
        /// or adds the user to the specified group.
        /// </summary>
        /// <param name="model">Model containing the users input arguments</param>
        /// <returns>Groups ViewPage</returns>
        [HttpPost]
        [Route("[action]")]
        public IActionResult Groups(GroupsViewModel model)
        {
            if (ModelState.IsValid)
            {
                if (model.Remove)
                    _model.RemoveFromGroup(_userManager.GetUserName(User), Guid.Parse(model.Id));
                else
                    _model.AddToGroup(_userManager.GetUserName(User), Guid.Parse(model.Id));
            }
            return Groups();
        }

        /// <summary>
        /// Retrives the CreateGroup.cshtml page
        /// </summary>
        /// <returns>CreateGroup ViewPage</returns>
        [HttpGet]
        [Route("[action]")]
        public IActionResult CreateGroup()
        {
            return View();
        }

        /// <summary>
        /// Handles Post requests generated from CreateGroup.cshtml
        /// Creates a group if the name is not taken and do not contain a '@'.
        /// </summary>
        /// <param name="model">Model containing the users input arguments</param>
        /// <returns>CreateGroup ViewPage</returns>
        [HttpPost]
        [Route("[action]")]
        public IActionResult CreateGroup(CreateGroupViewModel model)
        {
            if (ModelState.IsValid)
            {
                if (model.Name.Contains("@"))
                    ViewData["alertAT"] = "@";
                else
                {
                    if (_model.CreateGroup(model.Name))
                        ViewData["alertSuccess"] = model.Name;
                    else
                        ViewData["alertFail"] = model.Name;
                }
            }
            ModelState.Clear();
            return View();
        }

        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
