#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// typedef — define something new
// struct  — we chose to define a struct
// node    — name of the define struct, if we want to use it, we have to write `struct node`
// node    — node at the end is an alias for the struct, so we can write `node` instead of full `struct node`
typedef struct node
{
    // struct node — type of struct field, same as int above
    // *next       — this field is a pointer to the `struct node` type
    struct node *next;
    struct node *prev;
    // string as a dynamic array of chars (dynamic, as the length is not specified)
    // has to be at the end of the struct, so that the memory allocated for it can
    // be flexible
    char string[]; 
}
node;


void find_in_list(node *head, char *string_find)
{
	printf("Looking for: %s\n", string_find);
	node *current = head;
	while (current != NULL)
	{
		if (strcmp(current->string, string_find) == 0)
		{
			printf("Found it! Current string: %s\n", current->string);
		}
		node *next = current->next;
		current = next;
	}

}

void print_list(node *head)
{
	node *current = head;
	while (current != NULL)
	{
		node *next = current->next;
		printf("Current string: %s\n", current->string);
		current = next;
	}
}

void free_list(node *head)
{
	node *current = head;
	while (current != NULL)
	{
		node *next = current->next;
		printf("Freeing %p\n", current);
		free(current);
		current = next;
	}
}

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
	// we have to add number of bytes corresponding to 
	// the length of the string we will put there
	// and 1 for the string termination character
	// DISCLAIMER: it may work without it, only mallocing
	// sizeof(node), but this is very dangerous, and you
	// should always account for the dynamic array you 
	// put in the struct
	node *n = malloc(sizeof(node) + 12 + 1); 
	if (n == NULL)
	{
		// if n is NULL, malloc failed
		return 1;
	}
	
	// we assign arbitrary values to n that we initialized
	// and correctly allocated memory for
	// we can't directly assign a string to a dynamic array
	// so we have to use strcpy from string.h to do it for us
	strcpy(n->string, "Hello world!");
	n->next = NULL;
	n->prev = NULL;

	// list that will serve as a head of this linked list
	// is assigned with the first node
	list = n;

	printf("List points to number: %s\n", list->string);
	printf("List points to addres: %p\n", list);

	// print list function
	print_list(list);

	// find in list function
	char *find_string = "Hello world!";
	find_in_list(list, find_string);

	// free the memory allocated for n with malloc before
	// free(n);
	free_list(list);
}


