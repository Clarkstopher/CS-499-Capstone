# animalshelter.py

from pymongo import MongoClient
import pandas as pd

class AnimalShelter(object):
    """CRUD operations for the AAC animals collection."""

    def __init__(self, username, pwd, host="localhost", port=27017, db="AAC", col="animals"):
        # Use structured params with authSource to avoid URI quirks
        self.client = MongoClient(
            host=host,
            port=port,
            username=username,
            password=pwd,
            authSource=db,          # important: authenticate against the AAC DB
            serverSelectionTimeoutMS=5000
        )
        self.database = self.client[db]
        self.collection = self.database[col]

        # Verify connection early
        try:
            self.database.command("ping")
            print("Connected to MongoDB")
        except Exception as e:
            raise Exception(f"Could not connect to MongoDB: {e}")

    def create(self, data):
        if not data:
            raise ValueError("Data parameter is empty")
        result = self.collection.insert_one(data)
        return result.acknowledged

    def read(self, query=None, projection=None, limit=None):
        """
        Returns a list of dicts. Use projection to include/exclude fields.
        """
        query = query or {}
        projection = projection if projection is not None else {"_id": False}
        cursor = self.collection.find(query, projection)
        if limit:
            cursor = cursor.limit(int(limit))
        return list(cursor)  # materialize to avoid cursor issues in callbacks

    def read_df(self, query=None, projection=None, limit=None):
        """
        Convenience: return a pandas DataFrame with _id excluded by default.
        """
        docs = self.read(query=query, projection=projection, limit=limit)
        df = pd.DataFrame(docs)
        return df

    def update(self, query, data):
        if not query or not data:
            raise ValueError("Query and update data must be provided")
        result = self.collection.update_many(query, {"$set": data})
        return result.modified_count

    def delete(self, query):
        if not query:
            raise ValueError("Query must be provided")
        result = self.collection.delete_many(query)
        return result.deleted_count
    