# UIManager


@ Setting
- Project:build.gradle
```gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url  "https://dl.bintray.com/hare/UIManager"
        }
    }
}
```

- Module:build.gradle
```gradle
implementation 'developers.hare.com:uiamanger:1.0.0'
```

- Maven
```maven
<dependency>
  <groupId>developers.hare.com</groupId>
  <artifactId>uiamanger</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
