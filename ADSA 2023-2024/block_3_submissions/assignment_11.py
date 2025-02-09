import random
import time

def quicksort(L, start, end):
    if end > start:
        split = separate(L, start, end)
        quicksort(L, start, split)
        quicksort(L, split+2, end)

def separate(L, start, end):
    p = L[end]
    i = start-1
    for x in range(start, end):
        if L[x] <= p:
            i += 1
            temp = L[x]
            L[x] = L[i]
            L[i] = temp
    temp = L[i+1]
    L[i+1] = L[end]
    L[end] = temp
    return i

for x in range(5, 15):
    l = list(range(0, 2**x))
    random.shuffle(l)
    t = time.time()
    quicksort(l, 0, len(l)-1)
    print((time.time()-t)*10**3)
# Seems to be mostly nlog(n) after testing


# def Quickmedian(L, start, end, k):
#    separate L from start to end, get the middle element
#    if the lower part contains k-1 elements, then the middle has the kth, so return it
#    else if the lower half contains less:
#        find the k-S-1th element of the upper part where S is the length of the lower part
#    otherwise
#        find the kth element of the lower part
def Quickmedian(L, start, end, k):
    i = separate(L, start, end)
    if i+1-start == k-1:
        return L[i+1]
    elif i+1-start < k-1:
        return Quickmedian(L, i+2, end, k-(i+1-start)-1)
    else:
        return Quickmedian(L, start, i, k)

for x in range(5, 15):
    l = list(range(0, 2**x))
    random.shuffle(l)
    t = time.time()
    Quickmedian(l, 0, len(l)-1, len(l)+(-(len(l)+1))//2)
    print((time.time()-t)*10**3)

# works as theta(n)
