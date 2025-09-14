def generate_perms(n, k, solutions=None, candidate=None):
    if candidate is None:
        candidate = []
    if solutions is None:
        solutions = []
    
    if len(candidate) == k:  # tests the candidate
        solutions.append(candidate[:])
    else:
        extensions = list(range(n))  # generate extensions
        for num in candidate:
            extensions.remove(num)
        
        for extension in extensions:  # process candidates
            candidate.append(extension)
            generate_perms(n, k, solutions, candidate)
            candidate.pop()
    
    return solutions


def make_sum(L, goal, solutions=None, candidate=None):
    if candidate is None:
        candidate = []
    if solutions is None:
        solutions = []

    translated_candidate = [L[index] for index in candidate]  # tests the candidate

    numsum = sum(translated_candidate)
    if numsum == goal:
        solutions.append(translated_candidate[:])

    extensions = []  # generate extensions
    beginning = candidate[-1]+1 if len(candidate) > 0 else 0
    for index in range(beginning, len(L)):
        extensions.append(index)
    
    for extension in extensions:  # process candidates
        candidate.append(extension)
        make_sum(L, goal, solutions, candidate)
        candidate.pop()
    
    return solutions

def queens(n, candidate=None, extensions=None):
    if candidate is None:
        candidate = []
    if extensions == None:
        extensions = []
        for x in range(n):
            for y in range(n):
                extensions.append((x, y))

    if len(candidate) == n: # tests the candidate
        return display(candidate, n)

    if len(candidate) > 0:  # generate extensions
        new_extensions = []
        for i in extensions:
            newCanX = candidate[-1][0]
            newCanY = candidate[-1][1]
            if i[0] != newCanX and i[1] != newCanY and i[0]-i[1] != newCanX - newCanY and i[0]+i[1] != newCanX+newCanY:
                new_extensions.append(i)
        extensions = new_extensions
    
    for extension in extensions:  # process candidates
        candidate.append(extension)
        result = queens(n, candidate, extensions)
        if result != False:
            return result
        candidate.pop()
    
    return False

def display(candidate, n):
    full_list = []  # list of positions
    for row in range(n):
        full_list.append(n*["*"])

    for i in candidate:  # fills positions
        full_list[i[0]][i[1]] = "Q"

    rowlist = [" ".join(i) for i in full_list]  # turns into string
    return "\n".join(rowlist)
