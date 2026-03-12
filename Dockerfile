# Gunakan image Java 17 sebagai dasar (sesuaikan jika kamu pakai Java 21)
FROM openjdk-17-jdk-slim

# Tentukan folder kerja di dalam kontainer
WORKDIR /app

# Salin file .jar hasil build dari folder target ke dalam kontainer
# Pastikan nama file .jar sesuai dengan hasil build maven kamu
COPY target/*.jar app.jar

# Jalankan aplikasi Java-nya
ENTRYPOINT ["java", "-jar", "app.jar"]