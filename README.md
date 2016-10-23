# Twitter
Twitter Test App

this is a simple Twitter client App using
1- fabric kit sdk to make twitter login and api calls.
2- Ormlite to create local database to save user followers.
3- Glide to load profile images.

Two activities
1- Authenticate Activity: to make login process
2- Main Activity: launcher activity which contains fragments to show data of authenticated user

Three fragments 
1- Followers list fragment: to lookup local data (only first page) until server requests new data with pagination using cursor for each page
 -if internet connection is down request will fail and data is shown from local data base.
 -if new data received list will be updates with new data.
2- Follower Details fragment: to show follower details.



