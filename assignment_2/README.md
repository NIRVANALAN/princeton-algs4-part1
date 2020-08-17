# Generics
- TODO why java prohibits generic array creation?
 Java arrays are covariant but Java generics are not
 https://dzone.com/articles/covariance-and-contravariance
## Class
```
public class Stack<Item>{
    private class node{
        Item item;
    }
}
```
## Array
```
a = (Item[]) new Object[capacity]; // not new Item[capacity];
```


# Assignments(95)
- 读题，command-line argument or StdIn
- use ```n``` in size(), rather than calling iterator
- object loitering //todo
- ```copy=null```
- use array for constant access cost
- make sure you know which version(linkedList, array) works best...
