# Android Rates App
## Written by Quintin Balsdon

![Rates App Icon](app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png "Rates App Icon")

This Android App gets a list of currency rates from a data source and renders them on a screen

Icons made by [Freepik](https://www.flaticon.com/authors/freepik) from [Flaticon](https://www.flaticon.com/)

## Architecture
The app uses a mixture of [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) and [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter), depending on necessity.

### Dependency Injection
Injected via the Application object. They implement an ApiService class that is created once on the Application level and use [ActivityLifecycleCallbacks](https://developer.android.com/reference/android/app/Application.ActivityLifecycleCallbacks) to perform the injection.

There are 2 flavours:
#### 1. production
> Connects to the web endpoint using [RetroFit](https://square.github.io/retrofit/)

#### 2. offlinemock
> Uses a mocked class that generates responses in a programmable sequence. This version of the app has a distinct red notification bar

#### Activity
[RateListActivity](https://github.com/qbalsdon/currency_list_app/blob/master/app/src/main/java/com/balsdon/ratesapp/view/RateListActivity.kt) uses a [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) called [RatesListViewModel](https://github.com/qbalsdon/currency_list_app/blob/master/app/src/main/java/com/balsdon/ratesapp/view/RateListViewModel.kt) which publishes the results of calls to the service with [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)

#### View
The [RateItemView](https://github.com/qbalsdon/currency_list_app/blob/master/app/src/main/java/com/balsdon/ratesapp/rateItem/RateItemView.kt) is a custom Android View that uses [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) to support it's architecture. Since the view itself is a complex object this is where development started. The presenter does not require injection due to its simplicity, and a ViewModel can only be injected view an Activity or Fragment, excluding it from the option.

## Testing
Currently the testing has been unit testing only. The Presenters, ViewModels and behavioural elements are all separated from the view and view logic.