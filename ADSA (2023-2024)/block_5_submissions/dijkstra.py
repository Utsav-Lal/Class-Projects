import math
from weighted_graph import WeightedGraph

class ModifiableBinaryHeap:
    def __init__(self):
        self.array = []
        self.locations = {}

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
            self.locations[self.array[index]] = index
            self.array[self.__prev_node(index)] = temp
            self.locations[temp] = self.__prev_node(index)
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
            self.locations[self.array[index]]=index
            self.array[min_index] = temp
            self.locations[temp]=min_index
            self.__heapify_down(min_index)

    def __len__(self):
        return len(self.array)

    def insert(self, item):
        self.array.append(item)
        self.locations[item] = len(self.array)-1
        self.__heapify_up(len(self)-1)

    def get_min(self):
        if len(self) > 0:
            return self.array[0]
        else:
            return None

    def delete_min(self):
        if len(self) == 1:
            del self.locations[self.array[0]]
            self.array = []
        else:
            self.array[0] = self.array[len(self)-1]
            del self.locations[self.array[0]]
            self.locations[self.array[len(self)-1]] = 0
            del self.array[len(self)-1]
            self.__heapify_down(0)

    def initialize(self, inputs):
        for i in inputs:
            self.insert(i)

    def update(self, item):  # updates the value of a node
        if item in self.locations:
            index = self.locations[item]
            self.__heapify_down(index)
            self.__heapify_up(index)


class Vertex:  # vertex wrapper that stores its distance
    def __init__(self, vertex, value):
        self.vertex = vertex
        self.value = value

    def __lt__(self, other):
        return self.value < other.value

    def __gt__(self, other):
        return self.value > other.value

    def __le__(self, other):
        return self.value <= other.value

    def __ge__(self, other):
        return self.value >= other.value


def dijkstrize(graph, start_node):
    # creates the vertex wrappers and a map from a vertex to its wrapper
    vertexdict = {x:Vertex(x, math.inf if x != start_node else 0) for x in graph.get_vertices()}

    # initializes the pqueue
    pqueue = ModifiableBinaryHeap()
    pqueue.initialize(vertexdict.values())

    distdict = {}  # dictionary of nodes to their distances

    edgechecked = {x:False for x in graph.get_all_edges()}  # the edges already relaxed

    numvertices = len(pqueue) # number of vertices

    for i in range(numvertices):
        # get the minimum vertex
        curr = pqueue.get_min()
        pqueue.delete_min()

        # relaxes the edges
        for j in graph.get_edges(curr.vertex):
            if not edgechecked[j]:  # if the edge has already been checked don't do it again
                # gets the vertex at the other end
                templist = list(j.vertices)
                newvertex = vertexdict[templist[0] if templist[0] != curr.vertex else templist[1]]
                
                # changes the vertex value
                if newvertex.value > curr.value+j.weight:
                    newvertex.value = curr.value+j.weight
                    pqueue.update(newvertex)

        # adds the current vertex to the dictionary
        distdict[curr.vertex] = curr.value
    
    return distdict   # returns the dictionary

print(dijkstrize(WeightedGraph([('A', 'B', 1), ('B', 'C', 2), ('D', 'E', 3), ('E', 'F', 5), ('G', 'H', 3), ('H', 'I', 2), ('A', 'D', 1), ('B', 'E', 1), ('C', 'F', 3), ('D', 'G', 1), ('E', 'H', 2), ('F', 'I', 4)]), 'A'))
