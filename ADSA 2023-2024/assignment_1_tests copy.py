from assignment_1 import *
import string
from random import random, choice
from collections import Counter  # might be cheating?


# 1a

# allison
assert get_max([3, 2, 1]) == 3
assert get_max([4, 2, 5, 5, 1, 6, 3]) == 6
assert get_max([-94, 68, 11, -21, -50, 82, -32, -57, 38, 59]) == 82

# agniv
for _ in range(5):
    TEMP = [random() for _ in range(100)]
    for i in range(1, 101):
        assert get_max(TEMP[:i]) == max(TEMP[:i])


# 1b

# jason
assert get_max_three([10, 4, 3, 6, 7, 10]) == [10, 10, 7]
assert get_max_three([4, 4, 4, 4, 4]) == [4, 4, 4]

# allison
assert get_max_three([1, 1, 2, 2, 3, 3]) == [3, 3, 2]
assert get_max_three([1, 3, 4, 2, 5, 2, 4, 3, 1]) == [5, 4, 4]
assert get_max_three([6, -8, -7, -1, -2]) == [6, -1, -2]
assert get_max_three([20, 34, 73, 86, 15]) == [86, 73, 34]
assert get_max_three([7943, 6865, 2469, 409, 9935, 9093]) == [9935, 9093, 7943]
assert get_max_three([-39, 11, -87, 76, -53, 31, -14, 43, 11]) == [76, 43, 31]

# agniv
for _ in range(5):
    TEMP = [random() for _ in range(100)]
    for i in range(3, 101):
        assert get_max_three(TEMP[:i]) == sorted(TEMP[:i])[::-1][:3], TEMP[:i]


# 2

# jason
assert invert("nice dog!") == "!god ecin"

# allison
assert invert("madam") == "madam"
assert invert("live") == "evil"
assert invert("lived") == "devil"
assert invert("live") == "evil"
assert invert("was it a car or a cat i saw?") == "?was i tac a ro rac a ti saw"
assert invert("he2dlgb@6h[r{y@kd):'6") == "6':)dk@y{r[h6@bgld2eh"
assert invert("ne'4=:)\"|s0o(kt2,y#5q+Eac(R") == "R(caE+q5#y,2tk(o0s|\"):=4'en"

# jasper
assert invert("") == ""

# agniv
for _ in range(5):
    TEMP = ''.join([choice(string.ascii_letters) for _ in range(100)])
    for i in range(101):
        assert invert(TEMP[:i]) == TEMP[:i][::-1], TEMP[:i]


# 4a

# jason
assert simple_histogram("my yams") == {'m': 2, 'y': 2, 'a': 1, 's': 1}

# agniv
alphabet = list("abcdefghijklmnopqrstuvwxyz ")
for _ in range(5):
    TEMP = ''.join([choice(alphabet) for _ in range(100)])
    for i in range(101):
        TEMP_DICT = dict(Counter(TEMP[:i]))
        try:  # very dirty code i do not endorse this
            del TEMP_DICT[" "]
        except KeyError:
            pass
        assert simple_histogram(TEMP[:i]) == TEMP_DICT, TEMP[:i]


# 4b

# jason
assert funny_histogram("an aardvark and a cat") == {
    'c': {'c': 1, 'a': 1, 't': 1, 'n': 0, 'r': 0, 'd': 0, 'v': 0, 'k': 0},
    'a': {'c': 1, 'a': 7, 't': 1, 'n': 2, 'r': 2, 'd': 2, 'v': 1, 'k': 1},
    't': {'c': 1, 'a': 1, 't': 1, 'n': 0, 'r': 0, 'd': 0, 'v': 0, 'k': 0},
    'n': {'c': 0, 'a': 2, 't': 0, 'n': 2, 'r': 0, 'd': 1, 'v': 0, 'k': 0},
    'r': {'c': 0, 'a': 3, 't': 0, 'n': 0, 'r': 2, 'd': 1, 'v': 1, 'k': 1},
    'd': {'c': 0, 'a': 4, 't': 0, 'n': 1, 'r': 2, 'd': 2, 'v': 1, 'k': 1},
    'v': {'c': 0, 'a': 3, 't': 0, 'n': 0, 'r': 2, 'd': 1, 'v': 1, 'k': 1},
    'k': {'c': 0, 'a': 3, 't': 0, 'n': 0, 'r': 2, 'd': 1, 'v': 1, 'k': 1}
    }
