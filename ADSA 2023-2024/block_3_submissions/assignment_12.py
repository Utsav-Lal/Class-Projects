def magic_median(L, start, end, k=None):
    if k is None:  # sets the k-value if not provided
        k = (end-start+2)//2
    pivot = get_pivot_position(L, start, end)  # finds the pivot
    return Quickmedian(L, start, end, k, pivot)  # sorts according to the pivot

def get_pivot_position(L, start, end):
    medlst = []  # list of medians
    for x in range(0, (end-start+1)//5):  # finds the median of each bucket
        medlst.append(Quickmedian(L, start+x*5, start+x*5+4, 3))
    returnele = None  # pivot to return
    if len(medlst) == 0:  # if buckets cannot be made just find the median
        returnele = Quickmedian(L, start, end, (end-start+2)//2)
    else:  # otherwise find the median of buckets
        returnele = magic_median(medlst, 0, len(medlst)-1)
    for x in range(start, end+1):  # find the index of the element and return it
        if L[x] == returnele:
            return x

def Quickmedian(L, start, end, k, pivot_index=None):
    i = separate(L, start, end, pivot_index)
    if i+1-start == k-1:
        return L[i+1]
    elif i+1-start < k-1:
        return Quickmedian(L, i+2, end, k-(i+1-start)-1)
    else:
        return Quickmedian(L, start, i, k)

def separate(L, start, end, pivot_index=None):
    if pivot_index is None:
        pivot_index = end
    else:
        temp = L[end]
        L[end] = L[pivot_index]
        L[pivot_index] = temp
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



import time
import random


for x in range(0, 8):
    l = list(range(2**x))
    random.shuffle(l)
    t = time.time()
    magic_median(l, 0, len(l)-1)
    print((time.time()-t)*100)
    
# seems to be linear
