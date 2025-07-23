echo "=========================================="
echo "Starting Spring Boot application..."
echo "Project directory: $(pwd)"
echo "User: $(whoami)"
echo "Java version: $(java -version 2>&1 | head -n 1)"
echo "Maven version: $(mvn -v | head -n 1)"
echo "Timestamp: $(date)"
echo "=========================================="
cd ratelimiter
mvn spring-boot:run