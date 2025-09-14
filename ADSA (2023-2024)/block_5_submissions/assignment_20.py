from weighted_graph import WeightedGraph

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
        if index != 0 and self.array[index].weight < self.array[self.__prev_node(index)].weight:
            temp = self.array[index]
            self.array[index] = self.array[self.__prev_node(index)]
            self.array[self.__prev_node(index)] = temp
            self.__heapify_up(self.__prev_node(index))

    def __heapify_down(self, index):
        min_index = None
        if self.__child_node(index, 0) < len(self):
            min_index = self.__child_node(index, 0)
        if self.__child_node(index, 1) < len(self) and \
            (min_index is None or self.array[min_index].weight > self.array[self.__child_node(index, 1)].weight):
            min_index = self.__child_node(index, 1)
        if min_index is not None and self.array[min_index].weight < self.array[index].weight:
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


class UnionFind:
    def __init__(self, objects):
        self.storedict = {}  # list of objects and their parents
        self.sizedict = {}  # size of the trees under roots
        for i in objects:
            self.storedict[i] = i
            self.sizedict[i] = 1
    
    def in_same_group(self, obj_1, obj_2):
        # finds root of object 1
        currobj1 = obj_1
        while self.storedict[currobj1] != currobj1:
            currobj1 = self.storedict[currobj1]
        
        # finds root of object 2
        currobj2 = obj_2
        while self.storedict[currobj2] != currobj2:
            currobj2 = self.storedict[currobj2]
        
        return currobj1 == currobj2
    
    def merge(self, obj_1, obj_2):
        # finds roots of objects
        currobj1 = obj_1
        while self.storedict[currobj1] != currobj1:
            currobj1 = self.storedict[currobj1]
        currobj2 = obj_2
        while self.storedict[currobj2] != currobj2:
            currobj2 = self.storedict[currobj2]
        
        if not currobj1 == currobj2:  # if they are not the same they are in different groups
            if self.sizedict[currobj1] > self.sizedict[currobj2]:
                self.storedict[currobj2] = currobj1
                self.sizedict[currobj1] += self.sizedict[currobj2]
            else:
                self.storedict[currobj1] = currobj2
                self.sizedict[currobj2] += self.sizedict[currobj1]

            return True  # merging sucessful
        
        return False  # objects are already in the same group



# Since all the edges are in the pqueue, we will often have to cycle through many edges before we get an appropriate one,
# making more than V edges removed.
# 
# To make it work, we need to put only the minimum edge in the pqueue, and when an edge is processed, only put in the neighboring ones
# This will make sure that an appropriate edge comes sooner, as the only ones that won't are ones within Tn.
# Each edge removal/insertion will take log(E) time, and each edge will be viewed at most once. Thus, the total time is Elog(E).
# Since E is bounded by V-1 and VC2, log(E) is the same as log(V), so it is Elog(V).
def MST_Prim(graph):
    pqueue = BinaryHeap()  # priority queue
    vertexset = set()  # discovered vertices
    edgeset = set()  # discovered edges
    treeset = set()  # set of edges in tree
    total = 0  # weight of tree

    # finds the first edge and processes it
    min_edge = None
    for i in graph.get_all_edges():
        if min_edge is None or min_edge.weight > i.weight:
            min_edge = i
    if min_edge is None:
        return (set(), 0)
    
    vertexset = vertexset.union(min_edge.vertices)
    edgeset.add(min_edge)
    treeset.add(min_edge)
    total += min_edge.weight

    for endpoint in min_edge.vertices:
        for i in graph.get_edges(endpoint):
            if i not in edgeset:
                pqueue.insert(i)

    # the main loop
    while len(pqueue) != 0:
        # get the minimum edge connected to Tn
        curr = pqueue.get_min()
        pqueue.delete_min()

        # adds it to discovered edges
        edgeset.add(curr)

        # checks whether it connects to a vertex outside Tn
        temp = curr.vertices.copy()
        newvertex = temp.pop()
        if newvertex in vertexset:
            newvertex = temp.pop()
        if newvertex not in vertexset:  # if it does, add it to the tree
            vertexset.add(newvertex)
            treeset.add(curr)
            total += curr.weight
            for i in graph.get_edges(newvertex):
                if i not in edgeset:
                    pqueue.insert(i)

    return (treeset, total)


def MST_Kruskal(graph):
    pqueue = BinaryHeap()
    pqueue.initialize(graph.get_all_edges())
    uf = UnionFind(graph.get_vertices())

    edgeset = set()  # set of edges in tree
    total = 0  # total weight of tree

    while len(pqueue) > 0:
        curr = pqueue.get_min()
        pqueue.delete_min()

        # if the edge connects two different groups add it
        temp = list(curr.vertices)
        if uf.merge(temp[0], temp[1]):
            edgeset.add(curr)
            total += curr.weight

    return (edgeset, total)
