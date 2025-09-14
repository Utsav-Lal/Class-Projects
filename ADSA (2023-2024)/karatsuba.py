def karatsuba(x, y):

    # string representations of digits
    strx = str(x)
    stry = str(y)

    # keeps track of numbers of 0s added so we can remove at end
    diff = 0

    # adds padding 0s
    if len(strx) > len(stry):
        diff = len(strx)-len(stry)
        stry = stry + (len(strx)-len(stry))*"0"
    elif len(stry) > len(strx):
        diff = len(stry)-len(strx)
        strx = strx + (len(stry)-len(strx))*"0"

    # base case
    if (len(strx) == 1):
        return int(strx)*int(stry)/10**diff
    
    # splits the numbers into right and left parts
    xl = strx[:int(len(strx)/2)]
    xr = strx[int(len(strx)/2):]
    yl = stry[:int(len(stry)/2)]
    yr = stry[int(len(stry)/2):]

    # values for the algorithm
    a = karatsuba(int(xl), int(yl))
    b = karatsuba(int(xr), int(yr))
    c = karatsuba(int(xr)+int(xl), int(yr)+int(yl))

    # computes the value using the algorithm
    rawvalue = str(int((a*10**(2*(len(strx)-int(len(strx)/2)))+(c-a-b)*10**(len(strx)-int(len(strx)/2))+b)))

    # shifts the digit to remove the extra 0s added on
    value = rawvalue[:len(rawvalue)-diff]
    return 0 if len(value) == 0 else int(value)
