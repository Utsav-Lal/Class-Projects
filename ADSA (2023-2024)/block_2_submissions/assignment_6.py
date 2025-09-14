# A binary search tree class, as well as the nodes that make up the tree

sentinel = object()


class TreeNode:
    def __init__(self, value):
        self.value = value
        self.parent, self.left, self.right = None, None, None

    # Overwrite this method (and.or put a __str__ in BinarySearchTree) if
    # you want.
    def __str__(self):
        return str(self.value) + (" left" if self.left else "") + \
            (" right" if self.right else "") + \
            (" parent" if self.parent else "")


class BinarySearchTree:
    def __init__(self):
        self.root = None

    def find(self, value, node=sentinel):
        if node == sentinel:
            node = self.root
        if node is None:
            return None
        if node.value == value:
            return node
        if value < node.value:
            return self.find(value, node.left)
        if value > node.value:
            return self.find(value, node.right)

# returns the depth of a subtree with root node
    def get_depth(self, node=sentinel):
        if node == sentinel:
            node = self.root
        if node is None:  # returns the depth of an empty tree
            return -1
        else:
            # The depth of a subtree with the root node is one more than the
            # depth of its largest child
            return max([self.get_depth(node=node.left),
                        self.get_depth(node=node.right)])+1

    def insert(self, num, node=sentinel):  # inserts a value under a node
        if node == sentinel:
            node = self.root
        if node is None:  # If the root is empty it sets the root
            self.root = TreeNode(num)
        else:
            if num < node.value:
                if node.left is None:  # If the space is empty insert it there
                    k = TreeNode(num)
                    node.left = k
                    k.parent = node
                else:  # Otherwise insert it at the next node
                    self.insert(num, node=node.left)
            if num > node.value:  # similar to other case
                if node.right is None:
                    k = TreeNode(num)
                    node.right = k
                    k.parent = node
                else:
                    self.insert(num, node=node.right)

    def traverse_1(self, node=sentinel):  # Traverses a subtree with root node
        if node == sentinel:
            node = self.root
        if node is not None:  # If the node is not empty, traverse it
            self.traverse_1(node.left)
            print(node.value)
            self.traverse_1(node.right)

    # similar to Traverse_1 but instead combines lists
    def traverse_2(self, node=sentinel):
        if node == sentinel:
            node = self.root
        if node is not None:
            return self.traverse_2(node.left) + \
                [node.value] + self.traverse_2(node.right)
        else:
            return []

    def successor(self, node):
        curr = self.root
        closest = None  # successor canidate
        while curr is not None:  # repeat until the tree ends
            if curr.value <= node.value:
                # If the value of the current node is less
                # it cannot be a successor, so move right
                curr = curr.right
            else:
                if closest is None or curr.value <= closest.value:
                    # If a better canidate replace the old
                    closest = curr
                curr = curr.left
        return closest

    def delete(self, node):
        fillnode = None  # subtree combining node's children that replaces node
        if node.left is None:  # If one side is none just attach the other side
            fillnode = node.right
        elif node.right is None:
            fillnode = node.left
        else:  # attaches the left child to the leftmost node on right child
            curr = node.right
            while curr.left is not None:
                curr = curr.left
            curr.left = node.left
            node.left.parent = curr
            fillnode = node.right
        if node.parent is None:  # check if node is root
            self.root = fillnode
        else:
            # find which child the node is and attach
            lesser = True if node.parent.left == node else False
            if lesser:
                node.parent.left = fillnode
            else:
                node.parent.right = fillnode
        if fillnode is not None:
            fillnode.parent = node.parent

    def __str__(self):
        return self.__printTree()

    def __printTree(self, tree=sentinel, depth=0):  # recursive printer
        contents = ""  # string returned

        if tree == sentinel:
            tree = self.root
        if tree is None:
            return ""

        # add string of right subtree
        contents = self.__printTree(tree.right, depth + 1) + contents

        indent = ""
        for i in range(depth):
            indent += "   "

        # indent and add left subtree
        contents += indent+str(tree.value)
        contents += "\n"+self.__printTree(tree.left, depth + 1)

        return contents
