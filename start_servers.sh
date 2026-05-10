#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
OLLAMA_URL="${OLLAMA_URL:-http://localhost:11434}"

echo "==> Checking Ollama at ${OLLAMA_URL}"
if ! curl -sfo /dev/null "${OLLAMA_URL}/api/tags"; then
  echo "Ollama is not reachable at ${OLLAMA_URL}." >&2
  echo "Start it with 'ollama serve' (or open the Ollama app) and try again." >&2
  exit 1
fi
echo "    Ollama is up."

VENV_UVICORN="${SCRIPT_DIR}/python/.venv/bin/uvicorn"
if [[ ! -x "${VENV_UVICORN}" ]]; then
  echo "Python venv not found at python/.venv. Create it with:" >&2
  echo "  python3 -m venv python/.venv && python/.venv/bin/pip install fastapi uvicorn msclap" >&2
  exit 1
fi

echo "==> Launching FastAPI server in a new Terminal window"
osascript <<EOF
tell application "Terminal"
    activate
    do script "cd ${SCRIPT_DIR}/python && ${VENV_UVICORN} server:app --reload"
end tell
EOF

echo "==> docker compose up -d"
docker compose up -d

echo "==> All services started."
