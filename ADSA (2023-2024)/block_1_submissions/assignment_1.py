def get_max(my_list):
    maxel = None
    for i in my_list:
        if maxel == None or i > maxel:
            maxel = i
    return maxel


def get_max_three(my_list):
    worklist = my_list.copy()
    returnlist = []
    for x in range(3):
        temp = get_max(worklist)
        returnlist.append(temp)
        worklist.remove(temp)
    return returnlist


def invert(s):
    stringbuild = []
    for i in s:
        stringbuild.insert(0, i)
    return ''.join(stringbuild)


def frame_print(words):
    maxlen = 0
    for i in words:
        if maxlen < len(i):
            maxlen = len(i)
    print((maxlen+4)*"*")
    for i in words:
        print("* "+i+(maxlen-len(i))*" "+" *")
    print((maxlen+4)*"*")


def simple_histogram(sentence):
    histogram = {}
    for i in sentence:
        if i.islower():
            if i in histogram:
                histogram[i] += 1
            else:
                histogram[i] = 1
    return histogram


def mergedict(dict1, dict2):
    tempdict1 = dict1.copy()
    tempdict2 = dict2.copy()
    for i in tempdict1:
        if i in tempdict2:
            tempdict2[i] += tempdict1[i]
    tempdict1.update(tempdict2)
    return tempdict1


def funny_histogram(sentence):
    wordlist = sentence.split(" ")
    emptydict = {}
    fulldict = {}
    for word in wordlist:
        for letter in word:
            emptydict[letter] = 0
            fulldict[letter] = {}
    for word in wordlist:
        tempdict = mergedict(emptydict, simple_histogram(word))
        templist = []
        for letter in word:
            if letter not in templist:
                fulldict[letter] = mergedict(fulldict[letter], tempdict)
                templist.append(letter)
    return fulldict
