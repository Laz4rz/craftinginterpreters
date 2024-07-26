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


