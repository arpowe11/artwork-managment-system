import json
from models.Artwork import Artwork

def filter_artwork_data_and_upload(p: str) -> None:
    try:
        with open(p) as f:
            data = json.load(f)

        data_list = data["data"]  # Gets the data list that holds data in dict format
        data_list_length = len(data_list)
        print(f"[INFO] There are {data_list_length} data items in {p}", end="\n\n")

        print("\n[INFO] Loading artworks into mongodb collection:\n")
        for i in range(0, data_list_length):
            try:
               # Prints the art attributes of the specific artwork depending on the index
               print(f"ID: {i+1}")
               print("Title:", data_list[i]["title"])
               print("Artist:", data_list[i]["artist_title"])
               des = data_list[i]["description"] or "N/A"
               print("Description:", des)
               print("Description:", data_list[i]["description"])
               print("Art Type:", data_list[i]["classification_title"])
               print("Thumbnail:", data_list[i]["thumbnail"]["lqip"])
               print()
            except TypeError as err:
                print("Thumbnail: None")
                print()
                continue

        # Find the current max ID in the collection
        max_id_obj = Artwork.objects.order_by('-id').first()
        current_max_id = max_id_obj.id if max_id_obj else 0

        # Doing the same operation as above, instead of logging, it uploads those values to the schema
        for i, art in enumerate(data_list):
            title = art.get("title", "N/A")
            existing_art = Artwork.objects(title=title).first()
            try:
                if not existing_art:
                    # New artwork: assign ID after the current max
                    current_max_id += 1
                    artist = art.get("artist_title") or "N/A"
                    description = art.get("description") or "N/A"
                    art_type = art.get("classification_title") or "N/A"
                    thumbnail = art.get("thumbnail", {}).get("lqip") or "N/A"

                    Artwork(
                        id=current_max_id,
                        title=title,
                        artist=artist,
                        description=description,
                        art_type=art_type,
                        thumbnail=thumbnail
                    ).save()

            except TypeError as err:
                print(f"[WARN] {err}")
                continue

    except FileNotFoundError as err:
        print("[WARN] File not found, please provide the correct path to the file and try again.")


# For testing filtering in local file
if __name__ == "__main__":
    filter_artwork_data_and_upload("../artwork.json")
