# A binary search tree class, as well as the nodes that make up the tree

sentinel = object()


class AVLNode:
    def __init__(self, value):
        self.value = value
        self.parent, self.left, self.right = None, None, None
        self.depth = 0

    def __str__(self):
        return str(self.value) + (" left" if self.left else "") + \
            (" right" if self.right else "") + \
            (" parent" if self.parent else "")


class AVLTree:
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

    # gets node depth if the node exists, otherwise returns -1
    def get_node_depth(self, node):
        return -1 if node is None else node.depth

    # sets the parent of a node if it exists
    def set_parent(self, node, parent):
        if node is not None:
            node.parent = parent

    # checks whether a subtree is locally balanced
    def loc_balanced(self, node):
        return abs(self.get_node_depth(node.left) -
                   self.get_node_depth(node.right)) < 2

    # recalculates the depth of a node
    def get_new_depth(self, node):
        return max(self.get_node_depth(node.left),
                   self.get_node_depth(node.right))+1

    # checks that a tree is a bst by iterating through it
    def is_bst(self):
        fullvals = self.traverse_2()
        for x in range(1, len(fullvals)):
            if fullvals[x] < fullvals[x-1]:
                return False
        return True

    # checks whether a tree is balanced at each layer
    def is_balanced(self, node=sentinel):
        if node == sentinel:
            node = self.root
        if node is None:
            return True
        return self.loc_balanced(node) \
            and self.is_balanced(node.left) and self.is_balanced(node.right)

    # rotates the tree
    def rotate(self, node, direction):
        antidirection = "left" if direction == "right" else "right"
        newparent = getattr(node, antidirection)
        setattr(node, antidirection, getattr(newparent, direction))
        self.set_parent(getattr(node, antidirection), node)
        setattr(newparent, direction, node)
        newparent.parent = node.parent
        if self.root != node:
            setattr(node.parent, self.get_node_type(node), newparent)
        else:
            self.root = newparent
        node.parent = newparent

        # sets the new depths
        node.depth = self.get_new_depth(node)
        newparent.depth = self.get_new_depth(newparent)

    # recursively balances the tree, moving up
    def balance_and_set_depth(self, node):

        # depth of this subtree (defined conditionally)
        tempdepth = None

        # stores the top of the subtree (for rotations)
        topnode = node

        # checks if the tree is balanced at this level
        if not self.loc_balanced(node):

            # finds the subtree with the highest and lowest depth
            greater = "right" if self.get_node_depth(node.right)\
                  > self.get_node_depth(node.left) else "left"
            antigreater = "right" if greater == "left" else "left"

            # rotates the tree
            # does the smaller rotation on the greater side if needed
            if self.get_node_depth(getattr(
                getattr(node, greater), antigreater)) \
                > self.get_node_depth(getattr(
                    getattr(node, greater), greater)):
                self.rotate(getattr(node, greater), greater)

            # then the full one
            self.rotate(node, antigreater)

            # gets the depth of the new rotated subtree
            tempdepth = node.parent.depth

            # sets the new depth of the parent
            node.parent.depth = self.get_new_depth(node.parent)

            # replaces the top of the subtree
            topnode = node.parent
        else:  # if the tree is balanced just set the depth
            tempdepth = node.depth
            node.depth = self.get_new_depth(node)

        # if the depth of the subtree is changed, balancing must be moved up
        if tempdepth != topnode.depth and topnode.parent is not None:
            self.balance_and_set_depth(topnode.parent)

    # recursive - inserts a value under the node
    def insert(self, num, node=sentinel):
        if node == sentinel:
            node = self.root
        if node is None:  # If the root is empty it sets the root
            self.root = AVLNode(num)
        else:
            # finds which subtree the node is placed in
            path = "left" if num < node.value else "right"

            # if an empty space is found, insert and balance
            if getattr(node, path) is None:
                k = AVLNode(num)
                setattr(node, path, k)
                k.parent = node
                self.balance_and_set_depth(node)
            else:  # otherwise go down further
                self.insert(num, node=getattr(node, path))

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

    def get_node_type(self, child):
        return "left" if child.parent.left == child else "right"

    def delete(self, node):
        if node.parent:
            node_type = self.get_node_type(node)

        # if the node has no children, simply remove it and balance
        if node.left is None and node.right is None:
            if node.parent:
                setattr(node.parent, node_type, None)
                self.balance_and_set_depth(node.parent)
            else:
                self.root = None
            return

        # if one child exists, connect it to the parent
        if node.left is None or node.right is None:
            child_node = node.left if node.left is not None else node.right
            child_node.parent = node.parent
            if node.parent:
                setattr(node.parent, node_type, child_node)
                self.balance_and_set_depth(node.parent)
            else:
                self.root = child_node
            return

        # if both children exist

        # finds the successor node that replaces the deleted one
        suc_node = node.right
        while suc_node.left:
            suc_node = suc_node.left

        # removes the node from the tree
        if (suc_node == node.right):
            suc_node.parent.right = suc_node.right
        else:
            suc_node.parent.left = suc_node.right
        if suc_node.right is not None:
            suc_node.right.parent = suc_node.parent

        # connects the nodes children to the successor
        suc_node.left = node.left
        suc_node.left.parent = suc_node
        suc_node.right = node.right

        # node.right could be None
        self.set_parent(suc_node.right, suc_node)

        # gets node to balance at
        tempnode = suc_node.parent
        if tempnode == node:
            tempnode = suc_node

        suc_node.parent = node.parent

        if node.parent:
            setattr(node.parent, node_type, suc_node)
            self.balance_and_set_depth(tempnode)
        else:
            self.root = suc_node
            self.balance_and_set_depth(suc_node)
        return

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
