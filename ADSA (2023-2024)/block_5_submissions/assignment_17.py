from graph import *
from linked_list import LinkedList

class Stack:
    def __init__(self, inputs):
        self.store = LinkedList()
        for i in inputs:
            self.push(i)
    def push(self, value):
        self.store.insert(value)
    def pop(self):
        temp = self.store.head.value
        self.store.delete(self.store.head)
        return temp
    def is_empty(self):
        return self.store.size() == 0
    def __str__(self):
        return str(self.store)


def DFS_tree(G, start_node):
    dis_dic = {}  # dictionary of discovered nodes (for quick search)
    dis_set = set()
    DFS_helper(G, start_node, dis_dic, dis_set)
    return dis_set

def DFS_helper(G, node, dis_dic, dis_set, parent=None):
    # adds discovered to the list and dictionary
    dis_dic[node] = True
    if parent != None:
        dis_set.add((parent, node))

    # finds the next ones
    for i in sorted(G.get_neighbors(node)):
        if i not in dis_dic:
            DFS_helper(G, i, dis_dic, dis_set, parent=node)


def has_cycle(G):
    start_node = G.get_vertices()[0]
    dis_dic = {}  # dictionary of discovered nodes (for quick search)
    return cycle_helper(G, start_node, None, dis_dic)

def cycle_helper(G, node, parent_node, dis_dic):
    # adds discovered to the dictionary
    dis_dic[node] = True

    # iterates through neighbors checking for back edge
    for i in sorted(G.get_neighbors(node)):
        if i != parent_node and i in dis_dic:  # if back edge found detect a cycle
            return True
        elif i != parent_node:
            if cycle_helper(G, i, node, dis_dic):  # otherwise process the neighbor
                return True
    return False


def DFS_component_stack(G, node):
    dis_dic = {}  # dictionary of discovered nodes (for quick search)
    dis_list = []  # list of discovered nodes
    stack = Stack([node])  # stack of processes

    while not stack.is_empty():
        node = stack.pop()  # takes the first process available
        if node not in dis_dic:
            # discovers the node
            dis_dic[node] = True
            dis_list.append(node)

            # puts the subprocesses back in reverse order to match the recursive one
            for i in reversed(sorted(G.get_neighbors(node))):
                if i not in dis_dic:
                    stack.push(i)
    return dis_list


def DFS_cut_node(G):
    start_node = G.get_vertices()[0] # choses a start node
    dis_dic = {} # dictionary of discovered nodes
    cut_nodes = [] # list of cut nodes
    count = 0  # keeps track of the time each node was discovered
    cut_node_helper(G, start_node, dis_dic, count, None, cut_nodes)
    return cut_nodes

def cut_node_helper(G, node, dis_dic, count, parent_node, cut_nodes):
    # discovers node
    dis_dic[node] = count

    ancestor_node = None  # earliest ancestor the node has a second path to
    is_cut = False
    open_child_count = 0 # number of indpendent subtrees for the root

    # checks through its neighbors
    for i in sorted(G.get_neighbors(node)):
        if i != parent_node:
            if i in dis_dic:  # if the node is already discovered, it can become the earliest connection to an ancestor node
                if dis_dic[i] <= count and (ancestor_node is None or dis_dic[i] <= dis_dic[ancestor_node]):
                    ancestor_node = i
            else:
                open_child_count += 1
                k = cut_node_helper(G, i, dis_dic, count+1, node, cut_nodes)
                if k is None or k == node:
                    # if the earliest ancestor of a node's subtree is itself, removing it will remove the subtree making it a cut node
                    is_cut = True
                else:
                    if ancestor_node is None or dis_dic[k] <= dis_dic[ancestor_node]:  # if a subtree has a path to an ancestor, so does the node
                        ancestor_node = k
    
    if is_cut and (parent_node is not None or open_child_count > 1):  # if the root node only has one connected subtree, it is not a cut node
        cut_nodes.append(node)

    if ancestor_node == node:
        ancestor_node = None

    return ancestor_node
