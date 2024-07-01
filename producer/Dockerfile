FROM gradle:jdk17-jammy

WORKDIR /app

COPY . .

RUN gradle build

CMD ["gradle", "bootRun"]