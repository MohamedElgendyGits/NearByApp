# NearByApp


NearByApp is an application uses Foursquare API to display information about nearby places
around user using user’s current location specified by Latitude and
Longitude
App has two operational modes, “Realtime" and “Single Update”.
“Realtime” allows app to always display to the user the current near by
places based on his location, data should be seamlessly updated if user
moved by 500 m from the location of last retrieved places.


architecture and libraries :
- MVP for architecture pattern
- retrofit for network calls
- RxJava 2 for reactive programming and threads handling
- RxLocation for location services
- ButterKnife for injecting views
- Picasso for image loading