
# What is World-2D?
World-2D is an open source library to facilitate the development of graphic applications, designed for Android developers. You can add it to you gradle file, such as: 

`compile 'com.titicolab.world-2d:core:0.1.1-beta'`

Check the a getting started [SampleWorld](https://github.com/worldtwod/SampleWorld), We are working in new samples an tutorials, collaborators are welcome.

# Motivation
As publisher, independent apps and games developer, I explored and experienced with different alternatives and frameworks, such as:
* Android Custom Layout 
* Android Canvas
* Unity
* GDX
* ...

After several years of work and maintenance of such app, I have concluded that these alternatives are not the most suitable for an independent study with a small working team. Heresome of the difficulties listed: 
* Publish a multiplatform game can be a truly Herculean effort, open many battlefronts can be overwhelming. At end you get a worse user experience.
* The apps that are monetizable and moderately successful on the platform android may not result in IOS.
* Simple games can result in ridiculously large sizes of apk files.
* When you use a no Android native engine, likely you needs first to learn another programming language. 

Consequently, this project seeks develop and refine a library that allows quickly games creation for android developers. 

# Design Philosophy
World-2D should be easy to use and its applications are expected to be easy to maintain as well, easy for update according to changes and Android.  The architecture must be compatible and take advantage of native development environment, such as Android Studio. Also allow easy integration with  G ecosystem: Google Play Games API, Google Cloud, Firebase ... Etc. If the enemy can not join them.

# Upload 
`gradlew bintrayUpload`

# Changelog
 
 `compile 'com.titicolab.world-2d:core:0.1.1-beta'`
    The buffer sizes can be set from GameActivity, else the default value is set
   
 `compile 'com.titicolab.world-2d:core:0.1.1-beta'`
    Migrated to androidX
    
  `compile 'com.titicolab.world-2d:core:0.1.5-beta'`
    Nao the size of render buffer can be customized
    