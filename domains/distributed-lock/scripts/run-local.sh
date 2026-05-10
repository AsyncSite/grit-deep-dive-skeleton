#!/usr/bin/env bash
set -euo pipefail

docker compose up -d redis
./gradlew test
