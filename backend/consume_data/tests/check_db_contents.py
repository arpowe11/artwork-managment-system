import os
from dotenv import load_dotenv, find_dotenv
load_dotenv(find_dotenv(), override=True)

from mongoengine import connect
from models.Artwork import Artwork

def check_db_contents():
    MONGO_URL = os.getenv("MONGO_URL")

    # Connect to the database, if connection fails we have failsafe
    try:
        connect(db="artworkdb", host=MONGO_URL)
        print("[INFO] Connected to MongoDB")
    except Exception as err:
        print(f"[WARN] Failed to connect to MongoDB, \nError: {err}")

    # Count how many documents exist
    print("Total documents:", Artwork.objects.count())

    # Print first 5 documents
    for doc in Artwork.objects[:5]:
        print(doc.to_json())


if __name__ == "__main__":
    check_db_contents()
