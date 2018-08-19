# UIManager


@ Setting
- Project:build.gradle

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url  "https://dl.bintray.com/hare/UIManager"
        }
    }
}

- Module:build.gradle
implementation 'developers.hare.com:uiamanger:1.0.0'

- Maven
<dependency>
  <groupId>developers.hare.com</groupId>
  <artifactId>uiamanger</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>

