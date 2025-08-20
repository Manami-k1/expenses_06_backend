# ビルドステージ
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# 実行ステージ
FROM eclipse-temurin:17-jre

WORKDIR /app

# ビルド結果のjarファイルだけコピー
COPY --from=build /app/target/expenses-api-0.0.1-SNAPSHOT.jar ./app.jar

# 起動コマンド
CMD ["java", "-jar", "app.jar"]
