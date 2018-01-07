# whereismycar
Android Mobile Application developed for the Mobile Course by Alexandru Coltuneac(Group: 933)

Mobile application that let drivers from Cluj-Napoca to inform each other about the places where the cars are picked up by the police.

Main features of the application:

- Users can save the place where they parked their cars
- Users get a notification on the phone when a police car is spotted near the place they parked, if they parked illegally
- Users can inform each other about cars picked up by the police
- Users do have a public profile
- A rating system will be available in order to assess different users reports

A new, bad time came over Cluj-Napoca. Many cars parked illegaly are picked-up by the police and sent kilometers away, in a huge car parking area. Car owners are obliged to pay an ammount starting from 500 Lei, in order to get their cars back.

This application will use in a very high percent the information that is shared by the users. People who walk on the street and see a car that is ready to be picked-up or a trailer that carry one unlucky victim, can send a new report using a form available in the application. The report will contain a picture from the event they've just assisted and a description of the car that was picked-up (including Street name - optionally, License Plate Number, Brand, Model, Colour).

In order to be able to access this application, you will have to register, at the first use, using an email & password combination or a social account. Then, you will create your public profile that will contain an avatar, your name and a description. Moreover, you will have the possibility to add the license plate number of your car(s) but this information will be private and other users of the app won't see it.

Every user will have the possibility to save the place where he parked his car. The address of this place will be either picked from a Map or typed-in manually and will contain (Street name and/or Street number). The car owners can also "Un-park" their cars if they leave from the saved location.

Every time a new report will be sent by a user and the license plate number from it will match with one of other users of the application, that unlucky user will receive on his phone a Push notification, as an alert. Also, all the users that parked their cars on that street, will get a notification.

This application will also:

- Have Online support
- Implement CRUD REST client operations.

The user can:
  - Create a new account and a public profile
  - Add his car(s) to his account (private information)
  - Add a new report | Edit his report(s) | Delete his report(s)
  - Save the place where he parked his car | Un-park his car
  - View other reports made by the users and evaluate them (using 1-5 scale)
  - Have Notifications (gcm, ws, etc.)
  - The user will receive a push notification every time one of his cars is picked-up (based on the reports made by other users and his License Plate Number that must match the one in the report)
  - Synchronize the content with a remote location
  - Have Authentication mechanism (jwt, oauth) 
  - The user can login using his email and password OR a social account
  - Submit data on background threads or/using promises
  - Navigate easily between screens/views.
  - Manage the application state outside the user interface layer.
  - Use the observer pattern to notify the user interface about the changes occurred internally.

©️Coltuneac Alexandru Group: 933
