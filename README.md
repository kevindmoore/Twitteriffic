#Twitteriffic
This app has been designed as a simple mockup of a Twitter client. All server functions have classes that are designed to emulate a server with delays added to simulate a call to the network. We use a recycler view to display each row and since there is no divider, this looks ok. I've created a simple bubble drawable to display the text in.  
Startup of the app occurs in StartupActivity. It checks the login state first and starts the login activity if the user is not logged in, otherwise it starts the main activity.   
No permissions are required as we don't access the internet. If we did, we would need to add this permission.

###Preferences
We will be using the shared preferences to store log-in information and a SQLite database to save twitter feeds. Preference calls are made using my Prefs utility class.  

###Server API
We will provide an initial fake list of tweets from the server. This will make it useful for testing.
We will be using the google GSON library for parsing JSON into tweet classes. This will be either a mock provider or the one for the app. 
The server code consists of a ServerAPI class to make the actual call and uses a TweetProvider interface for getting the actual tweets - these will come from either a test resource or will be saved in a file on the user's phone. This is to emulate a separate server data source. (since tweets are also stored in SQLite). The server response is returned in the Response<T> object which holds the state and the data. The ServerResponseCallback is the interface that the user of the service has to implement to receive the data. We use my Tasker library to make the "network" calls on a background thread and then we sleep to simulate network time.
###SQLite
We will be using my sql library to provide access to SQLite. This makes it easy to create a database, update and make queries.  
There are two models, Login for login info and Tweet to hold a single tweet. 

###Login
For login information, I am using a single user to test login success and failure.
The default username/password is test/password. Anything else should fail. The Login activity was auto-generated by Android Studio so I had to remove lots of unnecessary code.

###Tests
There are 2 test classes. ServerAPITests is a junit test that test server functionality. This uses a mock provider with 5 tweets and tests loading them and posting a new item. There are also tests for loging in.  
DatabaseTests is an AndroidTest (as it needs a context) and tests calls to the SQLite database for adding and deleting tweets.

###Libraries used
I used the gson, appcompat, design and recyclerView from Google. I used EasySQLlibrary and Tasker from my libraries.   

    compile "com.android.support:appcompat-v7:$supportVersion"
    compile "com.android.support:design:$supportVersion"

    compile 'com.mastertechsoftware.easysqllibrary:easysqllibrary:1.0.7'
    compile 'com.mastertechsoftware.tasker:taskerlibrary:1.0.7'
    compile 'com.google.code.gson:gson:2.4'
    compile "com.android.support:recyclerview-v7:$supportVersion"
 