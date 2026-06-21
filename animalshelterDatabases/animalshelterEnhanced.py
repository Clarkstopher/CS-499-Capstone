# animalshelter.py

from pymongo import MongoClient
import pandas as pd
import logging

# ------------------------------------------------------------
# Logging Configuration
# ------------------------------------------------------------
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s"
)

class AnimalShelter(object):
    """CRUD operations for the AAC animals collection with enhanced validation, logging, and advanced queries."""

    # ------------------------------------------------------------
    # Constructor
    # ------------------------------------------------------------
    def __init__(self, username, pwd, host="localhost", port=27017, db="AAC", col="animals"):
        try:
            self.client = MongoClient(
                host=host,
                port=port,
                username=username,
                password=pwd,
                authSource=db,
                serverSelectionTimeoutMS=5000
            )
            self.database = self.client[db]
            self.collection = self.database[col]

            # Verify connection
            self.database.command("ping")
            logging.info("Connected to MongoDB successfully.")

        except Exception as e:
            logging.error(f"Could not connect to MongoDB: {e}")
            raise Exception(f"Could not connect to MongoDB: {e}")

    # ------------------------------------------------------------
    # Helper: Sanitize Input
    # ------------------------------------------------------------
    def _sanitize(self, data):
        """Remove empty values and strip whitespace."""
        return {k.strip(): v for k, v in data.items() if v not in [None, "", " "]}

    # ------------------------------------------------------------
    # CREATE
    # ------------------------------------------------------------
    def create(self, data):
        if not data:
            raise ValueError("Create failed: data parameter is empty.")

        clean_data = self._sanitize(data)
        if not clean_data:
            raise ValueError("Create failed: sanitized data is empty.")

        logging.info(f"Creating new record: {clean_data}")
        result = self.collection.insert_one(clean_data)
        return result.acknowledged

    # ------------------------------------------------------------
    # READ
    # ------------------------------------------------------------
    def read(self, query=None, projection=None, limit=None):
        query = query or {}
        projection = projection if projection is not None else {"_id": False}

        logging.info(f"Read query executed: {query}")

        cursor = self.collection.find(query, projection)
        if limit:
            cursor = cursor.limit(int(limit))

        return list(cursor)

    # Convenience: return DataFrame
    def read_df(self, query=None, projection=None, limit=None):
        docs = self.read(query=query, projection=projection, limit=limit)
        return pd.DataFrame(docs)

    # ------------------------------------------------------------
    # UPDATE
    # ------------------------------------------------------------
    def update(self, query, data):
        if not query:
            raise ValueError("Update failed: query must be provided.")
        if not data:
            raise ValueError("Update failed: update data must be provided.")

        clean_update = self._sanitize(data)
        if not clean_update:
            raise ValueError("Update failed: sanitized update data is empty.")

        logging.info(f"Updating records matching {query} with {clean_update}")
        result = self.collection.update_many(query, {"$set": clean_update})
        return result.modified_count

    # ------------------------------------------------------------
    # DELETE
    # ------------------------------------------------------------
    def delete(self, query):
        if not query:
            raise ValueError("Delete failed: query must be provided.")
        if query == {}:
            raise ValueError("Delete failed: refusing to delete entire collection.")

        logging.info(f"Deleting records matching: {query}")
        result = self.collection.delete_many(query)
        return result.deleted_count

    # ------------------------------------------------------------
    # ADVANCED QUERY METHOD
    # ------------------------------------------------------------
    def search_by_multiple_fields(self, criteria):
        """
        Perform advanced multi-field search using $and, $or, and range operators.
        """
        if not criteria:
            raise ValueError("Search criteria cannot be empty.")

        logging.info(f"Advanced search criteria: {criteria}")
        return list(self.collection.find(criteria, {"_id": False}))