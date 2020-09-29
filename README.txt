# Recommender-CS-18
Project repo for recommender

Link for project is 
https://cs.brown.edu/courses/csci0180/content/projects/recommender.html

Completed by
Josiah Lim
Robert Boudreaux

Brief description of our code structure and any known bugs:

DATASET:
	IMPLEMENTS: IAttributeDataset
	FIELDS: ArrayList<T> datums
			    This is an arraylist of all of the datums that are a part of
			    this dataset.
		    LinkedList<String> attr
			    A Linked list of strings representing all of the attributes in
			    each datum

	CONSTRUCTOR:LinkedList<Strings> attributes
			        A Linked list of strings representing all of the attributes
			        in each datum
		         LinkedList<T> rows
			        A linked list containing all of the datums that will be
			        members of the dataset

	The purpose of a dataset is to provide a container for a set of datums.
	There are several manipulations of the dataset to provide a user with
	subsets of the dataset depending on what they ask for. There are no methods
	in the class that can manipulate the fields, therefore, a reference to an
	instance of Dataset is a consistent, immutable object that provides a
	baseline in tree generator.

NODE:
    IMPLEMENTS: INode
    FIELDS: String currentAttributeToTest
                This is an arraylist of all of the datums that are a part of
                this dataset.
            INode[] branches
                A Linked list of strings representing all of the attributes
                in each datum
            Boolean isBest
                A boolean that is true if previousValue field in the
                node is the most common value of the dataset, false
                otherwise
            Object previousDecisionValue
                an object representing the value of the test attribute of
                the previous node's test attribute.


    CONSTRUCTOR:
            String test
                A string representing the current attribute to test for
            int branchNum
                An integer representing the number of possible values of the
                 test attribute (based off the input dataset)
            Object prevdecision
                An object representing the value of the test attribute of
                the previous node's test attribute.

    Each node is our representation of a tree. Each node contains a current test
     that is to be tested for, as well as all of the values of that
     corresponding attribute in the dataset used to build the node.
    Each node has the ability to traverse the rest of it's branches and reach
    the bottom of the tree.
    A node is the basic building block for the tree. Each node knows how to
    traverse the tree by looking into each of its branches and evaluating the
    current test attribute and deciding if the contents of the current branch
    are equivalent to that evaluation.

LEAF:
    IMPLEMENTS: INode
    FIELDS:
        Object decision
            The decision to take in the Leaf, because all datums in the leaf
            share a common attribute value for the attribute being tested for.
        Boolean isBest
            A boolean that is true if previousValue field in the node is the
            most common value of the dataset, false otherwise
    CONSTRUCTOR:
         Object decision
             The decision to take in the Leaf, because all datums in the leaf
             share a common attribute value for the attribute being tested for.
         Boolean isBest
             A boolean that is true if previousValue field in the node is the
             most common value of the dataset, false otherwise

    A leaf is our representation of the edge of the decision tree. A leaf is the
     recursive result of lookup tree.
    You arrive at a leaf similar to how you arrive at each node.

TREEGENERATOR:
    IMPLEMENTS: IGenerator
    FIELDS:
        INode decisionTree
            An INode to represent the decision tree that is to be used
        IAttributeDataset<T> dataset
            The dataset that we are using to base our decision tree on
    CONSTRUCTOR:
        IAttributeDataset<T> dataset
                    The dataset that we are using to base our decision tree on

    A tree genetator contains a dataset and an initially null node. A tree uses
    buildGenerator to mutate the current node field into the specifications
    decided by build generator***. The node (now the head of a tree) can have
    any datum of the same type as contained in the dataset passed into it at
    this point using the lookupDecision method. The node can also be mutated
    again by passing in the buildGenerator function.

    HOW BUILD GENERATOR WORKS:
    	Build generator takes in a target attribute and stores a local variable
    	attributes that is a linked list of strings. There is a for loop that
    	iterates 100 times to randomly scramble the contents of the attributes.
    	Then, the target attribute is moved to the end of the list and passed
    	into the helper function.

    	Build generator utilizes a helper function to expand the scope of the
    	problem. The way this works is it will...
    		-go through the attributes linked list and record it's current place
    		 by utilizing the globalI and incrementing depth
    		-create the next level by partitioning the current dataset by the
    		 current node's test attribute
    		-fill the next level with nodes by evaluating all possible values
    		 (as per the dataset) of the current test attribute and also figure
    		 out which one is the best
    		-recursively continue by passing in partitioned subset dataset as
    		 well as the incremented globalI until you arrive at leaves
    		-When you arrive at the last index in the attributes list, create a
    		 leaf to end the recursion
    		-assign resulting tree to node field

VEGETABLES (DATUMS):
    IMPLEMENTS: IAttributeDatum
    FIELDS:
          String color
            string representing the color of the vegetable
          Boolean lowCarb
            a boolean on whether or not the vegetable is low in carbohydrates
          Boolean highFiber
            a boolean on whether or not the vegetable is high in fiber
          Boolean likeToEat
            a boolean on whether or not the vegetable is liked to be eaten
    CONSTRUCTOR:
          String color
            string representing the color of the vegetable
          Boolean lowCarb
            a boolean on whether or not the vegetable is low in carbohydrates
          Boolean highFiber
            a boolean on whether or not the vegetable is high in fiber
          Boolean likeToEat
            a boolean on whether or not the vegetable is liked to be eaten

    This is one example of a datum. This needs to be changed for each different
    dataset that is not a vegetable dataset
*NO KNOWN BUGS


//////////////////////RESPONSES TO QUESTIONS//////////////////////
â€¢Run the file BiasTest.java (a few times if you get both the cis male and cis 
female hired ratio as zero). Run the file several times and write down the first
five hire percentages you get (where both percentages are not zero).
What do you notice in the differences between cis male and cis female hired 
ratios printed out?

