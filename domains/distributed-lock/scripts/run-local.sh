#!/usr/bin/env bash
set -euo pipefail

docker compose up -d redis
python3 -m venv .venv
. .venv/bin/activate
pip install -e .
pytest -q
