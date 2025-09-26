import requests
import json


def fetch_artwork() -> None:
    url = "https://api.artic.edu/api/v1/artworks"  # Free public API, does not need to be kept secret
    response = requests.get(url)

    print("[INFO] Status Code:", response.status_code)

    if response.status_code == 200:
        data = response.json()
        # Save to a JSON file
        with open("artwork.json", "w", encoding="utf-8") as f:
            json.dump(data, f, ensure_ascii=False, indent=4)
        print("[INFO] Response saved to artwork.json")
    else:
        print("[WARN] Error:", response.text)


# For testing request in local file
if __name__ == "__main__":
    fetch_artwork()
