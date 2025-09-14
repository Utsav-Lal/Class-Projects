class Node:
    def __init__(self, num, nxt):
        self.num = num
        self.next = nxt


def collapse_blocks(node):
    if node.next is not None:
        collapse_blocks(node.next)
        if node.num == node.next.num:
            node.next = node.next.next
