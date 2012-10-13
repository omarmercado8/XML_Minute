XML_Minute
==========

Web application that uses eXist XML database. 
The purpose of this project whas to work and understand the benefits of 
using a NXD for Semi Structured Data as Minutes


REQUIREMENTS AND ANALYSIS

The requirements for this project consist of using an XML database to store minutes that are 
taken during meetings held in the Computer Science Department. Another of the project 
requirement is to build an application that has a user interface where a user can create new 
minutes and save them, also retrieve information from this XML database without the need of 
too much computer experience.

Because minutes fall under the category of semi structured data it is one of the objectives to 
use an XML approach. Minutes will have a similar structure one to another like a Committee 
Name, a Date and Title, and a list of subjects that are to be covered during the meeting.
Another requirement of this project is that the software does not need to be installed in the 
user’s computer; it must be able to run inside of the Computer Science intranet. The same 
goes for the database, the database should be installed on a server that the application has to 
be able to access in a remotely way.

Another of the requirements is the ability to search through the minute information, this 
search consist on the user putting a list of keywords and then the application will check where 
this keywords are found. This search needs to have the ability to restrict only to go through 
the Title, the Name of a person that was on the meeting or by the full text that is inside of the 
documents. 

Another important requirement is for the system to use the e-mails of the committee members 
to create announcements, by selecting a committee the system has to be able to send an e-mail 
message to all the users that are part of that committee without having to send the same 
message multiple times for each member. This email system should take advantage of the 
committee information and send a reminder email message to the members one day in 
advance of their next meeting without any interaction from the user.

To work around with committees the application will need to have a simple way of creating 
committees and introducing all the necessary information that a committee must have this is 
information like; a Committee name or a committee meeting periodicity. Another of the 
committee creation requirement was the ability to add all the committee members but also 
select the chair of each committee. There is no need to edit committees once they have been 
created, but deleting is needed to avoid sending emails to committee members that are no 
longer having meetings. 

For the user to start working on a meeting it is first necessary to create an agenda, the 
application needs to have a special part for creating the agendas.
Creating a minute will be done by simply using the information from the agenda, the subjects 
cannot be change anymore but there is the possibility to take notes on each subjects, also the 
ability to be able to make it clear which members of the committee where absent from the 
meeting. 

To add a user to a committee is necessary to first create that user in the system and filled the 
necessary information, after that each user can be added into a new committee.
Another requirement is the ability to print the minutes that are stored in the XML database.
Because of the different platforms from where the application could be used inside of the 
department, it was necessary to try to make the application as much platform independent as 
possible. Something to take under special consideration is that because it is going to be a web 
application it had to be possible to work under different browsers and different versions of 
these browsers.
Since there is no budget allocated for this project all of the project must be developed under 
open source tools, also the XML database has to be an open source project.
