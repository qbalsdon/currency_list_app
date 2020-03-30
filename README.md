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

### Application

![Application Architecture](Architecture_Application.png "Application Architecture")

The reason for the relationship is to allow several different environments to exist without conflict. With the current structure its versatility is demonstrated by the addition of the [European Central Bank's API](https://exchangeratesapi.io/) service being injected as a different build variant. This also allows the app to have an "offline" variant which can be programmed for specific responses, and a different implementation is used in the Espresso tests to ensure consistent responses during testing.

#### Dependency Injection
Dependencies are injected via the Application object.
* Activities that require a data broker (for rendering data sources) implement the [RequiresDataBroker](app/src/main/java/com/balsdon/ratesapp/dataBroker/RequiresDataBroker.kt) interface so that the Application can use [ActivityLifecycleCallbacks](https://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks) to perform the injection.
* The object that uses country codes to render name and image resources (which requires Android context) is injected into the [RateItemPresenter](app/src/main/java/com/balsdon/ratesapp/rateItem/RateItemPresenter.kt) object

### Data Broker

![Application Architecture: Data Broker](Architecture_DataBroker.png "Application Architecture: Data Broker")

The DataBroker is the interface for rendering data from a data source, also known as the [façade pattern](https://en.wikipedia.org/wiki/Facade_pattern). The aim of the pattern is to allow different sources of data to be injected by developers for various reasons:
* Asynchronous fetch: do not hang the UI while the data is being retrieved, as in the case of complex database or web lookups
* Various sources: allow developers a platform- and app-agnostic mechanism for creating new layers, whether it is for testing or build variants.

### RateItemView

![Application Architecture: RateItemView](Architecture_RateItemView.png "Application Architecture: RateItemView")

The [RateItemView](app/src/main/java/com/balsdon/ratesapp/rateItem/RateItemView.kt) uses the [Model-View-Presenter](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) architecture pattern. This is due to the lack of necessity for the same kind of "observer-subscriber" model provided by Android ViewModels. This also simplifies the relationship of the item as it is to exist in the [RateListAdapter](app/src/main/java/com/balsdon/ratesapp/view/RateListAdapter.kt).

## Build variants

Each variant has a distinct app name and colour to uniquely identify them.

#### 1. production <span style="color:#808080">[&#9632;]</span>:
Connects to the web endpoint using [RetroFit](https://square.github.io/retrofit/)

#### 2. onlineecb <span style="color:#FFEB3B">[&#9632;]</span>:
Connects to the European Central Bank web endpoint ([https://exchangeratesapi.io/](https://exchangeratesapi.io/)) using [RetroFit](https://square.github.io/retrofit/). The purpose of this was to demonstrate the codes ability to use different environments.

#### 3. offlinemock <span style="color:#8BC34A">[&#9632;]</span>:
Uses a mocked class that generates responses in a programmable sequence.

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
* TODO: Get Instrumentation tests running on CI - the process is under development and is currently listed under [issues](https://github.com/qbalsdon/currency_list_app/issues/16)

## Icons
* App icon made by [Smashicons](https://www.flaticon.com/authors/smashicons) from [Flaticon](https://www.flaticon.com/)
* Icons made by [Freepik](https://www.flaticon.com/authors/freepik) from [Flaticon](https://www.flaticon.com/)


###### &copy; 2020 [Quintin Balsdon](https://www.linkedin.com/in/qbalsdon/)