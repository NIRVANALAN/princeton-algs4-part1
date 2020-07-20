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
