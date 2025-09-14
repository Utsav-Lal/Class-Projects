prices = {0:0, 1:1, 2:1, 3:2, 4:2, 5:4, 6:5, 7:5, 8:5, 9:10, 10:12,
          11:13, 12:16, 13:13, 14:21, 15:21, 16:21, 17:24, 18:25, 19:25, 20:25,
          21:25, 22:25, 23:26, 24:26, 25:26 ,26:27, 27:27, 28:27, 29:27, 30:28}


# ********************************************
# Rod dividing example for dynamic programming
# ********************************************
def sad_rod_recurse(n):
    if n==0:
        return prices[0]
    current_value = 0
    for i in range(1,n+1):
        new_value = prices[i] + sad_rod_recurse(n-i)
        current_value = max(current_value, new_value)
    return current_value


def rod_top_hat(n, computed_values=None):
    if computed_values is None:
        computed_values = {0:prices[0]}
    if n in computed_values:
        return computed_values[n]
    current_value = 0
    for i in range(1,n+1):
        new_value = prices[i] + rod_top_hat(n-i, computed_values)
        current_value = max(current_value, new_value)
    computed_values[n] = current_value
    return current_value


def rod_bottom_up(n):
    computed_values = {0: prices[0]}
    for rod_length in range(1,n+1):
        current_value = 0
        for i in range(1, rod_length+1):
            new_value = prices[i] + computed_values[rod_length-i]
            current_value = max(current_value, new_value)
        computed_values[rod_length] = current_value
    return computed_values[n]


def rod_with_instructions(n, computed_values=None, first=True):
    if computed_values is None:  # contains the instructions and the price
        computed_values = {0:([], prices[0])}

    if n in computed_values:
        if first:  # return only the instructions if it is the original call
            return computed_values[n][0]
        
        return computed_values[n]  # otherwise return both
    
    instructions = []  # the instruction set

    current_value = 0
    for i in range(1,n+1):
        result = rod_with_instructions(n-i, computed_values, first=False)
        new_value = prices[i] + result[1]
        if (new_value > current_value):
            current_value = new_value
            if i == n:
                instructions = []
            else:
                instructions = [i] + [x+i for x in result[0]]

    computed_values[n] = (instructions, current_value)

    if first:  # again, returns only the instructions if this is the original call
        return instructions
    else:
        return (instructions, current_value)


def max_sublist_sum(my_list):
    maxvalue = 0  # the largest sum
    lastvalue = 0  # the sum for a sequence starting at the index before
    for i in range(len(my_list)):
        if lastvalue < 0:  # if the last value is less than 0 don't include it
            if my_list[i] > maxvalue:
                maxvalue = my_list[i]
            lastvalue = my_list[i]
        else:
            if my_list[i]+lastvalue > maxvalue:
                maxvalue = my_list[i]+lastvalue
            lastvalue = my_list[i]+lastvalue
    return maxvalue
        

def LIS(lst):  # this is n^2
    computedvals = {}

    maxvalue = 0
    for i in range(len(lst)-1, -1, -1):  # check for the largest sequence starting at every integer
        result = LIS_helper(lst, computedvals, i)  # finds the largest sequence starting there
        if result > maxvalue:
            maxvalue = result
    return maxvalue


def LIS_helper(lst, computedvals, start):  # finds the largest sequence starting at an index
    if start in computedvals:
        return computedvals[start]
    else:
        # finds the largest sequence starting at every integer greater than this one
        maxval = 0
        for i in range(len(lst)-1, start, -1):
            if lst[i] > lst[start]:
                result = LIS_helper(lst, computedvals, i)
                if result > maxval:
                    maxval = result
        totalval = 1+maxval
        computedvals[start] = totalval
        return totalval


def max_sublist_product(my_list):
    maxvalue = 1  # the largest sum
    last_pos_value = 1  # the sum for a sequence starting at the index before
    last_neg_value = None
    for i in my_list:
        if i > 0:
            if last_pos_value is not None:
                if last_pos_value < 1:
                    last_pos_value = i
                else:
                    last_pos_value *= i
            else:
                last_pos_value = i
            
            if last_neg_value is not None:
                last_neg_value *= i
        elif i < 0:
            temp_neg_value = None
            if last_pos_value is not None and last_pos_value >= 1:
                temp_neg_value = last_pos_value*i
                last_pos_value = None

            if last_neg_value is not None:
                last_pos_value = last_neg_value*i
            else:
                last_neg_value = temp_neg_value
            
        if last_pos_value is not None and last_pos_value > maxvalue:
            maxvalue = last_pos_value
    return maxvalue

import random
import time
for x in range(1, 101):
    inp = []
    for i in range(x):
        inp.append(random.random()*10)
    a = time.time()
    max_sublist_product(inp)
    print((time.time()-a)*10**6)
