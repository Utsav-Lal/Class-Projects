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

def get_matching(women_prefs, men_prefs):
    women_engaged = [-1]*len(women_prefs)
    men_engaged = [-1]*len(men_prefs)
    men_proc_prefs = men_prefs.copy()
    for x in range(len(men_proc_prefs)):
        tempdict = {}
        for y in range(len(men_proc_prefs[x])):
            tempdict[men_proc_prefs[x][y]] = y
        men_proc_prefs[x] = tempdict
    first_not_engaged = 0
    women_option_list = len(women_prefs)*[0]
    while first_not_engaged != len(women_engaged):
        x = first_not_engaged
        if men_engaged[women_prefs[x][women_option_list[x]]] == -1:
            women_engaged[x] = women_prefs[x][women_option_list[x]]
            men_engaged[women_prefs[x][women_option_list[x]]] = x
            first_not_engaged += 1
        else:
            if men_proc_prefs[women_prefs[x][women_option_list[x]]]\
                [men_engaged[women_prefs[x][women_option_list[x]]]] \
                    > men_proc_prefs[women_prefs[x][0]][x]:
                women_engaged[men_engaged[women_prefs[x][women_option_list[x]]]] = -1
                first_not_engaged = men_engaged[women_prefs[x][women_option_list[x]]]
                women_engaged[x] = women_prefs[x][women_option_list[x]]
                men_engaged[women_prefs[x][women_option_list[x]]] = x
        women_option_list[x] += 1
    returndict = {}
    for x in range(len(women_engaged)):
        returndict[women_engaged[x]] = men_engaged[x]
    return returndict
