Pre-requisites
--------------
- Android SDK 26 or Higher
- Build Tools version 26.1.0
- Android Support 26.1.0
- Gradle version : gradle:3.0.1

Brief
-----
StampIt is an online/offline ad posting application
which demonstrate the implementations of mvp, dagger, firebase, room, espresso and more.

Demo
----
![Login and Signup](screenshots/signup_login.gif?raw=true "Login and Signup")
![addItem](screenshots/add_item.gif?raw=true "add item")

Project
-------

- MVP
- Dagger 2
- Room Persistant Liabrary
- Firbase Auth
- Firebase Database
- Espresso Testing
- Image capture
- File Provider
- Runtime permissions

Configuration
-------------
Use the project with your own Firebase instance

1. Clone this repository.
   - Create a new project in the Firebase console.
   - Click Add Firebase to your Android app provide a unique package name
   - Use the same package name for the applicationId in your build.gradle
   - insert SHA-1 fingerprint of your debug certificate, otherwise you won't be able to log in
2. Copy the generated google-services.json to the app folder of your project.
   - You should be able to successfully sync the project now.
3. Enable **Email/Password** in your Firebase -> Auth -> Sign-in Method.
4. Add following rules to allow access to only authenticated users
        
        {
          "rules": {
            ".read": "auth != null",
            ".write": "auth != null"
          }
        }

Build and run the app.


Contributing
---------------------

### How?
The easiest way to contribute is by [forking the repo](https://help.github.com/articles/fork-a-repo/), making your changes and [creating a pull request](https://help.github.com/articles/creating-a-pull-request/).

### Potential
- Rxandroid/java
- Validations
- Jobscheduler for backup
- Location autocomplete
- Layout tweeking
- Documentation(if required as particular place)
- Parhaps lot more...

License
-------
[Apache License 2.0](LICENSE)