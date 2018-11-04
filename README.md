
# androidApp_Template
Simple just made app for android device with all the base elements: Login, Registration and a page of InformationShow;
Hi I'm IEC ... if you want know something about me, check some details [here ](http://mycoderpage.altervista.org/On-CV/index.php)

## REPO Menu

- [Intro](Intro)
- [Some basic info]()
- [Repo Content]()
  - [APP]()
  - [SERVER]()
  - [DB]()
- [FUTURE IMPLEMENTATION]()
- [CREATION of EXECUTABLE & RUN]()

## Intro
HI mates,

this files in the various folders are a complete abnd working example of an app in android made by me for helping you, 
with almost all the basic pattern used by the apps right now.

Hope they can help someone of you

## Some basic info
Obviously this is an example/guide for people that has just develop in Android, i will not explain the code and the comment are rare inside
thats because i have no time for putting them right now.
If lot of you will ask me for that i could insert it, but i wont until some request comes :)

# Repo Content
The repo contains the common structure  client/server of an app.
Every one of the components are full example and just integrated between them, you need only to change the links inside the app and load the server in a free host service or your own one.



### APP
I realized this app through Android Studio IDE, so the structure of the folder is the generical one.

The app is mainly structured with some common pattern of the nowadays app so:

It begins with an old fashioned **Intro View** with a big image in the center  of the screen and the name of the app, also a subtitle or space for the *@creator* in the  bottom.

Then it proceed with a simple **Login form** which own also the link to the **Register form**.

After the first access in this form the app will directly store your data inside a class called  **SharedPrefManage** that will manage your info and will avoid in future to insert it in the form.

After that, in case of succesfully login the app will proceede with the **mainActivity**   it contains the a RecycleViewer for the output of the item i want to show
The design of every sigle element are inside the row_item.xml file
The design may be changed by uploading this.

There are also 2 more folders called :
>adapters
>models

those contains the model and the adapter of the item object, by changing those you can create your own one. 
Pay attenction then to upload all the methods also in the **ShowActivity** .

### SERVER
The server is structured in 2 files:

>Handler.php
>Funtion.php

**Handler** contains all the function to interface the db and some standard query that i've used in the fuction file. 
I designed it as a generic template file for handling a database, so be free to use it fpr whatever you want.
**Function** contains all the function called by the  Activities in the example, so there are just wrote some queries specifics for the Db design of the app! 
In case you want to create your own you have to change all these queries.
### DB
The db is very easy, obviously the app has not much functions so the only tables that it owns are:

| ------ | USER |
| ------ | ------ |
| uid | cod of user (just autoincremented) |
| username | unique name of the user |
| email | unique email of the user |
| password | psw protected with md5 (not so much as protection but is a basic protection protocol so... maybe a sale could be added) |
| data | (lessical error XD) however date of sign-in |
| permission | user permissions, if admin 'a' otherwhise 'c' as client |

then the second table...

| ------ | ITEM |
| ------ | ------ |
| cid | cod of item (just autoincremented) |
| name | name of the item |
| description | short description about the item |
| date | date of insertion in the Db of the Item |
| url | Item image to display in app |


### FUTURE IMPLEMENTATION
In future i will add some new pattern in the app like a left side menù and a tab layout.
I just realized it but i have no time to implement it so makes me know if you all are interested in it!


### CREATION of EXECUTABLE & RUN

To execute and run the app is necessary to import it in AndroidStudio.

To Run the server you should find a free Host service like altervista to upload the files. **The link of that then should be inserted in the app where is required!!!**

The DB also must be imported in the MySql of the server. Also in the Handle.php class you must change the credential with your new one

After that all should works fine so

### --------------- HAVE FUN AND RATE MY REPO------------------------
