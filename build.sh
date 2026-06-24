#!/bin/bash
# Build script for RelicSMPCore

echo "Building RelicSMPCore Plugin..."
echo "================================"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed!"
    exit 1
fi

# Check if Java 21+ is installed
JAVA_VERSION=$(java -version 2>&1 | grep -oP 'version "\K[^"]*' | cut -d. -f1)
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo "ERROR: Java 21 or higher is required! Current: Java $JAVA_VERSION"
    exit 1
fi

echo "Java Version: $JAVA_VERSION"
echo "Building..."

# Clean and build
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ BUILD SUCCESSFUL!"
    echo "================================"
    echo "JAR Location: target/relicsmpcore-1.0.0.jar"
    echo ""
else
    echo "❌ BUILD FAILED!"
    exit 1
fi
