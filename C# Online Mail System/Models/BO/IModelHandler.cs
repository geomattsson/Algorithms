using DistNet.Models.GroupModel;
using DistNet.Models.HomeModels;
using System;
using System.Collections.Generic;

namespace DistNet.Models
{
    public interface IModelHandler
    {
        /// <summary>
        /// Get a specific mail using MailId. 
        /// </summary>
        /// <param name="MailId"></param>
        /// <returns>If mail with id MailId has been found return it, otherwise return null</returns>
        Mail GetMail(Guid MailId);
        
        /// <summary>
        /// This method is used to save a mail in the database. 
        /// The sender can be an individual recipient of a group. 
        /// </summary>
        /// <param name="Sender"></param>
        /// <param name="Recipients"></param>
        /// <param name="MailTitle"></param>
        /// <param name="MailBody"></param>
        void SendMail(String Sender, List<String> Recepeints,
            String MailTitle, String MailBody);

        /// <summary>
        /// Remove a specific mail using the mail id to find id in the database,
        /// and then remove it.
        /// </summary>
        /// <param name="MailId"></param>
        void RemoveMail(Guid MailId);

        /// <summary>
        /// Remove a specific group using the group id to find id in the database,
        /// and then remove it.
        /// </summary>
        /// <param name="GroupId"></param>
        void RemoveGroup(Guid GroupId);

        /// <summary>
        /// Retrieves a list of people that have sent messages to the given user.
        /// </summary>
        /// <param name="UserName">The username to check if it has received any messages</param>
        /// <returns>List of SendersModel</returns>
        List<SendersModel> GetAllSenders(String UserName);

        /// <summary>
        /// Retrieves necessary information to present a preview of messages
        /// </summary>
        /// <param name="User">The recipient of the messages</param>
        /// <param name="Name">The sender of the messages</param>
        /// <returns>List of Previews for all messages sent from Name to User</returns>
        List<PreviewsModel> GetPreviews(String User, String Name);

        /// <summary>
        /// Creates a group with the given name
        /// </summary>
        /// <param name="GroupName">Name of the group to create</param>
        /// <returns>True if successful, false otherwise.</returns>
        Boolean CreateGroup(String GroupName);

        /// <summary>
        /// Get a list of all groups
        /// </summary>
        /// <returns>A list of all groups</returns>
        List<Group> GetAllGroups();

        /// <summary>
        /// Adds the given user to the group
        /// </summary>
        /// <param name="UserName">The user to add to the group</param>
        /// <param name="Id">The GroupID used to determine which 
        /// group to add the user to</param>
        void AddToGroup(String UserName, Guid Id);

        /// <summary>
        /// Removes the given user from the given group
        /// </summary>
        /// <param name="UserName">The user to remove from the group</param>
        /// <param name="Id">The GroupID used to determine which 
        /// group to remove the user from</param>
        void RemoveFromGroup(String UserName, Guid Id);

        /// <summary>
        /// Retreives a list of all users
        /// </summary>
        /// <returns>List of ApplicationUser</returns>
        List<ApplicationUser> GetAllUsers();

        /// <summary>
        /// Retrieves the number of unread messages for the given user.
        /// </summary>
        /// <param name="User">The Email of the user</param>
        /// <returns>Number of unread messages</returns>
        int UnreadMessages(String User);

        /// <summary>
        /// Retrieve all logins for the last 30 days and count them. 
        /// </summary>
        /// <param name="User">User to do it for</param>
        /// <returns>Return the number of logins for the last 30 days</returns>
        int NrOfLogins(String User);

        /// <summary>
        /// Gets the time for the users most recent login
        /// </summary>
        /// <param name="User">The UserID to get the most recent login for.</param>
        /// <returns>The DateTime of the most recent login</returns>
        DateTime LastLogin(String User);

        /// <summary>
        /// Retrieves the number of deleted, unread and 
        /// total messages for the given user
        /// </summary>
        /// <param name="User">Email address of the user to retrieve stats for</param>
        /// <returns>ReadStats object containing deleted, unread and total messages</returns>
        ReadStats GetReadStats(String User);
    }
}

