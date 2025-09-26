import os
from typing import Tuple

from dotenv import load_dotenv, find_dotenv
load_dotenv(find_dotenv(), override=True)

from mongoengine import connect
from utils.fetch_artwork import fetch_artwork
from utils.filter_art import filter_artwork_data_and_upload
from flask import Flask, request, jsonify, Response
from flask_cors import CORS

app = Flask(__name__)
cors = CORS(app, resources={r"/api/*": {"origins": "*"}})

@app.route("/api/ingest", methods=["POST"])
def main() -> tuple[Response, int]:
    if request.method != "POST":
        return jsonify({"error": "Method not allowed"}), 405

    MONGO_URL = os.getenv("MONGO_URL")

    # Connect to the database, if connection fails we have failsafe
    try:
        connect(db="artworkdb", host=MONGO_URL)
        print("[INFO] Connected to MongoDB")
    except Exception as err:
        print(f"[WARN] Failed to connect to MongoDB, \nError: {err}")

    fetch_artwork()
    filter_artwork_data_and_upload("./artwork.json")

    return jsonify({"success": "Artwork ingested successfully"}), 200


if __name__ == '__main__':
    app.run(debug=False, host="0.0.0.0", port=5001)
