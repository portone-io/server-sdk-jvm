JVM 서버 환경에서 포트원 V2 결제 시스템에 연동하기 위한 SDK입니다. 코틀린, 스칼라, 자바 등의 언어에서 사용할 수 있습니다.


## 의존성
JVM 11 이상이 필요합니다.

현대적인 코틀린 환경을 사용해 구현합니다. 내부 HTTP 클라이언트로는 OkHttp 엔진의 Ktor를 사용합니다. JSON 직렬화를 위해 kotlinx.serialization을 사용합니다.


## 설치
[![Maven Central Version](https://img.shields.io/maven-central/v/io.portone/server-sdk)](https://central.sonatype.com/artifact/io.portone/server-sdk)

메이븐 중앙 저장소를 통해 설치합니다.

### 그래들
```Gradle Kotlin DSL
dependencies {
    implementation("io.portone:server-sdk:x.y.z")
}

repositories {
    mavenCentral()
}
```

### 메이븐
```XML
<dependencies>
    <dependency>
        <groupId>io.portone</groupId>
        <artifactId>server-sdk</artifactId>
        <version>x.y.z</version>
    </dependency>
</dependencies>
```

### 앰퍼
```YAML
dependencies:
  - io.portone:server-sdk:x.y.z
```


## 버전
[유의적 버전 2.0.0](https://semver.org/spec/v2.0.0.html)을 사용합니다.

현재 주(主) 버전은 0입니다. 이는 라이브러리 공개 API가 아직 고정되지 않았음을 의미합니다. 주 버전이 1이 되기 전에도 릴리스 버전(프리릴리스가 아닌 버전)의 SDK를 프로덕션에서 사용할 수 있으며, 포트원은 관련 기술 지원을 제공합니다.


## API 안정성
포트원 V2는 REST API의 하위 호환을 보장합니다. 본 SDK는 REST API에 의존하므로, 한 버전의 SDK로 연동한 뒤에는 해당 버전에 특별한 버그가 없는 한 연동이 깨지지 않습니다.

SDK의 버전을 업데이트한 경우 코드 호환성이 깨질 수 있습니다. 이 경우 코드 작업이 필요합니다.


## 개발 로드맵
* [x] 웹훅 검증을 위한 `WebhookVerifier` 제공
* [ ] REST API 연동

REST API 연동이 완료되어 SDK를 이용해 간편하게 결제 시스템에 연동할 수 있게 된 후 1.0.0을 릴리스할 예정입니다.


## 기술 지원
* tech.support@portone.io
