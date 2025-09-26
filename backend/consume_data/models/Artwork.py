from mongoengine import Document, StringField, IntField

# Artwork schema for mongodb data collection
class Artwork(Document):
    id = IntField(primary_key=True, required=True)
    title = StringField(required=True)
    artist = StringField(required=False)
    description = StringField(required=False)
    art_type = StringField(required=False)
    thumbnail = StringField(required=False)
