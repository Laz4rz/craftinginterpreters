## Clox

I knew a bit of C. Now I basically don't. I mean, it's the level on which I'm not scarred by it. But writing something is pretty hard. Like with any foreign language, you're not fluent at. I'll read, not really speak.

#### Linked list, or C hello world.

We are asked to:

>Do the same thing for C. To get some practice with pointers, define a doubly-linked list of heap-allocated strings. Write functions to insert, find, and delete items from it. Test them.



```c
#include <stdio.h>
#include <stdlib.h>

// typedef — define something new
// struct  — we chose to define a struct
// node    — name of the define struct, if we want to use it, we have to write `struct node`
// node    — node at the end is an alias for the struct, so we can write `node` instead of full `struct node`
typedef struct node
{
    int number;
    // struct node — type of struct field, same as int above
    // *next       — this field is a pointer to the `struct node` type
    struct node *next;
}
node;

int main(void)
{	
	// initialize a garbage pointer to node structre
	// list will serve as the head of the list
	// its initialized with NULL to indicate the list
	// is empty
	node *list = NULL;

	// now we initialize n with proper memory allocated
	// as we prepare to fill its fields with values
	// the malloc has to be handled properly, to check
	// if the allocation was succesful, if not it will
	// return NULL
	node *n = malloc(sizeof(node));
	if (n == NULL)
	{
		// if n is NULL, malloc failed
		return 1;
	}
	
	// we assign arbitrary values to n that we initialized
	// and correctly allocated memory for
	n->number = 10;
	n->next = NULL;

	// list that will serve as a head of this linked list
	// is assigned with the first node
	list = n;

	printf("List points to number: %i\n", list->number);
	printf("List points to addres: %p\n", list);

	// free the memory allocated for n with malloc before
	free(n);
}

```

1. No fancy compiling, just `gcc -o linkedlist linkedlist.c`
2. Run with `./linkedlist`

How to make it into a doubly-linked list of integers? LIGHT WEIGHT BABEEE.

```c
typedef struct node
{
    struct node *next;
    struct node *prev;
    char string[];
}
node;
```

Also add in the beginning of the script:

```c
#include <string.h>
```

As we want to use the `strcpy` function to copy the string of choice into the struct

```c
strcpy(n->string, "Hello world!");
```

And finally, we need to manually add additional bytes to the malloc for our node. This number corresponds to the length of the string copied + 1 due to termination character. 

```c
node *n = malloc(sizeof(node) + 12 + 1); 
```

Oooof. Easy, but a lot of hops. 
