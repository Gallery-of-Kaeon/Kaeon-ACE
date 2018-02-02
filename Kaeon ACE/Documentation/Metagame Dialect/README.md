# ACE Metagame

The ACE Metagame dialect is a variation of the [ACE]() dialect used to amalgamate save data from multiple applications into a single environment.

All object elements in the ACE Metagame dialect must have their type specified as "ACE Meta",
and their data section must two elements which do not have children.
The first element should have as its content the name of the application it stores data for,
and the second element should have as its content an instance of save data for the application.

### Example

    App 1 Save 1: Type { ACE Meta } Data

    	App 1

    	-
    		State Data 1
    		State Data 2
    	-

    App 2 Save 1: Type { ACE Meta } Data

    	App 2

    	-
    		State Data 3
    		State Data 4
    	-

    App 1 Save 2: Type { ACE Meta } Data

    	App 1

    	-
    		State Data 5
    		State Data 6
    	-