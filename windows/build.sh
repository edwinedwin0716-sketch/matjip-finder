#!/bin/sh
# Build 맛볼거리.exe (Windows 10/11) with mono-mcs and pack the zip.
set -e
cd "$(dirname "$0")"
mkdir -p build ../download
mcs -langversion:5 -target:winexe -platform:anycpu -sdk:4.5 -r:System.Windows.Forms -win32icon:app.ico -out:build/맛볼거리.exe Launcher.cs
rm -rf build/pkg && mkdir -p "build/pkg/맛볼거리" && cp build/맛볼거리.exe "설치 및 사용법.txt" "build/pkg/맛볼거리/"
(cd build/pkg && rm -f ../../../download/Matjip-Windows-1.0.zip && zip -qr ../../../download/Matjip-Windows-1.0.zip 맛볼거리)
echo "zip ready: ../download/Matjip-Windows-1.0.zip"
