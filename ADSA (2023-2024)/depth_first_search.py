from graph import *


def DFS_component(G, node):
    dis_dic = {}  # dictionary of discovered nodes (for quick search)
    dis_lst = []  # list of discovered nodes in order
    DFS_helper(G, node, dis_dic, dis_lst)
    return dis_lst


def DFS_helper(G, node, dis_dic, dis_lst):
    # adds discovered to the list and dictionary
    dis_dic[node] = True
    dis_lst.append(node)

    # finds the next ones
    for i in sorted(G.get_neighbors(node)):
        if i not in dis_dic:
            DFS_helper(G, i, dis_dic, dis_lst)

print(DFS_component(Graph([('A', 'G'), ('A', 'C'), ('A', 'B'), ('C', 'D'), ('D', 'B'), ('B', 'E'), ('B', 'F')]), "A"))


def DFS_full(G):
    done_dic = {}  # dictionary of found nodes (for quick search)
    done_lst = []  # list of found nodes in order
    components = 0  # amount of connected components
    for i in G.get_vertices():
        if i not in done_dic:  # if so, it is a new component
            result = DFS_component(G, i)  # finds all nodes in the component

            for j in result:  # translates into the dictionary
                done_dic[j] = False

            done_lst = done_lst+result  # adds to the list

            components += 1

    return (done_lst, components)

print(DFS_full(Graph([('A', 'G'), ('A', 'C'), ('A', 'B'), ('C', 'D'), ('D', 'B'), ('B', 'E'), ('B', 'F'), ('I', 'J'), ('H', 'I')])))


def DFS_component_with_times(G, node):  # wrapper is pretty much the same as DFS_component
    dis_dic = {}
    dis_lst = []
    DFS_helper_with_times(G, node, dis_dic, dis_lst, 0)
    return dis_lst

def DFS_helper_with_times(G, node, dis_dic, dis_lst, time):
    ind = len(dis_lst)  # stores where to add the item after the end time is discovered

    prevtime = time  # stores the start time

    # adds the node in
    dis_dic[node] = True
    dis_lst.append(None)
    
    for i in sorted(G.get_neighbors(node)):
        if i not in dis_dic:
            time = DFS_helper_with_times(G, i, dis_dic, dis_lst, time+1)  # adds the time at the node
            
    dis_lst[ind] = (node, prevtime, time+1)  # adds the node

    return time+1  # returns the time it took for the parent nodes


print(DFS_component_with_times(Graph([('A', 'G'), ('A', 'C'), ('A', 'B'), ('C', 'D'), ('D', 'B'), ('B', 'E'), ('B', 'F')]), "A"))

def DFS_wrapper(G):
    return DFS_full(G)[1]


for x in range(0, 100):
    a = 0
    for _ in range(0, 10):
        a += DFS_wrapper(make_random_graph(x, edge_probability=0.1))
    print(a/10)

#It increases at first, then starts decreasing towards 0. All graphs with different edge probabilities are similar, with the size inversely proporional
