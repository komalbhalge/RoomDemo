# CHOCO

- Kotlin
- Model-View-ViewModel (MVVM)
- Reactive (Kotlin Flow)
- Single Activity Architecture
- Hilt Dagger 2
- Navigation

## Requirements

Before proceeding, ensure that you have the following requirements:

- JDK 1.8+
- Android SDK
- Kotlin v1.4+
- Android Studio 4.0+

## Getting Started

1. Onboard Choco project.

2. Clone the project from github:

git clone https://github.com/choco-hire/Komal-Android-.git

3. Install Android Studio and run through setup from the installer

4. Open up the project and let all the libs and gradle been setup automatically

5. Then you are good to go!


## Choco Scheme

1. Dev Environment
    Base API URL : "https://qo7vrra66k.execute-api.eu-west-1.amazonaws.com/choco/"
    
## Project Structure
 📂 data/            // → For data-layer related classes (Retrofit, Request/Response models)
        ├─ 📂 db/         // → For database-layer related classes (Dao, Repository, entities etc)
        ├─ 📂 model/      // → For model classes-layer related classes (ApiResponse,  ApiRequest)
             ├─ ApiResponse
 
 📂 domain/           // → For domain-layer related classes (UseCases, Domain models)
        ├─ 📂 common/      // → For database-layer related classes (Dao, Repository, entities etc)
        ├─ 📂 feature/     // → For feature related-use cases (LoginUseCase etc)
      

📂 injection/        // → For Dependency injection related classed -to contain Dagger-Hilt component and modules

📂 network/          // → For network connection related classes(Using Retrofit with coroutines and Kotlin Flow)

📂 testutils/        // → For unit/ui tests (Used mockito for testing)

📂 ui/         //For presentation-layer related classes (Fragments, View Models)
        ├─ common   //For common classes used throughout UI package
        ├─ feature  //Features divided by screen/module
            ├─ login    
            ├─ products    
            ├─ orders etc    
            
## Dependency details
    1. Database - Room - androidx.room
    2. Dependency Injection - Dagger-Hilt -com.google.dagger:hilt
    3. Navigation - androidx.navigation:navigation
    4. Network - Retrofit - com.squareup.retrofit2
    5. Image loading - Glide - com.github.bumptech
    6. Unit testing - mockito
