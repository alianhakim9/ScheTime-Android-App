# ScheTime Note App

Aplikasi Note untuk mobile native android.

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone git@github.com:wolox/ScheTime.git
```

## Configuration
### Keystores:
Create `app/keystore.gradle` with the following info:
```gradle
ext.key_alias='key0'
ext.key_password='ScheTimeApp'
ext.store_password='ScheTimeApp'
```
And place both keystores under `app/` directory:
- `ScheTime_KeyStore.keystore`

## Build variants
Use the Android Studio *Build Variants* button to choose between **production** and **staging** flavors combined with debug and release build types


## Generating signed APK
From Android Studio:
1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)*

## Maintainers
This project is mantained by:
* [Alian Hakim](http://github.com/alianhakim)


## Contributing

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Run the linter (ruby lint.rb').
5. Push your branch (git push origin my-new-feature)
6. Create a new Pull Request

# Screenshot App
![screen-1](https://user-images.githubusercontent.com/51102459/146638397-18105475-3932-4fe2-b477-0e7322449543.jpg)
![screen-2](https://user-images.githubusercontent.com/51102459/146638399-0d2b2562-f75a-4436-9b77-8499e62f732a.jpg)
![screen-3](https://user-images.githubusercontent.com/51102459/146638401-249ddb9f-5b92-4c12-8fdf-f9bf896e5c5b.jpg)
![screen-4](https://user-images.githubusercontent.com/51102459/146638402-12715be1-17d7-47d8-afd0-1987361603e5.jpg)
![screen-5](https://user-images.githubusercontent.com/51102459/146638405-289c0916-6698-4f36-b164-4aa184c49e25.jpg)
