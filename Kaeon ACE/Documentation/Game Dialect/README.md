[Home](https://github.com/Gallery-of-Kaeon/Kaeon-ACE/blob/master/README.md)

# The ACE Dialect

ACE is a dialect of the [ONE](https://github.com/Gallery-of-Kaeon/Kaeon-FUSION/blob/master/Kaeon%20FUSION/Documentation/1%20-%20Foundations/1%20-%20ONE/README.md) language that allows for the specification of objects,
object aggregation, and object references.

Every element is an ACE document is in the form of an object element.
An object element may either define a new object or reference a pre-defined one.

The content of an object element will specify its alias.

If an object element defines a new object,
it will have any combination of the following children:
an element with the content "Type",
an element with the content "Children",
and an element with the content "Data".

The type element,
if present,
will have a child with content specifying the type of the object.

The children element,
if present,
will have other object elements as children.

The data element,
if present,
will have children that each specify a field.
The content of each child will specify the alias of the field it specifies,
and its children will specify its content.

If an object element references an existing object,
it will have a single child with the content "Reference".
The reference element will have a child with content that matches the alias of another object element.
If the specified alias matches the alias of two or more objects,
the nearest object element will take priority.

### Example

    my dog { Type: dog }

    	Data: weight { 25 } name { Fido }

    my pets { Type: pets }

    	Children

    		dog { Reference: my dog }

    		my cat { Type: cat }
    			Data: weight { 8 } name { Fluffy }