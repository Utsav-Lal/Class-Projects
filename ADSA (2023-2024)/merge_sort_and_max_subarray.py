import math

def merge_sort(lst, start_index=None, end_index=None):
    start = start_index
    end = end_index
    if start is None or end is None:  # sets the default interval
        start = 0
        end =len(lst)-1
    if end > start:  # checks that there is more than one element
        div = math.ceil((end-start+1.0)/2)  # splitting point

        merge_sort(lst, start_index=start, end_index=start+div-1)
        merge_sort(lst, start_index=start+div, end_index=end)

        left = lst[start:start+div]  # items in left interval
        right = lst[start+div:end+1]  # items in right interval

        returnlist = []  # replaces list interval

        while len(left) > 0 or len(right) > 0:
            if len(left) == 0:
                returnlist.append(right[0])
                right = right[1:]
            elif len(right) == 0:
                returnlist.append(left[0])
                left = left[1:]
            else:
                if left[0] < right[0]:
                    returnlist.append(left[0])
                    left = left[1:]
                else:
                    returnlist.append(right[0])
                    right = right[1:]

        for x in range(len(returnlist)):  # replaces the elements
            lst[start+x] = returnlist[x]

def max_subarray_sum(lst, start_index=None, end_index=None):
    start = start_index
    end = end_index
    if (len(lst)) == 0:  # makes sure the list isn't empty
        return 0
    if start is None or end is None:  # sets default values
        start = 0
        end =len(lst)-1
    if end == start:  # base case - return a value if not negative
        if lst[start] >= 0:
            return lst[start]
        else:
            return 0
    else:
        div = math.ceil((end-start+1.0)/2)  # splitting point

        maxL = max_subarray_sum(lst, start_index=start, end_index=start+div-1)
        maxR = max_subarray_sum(lst, start_index=start+div, end_index=end)

        halfmaxL = 0  # max sum of the left side of a sequence crossing the middle
        halfleftsum = 0  # current sum of the left side
        for x in range(start+div-1, start-1, -1):  # builds a sequence to the left of the middle
            halfleftsum += lst[x]
            if halfleftsum > halfmaxL:
                halfmaxL = halfleftsum
        
        # same but for the right
        halfmaxR = 0
        halfrightsum = 0
        for x in range(start+div, end+1):
            halfrightsum += lst[x]
            if halfrightsum > halfmaxR:
                halfmaxR = halfrightsum

        # maximum sum from a sequence accross the middle
        maxMid = halfmaxL+halfmaxR

        # finds the maximum sequence and moves it forward
        returnval = maxR
        if maxL > returnval:
            returnval = maxL
        if maxMid > returnval:
            returnval = maxMid
        return returnval
