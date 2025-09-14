class BinaryHeap:
    def __init__(self):
        self.array = []

    def __prev_node(self, index):
        return int((index+index%2)/2-1)

    def __child_node(self, index, side):
        return int(2*index+1+side)

    def is_empty(self):
        return len(self.array)==0

    def __heapify_up(self, index):
        if index != 0 and self.array[index] < self.array[self.__prev_node(index)]:
            temp = self.array[index]
            self.array[index] = self.array[self.__prev_node(index)]
            self.array[self.__prev_node(index)] = temp
            self.__heapify_up(self.__prev_node(index))

    def __heapify_down(self, index):
        min_index = None
        if self.__child_node(index, 0) < len(self):
            min_index = self.__child_node(index, 0)
        if self.__child_node(index, 1) < len(self) and \
            (min_index is None or self.array[min_index] > self.array[self.__child_node(index, 1)]):
            min_index = self.__child_node(index, 1)
        if min_index is not None and self.array[min_index] < self.array[index]:
            temp = self.array[index]
            self.array[index] = self.array[min_index]
            self.array[min_index] = temp
            self.__heapify_down(min_index)

    def __len__(self):
        return len(self.array)

    def insert(self, item):
        self.array.append(item)
        self.__heapify_up(len(self)-1)

    def get_min(self):
        if len(self) > 0:
            return self.array[0]
        else:
            return None

    def delete_min(self):
        if len(self) <= 1:
            self.array = []
        else:
            self.array[0] = self.array[len(self)-1]
            del self.array[len(self)-1]
            self.__heapify_down(0)

    def initialize(self, inputs):
        for i in inputs:
            self.insert(i)
