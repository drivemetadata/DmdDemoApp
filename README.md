
<p align="center">
  <img src="https://www.drivemetadata.com/assets/img-dmd/a-logo.svg"/>
</p>

### DriveMetaData Android SDK (One Platform For All-In-One Marketing Stacks & Customer Data Platform)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.drivemetadata/dmd-android-sdk/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.drivemetadata/dmd-android-sdk/badge.svg)

## 👋 Introduction

[DriveMetaData](https://www.drivemetadata.com) One Platform For All-In-One Marketing Stacks & Customer Data Platform. [website](https://www.drivemeatadata.com)  and  [documentation](https://docs.google.com/document/d/13qg2HF0T6fAkSAZiQcji_0rCONef0LmBzNyMXd2v8SU/edit).

To get started, sign up [here](https://www.drivemetadata.com)

## 🎉 Installation

We publish the SDK to `mavenCentral` as an `AAR` file. Just declare it as dependency in your `build.gradle` file.

```groovy
    dependencies {
    implementation 'com.drivemetadata:dmd-android-sdk:1.1.8'
    implementation "com.android.installreferrer:installreferrer:2.2"
    }
```

Alternatively, you can download and add the AAR file included in this repo in your Module libs directory and tell gradle to install it like this:

### 📖 Dependencies

Add the Install Referrer library and Android Support Library v4 as dependencies to your Module `build.gradle` file.

```groovy
    dependencies {
    implementation 'com.drivemetadata:dmd-android-sdk:1.1.8'
    implementation "com.android.installreferrer:installreferrer:2.2"
}
```


Once you've updated your module `build.gradle` file, make sure you have specified `mavenCentral()` and `google()` as a repositories in your project `build.gradle` and then sync your project in File -> Sync Project with Gradle Files.


## 📲  DriveMetaData SDK Integration

Please refer to DriveMetaData's [Android Native SDK Setup](https://docs.google.com/document/d/13qg2HF0T6fAkSAZiQcji_0rCONef0LmBzNyMXd2v8SU/edit) page for step-by-step instructions on how to install the plugin.


## SDK Methods

Please see DriveMetaData's [Android Native SDK References](https://docs.google.com/document/d/13qg2HF0T6fAkSAZiQcji_0rCONef0LmBzNyMXd2v8SU/edit) page for a list of all the available callbacks and methods.

#### Change Log

Please refer to this repository's [release tags](https://github.com/drivemetadata/dmd-android-sdk/releases) for a complete change log of every released version.

#### Support

Please visit [drivemetadata.com](https://www.drivemetadata.com) or write to [support@drivemetadata.com](mailto:support@drivemetadata.com) for any kind of issues.

#### Demo Project

For reference, we have uploaded a demo project with the latest SDK in the <code>master</code> folder of this repository.

#### Supports:

* Tested and validated from Android 5.0 (API level 21) to Android 12 (API level 33).