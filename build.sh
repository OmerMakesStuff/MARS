#!/usr/bin/env bash
# build.sh — Compile MARS and produce a single fat Mars.jar bundling FlatLaf
set -e

FLATLAF_JAR="lib/flatlaf-3.7.1.jar"
BIN_DIR="bin"
OUT_JAR="Mars.jar"

if [ ! -f "$FLATLAF_JAR" ]; then
    echo "==> $FLATLAF_JAR not found. Downloading..."
    mkdir -p lib
    if ! curl -f -L -o "$FLATLAF_JAR" "https://repo1.maven.org/maven2/com/formdev/flatlaf/3.7.1/flatlaf-3.7.1.jar"; then
        echo "ERROR: Failed to download $FLATLAF_JAR. Please check your network connection."
        exit 1
    fi
fi

echo "==> Compiling MARS sources into $BIN_DIR..."
rm -rf "$BIN_DIR"
mkdir -p "$BIN_DIR"
find . -name "*.java" -not -path "./$BIN_DIR/*" | \
    xargs javac -cp .:$FLATLAF_JAR -encoding UTF-8 -d "$BIN_DIR"

echo "==> Extracting FlatLaf classes into $BIN_DIR..."
(cd "$BIN_DIR" && jar xf "../$FLATLAF_JAR")

echo "==> Creating fat JAR: $OUT_JAR..."
jar cmf mainclass.txt "$OUT_JAR" \
    PseudoOps.txt \
    Config.properties \
    Syscall.properties \
    Settings.properties \
    MARSlicense.txt \
    mainclass.txt \
    MipsXRayOpcode.xml \
    registerDatapath.xml \
    controlDatapath.xml \
    ALUcontrolDatapath.xml \
    CreateMarsJar.bat \
    build.sh \
    Mars.java \
    docs help images \
    -C "$BIN_DIR" .

echo "==> Done! Run with: java -jar $OUT_JAR"
