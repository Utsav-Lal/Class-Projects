from assignment_2 import *
from linked_list import LinkedList
from random import random, randrange, choice


def confirm_same(llist, norm_list):
    return str(llist) == " -> ".join(map(str, norm_list))


# 2

# spencer
tlist0 = LinkedList()
assert get_by_index(tlist0, 0) is None
assert get_by_index(tlist0, 10) is None

tlist1 = LinkedList([5])
assert get_by_index(tlist1, 0).value == 5
assert get_by_index(tlist1, 10) is None

tlist2 = LinkedList([1, 2, 3, 4, 5, 6])
assert get_by_index(tlist2, 2).value == 3
assert get_by_index(tlist2, 5).value == 6
assert get_by_index(tlist2, 10) is None

# agniv
for _ in range(5):
    TEMP = [random() for _ in range(randrange(100))]
    for i in range(1, len(TEMP)):
        ind = randrange(i)
        assert get_by_index(LinkedList(TEMP), ind).value == TEMP[ind], TEMP


# 3

# agniv
for _ in range(5):
    TEMP = [random() for _ in range(randrange(100))]
    for ind in map(randrange, range(1, len(TEMP))):
        llist = LinkedList(TEMP)
        llist.delete(get_by_index(llist, ind))
        assert confirm_same(llist, TEMP[:ind] + TEMP[ind+1:]), TEMP


# 4

# spencer
tlist3 = LinkedList([1, 2, 3, 4, 3, 6])
remove_last(tlist3, 3)
assert confirm_same(tlist3, [1, 2, 3, 4, 6])
remove_last(tlist3, 10)
assert confirm_same(tlist3, [1, 2, 3, 4, 6])

# sofia
tlist4 = LinkedList([1, 2, 3, 5, 3])
remove_last(tlist4, 1)
assert confirm_same(tlist4, [2, 3, 5, 3])
remove_last(tlist4, 3)
assert confirm_same(tlist4, [2, 3, 5])
remove_last(tlist4, 3)
assert confirm_same(tlist4, [2, 5])
remove_last(tlist4, 10)
assert confirm_same(tlist4, [2, 5])
remove_last(tlist4, 2)
assert confirm_same(tlist4, [5])
remove_last(tlist4, 5)
assert confirm_same(tlist4, [])
remove_last(tlist4, 5)
assert confirm_same(tlist4, [])

# agniv
for _ in range(5):
    TEMP = [randrange(100) for _ in range(randrange(1, 100))]
    for val in map(choice, [TEMP for _ in range(10)]):
        llist = LinkedList(TEMP)
        remove_last(llist, val)
        ind = len(TEMP) - 1 - TEMP[::-1].index(val)
        assert confirm_same(llist, TEMP[:ind] + TEMP[ind+1:]), TEMP


# 5

# spencer
tlist5 = LinkedList([1, 2, 3, 4, 5, 6])
reverse_list(tlist5)
assert confirm_same(tlist5, [6, 5, 4, 3, 2, 1])

tlist6 = LinkedList([1])
reverse_list(tlist6)
assert confirm_same(tlist6, [1])

# agniv
for _ in range(100):
    TEMP = [randrange(100) for _ in range(randrange(1, 4))]
    llist = LinkedList(TEMP)
    reverse_list(llist)
    assert confirm_same(llist, TEMP[::-1]), TEMP[::-1]

# sofia (testing all functions simultaneously)
tlist7 = LinkedList([1, 1, 3, 3, 4, 2, 2, 6, 6, 7])
remove_last(tlist7, 7)
assert confirm_same(tlist7, [1, 1, 3, 3, 4, 2, 2, 6, 6])
assert get_by_index(tlist7, 7).value == 6
assert get_by_index(tlist7, 6).value == 2
reverse_list(tlist7)
assert confirm_same(tlist7, [6, 6, 2, 2, 4, 3, 3, 1, 1])
assert get_by_index(tlist7, 7).value == 1
assert get_by_index(tlist7, 0).value == 6
remove_last(tlist7, 1)
assert confirm_same(tlist7, [6, 6, 2, 2, 4, 3, 3, 1])
assert get_by_index(tlist7, 6).value == 3
assert get_by_index(tlist7, 7).value == 1
remove_last(tlist7, 2)
assert confirm_same(tlist7, [6, 6, 2, 4, 3, 3, 1])
assert get_by_index(tlist7, 2).value == 2
assert get_by_index(tlist7, 3).value == 4
reverse_list(tlist7)
assert confirm_same(tlist7, [1, 3, 3, 4, 2, 6, 6])
assert get_by_index(tlist7, 0).value == 1
assert get_by_index(tlist7, 2).value == 3
assert get_by_index(tlist7, 6).value == 6
remove_last(tlist7, 6)
assert confirm_same(tlist7, [1, 3, 3, 4, 2, 6])
assert get_by_index(tlist7, 0).value == 1
assert get_by_index(tlist7, 3).value == 4
assert get_by_index(tlist7, 5).value == 6
assert get_by_index(tlist7, 6) is None
remove_last(tlist7, 7)
assert confirm_same(tlist7, [1, 3, 3, 4, 2, 6])
remove_last(tlist7, 1)
assert confirm_same(tlist7, [3, 3, 4, 2, 6])
assert get_by_index(tlist7, 0).value == 3
assert get_by_index(tlist7, 3).value == 2
assert get_by_index(tlist7, 4).value == 6
assert get_by_index(tlist7, 5) is None
reverse_list(tlist7)
assert confirm_same(tlist7, [6, 2, 4, 3, 3])
assert get_by_index(tlist7, 0).value == 6
assert get_by_index(tlist7, 3).value == 3
assert get_by_index(tlist7, 4).value == 3
assert get_by_index(tlist7, 5) is None
remove_last(tlist7, 3)
assert confirm_same(tlist7, [6, 2, 4, 3])
remove_last(tlist7, 3)
assert confirm_same(tlist7, [6, 2, 4])
remove_last(tlist7, 3)
assert confirm_same(tlist7, [6, 2, 4])
assert get_by_index(tlist7, 0).value == 6
assert get_by_index(tlist7, 3) is None
assert get_by_index(tlist7, 2).value == 4
assert get_by_index(tlist7, 5) is None
remove_last(tlist7, 2)
assert confirm_same(tlist7, [6, 4])
reverse_list(tlist7)
assert confirm_same(tlist7, [4, 6])
assert get_by_index(tlist7, 0).value == 4
assert get_by_index(tlist7, 1).value == 6
assert get_by_index(tlist7, 2) is None
remove_last(tlist7, 6)
assert confirm_same(tlist7, [4])
reverse_list(tlist7)
assert confirm_same(tlist7, [4])
assert get_by_index(tlist7, 0).value == 4
remove_last(tlist7, 4)
assert confirm_same(tlist7, [])
remove_last(tlist7, 4)
assert confirm_same(tlist7, [])
assert get_by_index(tlist7, 10) is None
