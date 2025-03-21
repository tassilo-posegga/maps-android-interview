# Maps SDK Android Interview project

Congratulations! You've made it to the pair programming interview. During this session, we'll work
through several additions and enhancements to a small photo search app (download
[code here](https://drive.google.com/file/d/1sqBq74kws7nH9snY_YL-FYDm6tzLFMpF/view?usp=sharing)).

Currently, the app has the following capabilities:

* A map view, an input text box and a search button.
* A view model that uses the Flickr API to retrieve pictures.
* The search results are displayed on the map.

## 🎯 How to Prepare for the technical exercise

To prepare for the interview, you should familiarize yourself with the provided starter application.
Follow the instructions in the [README.md](README.md) file in the root folder (also
check [Configure credentials section](https://docs.mapbox.com/android/maps/guides/install/) of the
Maps SDK for more details). Make sure you can build and run it.

The application uses [Kotlin coroutines](https://developer.android.com/kotlin/coroutines)
and [flows](https://developer.android.com/kotlin/flow) to perform some of its tasks. We recommend
you to become familiar with coroutines. Alternatively, you can switch the code to use another
approach you're more comfortable with (e.g. Threads, RxJava,...).

During the interview, you can also consult documentation and search the web.

## Setting up the application

Before running the app, you will need to gather the appropriate credentials. The SDK requires two
pieces of sensitive information from your Mapbox account.
If you don't have a Mapbox account: [sign up](https://account.mapbox.com/auth/signup/) and navigate
to your [Account page](https://account.mapbox.com/).
You'll need:

* **A public access token**: From your
  account's [tokens page](https://account.mapbox.com/access-tokens/), you can either copy your
  default public token or click the **Create a token** button to create a new public token.
    1. Create a new file at `app/src/main/res/values/developer-config.xml` with the access token in
       it:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="mapbox_access_token">YOUR MAPBOX ACCESS TOKEN</string>
</resources>
```

* **A secret access token with the `Downloads:Read` scope**.
    1. From your account's tokens page, click the **Create a token** button.
    2. From the token creation page, give your token a name and make sure the box next to
       the `Downloads:Read` scope is checked.
    3. Click the **Create token** button at the bottom of the page to create your token.
    4. The token you've created is a secret token, which means you will only have one opportunity to
       copy it somewhere secure.
    5. Find or create a gradle.properties file in
       your [Gradle user home folder](https://docs.gradle.org/current/userguide/directory_layout.html#dir:gradle_user_home).
       The folder can be found at `«USER_HOME»/.gradle`.
    6. Add your secret token the `gradle.properties` file:

```properties
MAPBOX_DOWNLOADS_TOKEN=YOUR_SECRET_MAPBOX_ACCESS_TOKEN
```

## Evaluation

We'll be evaluating:

* Your ability to extend an existing codebase
* How you architect and organize your solutions & your reasons for those decisions
* How you reason about performance including management of memory, processor, and network I/O
* Your approach to problem solving
* Your ability to communicate your design decisions
* Your ability to write asynchronous programs
* The readability and cleanliness of your code
* Your familiarity and skill with writing unit tests and designing testable software
