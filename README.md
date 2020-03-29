![Android Master](https://github.com/qbalsdon/currency_list_app/workflows/Android%20Master/badge.svg?branch=master&event=push)
# Android Rates App
![Rates App Icon](app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png "Rates App Icon")

## Written by Quintin Balsdon
A [Trello Board](https://trello.com/b/GtrPNW8y/rates-app) is used to track App progress

This Android App gets a list of currency rates from a data source and renders them on a screen. The user can modify the input on the top item and the other rates will automatically adjust. If the user clicks on any element except the top element, that element will become the top element
<br/>
<br/>
<a href="http://www.youtube.com/watch?feature=player_embedded&v=-5BXo8nmboY" target="_blank">
    <img src="http://img.youtube.com/vi/-5BXo8nmboY/0.jpg" alt="Basic functionality of the app" width="240" height="180" border="10" />
</a>

## Architecture
The app uses a mixture of [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) and [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter), depending on necessity.

### Dependency Injection
Injected via the Application object. They implement an ApiService class that is created once on the Application level and use [ActivityLifecycleCallbacks](https://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks) to perform the injection.

There are 3 flavours:
#### 1. production
> Connects to the web endpoint using [RetroFit](https://square.github.io/retrofit/)

#### 2. onlineecb
> Connects to the European Central Bank web endpoint ([https://exchangeratesapi.io/](https://exchangeratesapi.io/)) using [RetroFit](https://square.github.io/retrofit/). The purpose of this was to demonstrate the codes ability to use different environments.

#### 3. offlinemock
> Uses a mocked class that generates responses in a programmable sequence. This version of the app has a distinct red notification bar

#### Activity
[RateListActivity](https://github.com/qbalsdon/currency_list_app/blob/master/app/src/main/java/com/balsdon/ratesapp/view/RateListActivity.kt) uses a [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) called [RatesListViewModel](app/src/main/java/com/balsdon/ratesapp/view/viewModel/RateListViewModel.kt) which publishes the results of calls to the service with [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)

#### View
The [RateItemView](https://github.com/qbalsdon/currency_list_app/blob/master/app/src/main/java/com/balsdon/ratesapp/rateItem/RateItemView.kt) is a custom Android View that uses [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) to support it's architecture. Since the view itself is a complex object this is where development started. The presenter does not require injection due to its simplicity, and a ViewModel can only be injected view an Activity or Fragment, excluding it from the option.

## Testing
The Presenters, ViewModels and behavioural elements are all separated from the view and view logic. Views are kept as minimal as possible, only where Android [context](https://developer.android.com/reference/android/content/Context) or [lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle) is required. Business logic is tested with unit tests and behaviour is tested with [Espresso](https://developer.android.com/training/testing/espresso) tests

## CI
The project uses GitHub actions to run unit tests automatically. Currently the scripts perform the following actions:
* Build feature branch on push: ensures the sanity of the branch
* Build `master` and feature branch on pull request: ensures sanity of branches as part of code review process
* Generate apks on pull request: allows a tester to download debug artifacts of each version of the app for testing. These can be found by clicking on the latest build [here](https://github.com/qbalsdon/currency_list_app/actions?query=workflow%3A%22Android+Pull+Request+%26+Master+CI%22)
* TODO: Get Instrumentation tests running on CI - the process is under development

## Icons
* App icon made by [Smashicons](https://www.flaticon.com/authors/smashicons) from [Flaticon](https://www.flaticon.com/)
* Icons made by [Freepik](https://www.flaticon.com/authors/freepik) from [Flaticon](https://www.flaticon.com/)


###### &copy; 2020 [Quintin Balsdon](https://www.linkedin.com/in/qbalsdon/)