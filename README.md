#Twitteriffic
This app has been designed as a simple mockup of a Twitter client. All server functions have classes that are designed to emulate a server with delays added to simulate a call to the network.  
We will be using the shared preferences to store log-in information and a SQLite database to save twitter feeds.  
We will provide an initial fake list of tweets from the server. This will make it useful for testing.
We will be using the google GSON library for parsing JSON into tweet classes. We are using a TweetProvider interface for providing tweets. This will be either a mock provider or the one for the app. In a real app I would not need a provider for the main app as the server api code would directly return the results from the server.
We will be using my sql library to provide access to SQLite.  
For login information, I am using a single user to test login success and failure.
The default username/password is test/password. Anything else should fail.