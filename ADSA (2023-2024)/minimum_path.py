from graph import *

class Queue:
    def __init__(self, inputs):
        self.list = inputs

    def enqueue(self, item):
        self.list.append(item)
    
    def dequeue(self):
        return self.list.pop(0)

    def is_empty(self):
        return len(self.list) == 0
    
def distance(G, v, w):
    if v == w:
        return 0
    
    dis_dic = {}
    queue = Queue([])
    queue.enqueue((v, 0))

    while not queue.is_empty():
        k = queue.dequeue()
        node = k[0]
        count = k[1]  # distance from start node
        dis_dic[node] = True
        for i in G.get_neighbors(node):
            if i not in dis_dic:
                if i == w:
                    return count+1
                queue.enqueue((i, count+1))  # moves to the next node and adds to the distance

print(distance(Graph([('A', 'B'), ('B', 'C'), ('C', 'A')]), 'C', 'B'))
        

def minimum_path(G, v, w):
    if v == w:
        return []
    
    dis_dic = {}
    queue = Queue([])
    queue.enqueue((v, []))

    while not queue.is_empty():
        k = queue.dequeue()
        node = k[0]
        path = k[1]  # current path
        dis_dic[node] = True
        for i in sorted(G.get_neighbors(node)):
            if i not in dis_dic:
                if i == w:
                    return path+[(node, i)]
                queue.enqueue((i, path+[(node, i)]))  # moves to the next node and lengthens the path

print(minimum_path(Graph([('A', 'B'), ('B', 'C'), ('C', 'A'), ('F', 'B')]), 'C', 'F'))
