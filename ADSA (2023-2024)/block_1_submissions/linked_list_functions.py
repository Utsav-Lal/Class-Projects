from linked_list import LinkedList

def get_by_index(my_list, n):
    if n < my_list.size():
        curr = my_list.head
        count = 0
        while count != n:
            curr = curr.next
            count += 1
        return curr
    return None

def remove_last(my_list, value):
    curr = my_list.head
    last = None
    while curr is not None:
        if curr.value == value:
            last = curr
        curr = curr.next
    if last is not None:
        my_list.delete(last)

def reverse_list(my_list):
    prev = None
    curr = my_list.head
    after = curr
    while curr is not None:
        after = after.next
        curr.next = prev
        prev = curr
        curr = after
    my_list.head = prev
