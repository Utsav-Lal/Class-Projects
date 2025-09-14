class Node:
    def __init__(self, avalue, achildren=None):
        self.value = avalue
        self.children = []
        if achildren is not None:
            self.children = achildren


class BinomialHeap:
    def __init__(self, inits=None):
        self.heap = []

        # initializes list with a set of values
        if inits is not None:
            for num in inits:
                self.insert(num)

    def __combine(self, bin_tree_1, bin_tree_2):
        if bin_tree_1 is None:
            return bin_tree_2
        elif bin_tree_2 is None:
            return bin_tree_1
        else:  # makes the tree with the larger root the child of the other
            if bin_tree_1.value < bin_tree_2.value:
                bin_tree_1.children.append(bin_tree_2)
                return bin_tree_1
            else:
                bin_tree_2.children.append(bin_tree_1)
                return bin_tree_2

    def merge(self, bin_heap_2):
        # Makes both heaps the same length
        if len(bin_heap_2.heap) > len(self.heap):
            for _ in range(len(bin_heap_2.heap)-len(self.heap)):
                self.heap.append(None)
        else:
            for _ in range(len(self.heap)-len(bin_heap_2.heap)):
                bin_heap_2.heap.append(None)

        carry = None  # carries a bigger tree to the next slot
        temp_sum = None  # holds the sum of two trees at a time

        # Iterates through each slot of the heap
        for slot in range(len(bin_heap_2.heap)):

            # Combines the two trees in the same slot
            temp_sum = self.__combine(bin_heap_2.heap[slot], self.heap[slot])

            # If the two trees combine to a higher order tree,
            # it gets stored in the carry,
            # while the previous carry is put into the slot
            if temp_sum is not None and len(temp_sum.children) > slot:
                self.heap[slot] = carry
                carry = temp_sum
            else:  # Otherwise, the previous carry is added to the sum
                temp_sum = self.__combine(temp_sum, carry)

                # If the tree is now a higher order tree,
                # we store it in the carry
                if temp_sum is not None and len(temp_sum.children) > slot:
                    carry = temp_sum
                    self.heap[slot] = None
                else:  # Otherwise, the carry is empty
                    self.heap[slot] = temp_sum
                    carry = None

        # If two trees at the end of the list combine and make a bigger one,
        # a new slot is needed.
        if carry is not None:
            self.heap.append(carry)

    # Creates a temporary heap to store the new element for merging
    def insert(self, value):
        carrier = BinomialHeap()
        carrier.heap.append(Node(value))
        self.merge(carrier)

    def is_empty(self):
        return self.heap == []

    # Finds the lowest root as the min
    def get_min(self):
        curr_min = None
        for root in self.heap:
            if root is not None and (curr_min is None
                                     or root.value < curr_min):
                curr_min = root.value
        return curr_min

    def delete_min(self):
        # Finds the tree with the smallest root
        min_tree_slot = None
        for tree in range(len(self.heap)):
            if self.heap[tree] is not None and \
                    (min_tree_slot is None or
                        self.heap[tree].value
                        < self.heap[min_tree_slot].value):
                min_tree_slot = tree

        # Removes the root and puts the children into a carrier to be merged
        min_tree = self.heap[min_tree_slot]
        carrier = BinomialHeap()
        carrier.heap = min_tree.children
        if min_tree_slot == len(self.heap)-1:
            del self.heap[min_tree_slot]  # Prevents trailing Nones
        else:
            self.heap[min_tree_slot] = None
        self.merge(carrier)


def binomial_heapsort(nums):
    bHeap = BinomialHeap(nums)
    sorted_nums = []
    while not bHeap.is_empty():
        sorted_nums.append(bHeap.get_min())
        bHeap.delete_min()
    return sorted_nums
