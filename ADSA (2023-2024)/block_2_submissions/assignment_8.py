from linked_list import LinkedList


class Tombstone:  # tombstone class
    def __str__(self):
        return " _____ \n/     \\\n| RIP |\n|     |\n"


tombstone = Tombstone()  # tombstone object


class ChainHash:
    def __init__(self, hash_fn, max_size):
        self.table = [None]*max_size
        self.size = 0
        self.hash_fn = hash_fn

    def has_key(self, key):  # checks the list at the hash for the key
        place = self.table[self.hash_fn(key)]
        if place is not None:
            return place.find(key) is not None
        return False

    def insert(self, key):  # chains the key if it doesn't exist
        place = self.table[self.hash_fn(key)]
        if place is None:
            self.table[self.hash_fn(key)] = LinkedList(inputs=[key])
            self.size += 1
        else:
            if place.find(key) is None:
                place.insert(key)
                self.size += 1

    def delete(self, key):  # removes from list, deletes list if empty
        place = self.table[self.hash_fn(key)]
        if place is not None:
            pos = place.find(key)
            if pos is not None:
                place.delete(pos)
                self.size -= 1
                if place.size() == 0:
                    self.table[self.hash_fn(key)] = None

    def get_size(self):
        return self.size


class OpenHash:
    def __init__(self, hash_fn, max_size):
        self.table = [None]*max_size
        self.size = 0
        self.hash_fn = hash_fn

    def has_key(self, key):  # checks from expected position until empty
        count = 0
        currpos = self.hash_fn(key)
        while self.table[currpos] is not None and count < len(self.table):
            if self.table[currpos] == key:
                return True
            currpos += 1
            if currpos == len(self.table):
                currpos = 0
            count += 1
        return False

    def __find_key(self, key):  # finds a key, gets the first tombstone or None
        first_empty = None
        count = 0
        currpos = self.hash_fn(key)
        while self.table[currpos] is not None and count < len(self.table):
            if self.table[currpos] == key:
                return True, None
            currpos += 1
            if currpos == len(self.table):
                currpos = 0
            count += 1
            if first_empty is None and self.table[currpos] == tombstone:
                first_empty = currpos
        if first_empty is None and self.table[currpos] is None:
            first_empty = currpos
        return False, first_empty

    def insert(self, key):  # places in first empty position from hash index
        find_result = self.__find_key(key)
        if not find_result[0]:  # if key is not already in table
            if find_result[1] is not None:  # if empty position exists
                self.table[find_result[1]] = key
                self.size += 1
            else:  # throws error if list is full
                raise Exception("Max size exceeded")

    def delete(self, key):  # replaces the place with a tombstone
        count = 0
        currpos = self.hash_fn(key)
        while self.table[currpos] is not None and count < len(self.table):
            if self.table[currpos] == key:
                self.table[currpos] = tombstone
                self.size -= 1
                return
            currpos += 1
            count += 1
            if currpos == len(self.table):
                currpos = 0

    def get_size(self):
        return self.size