Cis female rate: 0.19 | Cis male rate: 0.46
Cis female rate: 0.15 | Cis male rate: 0.53
Cis female rate: 0.10 | Cis male rate: 0.53
Cis female rate: 0.12 | Cis male rate: 0.40
Cis female rate: 0.16 | Cis male rate: 0.50

Significant bias exists in hiring differences for cis female and cis male.

â€¢Now, change filepath to â€œtrain_candidates_equal.csvâ€. Run the file several 
times and write down the first five hire percentages you get (where both 
percentages are not zero). Do you still see the bias in the different hiring 
ratio?

Cis female rate: 0.08 | Cis male rate: 0.26
Cis female rate: 0.09 | Cis male rate: 0.20
Cis female rate: 0.12 | Cis male rate: 0.27
Cis female rate: 0.10 | Cis male rate: 0.24
Cis female rate: 0.08 | Cis male rate: 0.19

Yes, bias still present.

â€¢How do you think each training dataset (also presented on the Google sheet) 
affects the hiring bias?
The correlated one eliminates hiring bias. That is because gender is not part of 
the algorithm at all. It seems that the construction of the hiring decision tree
itself is unaffected by gender, therefore, all decisions are unaffected by the 
gender of the applicant.

For correlated, we have
Cis female rate: 0.31 | Cis male rate: 0.31
Cis female rate: 0.37 | Cis male rate: 0.37
Cis female rate: 0.28 | Cis male rate: 0.28

//////////////////////RESPONSES TO ETHICS QUESTIONS//////////////////////
1. How could you modify your recommender system to prevent gender bias from 
arising? Identify an improvement to the training data and/or algorithm that 
could help prevent unfair outcomes.

Not include gender as an attribute in creating the decision tree. That way, the 
candidate's gender is never taken into account during traversal of decision tree 
in the lookUpRecommendation method. Or, for all nodes on the decision tree that 
involves gender, we can do a random selection of which child node to select
(i.e. no preference on male or female at the gender nodes).

2. How does the approach your code used to choose which attribute to split on 
impact the resulting bias, if at all?

Our code chooses the attribute randomly. That seems to affect candidates that 
are in the "middle range" in terms of hiring success. It results in a bias of 
unpredictability to those in the middle. These people cannot reliably expect 
their applications to succeed, even given a large number of applications.

It would give biases to the attribute types which are at the top of the 
Linkedlist of attributes. That means that the attributes which are first "filled
up" by the applicant may be the most/least important attribute because it 
determines which node of the decision tree this applicant would traverse. In our
vegetable class, "color" might become the most/least significant factor because 
it is the first attribute on our Linkedlist of attributes.

3. If your hiring rates vary each time you build a new classifier, why does this
occur? If we fix the algorithm to choose the same attribute to split on each 
time, could we eliminate the bias?

Hiring rates may differ because the order of the attribute chosen at each split 
differs for each build. Candidates will traverse the tree differently and 
receive varied hiring success.

Choosing the same attribute to split on each time may not result in an 
elimination of bias. Bias may underlie the entire decision tree because the 
original dataset that we base our algorithm on may have already been a biased 
sample (dataset is based on previous biased human hires). Therefore, the 
decisions recommended by the computer algorithm will reflect hiring preferences 
based off the biased dataset.

4. Given the companyâ€™s current skewed demographics, outline the extent to which 
ensuring fairness in this hiring system is the responsibility of the engineers, 
hiring manager, or someone else.

It should be everyone's responsibility. Anyone who is aware that a particular 
aspect of the hiring systems is posing unfairness should take action within 
their means. It is bad to pretend as if nothing is happening. Passivity allows 
for unfairness to continue thriving.

5. Proponents of algorithmic hiring argue that these systems have the potential 
to make the hiring process more efficient and remove some human biases from 
hiring decisions. Other than potential bias, what criteria do you think an 
organization should consider when deciding whether to use an algorithmic system 
for hiring?

Monetary, environmental, energy cost involved in running the code relevant to 
the hiring system. Organization might also want to have ways to be answerable to
hiring decisions besides "the algorithm/computer decided so". When a certain 
hire perhaps in future resulted in an organizational catastrophe, the 
organization, wanting to avoid such hires in the future, may not have the 
necessary information to understand how they made the bad hire. That is because 
humans are able to pick up certain behavioral cues through in person interviews,
for example. The algorithm may also lead to hires that are disproportionately 
similar in profile. That would affect organizational diversity. A good team 
needs to have different talents and a diversity of thought.

6. The recommender system you created could be used in a wide variety of 
contexts. Youâ€™ve observed the potential of the system to generate biased 
results. Do you the programmer have any responsibility to prevent potentially 
harmful uses of your code? What is one step that you (the programmer) could take
to prevent negative consequences of someone using your general-purpose 
recommender system? You can propose a technical, non-technical, or policy idea. 
(Note that there is no â€œcorrectâ€ answer being sought here â€“ we are curious to 
see what ideas emerge from the class).

Yes, the programmer has responsibility to prevent potentially harmful use of 
code. Perhaps, we might want to be able to "privatize" or "lock" 
users of the recommender system from accessing the mostCommonValue method. They 
might be able to change that method to skew for some attribute values over 
others. For example, if the leadership experience node had 4 false and 1 true, 
but the hirer really likes people with leadership experience, they may 
manipulate mostCommonValue to multiply the true count by 5. Such that now, the 
mostCommonValue gives true instead of false (5:4 ratio). Similar acts can be 
done to preferentially boost certain ethnicities, education, age, etc.