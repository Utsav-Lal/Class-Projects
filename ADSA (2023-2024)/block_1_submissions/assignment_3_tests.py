"""
assignment 3 tests
"""
from random import random, randrange, sample
from assignment_3 import Stack, get_matching

# 1

# jasper
s = Stack([])
assert s.is_empty()
s.push(0)
assert not s.is_empty()
s.push(1)
assert s.pop() == 1
assert s.pop() == 0
assert s.is_empty()

# agniv

for _ in range(5):
    TEMP = [random() for _ in range(randrange(100))]
    stack = Stack([])
    for i in range(1, len(TEMP)):
        stack.push(TEMP[i])
        assert stack.pop() == TEMP[i], TEMP
        stack.push(TEMP[i])
# 4


def is_stable(women_pref, men_pref, matching):
    """ checks if a matching is stable. note does not have to be class alg. """
    for w_1, m_1 in enumerate(matching):
        for w_2, m_2 in enumerate(matching):
            # slow
            # reads out if w1 values m2 higher than m1 and
            # m2 values w1 higher than w2
            # note if m2==m1 this doesnt trigger
            if women_pref[w_1].index(m_2) < women_pref[w_1].index(m_1):
                # long line limit
                if men_pref[m_2].index(w_1) < men_pref[m_2].index(w_2):
                    return False
    return True


# jasper
cases = [
    (
        [],
        [],
        []
    ),
    (
        [
            [0, 1],
            [1, 0]
        ],
        [
            [0, 1],
            [1, 0]
        ],
        [0, 1]
    ),
    (
        [
            [1, 2, 0],
            [2, 1, 0],
            [1, 2, 0]
        ],
        [
            [2, 0, 1],
            [0, 1, 2],
            [1, 0, 2]
        ],
        [1, 2, 0]
    ),
    (
        [
            [1, 2, 3, 0],
            [2, 1, 3, 0],
            [1, 3, 0, 2],
            [1, 3, 0, 2]
        ],
        [
            [0, 3, 1, 2],
            [3, 0, 2, 1],
            [3, 0, 1, 2],
            [1, 0, 3, 2]
        ],
        [2, 3, 0, 1]
    ),
    (
        [
            [5, 4, 9, 6, 8, 0, 3, 1, 7, 2],
            [3, 2, 8, 0, 7, 6, 5, 1, 9, 4],
            [5, 4, 9, 3, 1, 0, 6, 8, 2, 7],
            [0, 1, 4, 2, 5, 8, 6, 9, 7, 3],
            [5, 7, 8, 9, 4, 1, 2, 0, 3, 6],
            [8, 2, 4, 6, 1, 7, 5, 0, 3, 9],
            [5, 2, 0, 4, 8, 7, 9, 3, 1, 6],
            [2, 3, 0, 7, 5, 9, 6, 4, 1, 8],
            [5, 4, 0, 7, 9, 3, 1, 2, 6, 8],
            [3, 9, 5, 0, 8, 2, 6, 1, 7, 4]
        ],
        [
            [4, 8, 5, 9, 7, 6, 1, 0, 3, 2],
            [1, 0, 4, 2, 8, 3, 5, 9, 6, 7],
            [4, 0, 6, 9, 8, 7, 5, 1, 2, 3],
            [6, 9, 7, 0, 2, 3, 8, 5, 4, 1],
            [6, 3, 7, 9, 0, 5, 8, 2, 1, 4],
            [1, 4, 5, 6, 0, 7, 9, 8, 2, 3],
            [5, 2, 3, 1, 6, 8, 7, 4, 9, 0],
            [6, 3, 0, 5, 4, 2, 1, 7, 8, 9],
            [0, 2, 5, 3, 7, 6, 1, 9, 8, 4],
            [3, 9, 8, 1, 6, 7, 2, 4, 5, 0]
        ],
        [6, 7, 1, 4, 5, 8, 2, 9, 0, 3]
    )
]

for a, b, out in cases:
    guess = get_matching(a, b)
    assert is_stable(a, b, guess), (a, b, guess, out, "not stable")
    assert guess == out, (a, b, guess, out, "does not match")

# agniv

for _ in range(40):
    NUM = 20
    w_pref = [sample(list(range(NUM)), NUM) for _ in range(NUM)]
    m_pref = [sample(list(range(NUM)), NUM) for _ in range(NUM)]
    STABILITY = is_stable(w_pref, m_pref, get_matching(w_pref, m_pref))
    assert STABILITY, (w_pref, m_pref, get_matching(w_pref, m_pref))
    # outputs list with the w_pref, m_pref, and the matching.
